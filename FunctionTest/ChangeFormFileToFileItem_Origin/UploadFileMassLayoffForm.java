package gov.state.uim.cin.struts;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.Resources;

import gov.state.uim.common.ServiceLocator;
import gov.state.uim.domain.bean.UploadFileMassLayoffBean;
import gov.state.uim.domain.data.MstMsCounty;
import gov.state.uim.domain.data.RegisteredEmployerData;
import gov.state.uim.domain.enums.AlienDocumentTypeEnum;
import gov.state.uim.domain.enums.ClaimantSuffixEnum;
import gov.state.uim.domain.enums.StateEnum;
import gov.state.uim.framework.BenefitsConstants;
import gov.state.uim.framework.cache.CacheUtility;
import gov.state.uim.framework.exception.BaseValidationException;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.DateFormatUtility;
import gov.state.uim.util.lang.DateUtility;
import gov.state.uim.util.lang.ESUtils;
import gov.state.uim.util.lang.FormFieldValidateUtility;
import gov.state.uim.util.lang.ReadExcel;
import gov.state.uim.util.lang.StringUtility;
/**
 * This class is form that corresponds to upload excel of mass laid off employee screen;
 * jsp            : uploadfilemasslayoff.jsp
 * Action         : UploadFileMassLayoffAction.java
 * Form           : UploadFileMassLayoffForm.java
 * tiles-defs.xml : .uploadfilemasslayoff (accessms/WEB-INF/jsp)
 * 
 * @author Tata Consultancy Services
 * 
 * @version 1.0 
 * 
 * 
 * @struts.form name ="uploadfilemasslayoffform"
 */
public class UploadFileMassLayoffForm extends BaseValidatorForm {

	private static final long serialVersionUID = 1L;
	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(UploadFileMassLayoffForm.class);
	private Long employerId;
	private String companyName;	
	private FormFile fileName;
	private Long massLayOffId;
	private final CaseInsensitiveMap countyMap =  new CaseInsensitiveMap();
	private final CaseInsensitiveMap suffixMap = new CaseInsensitiveMap(ClaimantSuffixEnum.getEnumMap());
	private List<UploadFileMassLayoffBean>excelResults = new ArrayList<UploadFileMassLayoffBean>();
	private final CaseInsensitiveMap map = new CaseInsensitiveMap(AlienDocumentTypeEnum.getEnumMap());
	private final HashMap<String, String> errorMap = new HashMap<String, String>();
	
