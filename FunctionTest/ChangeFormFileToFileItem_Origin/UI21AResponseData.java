package gov.state.uim.domain.data;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

import gov.state.uim.framework.domain.data.CustomBasePersistent;
import gov.state.uim.util.lang.EqualsBuild;
import gov.state.uim.util.lang.HashCodeBuild;
import gov.state.uim.util.lang.StringUtility;

/**
 * This class is Data Object that provides Hibernate mapping and methods 
 * related to T_UI21A_RESPONSE table.
 * 
 * @hibernate.class table="T_UI21A_RESPONSE"
 * 
 * 
 * @hibernate.query
 * 		query = "from UI21AResponseData ui21AResponseData inner join fetch ui21AResponseData.sidesUI21AResponseData inner join fetch ui21AResponseData.claimData.claimantData claimantData where claimantData.ssn=:ssn"
 * 		name = "getUI21AResponseForSidesBySsn" 
 * 
 * @hibernate.query
 * 		query = "from UI21AResponseData ui21AResponseData inner join fetch ui21AResponseData.sidesUI21AResponseData sidesUI21AResponseData left join fetch sidesUI21AResponseData.sidesResponseDetailsData left join fetch sidesUI21AResponseData.sidesUI21AResponseDumpData dumpData left join fetch dumpData.sidesUI21AResponseDumpAttachmentSet dumpAttachData where ui21AResponseData.ui21aResponseId=:ui21aResponseId"
 * 		name = "getUi21AResponseDataForSidesById"      
 * 
 *  @hibernate.query
 * 		query = "from UI21AResponseData data where data.claimData.claimId = :claimId and data.uiAccountNumber = :employerEan"
 * 		name = "getUi21AResponseDataByClaimandEAN"
 * 
 *  CIF_01960
 *  @hibernate.query
 * 		query = "from UI21AResponseData data where data.claimData.claimId = :claimId order by data.responseReceivedDate desc"
 * 		name = "getUi21AResponseDataByClaim"
 */

public class UI21AResponseData extends CustomBasePersistent{

	private static final long serialVersionUID = 700502552058774977L;
	
	private Long ui21aResponseId;
	private ClaimData claimData;
	private CorrespondenceData corrData;
	private Date responseReceivedDate;
	private Date dateOfSeparation;
	private String reasonForSeparation;
	private String commentFlag;
	private String timelyFlag;
	private String employerName;
	private Date corrSentDate;
	private BigDecimal eightTimes; 
	
	private Date employmentStartDate;
	private Date employmentEndDate;
	private BigDecimal grossEarnings;
	private String uiAccountNumber;
	private String receivedPension;
	private String workRefused;
	private Date refusedDate;
	private String referredName;
	private String referredTitle;
	private String referredPhone;
	private Date referredDate;
	private String dischargeReason;
	private InputStream attachmentOne;
	private InputStream attachmentTwo;
	private InputStream attachmentThree;
	private String attachmentFileNameOne;
	private String attachmentFileNameTwo;
	private String attachmentFileNameThree;
	private SidesUI21AResponseData sidesUI21AResponseData;
	
	/**
	 * @hibernate.id 
	 * column = "UI21A_RESPONSE_ID" 
	 * generator-class = "sequence"
	 * type = "long"
	 * @hibernate.generator-param 
	 * value = "SEQ_T_UI21A_RESPONSE" 
	 * name = "sequence"
	 */	
	public Long getUi21aResponseId() {
		return ui21aResponseId;
	}
	public void setUi21aResponseId(Long ui21aResponseId) {
		this.ui21aResponseId = ui21aResponseId;
	}
	
	/**
	 *@hibernate.many-to-one
	 *	column = "CLAIM_ID"
	 *	not-null = "true"
	 * 	class = "gov.state.uim.domain.data.ClaimData"
	 *@return
	 */	
	public ClaimData getClaimData() {
		return claimData;
	}
	public void setClaimData(ClaimData claimData) {
		this.claimData = claimData;
	}
	
