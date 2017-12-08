/**
 * This action class takes care of action performed from 'Detailed Employee Wages - Upload file' screen  
 * @author Tata Consultancy Services
 */
package gov.state.uim.twr.struts;

import org.apache.commons.fileupload.FileItem;

import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import gov.state.uim.ApplicationProperties;
import gov.state.uim.GlobalConstants;
import gov.state.uim.ViewConstants;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.domain.bean.TaxWageNavigationBean;
import gov.state.uim.domain.data.EmployerAccountData;
import gov.state.uim.domain.data.InformationSubmitedbyData;
import gov.state.uim.domain.data.RegisteredEmployerData;
import gov.state.uim.domain.data.TaxWageReportAttributesData;
import gov.state.uim.domain.data.WageReportData;
import gov.state.uim.domain.enums.TWRActionClassEnum;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.struts.BaseLookUpDispatchAction;
import gov.state.uim.tax.TaxConstants;
import gov.state.uim.twr.TWRUtil;
import gov.state.uim.util.lang.StringUtility;

/**
 * @struts.action path="/twruploadadjustmentwagefile" name="twruploadadjustmentwagefileform" scope="request"
 *                input=".twruploadadjustmentwagefile" validate="true" parameter="method"
 * 
 * @struts.action-set-property value "true" property = "onErrorSamePage" 
 * @struts.action-set-property 
 * 			value = "true" property = "load"
 * @struts.action-forward name="totalNotSameAsTR" path= ".twrverifytotalwages" redirect="false"
 * @struts.action-forward name="totalSameAsTR" path= ".twrsubmitcompleted" redirect="false"
 * @struts.action-forward name="twrtaxreportadjustment" path= ".twrtaxreportadjustment" redirect="false"
 * @struts.action-forward name="twradjustmentWageConformation" path= ".twrwagerptcompletedsuccessful" redirect="false"
 * @struts.action-set-property value = "true" property = "load"
 * @struts.action-set-property value = "totalNotSameAsTR,back,noTaxReport,backtoverifytotalwage,totalSameAsTR,twrtaxreportadjustment,twradjustmentWageConformation" property = "forwards"               
 * @struts.action-set-property value = "/twrverifytotalwages,/twrtaxreportadjustmentmethod,/twrlinkwageandtaxreport,/twrverifytotalwages,/twrsubmitcompleted,/twrtaxreportadjustment,/twrwagerptcompletedsuccessful" property =
 *                             "preLoadPath"              
 * @struts.action-forward name="continue" path= ".twrupdatetaxreportconfirm" redirect="false"
 * @struts.action-forward name="browse" path= ".twrupdatetaxreportconfirm" redirect="false"
 * @struts.action-forward name="back" path= ".twrtaxreportadjustmentmethod" redirect="false"
 * @struts.action-forward name="backtoverifytotalwage" path= ".twrverifytotalwages" redirect="false"
 * @struts.action-set-property value =
 *                             "gov.state.uim.twr.service.TaxMiscellaneousService"
 *                             property = "bizServiceClass"
 */
public class TWRUploadAdjustmentWageFileAction extends BaseLookUpDispatchAction {
	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(TWRUploadAdjustmentWageFileAction.class);


	/**
	 * This method takes care of loading the data for 'Detailed Employee Wages - Upload File' screen
	 *
	 * @param   ActionMapping mapping actions for the screen
	 * @param	ActionForm mapping form for the screen
	 * @param	HttpServletRequest Request object form the screen
	 * @param	HttpServletResponse Response object.
	 * @return  the collection of data objects(IObjectAssembly).
	 * @throws  java.util.IOException.ServeletExceprtion
	 */
	public void load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method load");
		}
		
		IObjectAssembly objAssembly = getObjectAssemblyFromSession(request);
		RegisteredEmployerData employerData = (RegisteredEmployerData) objAssembly.getFirstComponent(RegisteredEmployerData.class);
		EmployerAccountData employerAccountData = (EmployerAccountData)objAssembly.getFirstComponent(EmployerAccountData.class,true);
		objAssembly.getFirstComponent(TaxWageReportAttributesData.class);
		
		TWRUploadAdjustmentWageFileForm currform = (TWRUploadAdjustmentWageFileForm)form;
		currform.setEmplName(TWRUtil.getEmployerName(employerData, employerAccountData));
		
		currform.setEmplAcntNumb(objAssembly.getEan());	
		
		currform.setQuaterYear(TWRUtil.quarterYearToFormattedString( (Integer) objAssembly.getData(TaxConstants.QUARTER),
				(Integer) objAssembly.getData(TaxConstants.YEAR) ));
		
		InformationSubmitedbyData infoSubmittedByData = 
			(InformationSubmitedbyData)objAssembly.getFirstComponent(InformationSubmitedbyData.class);
		//currform.setSubmittedBy(infoSubmittedByData.getName());
		if(infoSubmittedByData != null){
			currform.setSubmittedBy(infoSubmittedByData.getFirstName() + ViewConstants.BLANK_SPACE+
				infoSubmittedByData.getMiddleInitial() + ViewConstants.BLANK_SPACE+
				infoSubmittedByData.getLastName());
		}
