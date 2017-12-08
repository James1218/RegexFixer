/**
 * This action class takes care of action performed from 'Detailed Employee Wages - Upload file' screen  
 * @author Tata Consultancy Services
 */
package gov.state.uim.twr.struts;

import org.apache.commons.fileupload.FileItem;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

//CIF_P2_00417
import gov.state.uim.GlobalConstants;
import gov.state.uim.ViewConstants;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.domain.bean.TaxWageNavigationBean;
import gov.state.uim.domain.data.CDSVendorDetailData;
import gov.state.uim.domain.data.EmployerAccountData;
import gov.state.uim.domain.data.InformationSubmitedbyData;
import gov.state.uim.domain.data.RegisteredEmployerData;
import gov.state.uim.domain.data.TaxWageReportAttributesData;
import gov.state.uim.domain.data.TxReportDueData;
import gov.state.uim.domain.enums.ReportDueCategoryEnum;
import gov.state.uim.domain.enums.TWRActionClassEnum;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.struts.BaseLookUpDispatchAction;
import gov.state.uim.tax.TaxConstants;
import gov.state.uim.twr.TWRUtil;
import gov.state.uim.util.lang.StringUtility;

/**
 * @struts.action path="/twruploadwagefile" name="twruploadwagefileform" scope="request" input=".twruploadwagefile" validate="true" parameter="method"
 * @struts.action-set-property value "true" property = "onErrorSamePage"
 * @struts.action-set-property value = "true" property = "load"
 * @struts.action-forward name="totalNotSameAsTR" path= ".twrverifytotalwages" redirect="false"
 * @struts.action-forward name="totalSameAsTR" path= ".twrsubmitcompleted" redirect="false"
 * @struts.action-forward name="noTaxReport" path= ".twrlinkwageandtaxreport" redirect="false"
 * @struts.action-set-property value = "true" property = "load"
 * @struts.action-set-property value = "continue,totalNotSameAsTR,back,noTaxReport,backtoverifytotalwage,totalSameAsTR,twrtaxreportadjustmentmethod" property = "forwards"
 * @struts.action-set-property value = "/twruploadedemployeewageswarnings,/twrverifytotalwages,/twrtaxreportmethod,/twrlinkwageandtaxreport,/twrverifytotalwages,/twrsubmitcompleted,/twrtaxreportadjustmentmethod" property = "preLoadPath"
 * @struts.action-forward name="continue" path= ".twruploadedemployeewageswarnings" redirect="false"
 * @struts.action-forward name="browse" path= ".twrupdatetaxreportconfirm" redirect="false"
 * @struts.action-forward name="back" path= ".twrtaxreportmethod" redirect="false"
 * @struts.action-forward name="backtoverifytotalwage" path= ".twrverifytotalwages" redirect="false"
 * @struts.action-forward name="twrtaxreportadjustmentmethod" path= ".twrtaxreportadjustmentmethod" redirect="false"
 * @struts.action-set-property value = "gov.state.uim.twr.service.TaxWageReportService" property = "bizServiceClass"
 */
public class TWRUploadWageFileAction extends BaseLookUpDispatchAction {
	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(TWRUploadWageFileAction.class);
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
		TaxWageReportAttributesData taxWageReportAttrData = (TaxWageReportAttributesData)objAssembly.getFirstComponent(TaxWageReportAttributesData.class);
		
		TWRUploadWageFileForm currform = (TWRUploadWageFileForm)form;
		currform.setEmplName(TWRUtil.getEmployerName(employerData, employerAccountData));
		
		currform.setEmplAcntNumb(objAssembly.getEan());	
		//CIF_P2_00417 Start
		if(taxWageReportAttrData.getQuarterYearData()!=null) { 
			currform.setQuaterYear(TWRUtil.quarterYearToFormattedString(taxWageReportAttrData.getQuarterYearData().getQuarter(), taxWageReportAttrData.getQuarterYearData().getYear()));
		}
		//CIF_P2_00417 End
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
		
