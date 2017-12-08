/*
 * This action corresponds to Enter SSN screen 
 * jsp				:employerquicklaunchresponse.jsp
 * Action			: EmployerQuickLaunchResponseAction.java
 * Form				: EmployerQuickLaunchResponseForm.java
 * tiles-defs.xml	: .employerquicklaunchresponse (acccessms/WEB-INF/jsp)
 */

package gov.state.uim.nmon.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import gov.state.uim.ViewConstants;
import gov.state.uim.domain.bean.CalendarBean;
import gov.state.uim.domain.bean.EanBean;
import gov.state.uim.domain.bean.PhoneBean;
import gov.state.uim.domain.enums.UI21AReasonSeparationEnum;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.DateUtility;
import gov.state.uim.util.lang.StringUtility;

/**
 * @struts.form name ="employerquicklaunchui21aresponseform"
 */
public class EmployerQuickLaunchUI21AResponseForm extends BaseValidatorForm {

	private static final long serialVersionUID = 8662022744430748649L;
	
	private String ssn;
	private String ean;
	private String  name;
	private String employerName;
	private String employerAddress;
	private CalendarBean startDate;
	private CalendarBean endDate;
	private CalendarBean basePeriodEndDate;
	private String earnings;
	private EanBean accountNumber;
	private String paymentInfo;
	private String workRefused;
	private CalendarBean refusedDate;
	private String reasonForSeparation;
	private String discharge;
	private String referredName;
	private String referredTitle;
	private PhoneBean phoneNumber;
/*	private FormFile attachmentOne;
	private FormFile attachmentTwo;
	private FormFile attachmentThree;*/
	private String attachmentPresent;
	
	public String getAttachmentPresent() {
		return attachmentPresent;
	}

	/**
	 * @struts.validator
	 * 			type = "required"
	 * 			msgkey="error.required"
	 * 			arg0resource = "access.nmon.quickaccess.attachment.number"
	 * 
	 * 			
	 */
	public void setAttachmentPresent(String attachmentPresent) {
		this.attachmentPresent = attachmentPresent;
	}

	public EmployerQuickLaunchUI21AResponseForm() {
		super();
		accountNumber = new EanBean();
		startDate=new CalendarBean();
		endDate=new CalendarBean();
		basePeriodEndDate=new CalendarBean();
		refusedDate=new CalendarBean();
		phoneNumber=new PhoneBean();
		
	}
	
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmployerName() {
		return employerName;
	}	
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	
	public String getEmployerAddress() {
		return employerAddress;
	}
	public void setEmployerAddress(String employerAddress) {
		this.employerAddress = employerAddress;
	}
	
	public CalendarBean getStartDate() {
		return startDate;
	}	
	/**      
	 * @struts.validator 
	 * 		type = "calDate" 
	 * 		override = "true"
	 *      msgkey="error.access.format.invalid"
	 *      arg0resource = "access.nmon.quicklaunch.startdate.number"
	 *      arg1resource = "access.date.format"
	 *      
	 * @struts.validator 
	 * 		type = "pastCalDate" 
	 * 		override = "true"
	 * 		msgkey="error.access.caldate.question.notpast"
	 * 		arg0resource = "access.nmon.quicklaunch.startdate.number"   
	 *      
	 * @struts.validator
	 * 		type = "minCalDate"
	 * 		override = "true"
	 * 		msgkey="error.access.caldate.question.after1900"
	 * 		arg0resource = "access.nmon.quicklaunch.startdate.number"
	 * 
	 */
	public void setStartDate(CalendarBean startDate) {
		this.startDate = startDate;
	}

	public CalendarBean getEndDate() {
		return endDate;
	}	
	/**      
	 * @struts.validator 
	 * 		type = "calDate" 
	 * 		override = "true"
	 *      msgkey="error.access.format.invalid"
	 *      arg0resource = "access.nmon.quicklaunch.enddate.number"
	 *      arg1resource = "access.date.format"
	 *      
	 * @struts.validator 
	 * 		type = "pastCalDate" 
	 * 		override = "true"
	 * 		msgkey="error.access.caldate.question.notpast"
	 * 		arg0resource = "access.nmon.quicklaunch.enddate.number"   
	 *      
	 * @struts.validator
	 * 		type = "minCalDate"
	 * 		override = "true"
	 * 		msgkey="error.access.caldate.question.after1900"
	 * 		arg0resource = "access.nmon.quicklaunch.enddate.number"
	 * 
	 */	
	public void setEndDate(CalendarBean endDate) {
		this.endDate = endDate;
	}
	
