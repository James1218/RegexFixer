// CIF_00140
package gov.state.uim.cin.struts;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import gov.state.uim.domain.bean.SWPEmployeeUploadBean;
import gov.state.uim.domain.data.MstMsCounty;
import gov.state.uim.domain.enums.ClaimantSuffixEnum;
import gov.state.uim.framework.BenefitsConstants;
import gov.state.uim.framework.cache.CacheUtility;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.DateUtility;
import gov.state.uim.util.lang.ESUtils;
import gov.state.uim.util.lang.ReadExcel;
import gov.state.uim.util.lang.StringUtility;

/**
 * This class is form that corresponds to upload excel of mass laid off employee screen;
 * jsp            : swpemployeeupload.jsp
 * Action         : SWPEmployeeUploadAction.java
 * Form           : SWPEmployeeUploadForm.java
 * tiles-defs.xml : .swpemployeeupload (accessms/WEB-INF/jsp)
 * 
 * @author Tata Consultancy Services
 * 
 * @version 1.0 
 * 
 * 
 * @struts.form name ="swpemployeeuploadform"
 */
public class SWPEmployeeUploadForm extends BaseValidatorForm {

	
	private static final long serialVersionUID = 1L;
	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(SWPEmployeeUploadForm.class);

	private List<SWPEmployeeUploadBean> excelResults = new ArrayList<SWPEmployeeUploadBean>();
	private FormFile fileName;
	//private final CaseInsensitiveMap map = new CaseInsensitiveMap(AlienDocumentTypeEnum.getEnumMap());
	private final CaseInsensitiveMap suffixMap = new CaseInsensitiveMap(ClaimantSuffixEnum.getEnumMap());
	private final HashMap<String, String> errorMap = new HashMap<String, String>();
	private final CaseInsensitiveMap countyMap =  new CaseInsensitiveMap();
	//private HashMap<String, String> countyMap = new HashMap<String, String>();
	public static final String[] READ_EXCEL = {BenefitsConstants.SSN,BenefitsConstants.FIRST_NAME,BenefitsConstants.MIDDLE_INITIAL,BenefitsConstants.LAST_NAME,BenefitsConstants.SUFFIX,BenefitsConstants.GENDER,BenefitsConstants.DATE_OF_BIRTH,BenefitsConstants.MAIL_ADDRESS_LINE_1,BenefitsConstants.MAIL_ADDRESS_LINE_2,
		BenefitsConstants.MAIL_ADDRESS_CITY,BenefitsConstants.MAIL_ADDRESS_COUNTY,BenefitsConstants.MAIL_ADDRESS_STATE,BenefitsConstants.MAIL_ADDRESS_ZIP,BenefitsConstants.EMP_REC_PENSION,BenefitsConstants.EMP_WORK_HRS,
		BenefitsConstants.CITIZENSHIP,BenefitsConstants.FEDERAL_TAX_STATUS};
	@Override
	public ActionErrors validate(final ActionMapping arg0, final HttpServletRequest arg1) {

		ActionErrors errors = null;

		try{
			errors = super.validate(arg0, arg1);

			if(errors.size() > 0){
				return errors;
			} 
			if(null != fileName  && !"".equals(fileName.getInputStream().toString())){

				final InputStream lObjInputStream = this.getFileName().getInputStream();
				errors = validateExcelFile(lObjInputStream, errors,arg1);

			}else{
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadEMployeeListSharedPlan.page.noFileSelected"));
			}
		}catch (final IOException e) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("error while parsing uploaded document "+e);
		}
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadEMployeeListSharedPlan.page.noFileSelected"));
		}
		return errors;

	}
	
	/**
	 * @param stream excel stream 
	 * @param errors action errors
	 * @return action errors if any
	 */
	private ActionErrors validateExcelFile (final InputStream stream, final ActionErrors errors,HttpServletRequest request ){

		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(stream);
			final List<ListOrderedMap> result = ReadExcel.readExcel(workbook, READ_EXCEL, 0, 1);
			SWPEmployeeUploadBean empBean = null;
			int loopCount =0;
			/*for (Object o : map.keySet()) {
//				System.out.println(o);

//				System.out.println(o.getClass());

//				System.out.println(map.get(o));

//				System.out.println(map.get(o).getClass());

			}*/
			for (ListOrderedMap loMap : result) {
				empBean = new SWPEmployeeUploadBean();
				empBean.setSsn(null !=  loMap.get(BenefitsConstants.SSN) ? loMap.get(BenefitsConstants.SSN).toString() : null);
				empBean.setFirstName(null != loMap.get(BenefitsConstants.FIRST_NAME) ? loMap.get(BenefitsConstants.FIRST_NAME).toString() : null);
				empBean.setMiddleInitial(null != loMap.get(BenefitsConstants.MIDDLE_INITIAL) ? loMap.get(BenefitsConstants.MIDDLE_INITIAL).toString() : null );
				empBean.setLastName(null != loMap.get(BenefitsConstants.LAST_NAME) ? loMap.get(BenefitsConstants.LAST_NAME).toString() : null);
				empBean.setSuffix(null != loMap.get(BenefitsConstants.SUFFIX) ? loMap.get(BenefitsConstants.SUFFIX).toString() : null);
				empBean.setGender(null !=  loMap.get(BenefitsConstants.GENDER) ? loMap.get(BenefitsConstants.GENDER).toString() : null);
				empBean.setBirthDate(loMap.get(BenefitsConstants.DATE_OF_BIRTH) instanceof Date ? (Date)loMap.get(BenefitsConstants.DATE_OF_BIRTH) : null);
				empBean.setCitizenship(null != loMap.get(BenefitsConstants.CITIZENSHIP) ? loMap.get(BenefitsConstants.CITIZENSHIP).toString() : null);
				empBean.setAddressLine1(null !=  loMap.get(BenefitsConstants.MAIL_ADDRESS_LINE_1) ? loMap.get(BenefitsConstants.MAIL_ADDRESS_LINE_1).toString() : null);
				empBean.setAddressLine2(null !=  loMap.get(BenefitsConstants.MAIL_ADDRESS_LINE_2) ? loMap.get(BenefitsConstants.MAIL_ADDRESS_LINE_2).toString() : null);
				empBean.setCity(null != loMap.get(BenefitsConstants.MAIL_ADDRESS_CITY) ? loMap.get(BenefitsConstants.MAIL_ADDRESS_CITY).toString() : null );
				empBean.setState(null != loMap.get(BenefitsConstants.MAIL_ADDRESS_STATE) ? loMap.get(BenefitsConstants.MAIL_ADDRESS_STATE).toString() : null );
				empBean.setCounty(null != loMap.get(BenefitsConstants.MAIL_ADDRESS_COUNTY) ?  loMap.get(BenefitsConstants.MAIL_ADDRESS_COUNTY).toString() :null );
				empBean.setZipCode(null != loMap.get(BenefitsConstants.MAIL_ADDRESS_ZIP) ? loMap.get(BenefitsConstants.MAIL_ADDRESS_ZIP).toString() : null );
				empBean.setEmpRecPension(null != loMap.get(BenefitsConstants.EMP_REC_PENSION) ?  loMap.get(BenefitsConstants.EMP_REC_PENSION).toString() : null);
				empBean.setRegularWeeklyWorkHrs(null != loMap.get(BenefitsConstants.EMP_WORK_HRS) ? loMap.get(BenefitsConstants.EMP_WORK_HRS).toString() : null);
				empBean.setFederalTaxStatus(null != loMap.get(BenefitsConstants.FEDERAL_TAX_STATUS) ? loMap.get(BenefitsConstants.FEDERAL_TAX_STATUS).toString() : null);
				//empBean.setAlienDocumentType(null != loMap.get(BenefitsConstants.ALIEN_DOCUMENT_TYPE) ? loMap.get(BenefitsConstants.ALIEN_DOCUMENT_TYPE).toString() : null);
				//empBean.setAlienDocumentNumber(null != loMap.get(BenefitsConstants.DOCUMENT_NUMBER) ? loMap.get(BenefitsConstants.DOCUMENT_NUMBER).toString() : null);
				//empBean.setExpirationDate(loMap.get(BenefitsConstants.EXPIRY_DATE) instanceof Date ? (Date)loMap.get(BenefitsConstants.EXPIRY_DATE) : null);
				
				excelResults.add(empBean);
				validateExcelReadFields(empBean, ++loopCount,request);
			}
				if(null == errorMap || errorMap.isEmpty()){
					final boolean duplicate = ESUtils.checkDuplicateValues(result, BenefitsConstants.SSN);
					if(duplicate){
						errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadEMployeeListSharedPlan.page.Duplicate"));
					}
				}
				if(null != errorMap && !errorMap.isEmpty()){
					final StringBuilder sBuilder = new StringBuilder();
					for ( String key: errorMap.keySet()) {
						sBuilder.append(errorMap.get(key)).append("<br/>");
					}
					errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadEMployeeListSharedPlan.page.InvalidFileFIelds",sBuilder.toString()));
				}
		} catch (final IOException e) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(" error while parsing excel file "+e);
		}
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadEMployeeListSharedPlan.page.InvalidFileFormat"));
		}
		return errors;

	}
	/**
	 * @param empBean containingall the excel raed fields in a row that needs to be validated
	 * @param iteration row number of the excel field that is being validated
	 */
	private void validateExcelReadFields(final SWPEmployeeUploadBean empBean,final Integer iteration,HttpServletRequest request){
		//CIF_00692:End
		//check SSN
		if(StringUtility.isBlank(empBean.getSsn()) || !StringUtility.isNumeric(empBean.getSsn())  || !(empBean.getSsn().length() == BenefitsConstants.NUMBER_NINE) ) {
			if(errorMap.containsKey(BenefitsConstants.SSN)){
				errorMap.put(BenefitsConstants.SSN , errorMap.get(BenefitsConstants.SSN)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.SSN, "Invalid SSN at line "+iteration);
			}
		}
		// check first name
		if(StringUtility.isBlank(empBean.getFirstName()) || empBean.getFirstName().length() > BenefitsConstants.NUMBER_THIRTY  
				|| !StringUtility.matchPattern(empBean.getFirstName(), StringUtility.NAME_PATTERN )){
			if(errorMap.containsKey(BenefitsConstants.FIRST_NAME)){
				errorMap.put(BenefitsConstants.FIRST_NAME , errorMap.get(BenefitsConstants.FIRST_NAME)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.FIRST_NAME, Resources.getMessage(request, "access.sharedUploadEmployee.firstName.error")+" "+iteration);
			}
		}
		// check middle initial
		if(StringUtility.isNotBlank(empBean.getMiddleInitial()) ){
			if(empBean.getMiddleInitial().length() > BenefitsConstants.NUMBER_ONE || !StringUtility.matchPattern(empBean.getMiddleInitial(), StringUtility.ONLY_ALPHABETS)){
				if(errorMap.containsKey(BenefitsConstants.MIDDLE_INITIAL)){
					errorMap.put(BenefitsConstants.MIDDLE_INITIAL , errorMap.get(BenefitsConstants.MIDDLE_INITIAL)+","+iteration);
				}else{
					errorMap.put(BenefitsConstants.MIDDLE_INITIAL,  Resources.getMessage(request, "access.sharedUploadEmployee.middleInitial.error")+" "+iteration);
				}

			}
		}
		// check last name
		if(StringUtility.isBlank(empBean.getLastName()) || empBean.getLastName().length() > BenefitsConstants.NUMBER_THIRTY  
				|| !StringUtility.matchPattern(empBean.getLastName(),StringUtility.NAME_PATTERN ) ){
			if(errorMap.containsKey(BenefitsConstants.LAST_NAME)){
				errorMap.put(BenefitsConstants.LAST_NAME , errorMap.get(BenefitsConstants.LAST_NAME)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.LAST_NAME,  Resources.getMessage(request, "access.sharedUploadEmployee.lastName.error")+" "+iteration);
			}
		}
		// check suffix
		if(StringUtility.isNotBlank(empBean.getSuffix())  ){
			if(empBean.getSuffix().length() > BenefitsConstants.NUMBER_THIRTY  
					|| !this.suffixMap.containsKey(empBean.getSuffix())){

				if(errorMap.containsKey(BenefitsConstants.SUFFIX)){
					errorMap.put(BenefitsConstants.SUFFIX , errorMap.get(BenefitsConstants.SUFFIX)+","+iteration);
				}else{
					errorMap.put(BenefitsConstants.SUFFIX,  Resources.getMessage(request, "access.sharedUploadEmployee.suffix.error")+" "+iteration);
				}
			}else{
				ClaimantSuffixEnum suffix =(ClaimantSuffixEnum)this.suffixMap.get(empBean.getSuffix()) ;
				empBean.setSuffix(suffix.getName());
			}
		}
		
		// check gender
		if(StringUtility.isBlank(empBean.getGender()) || !StringUtility.matchPattern(empBean.getGender(), StringUtility.GENDER_PATTERN) ){
			if(errorMap.containsKey(BenefitsConstants.GENDER)){
				errorMap.put(BenefitsConstants.GENDER , errorMap.get(BenefitsConstants.GENDER)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.GENDER,  Resources.getMessage(request, "access.sharedUploadEmployee.gender.error")+" "+iteration);
			}
		}
		// check date of birth
		if(null == empBean.getBirthDate() || !DateUtility.checkBirthDate(empBean.getBirthDate())){
			if(errorMap.containsKey(BenefitsConstants.DATE_OF_BIRTH)){
				errorMap.put(BenefitsConstants.DATE_OF_BIRTH , errorMap.get(BenefitsConstants.DATE_OF_BIRTH)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.DATE_OF_BIRTH,  Resources.getMessage(request, "access.sharedUploadEmployee.dateOfBirth.error")+" "+iteration);
			}
		}

		// check Address Line 1
		if(StringUtility.isBlank(empBean.getAddressLine1()) || empBean.getAddressLine1().length() > BenefitsConstants.NUMBER_FORTY  
				|| !StringUtility.matchPattern(empBean.getAddressLine1(),StringUtility.ADDRESS_LINE_PATTERN ) ){
			if(errorMap.containsKey(BenefitsConstants.MAIL_ADDRESS_LINE_1)){
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_LINE_1 , errorMap.get(BenefitsConstants.MAIL_ADDRESS_LINE_1)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_LINE_1, Resources.getMessage(request, "access.sharedUploadEmployee.addressLine1.error")+" "+iteration);
			}
		}
		// check Address Line 2
		if(StringUtility.isNotBlank(empBean.getAddressLine2()) && (empBean.getAddressLine2().length() > BenefitsConstants.NUMBER_FORTY  
				|| !StringUtility.matchPattern(empBean.getAddressLine2(),StringUtility.ADDRESS_LINE_PATTERN )) ){
			if(errorMap.containsKey(BenefitsConstants.MAIL_ADDRESS_LINE_2)){
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_LINE_2 , errorMap.get(BenefitsConstants.MAIL_ADDRESS_LINE_2)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_LINE_2, Resources.getMessage(request, "access.sharedUploadEmployee.addressLine2.error")+" "+iteration);
			}
		}
		// check city
		if(StringUtility.isBlank(empBean.getCity()) || empBean.getCity().length() > BenefitsConstants.NUMBER_THIRTY  
				|| !StringUtility.matchPattern(empBean.getCity(),StringUtility.NAME_PATTERN ) ){
			if(errorMap.containsKey(BenefitsConstants.MAIL_ADDRESS_CITY)){
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_CITY , errorMap.get(BenefitsConstants.MAIL_ADDRESS_CITY)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_CITY, Resources.getMessage(request, "access.sharedUploadEmployee.city.error")+" "+iteration);
			}
		}
		// check state validation pending
		if(StringUtility.isBlank(empBean.getState()) || StringUtility.isBlank((String)CacheUtility.getCachePropertyValue("T_MST_STATE", "key", empBean.getState().toUpperCase(), "description")) ){
			if(errorMap.containsKey(BenefitsConstants.MAIL_ADDRESS_STATE)){
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_STATE , errorMap.get(BenefitsConstants.MAIL_ADDRESS_STATE)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_STATE, Resources.getMessage(request, "access.sharedUploadEmployee.state.error")+" "+iteration);
			}
		}
		//check ZIP Code
		if(StringUtility.isBlank(empBean.getZipCode()) || empBean.getZipCode().length() > BenefitsConstants.NUMBER_TEN  
				|| !StringUtility.matchPattern(empBean.getZipCode(),StringUtility.ZIP_PATTERN ) ){
			if(errorMap.containsKey(BenefitsConstants.MAIL_ADDRESS_ZIP)){
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_ZIP , errorMap.get(BenefitsConstants.MAIL_ADDRESS_ZIP)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.MAIL_ADDRESS_ZIP, Resources.getMessage(request, "access.sharedUploadEmployee.zip.error")+" "+iteration);
			}
		}
		
		//check county
		if(countyMap.isEmpty()){
			List<MstMsCounty> list = CacheUtility.getCacheList("T_MST_COUNTY");
			for (MstMsCounty object : list) {
				countyMap.put(object.getDescription().trim(), object.getKey().trim());
			}
		}
		
		
		if(StringUtility.isBlank(empBean.getCounty()) || StringUtility.isBlank((String)countyMap.get(empBean.getCounty()))){
			if(errorMap.containsKey("COUNTY")){
				errorMap.put("COUNTY" , errorMap.get("COUNTY")+","+iteration);
			}else{
				errorMap.put("COUNTY", Resources.getMessage(request, "access.sharedUploadEmployee.county.error")+" "+iteration);
			}
		}

		// check employee Receiving pension
		if(StringUtility.isBlank(empBean.getEmpRecPension()) || !StringUtility.matchPattern(empBean.getEmpRecPension(), StringUtility.CITIZENSHIP_PATTERN)  ){
			if(errorMap.containsKey(BenefitsConstants.EMP_REC_PENSION)){
				errorMap.put(BenefitsConstants.EMP_REC_PENSION , errorMap.get(BenefitsConstants.EMP_REC_PENSION)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.EMP_REC_PENSION, Resources.getMessage(request, "access.sharedUploadEmployee.pension.error")+" "+iteration);
			}
		}
		// check employee working hours
		if(null == empBean.getRegularWeeklyWorkHrs()|| !StringUtility.isNumeric(empBean.getRegularWeeklyWorkHrs()) || Long.parseLong(empBean.getRegularWeeklyWorkHrs()) > BenefitsConstants.WEEK_HOURS ){
			if(errorMap.containsKey(BenefitsConstants.EMP_WORK_HRS)){
				errorMap.put(BenefitsConstants.EMP_WORK_HRS , errorMap.get(BenefitsConstants.EMP_WORK_HRS)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.EMP_WORK_HRS, Resources.getMessage(request, "access.sharedUploadEmployee.workHours.error")+" "+iteration);
			}
		}
		
		// check citizenship
		if(StringUtility.isBlank(empBean.getCitizenship()) || !StringUtility.matchPattern(empBean.getCitizenship(), StringUtility.CITIZENSHIP_PATTERN) ){
			if(errorMap.containsKey(BenefitsConstants.CITIZENSHIP)){
				errorMap.put(BenefitsConstants.CITIZENSHIP , errorMap.get(BenefitsConstants.CITIZENSHIP)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.CITIZENSHIP, Resources.getMessage(request, "access.sharedUploadEmployee.citizenship.error")+" "+iteration);
			}
		}
		// check federal tax withholding only if it is entered
		if(StringUtility.isNotBlank(empBean.getFederalTaxStatus()) && !StringUtility.matchPattern(empBean.getFederalTaxStatus(), StringUtility.CITIZENSHIP_PATTERN)){
			if(errorMap.containsKey(BenefitsConstants.FEDERAL_TAX_STATUS)){
				errorMap.put(BenefitsConstants.FEDERAL_TAX_STATUS , errorMap.get(BenefitsConstants.FEDERAL_TAX_STATUS)+","+iteration);
			}else{
				errorMap.put(BenefitsConstants.FEDERAL_TAX_STATUS, Resources.getMessage(request, "access.sharedUploadEmployee.federalTax.error")+" "+iteration);
			}
		}
		
	}
	
	public List<SWPEmployeeUploadBean> getExcelFields() {
		return excelResults;
	}

	public void setExcelFields(final List<SWPEmployeeUploadBean> excelFields) {
		this.excelResults = excelFields;
	}

	public FormFile getFileName() {
		return fileName;
	}

	public void setFileName(final FormFile fileName) {
		this.fileName = fileName;
	}
	
}