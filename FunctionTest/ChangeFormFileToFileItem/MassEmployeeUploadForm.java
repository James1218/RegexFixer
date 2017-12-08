package gov.state.uim.tra.struts;

import org.apache.commons.fileupload.FileItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.domain.bean.PotentialTRAClaimantDetailBean;
import gov.state.uim.framework.cache.CacheUtility;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.util.lang.ReadExcel;
import gov.state.uim.util.lang.StringUtility;

/**
 * CIF_00088 New screen for Add employee(Mass Upload).
 * 
 * This class is form that corresponds to upload excel of employee details;
 * jsp				: taapetitionmassupload.jsp 							
 * Action			: MassEmployeeUploadAction.java 			
 * Form				: MassEmployeeUploadForm.java 			
 * tiles-defs.xml	: .massemployeeupload (uinteract/WEB-INF/jsp/tra)
 * 
 * @author Tata Consultancy Services
 * 
 * @version 1.0 
 * 
 * 
 * @struts.form name ="massemployeeuploadform"
 */
public class MassEmployeeUploadForm extends BaseValidatorForm{
	private static final Logger LOGGER = Logger.getLogger(MassEmployeeUploadForm.class);


	/**
	 * 
	 */
	private static final long serialVersionUID = -7983913530703914377L;

	private FileItem fileName;
	
	private List<PotentialTRAClaimantDetailBean> excelResults = new ArrayList<PotentialTRAClaimantDetailBean>();
	private HashMap<String, String> errorMap = new HashMap<String, String>();
	public static final String NAME_PATTERN = "^[A-Za-z\\s'\\-]*$";
	public static final String ZIP_PATTERN = "^[A-Za-z0-9]*$";
	public static final String TELEPHONE_PATTERN = "^[0-9]{10}$";
	public static final String ADDRESS_LINE1_PATTERN = "^[A-Za-z0-9\\s\\.#/\\-]*$";
	public static final String[] READ_EXCEL = {"SSN","FIRST_NAME","LAST_NAME","ADDRESS_LINE_1","ADDRESS_LINE_2",
		"CITY","STATE","ZIP","COUNTRY","TELEPHONE_NUMBER"};
	
	
	public MassEmployeeUploadForm() {
		super();
	}

	public List<PotentialTRAClaimantDetailBean> getExcelResults() {
		return excelResults;
	}

	public void setExcelResults(List<PotentialTRAClaimantDetailBean> excelResults) {
		this.excelResults = excelResults;
	}

	public FileItem getFileName() {
		return fileName;
	}
	/**   
	 * @struts.validator 
	 * 		type = "required"
	 * 		msgkey="error.required"
	 * 		arg0resource = "access.tra.massemployeeupload.employeelistfile"
	 */
	public void setFileName(FileItem fileName) {
		this.fileName = fileName;
	}
	public void setFileName(Object fileName){
		if(fileName!=null){
			List<FileItem>fileItems=(List<FileItem>)fileName;
			if(fileItems!=null && !fileItems.isEmpty()){
				this.fileName=fileItems.get(0);
			}
		}
	}


	
	public void setFileName(Object fileName){
		this.fileName = (FormFile) fileName;
	}
	
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = null;

