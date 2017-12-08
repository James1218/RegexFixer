package gov.state.uim.app.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.StringUtility;

/**
 * @struts.form 
 * 		name = "taxattachuploaddocumentintodmsform"
 */

public class AttachUploadDocumentIntoDmsForm extends BaseValidatorForm {


	private static final long serialVersionUID = -146457594528619L;
	
	private String correspondenceName;
	private String corrId;
	private FormFile fileName ;
	
	
	public AttachUploadDocumentIntoDmsForm(){
	       super();
	}
	
	public String getCorrespondenceName() {
		return correspondenceName;
	}
	public void setCorrespondenceName(String correspondenceName) {
		this.correspondenceName = correspondenceName;
	}
	public FormFile getFileName() {
		return fileName;
	}
	public void setFileName(FormFile fileName) {
		this.fileName = fileName;
	}
	
	public String getCorrId() {
		return corrId;
	}

	public void setCorrId(String corrId) {
		this.corrId = corrId;
	}


   public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1){
		
		ActionErrors errors = null;
		errors = super.validate(arg0, arg1);
		
		if(errors.size() > 0){
			
		    return errors;
		}
		
		 if((this.corrId == null) || (this.corrId.isEmpty())){
			
			  errors.add("corrId", new ActionMessage("error.required", this.getApplicationMessages(arg1,"access.attach.correspondence.into.dms.correspondence.name")));	   
		  }
		
		 
		 int fileSize = this.fileName.getFileSize();
		 
		if((this.fileName == null) || (this.fileName.getFileName().equals(""))){
			
		  // errors.add("fileName", new ActionMessage("error.required", this.getApplicationMessages(arg1,"error.access.app.upload.Document.into.DMS.uploadfile")));
		   errors.add("fileName",new ActionMessage("error.access.app.upload.Document.into.DMS.uploadfile"));
		   
		}else if(!StringUtility.isValuePresent(GlobalConstants.UPLOAD_DOCUMENTS_DMS_SUPPORTED_FILE_FORMATS,StringUtility.getFileExtension(this.fileName.getFileName())))
		{
			errors.add("fileName",new ActionMessage("error.access.upload.Document.into.DMS.incorrect.extension"));
			
		}else if(fileSize == 0 || fileSize > GlobalConstants.UPLOAD_DOCUMENTS_DMS_LIMIT)
		  {
			 errors.add("fileName",new ActionMessage("error.access.upload.Document.into.DMS.incorrect.file.size"));
		  }

	  return errors;
	}
}