package gov.state.uim.app.struts;

import org.apache.commons.fileupload.FileItem;

import java.util.List;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.Resources;

import gov.state.uim.GlobalConstants;
import gov.state.uim.ViewConstants;
import gov.state.uim.app.AppealConstants;
import gov.state.uim.domain.bean.MessageHolderBean;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.StringUtility;
/**
* @struts.form
* 		name = "processproofreadingform"
*/

public class ProcessProofReadingForm extends BaseValidatorForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5871343302710935243L;
	private String docketNumber;
	private String previousDecision;
	private String aljDecision;
	private String issueDescription;
	private String issueDetails;
	private String verifyDecisionStartDate;
	private String verifyDecisionEndDate;
	private ArrayList caseHistoryParagraph;
	private ArrayList issueParagraph;
	private ArrayList factFindingParagraph;
	private ArrayList decisionReasonParagraph;
	private ArrayList reasonAndConclusionParagraph;
	private ArrayList appealRightsParagraph;
	private ArrayList lawParagraph;
	
	//added this to show the header values such as Claimant Name/SSN Employer Name/EAN.
	private String headerClaimantName;
	private String headerClaimantSSN;
	private String headerEmployerName;
	private String headerEAN;
	private String lawDetails;
	/*CIF_01010 Starts || Modified editingRequired. */
	private String returnDecision;
	private String editingRequired;
	private String noonPrinting;
	private String inFavorAppellant;
	/*CIF_01010 Ends */
	/*CIF_01563 Starts,Modified code for Reassignment Process Proof Reading.*/
	private String reassignedProcessReturnDecision;
	private String reassignedProcessEditingRequired;
	private boolean isUploadingAllowed;
		
	/*Added code for Proof Reading */
	
	/*CIF_00878 Starts,Added below String arrays and getter and setter for proceduralIssue,adnParticipants,historyAndReset,lawForDecision,decisionStatus,regularDecision */
	private String[] proceduralIssue;
	private String[] adnParticipants;
	private String[] historyAndReset;
	private String[] lawForDecision;
	private String[] decisionStatus;
	private String[] regularDecision;
	private String[] proceduralLaw;
	
	private ArrayList appealNoticeParagraph;
	private ArrayList procIssueList;
	private ArrayList adnParticipantsList;
	private ArrayList historyResetContinuedList;
	private ArrayList regularDecisionList;
	private ArrayList lawForDecisionList;
	private ArrayList decisionStatusList;
	private ArrayList proceduralLawList;
	
	/*CIF_00878 Starts,Added below String arrays */
	//CIF_02196 || Defect_3254 Start
	private String claimType;
	private String programType;
	private String appealId;
	private String appealType;
	private String miscAppealType;
	private FileItem fileName ;
	//CIF_02196 || Defect_3254 End
	public ProcessProofReadingForm() {
		super();
		this.appealRightsParagraph= new ArrayList();
		this.appealNoticeParagraph= new ArrayList();
		this.procIssueList=new ArrayList();
		this.caseHistoryParagraph= new ArrayList();
		this.decisionReasonParagraph= new ArrayList();
		this.factFindingParagraph= new ArrayList();
		this.issueParagraph= new ArrayList();
		this.lawParagraph=new ArrayList();
		this.reasonAndConclusionParagraph=new ArrayList();
		this.adnParticipantsList=new ArrayList();
		this.historyResetContinuedList=new ArrayList();
		this.regularDecisionList=new ArrayList();
		this.proceduralLawList=new ArrayList();
		this.lawForDecisionList=new ArrayList();
		this.decisionStatusList=new ArrayList();
		// TODO Auto-generated constructor stub
	}
	public void clear() {
		this.fileName = null;

	}
	/**
	 * @return the proceduralIssue
	 */
	public String[] getProceduralIssue() {
		return null != proceduralIssue ? proceduralIssue.clone() : null;
	}

	/**
	 * @param proceduralIssue the proceduralIssue to set
	 */
	public void setProceduralIssue(String[] proceduralIssue) {
		this.proceduralIssue = null != proceduralIssue ? proceduralIssue.clone(): null;
	}

	/**
	 * @return the adnParticipants
	 */
	public String[] getAdnParticipants() {
		return null != adnParticipants ? adnParticipants.clone() : null;
	}

	/**
	 * @param adnParticipants the adnParticipants to set
	 */
	public void setAdnParticipants(String[] adnParticipants) {
		this.adnParticipants = null != adnParticipants ? adnParticipants.clone(): null;
	}

	/**
	 * @return the historyAndReset
	 */
	public String[] getHistoryAndReset() {
		return null != historyAndReset ? historyAndReset.clone() : null;
	}

	/**
	 * @param historyAndReset the historyAndReset to set
	 */
	public void setHistoryAndReset(String[] historyAndReset) {
		this.historyAndReset = null != historyAndReset ? historyAndReset.clone(): null;
		
	}

	/**
	 * @return the lawForDecision
	 */
	public String[] getLawForDecision() {
		return null != lawForDecision ? lawForDecision.clone() : null;
	}

	/**
	 * @param lawForDecision the lawForDecision to set
	 */
	public void setLawForDecision(String[] lawForDecision) {
		this.lawForDecision = null != lawForDecision ? lawForDecision.clone(): null;
	}

	/**
	 * @return the decisionStatus
	 */
	public String[] getDecisionStatus() {
		return null != decisionStatus ? decisionStatus.clone() : null;
	}

	/**
	 * @param decisionStatus the decisionStatus to set
	 */
	public void setDecisionStatus(String[] decisionStatus) {
		this.decisionStatus = null != decisionStatus ? decisionStatus.clone(): null;
	}

	/**
	 * @return the regularDecision
	 */
	public String[] getRegularDecision() {
		return null != regularDecision ? regularDecision.clone() : null;
	}

	/**
	 * @param regularDecision the regularDecision to set
	 */
	public void setRegularDecision(String[] regularDecision) {
		this.regularDecision = null != regularDecision ? regularDecision.clone(): null;
	}

	
	
	public String[] getProceduralLaw() {
		return null != proceduralLaw ? proceduralLaw.clone() : null;
	}

	public void setProceduralLaw(String[] proceduralLaw) {
		this.proceduralLaw = null != proceduralLaw ? proceduralLaw.clone(): null;
		
	}

	/**
	 * @return the appealNoticeParagraph
	 */
	public ArrayList getAppealNoticeParagraph() {
		return appealNoticeParagraph;
	}

	/**
	 * @param appealNoticeParagraph the appealNoticeParagraph to set
	 */
	public void setAppealNoticeParagraph(ArrayList appealNoticeParagraph) {
		this.appealNoticeParagraph = appealNoticeParagraph;
	}

	/**
	 * @return the procIssueList
	 */
	public ArrayList getProcIssueList() {
		return procIssueList;
	}

	/**
	 * @param procIssueList the procIssueList to set
	 */
	public void setProcIssueList(ArrayList procIssueList) {
		this.procIssueList = procIssueList;
	}

	/**
	 * @return the adnParticipantsList
	 */
	public ArrayList getAdnParticipantsList() {
		return adnParticipantsList;
	}

	/**
	 * @param adnParticipantsList the adnParticipantsList to set
	 */
	public void setAdnParticipantsList(ArrayList adnParticipantsList) {
		this.adnParticipantsList = adnParticipantsList;
	}

	/**
	 * @return the historyResetContinuedList
	 */
	public ArrayList getHistoryResetContinuedList() {
		return historyResetContinuedList;
	}

	/**
	 * @param historyResetContinuedList the historyResetContinuedList to set
	 */
	public void setHistoryResetContinuedList(ArrayList historyResetContinuedList) {
		this.historyResetContinuedList = historyResetContinuedList;
	}

	/**
	 * @return the regularDecisionList
	 */
	public ArrayList getRegularDecisionList() {
		return regularDecisionList;
	}

	/**
	 * @param regularDecisionList the regularDecisionList to set
	 */
	public void setRegularDecisionList(ArrayList regularDecisionList) {
		this.regularDecisionList = regularDecisionList;
	}

	/**
	 * @return the lawForDecisionList
	 */
	public ArrayList getLawForDecisionList() {
		return lawForDecisionList;
	}

	/**
	 * @param lawForDecisionList the lawForDecisionList to set
	 */
	public void setLawForDecisionList(ArrayList lawForDecisionList) {
		this.lawForDecisionList = lawForDecisionList;
	}

	/**
	 * @return the decisionStatusList
	 */
	public ArrayList getDecisionStatusList() {
		return decisionStatusList;
	}

	/**
	 * @param decisionStatusList the decisionStatusList to set
	 */
	public void setDecisionStatusList(ArrayList decisionStatusList) {
		this.decisionStatusList = decisionStatusList;
	}

	public ArrayList getProceduralLawList() {
		return proceduralLawList;
	}

	public void setProceduralLawList(ArrayList proceduralLawList) {
		this.proceduralLawList = proceduralLawList;
	}

	public String getAljDecision() {
		return aljDecision;
	}

	public void setAljDecision(String aljDecision) {
		this.aljDecision = aljDecision;
	}

	public ArrayList getAppealRightsParagraph() {
		return appealRightsParagraph;
	}

	public void setAppealRightsParagraph(ArrayList appealRightsParagraph) {
		this.appealRightsParagraph = appealRightsParagraph;
	}

	public ArrayList getCaseHistoryParagraph() {
		return caseHistoryParagraph;
	}

	public void setCaseHistoryParagraph(ArrayList caseHistoryParagraph) {
		this.caseHistoryParagraph = caseHistoryParagraph;
	}

	public ArrayList getDecisionReasonParagraph() {
		return decisionReasonParagraph;
	}

	public void setDecisionReasonParagraph(ArrayList decisionReasonParagraph) {
		this.decisionReasonParagraph = decisionReasonParagraph;
	}

	public String getDocketNumber() {
		return docketNumber;
	}

	public void setDocketNumber(String docketNumber) {
		this.docketNumber = docketNumber;
	}

	public ArrayList getFactFindingParagraph() {
		return factFindingParagraph;
	}

	public void setFactFindingParagraph(ArrayList factFindingParagraph) {
		this.factFindingParagraph = factFindingParagraph;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public String getIssueDetails() {
		return issueDetails;
	}

	public void setIssueDetails(String issueDetails) {
		this.issueDetails = issueDetails;
	}

	/**
	 * @return the lawDetails
	 */
	public String getLawDetails() {
		return lawDetails;
	}

	/**
	 * @param lawDetails the lawDetails to set
	 */
	public void setLawDetails(final String lawDetails) {
		this.lawDetails = lawDetails;
	}
	
	/**
	 * @return the returnDecision
	 */
	public String getReturnDecision() {
		return returnDecision;
	}

	/**
	 * @param returnDecision the returnDecision to set
	 */
	public void setReturnDecision(String returnDecision) {
		this.returnDecision = returnDecision;
	}

	

	/**
	 * @return the editingRequired
	 */
	public String getEditingRequired() {
		return editingRequired;
	}

	/**
	 * @param editingRequired the editingRequired to set
	 */
	public void setEditingRequired(String editingRequired) {
		this.editingRequired = editingRequired;
	}

	/**
	 * @return the noonPrinting
	 */
	public String getNoonPrinting() {
		return noonPrinting;
	}

	/**
	 * @param noonPrinting the noonPrinting to set
	 */
	public void setNoonPrinting(String noonPrinting) {
		this.noonPrinting = noonPrinting;
	}

	public ArrayList getIssueParagraph() {
		return issueParagraph;
	}

	public void setIssueParagraph(ArrayList issueParagraph) {
		this.issueParagraph = issueParagraph;
	}

	public String getPreviousDecision() {
		return previousDecision;
	}

	public void setPreviousDecision(String previousDecision) {
		this.previousDecision = previousDecision;
	}

	public ArrayList getReasonAndConclusionParagraph() {
		return reasonAndConclusionParagraph;
	}

	public void setReasonAndConclusionParagraph(
			ArrayList reasonAndConclusionParagraph) {
		this.reasonAndConclusionParagraph = reasonAndConclusionParagraph;
	}

	public ArrayList getLawParagraph() {
		return lawParagraph;
	}

	public void setLawParagraph(ArrayList lawParagraph) {
		this.lawParagraph = lawParagraph;
	}

	public String getVerifyDecisionEndDate() {
		return verifyDecisionEndDate;
	}

	public void setVerifyDecisionEndDate(String verifyDecisionEndDate) {
		this.verifyDecisionEndDate = verifyDecisionEndDate;
	}

	public String getVerifyDecisionStartDate() {
		return verifyDecisionStartDate;
	}

	public void setVerifyDecisionStartDate(String verifyDecisionStartDate) {
		this.verifyDecisionStartDate = verifyDecisionStartDate;
	}
	
	public MessageHolderBean getAppealsRightBean(int index) {
		while(index >=appealRightsParagraph.size()){
			appealRightsParagraph.add(new MessageHolderBean());
		}
		return (MessageHolderBean)appealRightsParagraph.get(index);
	}
	public void setAppealsRightBean(int index, MessageHolderBean bean) {
		while(index >= appealRightsParagraph.size()){
			appealRightsParagraph.add(new MessageHolderBean());
		}
		appealRightsParagraph.set(index, bean);
	}
	
	public MessageHolderBean getDecisionReasonBean(int index) {
		while(index >=decisionReasonParagraph.size()){
			decisionReasonParagraph.add(new MessageHolderBean());
		}
		return (MessageHolderBean)decisionReasonParagraph.get(index);
	}
	public void setDecisionReasonBean(int index, MessageHolderBean bean) {
		while(index >= decisionReasonParagraph.size()){
			decisionReasonParagraph.add(new MessageHolderBean());
		}
		decisionReasonParagraph.set(index, bean);
	}
	
	public MessageHolderBean getCaseHistoryBean(int index) {
		while(index >=caseHistoryParagraph.size()){
			caseHistoryParagraph.add(new MessageHolderBean());
		}
		return (MessageHolderBean)caseHistoryParagraph.get(index);
	}
	public void setCaseHistoryBean(int index, MessageHolderBean bean) {
		while(index >= caseHistoryParagraph.size()){
			caseHistoryParagraph.add(new MessageHolderBean());
		}
		caseHistoryParagraph.set(index, bean);
	}
	
	public MessageHolderBean getFactFindingBean(int index) {
		while(index >=factFindingParagraph.size()){
			factFindingParagraph.add(new MessageHolderBean());
		}
		return (MessageHolderBean)factFindingParagraph.get(index);
	}
	public void setFactFindingBean(int index, MessageHolderBean bean) {
		while(index >= factFindingParagraph.size()){
			factFindingParagraph.add(new MessageHolderBean());
		}
		factFindingParagraph.set(index, bean);
	}
	
	public MessageHolderBean getIssueBean(int index) {
        // CIF_03147 || Defect_5279
        if (this.issueParagraph == null)
        {
            this.issueParagraph = new ArrayList();
        }
		while(index >=issueParagraph.size()){
			issueParagraph.add(new MessageHolderBean());
		}
		return (MessageHolderBean)issueParagraph.get(index);
	}
	public void setIssueBean(int index, MessageHolderBean bean) {
		while(index >= issueParagraph.size()){
			issueParagraph.add(new MessageHolderBean());
		}
		issueParagraph.set(index, bean);
	}

	public MessageHolderBean getReasonAndConclusionBean(int index) {
        // CIF_03147 || Defect_5279
        if (this.reasonAndConclusionParagraph == null)
        {
            this.reasonAndConclusionParagraph = new ArrayList();
        }
        while (index >= this.reasonAndConclusionParagraph.size())
        {
            this.reasonAndConclusionParagraph.add(new MessageHolderBean());
        }
        return (MessageHolderBean) this.reasonAndConclusionParagraph.get(index);
/*        
		while(index >=reasonAndConclusionParagraph.size()){
			reasonAndConclusionParagraph.add(new MessageHolderBean());
		}
		return (MessageHolderBean)reasonAndConclusionParagraph.get(index);*/
	}
	public void setReasonAndConclusionBean(int index, MessageHolderBean bean) {
		while(index >= reasonAndConclusionParagraph.size()){
			reasonAndConclusionParagraph.add(new MessageHolderBean());
		}
		reasonAndConclusionParagraph.set(index, bean);
	}
	
	public MessageHolderBean getLawBean(int index) {
        if (this.lawParagraph == null)
        {
            this.lawParagraph = new ArrayList();
        }
        while (index >= this.lawParagraph.size())
        {
            this.lawParagraph.add(new MessageHolderBean());
        }
        return (MessageHolderBean) this.lawParagraph.get(index);

	}
	public void setLawBean(int index, MessageHolderBean bean) {
		while(index >= lawParagraph.size()){
			lawParagraph.add(new MessageHolderBean());
		}
		lawParagraph.set(index, bean);
	}
	public FileItem getFileName() {
		return fileName;
	}
	public void setFileName(FileItem fileName) {
		this.fileName = fileName;
	}
	public void setFileName(Object fileName){
		if(fileName!=null){
			List<FileItem>fileItems=(List<FileItem>)fileName;
			if(fileItems!=null && !fileItems.isEmpty()){
				this.fileName=fileItems.get(0);
			}
		}
	}

@Override
public ActionErrors validate(final ActionMapping mapping, final HttpServletRequest request) {
		
		final ActionErrors errors = super.validate(mapping, request);
	if(super.isSameSubmitMethod(request,mapping,"access.submit")
			|| super.isSameSubmitMethod(request,mapping,"access.viewdecisionletter")){
		int caseHistoryLength=0;
		int issueLength=0;
		int factFindingLength=0;
		int lawLength=0;
		int reasoningLength=0;
		int decisionLength=0;
		int appealRightsLength=0;
		
		if(this.getIssueParagraph()!=null && !this.getIssueParagraph().isEmpty()){
			final Iterator t= this.getIssueParagraph().iterator();
			int i=1;
			while(t.hasNext()){
				final MessageHolderBean bean= (MessageHolderBean)t.next();
				if(bean!=null && bean.getMessage().equals(""))
				{
					if(i==1)
					{
						errors.add("issue", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.deputydetermination.paragraph",
								Resources.getMessage(request, "access.app.verifydecision.issue")));
					}
					/*else
					{
						errors.add("issue", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.deputydetermination.paragraph",
								Resources.getMessage(request, "access.app.verifydecision.issue")));
					}*/
					
				}
				if(bean!=null
						&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
					errors.add("issue"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
							Resources.getMessage(request, "access.app.verifydecision.issue"), String.valueOf(i)));
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.DATE_APPEAL_PARAM)
						||	StringUtility.contains(bean.getMessage(), AppealConstants.DECISION_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.AMOUNT_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.NUMBER_APPEAL_PARAM)))
				{
					errors.add("issue"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entervalue",
							Resources.getMessage(request, "access.app.verifydecision.issue"), String.valueOf(i)));
					
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.NEW_APPEAL_DECISION_PARAM)))
				{
					errors.add("issue"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entercurrdecision",
							Resources.getMessage(request, "access.app.verifydecision.issue"), String.valueOf(i)));
					
				}
				if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
					issueLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
				}
					if (bean != null) {
						issueLength += bean.getMessage().length();
					}
				i++;
				
			}
			if(issueLength > GlobalConstants.NUMERIC_THOUSAND){
				errors.add("issue", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
						Resources.getMessage(request, "access.app.verifydecision.issue")));
			}
		}
		
		if(this.getCaseHistoryParagraph()!=null && !this.getCaseHistoryParagraph().isEmpty()){
			final Iterator t= this.getCaseHistoryParagraph().iterator();
			int i=1;
			while(t.hasNext()){
				final MessageHolderBean bean= (MessageHolderBean)t.next();
				if(bean!=null && bean.getMessage().equals(""))
				{
					if(i==1)
					{
						errors.add("caseHistory", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.casehistory.paragraph1",
								Resources.getMessage(request, "access.app.verifydecision.caseHistory")));
					}
					/*else
					{
						errors.add("caseHistory", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.casehistory.paragraph2",
								Resources.getMessage(request, "access.app.verifydecision.caseHistory")));
					}*/
					
				}
				if(bean!=null
						&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
					errors.add("caseHistory"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
							Resources.getMessage(request, "access.app.verifydecision.casehistory"), String.valueOf(i)));
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.DATE_APPEAL_PARAM)
						||	StringUtility.contains(bean.getMessage(), AppealConstants.DECISION_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.AMOUNT_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.NUMBER_APPEAL_PARAM)))
				{
					errors.add("caseHistory"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entervalue",
							Resources.getMessage(request, "access.app.verifydecision.casehistory"), String.valueOf(i)));
					
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.NEW_APPEAL_DECISION_PARAM)))
				{
					errors.add("caseHistory"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entercurrdecision",
							Resources.getMessage(request, "access.app.verifydecision.casehistory"), String.valueOf(i)));
					
				}
				if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
					caseHistoryLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
				}
				if (bean != null) {
					caseHistoryLength += bean.getMessage().length();
				}
				i++;
				
			}
			if(caseHistoryLength > GlobalConstants.NUMERIC_TWO_THOUSAND_FIVE_HUNDRED){
				errors.add("caseHistory", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
						Resources.getMessage(request, "access.app.verifydecision.casehistory")));
			}
		}
	
		if(this.getFactFindingParagraph()!=null && !this.getFactFindingParagraph().isEmpty()){
			final Iterator t= this.getFactFindingParagraph().iterator();
			int i=1;
			while(t.hasNext()){
				final MessageHolderBean bean= (MessageHolderBean)t.next();
				if(bean!=null && bean.getMessage().equals(""))
				{
					if(i==1)
					{
						errors.add("factFinding", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.factfinding.paragraph1",
								Resources.getMessage(request, "access.app.verifydecision.factfinding")));
					}
					/*else
					{
						errors.add("factFinding", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.factfinding.paragraph2",
								Resources.getMessage(request, "access.app.verifydecision.factfinding")));
					}*/
					
				}
				if(bean!=null
						&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
					errors.add("factFinding"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
							Resources.getMessage(request, "access.app.verifydecision.factfinding"), String.valueOf(i)));
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.DATE_APPEAL_PARAM)
						||	StringUtility.contains(bean.getMessage(), AppealConstants.DECISION_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.AMOUNT_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.NUMBER_APPEAL_PARAM)))
				{
					errors.add("factFinding"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entervalue",
							Resources.getMessage(request, "access.app.verifydecision.factfinding"), String.valueOf(i)));
					
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.NEW_APPEAL_DECISION_PARAM)))
				{
					errors.add("factFinding"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entercurrdecision",
							Resources.getMessage(request, "access.app.verifydecision.factfinding"), String.valueOf(i)));
					
				}
				if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
					factFindingLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
				}
				if (bean != null) {
					factFindingLength += bean.getMessage().length();
				}
				i++;
				
			}
			if(factFindingLength > (GlobalConstants.NUMERIC_THOUSAND*GlobalConstants.NUMERIC_TEN)){
				errors.add("factFinding", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
						Resources.getMessage(request, "access.app.verifydecision.factfinding")));
			}
		}
		if(this.getLawParagraph()!=null && !this.getLawParagraph().isEmpty()){
			final Iterator t= this.getLawParagraph().iterator();
			int i=1;
			while(t.hasNext()){
				final MessageHolderBean bean= (MessageHolderBean)t.next();
				if(bean!=null && bean.getMessage().equals(""))
				{
					if(i==1)
					{
						errors.add("law", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.law.paragraph1",
								Resources.getMessage(request, "access.app.verifydecision.law")));
					}
					/*else
					{
						errors.add("reasoning", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.reasoning.paragraph2",
								Resources.getMessage(request, "access.app.verifydecision.reasoning")));
					}*/
					
				}
				if(bean!=null
						&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
					errors.add("law"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
							Resources.getMessage(request, "access.app.verifydecision.law"), String.valueOf(i)));
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.DATE_APPEAL_PARAM)
						||	StringUtility.contains(bean.getMessage(), AppealConstants.DECISION_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.AMOUNT_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.NUMBER_APPEAL_PARAM)))
				{
					errors.add("law"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entervalue",
							Resources.getMessage(request, "access.app.verifydecision.law"), String.valueOf(i)));
					
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.NEW_APPEAL_DECISION_PARAM)))
				{
					errors.add("law"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entercurrdecision",
							Resources.getMessage(request, "access.app.verifydecision.law"), String.valueOf(i)));
					
				}
				if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
					lawLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
				}
				if (bean != null) {
					lawLength += bean.getMessage().length();
				}
				i++;
				
			}
			if(lawLength > (GlobalConstants.NUMERIC_THOUSAND*GlobalConstants.NUMERIC_SIX)){
				errors.add("law", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
						Resources.getMessage(request, "access.app.verifydecision.law")));
			}
		}
		if(this.getReasonAndConclusionParagraph()!=null && !this.getReasonAndConclusionParagraph().isEmpty()){
			final Iterator t= this.getReasonAndConclusionParagraph().iterator();
			int i=1;
			while(t.hasNext()){
				final MessageHolderBean bean= (MessageHolderBean)t.next();
				if(bean!=null && bean.getMessage().equals(""))
				{
					if(i==1)
					{
						errors.add("reasoning", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.reasoning.paragraph1",
								Resources.getMessage(request, "access.app.verifydecision.reasoning")));
					}
					/*else
					{
						errors.add("reasoning", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.reasoning.paragraph2",
								Resources.getMessage(request, "access.app.verifydecision.reasoning")));
					}*/
					
				}
				if(bean!=null
						&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
					errors.add("reasoning"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
							Resources.getMessage(request, "access.app.verifydecision.reasoning"), String.valueOf(i)));
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.DATE_APPEAL_PARAM)
						||	StringUtility.contains(bean.getMessage(), AppealConstants.DECISION_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.AMOUNT_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.NUMBER_APPEAL_PARAM)))
				{
					errors.add("reasoning"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entervalue",
							Resources.getMessage(request, "access.app.verifydecision.reasoning"), String.valueOf(i)));
					
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.NEW_APPEAL_DECISION_PARAM)))
				{
					errors.add("reasoning"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entercurrdecision",
							Resources.getMessage(request, "access.app.verifydecision.reasoning"), String.valueOf(i)));
					
				}
				if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
					reasoningLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
				}
				if (bean != null) {
					reasoningLength += bean.getMessage().length();
				}
				i++;
				
			}
			if(reasoningLength > (GlobalConstants.NUMERIC_THOUSAND*GlobalConstants.NUMERIC_SIX)){
				errors.add("reasoning", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
						Resources.getMessage(request, "access.app.verifydecision.reasoning")));
			}
		}
		if(this.getDecisionReasonParagraph()!=null && !this.getDecisionReasonParagraph().isEmpty()){
			final Iterator t= this.getDecisionReasonParagraph().iterator();
			
			int i=1;
			while(t.hasNext()){
				final MessageHolderBean bean= (MessageHolderBean)t.next();
				if(bean!=null && bean.getMessage().equals(""))
				{
					if(i==1)
					{
						errors.add("decision", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.decision.paragraph1",
								Resources.getMessage(request, "access.app.verifydecision.decision")));
					}
					/*else
					{
						errors.add("decision", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.decision.paragraph2",
								Resources.getMessage(request, "access.app.verifydecision.decision")));
					}*/
					
				}
				if(bean!=null
						&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
					errors.add("decision"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
							Resources.getMessage(request, "access.app.verifydecision.decision"), String.valueOf(i)));
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.DATE_APPEAL_PARAM)
						||	StringUtility.contains(bean.getMessage(), AppealConstants.DECISION_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.AMOUNT_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.NUMBER_APPEAL_PARAM)))
				{
					errors.add("decision"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entervalue",
							Resources.getMessage(request, "access.app.verifydecision.decision"), String.valueOf(i)));
					
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.NEW_APPEAL_DECISION_PARAM)))
				{
					errors.add("decision"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entercurrdecision",
							Resources.getMessage(request, "access.app.verifydecision.decision"), String.valueOf(i)));
					
				}
				if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
					decisionLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
				}
				if (bean != null) {
					decisionLength += bean.getMessage().length();
				}
				i++;
				
			}
			if(decisionLength > (GlobalConstants.NUMERIC_THOUSAND*GlobalConstants.NUMERIC_FIVE)){
				errors.add("decision", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
						Resources.getMessage(request, "access.app.verifydecision.decision")));
			}
		}
		if(this.getAppealRightsParagraph()!=null && !this.getAppealRightsParagraph().isEmpty()){
			final Iterator t= this.getAppealRightsParagraph().iterator();
			int i=1;
			while(t.hasNext()){
				final MessageHolderBean bean= (MessageHolderBean)t.next();
				if(bean!=null
						&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
					errors.add("appealRights"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
							Resources.getMessage(request, "access.app.verifydecision.appealrights"), String.valueOf(i)));
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.DATE_APPEAL_PARAM)
						||	StringUtility.contains(bean.getMessage(), AppealConstants.DECISION_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.AMOUNT_APPEAL_PARAM)
						||  StringUtility.contains(bean.getMessage(), AppealConstants.NUMBER_APPEAL_PARAM)))
				{
					errors.add("appealRights"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entervalue",
							Resources.getMessage(request, "access.app.verifydecision.deputydetermination"), String.valueOf(i)));
					
				}
				if(bean!=null
						&& (StringUtility.contains(bean.getMessage(), AppealConstants.NEW_APPEAL_DECISION_PARAM)))
				{
					errors.add("appealRights"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.entercurrdecision",
							Resources.getMessage(request, "access.app.verifydecision.deputydetermination"), String.valueOf(i)));
					
				}
				if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
					appealRightsLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
				}
				if (bean != null) {
					appealRightsLength += bean.getMessage().length();
				}
				i++;
				
			}
			if(appealRightsLength > (GlobalConstants.NUMERIC_THOUSAND*GlobalConstants.NUMERIC_FIVE)){
				errors.add("appealRights", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
						Resources.getMessage(request, "access.app.verifydecision.appealrights")));
			}
		}
		
		if(ViewConstants.YES.equals(this.getReturnDecision()) && ViewConstants.YES.equals(this.getEditingRequired())){
			
				errors.add("returnDecision", new ActionMessage("error.required",
						Resources.getMessage(request, "error.access.app.proofreading.returnDecision.yes.value")));
			
		}
		if(ViewConstants.YES.equals(this.getReturnDecision()) || ViewConstants.YES.equals(this.getEditingRequired())){
		
			this.setUploadingAllowed(Boolean.FALSE);
		
	    }
		if(Boolean.FALSE.equals(this.isUploadingAllowed) && (null != this.fileName && !this.fileName.getFileName().equals("")))
		{
			errors.add("fileName",new ActionMessage("error.access.app.upload.Document.into.DMS.not.uploadfile"));
		}else if (Boolean.TRUE.equals(this.isUploadingAllowed)){
			int fileSize = this.fileName.getFileSize();
			 
			if((this.fileName == null) || (this.fileName.getFileName().equals(""))){
				
			   errors.add("fileName",new ActionMessage("error.access.app.upload.Document.into.DMS.uploadfile"));
			   
			}else if(!StringUtility.isValuePresent(GlobalConstants.UPLOAD_DOCUMENTS_LIRC_DECISION,StringUtility.getFileExtension(this.fileName.getFileName())))
			{
				errors.add("fileName",new ActionMessage("error.access.upload.Document.into.appeals.incorrect.extension"));
				
			}else if(fileSize == 0 || fileSize > GlobalConstants.UPLOAD_DOCUMENTS_DMS_LIMIT)
			  {
				 errors.add("fileName",new ActionMessage("error.access.upload.Document.into.DMS.incorrect.file.size"));
			  }
		}
		/*if(StringUtility.isBlank(this.inFavorAppellant))
		{
			errors.add("inFavorAppellant", new ActionMessage("error.required",
					Resources.getMessage(request, "error.access.app.processproofreading.proofreading.infavorappellant")));
		}*/
		
	}
	
	
	return errors;
}