		try{
			errors = super.validate(arg0, arg1);

			if(errors.size() > 0){
				return errors;
			} 
			if(null != fileName  && !"".equals(fileName.getInputStream().toString())){

				InputStream lObjInputStream = this.getFileName().getInputStream();
				errors = validateExcelFile(lObjInputStream, errors);

			}else{
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadfilemasslayoff.page.noFileSelected"));
			}
		}catch (IOException e) {

			if (LOGGER.isEnabledFor(Level.ERROR)) {
				LOGGER.error("error", e);
			}

			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadfilemasslayoff.page.noFileSelected"));
		}
		return errors;
	}
	private ActionErrors validateExcelFile(InputStream stream, ActionErrors errors){
		try {
			
			HSSFWorkbook workbook = new HSSFWorkbook(stream);
			List<ListOrderedMap> result = ReadExcel.readExcel(workbook, READ_EXCEL, 0, 1);
			ListOrderedMap loMap = null;
			// CIF_00563 Start
			// Added Code clearing data in session.
			excelResults.clear();
			errorMap.clear();
			PotentialTRAClaimantDetailBean claimantDetailsBean = null;
			int loopCount = 0;
			for (Iterator<ListOrderedMap> iterator = result.iterator(); iterator.hasNext();) {
				loMap = (ListOrderedMap) iterator.next();
				
				// validate Row values  and populate if correct then create a bean and  add values else add error message in a hashmap 
				// and add as a action error at the end of file execution
				claimantDetailsBean = new PotentialTRAClaimantDetailBean();
				
				claimantDetailsBean.setSsn(null !=  loMap.get("SSN") ? loMap.get("SSN").toString() : null);
				claimantDetailsBean.setFirstName(null != loMap.get("FIRST_NAME") ? loMap.get("FIRST_NAME").toString() : null);
				claimantDetailsBean.setLastName(null != loMap.get("LAST_NAME") ? loMap.get("LAST_NAME").toString() : null);
				claimantDetailsBean.setAddressLine1(null !=  loMap.get("ADDRESS_LINE_1") ? loMap.get("ADDRESS_LINE_1").toString() : null);
				claimantDetailsBean.setAddressLine2(null !=  loMap.get("ADDRESS_LINE_2") ? loMap.get("ADDRESS_LINE_2").toString() : null);
				claimantDetailsBean.setCity(null != loMap.get("CITY") ? loMap.get("CITY").toString() : null );
				claimantDetailsBean.setState(null != loMap.get("STATE") ? loMap.get("STATE").toString() : null );
				claimantDetailsBean.setCountry(null != loMap.get("COUNTRY") ?  loMap.get("COUNTRY").toString() :null );
				claimantDetailsBean.setZip(null != loMap.get("ZIP") ? loMap.get("ZIP").toString() : null );
				claimantDetailsBean.setTelephoneNumber(null != loMap.get("TELEPHONE_NUMBER") ?  loMap.get("TELEPHONE_NUMBER").toString() :null);
				excelResults.add(claimantDetailsBean);
				validateExcelReadFields(claimantDetailsBean, ++loopCount);
			}
			
			if(null != errorMap && !errorMap.isEmpty()){
				StringBuilder sBuilder = new StringBuilder();
				for ( String Key: errorMap.keySet()) {
					sBuilder.append(errorMap.get(Key)).append("<br/>");
				}
				errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadfilemasslayoff.page.InvalidFileFIelds",sBuilder.toString()));
			}
		}
		catch (IOException e) {
			errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("access.cin.uploadfilemasslayoff.page.InvalidFileFormat"));
		}


		return errors;
	}
