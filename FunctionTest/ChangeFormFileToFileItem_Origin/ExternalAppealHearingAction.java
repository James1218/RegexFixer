package gov.state.uim.app.struts;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.domain.bean.ExternalAppealHearingBean;
import gov.state.uim.framework.exception.BaseApplicationException;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.workflow.WorkflowConstants;
import gov.state.uim.framework.workflow.bean.WorkflowItemBean;
import gov.state.uim.framework.workflow.struts.BaseWorkflowLookUpDispatchAction;
import gov.state.uim.util.lang.DateFormatUtility;
import gov.state.uim.util.lang.StringUtility;
/**
 * @struts.action path="/externalappealhearing" name="externalappealhearingform" scope="request"
 *                input=".externalappealhearing" validate="true" parameter="method"
 * 
 * @struts.action-set-property value = "true" property = "load"
 * @struts.action-set-property value = "" property = "preLoadPath"
 * @struts.action-set-property value = "getAppealInformationForTranscription" property = "loadServiceMethod"
 * @struts.action-forward	name="back" path= ".individualworkqueue" redirect="false"
 * @struts.action-set-property
 *   value="true" property="onErrorSamePage"
 * @struts.action-set-property value =
 *                             "gov.state.uim.app.service.ProcessExternalResponsesService"
 *                             property = "bizServiceClass"
 */
public class ExternalAppealHearingAction extends BaseWorkflowLookUpDispatchAction{
	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(ExternalAppealHearingAction.class);