public String getHeaderClaimantName() {
	return headerClaimantName;
}

public void setHeaderClaimantName(String headerClaimantName) {
	this.headerClaimantName = headerClaimantName;
}

public String getHeaderClaimantSSN() {
	return headerClaimantSSN;
}

public void setHeaderClaimantSSN(String headerClaimantSSN) {
	this.headerClaimantSSN = headerClaimantSSN;
}

public String getHeaderEAN() {
	return headerEAN;
}

public void setHeaderEAN(String headerEAN) {
	this.headerEAN = headerEAN;
}

public String getHeaderEmployerName() {
	return headerEmployerName;
}

public void setHeaderEmployerName(String headerEmployerName) {
	this.headerEmployerName = headerEmployerName;
}

/**
 * @return the reassignedProcessReturnDecision
 */
public String getReassignedProcessReturnDecision() {
	return reassignedProcessReturnDecision;
}

/**
 * @param reassignedProcessReturnDecision the reassignedProcessReturnDecision to set
 */
public void setReassignedProcessReturnDecision(
		String reassignedProcessReturnDecision) {
	this.reassignedProcessReturnDecision = reassignedProcessReturnDecision;
}

/**
 * @return the reassignedProcessEditingRequired
 */
public String getReassignedProcessEditingRequired() {
	return reassignedProcessEditingRequired;
}

