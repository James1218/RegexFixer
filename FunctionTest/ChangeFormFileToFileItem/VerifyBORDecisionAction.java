package gov.state.uim.app.struts;

import org.apache.commons.fileupload.FileItem;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.ViewConstants;
import gov.state.uim.app.AppealConstants;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.domain.bean.AppealInfoBean;
import gov.state.uim.domain.bean.GeneralBean;
import gov.state.uim.domain.bean.MessageHolderBean;
import gov.state.uim.domain.bean.ReverseDecisionBean;
import gov.state.uim.domain.bean.UploadDocumentIntoDmsBean;
import gov.state.uim.domain.data.AppealData;
import gov.state.uim.domain.data.HearingData;
import gov.state.uim.domain.data.MstAppDecMaster;
import gov.state.uim.domain.enums.AppealDecisionCodeEnum;
import gov.state.uim.domain.enums.BORAppealDecisionEnum;
import gov.state.uim.domain.enums.CorrespondenceCodeEnum;
import gov.state.uim.framework.exception.BaseApplicationException;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.interfaces.report.GenericReportBean;
import gov.state.uim.interfaces.report.struts.BaseReportLookUpDispatchAction;
import gov.state.uim.util.lang.DateFormatUtility;
import gov.state.uim.util.lang.StringUtility;



/**
 * @struts.action path="/verifybordecision" name="verifybordecisionform" scope="session"
 *                input=".verifybordecision" validate="true" parameter="method"
 * @struts.action-set-property value = "true" property = "load"
 * @struts.action-set-property value "true" property = "onErrorSamePage"
 * @struts.action-forward name="affirmback" path= ".boardofreviewaffirmdecision" redirect="false"
 * @struts.action-forward name="reverseback" path= ".boardofreviewreversedecision" redirect="false"
 * @struts.action-forward name="samepage" path= ".verifydecision" redirect="false"
 * 
 * @struts.action-set-property value = "true" property = "load"
 * @struts.action-set-property value = "affirmback,reverseback" property = "forwards"
 * @struts.action-set-property value = "/boardofreviewaffirmdecision,/boardofreviewreversedecision" property = "preLoadPath"
 * 
 * @struts.action-set-property
 * 		value = "getBORDecisionLetterReportParams" property = "loadServiceMethod"
 *
 * @struts.action-set-property
 * 		value = "gov.state.uim.app.service.BORService"
 *      property = "bizServiceClass"
 */
