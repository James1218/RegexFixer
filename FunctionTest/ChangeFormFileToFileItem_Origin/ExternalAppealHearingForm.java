package gov.state.uim.app.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.framework.struts.BaseValidatorForm;

/**
 * @struts.form name ="externalappealhearingform"
 */
public class ExternalAppealHearingForm extends BaseValidatorForm{
	private static final Logger LOGGER = Logger.getLogger(ExternalAppealHearingForm.class);


	private static final long serialVersionUID = 1152542794055071123L;
	
	private String externalAppealId;
	private String claimantSsn;
    private String claimantName;
    private String employerEan;
	private String employerName;
	private String claimantOrEmployerFlag;
	private String appealDate;
	private String appellant;
	private String externalAgency;
	private String docketNumber;
	private FormFile fileName ;
	
	//CIF_01121
	private String transcriptYesNo;
	
	/**
	 * Default constructor to initialize the form attributes
	 */
	public ExternalAppealHearingForm(){
		super();
		//this.claimantName = new String();
		//this.claimantSsn = new String();
		//this.employerEan = new String();
		//this.employerName = new String();
		//this.externalAppealId = new String();
		//this.claimantOrEmployerFlag = new String();
		//this.appealDate = new String();
		//this.appellant = new String();
		//this.externalAgency = new String();
		//this.docketNumber = new String();
		//this.fileName = new FormFile();
		
	}
    
	
	/**
	 * @return Returns the fileName.
	 */
	public FormFile getFileName() {
		return fileName;
	}


	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(final FormFile fileName) {
		this.fileName = fileName;
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
	 * @return Returns the appellant.
	 */
	public String getAppellant() {
		return appellant;
	}



	/**
	 * @param appellant The appellant to set.
	 */
	public void setAppellant(final String appellant) {
		this.appellant = appellant;
	}



	/**
	 * @return Returns the claimantOrEmployerFlag.
	 */
	public String getClaimantOrEmployerFlag() {
		return claimantOrEmployerFlag;
	}


	/**
	 * @param claimantOrEmployerFlag The claimantOrEmployerFlag to set.
	 */
	public void setClaimantOrEmployerFlag(final String claimantOrEmployerFlag) {
		this.claimantOrEmployerFlag = claimantOrEmployerFlag;
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
	 * @return Returns the externalAppealId.
	 */
	public String getExternalAppealId() {
		return externalAppealId;
	}

	/**
	 * @param externalAppealId The externalAppealId to set.
	 */
	public void setExternalAppealId(final String externalAppealId) {
		this.externalAppealId = externalAppealId;
	}
    
	/**
	 * @return the transcriptYesNo
	 */
	public String getTranscriptYesNo() {
		return transcriptYesNo;
	}

	/**
	 * @struts.validator
	 * 		type = "required"
	 *  	msgkey="error.required"
	 *    
	 * @struts.validator-args
	 *  	arg0resource = "access.app.externalappealhearing.preparetranscript.error"
	 * @param transcriptYesNo the transcriptYesNo to set
	 */
	public void setTranscriptYesNo(final String transcriptYesNo) {
		this.transcriptYesNo = transcriptYesNo;
	}


	@Override
	public ActionErrors validate(final ActionMapping arg0, final HttpServletRequest arg1){

		ActionErrors errors = null;
		errors = super.validate(arg0, arg1);

		/*if(errors.size() > 0){
			return errors;
		}
		 */
		if(errors.isEmpty()){
			if(this.transcriptYesNo.equals(GlobalConstants.DB_ANSWER_YES)){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}


				/*if(this.fileName == null || "".equals(this.fileName.getFileName())){

					 errors.add("fileName", new ActionMessage(
		    			"error.required", this.getApplicationMessages("error.access.app.externalapphearing.uploadfile")));
					errors.add("fileName",new ActionMessage("error.access.app.externalapphearing.uploadfile"));
				}else {
					String uploadFormat = "";
					//String nameOfFileWithoutExtn = "";
					StringTokenizer stoken = new StringTokenizer(this.fileName.getFileName(), ".");
					if (stoken.hasMoreTokens()){
						//nameOfFileWithoutExtn = stoken.nextToken();
						stoken.nextToken();
						uploadFormat = stoken.nextToken();
					}
					if (!GlobalConstants.DOC_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
						errors.add("fileName",new ActionMessage("error.access.app.externalapphearing.uploadfileformat"));
					}
				}*/
			}
		}


		return errors;
	}
	
}