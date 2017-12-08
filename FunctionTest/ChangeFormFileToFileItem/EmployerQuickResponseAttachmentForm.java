package gov.state.uim.nmon.struts;

import org.apache.commons.fileupload.FileItem;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.StringUtility;
/**
 * @struts.form name ="employerquickresponseattachmentform"
 */
public class EmployerQuickResponseAttachmentForm extends BaseValidatorForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7968304881564184523L;
	private String strSsn;
	private String strEan;
	private String  name;
	private String employerName;
	
	private FileItem attachmentOne;
	private FileItem attachmentTwo;
	private FileItem attachmentThree;
	
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {		
		ActionErrors errors = super.validate(arg0, arg1);				
		if (errors.size() > 0) {
			return errors;
		}

		if((this.attachmentOne == null || this.attachmentOne.getFileName().equals("")) && 
				(this.attachmentTwo == null || this.attachmentTwo.getFileName().equals("")) && 
				(this.attachmentThree == null || this.attachmentThree.getFileName().equals("")))
		{
			errors.add("",new ActionMessage("error.access.quick.launch.select.file"));
		}
		//1.0.0.1 || CIF_INT_03926 ||  || ignore case while checking file extensions
		if(this.attachmentOne != null && !this.attachmentOne.getFileName().equals(""))
		{
			if(!StringUtility.isValuePresent(GlobalConstants.QUICK_ACCESS_SUPPORTED_FILE_FORMATS,
					StringUtility.getFileExtension(this.attachmentOne.getFileName()),true))
			{
				errors.add("attachmentOne",new ActionMessage("error.access.quick.launch.incorrect.extension","1"));
			}
			int fileSize = this.attachmentOne.getFileSize();
			if(fileSize > GlobalConstants.QUICK_RESPONSE_ATTACHMENT_LIMIT)
			{
				errors.add("attachmentOne",new ActionMessage("error.access.quick.launch.incorrect.file.size","1"));
			}
		}
		if(this.attachmentTwo != null && !this.attachmentTwo.getFileName().equals(""))
		{
			if(!StringUtility.isValuePresent(GlobalConstants.QUICK_ACCESS_SUPPORTED_FILE_FORMATS, 
					StringUtility.getFileExtension(this.attachmentTwo.getFileName()),true))
			{
				errors.add("attachmentTwo",new ActionMessage("error.access.quick.launch.incorrect.extension","2"));
			}
			int fileSize = this.attachmentTwo.getFileSize();
			if(fileSize > GlobalConstants.QUICK_RESPONSE_ATTACHMENT_LIMIT)
			{
				errors.add("attachmentTwo",new ActionMessage("error.access.quick.launch.incorrect.file.size","2"));
			}
			
			/*if(this.attachmentOne!= null && this.attachmentTwo.getFileName().equals(this.attachmentOne.getFileName()))
			{
				errors.add("attachmentTwo",new ActionMessage("error.access.quick.launch.already.added"));
			}*/
			
		}
		if(this.attachmentThree != null && !this.attachmentThree.getFileName().equals(""))
		{
			if(!StringUtility.isValuePresent(GlobalConstants.QUICK_ACCESS_SUPPORTED_FILE_FORMATS, 
					StringUtility.getFileExtension(this.attachmentThree.getFileName()),true))
			{
				errors.add("attachmentThree",new ActionMessage("error.access.quick.launch.incorrect.extension","3"));
			}
			int fileSize = this.attachmentThree.getFileSize();
			if(fileSize > GlobalConstants.QUICK_RESPONSE_ATTACHMENT_LIMIT)
			{
				errors.add("attachmentThree",new ActionMessage("error.access.quick.launch.incorrect.file.size","3"));
			}
	
		}
		return errors;
	}
	
	
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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



	public String getStrSsn() {
		return strSsn;
	}


	public void setStrSsn(String strSsn) {
		this.strSsn = strSsn;
	}


	public String getStrEan() {
		return strEan;
	}


	public void setStrEan(String strEan) {
		this.strEan = strEan;
	}
	
}