	/**
	 *@hibernate.many-to-one
	 *	column = "CORRESPONDENCE_ID"
	 *	not-null = "false"
	 * 	class = "gov.state.uim.domain.data.CorrespondenceData"
	 *@return
	 */
	public CorrespondenceData getCorrData() {
		return corrData;
	}	//1.1.7 || CIF_INT_04097  || Defect_190 || Process employers response from legacy
	public void setCorrData(CorrespondenceData corrData) {
		this.corrData = corrData;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "true" 
	 * 		type = "date"
	 * 		column = "RESPONSE_RECEIVED_DATE"
	 */	
	public Date getResponseReceivedDate() {
		return responseReceivedDate;
	}
	public void setResponseReceivedDate(Date responseReceivedDate) {
		this.responseReceivedDate = responseReceivedDate;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		type = "date"
	 * 		column = "DATE_OF_SEPARATION"
	 */		
	public Date getDateOfSeparation() {
		return dateOfSeparation;
	}
	public void setDateOfSeparation(Date dateOfSeparation) {
		this.dateOfSeparation = dateOfSeparation;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "true" 
	 * 		column = "REASON_FOR_SEPARATION"
	 * 		type = "string"
	 */
	public String getReasonForSeparation() {
		return reasonForSeparation;
	}
	public void setReasonForSeparation(String reasonForSeparation) {
		this.reasonForSeparation = reasonForSeparation;
	}	
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "COMMENT_FLAG"
	 * 		type = "string"
	 */
	public String getCommentFlag() {
		return commentFlag;
	}
	public void setCommentFlag(String commentFlag) {
		this.commentFlag = commentFlag;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "true" 
	 * 		column = "TIMELY_FLAG"
	 * 		type = "string"
	 */	
	public String getTimelyFlag() {
		return timelyFlag;
	}
	public void setTimelyFlag(String timelyFlag) {
		this.timelyFlag = timelyFlag;
	}	
	
	/**
	 * @hibernate.property 
	 * 		not-null = "true" 
	 * 		type = "date"
	 * 		column = "CORRESPONDENCE_SENT_DATE"
	 */	
	public Date getCorrSentDate() {
		return corrSentDate;
	}
	public void setCorrSentDate(Date corrSentDate) {
		this.corrSentDate = corrSentDate;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "true" 
	 * 		column = "EMPLOYER_NAME"
	 * 		type = "string"
	 */	
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) 
	{
		if (StringUtility.isNotBlank(employerName) && employerName.length() > 60)
		{
			employerName = employerName.substring(0,60);
		}
		this.employerName = employerName;
	}
	
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "DISCHARGE_REASON"
	 * 		type = "string"
	 */	
	public String getDischargeReason() {
		return dischargeReason;
	}
	public void setDischargeReason(String dischargeReason) {
		this.dischargeReason = dischargeReason;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "EMPLOYMENT_END_DATE"
	 * 		type = "date"
	 */	
	
	public Date getEmploymentEndDate() {
		return employmentEndDate;
	}
	public void setEmploymentEndDate(Date employmentEndDate) {
		this.employmentEndDate = employmentEndDate;
	}
	
	public BigDecimal getEightTimes() {
		return eightTimes;
	}
	public void setEightTimes(BigDecimal eightTimes) {
		this.eightTimes = eightTimes;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "EMPLOYMENT_START_DATE"
	 * 		type = "date"
	 */	
	public Date getEmploymentStartDate() {
		return employmentStartDate;
	}
	public void setEmploymentStartDate(Date employmentStartDate) {
		this.employmentStartDate = employmentStartDate;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "GROSS_EARNINGS"
	 * 		type = "big_decimal"
	 */
	public BigDecimal getGrossEarnings() {
		return grossEarnings;
	}
	public void setGrossEarnings(BigDecimal grossEarnings) {
		this.grossEarnings = grossEarnings;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "RECEIVED_PENSION"
	 * 		type = "string"
	 */
	public String getReceivedPension() {
		return receivedPension;
	}
	public void setReceivedPension(String receivedPension) {
		this.receivedPension = receivedPension;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "REFERRED_DATE"
	 * 		type = "date"
	 */
	public Date getReferredDate() {
		return referredDate;
	}
	public void setReferredDate(Date referredDate) {
		this.referredDate = referredDate;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "REFERRED_NAME"
	 * 		type = "string"
	 */
	public String getReferredName() {
		return referredName;
	}
	public void setReferredName(String referredName) {
		this.referredName = referredName;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "REFERRED_PHONE"
	 * 		type = "string"
	 */
	public String getReferredPhone() {
		return referredPhone;
	}
	public void setReferredPhone(String referredPhone) {
		this.referredPhone = referredPhone;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "REFERRED_TITLE"
	 * 		type = "string"
	 */
	public String getReferredTitle() {
		return referredTitle;
	}
	public void setReferredTitle(String referredTitle) {
		this.referredTitle = referredTitle;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "REFUSED_DATE"
	 * 		type = "date"
	 */	
	public Date getRefusedDate() {
		return refusedDate;
	}
	public void setRefusedDate(Date refusedDate) {
		this.refusedDate = refusedDate;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "UI_ACCOUNT_NUMBER"
	 * 		type = "string"
	 */
	public String getUiAccountNumber() {
		return uiAccountNumber;
	}
	public void setUiAccountNumber(String uiAccountNumber) {
		this.uiAccountNumber = uiAccountNumber;
	}
	
	/**
	 * @hibernate.property 
	 * 		not-null = "false" 
	 * 		column = "WORK_REFUSED"
	 * 		type = "string"
	 */	
	public String getWorkRefused() {
		return workRefused;
	}
	public void setWorkRefused(String workRefused) {
		this.workRefused = workRefused;
	}
	

	public boolean equals(Object other) {
        if (!(other instanceof UI21AResponseData)) {
            return false;
        }
        UI21AResponseData castOther = (UI21AResponseData) other;
        EqualsBuild equalsBuilder = new EqualsBuild();

        if ((this.ui21aResponseId == null) ||
                (castOther.ui21aResponseId == null)) {
            equalsBuilder.append(this.claimData, castOther.claimData);
        } else {
            equalsBuilder.append(this.ui21aResponseId, castOther.getUi21aResponseId());
        }
        return equalsBuilder.isEquals();
    }

    public int hashCode() {
        HashCodeBuild hashCodeBuilder = new HashCodeBuild();

        if (this.getUi21aResponseId() == null) {
        	hashCodeBuilder.append(this.getClaimData());
        } else {
            hashCodeBuilder.append(this.ui21aResponseId);
        }
        return hashCodeBuilder.toHashCode();
    }
	/*public FormFile getAttachmentOne() {
		return attachmentOne;
	}
	public void setAttachmentOne(FormFile attachmentOne) {
		this.attachmentOne = attachmentOne;
	}
	public FormFile getAttachmentThree() {
		return attachmentThree;
	}
	public void setAttachmentThree(FormFile attachmentThree) {
		this.attachmentThree = attachmentThree;
	}
	public FormFile getAttachmentTwo() {
		return attachmentTwo;
	}
	public void setAttachmentTwo(FormFile attachmentTwo) {
		this.attachmentTwo = attachmentTwo;
	}*/
	public InputStream getAttachmentOne() {
		return attachmentOne;
	}
	public void setAttachmentOne(InputStream attachmentOne) {
		this.attachmentOne = attachmentOne;
	}
	public InputStream getAttachmentThree() {
		return attachmentThree;
	}
	public void setAttachmentThree(InputStream attachmentThree) {
		this.attachmentThree = attachmentThree;
	}
	public InputStream getAttachmentTwo() {
		return attachmentTwo;
	}
	public void setAttachmentTwo(InputStream attachmentTwo) {
		this.attachmentTwo = attachmentTwo;
	}
	public String getAttachmentFileNameOne() {
		return attachmentFileNameOne;
	}
	public void setAttachmentFileNameOne(String attachmentFileNameOne) {
		this.attachmentFileNameOne = attachmentFileNameOne;
	}
	public String getAttachmentFileNameThree() {
		return attachmentFileNameThree;
	}
	public void setAttachmentFileNameThree(String attachmentFileNameThree) {
		this.attachmentFileNameThree = attachmentFileNameThree;
	}
	public String getAttachmentFileNameTwo() {
		return attachmentFileNameTwo;
	}
	public void setAttachmentFileNameTwo(String attachmentFileNameTwo) {
		this.attachmentFileNameTwo = attachmentFileNameTwo;
	}
	
	 	
	/**
	 *@hibernate.one-to-one
	 * property-ref = "ui21AData"
	 * cascade = "all"
	 * @return
	 */
	public SidesUI21AResponseData getSidesUI21AResponseData() {
		return sidesUI21AResponseData;
	}
	
	/**
	 * @param sidesUI21AResponseData The SidesUI21AResponseData to set.
	 */
	public void setSidesUI21AResponseData(SidesUI21AResponseData sidesUI21AResponseData) {
		this.sidesUI21AResponseData = sidesUI21AResponseData;
	}
}