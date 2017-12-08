package gov.state.uim.users.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.StringUtility;

/**
 * @struts.form name ="irsprocessinguploaddocumentform"
 */
public class IrsProcessingUploadDocumentForm extends BaseValidatorForm {
	private static final long serialVersionUID = -1393013163786512710L;
	//CIF_INT_05743 || LRUI-104 || Added  attachmentTwo,attachmentThree
    private FormFile attachmentOne;
    private FormFile attachmentTwo;
    private FormFile attachmentThree;

	/**
	 * @return the attachmentOne
	 */
	public FormFile getAttachmentOne() {
		return attachmentOne;
	}

	/**
	 * @param attachmentOne the attachmentOne to set
	 */
	public void setAttachmentOne(FormFile attachmentOne) {
		this.attachmentOne = attachmentOne;
	}
	
    public FormFile getAttachmentTwo() {
		return attachmentTwo;
	}

	public void setAttachmentTwo(FormFile attachmentTwo) {
		this.attachmentTwo = attachmentTwo;
	}

	public FormFile getAttachmentThree() {
		return attachmentThree;
	}

	public void setAttachmentThree(FormFile attachmentThree) {
		this.attachmentThree = attachmentThree;
	}

	/**
     * Validate Method
     */
    public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {     
        ActionErrors errors = super.validate(arg0, arg1);               
        if (errors.size() > 0) {
            return errors;
        }

        int totalFileSize=0;
        if(StringUtility.isBlank(this.attachmentOne.getFileName()) && StringUtility.isBlank(this.attachmentTwo.getFileName())
        		&& StringUtility.isBlank(this.attachmentThree.getFileName())){
        	errors.add("attachmentOne",new ActionMessage("error.irs.processing.upload.document.no.attachment","1"));
        	 return errors;
        }
        
        if(this.attachmentOne != null && StringUtils.isNotBlank(this.attachmentOne.getFileName()))
        {
            if(!StringUtility.isValuePresent(GlobalConstants.IRS_PROCESSING_UPLOAD_DOCUMENTS_SUPPORTED_FILE_FORMATS,
                    StringUtility.getFileExtension(this.attachmentOne.getFileName())))
            {
                errors.add("attachmentOne",new ActionMessage("error.irs.processing.upload.document.incorrect.extension","1"));
            }
            int fileSize = this.attachmentOne.getFileSize();
            totalFileSize = totalFileSize + fileSize;
            if(fileSize == 0 || fileSize > GlobalConstants.IRS_PROCESSING_UPLOAD_DOCUMENTS_ATTACHMENT_LIMIT)
            {
                errors.add("attachmentOne",new ActionMessage("error.irs.processing.upload.document.incorrect.file.size.error","1"));
            }
        }
        if(this.attachmentTwo != null && StringUtils.isNotBlank(this.attachmentTwo.getFileName()))
        {
            if(!StringUtility.isValuePresent(GlobalConstants.IRS_PROCESSING_UPLOAD_DOCUMENTS_SUPPORTED_FILE_FORMATS,
                    StringUtility.getFileExtension(this.attachmentTwo.getFileName())))
            {
                errors.add("attachmentOne",new ActionMessage("error.irs.processing.upload.document.incorrect.extension","1"));
            }
            int fileSize = this.attachmentTwo.getFileSize();
            totalFileSize = totalFileSize + fileSize;
            if(fileSize == 0 || fileSize > GlobalConstants.IRS_PROCESSING_UPLOAD_DOCUMENTS_ATTACHMENT_LIMIT)
            {
                errors.add("attachmentTwo",new ActionMessage("error.irs.processing.upload.document.incorrect.file.size.error","1"));
            }
        }
        if(this.attachmentThree != null && StringUtils.isNotBlank(this.attachmentThree.getFileName()))
        {
            if(!StringUtility.isValuePresent(GlobalConstants.IRS_PROCESSING_UPLOAD_DOCUMENTS_SUPPORTED_FILE_FORMATS,
                    StringUtility.getFileExtension(this.attachmentThree.getFileName())))
            {
                errors.add("attachmentThree",new ActionMessage("error.irs.processing.upload.document.incorrect.extension","1"));
            }
            int fileSize = this.attachmentThree.getFileSize();
            totalFileSize = totalFileSize + fileSize;
            if(fileSize == 0 || fileSize > GlobalConstants.IRS_PROCESSING_UPLOAD_DOCUMENTS_ATTACHMENT_LIMIT)
            {
                errors.add("attachmentOne",new ActionMessage("error.irs.processing.upload.document.incorrect.file.size.error","1"));
            }
        }
        return errors;
    }
	
	
}