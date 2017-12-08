package gov.state.uim.framework.dms.struts;

import org.apache.commons.fileupload.FileItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.domain.bean.CalendarBean;
import gov.state.uim.domain.bean.EanBean;
import gov.state.uim.domain.bean.PhoneBean;
import gov.state.uim.domain.bean.SsnBean;
import gov.state.uim.domain.data.IRDocumentData;
import gov.state.uim.framework.workflow.struts.BaseWorkflowValidatorForm;
import gov.state.uim.util.lang.StringUtility;

//CIF_00109
/**
 * This is the form class related to the Add/Update Information Request Screen
 * 
 * @struts.form name ="externalinformationrequestform"
 */
 public class ExternalInformationRequestForm extends BaseWorkflowValidatorForm {

	private static final long serialVersionUID = 1L;

	private CalendarBean dateOfRequest;

	private String invoice;

	private String requestingEntity;

	private String requestingEntityReference;

	private String addressLineOne;

	private String addressLineTwo;

	private String city;

	private String state;

	private String country;

	private String zipCode;

	private String contactPerson;

	private CalendarBean deadlineDate;
	
	//CIF_01667 
	private PhoneBean telephoneNumber;
	//CIF_01667 
	private PhoneBean faxNumber;

	private String email;

	private String entityType;

	private String lawOfEnforcement;

	private String claimantName;

	private String employerName;

	private String informationRequested;

	private String informationProvided;

	private String comment;

	private String status;

	private double billingAmount;

	private CalendarBean billingDate;

	private double paidAmount;

	private CalendarBean paidDate;

	private String methodOfPayment;
	
	private String requestType;

	private FileItem attachmentOne;
	private FileItem attachmentTwo;
	private FileItem attachmentThree;

	private SsnBean ssnBean;
	private EanBean eanBean;
	//CIF_01474
	private String irdocumentPath;
	//CIF_01474
	private List<IRDocumentData> irdocumentName;
	public ExternalInformationRequestForm() {
		super();

		// CIF_P2_01714	Current Date to be displayed
		dateOfRequest = new CalendarBean(new Date());
		deadlineDate = new CalendarBean();
		billingDate = new CalendarBean();
		paidDate = new CalendarBean();
		ssnBean = new SsnBean();
		eanBean = new EanBean();
		telephoneNumber=new PhoneBean();
		faxNumber=new PhoneBean();
		this.irdocumentName=new ArrayList<IRDocumentData>();

	}

	public EanBean getEanBean() {
		return eanBean;
	}

	public SsnBean getSsnBean() {
		return ssnBean;
	}

	public void setSsnBean(SsnBean ssnBean) {
		this.ssnBean = ssnBean;
	}

	public FileItem getAttachmentOne() {
		return attachmentOne;
	}

	public void setAttachmentOne(FileItem attachmentOne) {
		this.attachmentOne = attachmentOne;
	}
	public void setAttachmentOne(Object attachmentOne){
		if(attachmentOne!=null){
			List<FileItem>fileItems=(List<FileItem>)attachmentOne;
			if(fileItems!=null && !fileItems.isEmpty()){
				this.fileName=fileItems.get(0);
			}
		}
	}


	public FileItem getAttachmentThree() {
		return attachmentThree;
	}

	public void setAttachmentThree(FileItem attachmentThree) {
		this.attachmentThree = attachmentThree;
	}
	public void setAttachmentThree(Object attachmentThree){
		if(attachmentThree!=null){
			List<FileItem>fileItems=(List<FileItem>)attachmentThree;
			if(fileItems!=null && !fileItems.isEmpty()){
				this.fileName=fileItems.get(0);
			}
		}
	}


	public FileItem getAttachmentTwo() {
		return attachmentTwo;
	}

	public void setAttachmentTwo(FileItem attachmentTwo) {
		this.attachmentTwo = attachmentTwo;
	}
	public void setAttachmentTwo(Object attachmentTwo){
		if(attachmentTwo!=null){
			List<FileItem>fileItems=(List<FileItem>)attachmentTwo;
			if(fileItems!=null && !fileItems.isEmpty()){
				this.fileName=fileItems.get(0);
			}
		}
	}

	/**
	 * @struts.validator type = "maxlength" msgkey="error.maxlength"
	 *                   arg0resource =
	 *                   "access.cin.duaselfempques.primaryoccupationdesc.error"
	 *                   arg1value="access.maxlength.50"
	 * @struts.validator-var name="maxlength" value="50"
	 */

	public String getMethodOfPayment() {
		return methodOfPayment;
	}

	public void setMethodOfPayment(String methodOfPayment) {
		this.methodOfPayment = methodOfPayment;
	}

	public CalendarBean getDateOfRequest() {
		return dateOfRequest;
	}

	/**
	 * @struts.validator 
	 * 		type = "requiredCalDate"
	 * 		override = "true" 
	 * 		msgkey="error.required"
	 * 		arg0resource = "add.update.information.request.dateofrequest"
	 * 
	 * 	 @struts.validator
	 * 		type = "calDate"
	 * 		override = "true"
	 * 		msgkey="error.access.format.invaliddate"
	 * 		arg0resource = "add.update.information.request.dateofrequest"
	 * 
	 * 
	 * @struts.validator
	 * 		type = "pastCalDate"
	 * 		override = "true"
	 * 		msgkey = "error.access.caldate.question.notpast"
	 * 		arg0resource = "add.update.information.request.dateofrequest"
                 
	 */
	public void setDateOfRequest(CalendarBean dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}

	public CalendarBean getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(CalendarBean paidDate) {
		this.paidDate = paidDate;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getRequestingEntity() {
		return requestingEntity;
	}

	/**
	 * @struts.validator type = "required" msgkey="error.required" arg0resource
	 *                   = "add.update.information.request.requestingentity"
	 */

	public void setRequestingEntity(String requestingEntity) {
		this.requestingEntity = requestingEntity;
	}

	public String getRequestingEntityReference() {
		return requestingEntityReference;
	}

	public void setRequestingEntityReference(String requestingEntityReference) {
		this.requestingEntityReference = requestingEntityReference;
	}

	public String getAddressLineOne() {
		return addressLineOne;
	}
	/**
     * @struts.validator type = "required" override = "true" msgkey="error.address.line1"
     * @struts.validator type = "minlength" msgkey="error.address.line1.minlength"
     * @struts.validator-var name="minlength" value="3"
     * @struts.validator-args arg0resource = "access.address1"
     */
	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	public String getCity() {
		return city;
	}
	 /**
     * @struts.validator type = "required" override = "true" msgkey="error.address.city"
     * @struts.validator type = "validCity" msgkey="error.address.city.mask"
     * @struts.validator-args arg0resource = "access.city"
     */
	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}
	/**
     * @struts.validator type = "required" override = "true" msgkey="error.required"
     * @struts.validator-args arg0resource = "access.state"
     */
	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}
	/**
     * @struts.validator type = "required" override = "true" msgkey="error.required"
     * @struts.validator-args arg0resource = "access.country"
     */
	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public CalendarBean getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(CalendarBean deadlineDate) {
		this.deadlineDate = deadlineDate;
	}
	
	
	/**
	 * @return the telephoneNumber
	 */
	public PhoneBean getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * @param telephoneNumber the telephoneNumber to set
	 */
	public void setTelephoneNumber(PhoneBean telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	/**
	 * @return the faxNumber
	 */
	public PhoneBean getFaxNumber() {
		return faxNumber;
	}

	/**
	 * @param faxNumber the faxNumber to set
	 */
	public void setFaxNumber(PhoneBean faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getLawOfEnforcement() {
		return lawOfEnforcement;
	}

	public void setLawOfEnforcement(String lawOfEnforcement) {
		this.lawOfEnforcement = lawOfEnforcement;
	}

	public String getClaimantName() {
		return claimantName;
	}

	public void setClaimantName(String claimantName) {
		this.claimantName = claimantName;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	/*CIF_INT_00853	|| Defect_7920 starts*/
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	/*CIF_INT_00853	|| Defect_7920 end*/
	public void clear() {
		this.addressLineOne = null;
		this.addressLineTwo = null;
		//this.billingAmount = 0.0;
		this.city = null;
		this.claimantName = null;
		this.comment = null;
		this.contactPerson = null;
		this.comment = null;
		this.contactPerson = null;
		this.country = null;
		this.email = null;
		this.employerName = null;
		this.entityType = null;
		this.faxNumber = null;
		this.attachmentOne = null;
		this.attachmentTwo = null;
		this.attachmentThree = null;
		this.informationProvided = null;
		this.informationRequested = null;
		this.invoice = null;
		this.lawOfEnforcement = null;
		this.methodOfPayment = null;
		this.paidAmount = 0.0;
		this.requestingEntity = null;
		this.requestingEntityReference = null;
		this.state = null;
		this.status = null;
		this.telephoneNumber = null;
		this.zipCode = null;

	}

	/**
	 * @struts.validator type = "maxlength" msgkey="error.maxlength"
	 *                   arg0resource =
	 *                   "access.cin.duaselfempques.primaryoccupationdesc.error"
	 *                   arg1value="access.maxlength.250"
	 * @struts.validator-var name="maxlength" value="250"
	 */

	public String getInformationRequested() {
		return informationRequested;
	}

	public void setInformationRequested(String informationRequested) {
		this.informationRequested = informationRequested;
	}

	/**
	 * @struts.validator type = "maxlength" msgkey="error.maxlength"
	 *                   arg0resource =
	 *                   "access.cin.duaselfempques.primaryoccupationdesc.error"
	 *                   arg1value="access.maxlength.250"
	 * @struts.validator-var name="maxlength" value="250"
	 */

	public String getInformationProvided() {
		return informationProvided;
	}

	public void setInformationProvided(String informationProvided) {
		this.informationProvided = informationProvided;
	}

	/**
	 * @struts.validator type = "maxlength" msgkey="error.maxlength"
	 *                   arg0resource =
	 *                   "access.cin.duaselfempques.primaryoccupationdesc.error"
	 *                   arg1value="access.maxlength.500"
	 * @struts.validator-var name="maxlength" value="500"
	 */

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	/**
	 * @struts.validator type = "required" msgkey="error.required" arg0resource
	 *                   = "add.update.information.request.status"
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public double getBillingAmount() {
		return billingAmount;
	}

	public void setBillingAmount(double billingAmount) {
		this.billingAmount = billingAmount;
	}

	public CalendarBean getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(CalendarBean billingDate) {
		this.billingDate = billingDate;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public List<IRDocumentData> getIrdocumentName() {
		return irdocumentName;
	}

	public void setIrdocumentName(List<IRDocumentData> irdocumentName) {
		this.irdocumentName = irdocumentName;
	}

	public String getIrdocumentPath() {
		return irdocumentPath;
	}

	public void setIrdocumentPath(String irdocumentPath) {
		this.irdocumentPath = irdocumentPath;
	}
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {		
		if("GET".equals(arg1.getMethod()) && super.isSameSubmitMethod(arg1, arg0, "access.document.view")){
			return new ActionErrors();
			}
		ActionErrors errors = super.validate(arg0, arg1);				
		if (errors.size() > 0) {
			return errors;
		}
		if(this.attachmentOne != null && !this.attachmentOne.getFileName().equals(""))
		{
			if(!StringUtility.isValuePresent(GlobalConstants.UPLOAD_DOCUMENTS_INFORMATION_REPORT_REQUEST_SUPPORTED_FILE_FORMATS,
					StringUtility.getFileExtension(this.attachmentOne.getFileName())))
			{
				errors.add("attachmentOne",new ActionMessage("error.access.upload.Document.into.information.request.incorrect.extension","1"));
			}
			int fileSize = this.attachmentOne.getFileSize();
			if(fileSize == 0 || fileSize > GlobalConstants.UPLOAD_DOCUMENTS_INFORMATION_REPORT_REQUEST_ATTACHMENT_LIMIT)
			{
				errors.add("attachmentOne",new ActionMessage("access.upload.Document.into.IR.incorrect.file.size.error","1"));
			}
		}
		if(this.attachmentTwo != null && !this.attachmentTwo.getFileName().equals(""))
		{
			if(!StringUtility.isValuePresent(GlobalConstants.UPLOAD_DOCUMENTS_INFORMATION_REPORT_REQUEST_SUPPORTED_FILE_FORMATS, 
					StringUtility.getFileExtension(this.attachmentTwo.getFileName())))
			{
				errors.add("attachmentTwo",new ActionMessage("error.access.upload.Document.into.information.request.incorrect.extension","2"));
			}
			int fileSize = this.attachmentTwo.getFileSize();
			if(fileSize == 0 || fileSize > GlobalConstants.UPLOAD_DOCUMENTS_INFORMATION_REPORT_REQUEST_ATTACHMENT_LIMIT)
			{
				errors.add("attachmentTwo",new ActionMessage("access.upload.Document.into.IR.incorrect.file.size.error","2"));
			}			
			
		}
		if(this.attachmentThree != null && !this.attachmentThree.getFileName().equals(""))
		{
			if(!StringUtility.isValuePresent(GlobalConstants.UPLOAD_DOCUMENTS_INFORMATION_REPORT_REQUEST_SUPPORTED_FILE_FORMATS, 
					StringUtility.getFileExtension(this.attachmentThree.getFileName())))
			{
				errors.add("attachmentThree",new ActionMessage("error.access.upload.Document.into.information.request.incorrect.extension","3"));
			}
			int fileSize = this.attachmentThree.getFileSize();
			if(fileSize == 0 || fileSize > GlobalConstants.UPLOAD_DOCUMENTS_INFORMATION_REPORT_REQUEST_ATTACHMENT_LIMIT)
			{
				errors.add("attachmentThree",new ActionMessage("access.upload.Document.into.IR.incorrect.file.size.error","3"));
			}
	
		}
		return errors;
	}

}