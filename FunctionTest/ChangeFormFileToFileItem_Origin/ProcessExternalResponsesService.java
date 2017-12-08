package gov.state.uim.app.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gov.state.uim.GlobalConstants;
import gov.state.uim.app.AppealConstants;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.dao.IGenericInquiryDAO;
import gov.state.uim.dao.factory.DAOFactory;
import gov.state.uim.domain.Appeal;
import gov.state.uim.domain.AppealDecisionLetterBO;
import gov.state.uim.domain.Claimant;
import gov.state.uim.domain.Correspondence;
import gov.state.uim.domain.Decision;
import gov.state.uim.domain.Employer;
import gov.state.uim.domain.ExternalAppeals;
import gov.state.uim.domain.LateAppeal;
import gov.state.uim.domain.LateDeniedAppeal;
import gov.state.uim.domain.MiscAppeal;
import gov.state.uim.domain.NonApperanceAppeal;
import gov.state.uim.domain.NonMonAppeal;
import gov.state.uim.domain.TaxAppeal;
import gov.state.uim.domain.TaxInterceptAppeal;
import gov.state.uim.domain.bean.AppealCorrespondenceBean;
import gov.state.uim.domain.bean.AppealInfoBean;
import gov.state.uim.domain.bean.ExternalAppealHearingBean;
import gov.state.uim.domain.bean.ProcessExternalAppealBean;
import gov.state.uim.domain.bean.ProcessExternalAppealDecisonBean;
import gov.state.uim.domain.data.AppealData;
import gov.state.uim.domain.data.AppealDecisionDetailData;
import gov.state.uim.domain.data.AppealDecisionLetterData;
import gov.state.uim.domain.data.AppealPartyData;
import gov.state.uim.domain.data.ClaimantDecisionLetterData;
import gov.state.uim.domain.data.CorrespondenceData;
import gov.state.uim.domain.data.DecisionData;
import gov.state.uim.domain.data.EmployerDecisionLetterData;
import gov.state.uim.domain.data.IssueData;
import gov.state.uim.domain.data.LateAppealData;
import gov.state.uim.domain.data.LateDeniedAppealData;
import gov.state.uim.domain.data.MiscAppealData;
import gov.state.uim.domain.data.NonAppearanceAppealData;
import gov.state.uim.domain.data.NonMonAppealData;
import gov.state.uim.domain.data.RegisteredEmployerData;
import gov.state.uim.domain.data.TaxAppealData;
import gov.state.uim.domain.data.TaxInterceptAppealData;
import gov.state.uim.domain.enums.AppealDecisionCodeEnum;
import gov.state.uim.domain.enums.AppealPartyEnum;
import gov.state.uim.domain.enums.AppealPartyTypeEnum;
import gov.state.uim.domain.enums.AppealProofReadingStatusEnum;
import gov.state.uim.domain.enums.AppealStatusEnum;
import gov.state.uim.domain.enums.AppealTypeEnum;
import gov.state.uim.domain.enums.AppellantOrOpponentEnum;
import gov.state.uim.domain.enums.CorrespondenceCodeEnum;
import gov.state.uim.domain.enums.CorrespondenceParameterEnum;
import gov.state.uim.domain.enums.DecisionCodeEnum;
import gov.state.uim.domain.enums.DecisionStatusEnum;
import gov.state.uim.domain.enums.InterceptRefundTypeEnum;
import gov.state.uim.domain.enums.MiscAppIssueCategoryEnum;
import gov.state.uim.domain.enums.MiscAppealLevelEnum;
import gov.state.uim.domain.enums.MiscAppealTypeEnum;
import gov.state.uim.domain.enums.WorkFlowOperationsEnum;
import gov.state.uim.framework.cache.CacheUtility;
import gov.state.uim.framework.exception.BaseApplicationException;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.mail.Email;
import gov.state.uim.framework.mail.config.MailAddressConfig;
import gov.state.uim.framework.mail.config.MailConfigFactory;
import gov.state.uim.framework.mail.config.MailTemplateConfig;
import gov.state.uim.framework.service.BaseService;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.workflow.bean.WorkflowItemBean;
import gov.state.uim.util.AppealUtil;
import gov.state.uim.util.lang.StringUtility;
import gov.state.uim.workflow.transaction.WorkflowTransactionService;


public class ProcessExternalResponsesService extends BaseService implements BIProcessExternalResponsesService {
	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(ProcessExternalResponsesService.class);

	private static final String DESCRIPTION = "description";
	private static final String KEY = "key";
	
