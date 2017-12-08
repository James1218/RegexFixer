package gov.state.uim.sreg.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.domain.bean.ProtestBenefitChargesBean;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.StringUtility;


/**
*
* @struts.form name="uploademployerdocumentsform"
*/
public class UploadEmployerDocumentsForm extends BaseValidatorForm {
	
	//CIF_P2_00034:Employer Establishment_Upload Documents
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormFile employerUploadfile;

    // CIF_INT_00296 || Defect_7302 Start
    private String eanBean = new String();
    private String quarterYearBean = new String();
    private String ssnBean = new String();
    private String remarksBean = new String();
    private String isFlowFromProtestBenefit = "N";

    public String getEanBean()
    {
        return eanBean;
    }

    public void setEanBean(String eanBean)
    {
        this.eanBean = eanBean;
    }

    public String getQuarterYearBean()
    {
        return quarterYearBean;
    }

    public void setQuarterYearBean(String quarterYearBean)
    {
        this.quarterYearBean = quarterYearBean;
    }

    public String getSsnBean()
    {
        return ssnBean;
    }

    public void setSsnBean(String ssnBean)
    {
        this.ssnBean = ssnBean;
    }

    public String getRemarksBean()
    {
        return remarksBean;
    }

    public void setRemarksBean(String remarksBean)
    {
        this.remarksBean = remarksBean;
    }

    public String getIsFlowFromProtestBenefit()
    {
        return isFlowFromProtestBenefit;
    }

    public void setIsFlowFromProtestBenefit(String isFlowFromProtestBenefit)
    {
        this.isFlowFromProtestBenefit = isFlowFromProtestBenefit;
    }

    // CIF_INT_00296 || Defect_7302 End

    public FormFile getEmployerUploadfile()
    {
		return employerUploadfile;
	}
	public void setEmployerUploadfile(FormFile employerUploadfile) {
		this.employerUploadfile = employerUploadfile;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);		
		//CIF_P2_00949 Start
		if(super.isSameSubmitMethod(request,mapping,"access.document.upload")){
			 IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request);
			 if (null != objAssembly.getData("PROTEST_CHARGES_FLOW") && 
					 StringUtility.equalsIgnoreCase(String.valueOf(objAssembly.getData("PROTEST_CHARGES_FLOW")), "PROTEST_CHARGES_FLOW"))
			 {
				 ProtestBenefitChargesBean bean = objAssembly.getFirstBean(ProtestBenefitChargesBean.class);
				 if (null != bean)
				 {
					 this.setEanBean(bean.getEan());
					 this.setQuarterYearBean(bean.getQtrYearBean().getQuarter() + "/" + bean.getQtrYearBean().getYear());
					 this.setRemarksBean(bean.getRemarks());
					 this.setSsnBean(bean.getSsn());
				 }
				 this.setIsFlowFromProtestBenefit("Y");
			 }
			if(StringUtility.isEmpty(this.employerUploadfile.getFileName())) {
				errors.add("employerUploadfile", new ActionMessage("access.tax.upload.file.request"));			
			}
			
			if (this.employerUploadfile != null
					&& !this.employerUploadfile.getFileName().equals("")) {
				String contentType = this.employerUploadfile.getContentType();

				if (StringUtility.isNotBlank(contentType)
						&& StringUtility.equalsIgnoreCase(contentType,
								"application/msword")) {
					errors.add("employerUploadfile", new ActionMessage(
							"error.access.upload.incorrect.extension"));
				}else{
					if(!StringUtility.isValuePresent(GlobalConstants.QUICK_ACCESS_SUPPORTED_FILE_FORMATS,
							StringUtility.getFileExtension(this.employerUploadfile.getFileName()))){
						errors.add("employerUploadfile",new ActionMessage("error.access.upload.incorrect.extension"));
					}
				}
				int fileSize = this.employerUploadfile.getFileSize();
				if (fileSize == 0
						|| fileSize > GlobalConstants.QUICK_RESPONSE_ATTACHMENT_LIMIT) {
					errors.add("employerUploadfile", new ActionMessage(
							"error.access.upload.incorrect.file.size"));
				}
			}
		}
		//CIF_P2_00949 End
				
		if (errors.size() > 0) {
			return errors;
		}
		return errors;
	}



}