public class VerifyBORDecisionAction extends BaseReportLookUpDispatchAction {
	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(VerifyBORDecisionAction.class);
	public static final String INPUT_STREAM ="inputStream";
	public static final String CONTENT_TYPE ="contentType";
	public static final String DOCUMENT_NAME ="documentName";
	public static final String DOCUMENT_INFO ="documentInfo";
	public static final String UPLOAD_DOCUMENT ="uploademployerdocuments";
	public static final String DELETE_DOCUMENT_ID ="deleteDocumentID";
	public static final String DOCUMENT_MAP_LIST ="documentIDMaplist";
	public static final String DOCUMENT_NAMEID_MAP_LIST ="documentNameIDMapList";
	public VerifyBORDecisionAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method load");
		}
		
		VerifyBORDecisionForm thisForm = (VerifyBORDecisionForm)form;
		
		IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request, true);
		
		// set the session time out to this request - Def # 7585
        // session time extended to 3 hrs as per defect 9102
		request.getSession(false).setMaxInactiveInterval(3*60*60);
		
		objAssembly = super.executeLoadServiceMethod(mapping,objAssembly,request);
		

		
		AppealInfoBean infoBean = (AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
		objAssembly.getFirstComponent(MstAppDecMaster.class);
objAssembly.getFirstComponent(HearingData.class);
		
		// add the params that will be used for letter
//		builder.addParam("reviewDate", "08/01/2007");
//		builder.addParam("notificationDate", "08/11/2007");
//		builder.addParam("noticeOfAppeal", infoBean.getAppealDate());
//		builder.addParam("hearingMode", "Telephone Hearing");
//		builder.addParam("hearingDate", "10/01/2007");
//		builder.addParam("hearing", "10/11/2007");
//		builder.addParam("lawDescription", "BLAH BLAH BLAH");
//		builder.addParam("decision", additionalRemarks);
		/*CIF_02727 || DEFECT_3847|| Commented as not required*/

		/*if(appealDecisionMaster!=null){
			thisForm.setCaseHistoryParagraph(builder.buildCaseHistoryParagraph(appealDecisionMaster.getAppDecMasterId()));
			thisForm.setReasonAndConclusionParagraph(builder.buildDecisionReasonParagraph(appealDecisionMaster.getAppDecMasterId()));
			thisForm.setFactFindingParagraph(builder.buildFactFindingParagraph(appealDecisionMaster.getAppDecMasterId()));
			thisForm.setIssueParagraph(builder.buildIssueParagraph(appealDecisionMaster.getAppDecMasterId()));
			thisForm.setAppealRightsParagraph(builder.buildAppealRightsParagraph(appealDecisionMaster.getAppDecMasterId()));
			thisForm.setDecisionParagraph(builder.buildDecisionParagraph(appealDecisionMaster.getAppDecMasterId()));
		}
		else{
			String decisionValue = (String)objAssembly.getData(AppealConstants.BOR_REVIEW_DECISION_VALUE);
			if (BORAppealDecisionEnum.REVERSE.getName().equalsIgnoreCase(decisionValue)
					||BORAppealDecisionEnum.MODIFY.getName().equalsIgnoreCase(decisionValue)){
				thisForm.setCaseHistoryParagraph(builder.buildCaseHistoryParagraph(Long.valueOf(7)));
				thisForm.setReasonAndConclusionParagraph(builder.buildDecisionReasonParagraph(Long.valueOf(7)));
				thisForm.setFactFindingParagraph(builder.buildFactFindingParagraph(Long.valueOf(7)));
				thisForm.setIssueParagraph(builder.buildIssueParagraph(Long.valueOf(7)));
				thisForm.setAppealRightsParagraph(builder.buildAppealRightsParagraph(Long.valueOf(7)));
				thisForm.setDecisionParagraph(builder.buildDecisionParagraph(Long.valueOf(7)));
			}else if (BORAppealDecisionEnum.AFFIRM_WITH_EDIT.getName().equalsIgnoreCase(decisionValue)){
				thisForm.setCaseHistoryParagraph(builder.buildCaseHistoryParagraph(Long.valueOf(6)));
				thisForm.setReasonAndConclusionParagraph(builder.buildDecisionReasonParagraph(Long.valueOf(6)));
				thisForm.setFactFindingParagraph(builder.buildFactFindingParagraph(Long.valueOf(6)));
				thisForm.setIssueParagraph(builder.buildIssueParagraph(Long.valueOf(6)));
				thisForm.setAppealRightsParagraph(builder.buildAppealRightsParagraph(Long.valueOf(6)));
				thisForm.setDecisionParagraph(builder.buildDecisionParagraph(Long.valueOf(6)));
			}else if (BORAppealDecisionEnum.WITHDRAWAL_ON_RECORD.getName().equalsIgnoreCase(decisionValue)){
				thisForm.setCaseHistoryParagraph(builder.buildCaseHistoryParagraph(Long.valueOf(6)));
				thisForm.setReasonAndConclusionParagraph(builder.buildDecisionReasonParagraph(Long.valueOf(6)));
				thisForm.setFactFindingParagraph(builder.buildFactFindingParagraph(Long.valueOf(6)));
				thisForm.setIssueParagraph(builder.buildIssueParagraph(Long.valueOf(6)));
				thisForm.setAppealRightsParagraph(builder.buildAppealRightsParagraph(Long.valueOf(6)));
				thisForm.setDecisionParagraph(builder.buildDecisionParagraph(Long.valueOf(6)));
			}
//			thisForm.setCaseHistoryParagraph(builder.buildCaseHistoryParagraph(Long.valueOf(6)));
//			thisForm.setReasonAndConclusionParagraph(builder.buildDecisionReasonParagraph(Long.valueOf(6)));
//			thisForm.setFactFindingParagraph(builder.buildFactFindingParagraph(Long.valueOf(6)));
//			thisForm.setIssueParagraph(builder.buildIssueParagraph(Long.valueOf(6)));
//			thisForm.setAppealRightsParagraph(builder.buildAppealRightsParagraph(Long.valueOf(6)));
//			thisForm.setDecisionParagraph(builder.buildDecisionParagraph(Long.valueOf(6)));

		}*/
		thisForm.setAppInfoBeanDisp(infoBean);
		return;
	}
	
	
	@Override
	protected Map getServiceKeyMethodName() {
		Map map = new HashMap();
//		map.put("submit","saveReverseDecision");
		map.put("submit","saveBORReverseAffirmModifyDecision");
//		map.put("viewdecisionletter","saveReverseDecision");
		return map;
	}
	
	
	
	@Override
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("access.submit", "submit");
		map.put("access.viewdecisionletter", "generateReport");
		return map;
	}
	
	@Override
	protected String getGenerateReportButtonKey(){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getGenerateReportButtonKey");
		}
		return "access.viewdecisionletter";
	}
	
	@Override
	public IObjectAssembly pregenerateReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method pregenerateReport");
		}
		
		IObjectAssembly objAssembly = getObjectAssemblyFromSession(request);
		
		return objAssembly;
	}
	
	@Override
	public ActionForward generateReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method generateReport");
		}
		
		IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request, true);
		VerifyBORDecisionForm thisForm = (VerifyBORDecisionForm)form;