	@Override
	public IObjectAssembly getDecisionForALJandBOR(final IObjectAssembly objectAssembly) throws BaseApplicationException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getDecisionForALJandBOR");
		}

		ProcessExternalAppealBean bean = (ProcessExternalAppealBean) objectAssembly.getFirstBean(ProcessExternalAppealBean.class);
		AppealData appealData = Appeal.findAppealandAppealPartySetbyAppealId(bean.getAppealId());
		appealData.getAppealledAgainstDecision();
		Decision decisionBO = Decision.getDecisionAndFieldAgainstAppDataByDecisionId(bean.getDecisionId());
		if (decisionBO.getDecisionData().getFiledAgainestAppealData()!=null){
			throw new BaseApplicationException("error.access.app.externalappeal.appealexists");
		}else {
			
			//objectAssembly.addComponent(appealData,true);
			objectAssembly.addComponent(decisionBO.getDecisionData(),true);
			if (AppealTypeEnum.MISC.getName().equalsIgnoreCase(appealData.getAppealType())){
				MiscAppealData miscAppealData = (MiscAppealData)appealData;
				if (miscAppealData.getCorrespondenceId()!=null){
					Correspondence corrBo = Correspondence.getCorrDataByPkId(miscAppealData.getCorrespondenceId());
					bean.setIssueDesc(CorrespondenceCodeEnum.getEnum(corrBo.getCorrespondenceData().getCorrespondenceCode().trim()).getDescription());
				}
				if (StringUtility.isNotBlank(miscAppealData.getOvpIssue())){
					//miscAppealData.setOvpIssue(miscAppealData.getOvpIssue());
					String issueDescription =(String)CacheUtility.getCachePropertyValue("T_MST_OVP_APP_ISSUES", KEY, miscAppealData.getOvpIssue(), DESCRIPTION);
					bean.setIssueDesc(issueDescription);
				}
				if (StringUtility.isNotBlank(miscAppealData.getProIssue())){

					String issueDescription =(String)CacheUtility.getCachePropertyValue("T_MST_PRO_APP_ISSUES", KEY, miscAppealData.getProIssue(), DESCRIPTION);
					bean.setIssueDesc(issueDescription);
				}
				if (StringUtility.isNotBlank(miscAppealData.getTraTaaIssue())){
					//if TRA or TAA issue is there that will be displayed as issue in external appeal screen
					if (CacheUtility.getCachePropertyValue("T_MST_TAA_APP_ISSUES",KEY,miscAppealData.getTraTaaIssue(), DESCRIPTION)!=null){
						String issueDescription =(String)CacheUtility.getCachePropertyValue("T_MST_TAA_APP_ISSUES", KEY,miscAppealData.getTraTaaIssue(), DESCRIPTION);
						bean.setIssueDesc(issueDescription);
					}else {
						String issueDescription =(String)CacheUtility.getCachePropertyValue("T_MST_TRA_APP_ISSUES", KEY,miscAppealData.getTraTaaIssue(), DESCRIPTION);
						bean.setIssueDesc(issueDescription);
					}
				}
				if(StringUtility.isNotBlank(miscAppealData.getMiscAppIssue()))
				{
					String MiscissueCatStr=MiscAppIssueCategoryEnum.getEnum(miscAppealData.getMiscAppIssue()).getDescription();
					bean.setIssueDesc(MiscissueCatStr);
					
				}
				if (MiscAppealTypeEnum.OTHER_APPEAL.getName().equalsIgnoreCase(miscAppealData.getMiscAppealType())){
					bean.setIssueDesc(AppealConstants.MISC_OTHER);
				}
			}else if (AppealTypeEnum.TAXA.getName().equalsIgnoreCase(appealData.getAppealType())){
				TaxAppealData taxAppealData = (TaxAppealData)appealData;

				String  issueDescription= (String) CacheUtility.getCachePropertyValue("T_MST_DETERMINATION_TYPE",
						KEY, taxAppealData.getTaxAppealIssueCode(),
						DESCRIPTION);
				bean.setIssueDesc(issueDescription);
			}else if (AppealTypeEnum.TAX_INT.getName().equalsIgnoreCase(appealData.getAppealType())){
				// late to Tax Intercept
				//String  issueDescription = AppealConstants.TAXI_ISSUE_DESCRIPTION;
				//CIF_03347||Defect_5739||UAT||Added issue according to Refund Type
				if(appealData instanceof TaxInterceptAppealData)
				{
					TaxInterceptAppealData taxIntData = (TaxInterceptAppealData) appealData;
					if(taxIntData.getTaxInterceptMasterData() !=null)
					{
						String refundType="";
						refundType=taxIntData.getTaxInterceptMasterData().getRefundType();
						if(StringUtility.isBlank(refundType))
						{
							if(refundType.equals(InterceptRefundTypeEnum.LOTTERY.getName()))
							{
								bean.setIssueDesc(AppealConstants.TAXI_LOTR_ISSUE_DESCRIPTION);
							}
							else
							{
								bean.setIssueDesc(AppealConstants.TAXI_ISSUE_DESCRIPTION);
							}
					
						}
							
					}
					
				}
				
			}else if (AppealTypeEnum.LDAD.getName().equalsIgnoreCase(appealData.getAppealType())){
				// late to ALJ

				bean.setIssueDesc(AppealConstants.TIMELINESS);
			}
			//CIF_02396||Defect_3567||Appeals	Issues in DUA Appeals
			if(appealData.getAppealType()!=null && appealData.getAppealType().equals(AppealTypeEnum.LATE.getName()))
			{
				LateAppealData parentAppealData=null; 
				parentAppealData = (LateAppealData) appealData;
				if(parentAppealData!=null)
				{
					bean.setParentAppealType(parentAppealData.getParentAppealData().getAppealType());
				}
				
			}
			bean.setAppealData(appealData);
		}

		return objectAssembly;
	}

	// This method will save appeal data first and external data from appeal data
	@Override
	public  IObjectAssembly saveOrUpdateExternalAppeals(final IObjectAssembly objectAssembly) throws BaseApplicationException{

		ProcessExternalAppealBean externalAppealBean = (ProcessExternalAppealBean) objectAssembly.getFirstBean(ProcessExternalAppealBean.class);
		DecisionData decisionData = (DecisionData) objectAssembly.getFirstComponent(DecisionData.class);

		AppealData previousAppealData = externalAppealBean.getAppealData();
		//Appeal.findAppealandAppealPartySetbyAppealId(externalAppealBean.getAppealId());
		//    	previousAppealData.setActiveFlag(GlobalConstants.DB_ANSWER_NO);
		//    	Appeal previousAppealBo = Appeal.getAppealBoByAppealData(previousAppealData);
		//    	previousAppealBo.saveOrUpdate();
		//
		AppealData appealData = null;
		if (AppealTypeEnum.NON_MON.getName().equals(externalAppealBean.getAppealType())){
			appealData = (AppealData) objectAssembly.getFirstComponent(NonMonAppealData.class);
		}else if (AppealTypeEnum.TAX_INT.getName().equals(externalAppealBean.getAppealType())){
			appealData = (AppealData) objectAssembly.getFirstComponent(TaxInterceptAppealData.class);
		}else if (AppealTypeEnum.TAXA.getName().equals(externalAppealBean.getAppealType())){
			appealData = (AppealData) objectAssembly.getFirstComponent(TaxAppealData.class);
		}else if (AppealTypeEnum.LATE.getName().equals(externalAppealBean.getAppealType())){
			appealData = (AppealData) objectAssembly.getFirstComponent(LateAppealData.class);
		}else if (AppealTypeEnum.MISC.getName().equals(externalAppealBean.getAppealType())){
			appealData = (AppealData) objectAssembly.getFirstComponent(MiscAppealData.class);
		}else if (AppealTypeEnum.LDAD.getName().equals(externalAppealBean.getAppealType())){
			appealData = (AppealData) objectAssembly.getFirstComponent(LateDeniedAppealData.class);
		}else if (AppealTypeEnum.NOAP.getName().equals(externalAppealBean.getAppealType())){
			appealData = (AppealData) objectAssembly.getFirstComponent(NonAppearanceAppealData.class);
		}
		//appealData.setAppealReason(previousAppealData.getAppealReason());
		ExternalAppeals.validateBusinessErrorsAndSaveExternalAppealData(appealData,externalAppealBean,decisionData
				,previousAppealData);
		//
		//        /////////////////////////////////////////////////////////////////////////////////
		//    	int previousAppealLevelTobeCompared = 0;
		//    	int currentAppealLevelTobeCompared = 0;
		//    	if (previousAppealData.getAppealLevel().equalsIgnoreCase(ExternalCourtEnum.CIRCUIT_COURT.getName())
		//    			||previousAppealData.getAppealLevel().equalsIgnoreCase(ExternalCourtEnum.COURT_OF_APPEAL.getName())){
		//    		String appealLevel = ExternalCourtEnum.CIRCUIT_COURT.getDescription();
		//    		Integer appealLevelInt = Integer.valueOf(appealLevel);
		//    		previousAppealLevelTobeCompared = appealLevelInt.intValue();
		//    	}else if (previousAppealData.getAppealLevel().equalsIgnoreCase(ExternalCourtEnum.STATE_SUPREME_COURT.getName())
		//    			||previousAppealData.getAppealLevel().equalsIgnoreCase(ExternalCourtEnum.FEDERAL_SUPREME_COURT.getName())){
		//    		String appealLevel = ExternalCourtEnum.STATE_SUPREME_COURT.getDescription();
		//    		Integer appealLevelInt = Integer.valueOf(appealLevel);
		//    		previousAppealLevelTobeCompared = appealLevelInt.intValue();
		//    	}else if (previousAppealData.getAppealLevel().equalsIgnoreCase(ExternalCourtEnum.US_SUPREME_COURT.getName())){
		//    		String appealLevel = ExternalCourtEnum.US_SUPREME_COURT.getDescription();
		//    		Integer appealLevelInt = Integer.valueOf(appealLevel);
		//    		previousAppealLevelTobeCompared = appealLevelInt.intValue();
		//    	}
		//
		//    	if (appealData.getAppealLevel().equalsIgnoreCase(ExternalCourtEnum.CIRCUIT_COURT.getName())
		//    			||appealData.getAppealLevel().equalsIgnoreCase(ExternalCourtEnum.COURT_OF_APPEAL.getName())){
		//    		String appealLevel = ExternalCourtEnum.CIRCUIT_COURT.getDescription();
		//    		Integer appealLevelInt = Integer.valueOf(appealLevel);
		//    		currentAppealLevelTobeCompared = appealLevelInt.intValue();
		//    	}else if (appealData.getAppealLevel().equalsIgnoreCase(ExternalCourtEnum.STATE_SUPREME_COURT.getName())
		//    			||appealData.getAppealLevel().equalsIgnoreCase(ExternalCourtEnum.FEDERAL_SUPREME_COURT.getName())){
		//    		String appealLevel = ExternalCourtEnum.STATE_SUPREME_COURT.getDescription();
		//    		Integer appealLevelInt = Integer.valueOf(appealLevel);
		//    		currentAppealLevelTobeCompared = appealLevelInt.intValue();
		//    	}else if (appealData.getAppealLevel().equalsIgnoreCase(ExternalCourtEnum.US_SUPREME_COURT.getName())){
		//    		String appealLevel = ExternalCourtEnum.US_SUPREME_COURT.getDescription();
		//    		Integer appealLevelInt = Integer.valueOf(appealLevel);
		//    		currentAppealLevelTobeCompared = appealLevelInt.intValue();
		//    	}

		//if (previousAppealLevelTobeCompared==currentAppealLevelTobeCompared){
		//	throw new BaseApplicationException("error.access.app.externalappeal.samelevelexternalcourt");
		//}else if (currentAppealLevelTobeCompared-previousAppealLevelTobeCompared!=1){
		//	throw new BaseApplicationException("error.access.app.externalappeal.morethanoneleveldifference");
		//}else {
		/*saving new appeal*/
		//appealData.setAppealledAgainstDecision(decisionData);
		//Appeal appealBo = Appeal.getAppealBoByAppealData(appealData);
		//appealBo.saveOrUpdate();
		//appealBo.flush();
		// code for implementing wrok item for hearing
		//	    	Long appealId = appealBo.getAppealData().getAppealId();
		//	    	IBaseWorkflowDAO baseWorkflowDAO = DAOFactory.instance.getBaseWorkflowDAO();
		//		    BaseAccessDsContainerBean bean = new BaseAccessDsContainerBean();
		//		    GlobalDsContainerBean globalBean = bean.getNewGlobalDsContainerBean();
		//		    globalBean.setBusinessKey(appealId.toString());
		//		    // while creating work item if appellant is found to be claimant
		//		    // ist if will execute or else if will execute for emloyer ean
		//		    if (objectAssembly.getSsn()!=null){
		//		    	globalBean.setSsneanfein(objectAssembly.getSsn());
		//		    	globalBean.setTypeAsSsn();
		//		    }else if (objectAssembly.getEan()!=null){
		//		    	globalBean.setSsneanfein(objectAssembly.getEan());
		//		    	globalBean.setTypeAsEan();
		//		    }
		//		    /*If appeal is for DUA decision then create work item for transcription*/
		//		    if (GlobalConstants.DB_ANSWER_YES.equals(externalAppealBean.getDuaQuestion())){
		//		    	baseWorkflowDAO.createAndStartProcessInstance(AppealWorkflowConstants.EXT_APPEAL_HEARING,bean);
		//		    }
		//		    /*Only one this work item to be created,so if iterator is at BOR level work item
		//		     * will be created for transcription
		//		     * else for the other appeal level above circuit court work item will be
		//		     * created for external appeal response*/
		//		    if (DecisionStatusEnum.CIRCUIT_COURT.getName().equals(externalAppealBean.getDecisionLevel())
		//		    		|| DecisionStatusEnum.BOARD_OF_REVIEW.getName().equals(externalAppealBean.getDecisionLevel())){
		//		    	baseWorkflowDAO.createAndStartProcessInstance(AppealWorkflowConstants.EXT_APPEAL_HEARING,bean);
		//		    }else {
		//		    	baseWorkflowDAO.createAndStartProcessInstance(AppealWorkflowConstants.EXT_APPEAL_RESPONSE,bean);
		//		    }
		//}
		/////////////////////////////////////////////////////////////////////////////////////////////////
		return objectAssembly;
	}
	
	/* CIF_01121
	 * commented the method as this is a private method and its not referenced in the class
	 *
	// this is a private method for looping the no of decisions retrieved from database
	private List<ExternalAppealsDecisonDisplayBean> getDisplayList(final List list,final IObjectAssembly objectAssembly) throws BaseApplicationException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getDisplayList");
		}
		List<ExternalAppealsDecisonDisplayBean> displayList = new ArrayList<ExternalAppealsDecisonDisplayBean>();
		if (list!=null && list.size()>0){
			for (int i=0;i<list.size();i++){

				DecisionData decisionData = (DecisionData)list.get(i);
				ClaimantData claimantData = decisionData.getIssueData().getClaimData().getClaimantData();
				ExternalAppealsDecisonDisplayBean displayBean =  new ExternalAppealsDecisonDisplayBean();
				DecisionCodeEnum enums=(DecisionCodeEnum)(DecisionCodeEnum.getEnum(decisionData.getDecisionCode()));
				displayBean.setDecision(enums.getDescription());
				displayBean.setDecisonDate(decisionData.getDecisionGivenDate().toString());
				displayBean.setDocketNo(decisionData.getDecisionForAppealData().getDocketNumber().toString());
				displayBean.setEmployer(decisionData.getIssueData().getFactFindingData().getEmployerName());
				// search by EAN does not require employer data to be added from fact finding data
				// in that case no need to add employer data to displayBean
				// employer data obtained by EAN will be there in the bean
				if (StringUtility.isBlank((String)objectAssembly.getData("duasearchbyean"))
						|| (StringUtility.isBlank((String)objectAssembly.getData("searchbyeanforbor")))){
					displayBean.setEmployerData(decisionData.getIssueData().getFactFindingData().getEmployerData());
					if ("FED".equals(decisionData.getIssueData().getFactFindingData().getEmployerData().getEmployerType())){
						displayBean.setFedEmployerType(GlobalConstants.DB_ANSWER_YES);
					}else if ("REG".equals(decisionData.getIssueData().getFactFindingData().getEmployerData().getEmployerType())){
						displayBean.setRegEmployerType(GlobalConstants.DB_ANSWER_YES);
					}else if (("OSA".equals(decisionData.getIssueData().getFactFindingData().getEmployerData().getEmployerType()))){
						displayBean.setOsaEmployerType(GlobalConstants.DB_ANSWER_YES);
					}else if (("MIL".equals(decisionData.getIssueData().getFactFindingData().getEmployerData().getEmployerType()))){
						displayBean.setMilEmployerType(GlobalConstants.DB_ANSWER_YES);
					}
				}
				MstIssueMaster issueMasterData=decisionData.getIssueData().getDetectedMstIssueMaster();
				String issueDescription =(String)CacheUtility.getCachePropertyValue("ISSUE_CATEGORY_DISPLAY","key",issueMasterData.getIssueCategory(),"description");
				String issueDetails=(String)CacheUtility.getCachePropertyValue("ISSUE_SUB_CATEGORY_DISPLAY","key",issueMasterData.getIssueSubCategory(),"description");
				displayBean.setIssueDesc(issueDescription);
				displayBean.setIssueDetail(issueDetails);
				displayBean.setRowNum(i+"");
				displayBean.setSsn(claimantData.getSsn());
				displayBean.setClaimantData(claimantData);
				//objectAssembly.addComponent(claimantData,true);
				//objectAssembly.addComponent(decisionData,true);
				objectAssembly.addBean(displayBean,true);
				displayList.add(displayBean);

			}
		}else{
			throw new BaseApplicationException("error.access.app.extappeals.nodecision");
		}
		return  displayList;
	}
	*/
	
	/* CIF_01121
	 * commented the method as this is a private method and its not referenced in the class
	 *
	// this private method will add the employer data to object assembly
	private void addEmployerData(final ProcessExternalAppealBean bean,final IObjectAssembly objectAssembly,final Employer employrBO) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method addEmployerData");
		}

		Employer employeNameBO = null ;

		if (employrBO.getEmployerData().getEmployerType().equals("FED")){

			employeNameBO = Employer.findFedEmployerByPrimaryKey(employrBO.getEmployerData().getEmployerId());
			FederalEmployerData employerData = (FederalEmployerData) employeNameBO.getEmployerData();
			//objectAssembly.addData("FED",employrBO.getEmployerData().getEmployerType());
			bean.setFedEmployerType(GlobalConstants.DB_ANSWER_YES);
			bean.setEmployerData(employerData);
			objectAssembly.addComponent(employerData,true);

		}else if (employrBO.getEmployerData().getEmployerType().equals("MIL")){

			employeNameBO = Employer.getEmployerByEan(bean.getEan());
			bean.setMilEmployerType(GlobalConstants.DB_ANSWER_YES);
			//bean.setEmployerData(employerData);

		}else if (employrBO.getEmployerData().getEmployerType().equals("REG")){

			employeNameBO = Employer.getEmployerByEan(bean.getEan());
			RegisteredEmployerData employerData =  (RegisteredEmployerData) employeNameBO.getEmployerData();
			//objectAssembly.addData("REG",employrBO.getEmployerData().getEmployerType());
			bean.setRegEmployerType(GlobalConstants.DB_ANSWER_YES);
			bean.setEmployerData(employerData);
			objectAssembly.addComponent(employerData,true);

		}else if (employrBO.getEmployerData().getEmployerType().equals("OSA")){

			employeNameBO = Employer.findOtherStateEmployerByPrimaryKey(employrBO.getEmployerData().getEmployerId());
			OtherStateEmployerData employerData = (OtherStateEmployerData) employeNameBO.getEmployerData();
			//objectAssembly.addData("OSA",employrBO.getEmployerData().getEmployerType());
			bean.setOsaEmployertype(GlobalConstants.DB_ANSWER_YES);
			bean.setEmployerData(employerData);
			objectAssembly.addComponent(employerData,true);

		}
	}
	*/
	
	// This method will fetch all the external appeals on the basis of docket no and appeal level
	@Override
	public IObjectAssembly getExternalAppealsByDocketNoOrSSNorEan(final IObjectAssembly objectAssembly) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getExternalAppealsByDocketNoOrSSNorEan");
		}

		ProcessExternalAppealDecisonBean bean = (ProcessExternalAppealDecisonBean)objectAssembly.getFirstBean(ProcessExternalAppealDecisonBean.class);
		AppealData appealData = null;
		if (AppealTypeEnum.NON_MON.getName().equals(bean.getAppealType())){
			NonMonAppeal nonMonAppealBO = (NonMonAppeal) NonMonAppeal.fetchAppealDataAppealedDecisionsByAppealId(bean.getAppealId());
			appealData = nonMonAppealBO.getAppealData();
		}else if (AppealTypeEnum.TAX_INT.getName().equals(bean.getAppealType())){
			TaxInterceptAppeal taxInterceptAppealBO = (TaxInterceptAppeal) TaxInterceptAppeal.fetchAppealDataAppealedDecisionsByAppealId(bean.getAppealId());
			appealData = taxInterceptAppealBO.getAppealData();
		}else if (AppealTypeEnum.TAXA.getName().equals(bean.getAppealType())){
			TaxAppeal taxAppealBO = (TaxAppeal) TaxAppeal.fetchAppealDataAppealedDecisionsByAppealId(bean.getAppealId());
			appealData = taxAppealBO.getAppealData();
		}else if (AppealTypeEnum.LATE.getName().equals(bean.getAppealType())){
			LateAppeal lateAppealBO = (LateAppeal)LateAppeal.fetchAppealDataAppealedDecisionsByAppealId(bean.getAppealId());
			appealData = lateAppealBO.getAppealData();
		}else if (AppealTypeEnum.MISC.getName().equals(bean.getAppealType())){
			MiscAppeal miscAppealBO = (MiscAppeal)MiscAppeal.fetchAppealDataAppealedDecisionsByAppealId(bean.getAppealId());
			appealData = miscAppealBO.getAppealData();
		}else if (AppealTypeEnum.NOAP.getName().equals(bean.getAppealType())){
			NonApperanceAppeal noapAppealBO = (NonApperanceAppeal)NonApperanceAppeal.fetchAppealDataAppealedDecisionsByAppealId(bean.getAppealId());
			appealData = noapAppealBO.getAppealData();
		}else if (AppealTypeEnum.LDAD.getName().equals(bean.getAppealType())){
			LateDeniedAppeal ldadAppealBO = (LateDeniedAppeal)LateDeniedAppeal.fetchAppealDataAppealedDecisionsByAppealId(bean.getAppealId());
			appealData = ldadAppealBO.getAppealData();
		}


		//preload the decision for future use
		DecisionData appealAgainstDecision = appealData.getAppealledAgainstDecision();


		if (StringUtility.isNotBlank(bean.getDocket())|| StringUtility.isNotBlank(bean.getSsn()) ){
			if (!AppealTypeEnum.TAX_INT.getName().equals(bean.getAppealType()) && !AppealTypeEnum.TAXA.getName().equals(bean.getAppealType())){
				Long claimantId = bean.getClaimantId();
				// if there is no claimant id iterator will come as 0
				if (!GlobalConstants.DB_ANSWER_NO.equals(claimantId.toString())){
					Claimant claimantBO = Claimant.findByPrimaryKey(claimantId);
					bean.setFullName(StringUtility.getFullName(claimantBO.getClaimantData().getFirstName(),claimantBO.getClaimantData().getLastName(),true));
					bean.setSsn(claimantBO.getClaimantData().getSsn());
				}else {
					// this is to handle misc appeal where there will be no claimant id
					List<?> appellantList = AppealUtil.getAppllantInformation(appealData);
					AppealPartyData appealPartyData = (AppealPartyData)appellantList.get(0);
					bean.setFullName(appealPartyData.getPartyName());
					bean.setSsn(appealPartyData.getSsn());
				}

			}else {
				//CIF_P2_00871||Appeals||Defect_4114||External Appeal Decision

				if(!AppealTypeEnum.TAXA.getName().equals(bean.getAppealType()))
				{
					List<?> appellantList = AppealUtil.getAppllantInformation(appealData);
					AppealPartyData appealPartyData = (AppealPartyData)appellantList.get(0);
					bean.setFullName(appealPartyData.getPartyName());
					bean.setSsn(appealPartyData.getSsn());
				}
				else
				{
					List<?> appellantList = AppealUtil.getAppllantInformation(appealData); 
					AppealPartyData appealPartyData = (AppealPartyData)appellantList.get(0);
					Long employerId = appealPartyData.getEmployerData().getEmployerId();
					Employer employerBO = Employer.findByPrimaryKey(employerId);
					bean.setEmployerName(employerBO.getEmployerData().getEmployerName());
					bean.setEan(employerBO.getEmployerData().getEan());
				}
				
				
			}

		}else{
			Long employerId = bean.getEmployerId();
			Employer employerBO = Employer.findByPrimaryKey(employerId);
			bean.setEmployerName(employerBO.getEmployerData().getEmployerName());
			//bean.setEan(employerBO.getEmployerData().getEan());
		}
		/*if (StringUtility.isNotBlank(bean.getDocket())){
	    	// this list contains all the external appeals for the entered docket no and court level
	    	List list =  ExternalAppeals.externalAppealsDecisionList(Long.valueOf(bean.getDocket()),bean.getAppealLevel());
	    	appealList = this.getExternalAppealsDisplayListByDocketNo(list,bean);

    	}else if (StringUtility.isNotBlank(bean.getSsn())){

    		Claimant claimant = Claimant.findBySsn(bean.getSsn());
			Long claimantId = claimant.getClaimantData().getPkId();
			List list = ExternalAppeals.externalAppealsDecisionListBySSN(claimantId,bean.getAppealLevel());
			bean.setFullName(claimant.getClaimantData().getClaimantFullName());
			bean.setSsn(claimant.getClaimantData().getSsn());
			bean.setClaimantId(claimantId);
			appealList = this.getExternalAppealsDisplayListBySSN(list);// this method will retrun the list of appeals

    	}else if (StringUtility.isNotBlank(bean.getEan())){
    		Employer employerBO = Employer.findUnitWithContactsByEan(bean.getEan());
    		Employer employeNameBO = null ;
    		List list = ExternalAppeals.externalAppealsDecisionListByEAN(employerBO.getEmployerData().getEmployerId(),bean.getAppealLevel());
    		bean.setEan(employerBO.getEmployerData().getEan());
    		appealList = this.getExternalAppealsDisplayListByEAN(list);// this method will retrun the list of appeals

    		if (EmployerTypeEnum.FED.getName().equals(employerBO.getEmployerData().getEmployerType())){

				employeNameBO = Employer.findFedEmployerByPrimaryKey(employerBO.getEmployerData().getEmployerId());
				FederalEmployerData employerData = (FederalEmployerData) employeNameBO.getEmployerData();
				bean.setEmployerName(employerData.getEmployerName());
				//bean.setFedTypeEmployer(GlobalConstants.DB_ANSWER_YES);

			}else if (EmployerTypeEnum.MIL.getName().equals(employerBO.getEmployerData().getEmployerType())){

				employeNameBO = Employer.getEmployerByEan(bean.getEan());// for 'MIL' query to be done
				//bean.setMilTypeEmployer(GlobalConstants.DB_ANSWER_YES);
				//TODO

			}else if (EmployerTypeEnum.REG.getName().equals(employerBO.getEmployerData().getEmployerType())){

				employeNameBO = Employer.getEmployerByEan(bean.getEan());
				RegisteredEmployerData employerData =  (RegisteredEmployerData) employeNameBO.getEmployerData();
				bean.setEmployerName(employerData.getDisplayName());
				//bean.setRegTypeEmployer(GlobalConstants.DB_ANSWER_YES);

			}else if (EmployerTypeEnum.OSA.getName().equals(employerBO.getEmployerData().getEmployerType())){

				employeNameBO = Employer.findOtherStateEmployerByPrimaryKey(employerBO.getEmployerData().getEmployerId());
				OtherStateEmployerData employerData = (OtherStateEmployerData) employeNameBO.getEmployerData();
				bean.setEmployerName(employerData.getDisplayName());
				//bean.setOsaTypeEmployer(GlobalConstants.DB_ANSWER_YES);
			}
    	}
    	objectAssembly.addData("APPEALS",appealList);*/
		//bean.setAppealLevel(appealData.getAppealLevel());
		objectAssembly.addComponent(appealData,true);
		objectAssembly.addComponent(appealAgainstDecision);
		return objectAssembly;
	}
	
	/* CIF_01121
	 * commented the method as this is a private method and its not referenced in the class
	 *	
	// this is the list of appeals when search by docket no
	private List<ExternalAppealsDisplayBean> getExternalAppealsDisplayListByDocketNo(final List list,final ProcessExternalAppealDecisonBean processBean) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getExternalAppealsDisplayListByDocketNo");
		}

		List<ExternalAppealsDisplayBean> displayList = new ArrayList<ExternalAppealsDisplayBean>();
		Set appealPartySet = new HashSet();
		if (list!=null && list.size()>0){

			for (int i=0;i<list.size();i++){
				AppealData appealData = (AppealData) list.get(i);
				ExternalAppealsDisplayBean bean = new ExternalAppealsDisplayBean();
				// if the search of appeals take place by docket no
				if (StringUtility.isNotBlank(processBean.getDocket())){
					appealPartySet = appealData.getAppealPartySet();
					Iterator iterator = appealPartySet.iterator();
					while(iterator.hasNext()){
						AppealPartyData appealPartyData = (AppealPartyData) iterator.next();
						if (GlobalConstants.APPELLANT.equals(appealPartyData.getPartyType())){
							if (appealPartyData.getClaimantData()!=null){
								Claimant claimant = Claimant.findByPrimaryKey(appealPartyData.getClaimantData().getPkId());
								processBean.setFullName(claimant.getClaimantData().getClaimantFullName());
								processBean.setSsn(claimant.getClaimantData().getSsn());
							}
							else if (appealPartyData.getEmployerData()!=null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}


							}
						}
					}
				}
				bean.setAppealDate(appealData.getAppealDate().toString());
				bean.setCauseNumber(appealData.getExternalAppealsData().getCourtCauseNumber());
				String externalAgency=(String)CacheUtility.getCachePropertyValue("T_APPEAL_EXT_COURTS","key",appealData.getAppealLevel(),"description");
				bean.setExternalAgency(externalAgency);
				bean.setAppealReceivedDate(appealData.getExternalAppealsData().getReceivedDate().toString());
				String appellant =(String)CacheUtility.getCachePropertyValue("T_APPEALLANT","key",appealData.getAppellant(),"description");
				bean.setAppellant(appellant);
				if (appealData.getExternalAppealsData().getRespondedDate()!=null){
					bean.setResponseReceivedDateByMdes(appealData.getExternalAppealsData().getRespondedDate().toString());
				}else{
					throw new BaseApplicationException("error.access.app.extappeals.noresponse");
				}
				bean.setRowNum(i+"");
				bean.setAppealData(appealData);
				displayList.add(bean);
			}
		}else {
			throw new BaseApplicationException("error.access.app.extappeals.noappeals");
		}
		return displayList;
	}
	*/
	
	
	/* CIF_01121
	 * commented the method as this is a private method and its not referenced in the class
	 *
	
	// this is the list of appeals when searched by SSN
	private List<ExternalAppealsDisplayBean> getExternalAppealsDisplayListBySSN(final List list) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getExternalAppealsDisplayListBySSN");
		}

		List<ExternalAppealsDisplayBean> displayList = new ArrayList<ExternalAppealsDisplayBean>();
		if (list!=null && list.size()>0){
			for (int i=0;i<list.size();i++){

				AppealPartyData appealPartyData = (AppealPartyData)list.get(i);
				AppealData appealData = appealPartyData.getAppealData();
				ExternalAppealsDisplayBean bean = new ExternalAppealsDisplayBean();
				bean.setAppealDate(appealData.getAppealDate().toString());
				bean.setCauseNumber(appealData.getExternalAppealsData().getCourtCauseNumber());
				String externalAgency=(String)CacheUtility.getCachePropertyValue("T_APPEAL_EXT_COURTS","key",appealData.getAppealLevel(),"description");
				bean.setExternalAgency(externalAgency);
				bean.setAppealReceivedDate(appealData.getExternalAppealsData().getReceivedDate().toString());
				String appellant =(String)CacheUtility.getCachePropertyValue("T_APPEALLANT","key",appealData.getAppellant(),"description");
				bean.setAppellant(appellant);
				if (appealData.getExternalAppealsData().getRespondedDate()!=null){
					bean.setResponseReceivedDateByMdes(appealData.getExternalAppealsData().getRespondedDate().toString());
				}else{
					throw new BaseApplicationException("error.access.app.extappeals.noresponse");
				}
				bean.setRowNum(i+"");
				displayList.add(bean);
			}
		}else {
			throw new BaseApplicationException("error.access.app.extappeals.noappeals");
		}
		return displayList;
	}
	*/
	
	/* CIF_01121
	 * commented the method as this is a private method and its not referenced in the class
	 *
	
	// this is the list of appeals when searched by EAN
	private List<ExternalAppealsDisplayBean> getExternalAppealsDisplayListByEAN(final List list) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getExternalAppealsDisplayListByEAN");
		}

		List<ExternalAppealsDisplayBean> displayList = new ArrayList<ExternalAppealsDisplayBean>();
		if (list!=null && list.size()>0){
			for (int i=0;i<list.size();i++){

				AppealPartyData appealPartyData = (AppealPartyData)list.get(i);
				AppealData appealData = appealPartyData.getAppealData();
				ExternalAppealsDisplayBean bean = new ExternalAppealsDisplayBean();
				bean.setAppealDate(appealData.getAppealDate().toString());
				bean.setCauseNumber(appealData.getExternalAppealsData().getCourtCauseNumber());
				String externalAgency=(String)CacheUtility.getCachePropertyValue("T_APPEAL_EXT_COURTS","key",appealData.getAppealLevel(),"description");
				bean.setExternalAgency(externalAgency);
				bean.setAppealReceivedDate(appealData.getExternalAppealsData().getReceivedDate().toString());
				String appellant =(String)CacheUtility.getCachePropertyValue("T_APPEALLANT","key",appealData.getAppellant(),"description");
				bean.setAppellant(appellant);
				if (appealData.getExternalAppealsData().getRespondedDate()!=null){
					bean.setResponseReceivedDateByMdes(appealData.getExternalAppealsData().getRespondedDate().toString());
				}else{
					throw new BaseApplicationException("error.access.app.extappeals.noresponse");
				}
				bean.setRowNum(i+"");
				displayList.add(bean);
			}
		}else {
			throw new BaseApplicationException("error.access.app.extappeals.noappeals");
		}
		return displayList;
	}
	
	*/
	
	// This method will get the appeal data as input
	// For the appeal Data received new decision will be saved
	@Override
	public IObjectAssembly saveOrUpdateDecisionsForExternalAppeals(final IObjectAssembly objectAssembly) throws CloneNotSupportedException, BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveOrUpdateDecisionsForExternalAppeals");
		}
		ProcessExternalAppealDecisonBean bean = (ProcessExternalAppealDecisonBean)objectAssembly.getFirstBean(ProcessExternalAppealDecisonBean.class);
		String strHistoryCmt = null;
		AppealData appealData = null;

		//    	if (AppealTypeEnum.NON_MON.getName().equals(bean.getAppealType())){
		//		    appealData = (NonMonAppealData) objectAssembly.getFirstComponent(NonMonAppealData.class);
		//		    appealData.setAppealStatus(AppealStatusEnum.CLOSED.getName());
		//		    NonMonAppeal appealBO = new NonMonAppeal(appealData);
		//		    appealBO.saveOrUpdate();
		//
		//		}else if(AppealTypeEnum.MISC.getName().equals(bean.getAppealType())){
		//			appealData = (MiscAppealData) objectAssembly.getFirstComponent(MiscAppealData.class);
		//			appealData.setAppealStatus(AppealStatusEnum.CLOSED.getName());
		//			MiscAppeal miscAppealBO = new MiscAppeal(appealData);
		//			miscAppealBO.saveOrUpdate();
		//			//TODO
		//		}else if (AppealTypeEnum.TAX_INT.getName().equals(bean.getAppealType())){
		//			appealData = (TaxInterceptAppealData) objectAssembly.getFirstComponent(TaxInterceptAppealData.class);
		//			appealData.setAppealStatus(AppealStatusEnum.CLOSED.getName());
		//		    TaxInterceptAppeal appealBO = new TaxInterceptAppeal((TaxInterceptAppealData)appealData);
		//		    appealBO.saveOrUpdate();
		//            //TODO
		//		}else if (AppealTypeEnum.TAXA.getName().equals(bean.getAppealType())){
		//			appealData = (TaxAppealData) objectAssembly.getFirstComponent(TaxAppealData.class);
		//			appealData.setAppealStatus(AppealStatusEnum.CLOSED.getName());
		//		    TaxAppeal appealBO = new TaxAppeal((TaxAppealData)appealData);
		//		    appealBO.saveOrUpdate();
		//			//TODO
		//		}else if (AppealTypeEnum.LATE.getName().equals(bean.getAppealType())){
		//			appealData = (LateAppealData) objectAssembly.getFirstComponent(LateAppealData.class);
		//			appealData.setAppealStatus(AppealStatusEnum.CLOSED.getName());
		//		    LateAppeal appealBO = new LateAppeal((LateAppealData)appealData);
		//		    appealBO.saveOrUpdate();
		//			//TODO
		//		}

		//    	This code has been added because in case of remand we need to make the circuit code decision to inactive
		//so we are fetching again the same data and making iterator inactive.

		appealData = (AppealData) objectAssembly.getFirstComponent(AppealData.class, true);
		if(!StringUtility.isBlank(bean.getOrderFrom())){
			//11300 It may have been filed to the Supreme Court, but iterator can return from the Court of Appeals
			appealData.setAppealLevel(bean.getOrderFrom());
		}

		if(AppealDecisionCodeEnum.REMX.getName().equalsIgnoreCase(bean.getOrderDescription()))
		{
			/*DecisionData  decisionDataAtExternal = newDecisionTobeSaved.getDecisionData();
   	    	 decisionDataAtExternal.setActiveFlag(GlobalConstants.DB_ANSWER_NO);
   	    	 newDecisionTobeSaved= new Decision(decisionDataAtExternal);
   	    	 newDecisionTobeSaved.saveOrUpdate();*/
			processExternalRemandDecision(objectAssembly);
			//appealData = (AppealData) objectAssembly.getFirstComponent(AppealData.class, true);

		} else {

			//appealData = (AppealData) objectAssembly.getFirstComponent(AppealData.class, true);

			Appeal objAppealBO = Appeal.getAppealBoByAppealData(appealData);
			objAppealBO.getAppealData().setAppealStatus(AppealStatusEnum.CLOSED.getName());
			//objAppealBO.evictAppeal(appealData.getExternalAppealsData());
			objAppealBO.saveOrUpdate();

			DecisionData decisionData =  null;
			// this will fetch only decision for non mon appeal
			Decision decisionBO = Decision.getDecisionAndMstIssueMasterDataByDecisionId(appealData.getAppealledAgainstDecision().getDecisionID());
			decisionData = decisionBO.getDecisionData();
			if (decisionData==null){
				// if iterator is not a NMON appeal
				Decision decisionBOforAnytypeOfAppeal = Decision.getDecisionDataForAnyAppealsByDecisionId(appealData.getAppealledAgainstDecision().getDecisionID());
				decisionData = decisionBOforAnytypeOfAppeal.getDecisionData();
			}
			decisionData.setActiveFlag(GlobalConstants.DB_ANSWER_NO);
			Decision oldDecisionTobeUpdated = new Decision(decisionData);
			oldDecisionTobeUpdated.saveOrUpdate();
			//	    oldDecisionTobeUpdated.flush();
			/*new decision to be saved*/
			DecisionData decisiondataCloned = (DecisionData) decisionData.Clone();
			Decision newDecisionTobeSaved= new Decision(decisiondataCloned);
			decisiondataCloned.setDecisionGivenDate(bean.getOrderDate());
			//CIF_00383 Storing mandate in DB
			decisiondataCloned.setMandate(bean.getMandate());
			if (AppealDecisionCodeEnum.REVERSE.getName().equalsIgnoreCase(bean.getOrderDescription())){
				if (DecisionCodeEnum.ALLOWED.getName().equalsIgnoreCase(decisiondataCloned.getDecisionCode())){
					decisiondataCloned.setDecisionCode(DecisionCodeEnum.DENY.getName());
				}else if (DecisionCodeEnum.DENY.getName().equalsIgnoreCase(decisiondataCloned.getDecisionCode())){
					decisiondataCloned.setDecisionCode(DecisionCodeEnum.ALLOWED.getName());
				}
			}
			decisiondataCloned.setDecisionForAppealData(appealData);
			decisiondataCloned.setActiveFlag(GlobalConstants.DB_ANSWER_YES);
			decisiondataCloned.setDecisionStatus(appealData.getAppealLevel());
			decisiondataCloned.setDecisionID(null);
			//R4UAT00025764
			decisiondataCloned.setDecisionMailDate(null);
			AppealDecisionDetailData appealDecisionDetailData =  new AppealDecisionDetailData();
			appealDecisionDetailData.setAppealDecisionCode(bean.getOrderDescription());
			appealDecisionDetailData.setDecisonData(decisiondataCloned);
			//R4UAT00005251 - Order Details text not getting saved
			appealDecisionDetailData.setAditionalDetails(bean.getOrderDetails());
			if (AppealTypeEnum.TAX_INT.getName().equals(bean.getAppealType())){
				appealDecisionDetailData.setInterceptAmount(decisiondataCloned.getAppealDecisionDetailData().getInterceptAmount());
				appealDecisionDetailData.setRefundAmount(decisiondataCloned.getAppealDecisionDetailData().getRefundAmount());
			}
			decisiondataCloned.setAppealDecisionDetailData(appealDecisionDetailData);
			if (AppealTypeEnum.NON_MON.getName().equals(bean.getAppealType())){
				newDecisionTobeSaved.adjudicateDecision();
			}else{
				//Decision decision = new Decision(decisiondataCloned);
				newDecisionTobeSaved.saveOrUpdate();
			}
			//Added new logic for populating T_Appeal_Decision_Letter cq R4UAT00025764
			if(AppealDecisionCodeEnum.AFFIRM.getName().equalsIgnoreCase(bean.getOrderDescription()) || AppealDecisionCodeEnum.REVERSE.getName().equalsIgnoreCase(bean.getOrderDescription()) )
			{
				AppealDecisionLetterData decisionLeter= new AppealDecisionLetterData();
				decisionLeter.setDecisionData(newDecisionTobeSaved.getDecisionData());
				if(AppealDecisionCodeEnum.AFFIRM.getName().equalsIgnoreCase(bean.getOrderDescription()))
				{
					decisionLeter.setCaseHistory(AppealConstants.APP_EXT_DEC_AFRM.toString());
				} else if(AppealDecisionCodeEnum.REVERSE.getName().equalsIgnoreCase(bean.getOrderDescription()))
				{
					decisionLeter.setCaseHistory(AppealConstants.APP_EXT_DEC_RVSE.toString());
				}
				decisionLeter.setDecisionDetail(bean.getOrderDetails());
				decisionLeter.setProofreadingStatus(AppealProofReadingStatusEnum.NOT_APPLICABLE.getName());
				AppealDecisionLetterBO decisionLetterBO= new AppealDecisionLetterBO(decisionLeter);
				decisionLetterBO.saveOrUpdate();
			}
			//end
		}


		//
		//////////////////////////////////////////////////////////////
		//This is for adding business comments. //modified in CQ 6928
		if(AppealDecisionCodeEnum.AFFIRM.getName().equalsIgnoreCase(bean.getOrderDescription()))
		{
			strHistoryCmt = AppealConstants.APP_EXT_DEC_AFRM;
		} else if(AppealDecisionCodeEnum.REVERSE.getName().equalsIgnoreCase(bean.getOrderDescription()))
		{
			strHistoryCmt = AppealConstants.APP_EXT_DEC_RVSE;
		} else if(AppealDecisionCodeEnum.DISMISS.getName().equalsIgnoreCase(bean.getOrderDescription()))
		{
			strHistoryCmt = AppealConstants.APP_EXT_DEC_DSMS;
		} else if(AppealDecisionCodeEnum.REMX.getName().equalsIgnoreCase(bean.getOrderDescription()))
		{
			strHistoryCmt = AppealConstants.APP_EXT_DEC_REMD;
		}

		Appeal appealBO = Appeal.getAppealBoByAppealData(appealData);


		Object[] messageArgument = new Object[2];
		//messageArgument[0] -- This value is setting in the createBusinessComments method itself.
		messageArgument[0] = appealData.getDocketNumber();
		messageArgument[1] = CacheUtility.getCachePropertyValue("T_APPEAL_EXT_COURTS",KEY,appealData.getAppealLevel(),DESCRIPTION);

		appealBO.createBusinessComments(appealData,strHistoryCmt,messageArgument);


		return objectAssembly;
	}

	/**
	 * This method update the appeal and decision data when remanded 
	 * from court
	 * @param objAssembly IObjectAssembly
	 * @throws CloneNotSupportedException throws CloneNotSupportedException
	 * @throws BaseApplicationException throws BaseApplicationException
	 */
	private void processExternalRemandDecision(final IObjectAssembly objAssembly)  throws CloneNotSupportedException, BaseApplicationException
	{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method processExternalRemandDecision");
		}
		ProcessExternalAppealDecisonBean bean = (ProcessExternalAppealDecisonBean)objAssembly.getFirstBean(ProcessExternalAppealDecisonBean.class);

		//update the External Appeal to be inactive
		AppealData appealData = (AppealData) objAssembly.getFirstComponent(AppealData.class, true);
		appealData.setActiveFlag(GlobalConstants.DB_ANSWER_NO);
		//closed for now, may need to be REMX or something if external courts expect a response
		appealData.setAppealStatus(AppealStatusEnum.CLOSED.getName());
		Appeal objAppealBO = Appeal.getAppealBoByAppealData(appealData);
		objAppealBO.saveOrUpdate();

		//update the BOR's decision to active (should still be active from before)
		//set up the decision
		//Decision decisionBOforAnytypeOfAppeal = Decision.getDecisionDataForAnyAppealsByDecisionId(appealData.getAppealledAgainstDecision().getDecisionID());
		DecisionData decisionData = appealData.getAppealledAgainstDecision();

		//DECISION SAVED IN WRONG AREA - SEE CQ 13854
		//CQ 12911 - previous decision not getting set as inactive
		//	    decisionData.setActiveFlag(GlobalConstants.DB_ANSWER_NO);
		//	    Decision oldDecisionToBeSaved = new Decision(decisionData);
		//	    oldDecisionToBeSaved.saveOrUpdate();

		DecisionData decisiondataCloned = (DecisionData) decisionData.Clone();
		Decision newDecisionTobeSaved= new Decision(decisiondataCloned);
		decisiondataCloned.setDecisionGivenDate(bean.getOrderDate());
		decisiondataCloned.setDecisionForAppealData(appealData);
		//the remand decision does not become active
		decisiondataCloned.setActiveFlag(GlobalConstants.DB_ANSWER_NO);
		decisiondataCloned.setDecisionStatus(appealData.getAppealLevel());
		decisiondataCloned.setDecisionID(null);
		AppealDecisionDetailData appealDecisionDetailData =  new AppealDecisionDetailData();
		appealDecisionDetailData.setAppealDecisionCode(bean.getOrderDescription());
		appealDecisionDetailData.setDecisonData(decisiondataCloned);
		appealDecisionDetailData.setAditionalDetails(bean.getOrderDetails());
		if (AppealTypeEnum.TAX_INT.getName().equals(bean.getAppealType())){
			appealDecisionDetailData.setInterceptAmount(decisiondataCloned.getAppealDecisionDetailData().getInterceptAmount());
			appealDecisionDetailData.setRefundAmount(decisiondataCloned.getAppealDecisionDetailData().getRefundAmount());
		}
		decisiondataCloned.setAppealDecisionDetailData(appealDecisionDetailData);
		//if (AppealTypeEnum.NON_MON.getName().equals(bean.getAppealType())){
		//	 newDecisionTobeSaved.adjudicateDecision();
		//}else{
		newDecisionTobeSaved.saveOrUpdate();
		//}


		//look up most recent BOR level appeal of the same type for this docket
		AppealData previousAppeal = decisionData.getDecisionForAppealData();
		AppealData newBorAppeal = null;
		//clone the above appeal to create a new AppealData object at the BOR based on its type (Nmon, Late, Etc)
		//possibly set its status to something to signify iterator was a REMX
		if (previousAppeal instanceof NonMonAppealData) {
			//NonMonAppealData prevNonMon = (NonMonAppealData) previousAppeal;
			NonMonAppealData newNonMonAppeal = new NonMonAppealData();
			newNonMonAppeal.setLeadAppeal(GlobalConstants.DB_ANSWER_NO);
			newNonMonAppeal.setMultiClaimant(GlobalConstants.DB_ANSWER_NO);

			newBorAppeal = newNonMonAppeal;
		} else if (previousAppeal instanceof LateAppealData) {
			LateAppealData prevLate = (LateAppealData) previousAppeal;
			LateAppealData newLateAppeal = new LateAppealData();
			newLateAppeal.setParentAppealData(prevLate.getParentAppealData());
			newBorAppeal = newLateAppeal;
		} else if (previousAppeal instanceof NonAppearanceAppealData) {
			NonAppearanceAppealData prevNoApp = (NonAppearanceAppealData) previousAppeal;
			NonAppearanceAppealData newNoAppAppeal = new NonAppearanceAppealData();
			newNoAppAppeal.setParentAppealData(prevNoApp.getParentAppealData());
			newBorAppeal = newNoAppAppeal;
		}  else if (previousAppeal instanceof TaxAppealData) {
			TaxAppealData prevTax = (TaxAppealData) previousAppeal;
			TaxAppealData newTaxAppeal = new TaxAppealData();
			newTaxAppeal.setTaxAppealIssueCode(prevTax.getTaxAppealIssueCode());
			newTaxAppeal.setAppealingDecisionDate(prevTax.getAppealingDecisionDate());
			newBorAppeal = newTaxAppeal;
		}   else if (previousAppeal instanceof TaxInterceptAppealData) {
			TaxInterceptAppealData prevTaxi = (TaxInterceptAppealData) previousAppeal;
			TaxInterceptAppealData newTaxiAppeal = new TaxInterceptAppealData();
			newTaxiAppeal.setTaxInterceptMasterData(prevTaxi.getTaxInterceptMasterData());
			newBorAppeal = newTaxiAppeal;
		} else if (previousAppeal instanceof MiscAppealData) {
			MiscAppealData prevMisc = (MiscAppealData) previousAppeal;
			MiscAppealData newMiscAppeal = new MiscAppealData();
			newMiscAppeal.setMiscAppealType(prevMisc.getMiscAppealType());
			newMiscAppeal.setCorrespondenceId(prevMisc.getCorrespondenceId());
			newMiscAppeal.setOvpIssue(prevMisc.getOvpIssue());
			newMiscAppeal.setTraTaaIssue(prevMisc.getTraTaaIssue());
			newMiscAppeal.setProIssue(prevMisc.getProIssue());
			newMiscAppeal.setAppealingDecisionDate(new Date());
			newBorAppeal = newMiscAppeal;
		} else if (previousAppeal instanceof LateDeniedAppealData) {
			LateDeniedAppealData prevLateDeny = (LateDeniedAppealData) previousAppeal;
			LateDeniedAppealData newLateDenyAppeal = new LateDeniedAppealData();
			newLateDenyAppeal.setParentAppealData(prevLateDeny.getParentAppealData());
			newBorAppeal = newLateDenyAppeal;
		}
		newBorAppeal.setDocketNumber(previousAppeal.getDocketNumber());
		newBorAppeal.setAppealDate(bean.getOrderDate());
		newBorAppeal.setDocketNumber(previousAppeal.getDocketNumber());
		newBorAppeal.setAppealReason(bean.getOrderDetails());
		newBorAppeal.setInterpreter(previousAppeal.getInterpreter());
		newBorAppeal.setInterpreterDetails(previousAppeal.getInterpreterDetails());
		newBorAppeal.setOtherInterpreter(previousAppeal.getOtherInterpreter());
		//will probably need another status for when iterator has been remanded from external
		//newBorAppeal.setAppealStatus(AppealStatusEnum.APPEALED.getName());
		//CIF_02076,Defect_2938:Changed appeal status to Remand Upper
        newBorAppeal.setAppealStatus(AppealStatusEnum.REMAND_UPPER.getName());
		newBorAppeal.setAppellant(previousAppeal.getAppellant());
		newBorAppeal.setOpponent(previousAppeal.getOpponent());
		newBorAppeal.setAppealLevel(MiscAppealLevelEnum.BOFR.getName());
		newBorAppeal.setAppealledAgainstDecision(previousAppeal.getAppealledAgainstDecision());
		newBorAppeal.setAppealType(previousAppeal.getAppealType());
		newBorAppeal.setHearingScheduled(GlobalConstants.DB_ANSWER_NO);
		newBorAppeal.setActiveFlag(GlobalConstants.DB_ANSWER_YES);
		//set flag so the scheduler knows this is urgent.
		newBorAppeal.setPriorityFlag(GlobalConstants.DB_ANSWER_YES);

		Appeal newAppealToSave = Appeal.getAppealBoByAppealData(newBorAppeal);
		newAppealToSave.saveOrUpdate();


		//Create the AppealParties
		Set<AppealPartyData> existingPartySet = previousAppeal.getAppealPartySet();
		Iterator<AppealPartyData> partyIterator = existingPartySet.iterator();
		Set<AppealPartyData> newPartySet = new HashSet<AppealPartyData>();
		AppealPartyData newAppealPartyData = null;
		while (partyIterator.hasNext()) {
			newAppealPartyData = new AppealPartyData();
			AppealPartyData existingAppealPartyData = (AppealPartyData) partyIterator
					.next();

			newAppealPartyData.setAppealData(newBorAppeal);
			newAppealPartyData.setClaimantData(existingAppealPartyData
					.getClaimantData());
			newAppealPartyData.setEmployerData(existingAppealPartyData
					.getEmployerData());
			newAppealPartyData.setMailingAddress(existingAppealPartyData
					.getMailingAddress());
			newAppealPartyData.setPartyName(existingAppealPartyData
					.getPartyName());
			newAppealPartyData.setPartyOf(existingAppealPartyData
					.getPartyOf());
			newAppealPartyData.setPartyType(existingAppealPartyData
					.getPartyType());
			newAppealPartyData.setPhoneNumber(existingAppealPartyData
					.getPhoneNumber());
			newAppealPartyData.setSsn(existingAppealPartyData.getSsn());

			newPartySet.add(newAppealPartyData);

		}

		newBorAppeal.setAppealPartySet(newPartySet);
		newAppealToSave.saveOrUpdate();
		newAppealToSave.flush();
		//throw an error while testing to keep my data unsaved
		//Object o = null;
		//o.toString();

	}

	// This method will be called in the load method of hearing screen for a particular external appeal
	@Override
	public IObjectAssembly getExternalAppealInfoForHearing(final IObjectAssembly objectAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getExternalAppealInfoForHearing");
		}

		Long appealId = objectAssembly.getPrimaryKeyAsLong();
		//Appeal appealBO = Appeal.findByPrimaryKey(appealId);
		AppealData appealData = Appeal.fetchAppealAlongWithAppealChildByAppealId(appealId);
		List<?> appealList = Appeal.getAllAppealListByDocketNumber(appealData.getDocketNumber());
		//Long decisionIdForADJD =null;
		//Long decisionIdForALJ =null;
		//Long decisionIdForBOR =null;
		Long decisionId = null;

		List<AppealCorrespondenceBean> correspondenceList = new ArrayList<AppealCorrespondenceBean>();
		List<AppealCorrespondenceBean> decisionList = new ArrayList<AppealCorrespondenceBean>();
		if (appealList!=null && !appealList.isEmpty()){
			for (int i=0;i<appealList.size();i++){

				AppealData appealDataFromList = (AppealData)appealList.get(i);

				if (appealDataFromList.getAppealledAgainstDecision()!=null){
					if (DecisionStatusEnum.APPEALS_JUDGE.getName().equals(appealDataFromList.getAppealLevel())){
						AppealCorrespondenceBean appealCorrespondenceBean = new AppealCorrespondenceBean();
						// adjucator decision
						decisionId =appealDataFromList.getAppealledAgainstDecision().getDecisionID();
						if (AppealTypeEnum.NON_MON.getName().equals(appealDataFromList.getAppealType())){
							Long issueId = appealDataFromList.getAppealledAgainstDecision().getIssueData().getIssueId();
							objectAssembly.setPrimaryKey(IssueData.class,issueId);
						}
						appealCorrespondenceBean.setDecisionId(decisionId.toString());
						appealCorrespondenceBean.setAppealType(appealDataFromList.getAppealType());
						appealCorrespondenceBean.setDecisionLevel(DecisionStatusEnum.ADJUDICATED.getName());
						decisionList.add(appealCorrespondenceBean);
						Set<DecisionData> aljDecisionSet = appealDataFromList.getDecisions();
						Iterator<DecisionData> iterator = aljDecisionSet.iterator();
						AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = null;
						while(iterator.hasNext()){
							appealCorrespondenceBeanForAljDesc = new AppealCorrespondenceBean();
							DecisionData aljDecisionData = (DecisionData) iterator.next();
							appealCorrespondenceBeanForAljDesc.setDecisionId(aljDecisionData.getDecisionID().toString());
							appealCorrespondenceBeanForAljDesc.setDecisionLevel(DecisionStatusEnum.APPEALS_JUDGE.getName());
							appealCorrespondenceBeanForAljDesc.setAppealType(appealDataFromList.getAppealType());
							decisionList.add(appealCorrespondenceBeanForAljDesc);
						}
					}else if (DecisionStatusEnum.BOARD_OF_REVIEW.getName().equals(appealDataFromList.getAppealLevel())){
						Set<DecisionData> aljDecisionSet = appealDataFromList.getDecisions();
						Iterator<DecisionData> iterator = aljDecisionSet.iterator();
						AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = null;
						while(iterator.hasNext()){
							appealCorrespondenceBeanForAljDesc = new AppealCorrespondenceBean();
							DecisionData aljDecisionData = (DecisionData) iterator.next();
							appealCorrespondenceBeanForAljDesc.setDecisionId(aljDecisionData.getDecisionID().toString());
							appealCorrespondenceBeanForAljDesc.setDecisionLevel(DecisionStatusEnum.BOARD_OF_REVIEW.getName());
							appealCorrespondenceBeanForAljDesc.setAppealType(appealDataFromList.getAppealType());
							decisionList.add(appealCorrespondenceBeanForAljDesc);
						}
					}else if (DecisionStatusEnum.CIRCUIT_COURT.getName().equals(appealDataFromList.getAppealLevel())
							||DecisionStatusEnum.COURT_OF_APPEAL.getName().equals(appealDataFromList.getAppealLevel())){
						//Set aljDecisionSet = appealDataFromList.getDecisions();
						//Iterator iterator = aljDecisionSet.iterator();
						//while(iterator.hasNext()){
						AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = new AppealCorrespondenceBean();
						//DecisionData aljDecisionData = (DecisionData) iterator.next();
						appealCorrespondenceBeanForAljDesc.setDecisionId(appealDataFromList.getAppealId().toString());
						appealCorrespondenceBeanForAljDesc.setDecisionLevel(DecisionStatusEnum.CIRCUIT_COURT.getName());
						appealCorrespondenceBeanForAljDesc.setAppealType(appealDataFromList.getAppealType());
						decisionList.add(appealCorrespondenceBeanForAljDesc);
						//}
					}
					//appealCorrespondenceBean.setDecisionId(decisionId.toString());

					//        			List correspondenceDataByParameter6 = Correspondence.getCorrespondenceDataByParameter6(decisionId.toString(),CorrespondenceParameterEnum.DECISION_ID.getName());
					//        			if (correspondenceDataByParameter6!=null && correspondenceDataByParameter6.size()>0){
					//        				CorrespondenceData corrData = (CorrespondenceData)correspondenceDataByParameter6.get(0);
					//        				appealCorrespondenceBean.setCorrespondenceId(corrData.getCorrespondenceId().toString());
					//        				//correspondenceList.add(corrData);
					//        			}
					//        			correspondenceList.add(appealCorrespondenceBean);

				}else { 
					//other than non mon appeals.
					if (DecisionStatusEnum.APPEALS_JUDGE.getName().equals(appealDataFromList.getAppealLevel())){
						//    					AppealCorrespondenceBean appealCorrespondenceBean = new AppealCorrespondenceBean();
						//        				decisionId =appealDataFromList.getAppealledAgainstDecision().getDecisionID();// adjucator decision
						//
						//        				appealCorrespondenceBean.setDecisionId(decisionId.toString());
						//        				appealCorrespondenceBean.setAppealType(appealDataFromList.getAppealType());
						//        				appealCorrespondenceBean.setDecisionLevel(DecisionStatusEnum.ADJUDICATED.getName());
						//        				decisionList.add(appealCorrespondenceBean);
						Set<DecisionData> aljDecisionSet = appealDataFromList.getDecisions();
						Iterator<DecisionData> iterator = aljDecisionSet.iterator();
						AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = null;
						while(iterator.hasNext()){
							appealCorrespondenceBeanForAljDesc = new AppealCorrespondenceBean();
							DecisionData aljDecisionData = (DecisionData) iterator.next();
							appealCorrespondenceBeanForAljDesc.setDecisionId(aljDecisionData.getDecisionID().toString());
							appealCorrespondenceBeanForAljDesc.setDecisionLevel(DecisionStatusEnum.APPEALS_JUDGE.getName());
							appealCorrespondenceBeanForAljDesc.setAppealType(appealDataFromList.getAppealType());
							decisionList.add(appealCorrespondenceBeanForAljDesc);
						}
					}else if (DecisionStatusEnum.BOARD_OF_REVIEW.getName().equals(appealDataFromList.getAppealLevel())){
						Set<DecisionData> aljDecisionSet = appealDataFromList.getDecisions();
						Iterator<DecisionData> iterator = aljDecisionSet.iterator();
						AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = null;
						while(iterator.hasNext()){
							appealCorrespondenceBeanForAljDesc = new AppealCorrespondenceBean();
							DecisionData aljDecisionData = (DecisionData) iterator.next();
							appealCorrespondenceBeanForAljDesc.setDecisionId(aljDecisionData.getDecisionID().toString());
							appealCorrespondenceBeanForAljDesc.setDecisionLevel(DecisionStatusEnum.BOARD_OF_REVIEW.getName());
							appealCorrespondenceBeanForAljDesc.setAppealType(appealDataFromList.getAppealType());
							decisionList.add(appealCorrespondenceBeanForAljDesc);
						}
					}else if (DecisionStatusEnum.CIRCUIT_COURT.getName().equals(appealDataFromList.getAppealLevel())
							||DecisionStatusEnum.COURT_OF_APPEAL.getName().equals(appealDataFromList.getAppealLevel())){
						//Set aljDecisionSet = appealDataFromList.getDecisions();
						//Iterator iterator = aljDecisionSet.iterator();
						//while(iterator.hasNext()){
						AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = new AppealCorrespondenceBean();
						//DecisionData aljDecisionData = (DecisionData) iterator.next();
						appealCorrespondenceBeanForAljDesc.setDecisionId(appealDataFromList.getAppealId().toString());
						appealCorrespondenceBeanForAljDesc.setDecisionLevel(DecisionStatusEnum.CIRCUIT_COURT.getName());
						appealCorrespondenceBeanForAljDesc.setAppealType(appealDataFromList.getAppealType());
						decisionList.add(appealCorrespondenceBeanForAljDesc);
						//}
					}//End Else if of APpeal Level

				}// End Else of Appealed against decision not present
			}
			for (int j=0;j<decisionList.size();j++){
				AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = decisionList.get(j);
				AppealCorrespondenceBean appealCorrespondenceBean = null;
				if (AppealTypeEnum.NON_MON.getName().equalsIgnoreCase(appealData.getAppealType())){
					if (DecisionStatusEnum.ADJUDICATED.getName().equalsIgnoreCase(appealCorrespondenceBeanForAljDesc.getDecisionLevel())){
						if (AppealPartyTypeEnum.EMPLOYER.getName().equalsIgnoreCase(appealData.getAppellant()) ||
								AppealPartyTypeEnum.EMPLOYER.getName().equalsIgnoreCase(appealData.getOpponent())){
							EmployerDecisionLetterData employerDecisionLetterData = Correspondence.getEmployerDecisionLetterData(appealCorrespondenceBeanForAljDesc.getDecisionId());
							CorrespondenceData corrData = null;
							if (employerDecisionLetterData!=null){
								corrData = Correspondence.getCorrespondenceDataByParameter6ForEmployer(employerDecisionLetterData.getEmployerDecisionLetterId().toString());
							}
							if(corrData!=null){
								appealCorrespondenceBean = appealCorrespondenceBeanForAljDesc;
								appealCorrespondenceBean.setCorrespondenceId(corrData.getCorrespondenceId().toString());
							}else{
								appealCorrespondenceBean = appealCorrespondenceBeanForAljDesc;
								appealCorrespondenceBean.setAdjudicatorDecLetterIsthereOrNot(GlobalConstants.DB_ANSWER_NO);
							}
						}
						if (AppealPartyTypeEnum.CLAIMANT.getName().equalsIgnoreCase(appealData.getAppellant()) ||
								AppealPartyTypeEnum.CLAIMANT.getName().equalsIgnoreCase(appealData.getOpponent())){
							// claimant is involved
							ClaimantDecisionLetterData claimantDecisionLetterData = Correspondence.getClaimantDecisionLetterData(appealCorrespondenceBeanForAljDesc.getDecisionId());
							CorrespondenceData corrData = null;
							if (claimantDecisionLetterData!=null){
								corrData = Correspondence.getCorrespondenceDataByParameter6ForClaimant(claimantDecisionLetterData.getClaimantDecisionLetterId().toString());
							}
							if(corrData!=null){
								appealCorrespondenceBean = appealCorrespondenceBeanForAljDesc;
								appealCorrespondenceBean.setCorrespondenceId(corrData.getCorrespondenceId().toString());
							}else{
								appealCorrespondenceBean = appealCorrespondenceBeanForAljDesc;
								appealCorrespondenceBean.setAdjudicatorDecLetterIsthereOrNot(GlobalConstants.DB_ANSWER_NO);
							}
							//appealCorrespondenceBean.setCorrespondenceId(corrData.getCorrespondenceId().toString());
						}
					}else{
						if (DecisionStatusEnum.CIRCUIT_COURT.getName().equalsIgnoreCase(appealCorrespondenceBeanForAljDesc.getDecisionLevel())||
								DecisionStatusEnum.COURT_OF_APPEAL.getName().equalsIgnoreCase(appealCorrespondenceBeanForAljDesc.getDecisionLevel())){

							List<?> list = Correspondence.getCorrespondenceDataByParameter6ForExternal(appealCorrespondenceBeanForAljDesc.getDecisionId(),
									CorrespondenceParameterEnum.APPEAL_ID.getName(),CorrespondenceCodeEnum.EXTERNAL_APPEAL_TRANSCRIPTION.getName());
							if (list!=null){
								CorrespondenceData corrData = (CorrespondenceData) list.get(0);
								appealCorrespondenceBean = appealCorrespondenceBeanForAljDesc;
								appealCorrespondenceBean.setCorrespondenceId(corrData.getCorrespondenceId().toString());
							}
						}else{
							appealCorrespondenceBean = getCorrespondenceDataByParameter6(appealCorrespondenceBeanForAljDesc);
						}

					}
				}else{
					// for all other appeal types
					//appealCorrespondenceBean = getCorrespondenceDataByParameter6(appealCorrespondenceBeanForAljDesc);
					if (DecisionStatusEnum.CIRCUIT_COURT.getName().equalsIgnoreCase(appealCorrespondenceBeanForAljDesc.getDecisionLevel())||
							DecisionStatusEnum.COURT_OF_APPEAL.getName().equalsIgnoreCase(appealCorrespondenceBeanForAljDesc.getDecisionLevel())){
						List<?> list = Correspondence.getCorrespondenceDataByParameter6ForExternal(appealCorrespondenceBeanForAljDesc.getDecisionId(),
								CorrespondenceParameterEnum.APPEAL_ID.getName(),CorrespondenceCodeEnum.EXTERNAL_APPEAL_TRANSCRIPTION.getName());
						if (list!=null && !list.isEmpty()){
							CorrespondenceData corrData = (CorrespondenceData) list.get(0);
							appealCorrespondenceBean = appealCorrespondenceBeanForAljDesc;
							appealCorrespondenceBean.setCorrespondenceId(corrData.getCorrespondenceId().toString());
						}
					}else{
						appealCorrespondenceBean = getCorrespondenceDataByParameter6(appealCorrespondenceBeanForAljDesc);
					}
				}

				correspondenceList.add(appealCorrespondenceBean);
			}
			//AppealCorrespondenceBean appealCorrespondenceBeanForExternal = new AppealCorrespondenceBean();

			if(correspondenceList!=null && !correspondenceList.isEmpty()){
				objectAssembly.addBeanList(correspondenceList,true);
			}

		}

		/*DecisionData decisionData = appealData.getAppealledAgainstDecision();
    	if (decisionData.getIssueData()!=null){
    		List decisionDataList = Decision.getDecisionDataListByIssueId(decisionData.getIssueData().getIssueId());
    		objectAssembly.addComponentList(decisionDataList,true);
    	}*/
		ExternalAppealHearingBean newBean = new ExternalAppealHearingBean();
		newBean.setAppealDate(appealData.getAppealDate().toString());
		String externalAgency=(String)CacheUtility.getCachePropertyValue("T_APPEAL_EXT_COURTS",KEY,appealData.getAppealLevel(),DESCRIPTION);
		String appellant =(String)CacheUtility.getCachePropertyValue("T_MST_APPELLANT",KEY,appealData.getAppellant(),DESCRIPTION);
		String opponenet =(String)CacheUtility.getCachePropertyValue("T_MST_APPELLANT",KEY,appealData.getOpponent(),DESCRIPTION);
		newBean.setAppealData(appealData);
		newBean.setExternalAgency(externalAgency);
		newBean.setAppeallant(appellant);
		newBean.setOpponent(opponenet);
		newBean.setDocketNumber(appealData.getDocketNumber().toString());
		Set<AppealPartyData> appealPartySet = appealData.getAppealPartySet();
		Iterator<AppealPartyData> iterator = appealPartySet.iterator();
		while (iterator.hasNext()){
			AppealPartyData appealPartyData = (AppealPartyData) iterator.next();
			if (GlobalConstants.APPELLANT.equals(appealPartyData.getPartyType())){
				if (appealPartyData.getClaimantData()!=null){
					newBean.setClaimantName(appealPartyData.getClaimantData().getClaimantFullName());
					newBean.setClaimantSsn(appealPartyData.getClaimantData().getSsn());
				}else if (appealPartyData.getEmployerData()!=null){
					newBean.setEmployerEan(appealPartyData.getEmployerData().getEan());
					newBean.setEmployerName(appealPartyData.getEmployerData().getEmployerName());
				}else if (appealPartyData.getSsn()!=null){
					newBean.setClaimantName(appealPartyData.getPartyName());
					newBean.setClaimantSsn(appealPartyData.getSsn());
				}
			} else if(GlobalConstants.OPPONENT.equals(appealPartyData.getPartyType())) {
				if (appealPartyData.getClaimantData()!=null){
					newBean.setOpponentId(appealPartyData.getClaimantData().getSsn());
				}else if (appealPartyData.getEmployerData()!=null){
					newBean.setOpponentId(appealPartyData.getEmployerData().getEan());
				}else if (appealPartyData.getSsn()!=null){
					newBean.setOpponentId(appealPartyData.getSsn());
				}
			}
		}
		objectAssembly.addBean(newBean,true);
		objectAssembly.setPrimaryKey(appealId);
		objectAssembly.addComponent(appealData,true);
		return objectAssembly;
	}

	@Override
	public IObjectAssembly getAppealInformationForTranscription(final IObjectAssembly objectAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getAppealInformationForTranscription");
		}

		Long appealId = objectAssembly.getPrimaryKeyAsLong();
		//Appeal appealBO = Appeal.findByPrimaryKey(appealId);
		AppealData appealData = Appeal.fetchAppealAlongWithAppealChildByAppealId(appealId);
		ExternalAppealHearingBean newBean = new ExternalAppealHearingBean();
		newBean.setAppealDate(appealData.getAppealDate().toString());
		String externalAgency=(String)CacheUtility.getCachePropertyValue("T_APPEAL_EXT_COURTS",KEY,appealData.getAppealLevel(),DESCRIPTION);
		String appellant =(String)CacheUtility.getCachePropertyValue("T_MST_APPELLANT",KEY,appealData.getAppellant(),DESCRIPTION);
		newBean.setAppealData(appealData);
		newBean.setExternalAgency(externalAgency);
		newBean.setAppeallant(appellant);
		newBean.setDocketNumber(appealData.getDocketNumber().toString());
		Set<AppealPartyData> appealPartySet = appealData.getAppealPartySet();
		Iterator<AppealPartyData> iterator = appealPartySet.iterator();
		while (iterator.hasNext()){
			AppealPartyData appealPartyData = (AppealPartyData) iterator.next();
			if (GlobalConstants.APPELLANT.equals(appealPartyData.getPartyType())){
				if (appealPartyData.getClaimantData()!=null){
					newBean.setClaimantName(appealPartyData.getClaimantData().getClaimantFullName());
					newBean.setClaimantSsn(appealPartyData.getClaimantData().getSsn());
				}else if (appealPartyData.getEmployerData()!=null){
					if (AppealTypeEnum.TAXA.getName().equals(appealData.getAppealType())){
						newBean.setEmployerEan(((RegisteredEmployerData)appealPartyData.getEmployerData()).getEmployerAccountData().getEan());
						newBean.setEmployerName(((RegisteredEmployerData)appealPartyData.getEmployerData()).getEmployerAccountData().getEntityName());
					}else{
						newBean.setEmployerEan(appealPartyData.getEmployerData().getEan());
						newBean.setEmployerName(appealPartyData.getEmployerData().getEmployerName());
					}
				}else if (appealPartyData.getSsn()!=null){
					newBean.setClaimantName(appealPartyData.getPartyName());
					newBean.setClaimantSsn(appealPartyData.getSsn());
				}
			}
		}
		objectAssembly.addBean(newBean,true);
		objectAssembly.setPrimaryKey(appealId);
		objectAssembly.addComponent(appealData,true);
		return objectAssembly;
	}

	/**
	 * Retrieving the correspondence from the decision id
	 * @param appealCorrespondenceBean AppealCorrespondenceBean
	 * @return AppealCorrespondenceBean
	 */
	private AppealCorrespondenceBean getCorrespondenceDataByParameter6(final AppealCorrespondenceBean appealCorrespondenceBean){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getCorrespondenceDataByParameter6");
		}

		//List correspondenceList = new ArrayList();
		List<?> correspondenceDataByParameter6 = Correspondence.getCorrespondenceDataByParameter6(appealCorrespondenceBean.getDecisionId(),CorrespondenceParameterEnum.DECISION_ID.getName());
		if (correspondenceDataByParameter6!=null && !correspondenceDataByParameter6.isEmpty()){
			CorrespondenceData corrData = (CorrespondenceData)correspondenceDataByParameter6.get(0);
			appealCorrespondenceBean.setCorrespondenceId(corrData.getCorrespondenceId().toString());
			//correspondenceList.add(corrData);
		}
		//correspondenceList.add(appealCorrespondenceBean);
		return appealCorrespondenceBean;
	}

	// From External Appeal Hearing screen this method will be invoked
	// which will save the transcription and create one work item for the supervisor
	@Override
	public IObjectAssembly updateExternalAppByDocFile(final IObjectAssembly objectAssembly) throws IOException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method updateExternalAppByDocFile");
		}

		// code for implementing wrok item for hearing
		//AppealData appealData = (AppealData) objectAssembly.getFirstComponent(AppealData.class);
		Long appealId = objectAssembly.getPrimaryKeyAsLong();
		ExternalAppealHearingBean newBean = (ExternalAppealHearingBean) objectAssembly.getFirstBean(ExternalAppealHearingBean.class);
		/* CIF_01121
		 * commented there will be no correspondence generated
		 * 
		 * CorrespondenceData correspondenceData = new CorrespondenceData();
    	correspondenceData.setCorrespondenceCode(CorrespondenceCodeEnum.EXTERNAL_APPEAL_TRANSCRIPTION.getName());
 		correspondenceData.setDirection(CorrespondenceDirectionEnum.INCOMING.getName());
 		correspondenceData.setParameter6(String.valueOf(appealId));
 		correspondenceData.setParameter6Desc(CorrespondenceParameterEnum.APPEAL_ID.getName());
 		correspondenceData.setUpdatedBy(getContextUserId());
 		correspondenceData.setUpdatedOn(new Date());
 		Correspondence Correspondence = new Correspondence(correspondenceData);
 		Correspondence.saveOrUpdate();*/

		AppealData appealData = Appeal.findByAppealId(appealId);
		//    	FormFile avFile = newBean.getAvFile();
		//    	String uploadFormat = "";
		//		String nameOfFileWithoutExtn = "";
		//		InputStream inputStream = null;
		//		StringTokenizer stoken = new StringTokenizer(avFile.getFileName(), ".");
		//		if (stoken.hasMoreTokens()){
		//			 nameOfFileWithoutExtn = stoken.nextToken();
		//			 uploadFormat = stoken.nextToken();
		//		}

		/* CIF_01121
		 * commented need to work on this once the functionality for
		 * saving the file in the file system is done
		 * 
		 * BaseDmsDAO dao = new BaseDmsDAO();
		try{
		dao.setReadWriteSession();*/



		//		if (GlobalConstants.DOC_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
		//			 byte[] fileData = avFile.getFileData();
		//			 String fileName = avFile.getFileName();
		//			 //String basePath = "C:/UploadDoc";
		////			 File baseDir = new File(basePath);
		////			 if(!baseDir.exists()){
		////				  baseDir.mkdirs();
		////		     }
		//			 String uploadedFileName =fileName + GlobalConstants.DOC_FILE_EXTENSION;
		//			 File newFile = new File(uploadedFileName);
		//			 inputStream = avFile.getInputStream();
		//	         //upload the file to the directory specified
		//		     //OutputStream fileTobeWritten = new FileOutputStream(newFile);
		//		     //fileTobeWritten.write(fileData);
		//		     //fileTobeWritten.close();
		//		}
		//		if (newBean.getClaimantSsn()!=null){
		//			ClaimantDmsBean clmtDmsBean = new ClaimantDmsBean();
		//			clmtDmsBean.setSsn(newBean.getClaimantSsn());
		//			CorrespoandenceDmsBean corrClmtBean = clmtDmsBean.getNewCorrespoandenceDmsBean();
		//			corrClmtBean.setCorrId(String.valueOf(correspondenceData.getCorrespondenceId()));
		//			corrClmtBean.setDocCode(CorrespondenceCodeEnum.EXTERNAL_APPEAL_TRANSCRIPTION.getName());
		//			corrClmtBean.setInboundDirection();
		//			Calendar cal = new GregorianCalendar();
		//			cal.setTime(new Date());
		//			corrClmtBean.setMailDate(cal);
		//			corrClmtBean.setTitle(CorrespondenceCodeEnum.EXTERNAL_APPEAL_TRANSCRIPTION.getName());
		//			corrClmtBean.setStream(inputStream);
		//			corrClmtBean.setMimeType("application/msword");
		//			dao.addClaimantCorrespondence(clmtDmsBean);
		//		}
		//		else if(newBean.getAppeallant().equalsIgnoreCase(AppealPartyTypeEnum.EMPLOYER.getDescription())){
		//			EmployerDmsBean empDmsBean = new EmployerDmsBean();
		//			empDmsBean.setEan(newBean.getEmployerEan().substring(0,8));
		//			CorrespoandenceDmsBean corrEmpBean = empDmsBean.getNewCorrespoandenceDmsBean();
		//			corrEmpBean.setCorrId(String.valueOf(correspondenceData.getCorrespondenceId()));
		//			corrEmpBean.setDocCode(CorrespondenceCodeEnum.EXTERNAL_APPEAL_TRANSCRIPTION.getName());
		//			corrEmpBean.setInboundDirection();
		//			Calendar cal = new GregorianCalendar();
		//			cal.setTime(new Date());
		//			corrEmpBean.setMailDate(cal);
		//			corrEmpBean.setTitle(CorrespondenceCodeEnum.EXTERNAL_APPEAL_TRANSCRIPTION.getName());
		//			corrEmpBean.setStream(inputStream);
		//			corrEmpBean.setMimeType("application/msword");//CorrespoandenceDmsBean.mimeTypeForDocType
		//			dao.addEmployerCorrespondence(empDmsBean);
		//		}else{// use appeal node to save the document


		/* CIF_01121
		 * commented need to work on this once the functionality for
		 * saving the file in the file system is done

			AppealDmsBean appealDmdBean = new AppealDmsBean();
			appealDmdBean.setDdockectNo(appealData.getDocketNumber().toString());
			//empDmsBean.setEan(newBean.getEmployerEan().substring(0,8));
			CorrespoandenceDmsBean corrEmpBean = appealDmdBean.getNewCorrespoandenceDmsBean();
			corrEmpBean.setCorrId(String.valueOf(correspondenceData.getCorrespondenceId()));
			corrEmpBean.setDocCode(CorrespondenceCodeEnum.EXTERNAL_APPEAL_TRANSCRIPTION.getName());
			corrEmpBean.setInboundDirection();
			Calendar cal = new GregorianCalendar();
			cal.setTime(new Date());
			corrEmpBean.setMailDate(cal);
			corrEmpBean.setTitle(CorrespondenceCodeEnum.EXTERNAL_APPEAL_TRANSCRIPTION.getName());
			corrEmpBean.setStream(newBean.getInputStream());
			corrEmpBean.setMimeType("application/msword");//CorrespoandenceDmsBean.mimeTypeForDocType
			dao.addAppealCorrespondence(appealDmdBean);


		dao.save();*/


		/* CIF_01121
		 * commented, there will be no more work item EXT_APPEAL_RESPONSE
		 * so directly changing the status to Pending for decision	 
 		    appealData.setAppealStatus(AppealStatusEnum.RESPONSE_PENDING.getName());
		 */
		if(newBean.getTranscriptYesNo().equals(GlobalConstants.DB_ANSWER_YES)){
			appealData.setAppealStatus(AppealStatusEnum.TRANS_PENDING.getName());
		}
		else
		{
			appealData.setAppealStatus(AppealStatusEnum.READY_REVIEW.getName());
		}
		
		
		
		//appealData.setAppealStatus(AppealStatusEnum.PENDING_DECISION.getName());


		/* 
		 *  CIF_01121
		 * commented, there will be no more work item EXT_APPEAL_RESPONSE
		 * 
		 *  		 
    	//ExternalAppealHearingBean newBean = (ExternalAppealHearingBean)objectAssembly.getFirstBean(ExternalAppealHearingBean.class);
    	IBaseWorkflowDAO baseWorkflowDAO = DAOFactory.instance.getBaseWorkflowDAO();
	    BaseAccessDsContainerBean bean = new BaseAccessDsContainerBean();
	    GlobalDsContainerBean globalBean = bean.getNewGlobalDsContainerBean();
	    globalBean.setBusinessKey(appealId.toString());
	    //Appeal appealBO = Appeal.findByPrimaryKey(appealId);findByAppealId
	   // AppealData appealData = Appeal.findByAppealId(appealId);
	    // iterator will update the status as response pending as transcription is complete here


	    // while creating work item if appellant is found to be claimant
	    // ist if will execute or else if will execute for emloyer ean

	    if (objectAssembly.getSsn()!=null){
	    	globalBean.setName(newBean.getClaimantName());
	    	globalBean.setSsneanfein(objectAssembly.getSsn());
	    	globalBean.setTypeAsSsn();
	    }else if (objectAssembly.getEan()!=null){
	    	globalBean.setName(newBean.getEmployerName());
	    	globalBean.setSsneanfein(objectAssembly.getEan());
	    	globalBean.setTypeAsEan();
	    }
	    baseWorkflowDAO.createAndStartProcessInstance(AppealWorkflowConstants.EXT_APPEAL_RESPONSE,bean);
	    //The work item created for hearing information is closed here
	    WorkflowItemBean workflowItemBean = (WorkflowItemBean)objectAssembly.getFirstBean(WorkflowItemBean.class);


	 // START: CIF_00982 || Code change w.r.t new BRMS approach
	    // baseWorkflowDAO.startWorkItem(workflowItemBean.getPersistentOid());
		HashMap<String, String> mapValues = new HashMap<String, String>();
    	mapValues.put("persistenceOid", workflowItemBean.getPersistentOid());
    	WorkflowTransactionService wf = new WorkflowTransactionService();
    	wf.invokeWorkFlowOperation(WorkFlowOperationsEnum.Start.getName(), mapValues);*/
		// END: CIF_00982 || Code change w.r.t new BRMS approach

		/* CIF_01121
		 * commented need to work on this once the functionality for
		 * saving the file in the file system is done
    } finally {

			if(dao != null) {
				dao.logout();
			}
		}*/

		//CIF_01121 start
		// email notification for LIRC and close work item
		sendLIRCTranscriptEmailNotification(newBean.getTranscriptYesNo(),appealData);
		
		final Map<String, Object> mapValues = (Map<String, Object>)objectAssembly.getData(GlobalConstants.WI_MAP_VALUES);	

		WorkflowTransactionService wflow = new WorkflowTransactionService();
		wflow.invokeWorkFlowOperation(WorkFlowOperationsEnum.START_AAND_COMPLETE.getName(), mapValues);
		//CIF_01121 end
		
		
		return objectAssembly;
	}
	// From document list screen MDES response date is captured and through this method appeal data
	// will be updated
	@Override
	public IObjectAssembly saveOrUpdateExternalAppealsByResponseData(final IObjectAssembly objectAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveOrUpdateExternalAppealsByResponseData");
		}

		ExternalAppealHearingBean newBean = (ExternalAppealHearingBean)objectAssembly.getFirstBean(ExternalAppealHearingBean.class);
		if (newBean.getAppealData() instanceof NonMonAppealData){
			NonMonAppealData nonMonAppealData = (NonMonAppealData)objectAssembly.getFirstComponent(NonMonAppealData.class);
			NonMonAppeal nonMonAppealBO =  new NonMonAppeal(nonMonAppealData);
			nonMonAppealBO.saveOrUpdate();
		}else if (newBean.getAppealData() instanceof TaxInterceptAppealData){
			TaxInterceptAppealData taxInterceptAppData = (TaxInterceptAppealData)objectAssembly.getFirstComponent(TaxInterceptAppealData.class);
			TaxInterceptAppeal taxInterceptAppeal = new TaxInterceptAppeal(taxInterceptAppData);
			taxInterceptAppeal.saveOrUpdate();
		}else if (newBean.getAppealData() instanceof TaxAppealData){
			TaxAppealData taxAppealData = (TaxAppealData)objectAssembly.getFirstComponent(TaxAppealData.class);
			TaxAppeal taxAppeal = new TaxAppeal(taxAppealData);
			taxAppeal.saveOrUpdate();
		}else if (newBean.getAppealData() instanceof MiscAppealData){
			MiscAppealData miscAppealData = (MiscAppealData)objectAssembly.getFirstComponent(MiscAppealData.class);
			MiscAppeal miscAppeal = new MiscAppeal(miscAppealData);
			miscAppeal.saveOrUpdate();
		}else if (newBean.getAppealData() instanceof LateAppealData) {
			LateAppealData lateAppealData = (LateAppealData)objectAssembly.getFirstComponent(LateAppealData.class);
			LateAppeal lateAppeal = new LateAppeal(lateAppealData);
			lateAppeal.saveOrUpdate();
		}else if (newBean.getAppealData() instanceof LateDeniedAppealData) {
			LateDeniedAppealData ldadAppealData = (LateDeniedAppealData)objectAssembly.getFirstComponent(LateDeniedAppealData.class);
			LateDeniedAppeal ldadAppeal = new LateDeniedAppeal(ldadAppealData);
			ldadAppeal.saveOrUpdate();
		}else if (newBean.getAppealData() instanceof NonAppearanceAppealData) {
			NonAppearanceAppealData noapAppealData = (NonAppearanceAppealData)objectAssembly.getFirstComponent(NonAppearanceAppealData.class);
			NonApperanceAppeal noapAppeal = new NonApperanceAppeal(noapAppealData);
			noapAppeal.saveOrUpdate();
		}
		// The work item created for supervisor form the hearing information screen is closed here
		WorkflowItemBean workflowItemBean = (WorkflowItemBean)objectAssembly.getFirstBean(WorkflowItemBean.class);

		// START: CIF_00982 || Code change w.r.t new BRMS approach
		// IBaseWorkflowDAO baseWorkflowDAO = DAOFactory.instance.getBaseWorkflowDAO();
		// baseWorkflowDAO.startWorkItem(workflowItemBean.getPersistentOid());
		HashMap<String, String> mapValues = new HashMap<String, String>();
		mapValues.put("persistenceOid", workflowItemBean.getPersistentOid());
		WorkflowTransactionService wflow = new WorkflowTransactionService();
		wflow.invokeWorkFlowOperation(WorkFlowOperationsEnum.START.getName(), mapValues);
		// END: CIF_00982 || Code change w.r.t new BRMS approach

		return objectAssembly;
	}

	/*public IObjectAssembly getTaxInterceptDetails(IObjectAssembly objectAssembly) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getTaxInterceptDetails");
		}

    	TaxInterceptDetailBean newBean = (TaxInterceptDetailBean) objectAssembly.getFirstBean(TaxInterceptDetailBean.class);
    	List taxInterceptDetailsList = TaxInterceptDetails.getTaxInterceptDetailBySSN(newBean.getSsn(),newBean.getTypeOfAppellant());
    	// this list contains the tax intercept details data
    	// adding that data to object assembly
    	if (taxInterceptDetailsList!=null && taxInterceptDetailsList.size()>0){
    		TaxInterceptDetailsData taxInterceptAppealData = (TaxInterceptDetailsData)taxInterceptDetailsList.get(0);
    		objectAssembly.addComponent(taxInterceptAppealData,true);
    	}else {
    		throw new BaseApplicationException("error.access.app.taxappeals.noappeals");
    	}
    	return objectAssembly;
    }*/

	@Override
	public IObjectAssembly getExhibitListForAppeal(final IObjectAssembly objAssembly) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getExhibitListForAppeal");
		}

		String docketNumber = (String)objAssembly.getData("docketNumberForExhibit");
		String docketLevel = (String)objAssembly.getData("docketLevel");

		//Long appealId = (Long)objAssembly.getData("appealIdForExhibit");
		//clear out the exhibit list
		objAssembly.addData("exhibitsInfoForScreenList",null);
		List exhibitsInfoForScreenList = new ArrayList();

		if(docketNumber != null && docketLevel != null){
			IGenericInquiryDAO dao = DAOFactory.instance.getGenericInquiryDAO();
			exhibitsInfoForScreenList = dao.getInquiryResult("getExhibitsByDocketNumberAndAppealLevel", new Object[]{docketNumber,docketLevel});
			if(!exhibitsInfoForScreenList.isEmpty()){
				objAssembly.addData("exhibitsInfoForScreenList",exhibitsInfoForScreenList);
			}
		}

		return objAssembly;
	}
	
	
    /**
     * CIF_01121
     * This method will send email notification to LIRC for transcript required/not required
     * for LIRC recommendation
     * @param transcriptYesNo String
     * @param appealData AppealData
     */
    private void sendLIRCTranscriptEmailNotification(final String transcriptYesNo, final AppealData appealData){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method sendLIRCTranscriptEmailNotification");
		}
    	
    		Email mail = new Email();
    		String host = (String)MailConfigFactory.instance.getHostSessionProps().get("mail.host");
    		mail.setHostName(host);
    		MailAddressConfig mailAddressConfig = MailConfigFactory.instance.getMailAddressConfig("enotify");

    		StringBuffer sbuffer = new StringBuffer(150);
    		sbuffer.append(((MailTemplateConfig)MailConfigFactory.instance.getHeaderConfig("DEFAULT_HTML_START_AND_CSS_HEADER")).getText())
    		.append("<html><table><tr><td class=content>");
    		
    		if(transcriptYesNo.equals(GlobalConstants.DB_ANSWER_YES)){
    			sbuffer.append("The transcript has been uploaded and the LIRC recommendation process can start for the External Appeal Number <b>"
    		    		+appealData.getDocketNumber()+"</b>");
    		}else{
    			sbuffer.append("No transcript is required for the External Appeal Number <b>"
    		    		+appealData.getDocketNumber()+"</b>");
       		}

    		
    		sbuffer.append("</td></tr><br>")
    		.append(((MailTemplateConfig)MailConfigFactory.instance.getFooterConfig("DEFAULT_HTML_TAGS_END_FOOTER")).getText())
    		.append("<br><br>")
    		.append(((MailTemplateConfig)MailConfigFactory.instance.getFooterConfig("DEFAULT_DO_NOT_REPLY_AND_RECEIVE_IN_ERROR_FOOTER")).getText());

    		mail.setHtmlMsg(sbuffer.toString());
    		if(transcriptYesNo.equals(GlobalConstants.DB_ANSWER_YES)){
    			mail.setSubject("Transcript Required for LIRC" );
    		}else{
    			mail.setSubject("Transcript Not Required for LIRC" );
    		}

    		mail.setFrom(mailAddressConfig.getFromEmail(), mailAddressConfig.getFromName());
    		mail.setBounceAddress(mailAddressConfig.getBounceEmail());

    		//System.out.println(sb.toString());
    		//mail.addTo("LIRC.UI.@labor.mo.gov");
    		mail.addTo("anshul.kumar@wyo.gov");
    		mail.sendByHostName();
    		
    }
    
	
	public IObjectAssembly getPartyInfoForExtAppeal(final IObjectAssembly objAssembly) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getPartyInfoForExtAppeal");
		}
		
		
		ProcessExternalAppealBean searchbean = (ProcessExternalAppealBean) objAssembly.getFirstBean(ProcessExternalAppealBean.class);
		AppealInfoBean appealInfobean = (AppealInfoBean) objAssembly.getFirstBean(AppealInfoBean.class);
		
		
		AppealData previousAppealData = (AppealData) searchbean.getAppealData();//(NonMonAppealData) objAssembly.getFirstComponent(NonMonAppealData.class);
		AppealData appealData = (AppealData) objAssembly.getFirstComponent(AppealData.class,true);
		
		// making  set of party for appellant
				Set appealPartySet = new HashSet();
				List appellantInfoList = null;
				List opponentInfoList = null;
				if (!AppealTypeEnum.TAX_INT.getName().equals(searchbean.getAppealType())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equals(appealInfobean.getAppellant())){
						if (AppealPartyEnum.CLMT.getName().equals(previousAppealData.getAppellant())){
							 appellantInfoList = AppealUtil.getAppllantInformation(previousAppealData);
						}else if (AppealPartyEnum.CLMT.getName().equals(previousAppealData.getOpponent())){
							 appellantInfoList = AppealUtil.getOpponentInformation(previousAppealData);
						}
					}else if (AppellantOrOpponentEnum.EMPLOYER.getName().equals(appealInfobean.getAppellant())){
						if (AppealPartyEnum.EMPL.getName().equals(previousAppealData.getAppellant())){
								 appellantInfoList = AppealUtil.getAppllantInformation(previousAppealData);
						}else if (AppealPartyEnum.EMPL.getName().equals(previousAppealData.getOpponent())){
								 appellantInfoList = AppealUtil.getOpponentInformation(previousAppealData);
						}
					}else if (AppellantOrOpponentEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealInfobean.getAppellant())||
							AppellantOrOpponentEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealInfobean.getAppellant())||
							AppellantOrOpponentEnum.CLAIMANT.getName().equals(appealInfobean.getAppellant())){
						
						appellantInfoList = AppealUtil.getAppllantInformation(previousAppealData);
					}
					// handle for Late denied appeal with parent appeal as TAXI
					if (AppealTypeEnum.LDAD.getName().equalsIgnoreCase(searchbean.getAppealType()) ||
							AppealTypeEnum.TAX_INT.getName().equalsIgnoreCase(previousAppealData.getAppealType())){
						if (AppellantOrOpponentEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealInfobean.getAppellant())||
								AppellantOrOpponentEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealInfobean.getAppellant())||
								AppellantOrOpponentEnum.CLAIMANT.getName().equals(appealInfobean.getAppellant())){
							appellantInfoList = AppealUtil.getAppllantInformation(previousAppealData);
						}else if(AppellantOrOpponentEnum.EMPLOYER.getName().equals(appealInfobean.getAppellant())){
							appellantInfoList = AppealUtil.getAppllantInformation(previousAppealData);
						}
					}
					//3916 Added code for type No Appearance
					if (AppealTypeEnum.NOAP.getName().equalsIgnoreCase(searchbean.getAppealType()) ||
							AppealTypeEnum.TAX_INT.getName().equalsIgnoreCase(previousAppealData.getAppealType())){
						if (AppellantOrOpponentEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealInfobean.getAppellant())||
								AppellantOrOpponentEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealInfobean.getAppellant())||
								AppellantOrOpponentEnum.CLAIMANT.getName().equals(appealInfobean.getAppellant())){
							appellantInfoList = AppealUtil.getAppllantInformation(previousAppealData);
						}else if(AppellantOrOpponentEnum.EMPLOYER.getName().equals(appealInfobean.getAppellant())){
							appellantInfoList = AppealUtil.getAppllantInformation(previousAppealData);
						}
					}
					
					AppealPartyData appealPartyAppellantData = null;
					if (appellantInfoList != null && appellantInfoList.size() > 0){
						appealPartyAppellantData = (AppealPartyData)appellantInfoList.get(0);
					}
					AppealPartyData newAppealPartyForAppellantData = new AppealPartyData();
					//newAppealPartyForAppellantData.setPartyType(currentForm.getAppeallant());
					/*
					 * in case of appeal party as appellant party type will always be APNT
					 * Defect 1966*/
					newAppealPartyForAppellantData.setPartyType(AppealPartyTypeEnum.APPELLANT.getName());
					//CIF_01907 || Defect 2258 change the condition to match with Enum value for division
					if(appealInfobean.getAppellant().contains(AppellantOrOpponentEnum.MDES.getName())){
						newAppealPartyForAppellantData.setPartyName(AppellantOrOpponentEnum.MDES.getName());
					}
					else {
						newAppealPartyForAppellantData.setPartyName(appealPartyAppellantData.getPartyName());
					}
					
					newAppealPartyForAppellantData.setAppealData(appealData);
					if(appealInfobean.getAppellant().contains(AppellantOrOpponentEnum.MDES.getName())){
						newAppealPartyForAppellantData.setPartyName(AppellantOrOpponentEnum.MDES.getName());
					}
					else {
						searchbean.setFullName(appealPartyAppellantData.getPartyName());
					}