	public String getEarnings() {
		return earnings;
	}
	/** 
	 * @struts.validator 
	 * 			type = "fmtMonetaryAmount"
	 * 			msgkey= "errors.number"
	 *          arg1value="${var:maxamount}"
	 *          arg2value="${var:minamount}"	 
	 * 			arg0resource = "access.nmon.quicklaunch.grossearnings.number"
	 * 			arg1resource = "access.twr.double.maxlength"
	 * 
	 * @struts.validator-var 
	 * 			name="maxamount" value="99999999.99"
	 * 
	 * @struts.validator 
	 * 			type = "fmtMonetaryAmount"
	 * 			msgkey= "errors.number"
	 *          arg1value="${var:minamount}"
	 * 			arg0resource = "access.nmon.quicklaunch.grossearnings.number"
	 *
	 * @struts.validator-var 
	 * 			name="minamount" value="1.00"
	 * 
	 */	
	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}
	
	public EanBean getAccountNumber() {
		return accountNumber;
	}	
	/** 
	 * @struts.validator 
	 * 		type = "unitEan" 
	 * 		override = "true"
	 * 		msgkey="errors.ean" 
	 * 
	 * @struts.validator-args 
	 *      arg0resource = "access.nmon.quicklaunch.uiaccount.number"
	 */
	public void setAccountNumber(EanBean accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getPaymentInfo() {
		return paymentInfo;
	}	
	
	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	
	public String getWorkRefused() {
		return workRefused;
	}	
	public void setWorkRefused(String workRefused) {
		this.workRefused = workRefused;
	}
	
	public CalendarBean getRefusedDate() {
		return refusedDate;
	}
    /** @struts.validator 
	 * 		type = "calDate" 
	 * 		override = "true"
	 *      msgkey="error.access.format.invalid"
	 *      arg0resource = "access.nmon.quicklaunch.refuseddate.error"
	 *      arg1resource = "access.date.format"
	 *      
	 * @struts.validator 
	 * 		type = "pastCalDate" 
	 * 		override = "true"
	 * 		msgkey="error.access.caldate.question.notpast"
	 * 		arg0resource = "access.nmon.quicklaunch.refuseddate.error"   
	 *      
	 * @struts.validator
	 * 		type = "minCalDate"
	 * 		override = "true"
	 * 		msgkey="error.access.caldate.question.after1900"
	 * 		arg0resource = "access.nmon.quicklaunch.refuseddate.error"
	 * 
	 */	
	public void setRefusedDate(CalendarBean refusedDate) {
		this.refusedDate = refusedDate;
	}
	
	public String getReasonForSeparation() {
		return reasonForSeparation;
	}	
	/**
	 * @struts.validator
	 * 			type = "required"
	 * 			msgkey="error.required"
	 * 			arg0resource = "access.nmon.quicklaunch.reasonSeparation.number"
	 * 
	 * 			
	 */		
	public void setReasonForSeparation(String reasonForSeparation) {
		this.reasonForSeparation = reasonForSeparation;
	}
	
	
  	public String getDischarge() {
		return discharge;
	}
    /**
	 * 
	 * @struts.validator 
	 * 			type = "maxlength"
	 * 			msgkey="error.maxlength"
	 *          arg1value="${var:maxlength}"
	 * 			arg0resource = "access.nmon.quicklaunch.discharge.errornumber"
	 * 			arg1resource = "access.nmon.quicklaunch.discharge.maxlength"
	 * 
	 * @struts.validator-var 
	 * 			name="maxlength" value="256"
	 */
	public void setDischarge(String discharge) {
		this.discharge = discharge;
	}
	
	public String getReferredName() {
		return referredName;
	}
	public void setReferredName(String referredName) {
		this.referredName = referredName;
	}
	
	public String getReferredTitle() {
		return referredTitle;
	}	
	public void setReferredTitle(String referredTitle) {
		this.referredTitle = referredTitle;
	}

	public PhoneBean getPhoneNumber() {
		return phoneNumber;
	}	
	/**
	 *  @struts.validator
     *           type = "phone"
     *           override = "true"
     *           msgkey = "errors.phone"
     *           arg0resource = "access.nmon.quicklaunch.telephone.error"
	 * 
	 */
	public void setPhoneNumber(PhoneBean phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	public CalendarBean getBasePeriodEndDate() {
		return basePeriodEndDate;
	}
	public void setBasePeriodEndDate(CalendarBean basePeriodEndDate) {
		this.basePeriodEndDate = basePeriodEndDate;
	}
	
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {		
		ActionErrors errors = super.validate(arg0, arg1);				
		if (errors.size() > 0) {
			return errors;
		}
		if(!this.startDate.isNull() && !this.endDate.isNull()){
			if(DateUtility.compareDates(this.startDate.getValue(),this.endDate.getValue()) == 1) {
				errors.add("startDate",new ActionMessage("error.access.date.compare", "1","2"));
			}
		}
				
		if(StringUtility.isNotBlank(this.reasonForSeparation) && !UI21AReasonSeparationEnum.NOTEMPLOYED.getName().equals(this.reasonForSeparation) &&  endDate.isNull()){
			errors.add("endDate", new ActionMessage("error.access.question.dependsnull", "2","7", "other than Never Employed"));
		}
		if(ViewConstants.YES.equals(this.workRefused) && this.refusedDate.isNull()){
			errors.add("refusedDate", new ActionMessage("error.access.question.dependsnull", "6.a.","6", "yes"));
		}
		if(ViewConstants.NO.equals(this.workRefused) && (!this.refusedDate.isNull())){
			errors.add("refusedDate", new ActionMessage("error.access.question.dependsnotnull", "6.a.","6", "no"));
		}
		if(!UI21AReasonSeparationEnum.LACKOFWORK.getName().equals(this.reasonForSeparation) && !UI21AReasonSeparationEnum.NOTEMPLOYED.getName().equals(this.reasonForSeparation)){
			if(StringUtility.isBlank(this.discharge)){
				errors.add("discharge", new ActionMessage("error.access.question.dependsnull", "7.a","7", "other than Lack of work / Laid off or Never Employed"));
			}
		}
		
		/*
		if(this.attachmentOne != null && !this.attachmentOne.getFileName().equals(""))
		{
			if(!(this.attachmentOne.getFileName().endsWith(".txt") ||
					this.attachmentOne.getFileName().endsWith(".doc") ||
					this.attachmentOne.getFileName().endsWith(".xls") ||
							this.attachmentOne.getFileName().endsWith(".pdf")))
			{
				errors.add("attachmentOne",new ActionMessage("error.access.quick.launch.incorrect.extension","1"));
			}
			int fileSize = this.attachmentOne.getFileSize();
			if(fileSize == 0 || fileSize > 100*1024)
			{
				errors.add("attachmentOne",new ActionMessage("error.access.quick.launch.incorrect.file.size","1"));
			}
			
			
		}
		if(this.attachmentTwo != null && !this.attachmentTwo.getFileName().equals(""))
		{
			if(!(this.attachmentTwo.getFileName().endsWith(".txt") ||
						this.attachmentTwo.getFileName().endsWith(".doc") ||
								this.attachmentTwo.getFileName().endsWith(".xls") ||
										this.attachmentTwo.getFileName().endsWith(".pdf")))
			{
				errors.add("attachmentTwo",new ActionMessage("error.access.quick.launch.incorrect.extension","2"));
			}
			int fileSize = this.attachmentTwo.getFileSize();
			if(fileSize == 0 || fileSize > 100*1024)
			{
				errors.add("attachmentTwo",new ActionMessage("error.access.quick.launch.incorrect.file.size","2"));
			}
			
			if(this.attachmentOne!= null && this.attachmentTwo.getFileName().equals(this.attachmentOne.getFileName()))
			{
				errors.add("attachmentTwo",new ActionMessage("error.access.quick.launch.already.added"));
			}
			
		}
		if(this.attachmentThree != null && !this.attachmentThree.getFileName().equals(""))
		{
			if(!(this.attachmentThree.getFileName().endsWith(".txt") ||
						this.attachmentThree.getFileName().endsWith(".doc") ||
								this.attachmentThree.getFileName().endsWith(".xls") ||
										this.attachmentThree.getFileName().endsWith(".pdf")))
			{
				errors.add("attachmentThree",new ActionMessage("error.access.quick.launch.incorrect.extension","3"));
			}
			int fileSize = this.attachmentThree.getFileSize();
			if(fileSize == 0 || fileSize > 100*1024)
			{
				errors.add("attachmentThree",new ActionMessage("error.access.quick.launch.incorrect.file.size","3"));
			}
			
			if(this.attachmentOne!= null && this.attachmentThree.getFileName().equals(this.attachmentOne.getFileName()))
			{
				errors.add("attachmentThree",new ActionMessage("error.access.quick.launch.already.added"));
			}
			if(this.attachmentTwo!= null && this.attachmentThree.getFileName().equals(this.attachmentTwo.getFileName()))
			{
				errors.add("attachmentThree",new ActionMessage("error.access.quick.launch.already.added"));
			}
		}*/
		return errors;
	}

	/*public FormFile getAttachmentOne() {
		return attachmentOne;
	}

	public void setAttachmentOne(FormFile attachmentOne) {
		this.attachmentOne = attachmentOne;
	}

	public FormFile getAttachmentThree() {
		return attachmentThree;
	}

	public void setAttachmentThree(FormFile attachmentThree) {
		this.attachmentThree = attachmentThree;
	}

	public FormFile getAttachmentTwo() {
		return attachmentTwo;
	}

	public void setAttachmentTwo(FormFile attachmentTwo) {
		this.attachmentTwo = attachmentTwo;
	}*/

	
		
}