/**
 * @param reassignedProcessEditingRequired the reassignedProcessEditingRequired to set
 */
public void setReassignedProcessEditingRequired(
		String reassignedProcessEditingRequired) {
	this.reassignedProcessEditingRequired = reassignedProcessEditingRequired;
}

/**
 * @return the claimType
 */
public String getClaimType() {
	return claimType;
}

/**
 * @param claimType the claimType to set
 */
public void setClaimType(String claimType) {
	this.claimType = claimType;
}

/**
 * @return the programType
 */
public String getProgramType() {
	return programType;
}

/**
 * @param programType the programType to set
 */
public void setProgramType(String programType) {
	this.programType = programType;
}

/**
 * @return the appealId
 */
public String getAppealId() {
	return appealId;
}

/**
 * @param appealId the appealId to set
 */
public void setAppealId(String appealId) {
	this.appealId = appealId;
}

/**
 * @return the appealType
 */
public String getAppealType() {
	return appealType;
}

/**
 * @param appealType the appealType to set
 */
public void setAppealType(String appealType) {
	this.appealType = appealType;
}

/**
 * @return the miscAppealType
 */
public String getMiscAppealType() {
	return miscAppealType;
}

/**
 * @param miscAppealType the miscAppealType to set
 */
public void setMiscAppealType(String miscAppealType) {
	this.miscAppealType = miscAppealType;
}
public String getInFavorAppellant() {
	return inFavorAppellant;
}
public void setInFavorAppellant(String inFavorAppellant) {
	this.inFavorAppellant = inFavorAppellant;
}
public boolean isUploadingAllowed() {
	return isUploadingAllowed;
}
public void setUploadingAllowed(boolean isUploadingAllowed) {
	this.isUploadingAllowed = isUploadingAllowed;
}
}