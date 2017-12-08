package gov.state.uim.reports.desreport.struts;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.domain.bean.CalendarBean;
import gov.state.uim.domain.bean.EanBean;
import gov.state.uim.domain.bean.PhoneBean;
import gov.state.uim.domain.bean.SsnBean;
import gov.state.uim.domain.enums.MstCountryEnum;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.FormFieldValidateUtility;
import gov.state.uim.util.lang.StringUtility;

/**
 * @struts.form name ="desreportrequestform"
 */
public class DESReportRequestForm extends BaseValidatorForm
{

    private static final long serialVersionUID = -4619019254537312622L;

    private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(DESReportRequestForm.class);

    private CalendarBean dateOfRequest;
    private String requestingEntity;

    private String attention;
    private String careOf;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String stateOrProvince;
    private String stateOrProvinceForOthers;
    private String stateOrProvinceForUS;
    private String stateOrProvinceForCA;
    private String country;
    private String zipcode;

    private String contactPerson;
    private CalendarBean deadlineDate;
    private PhoneBean telephoneNumber;
    private PhoneBean fax;
    private String email;

    private String claimantName;
    private SsnBean claimantSsn;

    private String employerName;
    private EanBean ean;
    private String reportRequest;

    private FormFile attachmentOne;
    private FormFile attachmentTwo;
    private FormFile attachmentThree;

    public DESReportRequestForm()
    {
        super();
        this.dateOfRequest = new CalendarBean(new Date());
        deadlineDate = new CalendarBean();
        claimantSsn = new SsnBean();
        ean = new EanBean();
        telephoneNumber=new PhoneBean();
        fax=new PhoneBean();
    }

    /**
     * @return the dateOfRequest
     */
    public CalendarBean getDateOfRequest()
    {
        return dateOfRequest;
    }

    /**
     * @struts.validator type = "calDate" override = "true" msgkey="error.access.format.invalid" arg0resource = "error.des.request.report.number.one" arg1resource = "access.date.format"
     * @struts.validator-var value = "MM/dd/yyyy" name ="datePattern"
     * @struts.validator type = "minCalDate" override = "true" msgkey="error.access.caldate.question.after1900" arg0resource = "error.des.request.report.number.one"
     * @struts.validator type = "pastCalDate" override = "true" msgkey="error.access.caldate.question.notcurrentpast" arg0resource = "error.des.request.report.number.one"
     */
    public void setDateOfRequest(CalendarBean dateOfRequest)
    {
        this.dateOfRequest = dateOfRequest;
    }

    /**
     * @return the requestingEntity
     */
    public String getRequestingEntity()
    {
        return requestingEntity;
    }

    /**
     * @struts.validator type = "required" override = "true" msgkey="error.required"
     * @struts.validator-args arg0resource = "error.des.request.report.number.two"
     */
    public void setRequestingEntity(String requestingEntity)
    {
        this.requestingEntity = requestingEntity;
    }

    /**
     * @return the attention
     */
    public String getAttention()
    {
        return attention;
    }

    /**
     * @param attention the attention to set
     */
    public void setAttention(String attention)
    {
        this.attention = attention;
    }

    /**
     * @return the careOf
     */
    public String getCareOf()
    {
        return careOf;
    }

    /**
     * @param careOf the careOf to set
     */
    public void setCareOf(String careOf)
    {
        this.careOf = careOf;
    }

    /**
     * @return the addressLine1
     */
    public String getAddressLine1()
    {
        return addressLine1;
    }

    /**
     * @struts.validator type = "required" override = "true" msgkey="error.address.line1"
     * @struts.validator type = "minlength" msgkey="error.address.line1.minlength"
     * @struts.validator-var name="minlength" value="3"
     * @struts.validator-args arg0resource = "error.des.request.report.number.three.c"
     */
    public void setAddressLine1(String addressLine1)
    {
        this.addressLine1 = addressLine1;
    }

    /**
     * @return the addressLine2
     */
    public String getAddressLine2()
    {
        return addressLine2;
    }

    /**
     * @param addressLine2 the addressLine2 to set
     */
    public void setAddressLine2(String addressLine2)
    {
        this.addressLine2 = addressLine2;
    }

    /**
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @struts.validator type = "required" override = "true" msgkey="error.address.city"
     * @struts.validator type = "validCity" msgkey="error.address.city.mask"
     * @struts.validator-args arg0resource = "error.des.request.report.number.three.e"
     */
    public void setCity(String city)
    {
        this.city = city;
    }


    

    
    
//    /**
//     * @return the stateOrProvince
//     */
//    public String getStateOrProvince()
//    {
//        return stateOrProvince;
//    }
//
//    /**
//     * @struts.validator type = "required" override = "true" msgkey="error.required"
//     * @struts.validator-args arg0resource = "error.des.request.report.number.three.f"
//     */
//    public void setStateOrProvince(String stateOrProvince)
//    {
//        this.stateOrProvince = stateOrProvince;
//    }