//		Added to set the back action in the current flow	
		String prevForward = GlobalConstants.EMPTY_STRING;
		TaxWageNavigationBean twrNavigationBean = (TaxWageNavigationBean)objAssembly.getFirstBean(TaxWageNavigationBean.class);
		if(twrNavigationBean != null){
			prevForward = twrNavigationBean.getPreviousScreenName();
			setPageFlowPreviousActionPath(request, mapping, prevForward);
			objAssembly.removeBean(TaxWageNavigationBean.class);
		}
		if((Integer)objAssembly.getData(TaxConstants.ADJU_REPORT_TYPE) == TaxConstants.ADJU_REPORT_TYPE_TAX_WAGE_REPORT.intValue())
		{	
			request.setAttribute("SHOWNEXT", "true");
		} 
	}
	/**
	 * Method to map buttons and corresponding actions
	 *
	 * @return  Map collection of buttons and corresponding actions.
	 */
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		
		map.put("access.submit", "cont");
		map.put("access.next", "cont");
		return map;
	}
	/**
	 * Method to map actions and corresponding service methods
	 *
	 * @return  Map collection of actions and corresponding services.
	 */
	protected Map getServiceKeyMethodName() {
		Map map = new HashMap();
		map.put("cont", "deleteTWRWageReportForEanQuarterAndYear");
		return map;
	}
	

	

	/**
	 * This method processes form data for objectassembly to be used in service/post method for 'Continue' action
	 *
	 * @param   ActionMapping mapping actions for the screen
	 * @param	ActionForm mapping form for the screen
	 * @param	HttpServletRequest Request object form the screen
	 * @param	HttpServletResponse Response object.
	 * @return  the collection of data objects(IObjectAssembly).
	 * @throws  java.util.IOException.ServeletExceprtion
	 */
	public IObjectAssembly precont(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method precont");
		}

		// session processing
		IObjectAssembly objAssembly = getObjectAssemblyFromSession(request);
		WageReportData wageReportData = (WageReportData) objAssembly.getFirstComponent(WageReportData.class);
		
		TWRUploadAdjustmentWageFileForm currForm = (TWRUploadAdjustmentWageFileForm)form;
		uploadFile(currForm.getUploadFormat(), currForm.getFileName(), currForm.getEmplAcntNumb(), currForm.getQuaterYear());
		WageReportData newWageReportData = (WageReportData)objAssembly.getFirstComponent(WageReportData.class);
		newWageReportData.setTotalWage(currForm.getTotalWage());
		newWageReportData.setFileFormat(currForm.getUploadFormat());
		newWageReportData.setDetailedWageUseInd(GlobalConstants.DETAILED_WAGE_NOT_READY_FOR_USE);
		newWageReportData.setUpdatedBy(getUserId(request));
		newWageReportData.setCreatedBy(getUserId(request));
		
		//newWageReportData.setEmployerID(currForm.getEmplAcntNumb())
		objAssembly.addData(TaxConstants.EAN, currForm.getEmplAcntNumb());
		objAssembly.addData(TaxConstants.EMPLOYER_ID, wageReportData.getEmployerID());
		objAssembly.addData(TaxConstants.QUARTER, wageReportData.getQuarter());
		objAssembly.addData(TaxConstants.YEAR, wageReportData.getYear());
				
		return objAssembly;		
	}

	
	/**
	 * This method uplaods the file to a pre defined location in file server for the processWageReport
	 * batch to save the individual wage items
	 * @param avFile
	 * @param ean
	 * @param yearQuarter
	 * @throws IOException
	 */
	private void  uploadFile(String aFileFormat, FileItem avFile, String ean, String yearQuarter)  throws IOException{
		  byte[] tFileData = avFile.getFileData(); 
		  String basePath = ApplicationProperties.WAGE_REPORT_BASE_PATH_LOCATION;
		  File baseDir = new File(basePath);
		  if(!baseDir.exists()){
			  baseDir.mkdirs();
	      }
		  String extensionPath = basePath + ApplicationProperties.WAGE_REPORT_FILE_UPLOAD_LOCATION;
	      File newDir = new File(extensionPath);
	      if(!newDir.exists()){
	    	  newDir.mkdirs();
	      }
	      int[] quarterYearArr = TWRUtil.quarterYearFromFormattedString(yearQuarter);
	    	  
	      String uploadedFileName = ean + GlobalConstants.HYPHEN_SEPARATOR + 
	      							quarterYearArr[1] + GlobalConstants.HYPHEN_SEPARATOR + 
	      							GlobalConstants.ZERO+quarterYearArr[0]+ GlobalConstants.HYPHEN_SEPARATOR + 
	      							aFileFormat + GlobalConstants.TEXT_FILE_EXTENSION;

	      //CIF_P2_01129 - Veracode Change Phase 2
	      File newFile = new File(StringUtility.removeCRAndLF(extensionPath), StringUtility.removeCRAndLF(uploadedFileName));
	     //upload the file to the directory specified
	      OutputStream bos = new FileOutputStream(newFile); 
	      
	      bos.write(tFileData);
	      bos.close();   
	   
	}  
	/**
	 * This method does the post service processing(forwarding to appropriate screen, displaying error message,
	 * etc.)for 'Continue' action
	 *
	 * @param   ActionMapping mapping actions for the screen
	 * @param	ActionForm mapping form for the screen
	 * @param	HttpServletRequest Request object form the screen
	 * @param	HttpServletResponse Response object.
	 * @return  the collection of data objects(IObjectAssembly).
	 * @throws  java.util.IOException.ServeletExceprtion
	 */
	public ActionForward cont(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method cont");
		}
				
		IObjectAssembly objAssembly = super.getObjectAssembly(request);
		WageReportData wageReportData = (WageReportData)objAssembly.getFirstComponent(WageReportData.class);
		//Set the previous screen
		TaxWageNavigationBean twrNavigationBean = (TaxWageNavigationBean)objAssembly.fetchORCreateBean(TaxWageNavigationBean.class);
		twrNavigationBean.setPreviousScreenName(TWRActionClassEnum.TWR_UPLOAD_WAGE_FILE_ACTION.getName());
		
		setObjectAssemblyInSession(request, objAssembly);
		
		// checking for adjustment report type
		 if((Integer)objAssembly.getData(TaxConstants.ADJU_REPORT_TYPE) == TaxConstants.ADJU_REPORT_TYPE_TAX_WAGE_REPORT.intValue())
			{	
			return mapping.findForward("twrtaxreportadjustment");
			}
			
		// Create success code for showing complete successfully screen.
		Object obj[]= {TWRUtil.quarterYearToFormattedString(wageReportData.getQuarter().intValue(),wageReportData.getYear().intValue())};
		objAssembly.setSuccessCode("access.tax.twr.wagereport.fileupload.success.message",obj);
		addSuccessMessage(request,"access.tax.twr.wagereport.fileupload.success.message",TWRUtil.quarterYearToFormattedString(wageReportData.getQuarter().intValue(),wageReportData.getYear().intValue()));
		addSuccessMessage(request,"access.tax.twr.wagereport.fileupload.success.message",TWRUtil.quarterYearToFormattedString(wageReportData.getQuarter().intValue(),wageReportData.getYear().intValue()));
	    setSuccessPageHeading(request,"access.tax.twr.wagereport.success.header");
	    return mapping.findForward("twradjustmentWageConformation");
	}

	
	/**
	 * This method takes care of the go back action generated from 'Detailed Employee Wages - Upload File' screen
	 *
	 * @param   ActionMapping mapping actions for the screen
	 * @param	ActionForm mapping form for the screen
	 * @param	HttpServletRequest Request object form the screen
	 * @param	HttpServletResponse Response object.
	 * @return  ActionForward throws the corresponding jsp mentioned in the mapping..
	 * @throws  java.util.IOException.ServeletExceprtion
	 */
	protected ActionForward cancelled(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method cancelled");
		}
	
		String prevForward = getPageFlowPreviousActionPath(request, mapping);
		
		if ((prevForward != null) && (prevForward.equalsIgnoreCase(TWRActionClassEnum.TWR_VERIFY_TOTAL_WAGE_ACTION.getName()))){
			setPageFlowPreviousActionPath(request, mapping, null);
			return (mapping.findForward("backtoverifytotalwage"));
		}else{
			return (backForward(mapping));
		}
	}

}