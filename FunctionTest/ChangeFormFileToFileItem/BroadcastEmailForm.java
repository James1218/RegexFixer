/**
 * 
 */
package gov.state.uim.email.struts;

import org.apache.commons.fileupload.FileItem;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.StringUtility;

/**
 * @author TCS
 *
 * @struts.form name ="broadcastemailform"
 */
public class BroadcastEmailForm extends BaseValidatorForm {
	
	private static final long serialVersionUID = -3936463921160077472L;
	
	private String broadcastGroup;
	private String broadcastSubject;
	private String broadcastMessage;
	private String broadcastEmailAddress;
	private FileItem braodcastAttachment;
	private String broadcastAttachmentName;
	
	/**
	 * @return Returns the broadcastAttachmentName.
	 */
	public String getBroadcastAttachmentName() {
		return broadcastAttachmentName;
	}
	/**
	 * @param broadcastAttachmentName The broadcastAttachmentName to set.
	 */
	public void setBroadcastAttachmentName(String broadcastAttachmentName) {
		this.broadcastAttachmentName = broadcastAttachmentName;
	}
	/**
	 * @return Returns the broadcastGroup.
	 */
	public String getBroadcastGroup() {
		return broadcastGroup;
	}
	/**
	 * @param broadcastGroup The broadcastGroup to set.
	 */
	public void setBroadcastGroup(String broadcastGroup) {
		this.broadcastGroup = broadcastGroup;
	}
	/**
	 * @return Returns the broadcastSubject.
	 */
	public String getBroadcastSubject() {
		return broadcastSubject;
	}
	/**
	 * @struts.validator
	 *  	type = "required"
	 *  	override = "true"
	 *  	msgkey="error.required"
	 *  	arg0resource = "access.email.broadcast.broadcastsubject.error"   
	 */
	public void setBroadcastSubject(String broadcastSubject) {
		this.broadcastSubject = broadcastSubject;
	}
	/**
	 * @return Returns the broadcastMessage.
	 */
	public String getBroadcastMessage() {
		return broadcastMessage;
	}
	/**
	 * @struts.validator
	 * 		type = "required"
	 * 		override = "true"
	 * 		msgkey="error.required"
	 * 		arg0resource = "access.email.broadcast.broadcastmessage.error"
	 * 
	 * @struts.validator 
	 * 		type = "maxlength"
	 * 		msgkey="error.maxlength.2500"
	 * 		arg0resource = "access.email.broadcast.broadcastmessage.number"  
	 * @struts.validator-var 
	 * 		name="maxlength" value="2500" 
	 */
	public void setBroadcastMessage(String broadcastMessage) {
		this.broadcastMessage = broadcastMessage;
	}
	/**
	 * @return Returns the broadcastEmailAddress.
	 */
	public String getBroadcastEmailAddress() {
		return broadcastEmailAddress;
	}
	/**
	 * @struts.validator
	 * type = "multipleEmailAddresses"
	 * override = "true"
	 * msgkey="error.email.broadcast.emailadreess.invalid"
	 * arg0resource = "access.email.broadcast.broadcastsubject.error"
	 *  
	 */
	public void setBroadcastEmailAddress(String broadcastEmailAddress) {
		this.broadcastEmailAddress = broadcastEmailAddress;
	}
	/**
	 * @return Returns the braodcastAttachment.
	 */
	public FileItem getBraodcastAttachment() {
		return braodcastAttachment;
	}
	/**
	 * @param braodcastAttachment The braodcastAttachment to set.
	 */
	public void setBraodcastAttachment(FileItem braodcastAttachment) {
		this.braodcastAttachment = braodcastAttachment;
	}
	public void setBraodcastAttachment(Object braodcastAttachment){
		if(braodcastAttachment!=null){
			List<FileItem>fileItems=(List<FileItem>)braodcastAttachment;
			if(fileItems!=null && !fileItems.isEmpty()){
				this.fileName=fileItems.get(0);
			}
		}
	}

	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		
		ActionErrors errors = super.validate(mapping, request);
		
		if(errors.size() > 0){
			return errors;
		}
		
		if(StringUtility.isBlank(this.broadcastGroup) && StringUtility.isBlank(this.broadcastEmailAddress))
		{
			errors.add("broadcastEmailAddress",new ActionMessage("error.email.broadcast.group.email.notpresent"));			
		}
		
		if(this.braodcastAttachment != null && !this.braodcastAttachment.getFileName().equals(""))
		{
			if(!(this.braodcastAttachment.getFileName().endsWith(".txt") ||
					this.braodcastAttachment.getFileName().endsWith(".doc") ||
					this.braodcastAttachment.getFileName().endsWith(".xls") ||
							this.braodcastAttachment.getFileName().endsWith(".pdf")))
			{
				errors.add("braodcastAttachment",new ActionMessage("error.email.broadcast.attachment.file.incorrect.extension"));
			}
			int fileSize = this.braodcastAttachment.getFileSize();
			if(fileSize == 0 || fileSize > 4*1000*1024)
			{
				errors.add("braodcastAttachment",new ActionMessage("error.email.broadcast.attachment.file.size.exceeds"));
			}
		}
		
		return errors;
	}
}