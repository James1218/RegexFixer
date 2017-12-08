package gov.state.uim.users.struts;

import org.apache.commons.fileupload.FileItem;

import java.util.List;

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
    private FileItem attachmentOne;
    private FileItem attachmentTwo;
    private FileItem attachmentThree;

	/**
	 * @return the attachmentOne
	 */
	public FileItem getAttachmentOne() {
		return attachmentOne;
	}

	/**
	 * @param attachmentOne the attachmentOne to set
	 */
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