    /**
     * @return the stateOrProvince
     */
    public String getStateOrProvince()
    {
        return stateOrProvince;
    }

    /**
     * @param stateOrProvince the stateOrProvince to set
     */
    public void setStateOrProvince(String stateOrProvince)
    {
        this.stateOrProvince = stateOrProvince;
    }

    /**
     * @return the stateOrProvinceForOthers
     */
    public String getStateOrProvinceForOthers()
    {
        return stateOrProvinceForOthers;
    }

    /**
     * @param stateOrProvinceForOthers the stateOrProvinceForOthers to set
     */
    public void setStateOrProvinceForOthers(String stateOrProvinceForOthers)
    {
        this.stateOrProvinceForOthers = stateOrProvinceForOthers;
    }

    /**
     * @return the stateOrProvinceForUS
     */
    public String getStateOrProvinceForUS()
    {
        return stateOrProvinceForUS;
    }

    /**
     * @param stateOrProvinceForUS the stateOrProvinceForUS to set
     */
    public void setStateOrProvinceForUS(String stateOrProvinceForUS)
    {
        this.stateOrProvinceForUS = stateOrProvinceForUS;
    }

    /**
     * @return the stateOrProvinceForCA
     */
    public String getStateOrProvinceForCA()
    {
        return stateOrProvinceForCA;
    }

    /**
     * @param stateOrProvinceForCA the stateOrProvinceForCA to set
     */
    public void setStateOrProvinceForCA(String stateOrProvinceForCA)
    {
        this.stateOrProvinceForCA = stateOrProvinceForCA;
    }

    /**
     * @return the country
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * @struts.validator type = "required" override = "true" msgkey="error.required"
     * @struts.validator-args arg0resource = "error.des.request.report.number.three.f"
     */
    public void setCountry(String country)
    {
        this.country = country;
    }

    /**
     * @return the zipcode
     */
    public String getZipcode()
    {
        return zipcode;
    }

    /**
     * @struts.validator type = "required" msgkey="error.required" arg0resource = "error.des.request.report.number.three.h"
     * @struts.validator type = "positiveInteger" msgkey="error.address.zip.invalid" arg0resource = "error.des.request.report.number.three.h"
     * @struts.validator type = "mask" msgkey="error.address.zip.invalid" arg0resource = "error.des.request.report.number.three.h"
     * @struts.validator-var value = "^[0-9]*$" name = "mask"
     */
    public void setZipcode(String zipcode)
    {
        this.zipcode = zipcode;
    }

    /**
     * @return the contactPerson
     */
    public String getContactPerson()
    {
        return contactPerson;
    }

    /**
     * @param contactPerson the contactPerson to set
     */
    public void setContactPerson(String contactPerson)
    {
        this.contactPerson = contactPerson;
    }

    /**
     * @return the deadlineDate
     */
    public CalendarBean getDeadlineDate()
    {
        return deadlineDate;
    }

    /**
     * @struts.validator type = "calDate" override = "true" msgkey="error.access.format.invalid" arg0resource = "error.des.request.report.number.four.b" arg1resource = "access.date.format"
     * @struts.validator-var value = "MM/dd/yyyy" name ="datePattern"
     * @struts.validator type = "currentOrFutureCalDate" override = "true" msgkey = "error.access.caldate.question.notcurrentorfuture" arg0resource = "error.des.request.report.number.four.b"
     */
    public void setDeadlineDate(CalendarBean deadlineDate)
    {
        this.deadlineDate = deadlineDate;
    }

    /**
     * @return the telephoneNumber
     */
    public PhoneBean getTelephoneNumber()
    {
        return telephoneNumber;
    }