		//CIF_P2_00417 Start
		if(employerData!=null){
			currform.setTradeName(employerData.getTradeName());
		}
		TxReportDueData reportDueData=(TxReportDueData)objAssembly.getFirstComponent(TxReportDueData.class);
		if(reportDueData!=null){
			currform.setQuaterYear(reportDueData.getQuarter().toString().
					concat(GlobalConstants.QUARTER_YEAR_SEPARATOR).concat(reportDueData.getYear().toString()));
			
			currform.setReportType(ReportDueCategoryEnum.getEnum(reportDueData.getReportCategory().trim()).getDescription());
            
            if(reportDueData.getEffectiveStartDate()!=null && reportDueData.getEffectiveEndDate()!=null){
                
                String effectivePeriod = TWRUtil.getEffectivePeriodDates(reportDueData.getEffectiveStartDate(), reportDueData.getEffectiveEndDate());
                
                currform.setEffectivePeriod(effectivePeriod);
            }
		}
		CDSVendorDetailData cdsvendorDetail=(CDSVendorDetailData)objAssembly.getFirstComponent(CDSVendorDetailData.class);
		if(cdsvendorDetail!=null){
			currform.setCdsVendorName(cdsvendorDetail.getCompanyName());
		}
		else 
		{
			currform.setCdsVendorName(GlobalConstants.NA);
		}
		//CIF_P2_00417 End
	}
	/**
	 * Method to map buttons and corresponding actions
	 *
	 * @return  Map collection of buttons and corresponding actions.
	 */
	protected Map<String,String> getKeyMethodMap() {
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("access.submit", "cont");
		return map;
	}
	/**
	 * Method to map actions and corresponding service methods
	 *
	 * @return  Map collection of actions and corresponding services.
	 */
	protected Map<String,String> getServiceKeyMethodName() {
		Map<String,String> map = new HashMap<String,String>();		
		map.put("cont", "validateUploadedFile");
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
    public IObjectAssembly precont(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Start of method precont");
        }

        // session processing
        IObjectAssembly objAssembly = getObjectAssemblyFromSession(request);
        TWRUploadWageFileForm currForm = (TWRUploadWageFileForm) form;
        // CIF_P2_00417
        if (currForm.getFileName() != null)
        {
            FileItem file = currForm.getFileName();
         // CIF_INT_01895 || Defect_347
            String firstLine = new BufferedReader(new InputStreamReader(currForm.getFileName().getInputStream())).readLine();
            objAssembly.addData("firstLine", firstLine);

            // CIF_INT_02046 || Defect_8420
            if (!file.getContentType().equals("application/vnd.ms-excel") &&
                    (StringUtility.contains(file.getFileName(), ".txt") || StringUtility.contains(file.getFileName(), ".TXT")) &&
                    !(StringUtility.contains(file.getFileName(), ".dat") || StringUtility.contains(file.getFileName(), ".DAT")) &&
                    !(StringUtility.equalsIgnoreCase(String.valueOf(firstLine.charAt(0)), "S") ||
                            StringUtility.equalsIgnoreCase(String.valueOf(firstLine.charAt(0)), "A") ||
                            StringUtility.equalsIgnoreCase(String.valueOf(firstLine.charAt(0)), "B") ||
                            StringUtility.equalsIgnoreCase(String.valueOf(firstLine.charAt(0)), "E") ||
                            StringUtility.equalsIgnoreCase(String.valueOf(firstLine.charAt(0)), "T") ||
                            StringUtility.equalsIgnoreCase(String.valueOf(firstLine.charAt(0)), "F") ||
                            (StringUtility.equalsIgnoreCase(String.valueOf(firstLine.charAt(0)), "4") && firstLine.length() == 72 ) ||
                            (StringUtility.equalsIgnoreCase(String.valueOf(firstLine.charAt(0)), "R") && StringUtility.equalsIgnoreCase(String.valueOf(firstLine.charAt(1)), "S"))))
            {
                objAssembly.addData("fileType", "tabDelimitedTxtFile");
                objAssembly.addData("excelInputStream", (new BufferedReader(new InputStreamReader(currForm.getFileName().getInputStream()))));
            }
            else if (file.getContentType().equals("application/vnd.ms-excel"))
            {
                objAssembly.addData("fileType", "excel");
                objAssembly.addData("excelInputStream", (new BufferedReader(new InputStreamReader(currForm.getFileName().getInputStream()))));
            }
            else
            {
                objAssembly.addData("fileType", null);
            }
            LineNumberReader lnr = new LineNumberReader(new BufferedReader(new InputStreamReader(currForm.getFileName().getInputStream())));
            lnr.skip(Long.MAX_VALUE);
            objAssembly.addData("employeeOnFileCount", lnr.getLineNumber());
            lnr.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(currForm.getFileName().getInputStream()));
            objAssembly.addData("uploadFileStream", br);

            objAssembly.addData("uploadedFileName", currForm.getFileName().getFileName());

            BufferedReader brEmployerCount = new BufferedReader(new InputStreamReader(currForm.getFileName().getInputStream()));
            int count = 0;
            while ((brEmployerCount.readLine()) != null)
            {
                count++;
            }
            objAssembly.addData("employeeOnFileCount", count);
            // CIF_P2_00933 - END
        }

        return objAssembly;
    }
	
	public ActionForward cont(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method cont");
		}
		
		IObjectAssembly objAssembly = super.getObjectAssembly(request);
		if (objAssembly.hasBusinessError()) {
			super.addBusinessErrorInRequest(request, objAssembly.getFirstBusinessErrorInfo().getErrorCode(),
					objAssembly.getFirstBusinessErrorInfo().getErrorCode());
			objAssembly.removeBusinessError();
			return mapping.getInputForward();
		}		
		setObjectAssemblyInSession(request, objAssembly);
		return (super.continueForward(mapping));	
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
		IObjectAssembly objAssembly =getObjectAssemblyFromSession(request);
		String prevForward = getPageFlowPreviousActionPath(request, mapping);
		
		if ((prevForward != null) && (prevForward.equalsIgnoreCase(TWRActionClassEnum.TWR_VERIFY_TOTAL_WAGE_ACTION.getName()))){
			setPageFlowPreviousActionPath(request, mapping, null);
			return (mapping.findForward("backtoverifytotalwage"));
		}else if(null!=objAssembly.getData(TaxConstants.REPLACEMENT_METHOD)){
            // CIF_INT_01054 || Defect_7982
            return (mapping.findForward("twrtaxreportadjustmentmethod"));
		}
		else{
			return (backForward(mapping));
		}
	}

}