package gov.state.uim.tra.struts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.common.ServiceLocator;
import gov.state.uim.domain.bean.UploadTrainingFacilityBean;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.StringUtility;
/**
 * @struts.form name = "taauploadtrainingfacilityfileform"
 */
public class TAAUploadTrainingFacilityFileForm extends BaseValidatorForm {
private static final Logger LOGGER  = ServiceLocator.instance.getLogger(TAAUploadTrainingFacilityFileForm.class);
	private static final long serialVersionUID = -8770251449274131118L;
	private FormFile fileName;
	private UploadTrainingFacilityBean lObjBean ;
	private List lObjList = new ArrayList();

	
	public FormFile getFileName() {
		return fileName;
	}

	/**   
	 * @struts.validator 
	 * 		type = "required"
	 * 		msgkey="error.required"
	 * 		arg0resource = "access.taatrainingfacility.uploadtrainingfacilityfile.number"
	 */	
	public void setFileName(FormFile fileName) {
		this.fileName = fileName; 
	}
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		
		ActionErrors errors = null;
		
		try{
			errors = super.validate(arg0, arg1);
			
			if(errors.size() > 0){
				return errors;
			} 
			if(this.fileName != null && !this.fileName.getFileName().equals("")){
			
				InputStream lObjInputStream = this.getFileName().getInputStream();
				InputStreamReader lObjIStreamReader = new InputStreamReader(lObjInputStream);
				BufferedReader lObjBufferedReader = new BufferedReader(lObjIStreamReader);
				
				if(lObjBufferedReader.ready()) {
					errors = validateTextFile(lObjBufferedReader,errors);
				} 
			}
			else
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("error.access.taa.uploadtrainingfacilityfile.nofileselected"));
			}
		}
		catch (IOException e) {
		if (LOGGER.isDebugEnabled()) {
		LOGGER.debug(e);
		}
		}
		return errors;
	}
	private ActionErrors validateTextFile(BufferedReader lObjBufferedReader, ActionErrors errors) throws IOException {
		
		boolean isValid = true;
		while(lObjBufferedReader.ready()){

			this.lObjBean = new UploadTrainingFacilityBean();
			String lStrFacilityRecord = lObjBufferedReader.readLine();
			
			if(StringUtility.isBlank(lStrFacilityRecord))
			{
				isValid = false;
				break;
			}

			String lStrFacilityName = lStrFacilityRecord.substring(0,40);
			if(StringUtility.isBlank(lStrFacilityName)&& !GenericValidator.maxLength(lStrFacilityName,40))
			{
				isValid = false;
				break;
			}
			
			String lStrAddressLine1 = lStrFacilityRecord.substring(40,80);
			if(StringUtility.isBlank(lStrAddressLine1) && !GenericValidator.maxLength(lStrAddressLine1,40))
			{
				isValid = false;
				break;
			}
			
			String lStrAddressLine2 = lStrFacilityRecord.substring(80,120);
			if(StringUtility.isBlank(lStrAddressLine2) && !GenericValidator.maxLength(lStrAddressLine2,40)) 
			{
				isValid = false;
			}
			String lStrCity = lStrFacilityRecord.substring(120,137);
			if(StringUtility.isBlank(lStrCity) && !GenericValidator.maxLength(lStrCity,17))
			{
				isValid = false;
				break;
			}
			String lStrState = lStrFacilityRecord.substring(137,139);
			if(StringUtility.isBlank(lStrState) && !GenericValidator.maxLength(lStrState,2))
			{
				isValid = false;
				break;
			}
			String lStrZip = lStrFacilityRecord.substring(139,149);
			if(StringUtility.isBlank(lStrZip) && !GenericValidator.maxLength(lStrZip,10))
			{
				isValid = false;
				break;
			}			
			String lStrCountry = lStrFacilityRecord.substring(149,151);
			if(StringUtility.isBlank(lStrCountry) && !GenericValidator.maxLength(lStrCountry,2))
			{
				isValid = false;
				break;
			}
			String lStrAddressPosalBarCode = lStrFacilityRecord.substring(151,171);
			if(StringUtility.isBlank(lStrAddressPosalBarCode) && !GenericValidator.maxLength(lStrAddressPosalBarCode,20))
			{
				/*
				 * Needs to Be checked once txt file recieved from client
				 */
				isValid = false;
				break;
			}
//			String lStrAddressValidatedFlag= lStrFacilityRecord.substring(183,184);
//			if(StringUtility.isBlank(lStrAddressValidatedFlag))
//			{
////				isValid = false;
////				break;
//			}
//			String lStrLoadedFromMdaFlag = lStrFacilityRecord.substring(185,186);
//			if(StringUtility.isBlank(lStrLoadedFromMdaFlag))
//			{
////				isValid = false;
////				break;
//			}
			
			if(!isValid)
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("error.access.taa.uploadtrainingfacilityfile.invalidfile"));
			}
			else
			{
				this.lObjBean.setFacilityName(lStrFacilityName.trim());
				this.lObjBean.setAddress_line_1(lStrAddressLine1.trim());
				this.lObjBean.setAddress_line_2(lStrAddressLine2);
				this.lObjBean.setAddress_city(lStrCity.trim());
				this.lObjBean.setAddress_state(lStrState.trim());
				this.lObjBean.setAddress_country(lStrCountry.trim());
				this.lObjBean.setAddress_zip(lStrZip.trim());
				this.lObjBean.setAddress_postal_bar_code(lStrAddressPosalBarCode);
				
				this.lObjList.add(this.lObjBean);	
			}
		}
		return errors;
	}

	public List getLObjList() {
		return lObjList;
	}

	public void setLObjList(List objList) {
		this.lObjList = objList;
	}
}