	public static final String[] READ_EXCEL = {BenefitsConstants.SSN,BenefitsConstants.FIRST_NAME,BenefitsConstants.MIDDLE_INITIAL,BenefitsConstants.LAST_NAME,BenefitsConstants.SUFFIX,BenefitsConstants.MAIL_ADDRESS_LINE_1,
		BenefitsConstants.MAIL_ADDRESS_CITY,BenefitsConstants.MAIL_ADDRESS_COUNTY,BenefitsConstants.MAIL_ADDRESS_STATE,BenefitsConstants.MAIL_ADDRESS_ZIP,
		BenefitsConstants.DATE_OF_BIRTH,BenefitsConstants.GENDER,BenefitsConstants.PRIMARY_PHONE,BenefitsConstants.CITIZENSHIP};
	@Override
	public ActionErrors validate(final ActionMapping arg0, final HttpServletRequest arg1) {
		ActionErrors errors = null;

		try{
			errors = super.validate(arg0, arg1);

			if(errors.size() > 0){
				return errors;
			} 
			if(null != fileName  && !"".equals(fileName.getInputStream().toString())){

				InputStream lObjInputStream = this.getFileName().getInputStream();
				errors = validateExcelFile(lObjInputStream, errors,arg1);

			}else{
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadfilemasslayoff.page.noFileSelected"));
			}
		}catch (IOException e) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("error caught while parsing the excel file uploaded "+e);
		}
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadfilemasslayoff.page.noFileSelected"));
		}
		return errors;
	}


	/**
	 * @param stream InputStream
	 * @param errors ActionErrors
	 * @return errors ActionErrors
	 */
	private ActionErrors validateExcelFile(final InputStream stream, final ActionErrors errors,final HttpServletRequest request){
		try {
			
			HSSFWorkbook workbook = new HSSFWorkbook(stream);
			List<ListOrderedMap> result = ReadExcel.readExcel(workbook, READ_EXCEL, 0, 1);
			ListOrderedMap loMap = null;
			UploadFileMassLayoffBean layOffBean = null;
			int loopCount = 1;
			// Validate county
			if(countyMap.isEmpty()){
				List<MstMsCounty> list = CacheUtility.getCacheList("T_MST_COUNTY");
				for (MstMsCounty object : list) {
					countyMap.put(object.getDescription().trim(), object.getKey().trim());
				}
			}
			
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				loMap = (ListOrderedMap) iterator.next();
				
				// validate Row values  and populate if correct then create a bean and  add values else add error message in a hashmap 
				// and add as a action error at the end of file execution
				layOffBean = new UploadFileMassLayoffBean();
				layOffBean.setSsn(null !=  loMap.get(BenefitsConstants.SSN) ? loMap.get(BenefitsConstants.SSN).toString() : null);
				layOffBean.setFirstName(null != loMap.get(BenefitsConstants.FIRST_NAME) ? loMap.get(BenefitsConstants.FIRST_NAME).toString() : null);
				layOffBean.setMiddleInitial(null != loMap.get(BenefitsConstants.MIDDLE_INITIAL) ? loMap.get(BenefitsConstants.MIDDLE_INITIAL).toString() : null);
				layOffBean.setLastName(null != loMap.get(BenefitsConstants.LAST_NAME) ? loMap.get(BenefitsConstants.LAST_NAME).toString() : null);
				layOffBean.setSuffix(null != loMap.get(BenefitsConstants.SUFFIX) ? loMap.get(BenefitsConstants.SUFFIX).toString() : null);
				Date birthDate = null;
				if(null != loMap.get(BenefitsConstants.DATE_OF_BIRTH) && loMap.get(BenefitsConstants.DATE_OF_BIRTH) instanceof Date){
					birthDate = (Date) loMap.get(BenefitsConstants.DATE_OF_BIRTH);
				}else if(null != loMap.get(BenefitsConstants.DATE_OF_BIRTH)  && loMap.get(BenefitsConstants.DATE_OF_BIRTH) instanceof String){
					birthDate = DateFormatUtility.parse((String) loMap.get(BenefitsConstants.DATE_OF_BIRTH));
				}
				layOffBean.setBirthDate(birthDate);
				layOffBean.setGender(null !=  loMap.get(BenefitsConstants.GENDER) ? loMap.get(BenefitsConstants.GENDER).toString(): null);
				layOffBean.setCitizenship(null != loMap.get(BenefitsConstants.CITIZENSHIP) ? loMap.get(BenefitsConstants.CITIZENSHIP).toString() : null);
				layOffBean.setTelephoneNumber(null != loMap.get(BenefitsConstants.PRIMARY_PHONE) ?  loMap.get(BenefitsConstants.PRIMARY_PHONE).toString() :null);
				layOffBean.setStHouseName(null !=  loMap.get(BenefitsConstants.MAIL_ADDRESS_LINE_1) ? loMap.get(BenefitsConstants.MAIL_ADDRESS_LINE_1).toString() : null);
				layOffBean.setCity(null != loMap.get(BenefitsConstants.MAIL_ADDRESS_CITY) ? loMap.get(BenefitsConstants.MAIL_ADDRESS_CITY).toString() : null );
				layOffBean.setState(null != loMap.get(BenefitsConstants.MAIL_ADDRESS_STATE) ? loMap.get(BenefitsConstants.MAIL_ADDRESS_STATE).toString() : null );
				//layOffBean.setCountry(null != loMap.get("MAIL_ADDRESS_COUNTRY") ?  loMap.get("MAIL_ADDRESS_COUNTRY").toString() :null );
				layOffBean.setZipCode(null != loMap.get(BenefitsConstants.MAIL_ADDRESS_ZIP) ? loMap.get(BenefitsConstants.MAIL_ADDRESS_ZIP).toString() : null );
				// CIF_02957 || Jira : UIM-4138
				layOffBean.setCounty(null != loMap.get(BenefitsConstants.MAIL_ADDRESS_COUNTY) ? loMap.get(BenefitsConstants.MAIL_ADDRESS_COUNTY).toString() : null );
				/*layOffBean.setAlienDocumentType(null != loMap.get(BenefitsConstants.ALIEN_DOCUMENT_TYPE) ? loMap.get(BenefitsConstants.ALIEN_DOCUMENT_TYPE).toString() : null);
				layOffBean.setAlienDocumentNumber(null != loMap.get(BenefitsConstants.DOCUMENT_NUMBER) ? loMap.get(BenefitsConstants.DOCUMENT_NUMBER).toString() : null);
				layOffBean.setExpirationDate(loMap.get(BenefitsConstants.EXPIRY_DATE) instanceof Date ? (Date)loMap.get(BenefitsConstants.EXPIRY_DATE) : null);*/
				excelResults.add(layOffBean);
				validateExcelReadFields(layOffBean, ++loopCount,request);
			}
			
			if(null != errorMap && !errorMap.isEmpty()){
				StringBuilder sBuilder = new StringBuilder();
				for ( String key: errorMap.keySet()) {
					sBuilder.append(errorMap.get(key)).append("<br/>");
				}
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadfilemasslayoff.page.InvalidFileFIelds",sBuilder.toString()));
			}else{
				boolean duplicate = ESUtils.checkDuplicateValues(result, BenefitsConstants.SSN);
				if(duplicate){
					errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadEMployeeListSharedPlan.page.Duplicate"));
				}
			}
		} catch (IOException e) {
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadfilemasslayoff.page.InvalidFileFormat"));
		} catch (Exception e) {
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadfilemasslayoff.page.IvalidFileTemplate"));
		}

		return errors;
	}
	
	//CIF_INT_02610 Start
	public void validateFormFields(HttpServletRequest arg1) throws BaseValidationException{
		
		IObjectAssembly objAssembly=super.getObjectAssemblyFromSession(arg1);
		
		RegisteredEmployerData registeredEmpData = (RegisteredEmployerData) objAssembly.getFirstComponent(RegisteredEmployerData.class);
		
		if(null!=registeredEmpData) {
			FormFieldValidateUtility.validateLongFld(registeredEmpData.getEmployerId(), this.employerId);
			FormFieldValidateUtility.validateStringFld(registeredEmpData.getEmployerName(), this.companyName);
		}
		
	}
	//CIF_INT_02610 End
	/**
	 * @param layOffBean UploadFileMassLayoffBean
	 * @param iteration Integer
	 * @description this method validates the excel read fields 
	 */
	private void validateExcelReadFields(final UploadFileMassLayoffBean layOffBean,final Integer iteration,HttpServletRequest request ){
		/*final int I_551_LENGTH = BenefitsConstants.NUMBER_THIRTEEN;
		final String MASK_I_551 = "[a-zA-Z]{3}[0-9]{10}";

		final int I_94_LENGTH = BenefitsConstants.NUMBER_ELEVEN;
		final String MASK_I_94 = "^[0-9][0-9]*$";		

		//Added Validation for document type I-766
		final int I_766_LENGTH = BenefitsConstants.NUMBER_THIRTEEN;
		final String MASK_I_766 = "[a-zA-Z]{3}[0-9]{10}";*/	
		//check SSN
		if(StringUtility.isBlank(layOffBean.getSsn()) || !StringUtility.isNumeric(layOffBean.getSsn())  || !(layOffBean.getSsn().length() == BenefitsConstants.NUMBER_NINE) ) {
			if(errorMap.containsKey(BenefitsConstants.SSN)){
				errorMap.put(BenefitsConstants.SSN , errorMap.get(BenefitsConstants.SSN)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.SSN, "Invalid SSN at line "+iteration);
			}
		}
		// check first name
		if(StringUtility.isBlank(layOffBean.getFirstName()) || layOffBean.getFirstName().length() > BenefitsConstants.NUMBER_THIRTY  
				|| !StringUtility.matchPattern(layOffBean.getFirstName(), StringUtility.NAME_PATTERN )){
			if(errorMap.containsKey(BenefitsConstants.FIRST_NAME)){
				errorMap.put(BenefitsConstants.FIRST_NAME , errorMap.get(BenefitsConstants.FIRST_NAME)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.FIRST_NAME, "Invalid first name at line "+iteration);
			}
		}
		// check middle initial
				if(StringUtility.isNotBlank(layOffBean.getMiddleInitial()) ){
					if(layOffBean.getMiddleInitial().length() > BenefitsConstants.NUMBER_ONE || !StringUtility.matchPattern(layOffBean.getMiddleInitial(), StringUtility.ONLY_ALPHABETS)){
						if(errorMap.containsKey(BenefitsConstants.MIDDLE_INITIAL)){
							errorMap.put(BenefitsConstants.MIDDLE_INITIAL , errorMap.get(BenefitsConstants.MIDDLE_INITIAL)+","+iteration);
						}else{
							errorMap.put(BenefitsConstants.MIDDLE_INITIAL,  Resources.getMessage(request, "access.sharedUploadEmployee.middleInitial.error")+" "+iteration);
						}

					}
				}
		// check last name
		if(StringUtility.isBlank(layOffBean.getLastName()) || layOffBean.getLastName().length() > BenefitsConstants.NUMBER_THIRTY  
				|| !StringUtility.matchPattern(layOffBean.getLastName(),StringUtility.NAME_PATTERN ) ){
			if(errorMap.containsKey(BenefitsConstants.LAST_NAME)){
				errorMap.put(BenefitsConstants.LAST_NAME , errorMap.get(BenefitsConstants.LAST_NAME)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.LAST_NAME, "Invalid last name at line "+iteration);
			}
		}
		// check suffix
				if(StringUtility.isNotBlank(layOffBean.getSuffix())  ){
					if(layOffBean.getSuffix().length() > BenefitsConstants.NUMBER_THIRTY  
							|| !this.suffixMap.containsKey(layOffBean.getSuffix())){

						if(errorMap.containsKey(BenefitsConstants.SUFFIX)){
							errorMap.put(BenefitsConstants.SUFFIX , errorMap.get(BenefitsConstants.SUFFIX)+","+iteration);
						}else{
							errorMap.put(BenefitsConstants.SUFFIX,  Resources.getMessage(request, "access.sharedUploadEmployee.suffix.error")+" "+iteration);
						}
					}else{
						ClaimantSuffixEnum suffix =(ClaimantSuffixEnum)this.suffixMap.get(layOffBean.getSuffix()) ;
						layOffBean.setSuffix(suffix.getName());
					}
				}
				
		// check date of birth
		if(null == layOffBean.getBirthDate() || !DateUtility.checkBirthDate(layOffBean.getBirthDate())){
			if(errorMap.containsKey(BenefitsConstants.DATE_OF_BIRTH)){
				errorMap.put(BenefitsConstants.DATE_OF_BIRTH , errorMap.get(BenefitsConstants.DATE_OF_BIRTH)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.DATE_OF_BIRTH, "Invalid date of birth at line "+iteration);
			}
		}
		// check gender
		if(StringUtility.isBlank(layOffBean.getGender()) || !StringUtility.matchPattern(layOffBean.getGender(), StringUtility.GENDER_PATTERN) ){
			if(errorMap.containsKey(BenefitsConstants.GENDER)){
				errorMap.put(BenefitsConstants.GENDER , errorMap.get(BenefitsConstants.GENDER)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.GENDER, "Invalid gender at line "+iteration);
			}
		}
		
		// check citizenship
		if(StringUtility.isBlank(layOffBean.getCitizenship()) || !StringUtility.matchPattern(layOffBean.getCitizenship(), StringUtility.CITIZENSHIP_PATTERN) ){
			if(errorMap.containsKey(BenefitsConstants.CITIZENSHIP)){
				errorMap.put(BenefitsConstants.CITIZENSHIP , errorMap.get(BenefitsConstants.CITIZENSHIP)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.CITIZENSHIP, "Invalid citizenship at line "+iteration);
			}
		}

		// check telephone number
		if(StringUtility.isBlank(layOffBean.getTelephoneNumber()) || !StringUtility.matchPattern(layOffBean.getTelephoneNumber(), StringUtility.TELEPHONE_PATTERN) ){
			if(errorMap.containsKey(BenefitsConstants.PRIMARY_PHONE)){
				errorMap.put(BenefitsConstants.PRIMARY_PHONE , errorMap.get(BenefitsConstants.PRIMARY_PHONE)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.PRIMARY_PHONE, "Invalid telephone number at line "+iteration);
			}
		}
		// check st / house and name
		if(StringUtility.isBlank(layOffBean.getStHouseName()) || layOffBean.getStHouseName().length() > BenefitsConstants.NUMBER_FORTY  
				|| !StringUtility.matchPattern(layOffBean.getStHouseName(),StringUtility.ADDRESS_LINE_PATTERN ) ){
			if(errorMap.containsKey(BenefitsConstants.MAIL_ADDRESS_LINE_1)){
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_LINE_1 , errorMap.get(BenefitsConstants.MAIL_ADDRESS_LINE_1)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_LINE_1, "Invalid Street/House at line "+iteration);
			}
		}
		// check city
		if(StringUtility.isBlank(layOffBean.getCity()) || layOffBean.getCity().length() > BenefitsConstants.NUMBER_THIRTY  
				|| !StringUtility.matchPattern(layOffBean.getCity(),StringUtility.NAME_PATTERN ) ){
			if(errorMap.containsKey(BenefitsConstants.MAIL_ADDRESS_CITY)){
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_CITY , errorMap.get(BenefitsConstants.MAIL_ADDRESS_CITY)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_CITY, "Invalid city at line "+iteration);
			}
		}
		// check state validation pending
		if(StringUtility.isBlank(layOffBean.getState()) || StringUtility.isBlank((String)CacheUtility.getCachePropertyValue("T_MST_STATE", "key", layOffBean.getState().toUpperCase(),"description")) ){
			if(errorMap.containsKey(BenefitsConstants.MAIL_ADDRESS_STATE)){
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_STATE , errorMap.get(BenefitsConstants.MAIL_ADDRESS_STATE)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_STATE, "Invalid state at line "+iteration);
			}
		}

		//check zip code validation pending
		if(StringUtility.isBlank(layOffBean.getZipCode()) || layOffBean.getZipCode().length() > BenefitsConstants.NUMBER_TEN  
				|| !StringUtility.matchPattern(layOffBean.getZipCode(),StringUtility.ZIP_PATTERN ) ){
			if(errorMap.containsKey(BenefitsConstants.MAIL_ADDRESS_ZIP)){
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_ZIP , errorMap.get(BenefitsConstants.MAIL_ADDRESS_ZIP)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_ZIP, "Invalid zip code at line "+iteration);
			}
		}
		// CIF_02957 || Jira : UIM-4138
		if(StringUtility.isBlank(layOffBean.getCounty()) || StringUtility.isBlank((String)countyMap.get(layOffBean.getCounty()))){
			if(StringUtility.isBlank(layOffBean.getCounty()) && !StateEnum.MO.getName().equals(layOffBean.getState())){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

				// let it pass no need to add error as county needs to exist
			}else{
				if(errorMap.containsKey("COUNTY")){
					errorMap.put("COUNTY" , errorMap.get("COUNTY")+","+iteration);
				}else{
					errorMap.put("COUNTY", Resources.getMessage(request, "access.sharedUploadEmployee.county.error")+" "+iteration);
				}
			}
		}else{
			layOffBean.setCounty((String)countyMap.get(layOffBean.getCounty()));
		}
		/*if(StringUtility.isNotBlank(layOffBean.getCitizenship()) && "N".equalsIgnoreCase(layOffBean.getCitizenship()) ){
			if(StringUtility.isBlank(layOffBean.getAlienDocumentType()) || !this.map.containsKey(layOffBean.getAlienDocumentType())){
				if(errorMap.containsKey(BenefitsConstants.ALIEN_DOCUMENT_TYPE)){
					errorMap.put(BenefitsConstants.ALIEN_DOCUMENT_TYPE , errorMap.get(BenefitsConstants.ALIEN_DOCUMENT_TYPE)+","+iteration);
				}else{
					errorMap.put(BenefitsConstants.ALIEN_DOCUMENT_TYPE, "Invalid document type at line "+iteration);
				}
			}
		
			final String alienNumber = layOffBean.getAlienDocumentNumber();
			boolean invalidDocType = Boolean.FALSE;
			if (AlienDocumentTypeEnum.I_551.getName().equalsIgnoreCase(layOffBean.getAlienDocumentType())) {
				if (alienNumber.length() != I_551_LENGTH) {
					invalidDocType = Boolean.TRUE;
				}else{
					// to test the mask in case of I-551
					final Pattern p551 = Pattern.compile(MASK_I_551);
					final Matcher m551 = p551.matcher(alienNumber);
					if (!m551.matches()) {
						invalidDocType = Boolean.TRUE;
					} 
				}
			}

			//CIF_00692:Start
			//Added Validation for document type I_766
			if(AlienDocumentTypeEnum.I_766.getName().equals(layOffBean.getAlienDocumentType())){
				if(alienNumber.length() != I_766_LENGTH){
					invalidDocType = Boolean.TRUE;
				}else{
					final Pattern p766 = Pattern.compile(MASK_I_766);
					final Matcher m766 = p766.matcher(alienNumber);
					if(!m766.matches()){  
						invalidDocType = Boolean.TRUE;				
					}
				}

			}
			//CIF_00692:End

			// to test the length in case of I-94
			if (AlienDocumentTypeEnum.I_94.getName().equals(layOffBean.getAlienDocumentType())) {

				if (alienNumber.length() != I_94_LENGTH) {
					invalidDocType = Boolean.TRUE;
				}

				// to test the mask in case of I-551
				final Pattern p94 = Pattern.compile(MASK_I_94);
				final Matcher m94 = p94.matcher(alienNumber);
				if (!m94.matches()) {
					invalidDocType = Boolean.TRUE;
				} 			
			}

			if(invalidDocType){
				if(errorMap.containsKey(BenefitsConstants.DOCUMENT_NUMBER)){
					errorMap.put(BenefitsConstants.DOCUMENT_NUMBER , errorMap.get(BenefitsConstants.DOCUMENT_NUMBER)+","+iteration);
				}else{
					errorMap.put(BenefitsConstants.DOCUMENT_NUMBER, "Invalid Document number at line "+iteration);
				}
			}

			if(null == layOffBean.getExpirationDate()){
				if(errorMap.containsKey(BenefitsConstants.EXPIRY_DATE)){
					errorMap.put(BenefitsConstants.EXPIRY_DATE , errorMap.get(BenefitsConstants.EXPIRY_DATE)+","+iteration);
				}else{
					errorMap.put(BenefitsConstants.EXPIRY_DATE, "Invalid Expiration Date at line "+iteration);
				}
			}
		}else if (StringUtility.isNotBlank(layOffBean.getCitizenship()) && "Y".equalsIgnoreCase(layOffBean.getCitizenship()) &&  (null !=layOffBean.getExpirationDate()  || StringUtility.isNotBlank(layOffBean.getAlienDocumentNumber()) || StringUtility.isNotBlank(layOffBean.getAlienDocumentType())   )   ){
			if(errorMap.containsKey(BenefitsConstants.REMOVE_ALIEN_DOCS)){
				errorMap.put(BenefitsConstants.REMOVE_ALIEN_DOCS , errorMap.get(BenefitsConstants.REMOVE_ALIEN_DOCS)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.REMOVE_ALIEN_DOCS, "Remove Alien details from line "+iteration);
			}
		}*/
		// CIF_02957 || Jira : UIM-4138 ends
	}
	/**
	 * 
	 * @param name string 
	 * @return true if string and pattern matches
	 */
	/*private boolean matchPattern (String name,String pattern){
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(name);
		return m.find();
	}*/
	
	public Long getEmployerId() {
		return employerId;
	}
	public void setEmployerId(Long employerId) {
		this.employerId = employerId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public FormFile getFileName() {
		return fileName;
	}

	/**   
	 * @struts.validator 
	 * 		type = "required"
	 * 		msgkey="error.required"
	 * 		arg0resource = "access.cin.uploadfilemasslayoff.uploadfile.number"
	 */	
	public void setFileName(FormFile fileName) {
		this.fileName = fileName;
	}
	
	public void setFileName(Object fileName){
		this.fileName = (FormFile) fileName;
	}
	public List<UploadFileMassLayoffBean> getExcelResults() {
		return excelResults;
	}

	public void setExcelResults(List<UploadFileMassLayoffBean> excelResults) {
		this.excelResults = excelResults;
	}

	public Long getMassLayOffId() {
		return massLayOffId;
	}

	public void setMassLayOffId(Long massLayOffId) {
		this.massLayOffId = massLayOffId;
	}	

}