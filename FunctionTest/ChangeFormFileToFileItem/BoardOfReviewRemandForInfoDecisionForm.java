package gov.state.uim.app.struts;

import org.apache.commons.fileupload.FileItem;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.Resources;

import gov.state.uim.domain.bean.AppealInfoBean;
import gov.state.uim.domain.bean.CalendarBean;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.StringUtility;

/**
 * @struts.form name ="boardofreviewremandforinfodecisionform"
 */

public class BoardOfReviewRemandForInfoDecisionForm  extends BaseValidatorForm{
	private static final long serialVersionUID = -5487554778303110282L;
	
	private String remandReason;
	private String otherReason;
	private  String decision;
	private AppealInfoBean appInfoDisplayBean;
	private String claimantSsn;
	private String claimantName;
	private String mdesEan;
	private String employerName;
	private String caseHistory1;
	private String caseHistory2;
	private String caseHistory3;
	//CIF_02266
	private FileItem fileName;
	private CalendarBean decisionMailDate;
	
	public BoardOfReviewRemandForInfoDecisionForm() {
		super();
		this.decisionMailDate = new CalendarBean();
	}
	
	public String getClaimantName() {
		return claimantName;
	}
	public void setClaimantName(String claimantName) {
		this.claimantName = claimantName;
	}
	public String getClaimantSsn() {
		return claimantSsn;
	}
	public void setClaimantSsn(String claimantSsn) {
		this.claimantSsn = claimantSsn;
	}
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	public String getMdesEan() {
		return mdesEan;
	}
	public void setMdesEan(String mdesEan) {
		this.mdesEan = mdesEan;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getOtherReason() {
		return otherReason;
	}
	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}
	public String getRemandReason() {
		return remandReason;
	}
	
	/**
	 * @struts.validator
	 * type = "required"
	 * 	msgkey="error.required"
	 * @struts.validator-args
	 * arg0resource = "access.app.boardofreviewremandforinfodecision.reason.number"
	 * */

	
	public void setRemandReason(String remandReason) {
		this.remandReason = remandReason;
	}
	
	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = super.validate(arg0, arg1);
		// if submit button is pressed
		if(super.isSameSubmitMethod(arg1,arg0,"access.submit")){
			if("OTHR".equals(remandReason)){
				if(otherReason == null || otherReason.equals("")){
					errors.add("otherReason", new ActionMessage("error.access.boardofreviewremandforinfodecision.null"));
				}
			}
			if (StringUtility.isNotBlank(this.otherReason)){
				if (this.otherReason.length() > 500){
					errors.add("otherReason", new ActionMessage("error.maxlength.500",
							Resources.getMessage(arg1, "1.a")));
				}
			}

			/*
			 * commented as of CIF_02266
			 * Not in use
			 * //3666
			//check if <ENTER> is still there
			if(caseHistory1!=null
					&& StringUtility.contains(caseHistory1, AppealConstants.DEFAULT_PARAM)){
				errors.add("caseHistory1", new ActionMessage("error.access.app.verifydecision.paragraphvalidation.enter", Resources.getMessage(arg1, "access.app.verifydecision.casehistory"), "1"));
			}
						
			//check if the total length of the text entered + number of paragraph separators are not > 2000 (pad for at least 2 line break delims)
			if(caseHistory1!=null && caseHistory2!=null && caseHistory3!=null)
			{
				if((caseHistory1.length() + caseHistory2.length() + caseHistory3.length()) > 1994){
					errors.add("caseHistory1", new ActionMessage("error.maxlength.2000",
							Resources.getMessage(arg1, "2")));
				}	
			}*/
			
			
			if( getFileName().getFileSize()== 0){
			       errors.add("fileName", new ActionMessage("error.required", 
			    		   Resources.getMessage(arg1, "access.app.boardofreviewremandforinfodecision.selectdecision.number")));
			}else{	 
			    //only allow pdf to upload
			    if(!"application/pdf".equals(getFileName().getContentType())){
			        errors.add("fileName", 
			        		new ActionMessage("access.app.boardofreviewremandforinfodecision.selectdecision.filetype.error"));

			    }
			}
		}
		return errors;
	
	}
	public AppealInfoBean getAppInfoDisplayBean() {
		return appInfoDisplayBean;
	}
	public void setAppInfoDisplayBean(AppealInfoBean appInfoDisplayBean) {
		this.appInfoDisplayBean = appInfoDisplayBean;
	}
	public String getCaseHistory1() {
		return caseHistory1;
	}
	public void setCaseHistory1(String caseHistory1) {
		this.caseHistory1 = caseHistory1;
	}
	public String getCaseHistory2() {
		return caseHistory2;
	}
	public void setCaseHistory2(String caseHistory2) {
		this.caseHistory2 = caseHistory2;
	}
	public String getCaseHistory3() {
		return caseHistory3;
	}
	public void setCaseHistory3(String caseHistory3) {
		this.caseHistory3 = caseHistory3;
	}
	/**
	 * @return the fileName
	 */
	public FileItem getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
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

	
	public CalendarBean getDecisionMailDate() {
		return decisionMailDate;
	}
	
	
	/**
	 * @struts.validator
	 * 		type = "requiredCalDate"
	 * 		override = "true"
	 * 		msgkey = "error.required"
	 * 		arg0resource = "access.app.boardofreviewremandforinfodecision.decisionmaildate.error"
	 * 
	 * @struts.validator
	 * 		type = "calDate"
	 * 		override = "true"
	 * 		msgkey="error.access.format.invalid"
	 * 		arg0resource = "access.app.boardofreviewremandforinfodecision.decisionmaildate.error"
	 * 		arg1resource = "access.date.format"
	 * @struts.validator-var
	 *   	value = "MM/dd/yyyy"
	 *   	name ="datePattern"
	 *
	 */
	public void setDecisionMailDate(CalendarBean decisionMailDate) {
		this.decisionMailDate = decisionMailDate;
	}

}