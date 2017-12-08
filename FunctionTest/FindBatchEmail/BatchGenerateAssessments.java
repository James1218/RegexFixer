package gov.state.uim.batch.cop;
//CIF_00589
//New Batch File to generate Assessments

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Level;

import gov.state.uim.batch.cop.dao.BatchAssessmentsDAO;
import gov.state.uim.batch.cop.data.BatchIssueAssessmentData;
import gov.state.uim.batch.framework.BaseBatch;
import gov.state.uim.batch.framework.BatchContext;
import gov.state.uim.batch.framework.util.BatchUtility;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.mail.Email;
import gov.state.uim.framework.service.IObjectAssembly;


public class BatchGenerateAssessments extends BaseBatch {

	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(BatchGenerateAssessments.class);

	public static final String UPDATED_BY = "BatchGenerateAssessments";
	public static final String CREATED_BY = "BatchGenerateAssessments";
	
	@Override
	public void performBusinessProcess() throws Exception {
		final BatchAssessmentsDAO dao = new BatchAssessmentsDAO();
		final Timestamp startTs = super.getSelectionStartTime();		
		final Timestamp endTs = super.getSelectionEndTime();
		if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Selecting records updated between "+ startTs+ " and "+ endTs);
		}
		if (startTs == null){
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("The last batch run timestamp does not exist in table for BatchGenerateWarrant Batch. So Exiting batch....");					
			}
			return;
		}  
		try{
			//CIF_00589
			//Updates all the overpayments to FINL, which have no appeal pending flag and are established more than 30 days back
			dao.updateWithNamedQuery(startTs, "updateOverpaymentLegalStatus", dao, "updateOverpaymentLegalStatus");
			
			//CIF_INT_02675 || Defect_968 Start
			//Updates the legal status to NLGL for the modified overpayments (fraud to non-fraud or non-fraud to fraud)
			dao.updateWithNamedQuery(new java.sql.Date(startTs.getTime()), "updateModifiedOverpaymentLegalStatus", dao, "updateModifiedOverpaymentLegalStatus");
			//CIF_INT_02675 || Defect_968 End
			
			List<BatchIssueAssessmentData> ovpFINLDataList = dao.getOvpDataInFINLState();
			Map<String, List<Long>> map = new HashMap<String, List<Long>>();
			//Set claimantSsnSet = new LinkedHashSet();
			
			//CIF_02806 Start
			//List<BatchIssueAssessmentData> listAfterBankruptcyFilter = dao.filterExistingListForBankruptcy(ovpFINLDataList);
			//List<BatchIssueAssessmentData> listAfterOvpHoldFilter = dao.filterExistingListForOvpHold(listAfterBankruptcyFilter);
			//List<BatchIssueAssessmentData> listAfterPaymentPlanFilter = dao.filterExistingListForPaymentPlan(listAfterOvpHoldFilter);
			//CIF_02806 End
			if(ovpFINLDataList!=null && !ovpFINLDataList.isEmpty()){
		if (LOGGER.isInfoEnabled()) {
				LOGGER.info("No of overpayment that are going to be processed:::"+ovpFINLDataList.size());
		}
				for(Iterator<BatchIssueAssessmentData> itr = ovpFINLDataList.iterator();itr.hasNext();){
					BatchIssueAssessmentData bean = itr.next();				
					if (map.containsKey(bean.getSsn())) {		
						List<Long> ovpList = map.get(bean.getSsn());
						ovpList.add(bean.getOverpaymentId());
						map.put(bean.getSsn(), ovpList);
					}else {	
						List<Long> newOvpList = new ArrayList<Long>();
						newOvpList.add(bean.getOverpaymentId());
						map.put(bean.getSsn(), newOvpList);
					}
				}
				/*for(Iterator iter = ovpFINLDataList.iterator(); iter.hasNext(); ){
					BatchIssueAssessmentData data = (BatchIssueAssessmentData)iter.next();
					claimantSsnSet.add(data.getSsn());
				}*/
		if (LOGGER.isInfoEnabled()) {
				LOGGER.info("No of SSN's that are going to be processed:::"+map.size());
		}
			}else{
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug("No Overpayments to Generate Assessments");
				}
			}
				
			
			//CIF_03155 Start
			Map<String, List<Long>> tempMap = new HashMap<String, List<Long>>();
			int assessmentCount=0;
			IObjectAssembly objAssembly = super.getNewObjectAssembly();
			while(!map.isEmpty()) {
				int i=0;
				Set<Entry<String, List<Long>>> assessSet = map.entrySet();
				for(Iterator<Entry<String, List<Long>>> itr = assessSet.iterator();itr.hasNext();){
					Entry<String, List<Long>> entry = itr.next();
					tempMap.put(entry.getKey(), entry.getValue());
					i++;
					if(i==250 || tempMap.isEmpty()) {
						break;
					}
				}				
				objAssembly.addData("tempAssessMap",tempMap);
				//CIF_P2_01498 || logger info
				LOGGER.batchRecordProcess("Processing claimants for generating assessments. Please check BIZ log for more details");
				executeBusinessService(objAssembly,"validateOverpayments");
				assessmentCount = assessmentCount+tempMap.size();
				
				Set<Entry<String, List<Long>>> tempAssessSet = tempMap.entrySet();
				for(Iterator<Entry<String, List<Long>>> itr = tempAssessSet.iterator();itr.hasNext();){
					Entry<String, List<Long>> entry = itr.next();
					map.remove(entry.getKey());
				}				
				tempMap.clear();
		if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Total assessments generated till now: "+assessmentCount);
		}
			}
			//CIF_03155 End
			

			//Reset the legal status if prosecution is convicted and overpayment balance is zero
			//CIF_INT_02288 || UIM-5334 Start
			objAssembly = super.getNewObjectAssembly();
			List<Long> ovpProsecutionConvictedList = dao.getOvpProsecutionConvicted();
			if(null!=ovpProsecutionConvictedList && !ovpProsecutionConvictedList.isEmpty()) {
				objAssembly.addData("ovpProsecutionConvictedList",ovpProsecutionConvictedList);
				executeBusinessService(objAssembly,"validateOverpayments");
			}			
			//CIF_INT_02288 || UIM-5334 End
			
		}catch(Exception e){
		if (LOGGER.isEnabledFor(Level.ERROR)) {
			LOGGER.error("Message ",e);
		}
			sendEmailsForNotification(UPDATED_BY);
		}
		
	}
	/**
	 * Email if Batch Fails
	 * @param subject BatchName
	 */
	public void sendEmailsForNotification(String subject) {
		Email email = BatchUtility.getEmail();
		StringBuffer sb = new StringBuffer();
		sb.append("<html>")
		.append("The Following failed in the middle of execution : "+subject)
		.append("</html>");
		email.setHtmlMsg(sb.toString());
		email.setSubject(subject);
		email.setFrom("anshul.kumar@wyo.gov", UPDATED_BY);
		String emailTo = BatchContext.getConfig().getProperty("email");
		String[] emailToArr = emailTo.split(",");
		for (int i = 0; i < emailToArr.length; i++) {
			email.addTo(emailToArr[i]);
		}
		try {
			email.sendByHostName();
		} catch (Exception ex) {
			LOGGER.busErrorInternal("Error While Sending the Email" + ex);
		}
	}
}
	
	
	
	