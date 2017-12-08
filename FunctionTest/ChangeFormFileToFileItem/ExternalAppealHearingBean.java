package gov.state.uim.domain.bean;

import org.apache.commons.fileupload.FileItem;

import java.util.List;


import java.io.InputStream;

import org.apache.struts.upload.FormFile;

import gov.state.uim.domain.data.AppealData;
import gov.state.uim.framework.domain.bean.IBeanComponent;

public class ExternalAppealHearingBean implements IBeanComponent{

	
	private static final long serialVersionUID = -5522129082859798277L;

	private String claimantSsn;
	private String claimantName;
	private String employerEan;
	private String employerName;
	private String appealDate;
	private String appeallant;

	private String docketNumber;
	private String externalAgency;
	private AppealData appealData;
	private FileItem avFile;    	
	private InputStream inputStream;
	//EAN or SSN if Opponent exists
	private String opponentId = "";
	private String opponent;
	
	//CIF_01121
	private String transcriptYesNo;
	
	
	
	/**
	 * @return Returns the appealData.
	 */
	public AppealData getAppealData() {
		return appealData;
	}
	/**
	 * @param appealData The appealData to set.
	 */
	public void setAppealData(final AppealData appealData) {
		this.appealData = appealData;
	}
	/**
	 * @return Returns the docketNumber.
	 */
	public String getDocketNumber() {
		return docketNumber;
	}
	/**
	 * @param docketNumber The docketNumber to set.
	 */
	public void setDocketNumber(final String docketNumber) {
		this.docketNumber = docketNumber;
	}
	/**
	 * @return Returns the externalAgency.
	 */
	public String getExternalAgency() {
		return externalAgency;
	}
	/**
	 * @param externalAgency The externalAgency to set.
	 */
	public void setExternalAgency(final String externalAgency) {
		this.externalAgency = externalAgency;
	}
	
	/**
	 * @return Returns the appealDate.
	 */
	public String getAppealDate() {
		return appealDate;
	}
	/**
	 * @param appealDate The appealDate to set.
	 */
	public void setAppealDate(final String appealDate) {
		this.appealDate = appealDate;
	}
	/**
	 * @return Returns the appeallant.
	 */
	public String getAppeallant() {
		return appeallant;
	}
	/**
	 * @param appeallant The appeallant to set.
	 */
	public void setAppeallant(final String appeallant) {
		this.appeallant = appeallant;
	}
	/**
	 * @return Returns the claimantName.
	 */
	public String getClaimantName() {
		return claimantName;
	}
	/**
	 * @param claimantName The claimantName to set.
	 */
	public void setClaimantName(final String claimantName) {
		this.claimantName = claimantName;
	}
	/**
	 * @return Returns the claimantSsn.
	 */
	public String getClaimantSsn() {
		return claimantSsn;
	}
	/**
	 * @param claimantSsn The claimantSsn to set.
	 */
	public void setClaimantSsn(final String claimantSsn) {
		this.claimantSsn = claimantSsn;
	}
	/**
	 * @return Returns the employerEan.
	 */
	public String getEmployerEan() {
		return employerEan;
	}
	/**
	 * @param employerEan The employerEan to set.
	 */
	public void setEmployerEan(final String employerEan) {
		this.employerEan = employerEan;
	}
	/**
	 * @return Returns the employerName.
	 */
	public String getEmployerName() {
		return employerName;
	}
	/**
	 * @param employerName The employerName to set.
	 */
	public void setEmployerName(final String employerName) {
		this.employerName = employerName;
	}
	/**
	 * @return Returns the avFile.
	 */
	public FileItem getAvFile() {
		return avFile;
	}
	/**
	 * @param avFile The avFile to set.
	 */
	public void setAvFile(final FileItem avFile) {
		this.avFile = avFile;
	}
	public void setAvFile(Object avFile){
		if(avFile!=null){
			List<FileItem>fileItems=(List<FileItem>)avFile;
			if(fileItems!=null && !fileItems.isEmpty()){
				this.fileName=fileItems.get(0);
			}
		}
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(final InputStream inputStream) {
		this.inputStream = inputStream;
	}
	/**
	 * @return the opponentId
	 */
	public String getOpponentId() {
		return opponentId;
	}
	/**
	 * @param opponentId the opponentId to set
	 */
	public void setOpponentId(final String opponentId) {
		this.opponentId = opponentId;
	}
	/**
	 * @return the opponent
	 */
	public String getOpponent() {
		return opponent;
	}
	/**
	 * @param opponent the opponent to set
	 */
	public void setOpponent(final String opponent) {
		this.opponent = opponent;
	}
	/**
	 * @return the transcriptYesNo
	 */
	public String getTranscriptYesNo() {
		return transcriptYesNo;
	}
	/**
	 * @param transcriptYesNo the transcriptYesNo to set
	 */
	public void setTranscriptYesNo(final String transcriptYesNo) {
		this.transcriptYesNo = transcriptYesNo;
	}



	
	

}