//					if (AppealTypeEnum.MISC.getName().equals(searchbean.getAppealType())){
//						if (StringUtility.isNotBlank(appealPartyAppellantData.getSsn())){
//							objAssembly.setSsn(appealPartyAppellantData.getSsn());
//							searchbean.setSsn(appealPartyAppellantData.getSsn());
//							newAppealPartyForAppellantData.setSsn(appealPartyAppellantData.getSsn());
//						}
//					}
					if(appealPartyAppellantData != null){
						if (appealPartyAppellantData.getClaimantData()!=null){
							newAppealPartyForAppellantData.setClaimantData(appealPartyAppellantData.getClaimantData());
							objAssembly.setSsn(appealPartyAppellantData.getClaimantData().getSsn());
							searchbean.setSsn(appealPartyAppellantData.getClaimantData().getSsn());
							newAppealPartyForAppellantData.setSsn(appealPartyAppellantData.getSsn());
						}else if (appealPartyAppellantData.getEmployerData()!=null){
							newAppealPartyForAppellantData.setEmployerData(appealPartyAppellantData.getEmployerData());
							objAssembly.setEan(appealPartyAppellantData.getEmployerData().getEan());
							searchbean.setEan(appealPartyAppellantData.getEmployerData().getEan());
							newAppealPartyForAppellantData.setEmployerData(appealPartyAppellantData.getEmployerData());
						}
					}
					///need something here for when it's null
					
					appealPartySet.add(newAppealPartyForAppellantData);
					appealData.setAppealPartySet(appealPartySet);
					
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equals(appealInfobean.getOpponent())){
						if (AppealPartyEnum.CLMT.getName().equals(previousAppealData.getAppellant())){
							opponentInfoList = AppealUtil.getAppllantInformation(previousAppealData);
						}else if (AppealPartyEnum.CLMT.getName().equals(previousAppealData.getOpponent())){
							opponentInfoList = AppealUtil.getOpponentInformation(previousAppealData);
						}
					}else if (AppellantOrOpponentEnum.EMPLOYER.getName().equals(appealInfobean.getOpponent())){
						if (AppealPartyEnum.EMPL.getName().equals(previousAppealData.getAppellant())){
							opponentInfoList = AppealUtil.getAppllantInformation(previousAppealData);
						}else if (AppealPartyEnum.EMPL.getName().equals(previousAppealData.getOpponent())){
							opponentInfoList = AppealUtil.getOpponentInformation(previousAppealData);
						}
					}
					/*if opponent is MDES then a new party to be made with MDES*/
					if (!AppellantOrOpponentEnum.MDES.getName().equals(appealInfobean.getOpponent())){
						if (opponentInfoList!=null && opponentInfoList.size()>0){
							AppealPartyData appealPartyOpponentData = (AppealPartyData)opponentInfoList.get(0);
							AppealPartyData newAppealPartyForOpponentData = new AppealPartyData();
							//newAppealPartyForOpponentData.setPartyType(appealPartyOpponentData.getPartyType());
							newAppealPartyForOpponentData.setPartyType(AppealPartyTypeEnum.OPPONENT.getName());
							newAppealPartyForOpponentData.setPartyName(appealPartyOpponentData.getPartyName());
							newAppealPartyForOpponentData.setAppealData(appealData);
							//searchbean.setFullName(appealPartyOpponentData.getPartyName());
							if (appealPartyOpponentData.getClaimantData()!=null){
								newAppealPartyForOpponentData.setClaimantData(appealPartyOpponentData.getClaimantData());
								objAssembly.setSsn(appealPartyOpponentData.getClaimantData().getSsn());
								searchbean.setSsn(appealPartyOpponentData.getClaimantData().getSsn());
							}else if (appealPartyOpponentData.getEmployerData()!=null){
								newAppealPartyForOpponentData.setEmployerData(appealPartyOpponentData.getEmployerData());
								objAssembly.setEan(appealPartyOpponentData.getEmployerData().getEan());
								searchbean.setEan(appealPartyOpponentData.getEmployerData().getEan());
							}
							appealPartySet.add(newAppealPartyForOpponentData);
							appealData.setAppealPartySet(appealPartySet);
						}
					}else {// if opponent is MDES
						AppealPartyData newAppealPartyDataForMDES = new AppealPartyData();
						newAppealPartyDataForMDES.setPartyName(AppellantOrOpponentEnum.MDES.getName());
						newAppealPartyDataForMDES.setPartyType(GlobalConstants.MDES_PARTY_TYPE);
						newAppealPartyDataForMDES.setAppealData(appealData);
						appealPartySet.add(newAppealPartyDataForMDES);
						appealData.setAppealPartySet(appealPartySet);
					}
					
				}else {// if it is tax intercept appeal
					//if (AppellantOrOpponentEnum.Claimant.getName().equals(currentForm.getAppeallant())){
						//if (AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(previousAppealData.getAppellant())){
					appellantInfoList = AppealUtil.getAppllantInformation(previousAppealData);
					//	}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(previousAppealData.getAppellant())){
						//	appellantInfoList = AppealUtil.getAppllantInformation(previousAppealData);
					//	}
					//}else if (AppellantOrOpponentEnum.SPOUSE.getName().equals(currentForm.getAppeallant())){
					///	if (AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(previousAppealData.getAppellant())){
					//		appellantInfoList = AppealUtil.getAppllantInformation(previousAppealData);
					//	}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(previousAppealData.getAppellant())){
						//	appellantInfoList = AppealUtil.getAppllantInformation(previousAppealData);
					//	}
					//}
					AppealPartyData appealPartyTaxInterceptData = (AppealPartyData)appellantInfoList.get(0);
					AppealPartyData newAppealPartyDataForTaxIntercept = new AppealPartyData();
					newAppealPartyDataForTaxIntercept.setAppealData(appealData);
					newAppealPartyDataForTaxIntercept.setPartyName(appealPartyTaxInterceptData.getPartyName());
					newAppealPartyDataForTaxIntercept.setPartyOf(appealPartyTaxInterceptData.getPartyOf());
					newAppealPartyDataForTaxIntercept.setPartyType(appealPartyTaxInterceptData.getPartyType());
					newAppealPartyDataForTaxIntercept.setSsn(appealPartyTaxInterceptData.getSsn());
					newAppealPartyDataForTaxIntercept.setMailingAddress(appealPartyTaxInterceptData.getMailingAddress());
					appealPartySet.add(newAppealPartyDataForTaxIntercept);
					/*In case of Tax Intercept appeal MDES will be the only opponent*/
					AppealPartyData newAppealPartyDataForTaxInterceptMDES = new AppealPartyData();
					newAppealPartyDataForTaxInterceptMDES.setPartyName(AppellantOrOpponentEnum.MDES.getName());
					newAppealPartyDataForTaxInterceptMDES.setPartyType(GlobalConstants.MDES_PARTY_TYPE);
					newAppealPartyDataForTaxInterceptMDES.setAppealData(appealData);
					appealData.setAppealPartySet(appealPartySet);
					objAssembly.setSsn(appealPartyTaxInterceptData.getSsn());
					searchbean.setFullName(appealPartyTaxInterceptData.getPartyName());
				}
				objAssembly.addComponent(appealData,true);

		return objAssembly;
	}


}