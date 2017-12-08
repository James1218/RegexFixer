package gov.state.uim.audit.struts;

import org.apache.commons.fileupload.FileItem;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.framework.struts.BaseValidatorForm;

/**
 * @struts.form name="audituploadw2form"
 */
public class AuditUploadW2Form extends BaseValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5088293751809169898L;
	private String mdesEan;
	private String employerName;
	private String auditNumber;
	private String auditYear;
	private String fileformat;
	private FileItem filePath;

	public AuditUploadW2Form() {
		super();
	}

	public String getMdesEan() {
		return mdesEan;
	}

	public void setMdesEan(String mdesEan) {
		this.mdesEan = mdesEan;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getAuditNumber() {
		return auditNumber;
	}

	public void setAuditNumber(String auditNumber) {
		this.auditNumber = auditNumber;
	}

	public String getAuditYear() {
		return auditYear;
	}

	public void setAuditYear(String auditYear) {
		this.auditYear = auditYear;
	}

	public String getFileformat() {
		return fileformat;
	}

	/**
	 * @struts.validator type = "required" msgkey="error.required"
	 * 
	 * @struts.validator-args arg0resource =
	 *                        "access.audit.auditDetails.uploadw2s.fileformat.number"
	 */
	public void setFileformat(String fileformat) {
		this.fileformat = fileformat;
	}

	public FileItem getFilePath() {
		return filePath;
	}

	public void setFilePath(FileItem filePath) {
		this.filePath = filePath;
	}
	public void setFilePath(Object filePath){
		if(filePath!=null){
			List<FileItem>fileItems=(List<FileItem>)filePath;
			if(fileItems!=null && !fileItems.isEmpty()){
				this.fileName=fileItems.get(0);
			}
		}
	}


	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);

		String fileName = filePath.getFileName();
		if (!(fileName.endsWith("xls") || fileName.endsWith("XLS"))) {
			errors.add("fileuploaded", new ActionMessage(
					"error.audit.audituploadw2.fileformat"));
		}

		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}
}