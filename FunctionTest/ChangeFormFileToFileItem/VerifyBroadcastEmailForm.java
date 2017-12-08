/**
 * 
 */
package gov.state.uim.email.struts;

import org.apache.commons.fileupload.FileItem;

import java.util.List;

import org.apache.struts.upload.FormFile;

import gov.state.uim.framework.struts.BaseValidatorForm;

/**
 * @author TCS
 *
 * @struts.form name ="verifybroadcastemailform"
 */
public class VerifyBroadcastEmailForm extends BaseValidatorForm {
	
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
	
	public void setBroadcastSubject(String broadcastSubject) {
		this.broadcastSubject = broadcastSubject;
	}
	/**
	 * @return Returns the broadcastMessage.
	 */
	public String getBroadcastMessage() {
		return broadcastMessage;
	}

	public void setBroadcastMessage(String broadcastMessage) {
		this.broadcastMessage = broadcastMessage;
	}
	/**
	 * @return Returns the broadcastEmailAddress.
	 */
	public String getBroadcastEmailAddress() {
		return broadcastEmailAddress;
	}
		
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

}