    /**
     * @struts.validator type = "requiredPhone" override="true" msgkey="error.required"
     * @struts.validator type = "phone" override = "true" msgkey="errors.phone"
     * @struts.validator-args arg0resource = "error.des.request.report.number.four.c"
     */
    public void setTelephoneNumber(PhoneBean telephoneNumber)
    {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * @return the fax
     */
    public PhoneBean getFax()
    {
        return fax;
    }

    /**
     * @struts.validator type = "phone" override = "true" msgkey="error.fax"
     * @struts.validator-args arg0resource = "error.des.request.report.number.four.d"
     */
    public void setFax(PhoneBean fax)
    {
        this.fax = fax;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @struts.validator type = "email" override = "true" msgkey = "errors.email"
     * @struts.validator-args arg0resource = "error.des.request.report.number.four.e"
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return the claimantName
     */
    public String getClaimantName()
    {
        return claimantName;
    }

    /**
     * @param claimantName the claimantName to set
     */
    public void setClaimantName(String claimantName)
    {
        this.claimantName = claimantName;
    }

    /**
     * @return the claimantSsn
     */
    public SsnBean getClaimantSsn()
    {
        return claimantSsn;
    }

    /**
     * @struts.validator type = "ssn" msgkey="errors.ssn" override = "true"
     * @struts.validator-args arg0resource = "error.des.request.report.number.six"
     */
    public void setClaimantSsn(SsnBean claimantSsn)
    {
        this.claimantSsn = claimantSsn;
    }

    /**
     * @return the employerName
     */
    public String getEmployerName()
    {
        return employerName;
    }

    /**
     * @param employerName the employerName to set
     */
    public void setEmployerName(String employerName)
    {
        this.employerName = employerName;
    }

    /**
     * @return the ean
     */
    public EanBean getEan()
    {
        return ean;
    }

    /**
     * @struts.validator type = "ean" msgkey="errors.ean" override = "true"
     * @struts.validator-args arg0resource = "error.des.request.report.number.eight"
     */
    public void setEan(EanBean ean)
    {
        this.ean = ean;
    }

    /**
     * @return the reportRequest
     */
    public String getReportRequest()
    {
        return reportRequest;
    }

    /**
     * @struts.validator type = "required" override = "true" msgkey="error.required"
     * @struts.validator type = "maxlength" msgkey="error.maxlength.500"
     * @struts.validator-var name="maxlength" value="500"
     * @struts.validator-args arg0resource = "error.des.request.report.number.nine"
     */
    public void setReportRequest(String reportRequest)
    {
        this.reportRequest = reportRequest;
    }
    
    

    public FormFile getAttachmentOne()
    {
        return attachmentOne;
    }

    public void setAttachmentOne(FormFile attachmentOne)
    {
        this.attachmentOne = attachmentOne;
    }

    public FormFile getAttachmentTwo()
    {
        return attachmentTwo;
    }

    public void setAttachmentTwo(FormFile attachmentTwo)
    {
        this.attachmentTwo = attachmentTwo;
    }

    public FormFile getAttachmentThree()
    {
        return attachmentThree;
    }

    public void setAttachmentThree(FormFile attachmentThree)
    {
        this.attachmentThree = attachmentThree;
    }

    public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {     
        ActionErrors errors = super.validate(arg0, arg1);               
        if (errors.size() > 0) {
            return errors;
        }
        if(MstCountryEnum.US.getName().equals(this.country)){
            if(StringUtils.isNotBlank(this.stateOrProvinceForUS)){
                this.stateOrProvince=this.stateOrProvinceForUS;
            }else{
                errors.add("stateOrProvince",new ActionMessage("error.required","3.g"));
            }
            this.stateOrProvinceForCA=GlobalConstants.EMPTY_STRING;
            this.stateOrProvinceForOthers=GlobalConstants.EMPTY_STRING; 
        }else if(MstCountryEnum.CA.getName().equals(this.country)){
            if(StringUtils.isNotBlank(this.stateOrProvinceForCA)){
                this.stateOrProvince=this.stateOrProvinceForCA;
            }else{
                errors.add("stateOrProvince",new ActionMessage("error.required","3.g"));
            }
            this.stateOrProvinceForUS=GlobalConstants.EMPTY_STRING;
            this.stateOrProvinceForOthers=GlobalConstants.EMPTY_STRING;
        }else{
            if(StringUtils.isNotBlank(this.stateOrProvinceForOthers)){
                this.stateOrProvince=this.stateOrProvinceForOthers;
            }else{
                errors.add("stateOrProvince",new ActionMessage("error.required","3.g"));
            }
            this.stateOrProvinceForUS=GlobalConstants.EMPTY_STRING;
            this.stateOrProvinceForCA=GlobalConstants.EMPTY_STRING; 
        }
//        access.des.request.report.desupload.document.incorrect.file.size.error = File size is incorrect for Attachment. File size should be less than 5MB.
//                access.des.request.report.desupload.document.total.file.size.error= Total size of fils should not exceed 5MB
//                error.des.request.report.upload.document.incorrect.extension = File extension is incorrect for Attachment. Files with extension .pdf, .txt, .doc, .docx, .xls, .xlsx can be uploaded.

        int totalFileSize=0;
        if(this.attachmentOne != null && !GlobalConstants.EMPTY_STRING.equals(this.attachmentOne.getFileName()))
        {
            if(!StringUtility.isValuePresent(GlobalConstants.UPLOAD_DOCUMENTS_DES_REPORT_REQUEST_SUPPORTED_FILE_FORMATS,
                    StringUtility.getFileExtension(this.attachmentOne.getFileName())))
            {
                errors.add("attachmentOne",new ActionMessage("error.des.request.report.upload.document.incorrect.extension","1"));
            }
            int fileSize = this.attachmentOne.getFileSize();
            totalFileSize = totalFileSize + fileSize;
            if(fileSize == 0 || fileSize > GlobalConstants.UPLOAD_DOCUMENTS_DES_REPORT_REQUEST_ATTACHMENT_LIMIT)
            {
                errors.add("attachmentOne",new ActionMessage("access.des.request.report.desupload.document.incorrect.file.size.error","1"));
            }
        }
        if(this.attachmentTwo != null && !GlobalConstants.EMPTY_STRING.equals(this.attachmentTwo.getFileName()))
        {
            if(!StringUtility.isValuePresent(GlobalConstants.UPLOAD_DOCUMENTS_DES_REPORT_REQUEST_SUPPORTED_FILE_FORMATS, 
                    StringUtility.getFileExtension(this.attachmentTwo.getFileName())))
            {
                errors.add("attachmentTwo",new ActionMessage("error.des.request.report.upload.document.incorrect.extension","2"));
            }
            int fileSize = this.attachmentTwo.getFileSize();
            totalFileSize = totalFileSize + fileSize;
            if(fileSize == 0 || fileSize > GlobalConstants.UPLOAD_DOCUMENTS_DES_REPORT_REQUEST_ATTACHMENT_LIMIT)
            {
                errors.add("attachmentTwo",new ActionMessage("access.des.request.report.desupload.document.incorrect.file.size.error","2"));
            }           
            
        }
        if(this.attachmentThree != null && !GlobalConstants.EMPTY_STRING.equals(this.attachmentThree.getFileName()))
        {
            if(!StringUtility.isValuePresent(GlobalConstants.UPLOAD_DOCUMENTS_DES_REPORT_REQUEST_SUPPORTED_FILE_FORMATS, 
                    StringUtility.getFileExtension(this.attachmentThree.getFileName())))
            {
                errors.add("attachmentThree",new ActionMessage("error.des.request.report.upload.document.incorrect.extension","3"));
            }
            int fileSize = this.attachmentThree.getFileSize();
            totalFileSize = totalFileSize + fileSize;
            if(fileSize == 0 || fileSize > GlobalConstants.UPLOAD_DOCUMENTS_DES_REPORT_REQUEST_ATTACHMENT_LIMIT)
            {
                errors.add("attachmentThree",new ActionMessage("access.des.request.report.desupload.document.incorrect.file.size.error","3"));
            }
        }
        if(totalFileSize >GlobalConstants.UPLOAD_DOCUMENTS_DES_REPORT_REQUEST_ATTACHMENT_LIMIT)
        {
            errors.add(GlobalConstants.EMPTY_STRING,new ActionMessage("access.des.request.report.desupload.document.total.file.size.error"));
        }
        
        return errors;
    }
    
    /*CIF_INT_02610 Start*/
	@Override
	public void validateFormFields(ActionMapping mapping,HttpServletRequest request) throws Exception {
		
		//Country
		FormFieldValidateUtility.validateDropDownFld(this.country, "T_MST_COUNTRY", "key",false);
		if("US".equals(this.country)){
			if(StringUtility.isNotBlank(this.stateOrProvinceForUS)){
				FormFieldValidateUtility.validateDropDownFld(this.stateOrProvinceForUS, "T_MST_STATE", "key",false);
			}else{
				if(StringUtility.isNotBlank(this.stateOrProvince)){
			FormFieldValidateUtility.validateDropDownFld(this.stateOrProvince, "T_MST_STATE", "key",false);
		}
			}
		}
		if("CA".equals(this.country)){
			if(StringUtility.isNotBlank(this.stateOrProvinceForCA)){
				FormFieldValidateUtility.validateDropDownFld(this.stateOrProvinceForCA, "T_MST_CANADIAN_PROVINCE", "key",false);
			}else{
				if(StringUtility.isNotBlank(this.stateOrProvince)){
					FormFieldValidateUtility.validateDropDownFld(this.stateOrProvince, "T_MST_STATE", "key",false);
				}
			}
		}
	}
     
}