	@Override
	protected Map<String, String> getServiceKeyMethodName() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("submit", "updateExternalAppByDocFile");
		return map;
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("access.submit", "submit");
		//map.put("access.back", "back");
		return map;
	}

	@Override
	public void load(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
			throws IOException, ServletException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method load");
		}
		
		ExternalAppealHearingForm currentForm = (ExternalAppealHearingForm)form;
		IObjectAssembly objectAssembly = getObjectAssemblyFromSession(request,true);
		
		// set the session time out to this request - Def # 7585
		request.getSession(false).setMaxInactiveInterval(GlobalConstants.NUMERIC_TWO * GlobalConstants.NUMERIC_SIXTY * GlobalConstants.NUMERIC_SIXTY);
		
		WorkflowItemBean workflowItemBean = getWorkflowItemBean(request);
		String appealId = null;
		if(workflowItemBean!=null){
			appealId = (String)workflowItemBean.getBusinessKey();
			objectAssembly.addBean(workflowItemBean);
		}
		objectAssembly.setPrimaryKey(Long.valueOf(appealId));
		objectAssembly = super.executeLoadServiceMethod(mapping,objectAssembly,request);
		ExternalAppealHearingBean newBean = (ExternalAppealHearingBean) objectAssembly.getFirstBean(ExternalAppealHearingBean.class);
		currentForm.setAppellant(newBean.getAppeallant());
		currentForm.setAppealDate(DateFormatUtility.format(DateFormatUtility.parse(newBean.getAppealDate(),GlobalConstants.DB_DATE_FORMAT)));
		currentForm.setExternalAgency(newBean.getExternalAgency());
        currentForm.setDocketNumber(newBean.getDocketNumber());
		if (StringUtility.isNotBlank(newBean.getClaimantSsn())){
			currentForm.setClaimantOrEmployerFlag(GlobalConstants.DB_ANSWER_YES);
			currentForm.setClaimantName(newBean.getClaimantName());
			currentForm.setClaimantSsn(newBean.getClaimantSsn());
		}else if (StringUtility.isNotBlank(newBean.getEmployerEan())){
			currentForm.setClaimantOrEmployerFlag(GlobalConstants.DB_ANSWER_NO);
			currentForm.setEmployerEan(newBean.getEmployerEan());
			currentForm.setEmployerName(newBean.getEmployerName());
		}
	    setObjectAssemblyInSession(request,objectAssembly);
	}
	
	/**
	 * pre method for submit button, prepare transcript if yes is selected
	 * saving the file in the file system
	 *  
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return IObjectAssembly
	 * @throws IOException throws IOException
	 * @throws ServletException throws ServletException
	 * @throws BaseApplicationException throws BaseApplicationException
	 */
	public IObjectAssembly presubmit(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
			throws IOException, ServletException, BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method presubmit");
		}
	
		IObjectAssembly objectAssembly = getObjectAssemblyFromSession(request,true);
		ExternalAppealHearingBean newBean = (ExternalAppealHearingBean) objectAssembly.getFirstBean(ExternalAppealHearingBean.class);
		ExternalAppealHearingForm currentForm = (ExternalAppealHearingForm)form;
		if (StringUtility.isNotBlank(newBean.getClaimantSsn())){
			objectAssembly.setSsn(newBean.getClaimantSsn());
		}else if (StringUtility.isNotBlank(newBean.getEmployerEan())){
			objectAssembly.setEan(newBean.getEmployerEan());
		}
		//newBean.setAvFile(currentForm.getFileName());
		
		//CIF_01121 start
		String transcriptYesNo = currentForm.getTranscriptYesNo();
		
		newBean.setTranscriptYesNo(transcriptYesNo);
		
		if(transcriptYesNo.equals(GlobalConstants.DB_ANSWER_YES)){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

			//this.uploadFile(currentForm.getFileName(),newBean);
		}
		
		
		final WorkflowItemBean wfItem = super.getWorkflowItemBean(request);		
		Map<String, Object> mapValues = new HashMap<String, Object>();

		mapValues.put(WorkflowConstants.USER_ID, super.getUserId(request));
		mapValues.put(WorkflowConstants.TASK_ID, wfItem.getPersistentOid());
		
		objectAssembly.addData(GlobalConstants.WI_MAP_VALUES, mapValues);
		
		
		//CIF_01121 end
		return objectAssembly;
	}
	
	/**
	 * method for submit button, prepare transcript if yes is selected
	 * saving the file in the file system and navigation to the success screen
	 *  
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return IObjectAssembly
	 * @throws IOException throws IOException
	 * @throws ServletException throws ServletException
	 * @throws BaseApplicationException throws BaseApplicationException
	 */
	public ActionForward submit(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
	throws IOException, ServletException, BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method submit");
		}
		
		//IObjectAssembly objectAssembly = getObjectAssemblyFromSession(request,true);
		super.setSuccessPageHeading(request, "access.app.externalappealhearing.page.successtitle");
		super.addSuccessMessage(request,"access.app.externalappealhearing.success");
		return super.successMessageForward(mapping);
		
	}
	
		
	@Override
	protected ActionForward cancelled(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response)
			throws BaseApplicationException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method cancelled");
		}
		
		return super.backForward(mapping);
	}
	
	/**
	 * CIF_01121
	 * saving the file in the local system. Need to modify the method once the saving file into
	 * the file system is done
	 * 
	 * @param avFile FormFile
	 * @param newBean ExternalAppealHearingBean
	 * @throws IOException throws IOException
	 */
	private void uploadFile(final FormFile avFile,final ExternalAppealHearingBean newBean) throws IOException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method uploadFile");
		}
		 
		/* CIF_01121
		 * commented need to work on this once the functionality for
		 * saving the file in the file system is done
		 * now saving the file in the local system*/
				
		//FormFile avFile = newBean.getAvFile();
    	String uploadFormat = "";
		//String nameOfFileWithoutExtn = "";
		InputStream inputStream = null;
		StringTokenizer stoken = new StringTokenizer(avFile.getFileName(), ".");
		if (stoken.hasMoreTokens()){
			 stoken.nextToken();
			 uploadFormat = stoken.nextToken();
		}
		if (GlobalConstants.DOC_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
			 byte[] fileData = avFile.getFileData();
			 String fileName = avFile.getFileName();
			 String basePath = "C:/UploadDoc";
			 File baseDir = new File(basePath);
			 if(!baseDir.exists()){
				  baseDir.mkdirs();
		     }
			//CIF_01395 Veracode Change
			 String uploadedFileName =basePath + "/"+fileName ;
			 ArrayList<String> filename = new ArrayList<String>();
			 filename.add(uploadedFileName);
			 File newFile = new File(filename.get(0));
			 //File newFile = new File(uploadedFileName);
			 ////CIF_01395 Veracode Change - End
			 inputStream = avFile.getInputStream();
			 newBean.setInputStream(inputStream);
	         //upload the file to the directory specified
		     OutputStream fileTobeWritten = new FileOutputStream(newFile);
		     fileTobeWritten.write(fileData);
		     fileTobeWritten.close();
		}
	}

}