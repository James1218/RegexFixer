package gov.state.uim.domain.bean;

import java.io.InputStream;

import org.apache.struts.upload.FormFile;

import gov.state.uim.framework.domain.bean.IBeanComponent;

/**
 * 
 */
public class UploadDocumentIntoDmsBean implements IBeanComponent{

	private static final long serialVersionUID = 5495373422256625972L;
	private String ssn;
	private String ean;
	private Long docketNumber;
	private String CorrespondenceName;
	private FormFile fileName;
	private InputStream inputStream;
	private String nodeType;
	private String mimeType; 
	private String correspondenceCode;
		
	
	//private fileForm filename; 
		
	public String getCorrespondenceCode() {
		return correspondenceCode;
	}
	
	public void setCorrespondenceCode(String correspondenceCode) {
		this.correspondenceCode = correspondenceCode;
	}
	
	public String getEan() {
		return ean;
	}
	
	public void setEan(String ean) {
		this.ean = ean;
	}
	
	public String getSsn() {
		return ssn;
	}
	
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	public Long getDocketNumber() {
		return docketNumber;
	}
	public void setDocketNumber(Long docketNumber) {
		this.docketNumber = docketNumber;
	}

	public String getCorrespondenceName() {
		return CorrespondenceName;
	}
	
	public void setCorrespondenceName(String correspondenceName) {
		CorrespondenceName = correspondenceName;
	}
	
	public FormFile getFileName() {
		return fileName;
	}
	
	public void setFileName(FormFile fileName) {
		this.fileName = fileName;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public String getNodeType() {
		return nodeType;
	}
	
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}


}