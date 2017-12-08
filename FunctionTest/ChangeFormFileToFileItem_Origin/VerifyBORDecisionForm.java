package gov.state.uim.app.struts;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.domain.bean.AppealInfoBean;
import gov.state.uim.domain.bean.CalendarBean;
import gov.state.uim.domain.bean.MessageHolderBean;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.StringUtility;

/**
 * @struts.form
 * 		name = "verifybordecisionform"
 */
public class VerifyBORDecisionForm extends BaseValidatorForm {
	private static final Logger LOGGER = Logger.getLogger(VerifyBORDecisionForm.class);


	/**
	 * 
	 */
	private FormFile fileName ;
	private CalendarBean decisionMailDate;
	
	private static final long serialVersionUID = 4522579915623075466L;
	private String proofReadingFlag;
	private ArrayList caseHistoryParagraph;
	private ArrayList issueParagraph;
	private ArrayList factFindingParagraph;
	private ArrayList decisionReasonParagraph;
	private ArrayList decisionParagraph;
	private ArrayList reasonAndConclusionParagraph;
	private ArrayList appealRightsParagraph;
	private AppealInfoBean appInfoBeanDisp;
	private String inFavorAppellant;
	
	
	public VerifyBORDecisionForm() {
		super();
		this.decisionMailDate = new CalendarBean();
		this.caseHistoryParagraph = new ArrayList();
		this.issueParagraph = new ArrayList();
		this.factFindingParagraph = new ArrayList();
		this.decisionReasonParagraph = new ArrayList();
		this.decisionParagraph = new ArrayList();
		this.reasonAndConclusionParagraph = new ArrayList();
		this.appealRightsParagraph = new ArrayList();
	}
	public void clear() {
		this.fileName = null;

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

	public ArrayList getFactFindingParagraph() {
		return factFindingParagraph;
	}

	public void setFactFindingParagraph(ArrayList factFindingParagraph) {
		this.factFindingParagraph = factFindingParagraph;
	}

	public ArrayList getIssueParagraph() {
		return issueParagraph;
	}

	public void setIssueParagraph(ArrayList issueParagraph) {
		this.issueParagraph = issueParagraph;
	}

	public String getProofReadingFlag() {
		return proofReadingFlag;
	}

	public void setProofReadingFlag(String proofReadingFlag) {
		this.proofReadingFlag = proofReadingFlag;
	}

	public ArrayList getReasonAndConclusionParagraph() {
		return reasonAndConclusionParagraph;
	}

	public void setReasonAndConclusionParagraph(
			ArrayList reasonAndConclusionParagraph) {
		this.reasonAndConclusionParagraph = reasonAndConclusionParagraph;
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
	
	public MessageHolderBean getDecisionBean(int index) {
		while(index >=decisionParagraph.size()){
			decisionParagraph.add(new MessageHolderBean());
		}
		return (MessageHolderBean)decisionParagraph.get(index);
	}
	public void setDecisionBean(int index, MessageHolderBean bean) {
		while(index >= decisionParagraph.size()){
			decisionParagraph.add(new MessageHolderBean());
		}
		decisionParagraph.set(index, bean);
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
		while(index >=reasonAndConclusionParagraph.size()){
			reasonAndConclusionParagraph.add(new MessageHolderBean());
		}
		return (MessageHolderBean)reasonAndConclusionParagraph.get(index);
	}
	public void setReasonAndConclusionBean(int index, MessageHolderBean bean) {
		while(index >= reasonAndConclusionParagraph.size()){
			reasonAndConclusionParagraph.add(new MessageHolderBean());
		}
		reasonAndConclusionParagraph.set(index, bean);
	}

	public ArrayList getDecisionParagraph() {
		return decisionParagraph;
	}

	public void setDecisionParagraph(ArrayList decisionParagraph) {
		this.decisionParagraph = decisionParagraph;
	}
	
	public FormFile getFileName() {
		return fileName;
	}
	/**   
	 * @struts.validator 
	 * 		type = "required"
	 * 		msgkey="error.required"
	 * 		arg0resource = "access.cin.uploadfilemasslayoff.uploadfile.number"
	 */	
	public void setFileName(FormFile fileName) {
		this.fileName = fileName;
	}
	
	
	
	
	
	public CalendarBean getDecisionMailDate() {
		return decisionMailDate;
	}
	
	
	/**
	 * @struts.validator
	 * 		type = "requiredCalDate"
	 * 		override = "true"
	 * 		msgkey = "error.required"
	 * 		arg0resource = "access.app.verifybordecision.decisionmaildate.error"
	 * 
	 * @struts.validator
	 * 		type = "calDate"
	 * 		override = "true"
	 * 		msgkey="error.access.format.invalid"
	 * 		arg0resource = "access.app.verifybordecision.decisionmaildate.error"
	 * 		arg1resource = "access.date.format"
	 * @struts.validator-var
	 *   	value = "MM/dd/yyyy"
	 *   	name ="datePattern"
	 *
	 */
	public void setDecisionMailDate(CalendarBean decisionMailDate) {
		this.decisionMailDate = decisionMailDate;
	}
	/*public void setFileName(Object fileName){
		this.fileName = (FormFile) fileName;
	}*/
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if(super.isSameSubmitMethod(request,mapping,"access.submit")){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

		/*Commneted as to upload the decision letter. */
			/*	int caseHistoryLength=0;
			int issueLength=0;
			int factFindingLength=0;
			int reasoningLength=0;
			int decisionLength=0;
			int appealRightsLength=0;
//			check for empty paragraph defect #8943
			if (this.getDecisionParagraph()!=null){
				Iterator t= this.getDecisionParagraph().iterator();
				while(t.hasNext()){
					MessageHolderBean bean= (MessageHolderBean)t.next();
					if (bean!=null){
						String message  = bean.getMessage();
						if (StringUtility.isBlank(message)){
							errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.access.app.verifydecision.mandantory"));
						}
					}
				}
			}
				//Check for Case History
			if(this.getCaseHistoryParagraph()!=null && !this.getCaseHistoryParagraph().isEmpty()){
				Iterator t= this.getCaseHistoryParagraph().iterator();
				int i=1;
				while(t.hasNext()){
					MessageHolderBean bean= (MessageHolderBean)t.next();
					//check if <ENTER> is still there
					if(bean!=null
							&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
						errors.add("caseHistory"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
								Resources.getMessage(request, "access.app.verifydecision.casehistory"), String.valueOf(i)));
					}
					//check if the total length of the text entered + number of paragraph separators are not > 5000
					if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
						caseHistoryLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
					}
					caseHistoryLength += bean.getMessage().length();
					i++;
					
				}
				if(caseHistoryLength > 2500){
					errors.add("caseHistory", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
							Resources.getMessage(request, "access.app.verifydecision.casehistory")));
				}
			}
			//Check for Issue paragraphs
			if(this.getIssueParagraph()!=null && !this.getIssueParagraph().isEmpty()){
				Iterator t= this.getIssueParagraph().iterator();
				int i=1;
				while(t.hasNext()){
					MessageHolderBean bean= (MessageHolderBean)t.next();
					if(bean!=null
							&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
						errors.add("issue"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
								Resources.getMessage(request, "access.app.verifydecision.issue"), String.valueOf(i)));
					}
					if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
						issueLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
					}
					issueLength += bean.getMessage().length();
					i++;
					
				}
				if(issueLength > 1000){
					errors.add("issue", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
							Resources.getMessage(request, "access.app.verifydecision.issue")));
				}
			}
			//check for fact finding paragraphs
			if(this.getFactFindingParagraph()!=null && !this.getFactFindingParagraph().isEmpty()){
				Iterator t= this.getFactFindingParagraph().iterator();
				int i=1;
				while(t.hasNext()){
					MessageHolderBean bean= (MessageHolderBean)t.next();
					if(bean!=null
							&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
						errors.add("factFinding"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
								Resources.getMessage(request, "access.app.verifydecision.factfinding"), String.valueOf(i)));
					}
					if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
						factFindingLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
					}
					factFindingLength += bean.getMessage().length();
					i++;
					
				}
				if(factFindingLength > 7000){
					errors.add("factFinding", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
							Resources.getMessage(request, "access.app.verifydecision.factfinding")));
				}
			}
									
			//check for decision reason paragraph
			if(this.getDecisionReasonParagraph()!=null && !this.getDecisionReasonParagraph().isEmpty()){
				Iterator t= this.getDecisionReasonParagraph().iterator();
				int i=1;
				while(t.hasNext()){
					MessageHolderBean bean= (MessageHolderBean)t.next();
					if(bean!=null
							&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
						errors.add("decision"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
								Resources.getMessage(request, "access.app.verifydecision.decision"), String.valueOf(i)));
					}
					if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
						decisionLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
					}
					decisionLength += bean.getMessage().length();
					i++;
					
				}
				if(decisionLength > 5000){
					errors.add("decision", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
							Resources.getMessage(request, "access.app.verifydecision.decision")));
				}
			}
			
			//check for decision paragraph
			if(this.getDecisionParagraph()!=null && !this.getDecisionParagraph().isEmpty()){
				Iterator t= this.getDecisionParagraph().iterator();
				int i=1;
				while(t.hasNext()){
					MessageHolderBean bean= (MessageHolderBean)t.next();
					if(bean!=null
							&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
						errors.add("decision"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
								Resources.getMessage(request, "access.app.verifydecision.decision"), String.valueOf(i)));
					}
					if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
						decisionLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
					}
					decisionLength += bean.getMessage().length();
					i++;
					
				}
				if(decisionLength > 5000){
					errors.add("decision", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
							Resources.getMessage(request, "access.app.verifydecision.decision")));
				}
			}
			//check for reason and conclusion paragraph
			if(this.getReasonAndConclusionParagraph()!=null && !this.getReasonAndConclusionParagraph().isEmpty()){
				Iterator t= this.getReasonAndConclusionParagraph().iterator();
				int i=1;
				while(t.hasNext()){
					MessageHolderBean bean= (MessageHolderBean)t.next();
					if(bean!=null
							&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
						errors.add("reasoning"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
								Resources.getMessage(request, "access.app.verifydecision.reasoning"), String.valueOf(i)));
					}
					if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
						reasoningLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
					}
					reasoningLength += bean.getMessage().length();
					i++;
					
				}
				if(reasoningLength > 12000){
					errors.add("reasoning", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
							Resources.getMessage(request, "access.app.verifydecision.reasoning")));
				}
			}
			//check for appeals right paragraph
			if(this.getAppealRightsParagraph()!=null && !this.getAppealRightsParagraph().isEmpty()){
				Iterator t= this.getAppealRightsParagraph().iterator();
				int i=1;
				while(t.hasNext()){
					MessageHolderBean bean= (MessageHolderBean)t.next();
					if(bean!=null
							&& StringUtility.contains(bean.getMessage(), AppealConstants.DEFAULT_PARAM)){
						errors.add("appealRights"+i, new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter",
								Resources.getMessage(request, "access.app.verifydecision.appealrights"), String.valueOf(i)));
					}
					if(i>1 && StringUtility.isNotBlank(bean.getMessage())) {
						appealRightsLength +=AppealConstants.DECISION_LETTER_PARA_SEP.length();
					}
					appealRightsLength += bean.getMessage().length();
					i++;
					
				}
				if(appealRightsLength > 5000){
					errors.add("appealRights", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.size",
							Resources.getMessage(request, "access.app.verifydecision.appealrights")));
				}
			}
		}*/
		
		}
		
		 int fileSize = this.fileName.getFileSize();
		 
			if((this.fileName == null) || (this.fileName.getFileName().equals(""))){
				
			  // errors.add("fileName", new ActionMessage("error.required", this.getApplicationMessages(arg1,"error.access.app.upload.Document.into.DMS.uploadfile")));
			   errors.add("fileName",new ActionMessage("error.access.app.upload.Document.into.DMS.uploadfile"));
			   
			}//else if(!StringUtility.isValuePresent(GlobalConstants.UPLOAD_DOCUMENTS_DMS_SUPPORTED_FILE_FORMATS,StringUtility.getFileExtension(this.fileName.getFileName())))
			//CIF_P2_01300 : To allow only PDF files
			else if(!StringUtility.isValuePresent(GlobalConstants.UPLOAD_DOCUMENTS_LIRC_DECISION,StringUtility.getFileExtension(this.fileName.getFileName())))
			{
				errors.add("fileName",new ActionMessage("error.access.upload.Document.into.appeals.incorrect.extension"));
				
			}else if(fileSize == 0)
			  {
				 errors.add("fileName",new ActionMessage("error.access.upload.Document.into.DMS.lirc.incorrect.file.size"));
			  }

			/*if(StringUtility.isBlank(this.inFavorAppellant))
			{
				errors.add("inFavorAppellant", new ActionMessage("error.required",
						Resources.getMessage(request, "error.access.app.processproofreading.proofreading.infavorappellant")));
			}*/
		
		
		if (errors.size() > 0) {
			return errors;
		}
		
		return errors;
	}

	public AppealInfoBean getAppInfoBeanDisp() {
		return appInfoBeanDisp;
	}

	public void setAppInfoBeanDisp(AppealInfoBean appInfoBeanDisp) {
		this.appInfoBeanDisp = appInfoBeanDisp;
	}
	public String getInFavorAppellant() {
		return inFavorAppellant;
	}
	public void setInFavorAppellant(String inFavorAppellant) {
		this.inFavorAppellant = inFavorAppellant;
	}

}