private void validateExcelReadFields(PotentialTRAClaimantDetailBean claimantDetailsBean,Integer iteration ){
		
		//check SSN
		if(StringUtility.isBlank(claimantDetailsBean.getSsn()) || !StringUtility.isNumeric(claimantDetailsBean.getSsn())  || !(claimantDetailsBean.getSsn().length() == 9) ) {
			if(errorMap.containsKey("SSN")){
				errorMap.put("SSN" , errorMap.get("SSN")+","+iteration);
			}else{
				errorMap.put("SSN", "Invalid SSN at line "+iteration);
			}
		}
		// check first name
		if(StringUtility.isBlank(claimantDetailsBean.getFirstName()) || claimantDetailsBean.getFirstName().length() > 30  
				|| !matchPattern(claimantDetailsBean.getFirstName(), MassEmployeeUploadForm.NAME_PATTERN )){
			if(errorMap.containsKey("FIRST_NAME")){
				errorMap.put("FIRST_NAME" , errorMap.get("FIRST_NAME")+","+iteration);
			}else{
				errorMap.put("FIRST_NAME", "Invalid first name at line "+iteration);
			}
		}
		// check last name
		if(StringUtility.isBlank(claimantDetailsBean.getLastName()) || claimantDetailsBean.getLastName().length() > 30  
				|| !matchPattern(claimantDetailsBean.getLastName(),MassEmployeeUploadForm.NAME_PATTERN ) ){
			if(errorMap.containsKey("LAST_NAME")){
				errorMap.put("LAST_NAME" , errorMap.get("LAST_NAME")+","+iteration);
			}else{
				errorMap.put("LAST_NAME", "Invalid last name at line "+iteration);
			}
		}
				
		// check ADDRESS_LINE_1
		if(StringUtility.isBlank(claimantDetailsBean.getAddressLine1()) || claimantDetailsBean.getAddressLine1().length() > 40  
				|| !matchPattern(claimantDetailsBean.getAddressLine1(),MassEmployeeUploadForm.ADDRESS_LINE1_PATTERN ) ){
			if(errorMap.containsKey("ADDRESS_LINE_1")){
				errorMap.put("ADDRESS_LINE_1" , errorMap.get("ADDRESS_LINE_1")+","+iteration);
			}else{
				errorMap.put("ADDRESS_LINE_1", "Invalid Address line1 "+iteration);
		    }
		}
		// check ADDRESS_LINE_1
		if(StringUtility.isBlank(claimantDetailsBean.getAddressLine2()) || claimantDetailsBean.getAddressLine2().length() > 40  
				|| !matchPattern(claimantDetailsBean.getAddressLine2(),MassEmployeeUploadForm.ADDRESS_LINE1_PATTERN ) ){
			if(errorMap.containsKey("ADDRESS_LINE_2")){
				errorMap.put("ADDRESS_LINE_2" , errorMap.get("ADDRESS_LINE_1")+","+iteration);
			}else{
				errorMap.put("ADDRESS_LINE_2", "Invalid Address line2 "+iteration);
			}
		}
		// check city
		if(StringUtility.isBlank(claimantDetailsBean.getCity()) || claimantDetailsBean.getCity().length() > 30  
				|| !matchPattern(claimantDetailsBean.getCity(),MassEmployeeUploadForm.NAME_PATTERN ) ){
			if(errorMap.containsKey("CITY")){
				errorMap.put("CITY" , errorMap.get("CITY")+","+iteration);
			}else{
				errorMap.put("CITY", "Invalid city at line "+iteration);
			}
		}
		// check state validation pending
//		List list = CacheUtility.getCacheList("T_MST_STATE");
		if(StringUtility.isBlank(claimantDetailsBean.getState()) || StringUtility.isBlank((String)CacheUtility.getCachePropertyValue("T_MST_STATE", "key", claimantDetailsBean.getState().toUpperCase(), "description")) ){
			if(errorMap.containsKey("STATE")){
				errorMap.put("STATE" , errorMap.get("STATE")+","+iteration);
			}else{
				errorMap.put("STATE", "Invalid state at line "+iteration);
			}
		}
		
		//check country
		if(StringUtility.isBlank(claimantDetailsBean.getCountry()) || claimantDetailsBean.getCountry().length() > 50  ){
			if(errorMap.containsKey("COUNTRY")){
				errorMap.put("COUNTRY" , errorMap.get("COUNTRY")+","+iteration);
			}else{
				errorMap.put("COUNTRY", "Invalid country at line "+iteration);
			}
		}
		
		//check zip code validation pending
		if(StringUtility.isBlank(claimantDetailsBean.getZip()) || claimantDetailsBean.getZip().length() > 10  
				|| !matchPattern(claimantDetailsBean.getZip(),MassEmployeeUploadForm.ZIP_PATTERN ) ){
			if(errorMap.containsKey("ZIP")){
				errorMap.put("ZIP" , errorMap.get("ZIP")+","+iteration);
			}else{
				errorMap.put("ZIP", "Invalid zip code at line "+iteration);
			}
		}
		// check telephone number
		if(StringUtility.isBlank(claimantDetailsBean.getTelephoneNumber()) || !matchPattern(claimantDetailsBean.getTelephoneNumber(), MassEmployeeUploadForm.TELEPHONE_PATTERN) ){
			if(errorMap.containsKey("TELEPHONE_NUMBER")){
				errorMap.put("TELEPHONE_NUMBER" , errorMap.get("TELEPHONE_NUMBER")+","+iteration);
			}else{
				errorMap.put("TELEPHONE_NUMBER", "Invalid telephone number at line "+iteration);
			}
		}
	}

private boolean matchPattern (String name,String pattern){
	Pattern p = Pattern.compile(pattern);
	Matcher m = p.matcher(name);
	return m.find();
}
}