//		AppealInfoBean infoBean = (AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
		String sepIdentifier=AppealConstants.DECISION_LETTER_PARA_SEP;
		AppealData appealData= (AppealData)objAssembly.getFirstComponent(AppealData.class, true);
		
		objAssembly = super.executeLoadServiceMethod(mapping,objAssembly,request);
		
		
		GenericReportBean rptBean = new GenericReportBean();
		rptBean.setReportName(CorrespondenceCodeEnum.VIEW_BOR_DECISION_LETTER_AFFIRM.getName());
		rptBean.addReportParam("caseHistoryParam",getArrayListToString(thisForm.getCaseHistoryParagraph(), sepIdentifier));
		rptBean.addReportParam("issueParam", getArrayListToString(thisForm.getIssueParagraph(), sepIdentifier));
		rptBean.addReportParam("factFindingParam", getArrayListToString(thisForm.getFactFindingParagraph(), sepIdentifier));
		rptBean.addReportParam("reasoningAndConclusionParam", getArrayListToString(thisForm.getReasonAndConclusionParagraph(), sepIdentifier));
		rptBean.addReportParam("decisionParam", getArrayListToString(thisForm.getDecisionParagraph(), sepIdentifier));
		rptBean.addReportParam("appealRightsParam", getArrayListToString(thisForm.getAppealRightsParagraph(), sepIdentifier));
		rptBean.addReportParam("ParamMailDate",DateFormatUtility.format(new Date(),GlobalConstants.DATE_FORMAT));
		rptBean.addReportParam("APPEALID",String.valueOf(appealData.getAppealId()));
		return super.generateReport(mapping, form,	request,  response,rptBean);
	}

	
	public IObjectAssembly presubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method presubmit"); 
		}
		
		IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request, true);
		VerifyBORDecisionForm thisForm = (VerifyBORDecisionForm)form;
		ReverseDecisionBean boardOfReviewReverseDecisionBean = (ReverseDecisionBean)objAssembly.getFirstBean(ReverseDecisionBean.class);
		
		/*Commenetd as to upload the decision letter. */
		/*String sepIdentifier="|%|";
		
		AppealDecisionLetterData decisionLetterData = new AppealDecisionLetterData();
		boardOfReviewReverseDecisionBean.setProofReadingFlag(thisForm.getProofReadingFlag());
		boardOfReviewReverseDecisionBean.setAppealRightsParagraph(getArrayListToString(thisForm.getAppealRightsParagraph(), sepIdentifier));
		boardOfReviewReverseDecisionBean.setCaseHistoryParagraph(getArrayListToString(thisForm.getCaseHistoryParagraph(), sepIdentifier));
		boardOfReviewReverseDecisionBean.setDecisionReasonParagraph(getArrayListToString(thisForm.getDecisionParagraph(), sepIdentifier));
		boardOfReviewReverseDecisionBean.setFactFindingParagraph(getArrayListToString(thisForm.getFactFindingParagraph(), sepIdentifier));*/
		//boardOfReviewReverseDecisionBean.setIssueParagraph("21232323");
		//boardOfReviewReverseDecisionBean.setReasonAndConclusionParagraph("gdsgdsg");
		//boardOfReviewReverseDecisionBean.setDecisionReasonParagraph("131313");
		
		try {
			objAssembly = pdfConvert(mapping, form, request);
		} catch (BaseApplicationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		if (LOGGER.isEnabledFor(Level.ERROR)) {
			LOGGER.error("Exception", e);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		if (LOGGER.isEnabledFor(Level.ERROR)) {
			LOGGER.error("Exception", e);
		}
		}
		AppealInfoBean appealInfoBean = (AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
		if(appealInfoBean!=null && boardOfReviewReverseDecisionBean!=null)
		{
			appealInfoBean.setDecisionMailDate(thisForm.getDecisionMailDate().getValue());
			boardOfReviewReverseDecisionBean.setAppealInfoBean(appealInfoBean);
			
		}
		if(appealInfoBean!=null && appealInfoBean.getDocketNumber()!=null)
		{
			objAssembly.addData("APPLNUM",appealInfoBean.getDocketNumber());
			objAssembly.addData("APPEALID",appealInfoBean.getAppealId());
		}
		if(thisForm.getFileName()!=null)
		{
			objAssembly.addData("documentName",thisForm.getFileName());
			//objAssembly = BusinessDelegate.executeServiceMethod(objAssembly, UploadDocumentService.class.getName(), "insertDocumentinDMS", getUserContext(request));
			
		}
		if(null != thisForm.getInFavorAppellant())
		{
			appealInfoBean.setInFavorAppellant(thisForm.getInFavorAppellant());
		}
		
		/*UploadDocumentIntoDmsBean documentBean = objAssembly.getFirstBean(UploadDocumentIntoDmsBean.class);
		if(documentBean==null)
		{
			documentBean=objAssembly.createANDFetchBean(UploadDocumentIntoDmsBean.class);
		}
        documentBean.setFileName(thisForm.getFileName());
        this.uploadFile(thisForm.getFileName(),documentBean);
        objAssembly.addBean(documentBean, true);*/
		//CIF_P2_00782
        // CIF_03322 || Defect_5607
        GeneralBean applBean=new GeneralBean();
        if(super.isBenefitsFlow(request)){
        	applBean.setParam1(ViewConstants.YES);
            objAssembly.addData(ViewConstants.CURRENT_APPLICATION_BENEFIT, ViewConstants.CURRENT_APPLICATION_BENEFIT);
        }else{
        	applBean.setParam1(ViewConstants.NO);
        }
		objAssembly.addBean(applBean,true);
		return objAssembly;
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 * @throws BaseApplicationException
	 */
		private IObjectAssembly pdfConvert(ActionMapping mapping, ActionForm form,
				HttpServletRequest request) throws FileNotFoundException,
				IOException, Exception, BaseApplicationException {
			IObjectAssembly objAssembly = null;
			InputStream inputStream = null;
			String documentName  =null;
			String contentType = null;
			
				//get the object assembly and set the request object to it.
			  // HttpSession documentViewerSession = request.getSession(false);
				objAssembly = getObjectAssemblyFromSession(request, true);
				if(objAssembly == null){
					objAssembly = ServiceLocator.instance.getObjectAssembly();
				}
				VerifyBORDecisionForm uploadForm = (VerifyBORDecisionForm) form;
				inputStream = uploadForm.getFileName()
						.getInputStream();
				documentName = uploadForm.getFileName()
						.getFileName();
				contentType = uploadForm.getFileName()
						.getContentType();
		if (LOGGER.isInfoEnabled()) {
				LOGGER.info("documentName..." + documentName + " , contentType :"+ contentType);
		}

				if(inputStream != null){
					objAssembly.addData(DOCUMENT_NAME, documentName);
				}
				if(documentName != null){
					objAssembly.addData(INPUT_STREAM, inputStream);
				}
				if(contentType != null){
					objAssembly.addData(CONTENT_TYPE, contentType);
				}
		if (LOGGER.isInfoEnabled()) {
				LOGGER.info("contentType ..." + contentType);
		}
			setObjectAssemblyInSession(request, objAssembly);
			//set into the session.
			//documentViewerSession.setAttribute("documentViewerSession",objAssembly.getData("inputStream"));
			
			super.setDataInSession(request, "documentViewerSession",objAssembly.getData("inputStream"));
			
			return objAssembly;
		}
	
	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method submit");
		}
		
		IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request, true);
		AppealInfoBean appealInfoBean =(AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
		super.setSuccessPageHeading(request, "access.app.appealconfirmation");
		super.addSuccessMessage(request,"access.app.borappaldecision.success",appealInfoBean.getDocketNumber());
		return super.successMessageForward(mapping);
		//return (super.continueForward(mapping));
	}
	
	/*CIF_00541,Desc:Modified AFFIRM_WITH_EDIT to AFFIRM.*/
	@Override
	protected ActionForward cancelled(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method cancelled");
		}
		IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request, true);
		String decisionValue = (String)objAssembly.getData(AppealConstants.BOR_REVIEW_DECISION_VALUE);
		if (BORAppealDecisionEnum.REVERSE.getName().equalsIgnoreCase(decisionValue)
				||BORAppealDecisionEnum.MODIFY.getName().equalsIgnoreCase(decisionValue)){
			return mapping.findForward("reverseback");
		}else if (BORAppealDecisionEnum.AFFIRM.getName().equalsIgnoreCase(decisionValue)){
			return mapping.findForward("affirmback");
		}else if (BORAppealDecisionEnum.WITHDRAWAL_ON_RECORD.getName().equalsIgnoreCase(decisionValue)){
			return mapping.findForward("affirmback");
		}else if (AppealDecisionCodeEnum.NTST.getName().equals(decisionValue)
				|| AppealDecisionCodeEnum.OTHR.getName().equals(decisionValue)
				|| AppealDecisionCodeEnum.VACANT.getName().equals(decisionValue)
				|| AppealDecisionCodeEnum.DISMISS.getName().equals(decisionValue)
				|| AppealDecisionCodeEnum.DENY_APPLICATION_REVIEW.getName().equals(decisionValue)
				|| AppealDecisionCodeEnum.CORRECTED.getName().equals(decisionValue)
				|| AppealDecisionCodeEnum.RECONSIDERED.getName().equals(decisionValue)
				|| AppealDecisionCodeEnum.SET_ASIDE.getName().equals(decisionValue)
				|| AppealDecisionCodeEnum.SHOW_CAUSE.getName().equals(decisionValue)){
			return mapping.findForward("affirmback");
		}
		return null;
	}
	
	private String getArrayListToString(ArrayList list, String sepIdentifier){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getArrayListToString");
		}
		String listString="";
		if(list!=null && !list.isEmpty()){
		Iterator t=list.iterator();
		while(t.hasNext()){
			listString += ((MessageHolderBean)t.next()).getMessage();
			if(t.hasNext()) {
				listString +=sepIdentifier;
			}
		}
		listString = StringUtility.removeSpecialCharacter(listString);
		listString = StringUtility.removeEnterCharacter(listString);
		}
		return listString;
		
	}
	
	  private void uploadFile(FileItem avFile,UploadDocumentIntoDmsBean uploadBean) throws IOException
	    {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method uploadFile");
		}
	    	String uploadFormat = "";
			InputStream inputStream = null;
			StringTokenizer stoken = new StringTokenizer(avFile.getFileName(), ".");
			if (stoken.hasMoreTokens()){
				 stoken.nextToken();
				 uploadFormat = stoken.nextToken();
			}
			
			if(GlobalConstants.DOC_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
				 inputStream = avFile.getInputStream();
				 uploadBean.setInputStream(inputStream);
				 uploadBean.setMimeType("application/msword");
				
			}else if(GlobalConstants.WORD_DOCX_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
			     inputStream = avFile.getInputStream();
			     uploadBean.setInputStream(inputStream);
			     uploadBean.setMimeType("application/msword");
		  
			}else if(GlobalConstants.PDF_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
				 inputStream = avFile.getInputStream();
				 uploadBean.setInputStream(inputStream);
				 uploadBean.setMimeType("application/pdf");
				 
				 
		    }else if(GlobalConstants.EXCEL_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
				 inputStream = avFile.getInputStream();
				 uploadBean.setInputStream(inputStream);
				 uploadBean.setMimeType("application/vnd.ms-excel");
				 
		    }else if(GlobalConstants. XLSX_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
				 inputStream = avFile.getInputStream();
				 uploadBean.setInputStream(inputStream);
				 uploadBean.setMimeType("application/vnd.ms-excel");
				 	 
		    }else if(GlobalConstants.JPEG_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
				 inputStream = avFile.getInputStream();
				 uploadBean.setInputStream(inputStream);
				 uploadBean.setMimeType("image/pjpeg");
						 
	        }else if(GlobalConstants.TIFF_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
				 inputStream = avFile.getInputStream();
				 uploadBean.setInputStream(inputStream);
				 uploadBean.setMimeType("image/x-tiff");
				 
	        }else if(GlobalConstants.JPE_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
		        inputStream = avFile.getInputStream();
			    uploadBean.setInputStream(inputStream);
			    uploadBean.setMimeType("image/jpeg");
			 
	        }else if(GlobalConstants.JPG_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
		        inputStream = avFile.getInputStream();
		        uploadBean.setInputStream(inputStream);
		        uploadBean.setMimeType("image/jpeg");
		     
	        }else if(GlobalConstants.JFIF_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
		       inputStream = avFile.getInputStream();
		       uploadBean.setInputStream(inputStream);
		       uploadBean.setMimeType("image/jpeg");
		       
	        }else if(GlobalConstants.TIF_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
		       inputStream = avFile.getInputStream();
		       uploadBean.setInputStream(inputStream);
		       uploadBean.setMimeType("image/x-tiff");
	      }
			
	  }

}