package gov.state.uim.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.DynaBean;

import gov.state.uim.GlobalConstants;
import gov.state.uim.TransientSessionConstants;
import gov.state.uim.ViewConstants;
import gov.state.uim.WorkflowProcessTemplateConstants;
import gov.state.uim.app.AppealConstants;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.dao.IAppealDAO;
import gov.state.uim.dao.factory.DAOFactory;
import gov.state.uim.domain.Appeal;
import gov.state.uim.domain.AppealDecisionLetterBO;
import gov.state.uim.domain.AppealScheduleBO;
import gov.state.uim.domain.BORMeeting;
import gov.state.uim.domain.BORMeetingAppealMap;
import gov.state.uim.domain.BoardOfReview;
import gov.state.uim.domain.BorRecommendation;
import gov.state.uim.domain.Claimant;
import gov.state.uim.domain.Correspondence;
import gov.state.uim.domain.Decision;
import gov.state.uim.domain.DocumentImageIndexBO;
import gov.state.uim.domain.EmployerContact;
import gov.state.uim.domain.FSUser;
import gov.state.uim.domain.FederalEmployer;
import gov.state.uim.domain.Issue;
import gov.state.uim.domain.LateAppeal;
import gov.state.uim.domain.LateDeniedAppeal;
import gov.state.uim.domain.MiscAppeal;
import gov.state.uim.domain.NonApperanceAppeal;
import gov.state.uim.domain.NonMonAppeal;
import gov.state.uim.domain.TaxAppeal;
import gov.state.uim.domain.TaxInterceptAppeal;
import gov.state.uim.domain.bean.ALJDecisionDisplayBean;
import gov.state.uim.domain.bean.AddressBean;
import gov.state.uim.domain.bean.AdjudicatorDecisionDisplayBean;
import gov.state.uim.domain.bean.AppealCorrespondenceBean;
import gov.state.uim.domain.bean.AppealDecisionCompareBean;
import gov.state.uim.domain.bean.AppealExhibitInfoBean;
import gov.state.uim.domain.bean.AppealInfoBean;
import gov.state.uim.domain.bean.BORDecisionBean;
import gov.state.uim.domain.bean.BorAppealsToScheduleBean;
import gov.state.uim.domain.bean.MasterDocketCountBean;
import gov.state.uim.domain.bean.MemberDocketBean;
import gov.state.uim.domain.bean.MessageHolderBean;
import gov.state.uim.domain.bean.RemandForInfoBean;
import gov.state.uim.domain.bean.RemandForNewDecisionBean;
import gov.state.uim.domain.bean.ReverseDecisionBean;
import gov.state.uim.domain.bean.SsnBean;
import gov.state.uim.domain.bean.UploadDocumentIntoDmsBean;
import gov.state.uim.domain.data.AddressComponentData;
import gov.state.uim.domain.data.AppealData;
import gov.state.uim.domain.data.AppealDecisionDetailData;
import gov.state.uim.domain.data.AppealDecisionLetterData;
import gov.state.uim.domain.data.AppealExhibitData;
import gov.state.uim.domain.data.AppealPartyData;
import gov.state.uim.domain.data.AppealRemandDetailData;
import gov.state.uim.domain.data.BORMasterDocketData;
import gov.state.uim.domain.data.BORMeetingAppealMapData;
import gov.state.uim.domain.data.BORMeetingData;
import gov.state.uim.domain.data.BORParticipantData;
import gov.state.uim.domain.data.BORRecommendationData;
import gov.state.uim.domain.data.ClaimantData;
import gov.state.uim.domain.data.ClaimantDataBridge;
import gov.state.uim.domain.data.ClaimantDecisionLetterData;
import gov.state.uim.domain.data.CorrespondenceData;
import gov.state.uim.domain.data.DecisionData;
import gov.state.uim.domain.data.DocumentImageIndexData;
import gov.state.uim.domain.data.EmployerContactData;
import gov.state.uim.domain.data.EmployerData;
import gov.state.uim.domain.data.EmployerDecisionLetterData;
import gov.state.uim.domain.data.FSUserData;
import gov.state.uim.domain.data.IssueData;
import gov.state.uim.domain.data.LateAppealData;
import gov.state.uim.domain.data.LateDeniedAppealData;
import gov.state.uim.domain.data.MiscAppealData;
import gov.state.uim.domain.data.MstAppDecMaster;
import gov.state.uim.domain.data.MstIssueMaster;
import gov.state.uim.domain.data.NonAppearanceAppealData;
import gov.state.uim.domain.data.NonMonAppealData;
import gov.state.uim.domain.data.RecallDecisionDetailData;
import gov.state.uim.domain.data.RegisteredEmployerData;
import gov.state.uim.domain.data.TaxAppealData;
import gov.state.uim.domain.data.TaxInterceptAppealData;
import gov.state.uim.domain.data.TaxInterceptMasterData;
import gov.state.uim.domain.decision.appeals.AppealsDecisionLettersParagraphBuilder;
import gov.state.uim.domain.enums.AppealDecisionCodeEnum;
import gov.state.uim.domain.enums.AppealPartyTypeEnum;
import gov.state.uim.domain.enums.AppealProofReadingStatusEnum;
import gov.state.uim.domain.enums.AppealRemandedForEnum;
import gov.state.uim.domain.enums.AppealStatusEnum;
import gov.state.uim.domain.enums.AppealTypeEnum;
import gov.state.uim.domain.enums.AppellantOrOpponentEnum;
import gov.state.uim.domain.enums.BORAppealDecisionEnum;
import gov.state.uim.domain.enums.BorMeetingStatusEnum;
import gov.state.uim.domain.enums.CorrespondenceCodeEnum;
import gov.state.uim.domain.enums.CorrespondenceCodeIncomingEnum;
import gov.state.uim.domain.enums.CorrespondenceCodeTaxEnum;
import gov.state.uim.domain.enums.CorrespondenceDirectionEnum;
import gov.state.uim.domain.enums.CorrespondenceParameterDescriptionEnum;
import gov.state.uim.domain.enums.CorrespondenceParameterEnum;
import gov.state.uim.domain.enums.DecisionStatusEnum;
import gov.state.uim.domain.enums.EmployerTypeEnum;
import gov.state.uim.domain.enums.FunctionsEnum;
import gov.state.uim.domain.enums.InterpreterEnum;
import gov.state.uim.domain.enums.IssueCategoryEnum;
import gov.state.uim.domain.enums.IssueSubCategoryEnum;
import gov.state.uim.domain.enums.MasterDocketTypeEnum;
import gov.state.uim.domain.enums.RemandReasonEnum;
import gov.state.uim.domain.enums.TaxAppealIssueEnum;
import gov.state.uim.domain.enums.WorkFlowOperationsEnum;
import gov.state.uim.framework.cache.CacheUtility;
import gov.state.uim.framework.dao.hibernate.BaseHibernateDAO;
import gov.state.uim.framework.dao.jdbc.IBaseJdbcDAO;
import gov.state.uim.framework.dms.AppealDmsBean;
import gov.state.uim.framework.dms.BaseDmsDAO;
import gov.state.uim.framework.dms.ClaimantDmsBean;
import gov.state.uim.framework.dms.CorrespoandenceDmsBean;
import gov.state.uim.framework.dms.service.UploadDocumentService;
import gov.state.uim.framework.exception.BaseApplicationException;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.reqtrace.cif_wy;
import gov.state.uim.framework.service.BaseService;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.workflow.bean.BaseAccessDsContainerBean;
import gov.state.uim.framework.workflow.bean.GenericWorkflowSearchBean;
import gov.state.uim.framework.workflow.bean.GlobalDsContainerBean;
import gov.state.uim.framework.workflow.bean.WorkflowItemBean;
import gov.state.uim.framework.workflow.dao.IBaseWorkflowDAO;
import gov.state.uim.multistate.BaseOrStateEnum;
import gov.state.uim.multistate.MultiStateClassFactory;
import gov.state.uim.util.AppealUtil;
import gov.state.uim.util.lang.DateFormatUtility;
import gov.state.uim.util.lang.DateUtility;
import gov.state.uim.util.lang.StringUtility;
import gov.state.uim.workflow.transaction.WorkflowTransactionService;

	public class BORService extends BaseService implements BIBORService {
		
		private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(BORService.class);
		
		
		// this method fetches list of appeals based on roles (BOR,Tax appeal,Tax intercept BOR)
		@Override
		public IObjectAssembly getBoardMemberDocketByRole(IObjectAssembly objAssembly) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getBoardMemberDocketByRole");
		}
			List docketMemberList = new ArrayList();
			
			docketMemberList = Appeal.fetchAppealsByBORLevels("BOR");
			// TODO remove this once the roles are finalised
			
			if(Arrays.asList(super.getUserContext().getFunctions()).contains("BOR_FUNCTION")){
				docketMemberList = Appeal.fetchAppealsByBORLevels("BOR");
			}else if(Arrays.asList(super.getUserContext().getFunctions()).contains("BOR_FUNCTION") && Arrays.asList(super.getUserContext().getFunctions()).contains("TAX_APPEAL_BOR")){
				docketMemberList = Appeal.fetchAppealsByBORLevels("TABOR");
			}else if(Arrays.asList(super.getUserContext().getFunctions()).contains("BOR_FUNCTION") && Arrays.asList(super.getUserContext().getFunctions()).contains("TAX_INTERCEPT_BOR")){
				docketMemberList = Appeal.fetchAppealsByBORLevels("TIBOR");
			}
			objAssembly.addComponentList(docketMemberList);
			
			return objAssembly;
		}
	
//		 this method fetches appeal information at the BOR level
		@Override
		public IObjectAssembly getAppealInfoForBOR(IObjectAssembly objAssembly) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getAppealInfoForBOR");
		}
			MemberDocketBean memberDocketBean = (MemberDocketBean)objAssembly.getFirstBean(MemberDocketBean.class);
			Appeal appealBO = null;
			//Cliamt address
			AddressBean employerAddress = new AddressBean();
			/*
			 * If the appeal does not have any recommendation yet the bor recommendation id will come as 0
			 * if it is 0 then fire the query without bor recommendation
			 * else
			 * fire the query that fetch appeal data along with bor recommendation data
			 * */
			if (memberDocketBean.getBorRecommendationId()==null ||
					GlobalConstants.DB_ANSWER_NO.equals(memberDocketBean.getBorRecommendationId())){
				//bor recommendation id is 0
				if (AppealTypeEnum.TAX_INT.getName().equals(memberDocketBean.getAppealType())){
					appealBO = Appeal.getTaxInterceptAppealDataWithAppealExhibitSetByAppealId(memberDocketBean.getAppealId());
				}else {
					appealBO = Appeal.getAppealDataWithAppealExhibitSetByAppealId(memberDocketBean.getAppealId());
				}
								
			}else {
                if (AppealTypeEnum.TAX_INT.getName().equals(memberDocketBean.getAppealType())){
                	appealBO = Appeal.getTaxInterceptAppealDataWithAppealExhibitSetByAppealIdAndBorData(memberDocketBean.getAppealId(),Long.valueOf(memberDocketBean.getBorRecommendationId()));
				}else {
					appealBO = Appeal.getAppealDataWithAppealExhibitSetByAppealIdAndBorData(memberDocketBean.getAppealId(),Long.valueOf(memberDocketBean.getBorRecommendationId()));
				}
				
			}
			 // fetching the Userid for current user logged in
			String userId = super.getUserContext().getUserId();
			FSUserData fsuserData = FSUser.getFSUserByUserId(userId);
			// adding user details
			objAssembly.addComponent(fsuserData,true);
			
			//for  diaplaying  Appeal info
			AppealInfoBean appealInfoBean = new AppealInfoBean();
			//AppealInfoBean appealInfoBean = (AppealInfoBean)objAssembly.fetchORCreateBean(AppealInfoBean.class);
			//chk for instances of appeal data
			//if (appealBO.getAppealData() instanceof NonMonAppealData)
			
			Map issueMap=Appeal.getIssueDescriptionAndDecisionDateFromAppealData(appealBO.getAppealData());
			if(issueMap!=null && issueMap.containsKey(AppealConstants.ISSUE_DESCRIPTION)){
				appealInfoBean.setIssueDescriptionDetailsString(String.valueOf(issueMap.get(AppealConstants.ISSUE_DESCRIPTION)));
			}else{
				appealInfoBean.setIssueDescriptionDetailsString(ViewConstants.NOT_APPLICABLE);
			}
			
			if(issueMap!=null && issueMap.containsKey(AppealConstants.ISSUE_CATEGORY)){
				appealInfoBean.setIssueDescriptionString(String.valueOf(issueMap.get(AppealConstants.ISSUE_CATEGORY)));
			}else{
				appealInfoBean.setIssueDescriptionString(ViewConstants.NOT_APPLICABLE);
			}
			if(issueMap!=null && issueMap.containsKey(AppealConstants.ISSUE_SUB_CATEGORY)){
				appealInfoBean.setIssueDetailsString(String.valueOf(issueMap.get(AppealConstants.ISSUE_SUB_CATEGORY)));
			}else{
				appealInfoBean.setIssueDetailsString(ViewConstants.NOT_APPLICABLE);
			}

			Map claimantEmployerInfoMap = AppealUtil.getClaimantAndEmployerInformationByAppealId(appealBO.getAppealData());
			appealInfoBean.setHeaderClaimantName((String)claimantEmployerInfoMap.get(AppealConstants.KEY_CLAIMANT_NAME));
			appealInfoBean.setHeaderClaimantSSN((String)claimantEmployerInfoMap.get(AppealConstants.KEY_CLAIMANT_SSN));
			appealInfoBean.setHeaderEAN((String)claimantEmployerInfoMap.get(AppealConstants.KEY_MDES_EAN));
			appealInfoBean.setHeaderEmployerName((String)claimantEmployerInfoMap.get(AppealConstants.KEY_EMPLOYER_NAME));
			appealInfoBean.setHeaderSpouseName((String)claimantEmployerInfoMap.get(AppealConstants.KEY_SPOUSE_NAME));
			appealInfoBean.setHeaderSpouseSSN((String)claimantEmployerInfoMap.get(AppealConstants.KEY_SPOUSE_SSN));
			
			if (appealBO instanceof NonMonAppeal){
				
				//NonMonAppealData nonMonAppealData = new NonMonAppealData();
				
				NonMonAppeal nonMonAppealBO = (NonMonAppeal)appealBO;
				NonMonAppealData nonMonAppealData = (NonMonAppealData)nonMonAppealBO.getAppealData();
				// get the Nonmon decision ID
				DecisionData nondecisiondata = nonMonAppealData.getAppealledAgainstDecision();
				// get the decision object for the appeal
				//Decision decision = Decision.getDecisionAndMstIssueMasterDataByDecisionId(nonMonDecisionId);
				// Climant data object
				//ClaimantData claimantData = nondecisiondata.getIssueData().getClaimData().getClaimantData();
				// Employer Data
				//EmployerData employerData = nondecisiondata.getIssueData().getFactFindingData().getEmployerData();
				// setting appeal info
				if(nondecisiondata!=null &&nondecisiondata.getIssueData()!=null && nondecisiondata.getIssueData().getIssueId()!=null)
				{
					appealInfoBean.setIssueId(nondecisiondata.getIssueData().getIssueId().toString());
				}
				appealInfoBean.setAppealReason(nonMonAppealData.getAppealReason());
				appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",nonMonAppealData.getAppellant(),"description"));
				/*CIF_01929 || Added validation for empty String */
				if(nonMonAppealData.getInterpreter() != null && !nonMonAppealData.getInterpreter().trim().equalsIgnoreCase("")){
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(nonMonAppealData.getInterpreter()).getDescription());
					// TODO add field according to input
				}else{
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				appealInfoBean.setDocketNumber(nonMonAppealData.getDocketNumber().toString());
				if(nondecisiondata!=null && nondecisiondata.getIssueData()!=null
						&& nondecisiondata.getIssueData().getMstIssueMaster()!=null
						&& nondecisiondata.getIssueData().getMstIssueMaster().getIssueCategory()!=null)
				{
					appealInfoBean.setDecisionDescription((String)CacheUtility.getCachePropertyValue("ISSUE_CATEGORY_DISPLAY","key",nondecisiondata.getIssueData().getMstIssueMaster().getIssueCategory(),"description"));
				}
				// getting the appellant and opponent list,if claimant/employer is the appellant or opponent
				// accordingly that will be shown under appellant or opponent section
				List appellantInfoList = AppealUtil.getAppllantInformation(nonMonAppealData);
				//ClaimantData claimantData = null;
				AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
				if (appealPartyDataForAppellant.getClaimantData()!=null){
					ClaimantData claimantData = appealPartyDataForAppellant.getClaimantData();
                    // setting claimant Address
					appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
					appealInfoBean.setClaimantSsn(claimantData.getSsn());
					appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
					appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.CLAIMANT.getName());
				}else if (appealPartyDataForAppellant.getEmployerData()!=null){
					EmployerData employerData = appealPartyDataForAppellant.getEmployerData();
					appealInfoBean.setEmployerName(employerData.getEmployerName());
					appealInfoBean.setEmployerEan(employerData.getEan());
					// getting employer contact details from employer contact table
					EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
					if(employerContactData != null){
						this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
						
					}
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
				} 
				if(AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(appealBO.getAppealData().getAppellant()) && AppealPartyTypeEnum.APPELLANT.getName().equalsIgnoreCase(appealPartyDataForAppellant.getPartyType()) && appealPartyDataForAppellant.getEmployerData()==null){
					//This if condition is added to handle Outofstate and Unregistered employer details
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
					
					appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
					appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);
					appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
				}
				
				List opponentInfoList = AppealUtil.getOpponentInformation(nonMonAppealData);
				if(opponentInfoList != null && !opponentInfoList.isEmpty()) {
					AppealPartyData appealPartyDataForOpponent= (AppealPartyData)opponentInfoList.get(0);
					if (appealPartyDataForOpponent.getClaimantData()!=null){
						ClaimantData claimantData = appealPartyDataForOpponent.getClaimantData();
	                    // setting claimant Address
						appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
						appealInfoBean.setClaimantSsn(claimantData.getSsn());
						appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
						appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.CLAIMANT.getName());
					}else if (appealPartyDataForOpponent.getEmployerData()!=null){
						EmployerData employerData = appealPartyDataForOpponent.getEmployerData();
						appealInfoBean.setEmployerName(employerData.getEmployerName());
						appealInfoBean.setEmployerEan(employerData.getEan());
						// getting employer contact details from employer contact table
						EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
						if(employerContactData != null){
							this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
							
						}
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.EMPLOYER.getName());
					}
					if(AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(appealBO.getAppealData().getOpponent()) && AppealPartyTypeEnum.OPPONENT.getName().equalsIgnoreCase(appealPartyDataForAppellant.getPartyType()) && appealPartyDataForAppellant.getEmployerData()==null){
						//This if condition is added to handle Outofstate and Unregistered employer details
						appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
						
						appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
						appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);
						appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
					}
				}
				// fetching  the exhibit set
//				Set exhibitSet = nonMonAppealData.getAppealExhibitSet();
//				exhibitSet.size();
//				appealInfoBean.setExhibitSet(exhibitSet);
				objAssembly.addComponent(nonMonAppealData,true);
                //fetch the  bor recommendation set
				Set borRecSet = nonMonAppealData.getBorRecommendationSet();
				borRecSet.size();
				//fetch the appeal remand details set
				Set remandSet = nonMonAppealData.getAppealRemandDetailSet();
				remandSet.size();

				// adding nonMonetarydata object to object assembly
				if(nondecisiondata!=null && nondecisiondata.getIssueData()!=null
						&&nondecisiondata.getIssueData().getIssueId()!=null){
					objAssembly.setPrimaryKey(IssueData.class,nondecisiondata.getIssueData().getIssueId());
				}
				appealInfoBean.setAppealType(AppealTypeEnum.NON_MON.getName());
				
			//	TODO chk for tax intercept appeal
			}else if(appealBO instanceof TaxAppeal){
				// typecast with taxappeal data
				//TaxAppeal taxAppealBO = (TaxAppeal)appealBO;
				TaxAppealData taxAppealData =(TaxAppealData)appealBO.getAppealData();
				// set the docket number
				appealInfoBean.setDocketNumber(taxAppealData.getDocketNumber().toString());
				if(taxAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(taxAppealData.getInterpreter()).getDescription());
				}else{
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				// get the taxappeal issue type
				
				String issueCodeDesc = (String)CacheUtility.getCachePropertyValue("T_MST_TAX_APPEAL_ISSUE","key",taxAppealData.getTaxAppealIssueCode(),"description");
				appealInfoBean.setDecisionDescription(issueCodeDesc);
				// get the Appeallant
				appealInfoBean.setAppellant(taxAppealData.getAppellant());
				//appealInfoBean.setAppellantValue(taxAppealData.getAppellant());
				List appellantInfoList = AppealUtil.getAppllantInformation(appealBO.getAppealData());
				//ClaimantData claimantData = null;
				AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
									
				if(AppealPartyTypeEnum.APPELLANT.getName().equals(appealPartyDataForAppellant.getPartyType())){
					EmployerData employerData = (EmployerData)appealPartyDataForAppellant.getEmployerData();
					//	setting Employer info
					if(employerData != null){
						appealInfoBean.setEmployerName(((RegisteredEmployerData)employerData).getEmployerAccountData().getEntityName());
						appealInfoBean.setEmployerEan(((RegisteredEmployerData)employerData).getEmployerAccountData().getEan());
						// getting employer contact details from employer contact table
						EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
						if(employerContactData != null){
							this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
							appealInfoBean.setEmployerAddressBean(employerAddress);
						}//end if of employerContactData
					}//end if of employer data
				}//end  if of appealPartyData.getPartyType()
				
				//appealInfoBean.setDecisionDescription(taxAppealData.getTaxAppealIssueCode());
				// set reason for appeal
				appealInfoBean.setAppealReason(taxAppealData.getAppealReason());
				//	fetching  the exhibit set
//				Set exhibitSet = taxAppealData.getAppealExhibitSet();
//				if(exhibitSet != null){
//					exhibitSet.size();
//					appealInfoBean.setExhibitSet(exhibitSet);
//				}
				// fetch the  bor recommendation set
				Set borRecSet = taxAppealData.getBorRecommendationSet();
				borRecSet.size();
				//fetch the appeal remand details set
				Set remandSet = taxAppealData.getAppealRemandDetailSet();
				remandSet.size();

				appealInfoBean.setAppealType(AppealTypeEnum.TAXA.getName());
				// set tax appeal data in the object assembly
				objAssembly.addComponent(taxAppealData,true);
			}else if(appealBO.getAppealData() instanceof LateAppealData){
				//	typecast with lateappeal data
				LateAppealData lateAppealData =(LateAppealData)appealBO.getAppealData();
				appealInfoBean.setParentAppealType(AppealTypeEnum.getEnum(lateAppealData.getParentAppealData().getAppealType()).getDescription());
				if (AppealTypeEnum.NON_MON.getName().equals(lateAppealData.getParentAppealData().getAppealType())){
					Decision decision = Decision.getDecisionByPkid(lateAppealData.getParentAppealData().getAppealledAgainstDecision().getDecisionID());
					String issueCategory = decision.getDecisionData().getIssueData().getMstIssueMaster().getIssueCategory();
					String issueSubCategory = decision.getDecisionData().getIssueData().getMstIssueMaster().getIssueSubCategory();
					appealInfoBean.setParentAppealType(AppealTypeEnum.NON_MON.getDescription());
					String issueCategoryDescription = IssueCategoryEnum.getEnum(issueCategory).getDescription();
					String issueSubCategoryDescription = IssueSubCategoryEnum.getEnum(issueSubCategory).getDescription();
					appealInfoBean.setParentAppealIssueDescription(issueCategoryDescription + "-" + issueSubCategoryDescription);

				}
				appealInfoBean.setDocketNumber(lateAppealData.getDocketNumber().toString());
				appealInfoBean.setDecisionDescription(AppealConstants.TIMELINESS);
				if(lateAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(lateAppealData.getInterpreter()).getDescription());
				}else{
					appealInfoBean.setInterpreter(InterpreterEnum.NOIP.getDescription());
				}
				// get the Appeallant
				appealInfoBean.setAppellantValue(lateAppealData.getAppellant());
				appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",lateAppealData.getAppellant(),"description"));
				appealInfoBean.setAppealReason(lateAppealData.getAppealReason());
				//	get the employer details
				//Set lateAppealPartySet = lateAppealData.getAppealPartySet();
				List appellantList = AppealUtil.getAppllantInformation(lateAppealData);
				if(appellantList != null && appellantList.size()>0){
					//for (Iterator it = lateAppealPartySet.iterator(); it.hasNext(); ) {
						// get the appealparty data
				AppealPartyData appealPartyData = (AppealPartyData) appellantList.get(0);
				//(AppealPartyData)it.next();
						//if(AppealPartyTypeEnum.APPELLANT.getName().equals(appealPartyData.getPartyType())){
				EmployerData employerData = (EmployerData)appealPartyData.getEmployerData();
				AppealData newAppealData = Appeal.findByAppealId(lateAppealData.getParentAppealData().getAppealId());
				//	setting Employer info
				if(TaxAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if(employerData != null){
						appealInfoBean.setEmployerName(employerData.getEmployerName());
						appealInfoBean.setEmployerEan(employerData.getEan());
						// getting employer contact details from employer contact table
						EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
						this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
						if(employerContactData != null){
							
							appealInfoBean.setAppealType(AppealTypeEnum.TAXA.getName());
						}//end if of employerContactData
					}//end if of employer data
				}
				if(NonMonAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(lateAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
					if (AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(lateAppealData.getAppellant())){
						EmployerData nonMonEmployerData = (EmployerData)appealPartyData.getEmployerData();
						if(nonMonEmployerData != null){
							//	getting employer contact details from employer contact table
							EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
							if (employerContactData != null) {
								this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
							}else {
								List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
								AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
								appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
								appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
								appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);
								appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
							}

						}
					}
					appealInfoBean.setAppealType(AppealTypeEnum.NON_MON.getName());
				}
				if( TaxInterceptAppealData.class.isAssignableFrom(newAppealData.getClass())){
					/* CIF_01352 start
					 * commented Both as party is not there in missouri
					 * if (AppellantOrOpponentEnum.BOTH.getName().equalsIgnoreCase(lateAppealData.getAppellant())){
						AppealPartyData appealParty1DataForAppellant = (AppealPartyData)appellantList.get(0);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setClaimantDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
							
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
									
						}
					
						AppealPartyData appealParty2DataForAppellant = (AppealPartyData)appellantList.get(1);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty2DataForAppellant.getPartyType())){
			                
							this.setClaimantDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty2DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
											
						}
					}else*/ if (AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(lateAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setClaimantDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(lateAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setSpouseDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}
					
					appealInfoBean.setAppealType(AppealTypeEnum.LATE.getName());
				}
				if (MiscAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(lateAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
				}

							
						//}//end  if of appealPartyData.getPartyType()
					//}// end for looop
				appealInfoBean.setAppealReason(lateAppealData.getAppealReason());
					
				appealInfoBean.setParentAppealId(lateAppealData.getParentAppealData().getAppealId().toString());
					//	set late appeal data in the object assembly
				objAssembly.addComponent(lateAppealData,true);
			}// end if lateAppealPartySet
			}else if (appealBO instanceof TaxInterceptAppeal){
	            
				TaxInterceptAppeal taxInterceptAppealBO =(TaxInterceptAppeal) appealBO;
				
				TaxInterceptAppealData taxInterceptAppealData =taxInterceptAppealBO.getTaxInterceptAppealData();
				//List list = TaxInterceptAppeal.getTaxInterceptMasterDetailsByPkId(taxInterceptAppealData.getTaxInterceptMasterData().getTaxInterceptMasterId());
				//TaxInterceptMasterData taxInterceptMasterData = (TaxInterceptMasterData)list.get(0);
				// set the docket number
				appealInfoBean.setDocketNumber(taxInterceptAppealData.getDocketNumber().toString());
				if(taxInterceptAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(taxInterceptAppealData.getInterpreter()).getDescription());
				}else{
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				String appellant =(String)CacheUtility.getCachePropertyValue("T_MST_APPELLANT","key",taxInterceptAppealData.getAppellant(),"description");
				appealInfoBean.setAppellant(appellant);
				appealInfoBean.setAppellantValue(taxInterceptAppealData.getAppellant());
				/* CIF_01352 start
				 * commented Both as party is not there in missouri
				 * if (AppellantOrOpponentEnum.BOTH.getName().equals(taxInterceptAppealData.getAppellant())){
					List appellantInfoList = AppealUtil.getAppllantInformation(taxInterceptAppealData);
					//ClaimantData claimantData = null;
					AppealPartyData appealParty1DataForAppellant = (AppealPartyData)appellantInfoList.get(0);
					if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty1DataForAppellant.getPartyType())){
						this.setClaimantDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
						
					}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty1DataForAppellant.getPartyType())){
						this.setSpouseDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
								
					}
				
					AppealPartyData appealParty2DataForAppellant = (AppealPartyData)appellantInfoList.get(1);
					if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty2DataForAppellant.getPartyType())){
		                
						this.setClaimantDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
					}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty2DataForAppellant.getPartyType())){
						this.setSpouseDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
										
					}
				}else*/ if (AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(taxInterceptAppealData.getAppellant())){
					
					List appellantInfoList = AppealUtil.getAppllantInformation(taxInterceptAppealData);
					AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
					this.setClaimantDetailsForTaxIntercept(appealPartyDataForAppellant,appealInfoBean);
				}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(taxInterceptAppealData.getAppellant())){
					
					List appellantInfoList = AppealUtil.getAppllantInformation(taxInterceptAppealData);
					AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
					this.setSpouseDetailsForTaxIntercept(appealPartyDataForAppellant,appealInfoBean);
				}
				// set reason for appeal
				appealInfoBean.setAppealReason(taxInterceptAppealData.getAppealReason());
				//	fetching  the exhibit set
//				Set exhibitSet = taxInterceptAppealData.getAppealExhibitSet();
//				if(exhibitSet != null){
//					exhibitSet.size();
//					appealInfoBean.setExhibitSet(exhibitSet);
//				}
				// fetch the  bor recommendation set
				//Set borRecSet = taxInterceptAppealData.getBorRecommendationSet();
				//borRecSet.size();
				appealInfoBean.setAppealType(AppealTypeEnum.TAX_INT.getName());
				// set tax appeal data in the object assembly
				objAssembly.addComponent(taxInterceptAppealData,true);
				objAssembly.addComponent(taxInterceptAppealData.getTaxInterceptMasterData(),true);
			}else if (appealBO.getAppealData() instanceof MiscAppealData){
				  
				MiscAppeal miscAppealBo = (MiscAppeal)appealBO;
				MiscAppealData miscAppealData = (MiscAppealData)miscAppealBo.getAppealData();
				// get the Nonmon decision ID
				appealInfoBean.setAppealReason(miscAppealData.getAppealReason());
				//appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",miscAppealData.getAppellant(),"description"));
				appealInfoBean.setAppellant(miscAppealData.getAppellant());
				appealInfoBean.setAppellantValue(miscAppealData.getAppellant());
				if(miscAppealData.getInterpreter() != null) {
					//appealInfoBean.setInterpreter(InterpreterEnum.getEnum(nonMonAppealData.getInterpreter()).getDescription());
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(miscAppealData.getInterpreter()).getDescription());
				}else {
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}if(miscAppealData.getSsn() != null){
					SsnBean objSssBean = new SsnBean(miscAppealData.getSsn());
					appealInfoBean.setMiscSSN(objSssBean.getSsnWithDelimiter());
				}
				
				appealInfoBean.setDocketNumber(miscAppealData.getDocketNumber().toString());
				//appealInfoBean.setDecisionDescription((String)CacheUtility.getCachePropertyValue("ISSUE_CATEGORY_DISPLAY","key",decisiondata.getIssueData().getMstIssueMaster().getIssueCategory(),"description"));
				appealInfoBean.setDecisionDescription(GlobalConstants.NA);
				// for misc appeal data issue desc will be NA
				List appellantInfoList = AppealUtil.getAppllantInformation(miscAppealData);
				//ClaimantData claimantData = null;
				AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
				if (appealPartyDataForAppellant.getSsn()!=null) {
					//ClaimantData claimantData = appealPartyDataForAppellant.getClaimantData();
                    // setting claimant Address
					appealInfoBean.setClaimantName(appealPartyDataForAppellant.getPartyName());
					appealInfoBean.setClaimantSsn(appealPartyDataForAppellant.getSsn());
					if (appealPartyDataForAppellant.getPhoneNumber()!=null){
						appealInfoBean.setClaimantTelephone(appealPartyDataForAppellant.getPhoneNumber());
					}
					appealInfoBean.setClaimantAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.CLAIMANT.getName());
				}else if (appealPartyDataForAppellant.getEmployerData()!=null) {
					EmployerData employerData = appealPartyDataForAppellant.getEmployerData();
					appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
					
					// getting employer contact details from employer contact table
					if (appealPartyDataForAppellant.getEmployerData()!=null){
						appealInfoBean.setEmployerEan(appealPartyDataForAppellant.getEmployerData().getEan());
						EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
						if(employerContactData != null) {
							this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
						}
					}else{
						appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
						appealInfoBean.setEmployerEan(GlobalConstants.NA);
					}
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
				}else{
					// claimant id is there in appeal party table
					ClaimantData claimantData =(ClaimantData)appealPartyDataForAppellant.getClaimantData();
					if(claimantData !=null ){
						appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
						appealInfoBean.setClaimantSsn(claimantData.getSsn());
						if(claimantData.getClaimantProfileData()!=null){
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
						}
						// setting claimant Address
						appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
					}
				}
				List opponentInfoList = AppealUtil.getOpponentInformation(miscAppealData);
				if(opponentInfoList != null && !opponentInfoList.isEmpty()) {
					AppealPartyData appealPartyDataForOpponent= (AppealPartyData)opponentInfoList.get(0);
					if (appealPartyDataForOpponent.getSsn()!=null) {
						//ClaimantData claimantData = appealPartyDataForAppellant.getClaimantData();
	                    // setting claimant Address
						appealInfoBean.setClaimantName(appealPartyDataForOpponent.getPartyName());
						appealInfoBean.setClaimantSsn(appealPartyDataForOpponent.getSsn());
						if (appealPartyDataForOpponent.getPhoneNumber()!=null){
							appealInfoBean.setClaimantTelephone(appealPartyDataForOpponent.getPhoneNumber());
						}
						
						appealInfoBean.setClaimantAddressBean(appealPartyDataForOpponent.getMailingAddress().getAddressBean());
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.CLAIMANT.getName());
					}else {
						EmployerData employerData = appealPartyDataForOpponent.getEmployerData();
						appealInfoBean.setEmployerName(appealPartyDataForOpponent.getPartyName());
						if (employerData!=null){
							appealInfoBean.setEmployerEan(employerData.getEan());
                            // getting employer contact details from employer contact table
							EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
							if(employerContactData != null) {
								this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
							}
						}else {
							appealInfoBean.setEmployerAddressBean(appealPartyDataForOpponent.getMailingAddress().getAddressBean());
							appealInfoBean.setEmployerEan(GlobalConstants.NA);
						}
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.EMPLOYER.getName());
					}
				}
				// fetching  the exhibit set
//				Set exhibitSet = miscAppealData.getAppealExhibitSet();
//				exhibitSet.size();
//				appealInfoBean.setExhibitSet(exhibitSet);
				objAssembly.addComponent(miscAppealData,true);
				// fetch the  bor recommendation set
				Set borRecSet = miscAppealData.getBorRecommendationSet();
				borRecSet.size();
				// fetch the appeal remand details set
				Set remandSet = miscAppealData.getAppealRemandDetailSet();
				remandSet.size();
				// adding nonMonetarydata object to object assembly
				//objAssembly.setPrimaryKey(IssueData.class,decisiondata.getIssueData().getIssueId());
				appealInfoBean.setAppealType(AppealTypeEnum.MISC.getName());
			}else if (appealBO.getAppealData() instanceof LateDeniedAppealData) {

                LateDeniedAppealData lateDeniedAppealData =(LateDeniedAppealData)appealBO.getAppealData();
				appealInfoBean.setDocketNumber(lateDeniedAppealData.getDocketNumber().toString());
				appealInfoBean.setDecisionDescription(AppealConstants.TIMELINESS);
				if(lateDeniedAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(lateDeniedAppealData.getInterpreter()).getDescription());
				}else{
					appealInfoBean.setInterpreter(InterpreterEnum.NOIP.getDescription());
				}
				// get the Appeallant
				appealInfoBean.setAppellantValue(lateDeniedAppealData.getAppellant());
				//appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",lateDeniedAppealData.getAppellant(),"description"));
				appealInfoBean.setAppellant(AppellantOrOpponentEnum.getEnum(lateDeniedAppealData.getAppellant()).getDescription());
				appealInfoBean.setAppealReason(lateDeniedAppealData.getAppealReason());
				appealInfoBean.setAppealType(AppealTypeEnum.getEnum(lateDeniedAppealData.getAppealType()).getDescription());
				//	get the employer details
				//Set lateAppealPartySet = lateAppealData.getAppealPartySet();
				List appellantList = AppealUtil.getAppllantInformation(lateDeniedAppealData);
				if(appellantList != null && appellantList.size()>0){
					//for (Iterator it = lateAppealPartySet.iterator(); it.hasNext(); ) {
						// get the appealparty data
				AppealPartyData appealPartyData = (AppealPartyData) appellantList.get(0);
				//(AppealPartyData)it.next();
						//if(AppealPartyTypeEnum.APPELLANT.getName().equals(appealPartyData.getPartyType())){
				EmployerData employerData = (EmployerData)appealPartyData.getEmployerData();
				AppealData newAppealData = Appeal.findByAppealId(lateDeniedAppealData.getParentAppealData().getAppealId());
				//	setting Employer info
				if(TaxAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if(employerData != null){
						appealInfoBean.setEmployerName(employerData.getEmployerName());
						appealInfoBean.setEmployerEan(employerData.getEan());
						// getting employer contact details from employer contact table
						EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
						this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
						if(employerContactData != null){
							
							appealInfoBean.setAppealType(AppealTypeEnum.TAXA.getName());
						}//end if of employerContactData
					}//end if of employer data
				}
				if(NonMonAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(lateDeniedAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
					if (AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(lateDeniedAppealData.getAppellant())){
						EmployerData nonMonEmployerData = (EmployerData)appealPartyData.getEmployerData();
						if(nonMonEmployerData != null){
							//	getting employer contact details from employer contact table
							appealInfoBean.setEmployerEan(nonMonEmployerData.getEan());
							appealInfoBean.setEmployerName(nonMonEmployerData.getEmployerName());
							EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
							this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
						}
					}
					//appealInfoBean.setAppealType(AppealTypeEnum.NON_MON.getName());
				}
				if( TaxInterceptAppealData.class.isAssignableFrom(newAppealData.getClass())){
					/* CIF_01352 start
					 * commented Both as party is not there in missouri
					 * if (AppellantOrOpponentEnum.BOTH.getName().equalsIgnoreCase(lateDeniedAppealData.getAppellant())){
						AppealPartyData appealParty1DataForAppellant = (AppealPartyData)appellantList.get(0);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setClaimantDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
							
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
									
						}
					
						AppealPartyData appealParty2DataForAppellant = (AppealPartyData)appellantList.get(1);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty2DataForAppellant.getPartyType())){
			                
							this.setClaimantDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty2DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
											
						}
					}else*/ if (AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(lateDeniedAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setClaimantDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(lateDeniedAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setSpouseDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}
					
					//appealInfoBean.setAppealType(AppealTypeEnum.LATE.getName());
				}
				if (MiscAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(lateDeniedAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
				}

				   appealInfoBean.setAppealReason(lateDeniedAppealData.getAppealReason());
				   appealInfoBean.setParentAppealId(lateDeniedAppealData.getParentAppealData().getAppealId().toString());
				   objAssembly.addComponent(lateDeniedAppealData,true);
			    }
			}else if (appealBO.getAppealData() instanceof NonAppearanceAppealData) {

				NonAppearanceAppealData nonAppearanceAppealData =(NonAppearanceAppealData)appealBO.getAppealData();
				appealInfoBean.setDocketNumber(nonAppearanceAppealData.getDocketNumber().toString());
				appealInfoBean.setDecisionDescription(AppealConstants.NO_APPERANCE);
				if(nonAppearanceAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(nonAppearanceAppealData.getInterpreter()).getDescription());
				}else{
					appealInfoBean.setInterpreter(InterpreterEnum.NOIP.getDescription());
				}
				// get the Appeallant
				appealInfoBean.setAppellantValue(nonAppearanceAppealData.getAppellant());
				appealInfoBean.setAppellant(AppellantOrOpponentEnum.getEnum(nonAppearanceAppealData.getAppellant()).getDescription());
				appealInfoBean.setAppealReason(nonAppearanceAppealData.getAppealReason());
				appealInfoBean.setAppealType(AppealTypeEnum.getEnum(nonAppearanceAppealData.getAppealType()).getDescription());
				//	get the employer details
				//Set lateAppealPartySet = lateAppealData.getAppealPartySet();
				List appellantList = AppealUtil.getAppllantInformation(nonAppearanceAppealData);
				if(appellantList != null && appellantList.size()>0){
					//for (Iterator it = lateAppealPartySet.iterator(); it.hasNext(); ) {
						// get the appealparty data
				AppealPartyData appealPartyData = (AppealPartyData) appellantList.get(0);
				//(AppealPartyData)it.next();
						//if(AppealPartyTypeEnum.APPELLANT.getName().equals(appealPartyData.getPartyType())){
				EmployerData employerData = (EmployerData)appealPartyData.getEmployerData();
				AppealData newAppealData = Appeal.findByAppealId(nonAppearanceAppealData.getParentAppealData().getAppealId());
				//	setting Employer info
				if(TaxAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if(employerData != null){
						appealInfoBean.setEmployerName(employerData.getEmployerName());
						appealInfoBean.setEmployerEan(employerData.getEan());
						// getting employer contact details from employer contact table
						EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
						this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
						if(employerContactData != null){
							
							appealInfoBean.setAppealType(AppealTypeEnum.TAXA.getName());
						}//end if of employerContactData
					}//end if of employer data
				}
				if(NonMonAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(nonAppearanceAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
					if (AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(nonAppearanceAppealData.getAppellant())){
						EmployerData nonMonEmployerData = (EmployerData)appealPartyData.getEmployerData();
						if(nonMonEmployerData != null){
							//	getting employer contact details from employer contact table
							EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
							this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
						}
					}
					//appealInfoBean.setAppealType(AppealTypeEnum.NON_MON.getName());
				}
				if( TaxInterceptAppealData.class.isAssignableFrom(newAppealData.getClass())){
					/* CIF_01352 start
					 * commented Both as party is not there in missouri
					 * 
					 * if (AppellantOrOpponentEnum.BOTH.getName().equalsIgnoreCase(nonAppearanceAppealData.getAppellant())){
						AppealPartyData appealParty1DataForAppellant = (AppealPartyData)appellantList.get(0);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setClaimantDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
							
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
									
						}
					
						AppealPartyData appealParty2DataForAppellant = (AppealPartyData)appellantList.get(1);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty2DataForAppellant.getPartyType())){
			                
							this.setClaimantDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty2DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
											
						}
					}else*/ if (AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(nonAppearanceAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setClaimantDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(nonAppearanceAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setSpouseDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}
					
					//appealInfoBean.setAppealType(AppealTypeEnum.LATE.getName());
				}
				if(MiscAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(nonAppearanceAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
				}
				appealInfoBean.setAppealReason(nonAppearanceAppealData.getAppealReason());
				appealInfoBean.setParentAppealId(nonAppearanceAppealData.getParentAppealData().getAppealId().toString());
				objAssembly.addComponent(nonAppearanceAppealData,true);
			    }
			
			}
			//fetching  the exhibit set
			//Set exhibitSet = Appeal.getExhibitListByDocketNumber(appealBO.getAppealData().getDocketNumber());
			//appealInfoBean.setExhibitSet(exhibitSet);
			
			
			List<DocumentImageIndexData> exhibitList=DocumentImageIndexBO.getDocImgIndxDataByIndxKeyAndVal("APPLNUM",appealBO.getAppealData().getDocketNumber().toString());
			ArrayList<AppealExhibitInfoBean> exhibitInfoList=new ArrayList<AppealExhibitInfoBean>();
			for(int i=0;i<exhibitList.size();i++)
			{
				DocumentImageIndexData indexData=exhibitList.get(i);
				DocumentImageIndexData newIndexData= DocumentImageIndexBO.findIdxValueBydocumentIdandIndexKey("EXBTNUM",indexData.getDocImageData().getDocument_id().toString().trim());
				if(newIndexData!=null && newIndexData.getIdxVal()!=null)
				{
					AppealExhibitInfoBean exhibitInfoBean=new AppealExhibitInfoBean();
					exhibitInfoBean.setIndexValue(newIndexData.getIdxVal());
					exhibitInfoBean.setDocumentImageIndexId(indexData.getDoc_im_idx_id().toString().trim());
					exhibitInfoBean.setDocumentId(indexData.getDocImageData().getDocument_id().toString().trim());
					exhibitInfoBean.setDocumentPath(indexData.getDocImageData().getDocPath().toString().trim());
					exhibitInfoBean.setExhibitNumber(newIndexData.getIdxVal());
					exhibitInfoList.add(exhibitInfoBean);
					
				}
			}
			appealInfoBean.setExhibitList(exhibitInfoList);
			/*Set<DocumentImageIndexData> exhibitSet=new HashSet();
			if(exhibitList!=null)
			{
				exhibitSet=new HashSet(exhibitList);
				
			}*/
		    objAssembly = getCorrespondenceIdForBORRecommendation(objAssembly);
		    //appealInfoBean.setExhibitSet(exhibitSet);
			// adding the bean in the object assembly
			objAssembly.addBean(appealInfoBean,true);
			return objAssembly;
		}
		//CIF_00478 Starts
		@Override
		public IObjectAssembly getAppealInfoForBORMeeting(IObjectAssembly objAssembly) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getAppealInfoForBORMeeting");
		}
			AddressBean employerAddress = new AddressBean();

			AppealData appealData = Appeal.fetchAppealAlongWithAppealChildByAppealId(Long.valueOf(objAssembly.getPrimaryKey().toString()));
			
			/*
			 * This if will be called when it comes from process proof reading flow
			 * else determineAppealFlow will be blank only
			 * */
			String determineAppealFlow = (String)objAssembly.getData(AppealConstants.APP_PROOFREADING_FLOW);
			if (StringUtility.isNotBlank(determineAppealFlow)){
				DecisionData decisionData = appealData.getActiveDecisionData();
				if (decisionData!=null){
					// this null is handled for next and back,in back decisionData will be null, so do not add to objassembly
					objAssembly.addComponent(decisionData,true);
				}
			}
			
			//preload claimantdata to prevent lazy init later on
			DecisionData decisionData = appealData.getAppealledAgainstDecision();
			 if(decisionData != null && decisionData.getIssueData() != null
					 && decisionData.getIssueData().getClaimData() != null
					 && decisionData.getIssueData().getClaimData().getClaimantData() != null)
			 {				 
				 ClaimantData claimantData = decisionData.getIssueData().getClaimData().getClaimantData();
				 objAssembly.addComponent(claimantData);
			 }
			
			//for  diaplaying  Appeal info
			AppealInfoBean appealInfoBean = (AppealInfoBean)objAssembly.fetchORCreateBean(AppealInfoBean.class);
			//new AppealInfoBean();
			List aljUserList = AppealScheduleBO.getALJUserListFromLdap(AppealConstants.BOR_UNIT_ID);
			// its using the ALJ unit id, needs to be changed for BOR
			objAssembly.addBeanList(aljUserList,true);
			Map issueMap=Appeal.getIssueDescriptionAndDecisionDateFromAppealData(appealData);
			if(issueMap!=null && issueMap.containsKey(AppealConstants.ISSUE_DESCRIPTION)){
				appealInfoBean.setIssueDescriptionDetailsString(String.valueOf(issueMap.get(AppealConstants.ISSUE_DESCRIPTION)));
			}else{
				appealInfoBean.setIssueDescriptionDetailsString(ViewConstants.NOT_APPLICABLE);
			}
			if(issueMap!=null && issueMap.containsKey(AppealConstants.ISSUE_CATEGORY)){
				appealInfoBean.setIssueDescriptionString(String.valueOf(issueMap.get(AppealConstants.ISSUE_CATEGORY)));
			}else{
				appealInfoBean.setIssueDescriptionString(ViewConstants.NOT_APPLICABLE);
			}
			if(issueMap!=null && issueMap.containsKey(AppealConstants.ISSUE_SUB_CATEGORY)){
				appealInfoBean.setIssueDetailsString(String.valueOf(issueMap.get(AppealConstants.ISSUE_SUB_CATEGORY)));
			}else{
				appealInfoBean.setIssueDetailsString(ViewConstants.NOT_APPLICABLE);
			}
			
			Map claimantEmployerInfoMap = AppealUtil.getClaimantAndEmployerInformationByAppealId(appealData);
			appealInfoBean.setHeaderClaimantName((String)claimantEmployerInfoMap.get(AppealConstants.KEY_CLAIMANT_NAME));
			appealInfoBean.setHeaderClaimantSSN((String)claimantEmployerInfoMap.get(AppealConstants.KEY_CLAIMANT_SSN));
			appealInfoBean.setHeaderEAN((String)claimantEmployerInfoMap.get(AppealConstants.KEY_MDES_EAN));
			appealInfoBean.setHeaderEmployerName((String)claimantEmployerInfoMap.get(AppealConstants.KEY_EMPLOYER_NAME));
			appealInfoBean.setHeaderSpouseName((String)claimantEmployerInfoMap.get(AppealConstants.KEY_SPOUSE_NAME));
			appealInfoBean.setHeaderSpouseSSN((String)claimantEmployerInfoMap.get(AppealConstants.KEY_SPOUSE_SSN));

			
			//chk for instances of appeal data
			//if (appealBO.getAppealData() instanceof NonMonAppealData)
			if (appealData instanceof NonMonAppealData){
				//NonMonAppeal nonMonAppealBO = (NonMonAppeal)appealBO;
				NonMonAppealData nonMonAppealData = (NonMonAppealData)appealData;
				// get the Nonmon decision ID
				DecisionData nondecisiondata = nonMonAppealData.getAppealledAgainstDecision();
				/*CIF_00476 Starts,Desc:Checked for non decision data */
				if(nondecisiondata!=null){
				appealInfoBean.setIssueId(nondecisiondata.getIssueData().getIssueId().toString());
				}
				/*CIF_00476 Ends */
				appealInfoBean.setAppealReason(nonMonAppealData.getAppealReason());
				appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",nonMonAppealData.getAppellant(),"description"));
				appealInfoBean.setAppellantValue(nonMonAppealData.getAppellant());
				if(nonMonAppealData.getInterpreter() != null) {
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(nonMonAppealData.getInterpreter()).getDescription());
					// TODO add field according to input
				}else {
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				appealInfoBean.setDocketNumber(nonMonAppealData.getDocketNumber().toString());
				appealInfoBean.setDecisionDescription((String)CacheUtility.getCachePropertyValue("ISSUE_CATEGORY_DISPLAY","key",nondecisiondata.getIssueData().getMstIssueMaster().getIssueCategory(),"description"));
				// getting the appellant and opponent list,if claimant/employer is the appellant or opponent
				// accordingly that will be shown under appellant or opponent section
				List appellantInfoList = AppealUtil.getAppllantInformation(nonMonAppealData);
				//ClaimantData claimantData = null;
				AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
				if (appealPartyDataForAppellant.getClaimantData()!=null) {
					ClaimantData claimantData = appealPartyDataForAppellant.getClaimantData();
                    // setting claimant Address
					appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
					appealInfoBean.setClaimantSsn(claimantData.getSsn());
					appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
					appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.CLAIMANT.getName());
				}else if (appealPartyDataForAppellant.getEmployerData()!=null) {
					EmployerData employerData = appealPartyDataForAppellant.getEmployerData();
					appealInfoBean.setEmployerName(employerData.getEmployerName());
					appealInfoBean.setEmployerEan(employerData.getEan());
					// getting employer contact details from employer contact table
					setEmployerContactDetails(employerAddress, employerData, appealInfoBean);
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
				} 
				if(AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(nonMonAppealData.getAppellant()) && AppealPartyTypeEnum.APPELLANT.getName().equalsIgnoreCase(appealPartyDataForAppellant.getPartyType()) && appealPartyDataForAppellant.getEmployerData()==null){
					//This if condition is added to handle Outofstate and Unregistered employer details
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
					
					appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
					appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);
					appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
				}
				
				
				List opponentInfoList = AppealUtil.getOpponentInformation(nonMonAppealData);
				if(opponentInfoList != null && !opponentInfoList.isEmpty()) {
					AppealPartyData appealPartyDataForOpponent= (AppealPartyData)opponentInfoList.get(0);
					if (appealPartyDataForOpponent.getClaimantData()!=null) {
						ClaimantData claimantData = appealPartyDataForOpponent.getClaimantData();
	                    // setting claimant Address
						appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
						appealInfoBean.setClaimantSsn(claimantData.getSsn());
						appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
						appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.CLAIMANT.getName());
					}else if (appealPartyDataForOpponent.getEmployerData()!=null) {
						EmployerData employerData = appealPartyDataForOpponent.getEmployerData();
						appealInfoBean.setEmployerName(employerData.getEmployerName());
						appealInfoBean.setEmployerEan(employerData.getEan());
						// getting employer contact details from employer contact table
						EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
						setEmployerContactDetails(employerAddress, employerData, appealInfoBean);
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.EMPLOYER.getName());
					}
					if(AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(nonMonAppealData.getOpponent()) && AppealPartyTypeEnum.OPPONENT.getName().equalsIgnoreCase(appealPartyDataForAppellant.getPartyType()) && appealPartyDataForAppellant.getEmployerData()==null){
						//This if condition is added to handle Outofstate and Unregistered employer details
						appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
						
						appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
						appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);
						appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
					}
				}
				// fetching  the exhibit set
//				Set exhibitSet = Appeal.getExhibitListByDocketNumber(nonMonAppealData.getDocketNumber());
//				exhibitSet.size();
//				appealInfoBean.setExhibitSet(exhibitSet);
				objAssembly.addComponent(nonMonAppealData,true);
				// fetch the  bor recommendation set
				Set borRecSet = nonMonAppealData.getBorRecommendationSet();
				borRecSet.size();
				// fetch the appeal remand details set
				Set remandSet = nonMonAppealData.getAppealRemandDetailSet();
				remandSet.size();
				// adding nonMonetarydata object to object assembly
				objAssembly.setPrimaryKey(IssueData.class,nondecisiondata.getIssueData().getIssueId());
				appealInfoBean.setAppealType(AppealTypeEnum.NON_MON.getName());			
			}else if(appealData instanceof TaxAppealData) {
				//TaxAppealData taxAppealData =(TaxAppealData)appealBO.getAppealData();
				TaxAppealData taxAppealData =(TaxAppealData)appealData;
				// set the docket number
				appealInfoBean.setDocketNumber(taxAppealData.getDocketNumber().toString());
				if(taxAppealData.getInterpreter() != null) {
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(taxAppealData.getInterpreter()).getDescription());
				}else {
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				// get the taxappeal issue type
				String issueCodeDesc = (String)CacheUtility.getCachePropertyValue("T_MST_TAX_APPEAL_ISSUE","key",taxAppealData.getTaxAppealIssueCode(),"description");
				appealInfoBean.setDecisionDescription(issueCodeDesc);
				// get the Appeallant
				appealInfoBean.setAppellant(taxAppealData.getAppellant());
				appealInfoBean.setAppellantValue(taxAppealData.getAppellant());
				//List appellantInfoList = AppealUtil.getAppllantInformation(appealBO.getAppealData());
				List appellantInfoList = AppealUtil.getAppllantInformation(appealData);
				//ClaimantData claimantData = null;
				AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
				if(AppealPartyTypeEnum.APPELLANT.getName().equals(appealPartyDataForAppellant.getPartyType())) {
					EmployerData employerData = (EmployerData)appealPartyDataForAppellant.getEmployerData();
					//	setting Employer info
					if(employerData != null) {
						
						appealInfoBean.setEmployerName(((RegisteredEmployerData)employerData).getEmployerAccountData().getEntityName());
						appealInfoBean.setEmployerEan(((RegisteredEmployerData)employerData).getEmployerAccountData().getEan());
						// getting employer contact details from employer contact table
						setEmployerContactDetails(employerAddress, employerData, appealInfoBean);
						//end if of employerContactData
					}//end if of employer data
				}//end  if of appealPartyData.getPartyType()
				//appealInfoBean.setDecisionDescription(taxAppealData.getTaxAppealIssueCode());
				// set reason for appeal
				appealInfoBean.setAppealReason(taxAppealData.getAppealReason());
				//	fetching  the exhibit set
//				Set exhibitSet = taxAppealData.getAppealExhibitSet();
//				Set exhibitSet = Appeal.getExhibitListByDocketNumber(taxAppealData.getDocketNumber());
//				if(exhibitSet != null) {
//					exhibitSet.size();
//					appealInfoBean.setExhibitSet(exhibitSet);
//				}
				// fetch the  bor recommendation set
				Set borRecSet = taxAppealData.getBorRecommendationSet();
				borRecSet.size();
				//fetch the appeal remand details set
				Set remandSet = taxAppealData.getAppealRemandDetailSet();
				remandSet.size();
				appealInfoBean.setAppealType(AppealTypeEnum.TAXA.getName());
				// set tax appeal data in the object assembly
				objAssembly.addComponent(taxAppealData,true);
			}else if(appealData instanceof LateAppealData) {				
                
				LateAppealData lateAppealData =(LateAppealData)appealData;
				appealInfoBean.setDocketNumber(lateAppealData.getDocketNumber().toString());
				appealInfoBean.setDecisionDescription(AppealConstants.TIMELINESS);
				if(lateAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(lateAppealData.getInterpreter()).getDescription());
				}else{
					appealInfoBean.setInterpreter(InterpreterEnum.NOIP.getDescription());
				}
				// get the Appeallant
				appealInfoBean.setAppellantValue(lateAppealData.getAppellant());
				appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",lateAppealData.getAppellant(),"description"));
				appealInfoBean.setAppealReason(lateAppealData.getAppealReason());
				appealInfoBean.setAppealType(AppealTypeEnum.getEnum(lateAppealData.getAppealType()).getDescription());
				//	get the employer details
				//Set lateAppealPartySet = lateAppealData.getAppealPartySet();
				List appellantList = AppealUtil.getAppllantInformation(lateAppealData);
				if(appellantList != null && appellantList.size()>0){
					//for (Iterator it = lateAppealPartySet.iterator(); it.hasNext(); ) {
						// get the appealparty data
				AppealPartyData appealPartyData = (AppealPartyData) appellantList.get(0);
				//(AppealPartyData)it.next();
						//if(AppealPartyTypeEnum.APPELLANT.getName().equals(appealPartyData.getPartyType())){
				
				EmployerData employerData = (EmployerData)appealPartyData.getEmployerData();
				AppealData newAppealData = lateAppealData.getParentAppealData();
				
				//CQ 9597 - preload the data			
				objAssembly.addData("latesParentAppeal", newAppealData);
				objAssembly.addData("latesParentIssue", newAppealData.getAppealledAgainstDecision().getIssueData());
				
				
				//	setting Employer info
				if(TaxAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if(employerData != null){
						appealInfoBean.setEmployerName(employerData.getEmployerName());
						appealInfoBean.setEmployerEan(employerData.getEan());
						// getting employer contact details from employer contact table
						if(setEmployerContactDetails(employerAddress, employerData, appealInfoBean)){
							appealInfoBean.setAppealType(AppealTypeEnum.TAXA.getName());
						}
					}//end if of employer data
				}
				if(NonMonAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(lateAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
					if (AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(lateAppealData.getAppellant())){
						EmployerData nonMonEmployerData = (EmployerData)appealPartyData.getEmployerData();
						if(nonMonEmployerData != null){
							//	getting employer contact details from employer contact table
							EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
							setEmployerContactDetails(employerAddress,employerData,appealInfoBean);
						}else {
							List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
							AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
							appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());													
							appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());												
							appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);		
							appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
						}
					}
					//appealInfoBean.setAppealType(AppealTypeEnum.NON_MON.getName());
				}
				if( TaxInterceptAppealData.class.isAssignableFrom(newAppealData.getClass())){
					/* CIF_01352 start
					 * commented Both as party is not there in missouri
					 * if (AppellantOrOpponentEnum.BOTH.getName().equalsIgnoreCase(lateAppealData.getAppellant())){
						AppealPartyData appealParty1DataForAppellant = (AppealPartyData)appellantList.get(0);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setClaimantDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);                
							
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
									
						}
					
						AppealPartyData appealParty2DataForAppellant = (AppealPartyData)appellantList.get(1);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty2DataForAppellant.getPartyType())){
			                
							this.setClaimantDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty2DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
											
						}
					}else*/ if (AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(lateAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setClaimantDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(lateAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setSpouseDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}
					
					//appealInfoBean.setAppealType(AppealTypeEnum.LATE.getName());
				}
				if (MiscAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(lateAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
				}
				if (NonAppearanceAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(lateAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
					if (AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(lateAppealData.getAppellant())){
						EmployerData nonMonEmployerData = (EmployerData)appealPartyData.getEmployerData();
						if(nonMonEmployerData != null){
							//	getting employer contact details from employer contact table
							setEmployerContactDetails(employerAddress, employerData, appealInfoBean);
						}else {
							List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
							AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
							appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());													
							appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());												
							appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);		
							appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
						}
					}
				}

				   appealInfoBean.setAppealReason(lateAppealData.getAppealReason());
				   appealInfoBean.setParentAppealId(lateAppealData.getParentAppealData().getAppealId().toString());
				   objAssembly.addComponent(lateAppealData,true);
			    }
			}else if (appealData instanceof TaxInterceptAppealData) {
	            //TaxInterceptAppeal taxInterceptAppealBO =(TaxInterceptAppeal) appealBO;
	            //TaxInterceptAppeal taxInterceptAppealBO =(TaxInterceptAppeal) appealData;
				TaxInterceptAppealData taxInterceptAppealData = (TaxInterceptAppealData)appealData;
				// set the docket number
				appealInfoBean.setDocketNumber(taxInterceptAppealData.getDocketNumber().toString());
				if(taxInterceptAppealData.getInterpreter() != null) {
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(taxInterceptAppealData.getInterpreter()).getDescription());
				}else {
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				String appellant =(String)CacheUtility.getCachePropertyValue("T_MST_APPELLANT","key",taxInterceptAppealData.getAppellant(),"description");
				appealInfoBean.setAppellant(appellant);
				appealInfoBean.setAppellantValue(taxInterceptAppealData.getAppellant());
				/* CIF_01352 start
				 * commented Both as party is not there in missouri
				 * if (AppellantOrOpponentEnum.BOTH.getName().equals(taxInterceptAppealData.getAppellant())) {
					List appellantInfoList = AppealUtil.getAppllantInformation(taxInterceptAppealData);
					AppealPartyData appealParty1DataForAppellant = (AppealPartyData)appellantInfoList.get(0);
					if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty1DataForAppellant.getPartyType())) {
						this.setClaimantDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
						appealInfoBean.setClaimantName(appealParty1DataForAppellant.getPartyName());
						appealInfoBean.setClaimantSsn(appealParty1DataForAppellant.getSsn());
						appealInfoBean.setClaimantTelephone(appealParty1DataForAppellant.getPhoneNumber());
						appealInfoBean.setClaimantAddressBean(appealParty1DataForAppellant.getMailingAddress().getAddressBean());
					}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty1DataForAppellant.getPartyType())) {
						this.setSpouseDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
						appealInfoBean.setSpouseName(appealParty1DataForAppellant.getPartyName());
						appealInfoBean.setSpouseSsn(appealParty1DataForAppellant.getSsn());
						appealInfoBean.setSpousePhoneNum((appealParty1DataForAppellant.getPhoneNumber()));
						appealInfoBean.setSpouseAddressBean(appealParty1DataForAppellant.getMailingAddress().getAddressBean());
					}
					AppealPartyData appealParty2DataForAppellant = (AppealPartyData)appellantInfoList.get(1);
					if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty2DataForAppellant.getPartyType())) {
						this.setClaimantDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
						appealInfoBean.setClaimantName(appealParty2DataForAppellant.getPartyName());
						appealInfoBean.setClaimantSsn(appealParty2DataForAppellant.getSsn());
						appealInfoBean.setClaimantTelephone(appealParty2DataForAppellant.getPhoneNumber());
						appealInfoBean.setClaimantAddressBean(appealParty2DataForAppellant.getMailingAddress().getAddressBean());
					}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty2DataForAppellant.getPartyType())) {
						this.setSpouseDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
						appealInfoBean.setSpouseName(appealParty2DataForAppellant.getPartyName());
						appealInfoBean.setSpouseSsn(appealParty2DataForAppellant.getSsn());
						appealInfoBean.setSpousePhoneNum((appealParty2DataForAppellant.getPhoneNumber()));
						appealInfoBean.setSpouseAddressBean(appealParty2DataForAppellant.getMailingAddress().getAddressBean());
						
					}
				}else */if (AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(taxInterceptAppealData.getAppellant())) {
					List appellantInfoList = AppealUtil.getAppllantInformation(taxInterceptAppealData);
					AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
					this.setClaimantDetailsForTaxIntercept(appealPartyDataForAppellant,appealInfoBean);
					/*appealInfoBean.setClaimantName(appealPartyDataForAppellant.getPartyName());
					appealInfoBean.setClaimantSsn(appealPartyDataForAppellant.getSsn());
					appealInfoBean.setClaimantTelephone(appealPartyDataForAppellant.getPhoneNumber());
					appealInfoBean.setClaimantAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());*/
				}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(taxInterceptAppealData.getAppellant())) {
					List appellantInfoList = AppealUtil.getAppllantInformation(taxInterceptAppealData);
					AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
					this.setSpouseDetailsForTaxIntercept(appealPartyDataForAppellant,appealInfoBean);
					/*appealInfoBean.setSpouseName(appealPartyDataForAppellant.getPartyName());
					appealInfoBean.setSpouseSsn(appealPartyDataForAppellant.getSsn());
					appealInfoBean.setSpousePhoneNum((appealPartyDataForAppellant.getPhoneNumber()));
					appealInfoBean.setSpouseAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());*/
				}
				// set reason for appeal
				appealInfoBean.setAppealReason(taxInterceptAppealData.getAppealReason());
				//	fetching  the exhibit set
//				Set exhibitSet = taxInterceptAppealData.getAppealExhibitSet();
//				Set exhibitSet = Appeal.getExhibitListByDocketNumber(taxInterceptAppealData.getDocketNumber());
//				if(exhibitSet != null) {
//					exhibitSet.size();
//					appealInfoBean.setExhibitSet(exhibitSet);
//				}
				Set borRecSet = taxInterceptAppealData.getBorRecommendationSet();
				if(borRecSet != null) {
					borRecSet.size();
				}
				TaxInterceptMasterData taxIntMasterData = taxInterceptAppealData.getTaxInterceptMasterData();
				taxIntMasterData.getSitDate();
				appealInfoBean.setAppealType(AppealTypeEnum.TAX_INT.getName());
				// set tax appeal data in the object assembly
				objAssembly.addComponent(taxInterceptAppealData,true);
				objAssembly.addComponent(taxInterceptAppealData.getTaxInterceptMasterData(),true);
			}else if (appealData instanceof MiscAppealData){
				  
				//MiscAppeal miscAppealBo = (MiscAppeal)appealBO;
				MiscAppealData miscAppealData =(MiscAppealData)appealData;
				// get the Nonmon decision ID
				//DecisionData decisiondata = miscAppealData.getAppealledAgainstDecision();
				//appealInfoBean.setIssueId(decisiondata.getIssueData().getIssueId().toString());
				appealInfoBean.setAppealReason(miscAppealData.getAppealReason());
				//appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",miscAppealData.getAppellant(),"description"));
				appealInfoBean.setAppellantValue(miscAppealData.getAppellant());
				if(miscAppealData.getInterpreter() != null && !miscAppealData.getInterpreter().trim().equals("")) {
					//appealInfoBean.setInterpreter(InterpreterEnum.getEnum(nonMonAppealData.getInterpreter()).getDescription());
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(miscAppealData.getInterpreter()).getDescription());
				}else {
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				appealInfoBean.setDocketNumber(miscAppealData.getDocketNumber().toString());
				//appealInfoBean.setDecisionDescription((String)CacheUtility.getCachePropertyValue("ISSUE_CATEGORY_DISPLAY","key",decisiondata.getIssueData().getMstIssueMaster().getIssueCategory(),"description"));
				appealInfoBean.setDecisionDescription(GlobalConstants.NA);
				// getting the appellant and opponent list,if claimant/employer is the appellant or opponent
				// accordingly that will be shown under appellant or opponent section
				List appellantInfoList = AppealUtil.getAppllantInformation(miscAppealData);
				//ClaimantData claimantData = null;
				
				if(miscAppealData.getSsn() != null){
					SsnBean objSssBean = new SsnBean(miscAppealData.getSsn());
					appealInfoBean.setMiscSSN(objSssBean.getSsnWithDelimiter());
				}
				AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
				if (appealPartyDataForAppellant.getSsn()!=null) {
					//ClaimantData claimantData = appealPartyDataForAppellant.getClaimantData();
                    // setting claimant Address
					appealInfoBean.setClaimantName(appealPartyDataForAppellant.getPartyName());
					appealInfoBean.setClaimantSsn(appealPartyDataForAppellant.getSsn());
					if (appealPartyDataForAppellant.getPhoneNumber()!=null){
						appealInfoBean.setClaimantTelephone(appealPartyDataForAppellant.getPhoneNumber());
					}
					if(appealPartyDataForAppellant.getMailingAddress()!=null){
						appealInfoBean.setClaimantAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
					}else if(appealPartyDataForAppellant.getClaimantData()!=null){
						if(appealPartyDataForAppellant.getClaimantData().getMailingAddress()!=null){
							appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getClaimantData().getMailingAddress().getAddrData().getAddressBean());
						}
					}
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.CLAIMANT.getName());
				}else if (appealPartyDataForAppellant.getEmployerData()!=null){
					EmployerData employerData = appealPartyDataForAppellant.getEmployerData();
					appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
					
					// getting employer contact details from employer contact table
					if (appealPartyDataForAppellant.getEmployerData()!=null){
						appealInfoBean.setEmployerEan(appealPartyDataForAppellant.getEmployerData().getEan());
						setEmployerContactDetails(employerAddress, employerData, appealInfoBean);
					}else{
						if(appealPartyDataForAppellant.getMailingAddress()!=null){
							appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
						}
						appealInfoBean.setEmployerEan(GlobalConstants.NA);
					}
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
				}else{
					// claimant id is there for the appeal party
					ClaimantData claimantData = appealPartyDataForAppellant.getClaimantData();
					if(claimantData == null){
						String ssnFromMiscData = miscAppealData.getSsn();
						if(ssnFromMiscData != null){
							Claimant claimantBO = Claimant.fetchClaimantBySsn(ssnFromMiscData);
							claimantData = claimantBO.getClaimantData();
						}
					}

					// setting claimant Address
					appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
					appealInfoBean.setClaimantSsn(claimantData.getSsn());
					if (claimantData.getClaimantProfileData()!=null){
						if (StringUtility.isNotBlank(claimantData.getClaimantProfileData().getPrimaryPhone())){
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
						}
					}
					appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.CLAIMANT.getName());
				}
				List opponentInfoList = AppealUtil.getOpponentInformation(miscAppealData);
				if(opponentInfoList != null && !opponentInfoList.isEmpty()) {
					AppealPartyData appealPartyDataForOpponent= (AppealPartyData)opponentInfoList.get(0);
					if (appealPartyDataForOpponent.getSsn()!=null) {
						//ClaimantData claimantData = appealPartyDataForAppellant.getClaimantData();
	                    // setting claimant Address
						appealInfoBean.setClaimantName(appealPartyDataForOpponent.getPartyName());
						appealInfoBean.setClaimantSsn(appealPartyDataForOpponent.getSsn());
						if (appealPartyDataForOpponent.getPhoneNumber()!=null){
							appealInfoBean.setClaimantTelephone(appealPartyDataForOpponent.getPhoneNumber());
						}
						
						appealInfoBean.setClaimantAddressBean(appealPartyDataForOpponent.getMailingAddress().getAddressBean());
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.CLAIMANT.getName());
					}else {
						EmployerData employerData = appealPartyDataForOpponent.getEmployerData();
						appealInfoBean.setEmployerName(appealPartyDataForOpponent.getPartyName());
						if (employerData!=null){
							appealInfoBean.setEmployerEan(employerData.getEan());
                            // getting employer contact details from employer contact table
							setEmployerContactDetails(employerAddress, employerData, appealInfoBean);
						}else {
							appealInfoBean.setEmployerAddressBean(appealPartyDataForOpponent.getMailingAddress().getAddressBean());
							appealInfoBean.setEmployerEan(GlobalConstants.NA);
						}
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.EMPLOYER.getName());
					}
				}
				// fetching  the exhibit set
//				Set exhibitSet = miscAppealData.getAppealExhibitSet();
//				Set exhibitSet = Appeal.getExhibitListByDocketNumber(miscAppealData.getDocketNumber());
//				exhibitSet.size();
//				appealInfoBean.setExhibitSet(exhibitSet);
				objAssembly.addComponent(miscAppealData,true);
				// fetch the  bor recommendation set
				Set borRecSet = miscAppealData.getBorRecommendationSet();
				borRecSet.size();
				// fetch the appeal remand details set
				Set remandSet = miscAppealData.getAppealRemandDetailSet();
				remandSet.size();
				// adding nonMonetarydata object to object assembly
				//objAssembly.setPrimaryKey(IssueData.class,decisiondata.getIssueData().getIssueId());
				appealInfoBean.setAppealType(AppealTypeEnum.MISC.getName());	
			}else if (appealData instanceof LateDeniedAppealData) {

                LateDeniedAppealData lateDeniedAppealData =(LateDeniedAppealData)appealData;
				appealInfoBean.setDocketNumber(lateDeniedAppealData.getDocketNumber().toString());
				appealInfoBean.setDecisionDescription(AppealConstants.TIMELINESS);
				if(lateDeniedAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(lateDeniedAppealData.getInterpreter()).getDescription());
				}else{
					appealInfoBean.setInterpreter(InterpreterEnum.NOIP.getDescription());
				}
				
				//CQ 9597 Preload the data
				BORMeetingAppealMapData borMeetingAppealMapData= 
	    			BORMeetingAppealMap.fetchBORDecisionLetterReportParams(appealData.getAppealId());
				objAssembly.addData("borMeetingAppealMapData", borMeetingAppealMapData);

				
				// get the Appeallant
				appealInfoBean.setAppellantValue(lateDeniedAppealData.getAppellant());
				//appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",lateDeniedAppealData.getAppellant(),"description"));
				appealInfoBean.setAppellant(AppellantOrOpponentEnum.getEnum(lateDeniedAppealData.getAppellant()).getDescription());
				appealInfoBean.setAppealReason(lateDeniedAppealData.getAppealReason());
				appealInfoBean.setAppealType(AppealTypeEnum.getEnum(lateDeniedAppealData.getAppealType()).getDescription());
				//	get the employer details
				//Set lateAppealPartySet = lateAppealData.getAppealPartySet();
				List appellantList = AppealUtil.getAppllantInformation(lateDeniedAppealData);
				if(appellantList != null && appellantList.size()>0){
					//for (Iterator it = lateAppealPartySet.iterator(); it.hasNext(); ) {
						// get the appealparty data
				AppealPartyData appealPartyData = (AppealPartyData) appellantList.get(0);
				//(AppealPartyData)it.next();
						//if(AppealPartyTypeEnum.APPELLANT.getName().equals(appealPartyData.getPartyType())){
				EmployerData employerData = (EmployerData)appealPartyData.getEmployerData();
				AppealData newAppealData = lateDeniedAppealData.getParentAppealData();
				//	setting Employer info
				if(TaxAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if(employerData != null){
						appealInfoBean.setEmployerName(employerData.getEmployerName());
						appealInfoBean.setEmployerEan(employerData.getEan());
						if(setEmployerContactDetails(employerAddress, employerData, appealInfoBean)){
							appealInfoBean.setAppealType(AppealTypeEnum.TAXA.getName());
						}//end if of employerContactData
					}//end if of employer data
				}
				if(NonMonAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(lateDeniedAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
					if (AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(lateDeniedAppealData.getAppellant())){
						EmployerData nonMonEmployerData = (EmployerData)appealPartyData.getEmployerData();
						if(nonMonEmployerData != null){
							//	getting employer contact details from employer contact table
							appealInfoBean.setEmployerEan(nonMonEmployerData.getEan());
							appealInfoBean.setEmployerName(nonMonEmployerData.getEmployerName());
							//try to set employer contact details normally
							if(!(setEmployerContactDetails(employerAddress, employerData, appealInfoBean))){
								List appellantInfoList = AppealUtil.getAppllantInformation(lateDeniedAppealData);
								AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
								appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
								appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
								appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);		
								appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
							}
						}else {
							List appellantInfoList = AppealUtil.getAppllantInformation(lateDeniedAppealData);
							AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
							appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
							appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
							appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);
							appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
						}
					}
					//appealInfoBean.setAppealType(AppealTypeEnum.NON_MON.getName());
				}
				if( TaxInterceptAppealData.class.isAssignableFrom(newAppealData.getClass())){
					/* CIF_01352 start
					 * commented Both as party is not there in missouri
					 * if (AppellantOrOpponentEnum.BOTH.getName().equalsIgnoreCase(lateDeniedAppealData.getAppellant())){
						AppealPartyData appealParty1DataForAppellant = (AppealPartyData)appellantList.get(0);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setClaimantDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);                
							
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
									
						}
					
						AppealPartyData appealParty2DataForAppellant = (AppealPartyData)appellantList.get(1);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty2DataForAppellant.getPartyType())){
			                
							this.setClaimantDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty2DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
											
						}
					}else*/ if (AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(lateDeniedAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setClaimantDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(lateDeniedAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setSpouseDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}
					
					//appealInfoBean.setAppealType(AppealTypeEnum.LATE.getName());
				}
				if (MiscAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(lateDeniedAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
				}
				appealInfoBean.setAppealReason(lateDeniedAppealData.getAppealReason());
				appealInfoBean.setParentAppealId(lateDeniedAppealData.getParentAppealData().getAppealId().toString());
				objAssembly.addComponent(lateDeniedAppealData,true);
			    }
				
			}else if (appealData instanceof NonAppearanceAppealData) {

				NonAppearanceAppealData nonAppearanceAppealData =(NonAppearanceAppealData)appealData;
				appealInfoBean.setDocketNumber(nonAppearanceAppealData.getDocketNumber().toString());
				appealInfoBean.setDecisionDescription(AppealConstants.NO_APPERANCE);
				if(nonAppearanceAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(nonAppearanceAppealData.getInterpreter()).getDescription());
				}else{
					appealInfoBean.setInterpreter(InterpreterEnum.NOIP.getDescription());
				}
				// get the Appeallant
				appealInfoBean.setAppellantValue(nonAppearanceAppealData.getAppellant());
				//appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",nonAppearanceAppealData.getAppellant(),"description"));
				appealInfoBean.setAppellant(AppealPartyTypeEnum.getEnum(nonAppearanceAppealData.getAppellant()).getDescription());
				appealInfoBean.setAppealReason(nonAppearanceAppealData.getAppealReason());
				appealInfoBean.setAppealType(AppealTypeEnum.getEnum(nonAppearanceAppealData.getAppealType()).getDescription());
				//	get the employer details
				//Set lateAppealPartySet = lateAppealData.getAppealPartySet();
				List appellantList = AppealUtil.getAppllantInformation(nonAppearanceAppealData);
				if(appellantList != null && appellantList.size()>0){
					//for (Iterator it = lateAppealPartySet.iterator(); it.hasNext(); ) {
						// get the appealparty data
				AppealPartyData appealPartyData = (AppealPartyData) appellantList.get(0);
				//(AppealPartyData)it.next();
						//if(AppealPartyTypeEnum.APPELLANT.getName().equals(appealPartyData.getPartyType())){
				EmployerData employerData = (EmployerData)appealPartyData.getEmployerData();
				AppealData newAppealData = Appeal.findByAppealId(nonAppearanceAppealData.getParentAppealData().getAppealId());
				//	setting Employer info
				if(TaxAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if(employerData != null){
						appealInfoBean.setEmployerName(employerData.getEmployerName());
						appealInfoBean.setEmployerEan(employerData.getEan());
						// getting employer contact details from employer contact table
						if(setEmployerContactDetails(employerAddress, employerData, appealInfoBean)){						
							appealInfoBean.setAppealType(AppealTypeEnum.TAXA.getName());
						}//end if of employerContactData
					}//end if of employer data
				}
				if(NonMonAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(nonAppearanceAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
					if (AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(nonAppearanceAppealData.getAppellant())){
						EmployerData nonMonEmployerData = (EmployerData)appealPartyData.getEmployerData();
						if(nonMonEmployerData != null){
							//	getting employer contact details from employer contact table
							appealInfoBean.setEmployerName(appealPartyData.getPartyName());
							appealInfoBean.setEmployerEan(nonMonEmployerData.getEan());
							//try to set employer contact details normally
							if(!(setEmployerContactDetails(employerAddress, employerData, appealInfoBean))){
								List appellantInfoList = AppealUtil.getAppllantInformation(nonAppearanceAppealData);
								AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
								appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
								appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
								appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);
								appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
							}
						}else {
							List appellantInfoList = AppealUtil.getAppllantInformation(nonAppearanceAppealData);
							AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
							appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
							appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
							appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);
							appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
						}
					}
					//appealInfoBean.setAppealType(AppealTypeEnum.NON_MON.getName());
				} else if(LateAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(nonAppearanceAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
					if (AppellantOrOpponentEnum.EMPLOYER.getName().equalsIgnoreCase(nonAppearanceAppealData.getAppellant())){
						EmployerData nonMonEmployerData = (EmployerData)appealPartyData.getEmployerData();
						if(nonMonEmployerData != null){
							//	getting employer contact details from employer contact table
							appealInfoBean.setEmployerName(appealPartyData.getPartyName());
							appealInfoBean.setEmployerEan(nonMonEmployerData.getEan());
							//try to set employer contact details normally
							if(!(setEmployerContactDetails(employerAddress, employerData, appealInfoBean))){
								List appellantInfoList = AppealUtil.getAppllantInformation(nonAppearanceAppealData);
								AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
								appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
								appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
								appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);
								appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
							}
						}else {
							List appellantInfoList = AppealUtil.getAppllantInformation(nonAppearanceAppealData);
							AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
							appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
							appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
							appealInfoBean.setEmployerEan(AppealConstants.DUMMY_EAN);
							appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
						}
					}
					//appealInfoBean.setAppealType(AppealTypeEnum.NON_MON.getName());
				}
				if( TaxInterceptAppealData.class.isAssignableFrom(newAppealData.getClass())){
					/* CIF_01352 start
					 * commented Both as party is not there in missouri
					 * if (AppellantOrOpponentEnum.BOTH.getName().equalsIgnoreCase(nonAppearanceAppealData.getAppellant())){
						AppealPartyData appealParty1DataForAppellant = (AppealPartyData)appellantList.get(0);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setClaimantDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);                
							
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty1DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty1DataForAppellant,appealInfoBean);
									
						}
					
						AppealPartyData appealParty2DataForAppellant = (AppealPartyData)appellantList.get(1);
						if(AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(appealParty2DataForAppellant.getPartyType())){
			                
							this.setClaimantDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
						}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(appealParty2DataForAppellant.getPartyType())){
							this.setSpouseDetailsForTaxIntercept(appealParty2DataForAppellant,appealInfoBean);
											
						}
					}else*/ if (AppealPartyTypeEnum.TAXI_APPELLANT_CLAIMANT.getName().equals(nonAppearanceAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setClaimantDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}else if (AppealPartyTypeEnum.TAXI_APPELLANT_SPOUSE.getName().equals(nonAppearanceAppealData.getAppellant())){
						
						//List appellantInfoList = AppealUtil.getAppllantInformation(lateAppealData);
						//AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantList.get(0);
						this.setSpouseDetailsForTaxIntercept(appealPartyData,appealInfoBean);
					}
					
					//appealInfoBean.setAppealType(AppealTypeEnum.LATE.getName());
				}
				if (MiscAppealData.class.isAssignableFrom(newAppealData.getClass())){
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equalsIgnoreCase(nonAppearanceAppealData.getAppellant())){
						ClaimantData claimantData =(ClaimantData)appealPartyData.getClaimantData();
						if(claimantData !=null ){
							appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
							appealInfoBean.setClaimantSsn(claimantData.getSsn());
							appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
							// setting claimant Address
							appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
						}
					}
				}
				appealInfoBean.setAppealReason(nonAppearanceAppealData.getAppealReason());
				appealInfoBean.setParentAppealId(nonAppearanceAppealData.getParentAppealData().getAppealId().toString());
				objAssembly.addComponent(nonAppearanceAppealData,true);
			    }
			
			}
			//fetching  the exhibit set
			//CIF_02416||Defect_3405||Appeals	Issues in LIRC recommendation
			/*Modified code for Appeal Exhibit */
			//Set exhibitSet = Appeal.getExhibitListByDocketNumber(appealData.getDocketNumber());
			//setFlagsForExhibitSet(exhibitSet);
			//appealInfoBean.setExhibitSet(exhibitSet);
			List<DocumentImageIndexData> exhibitList=DocumentImageIndexBO.getDocImgIndxDataByIndxKeyAndVal("APPLNUM",appealData.getDocketNumber().toString());
			ArrayList<AppealExhibitInfoBean> exhibitInfoList=new ArrayList<AppealExhibitInfoBean>();
			for(int i=0;i<exhibitList.size();i++)
			{
				DocumentImageIndexData indexData=exhibitList.get(i);
				DocumentImageIndexData newIndexData= DocumentImageIndexBO.findIdxValueBydocumentIdandIndexKey("EXBTNUM",indexData.getDocImageData().getDocument_id().toString().trim());
				if(newIndexData!=null && newIndexData.getIdxVal()!=null)
				{
					AppealExhibitInfoBean exhibitInfoBean=new AppealExhibitInfoBean();
					exhibitInfoBean.setIndexValue(newIndexData.getIdxVal());
					exhibitInfoBean.setDocumentImageIndexId(indexData.getDoc_im_idx_id().toString().trim());
					exhibitInfoBean.setDocumentId(indexData.getDocImageData().getDocument_id().toString().trim());
					exhibitInfoBean.setDocumentPath(indexData.getDocImageData().getDocPath().toString().trim());
					exhibitInfoBean.setExhibitNumber(newIndexData.getIdxVal());
					exhibitInfoList.add(exhibitInfoBean);
					
				}
			}
			appealInfoBean.setExhibitList(exhibitInfoList);
			
			/*End of Modified code for Appeal Exhibit */
//			defect #8437
			appealInfoBean.setAppealId(appealData.getAppealId().toString());			
			objAssembly.setPrimaryKey(Long.valueOf(appealInfoBean.getAppealId()));
			objAssembly = getCorrespondenceIdForBORMeeting(objAssembly);
			// adding the bean in the object assembly
			//objAssembly.addBean(appealInfoBean, true);
			
		//@cif_wy(impactNumber = "P1-APP-27" , requirementId = "FR_1632", designDocName = "04 UIC", designDocSection = "4.7", dcrNo = "", mddrNo = "DCR_MDDR_153", impactPoint = "Start") 	
			Object lObject = MultiStateClassFactory.getObject(this.getClass()
					.getName(), BaseOrStateEnum.STATE, null, null, Boolean.TRUE);

			if (null != lObject) {
				List<FSUserData> fsUserDataList	 = ((BORService) lObject).getUserDataListForUicCommesionMembers(GlobalConstants.UIC_USER_NAME);
			   if(fsUserDataList != null && !fsUserDataList.isEmpty()){
				   objAssembly.addData("fsUserDataList" , fsUserDataList);
			   }
			}
		//@cif_wy(impactNumber = "P1-APP-27" , requirementId = "FR_1632", designDocName = "04 UIC", designDocSection = "4.7", dcrNo = "", mddrNo = "DCR_MDDR_153", impactPoint = "Start") 	
			return objAssembly;
		}
		//CIF_00478 Ends
		//this method will set the exhibits flags to a default value of 0 (No), if they were never set
		private void setFlagsForExhibitSet(Set exhibitSet)
		{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method setFlagsForExhibitSet");
		}
			Iterator iter = exhibitSet.iterator();
			while(iter.hasNext()){
				AppealExhibitData exhibitData  = (AppealExhibitData)iter.next();
				if(StringUtility.isBlank(exhibitData.getUsedAppealFlag())){
					exhibitData.setUsedAppealFlag(GlobalConstants.DB_ANSWER_NO);
				}
				if(StringUtility.isBlank(exhibitData.getUsedNmonFlag())){
					exhibitData.setUsedNmonFlag(GlobalConstants.DB_ANSWER_NO);
				}
			}
		}
		
		
		// this method s used to get adjudicator decision
		@Override
		public IObjectAssembly getAdjudicatorDecision(IObjectAssembly objAssembly) throws BaseApplicationException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getAdjudicatorDecision");
		}
			Long  docketNumber = Long.valueOf(Long.parseLong((String)objAssembly.getData("docketNumber")));
			// get the Appeal bo with docket number at Adjudicator level
			Appeal appealBO = Appeal.getAdjudicatorDecisionByDocketNumber(docketNumber);
			
			/* CIF_INT_00928
			 * If Late non-mon appeal, adjudicator decision is coming as blank.
			 * System is fetching the details by docket number instead of appeal id.
			 */
			if(appealBO instanceof LateAppeal){
				appealBO = Appeal.getAppealBoByAppealData(
						((LateAppealData)appealBO.getAppealData()).getParentAppealData());
			}

			if(appealBO instanceof NonMonAppeal){
				NonMonAppealData nonMonAppealData = (NonMonAppealData)appealBO.getAppealData();
				// get the decision data using the Nonmon decision id
				Decision decision = Decision.getDecisionAndMstIssueMasterDataByDecisionId(nonMonAppealData.getAppealledAgainstDecision().getDecisionID());
				DecisionData decisionData = decision.getDecisionData();
				// set the data for display
				AdjudicatorDecisionDisplayBean adjudicatorDecisionDisplayBean = new AdjudicatorDecisionDisplayBean();
				adjudicatorDecisionDisplayBean.setDocketNumber(nonMonAppealData.getDocketNumber().toString());
				// setting decision data to bean
				adjudicatorDecisionDisplayBean.setDecisionData(decisionData);
				//	adding the bean in the object assembly
				objAssembly.addBean(adjudicatorDecisionDisplayBean, true);
			}
			
		
			return objAssembly;
		
		}
		// this method is used to display the ALJ decision
		@Override
		public IObjectAssembly getALJDecision(IObjectAssembly objAssembly) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getALJDecision");
		}
			Long  docketNumber = Long.valueOf(Long.parseLong((String)objAssembly.getData("aljdecisiondocketNumber")));
			// get the Appeal bo with docket number at Adjudicator level
			Appeal appealBO = Appeal.getALJDecisionByDocketNumber(docketNumber);
			ALJDecisionDisplayBean aljDecisionDisplayBean = new ALJDecisionDisplayBean();
			if(appealBO!=null)
			{
				NonMonAppealData nonMonAppealData = (NonMonAppealData)appealBO.getAppealData();
				// get the decision data using the Nonmon decision id
				Decision decision = Decision.getDecisionAndMstIssueMasterDataByDecisionId(nonMonAppealData.getAppealledAgainstDecision().getDecisionID());
				DecisionData decisionData = decision.getDecisionData();
				// set the data for display
				
				aljDecisionDisplayBean.setDocketNumber(nonMonAppealData.getDocketNumber().toString());
				//	setting decision data to bean
				aljDecisionDisplayBean.setDecisionData(decisionData);
				//	adding the bean in the object assembly
			}
			else
			{
				aljDecisionDisplayBean.setDocketNumber(docketNumber.toString());
			}
			
			objAssembly.addBean(aljDecisionDisplayBean);
			return objAssembly;
		}
		
		// this method is used to save the BOR recommendation
		@Override
		public IObjectAssembly processBORAppealInfoAndSaveRecommendation(IObjectAssembly objAssembly) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method processBORAppealInfoAndSaveRecommendation");
		}
			
			MemberDocketBean memberDocketBean = (MemberDocketBean)objAssembly.fetchORCreateBean(MemberDocketBean.class);
			FSUserData fsUserData = FSUser.getFSUserByUserId(memberDocketBean.getUserId());
			BORRecommendationData borRecommendationData = (BORRecommendationData)objAssembly.getFirstComponent(BORRecommendationData.class,true);
			AppealData appealData = null;
			borRecommendationData.setFsUserData(fsUserData);
			if (AppealTypeEnum.NON_MON.getName().equals(memberDocketBean.getAppealType())){
				appealData = (NonMonAppealData)objAssembly.getFirstComponent(NonMonAppealData.class);
			 
			}else if (AppealTypeEnum.TAXA.getName().equals(memberDocketBean.getAppealType())){
				appealData = (TaxAppealData)objAssembly.getFirstComponent(TaxAppealData.class);
				
			}else if (AppealTypeEnum.TAX_INT.getName().equals(memberDocketBean.getAppealType())){
				appealData = (TaxInterceptAppealData)objAssembly.getFirstComponent(TaxInterceptAppealData.class);
				
			}else if (AppealTypeEnum.LATE.getName().equals(memberDocketBean.getAppealType())){
				appealData = (LateAppealData)objAssembly.getFirstComponent(LateAppealData.class);
				
			}else if (AppealTypeEnum.MISC.getName().equals(memberDocketBean.getAppealType())){
				appealData = (MiscAppealData)objAssembly.getFirstComponent(MiscAppealData.class);
				
			}else if (AppealTypeEnum.LDAD.getName().equals(memberDocketBean.getAppealType())){
				appealData = (LateDeniedAppealData)objAssembly.getFirstComponent(LateDeniedAppealData.class);
			}else if (AppealTypeEnum.NOAP.getName().equals(memberDocketBean.getAppealType())){
				appealData = (NonAppearanceAppealData)objAssembly.getFirstComponent(NonAppearanceAppealData.class);
			}
			borRecommendationData.setAppealData(appealData);
			BoardOfReview borReviewBO =  new BoardOfReview(borRecommendationData);
			borReviewBO.saveOrUpdate();
	        return objAssembly;
		}
		
		// get all the useid with has a particular role from ldap (here we get for BOR role)
		@Override
		public IObjectAssembly getMembersforConductingBORMeeting(IObjectAssembly objAssembly) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getMembersforConductingBORMeeting");
		}
			String[] roles = new String[]{FunctionsEnum.NMON_BOR_REC.getName(), FunctionsEnum.TAX_BOR_REC.getName(),
					FunctionsEnum.TAX_INTERCEPT_BOR_REC.getName(), FunctionsEnum.MISC_BOR_REC.getName(),FunctionsEnum.LATE_BOR_REC.getName(),
					FunctionsEnum.LATE_DEND_BOR_REC.getName(),FunctionsEnum.NO_APPEARANCE.getName()};
			List MembersforConductingBORMeetingList = FSUser.getListofUsersByRoleId(roles);
			ArrayList MembersforConductingBORMeetingArrayList =  new ArrayList();
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			Set borParticipantSet = borMeetingData.getBorParticipantSet();
			// get the users lists by userids
			Iterator membersforConductingBORMeetingListIterator = MembersforConductingBORMeetingList.iterator();
			DynaBean usersDynaBean = null;
			while (membersforConductingBORMeetingListIterator.hasNext()){
				usersDynaBean = (DynaBean) (membersforConductingBORMeetingListIterator.next());
				FSUserData fsUserData = new FSUserData();
				fsUserData.setFirstName((String) usersDynaBean.get("accessfirstname"));
				fsUserData.setLastName((String) usersDynaBean.get("accesslastname"));
				fsUserData.setMiddleInitial((String) usersDynaBean.get("accessmiddleinitial"));
				fsUserData.setUserId((String) usersDynaBean.get("accessuserid"));
				Iterator borParticipantSetIt = borParticipantSet.iterator();
				while (borParticipantSetIt.hasNext()){
					BORParticipantData borParticipantData = (BORParticipantData) borParticipantSetIt.next();
					//Bormeeting.getBorParticipantData();
					BORParticipantData borParticipantDataWithMdesUserInfo = BORMeeting.getBorParticipantData(borParticipantData.getBorParticipantID());
					if (borParticipantDataWithMdesUserInfo.getFsUserData().getUserId().equalsIgnoreCase((String) usersDynaBean.get("accessuserid"))){
						fsUserData.setWasMemberPresent(GlobalConstants.DB_ANSWER_YES);
						fsUserData.setUserId(borParticipantDataWithMdesUserInfo.getFsUserData().getUserId());
						break;
					}
				}
				MembersforConductingBORMeetingArrayList.add(fsUserData);
			}
			
			//CQ 11416
			//Filter out inactive users
			Iterator inactiveFilterIter = MembersforConductingBORMeetingArrayList.iterator();
			while(inactiveFilterIter.hasNext()){
				FSUserData borUser = (FSUserData)inactiveFilterIter.next();
				borUser = FSUser.getFSUserByUserId(borUser.getUserId());
				if(borUser == null || GlobalConstants.DB_ANSWER_NO.equals(borUser.getUserStatus())) {
					inactiveFilterIter.remove();
				}
			}
			
			objAssembly.addComponentList(MembersforConductingBORMeetingArrayList,true);
			return objAssembly;
		}
		
		@Override
		public IObjectAssembly processBORMmeetingStepOne(IObjectAssembly objAssembly) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method processBORMmeetingStepOne");
		}
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			// get the se;lected members from the screen (ALJ selected)
			//String [] selectedMembersForMeeting = (String [])objAssembly.getData("selectedMembers");
			/*CIF_00476 Starts.Desc:Retrieved member details and added to selectedMembersForMeeting*/
			String userId=super.getUserContext().getUserId();
			String [] selectedMembersForMeeting ={userId};
			//String [] selectedMembersForMeeting ={"ryadav"};
			/*CIF_00476 Starts*/
			Set participantsSet = new HashSet();
			//String userId = null;
			// get the data and save the participants in T_BOR_PARTICIPANT table 
			if(selectedMembersForMeeting !=null && selectedMembersForMeeting.length!=0){
				for(int i=0;i< selectedMembersForMeeting.length;i++){
					userId = selectedMembersForMeeting[i].toString();
					BORParticipantData borParticipantData = new BORParticipantData();
					FSUserData fsuserData = FSUser.getFSUserByUserId(userId);
					borParticipantData.setFsUserData(fsuserData);
					borParticipantData.setBorMeetingData(borMeetingData);
					participantsSet.add(borParticipantData);
				}
				borMeetingData.setBorParticipantSet(participantsSet);			
			}
			BORMeeting borMeetingBO = new BORMeeting(borMeetingData);
			borMeetingBO.saveOrUpdate();
			objAssembly.addData("appealTypeList", borMeetingBO.getAppealTypesForBorMeeting(selectedMembersForMeeting));
			//objAssembly.addComponent(borMeetingBO.getBORMeetingData(),true);
			return objAssembly;
		}
		
		// this method get info for Reviewing for BOR meeting for all appeals which are at BOR level
		@Override
		public IObjectAssembly getInfoForMasterDocketReview(IObjectAssembly objAssembly) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getInfoForMasterDocketReview");
		}
					
			List appealListbymeetingId = new ArrayList();
			List docketMemberList = new ArrayList();
			docketMemberList.clear();
			List remandCompletedAppList = new ArrayList();
			// get all appeals which are at BOR level
			docketMemberList = Appeal.fetchAppealsByBORLevels("BOR");
			// get the bor meeting data
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			// get all the appeals whose decisions are taken in the present meeting
			if(borMeetingData != null){
				appealListbymeetingId = Appeal.fetchAppealDataByBORMeetingId(borMeetingData.getBorMeetingId());
				if(appealListbymeetingId != null && appealListbymeetingId.size() > 0){
				docketMemberList.addAll(appealListbymeetingId);
				}
			}
			remandCompletedAppList = Appeal.getRemandCompletedAppeals();
			// to add all the list of appeals which have reamnd status completed
			if(remandCompletedAppList != null && remandCompletedAppList.size() >0){
				docketMemberList.addAll(remandCompletedAppList);
			}
			
			objAssembly.addComponentList(docketMemberList,true);
			return objAssembly;
		}
		
		// this method is used to adjourn the BOR meeting and the end time for meeting is updated in T_BOARD_OF_REVIEW table
		@Override
		public IObjectAssembly adjournBORMeeting(IObjectAssembly objAssembly) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method adjournBORMeeting");
		}
			String ajournMeeting = (String)objAssembly.getData("adjournBORMeeting");
			Boolean meetingAdjourned = Boolean.FALSE;
			if(GlobalConstants.DB_ANSWER_YES.equals(ajournMeeting)){
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			borMeetingData.setBorParticipantSet(null);
			List borMeetingMapList = BORMeetingAppealMap.fetchBorMeetingAppealMapData(borMeetingData.getBorMeetingId());
			if (borMeetingMapList!=null && borMeetingMapList.size()>0){
				for (int i=0;i<borMeetingMapList.size();i++){
					BORMeetingAppealMapData borMeetingAppealMapData = (BORMeetingAppealMapData)borMeetingMapList.get(i);
					//borMeetingAppealMapData.setBorMasterDocketData(null);
					//borMeetingAppealMapData.setBorMeetingData(borMeetingData);
					AppealData apepalData = borMeetingAppealMapData.getAppealData();
					apepalData.setPriorityFlag(GlobalConstants.DB_ANSWER_YES);
					apepalData.setHearingScheduled(GlobalConstants.DB_ANSWER_NO);
					if(!AppealStatusEnum.REMAND_LOWER.getName().equals(apepalData.getAppealStatus())){
						apepalData.setAppealStatus(AppealStatusEnum.CONTINUED.getName());
					}
					Appeal appealBO =  Appeal.getAppealBoByAppealData(apepalData);
					appealBO.saveOrUpdate();
					//borMeetingAppealMapData.setBorMeetingData(null);
					//BORMeetingData borMeetingData = borMeetingAppealMapData.getBorMeetingData();
					
				}
			    BORMeetingAppealMap.deleteAll(borMeetingMapList,borMeetingData);
			}
			borMeetingData.setBorMasterDocketDataSet(null);
			borMeetingData.setStatus(BorMeetingStatusEnum.COMPLETED.getName());
			BORMeeting borMeetingBO = new BORMeeting(borMeetingData);
			borMeetingBO.saveOrUpdate();
			
			/*Date endDate = new Date();
			String endTime = DateFormatUtility.format(endDate,"HH:mm:ss");
			java.sql.Time meetingEndTime  = java.sql.Time.valueOf(endTime);
			borMeetingData.setEndTime(meetingEndTime);
			BORMeeting borMeetingBO = new BORMeeting(borMeetingData);
			borMeetingBO.saveOrUpdate();*/
			meetingAdjourned = Boolean.TRUE;
			}
			objAssembly.addData("meetingAdjourned",meetingAdjourned);
			return objAssembly;
		}
		
		// this method is used to save bor Affirm decision
		@Override
		public IObjectAssembly saveBORAffirmDecision(IObjectAssembly objAssembly) throws CloneNotSupportedException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveBORAffirmDecision");
		}
			List borMemberList = new ArrayList();
			//get the NonMonData from the object assembly
	        NonMonAppealData nonMonAppealData = (NonMonAppealData)objAssembly.getFirstComponent(NonMonAppealData.class);
	        //get the taxappeal data from the obj assembly
	        TaxAppealData taxAppealData = (TaxAppealData)objAssembly.getFirstComponent(TaxAppealData.class);
	        //get the bor meeting data
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			// get the additional reamarks
			String addRemarks = (String)objAssembly.getData("additionalRemarks");
			ReverseDecisionBean bean = (ReverseDecisionBean)objAssembly.getFirstBean(ReverseDecisionBean.class);
			/*
			 * saving or update of appeal decision will  be treversed from decision as well as
			 * proof of reading screen
			 * this parameter will determine flow : Decision or Proof Reading
			 * if that is blank from appeal decision flow then it is from proof reading flow
			 * */
			String determineAppealFlow = null;
	        determineAppealFlow =  (String)objAssembly.getData(AppealConstants.APP_DECISION_FLOW);
	        
	        if(nonMonAppealData != null){
	        	// get the BOR decision list frm the object assembly
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	// create nonMonBO
	        	NonMonAppeal nonMonAppealBO = new NonMonAppeal(nonMonAppealData);
	        	// call the business method for saving affirm decison
	        	nonMonAppealData.getBorRecommendationSet().clear();
	        	nonMonAppealBO.saveBORAffirmDecision(addRemarks,bean);
	        }	else if(taxAppealData != null){
	        	//get the BOR decision list from the object assembly
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	TaxAppeal taxAppealBO = new TaxAppeal(taxAppealData);
	        	taxAppealBO.saveBORAffirmDecision(addRemarks,bean);
	        	
	        }else if(nonMonAppealData == null){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

	        //TODO change the condition to chk for taxincept data
	        }
	        List borRecommendationDataList = objAssembly.getComponentList(BORRecommendationData.class);
		    if (borRecommendationDataList!=null && borRecommendationDataList.size()>0){
		    	
		         BorRecommendation.saveOrUpdateOrDelete(borRecommendationDataList);
		    }
	        return objAssembly;
		}
		
		// this method is used to save bor Affirm decision
		@Override
		public IObjectAssembly saveBORReverseDecision(IObjectAssembly objAssembly) throws CloneNotSupportedException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveBORReverseDecision");
		}
			List borMemberList = new ArrayList();
			// get the reversedecision bean from the object assembly;
			ReverseDecisionBean boardOfReviewReverseDecisionBean = (ReverseDecisionBean)objAssembly.getFirstBean(ReverseDecisionBean.class);
			//get the NonMonData from the object assembly
	        NonMonAppealData nonMonAppealData = (NonMonAppealData)objAssembly.getFirstComponent(NonMonAppealData.class);
			// get the taxappeal data from the obj assembly
	        TaxAppealData taxAppealData = (TaxAppealData)objAssembly.getFirstComponent(TaxAppealData.class);
	        // getting misc data
	        MiscAppealData miscAppealData  = (MiscAppealData)objAssembly.getFirstComponent(MiscAppealData.class);
	        //getting Late Appeal Data
	        LateAppealData lateAppealData  = (LateAppealData)objAssembly.getFirstComponent(LateAppealData.class);
	        
	        //getting Non Appearance Appeal Data
	        NonAppearanceAppealData nonAppearanceAppealData  = (NonAppearanceAppealData)objAssembly.getFirstComponent(NonAppearanceAppealData.class);
	        
	        // get the bor meeting data
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
			String docketNumber="";
			
			/*UploadDocumentIntoDmsBean uploadBean = objAssembly.getFirstBean(UploadDocumentIntoDmsBean.class);
			AppealInfoBean appealInfoBean =(AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
			if(appealInfoBean.getDocketNumber()!=null)
			{
				Long docketNo=Long.parseLong(appealInfoBean.getDocketNumber());
				uploadBean.setDocketNumber(docketNo);
			}*/
			/*if(uploadBean!=null)
			{
				//boardOfReviewReverseDecisionBean.setFileName(uploadBean.getFileName().getFileName());
				
				//String basePath=ApplicationProperties.FILE_BASE_PATH;
				//String relativePath=ApplicationProperties.IR_FILE_UPLOAD_LOCATION;
				//String documentPath=basePath+relativePath;
				String documentPath="C:\\Sabya-Tools\\File_Upload\\";
				String currFileName="";
				FormFile currFile=uploadBean.getFileName();		
				currFileName = currFile.toString();
				String newFileName="";
				String newFileExt="";
				int i=currFileName.lastIndexOf(".");
				if(i>0)
				{
					newFileName=currFileName.substring(0, i);
					newFileExt=currFileName.substring(i+1);
				}
		    	newFileName=newFileName+"_"+uploadBean.getDocketNumber().toString()+"."+newFileExt;
				documentPath=documentPath+newFileName;
				File newfile = new File(documentPath);
		    	boardOfReviewReverseDecisionBean.setFileName(documentPath);
		    	try {
				     FileOutputStream fos=new FileOutputStream(newfile);
				     if(uploadBean.getInputStream()!=null)
				     {
				       IOUtils.copy(uploadBean.getInputStream(), fos);
				     }
				   } catch (IOException e) {
					// TODO Auto-generated catch block

			if (LOGGER.isEnabledFor(Level.ERROR)) {
				LOGGER.error("error", e);
			}

				  }
		    	
		    	//objAssembly.addBean(uploadBean);
		    	//attachUploadDocumentIntoDms(objAssembly);
			}*/
			
	        if(nonMonAppealData != null){
	        	// get the BOR decision list frm the object assembly
	        	NonMonAppeal nonMonAppealBO = new NonMonAppeal(nonMonAppealData);
	        	nonMonAppealData.setBorRecommendationSet(null);
	        	//uploadBean.setDocketNumber(nonMonAppealData.getDocketNumber());
	        	nonMonAppealBO.saveBORReverseDecision(borMemberList,boardOfReviewReverseDecisionBean,borMeetingData);
	        				
	        }else if(taxAppealData != null){
	        	
	        	TaxAppeal taxAppealBO = new TaxAppeal(taxAppealData);
	        	taxAppealData.setBorRecommendationSet(null);
	        	//uploadBean.setDocketNumber(taxAppealData.getDocketNumber());
	        	taxAppealBO.saveBORReverseDecision(borMemberList,boardOfReviewReverseDecisionBean,borMeetingData);
	        	
	        }else if(miscAppealData != null){
	            	
	        	MiscAppeal miscAppealBO = new MiscAppeal(miscAppealData);
	        	miscAppealData.setBorRecommendationSet(null);
	        	//uploadBean.setDocketNumber(miscAppealData.getDocketNumber());
	        	miscAppealBO.saveReverseDecisionForBor(borMeetingData,boardOfReviewReverseDecisionBean);
	        }else if(lateAppealData != null){
	        	LateAppeal lateAppealBO = new LateAppeal(lateAppealData);
	        	lateAppealData.setBorRecommendationSet(null);
	        	//uploadBean.setDocketNumber(miscAppealData.getDocketNumber());
	        	lateAppealBO.saveReverseDecisionForBor(borMeetingData,boardOfReviewReverseDecisionBean);
            }else if(nonAppearanceAppealData != null){
	        	NonApperanceAppeal nonApperanceAppealBO = new NonApperanceAppeal(nonAppearanceAppealData);
	        	nonAppearanceAppealData.setBorRecommendationSet(null);
	        	//uploadBean.setDocketNumber(miscAppealData.getDocketNumber());
	        	nonApperanceAppealBO.saveReverseDecisionForBor(borMeetingData,boardOfReviewReverseDecisionBean);
            }  
	        List borRecommendationDataList = objAssembly.getComponentList(BORRecommendationData.class);
		    if (borRecommendationDataList!=null && borRecommendationDataList.size()>0){
		    	
		         //BorRecommendation.saveOrUpdateOrDelete(borRecommendationDataList);
		    	// this will delete existing recommendation except recuse decision
		    	BorRecommendation.saveOrUpdateOrDeleteRecommendationOnSaveDecision(borRecommendationDataList);
		    }
		   
			return objAssembly;
		}
		@Override
		public IObjectAssembly attachUploadDocumentIntoDms(final IObjectAssembly objectAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method attachUploadDocumentIntoDms");
		}

			UploadDocumentIntoDmsBean uploadBean = objectAssembly.getFirstBean(UploadDocumentIntoDmsBean.class);
			CorrespondenceData correspondenceData = new CorrespondenceData();
			correspondenceData.setCorrespondenceCode(CorrespondenceCodeIncomingEnum.getEnum(uploadBean.getCorrespondenceCode()).getName());
			correspondenceData.setDirection(CorrespondenceDirectionEnum.INCOMING.getName());
			ClaimantData claimantData =  objectAssembly.getFirstComponent(ClaimantDataBridge.class);

			if(uploadBean.getNodeType().equals(GlobalConstants.CLAIMANT)){

				correspondenceData.setClaimantData(claimantData);
			}else{

				correspondenceData.setParameter6(String.valueOf(objectAssembly.getPrimaryKey()));
				correspondenceData.setParameter6Desc(CorrespondenceParameterEnum.APPEAL_ID.getName());
			}

			correspondenceData.setUpdatedBy(getContextUserId());
			correspondenceData.setUpdatedOn(new Date());
			Correspondence correspondence = new Correspondence(correspondenceData);
			correspondence.saveOrUpdate();

			//This is to initialize Dao
			BaseDmsDAO dao = new BaseDmsDAO();
			try {
				dao.setReadWriteSession();

				if(uploadBean.getNodeType().equals(GlobalConstants.APPELLANT)){

					AppealDmsBean appealDmdBean = new AppealDmsBean();
					appealDmdBean.setDdockectNo(uploadBean.getDocketNumber().toString());
					CorrespoandenceDmsBean corrEmpBean = appealDmdBean.getNewCorrespoandenceDmsBean();
					corrEmpBean.setCorrId(String.valueOf(correspondenceData.getCorrespondenceId()));
					corrEmpBean.setDocCode(uploadBean.getCorrespondenceCode());
					corrEmpBean.setInboundDirection();
					Calendar cal = new GregorianCalendar();
					cal.setTime(new Date());
					corrEmpBean.setMailDate(cal);
					corrEmpBean.setTitle(CorrespondenceCodeIncomingEnum.getEnum(uploadBean.getCorrespondenceCode()).getDescription());
					corrEmpBean.setStream(uploadBean.getInputStream());
					corrEmpBean.setMimeType(uploadBean.getMimeType());
					dao.addAppealCorrespondence(appealDmdBean);

					dao.save();
				} else{

					ClaimantDmsBean claimDmsBean = new ClaimantDmsBean();
					claimDmsBean.setSsn(uploadBean.getSsn());
					CorrespoandenceDmsBean corrEmpBean = claimDmsBean.getNewCorrespoandenceDmsBean();
					corrEmpBean.setCorrId(String.valueOf(correspondenceData.getCorrespondenceId()));
					corrEmpBean.setDocCode(uploadBean.getCorrespondenceCode());
					corrEmpBean.setInboundDirection();
					Calendar cal = new GregorianCalendar();
					cal.setTime(new Date());
					corrEmpBean.setMailDate(cal);
					corrEmpBean.setTitle(CorrespondenceCodeIncomingEnum.getEnum(uploadBean.getCorrespondenceCode()).getDescription());
					corrEmpBean.setStream(uploadBean.getInputStream());
					corrEmpBean.setMimeType(uploadBean.getMimeType());
					dao.addClaimantCorrespondence(claimDmsBean);

					dao.save();
				}
			} finally {
				if(dao != null) {
					dao.logout();
				}
			}
			return objectAssembly;
		}
		// this method gets information for customization
		@Override
		public IObjectAssembly getAppealInfoForBORCustomizationOfDecision(IObjectAssembly objAssembly) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getAppealInfoForBORCustomizationOfDecision");
		}
			AddressBean employerAddress = new AddressBean();
			String pkey = (String)objAssembly.getPrimaryKey();
			//	for  diaplaying  Appeal info
			AppealInfoBean appealInfoBean = new AppealInfoBean();
			// getting the appleal business objet by appealid
			Long appealId = Long.valueOf(pkey);
			Appeal appealBO = Appeal.findByPrimaryKey(appealId);
			// set appealid
			appealInfoBean.setAppealId(pkey);
			Map issueMap=Appeal.getIssueDescriptionAndDecisionDateFromAppealData(appealBO.getAppealData());
			if(issueMap!=null && issueMap.containsKey(AppealConstants.ISSUE_DESCRIPTION)){
				appealInfoBean.setIssueDescriptionDetailsString(String.valueOf(issueMap.get(AppealConstants.ISSUE_DESCRIPTION)));
			}else{
				appealInfoBean.setIssueDescriptionDetailsString(ViewConstants.NOT_APPLICABLE);
			}
			if (appealBO.getAppealData() instanceof NonMonAppealData){
				NonMonAppealData nonMonAppealData = (NonMonAppealData)appealBO.getAppealData();
				// get the Nonmon decision ID
				//Long nonMonDecisionId = nonMonAppealData.getNonMonDecisionId();
				
				DecisionData nondecisiondata = nonMonAppealData.getAppealledAgainstDecision();
				ClaimantData claimantData = nondecisiondata.getIssueData().getClaimData().getClaimantData();
				// Employer Data
				EmployerData employerData =nondecisiondata.getIssueData().getFactFindingData().getEmployerData();
				// setting appeal info
				appealInfoBean.setAppealType(AppealTypeEnum.NON_MON.getName());
				appealInfoBean.setIssueId(nondecisiondata.getIssueData().getIssueId().toString());
				appealInfoBean.setAppealReason(nonMonAppealData.getAppealReason());
				appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",nonMonAppealData.getAppellant(),"description"));
				if(nonMonAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(nonMonAppealData.getInterpreter());
				}else{
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				appealInfoBean.setDocketNumber(nonMonAppealData.getDocketNumber().toString());
				appealInfoBean.setDecisionDescription((String)CacheUtility.getCachePropertyValue("ISSUE_CATEGORY_DISPLAY","key",nondecisiondata.getIssueData().getMstIssueMaster().getIssueCategory(),"description"));
				// setting claimant info
				if(claimantData !=null){
					appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
					appealInfoBean.setClaimantSsn(claimantData.getSsn());
					appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
					// setting claimant Address
					appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
				}
				// setting Employer info
				if(employerData != null){
					appealInfoBean.setEmployerName(employerData.getEmployerName());
					appealInfoBean.setEmployerEan(employerData.getEan());
					
					// getting employer contact details from employer contact table
					EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
					if(employerContactData != null){
						employerAddress.setAddress1(employerContactData.getAddressLine1());
						employerAddress.setAddress2(employerContactData.getAddressLine2());
						employerAddress.setCity(employerContactData.getCity());
						employerAddress.setCountry(employerContactData.getCountry());
						employerAddress.setZip(employerContactData.getZip());
						employerAddress.setState(employerContactData.getState());
						employerAddress.setPostalBarCode(employerContactData.getBarcode());
						if(employerContactData.getPhone() != null){
							appealInfoBean.setEmployerTelephone(employerContactData.getPhone());
						}else{
							appealInfoBean.setEmployerTelephone(GlobalConstants.NOT_APPLICABLE);
						}
						appealInfoBean.setEmployerAddressBean(employerAddress);
					}
				}
				
				appealInfoBean.setDecision((String)CacheUtility.getCachePropertyValue("T_MST_BOR_RECOMMENDATION","key",nondecisiondata.getDecisionCode(),"description"));
				appealInfoBean.setDecisionStDate(nondecisiondata.getDecisionStartDate().toString());
				// to display appeal information of decision taken  from appeal decision detail table
				//	for getting the decisions at BOR level which is active
				Set decisionsSet = nonMonAppealData.getDecisions();
				
				if(decisionsSet != null){
					for (Iterator it = decisionsSet.iterator(); it.hasNext(); ) {
						//	get the appealparty data
						DecisionData decisionData = (DecisionData)it.next();
						if(decisionData.getActiveFlag().equals(GlobalConstants.DB_ANSWER_YES) && decisionData.getDecisionStatus().equals(DecisionStatusEnum.BOARD_OF_REVIEW.getName())){
							appealInfoBean.setDecisionStDate(DateFormatUtility.format(decisionData.getDecisionStartDate()));
							appealInfoBean.setDecisionEndDate(DateFormatUtility.format(decisionData.getDecisionEndDate()));
							appealInfoBean.setDecision(decisionData.getAppealDecisionDetailData().getAppealDecisionCode());
							appealInfoBean.setDecisionNotes(decisionData.getAppealDecisionDetailData().getDecisionNote());
							appealInfoBean.setCustomizeDecision(decisionData.getAppealDecisionDetailData().getCustomizedDecision());
						}
					}
				}
							
				// adding the bean in the object assembly
				//objAssembly.addBean(appealInfoBean,true);
				// adding decision data to the object assembly
				objAssembly.addComponent(nondecisiondata,true);
				//	adding issuedata to object assembly
				objAssembly.addComponent(nonMonAppealData,true);
			}else if(appealBO.getAppealData() instanceof TaxAppealData){
				// typecast with taxappeal data
				TaxAppealData taxAppealData =(TaxAppealData)appealBO.getAppealData();
				// set the docket number
				appealInfoBean.setDocketNumber(taxAppealData.getDocketNumber().toString());
				appealInfoBean.setAppealType(AppealTypeEnum.TAXA.getName());
				if(taxAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(taxAppealData.getInterpreter()).getDescription());
				}else{
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				// get the taxappeal issue type
				appealInfoBean.setDecisionDescription(taxAppealData.getTaxAppealIssueCode());
				// get the Appeallant
				appealInfoBean.setAppellant(AppellantOrOpponentEnum.getEnum(taxAppealData.getAppellant()).getDescription());
				// get the employer details
				Set taxAppealPartySet = taxAppealData.getAppealPartySet();
				//Assuming that we have only one Appelant and that is employer
				if(taxAppealPartySet != null){
					for (Iterator it = taxAppealPartySet.iterator(); it.hasNext(); ) {
						// get the appealparty data
						AppealPartyData appealPartyData = (AppealPartyData)it.next();
						if(AppealPartyTypeEnum.APPELLANT.getName().equals(appealPartyData.getPartyType())){
							EmployerData employerData = (EmployerData)appealPartyData.getEmployerData();
							//	setting Employer info
							if(employerData != null){
								appealInfoBean.setEmployerName(employerData.getEmployerName());
								appealInfoBean.setEmployerEan(employerData.getEan());
								// getting employer contact details from employer contact table
								EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
								if(employerContactData != null){
									employerAddress.setAddress1(employerContactData.getAddressLine1());
									employerAddress.setAddress2(employerContactData.getAddressLine2());
									employerAddress.setCity(employerContactData.getCity());
									employerAddress.setCountry(employerContactData.getCountry());
									employerAddress.setZip(employerContactData.getZip());
									employerAddress.setState(employerContactData.getState());
									employerAddress.setPostalBarCode(employerContactData.getBarcode());
									if(employerContactData.getPhone() != null){
										appealInfoBean.setEmployerTelephone(employerContactData.getPhone());
									}else{
										appealInfoBean.setEmployerTelephone(GlobalConstants.NOT_APPLICABLE);
									}
									appealInfoBean.setEmployerAddressBean(employerAddress);
								}//end if of employerContactData
							}//end if of employer data
						}//end  if of appealPartyData.getPartyType()
					}// end for looop
				}// end if taxAppealPartySet
				
				appealInfoBean.setDecisionDescription(taxAppealData.getTaxAppealIssueCode());
				// set reason for appeal
				appealInfoBean.setAppealReason(taxAppealData.getAppealReason());
				// for getting the decisions at BOR level which is active
				Set otherAppealdetailsDataSet = taxAppealData.getDecisions();
				
				if(otherAppealdetailsDataSet != null){
					for (Iterator it = otherAppealdetailsDataSet.iterator(); it.hasNext(); ) {
						//	get the appealparty data
						//Following code is changed for Sonar fix for impossible cast
						//OtherAppealDecisionData otherAppealDecisionData = (OtherAppealDecisionData)it.next();
						DecisionData otherAppealDecisionData = (DecisionData)it.next();
						if(otherAppealDecisionData.getActiveFlag().equals(GlobalConstants.DB_ANSWER_YES) && otherAppealDecisionData.getDecisionStatus().equals(DecisionStatusEnum.BOARD_OF_REVIEW.getName())){
							appealInfoBean.setDecisionStDate(DateFormatUtility.format(otherAppealDecisionData.getDecisionStartDate()));
							appealInfoBean.setDecisionEndDate(DateFormatUtility.format(otherAppealDecisionData.getDecisionEndDate()));
							//appealInfoBean.setDecision(taxAppealData.getDecisioncode());
							//Following code is commented as per the sonar fix changes made on the otherAppealDecisionData type
							//appealInfoBean.setDecisionNotes(otherAppealDecisionData.getDecisionNote());
							//appealInfoBean.setCustomizeDecision(otherAppealDecisionData.getCustomizedDecision());
							//appealInfoBean.setActiveOtherDecision(otherAppealDecisionData.getOtherAppealDecisionid());
						}
					}
				}
				
				//	adding the bean in the object assembly
				
				// adding Taxappealdata to object assembly
				objAssembly.addComponent(taxAppealData,true);
			}else if (appealBO.getAppealData() instanceof MiscAppealData){
				
				MiscAppeal miscAppealBo = (MiscAppeal)appealBO;
				MiscAppealData miscAppealData = (MiscAppealData)miscAppealBo.getAppealData();
				// get the Nonmon decision ID
				appealInfoBean.setAppealType(AppealTypeEnum.MISC.getName());
				appealInfoBean.setAppealReason(miscAppealData.getAppealReason());
				appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",miscAppealData.getAppellant(),"description"));
				appealInfoBean.setAppellantValue(miscAppealData.getAppellant());
				if(miscAppealData.getInterpreter() != null) {
					//appealInfoBean.setInterpreter(InterpreterEnum.getEnum(nonMonAppealData.getInterpreter()).getDescription());
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(miscAppealData.getInterpreter()).getDescription());
				}
				else {
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				appealInfoBean.setDocketNumber(miscAppealData.getDocketNumber().toString());
				//appealInfoBean.setDecisionDescription((String)CacheUtility.getCachePropertyValue("ISSUE_CATEGORY_DISPLAY","key",decisiondata.getIssueData().getMstIssueMaster().getIssueCategory(),"description"));
				appealInfoBean.setDecisionDescription(GlobalConstants.NA);
				// for misc appeal data issue desc will be NA
				List appellantInfoList = AppealUtil.getAppllantInformation(miscAppealData);
				//ClaimantData claimantData = null;
				AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
				if (appealPartyDataForAppellant.getSsn()!=null) {
					//ClaimantData claimantData = appealPartyDataForAppellant.getClaimantData();
                    // setting claimant Address
					appealInfoBean.setClaimantName(appealPartyDataForAppellant.getPartyName());
					appealInfoBean.setClaimantSsn(appealPartyDataForAppellant.getSsn());
					if (appealPartyDataForAppellant.getPhoneNumber()!=null){
						appealInfoBean.setClaimantTelephone(appealPartyDataForAppellant.getPhoneNumber());
					}
					appealInfoBean.setClaimantAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.CLAIMANT.getName());
				}
				else {
					EmployerData employerData = appealPartyDataForAppellant.getEmployerData();
					appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
					
					// getting employer contact details from employer contact table
					if (appealPartyDataForAppellant.getEmployerData()!=null){
						appealInfoBean.setEmployerEan(appealPartyDataForAppellant.getEmployerData().getEan());
						EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
						if(employerContactData != null) {
							this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
						}
					}else{
						appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
						appealInfoBean.setEmployerEan(GlobalConstants.NA);
					}
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
				}
				List opponentInfoList = AppealUtil.getOpponentInformation(miscAppealData);
				if(opponentInfoList != null && !opponentInfoList.isEmpty()) {
					AppealPartyData appealPartyDataForOpponent= (AppealPartyData)opponentInfoList.get(0);
					if (appealPartyDataForOpponent.getSsn()!=null) {
						//ClaimantData claimantData = appealPartyDataForAppellant.getClaimantData();
	                    // setting claimant Address
						appealInfoBean.setClaimantName(appealPartyDataForOpponent.getPartyName());
						appealInfoBean.setClaimantSsn(appealPartyDataForOpponent.getSsn());
						if (appealPartyDataForOpponent.getPhoneNumber()!=null){
							appealInfoBean.setClaimantTelephone(appealPartyDataForOpponent.getPhoneNumber());
						}
						
						appealInfoBean.setClaimantAddressBean(appealPartyDataForOpponent.getMailingAddress().getAddressBean());
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.CLAIMANT.getName());
					}else {
						EmployerData employerData = appealPartyDataForOpponent.getEmployerData();
						appealInfoBean.setEmployerName(appealPartyDataForOpponent.getPartyName());
						if (employerData!=null){
							appealInfoBean.setEmployerEan(employerData.getEan());
                            // getting employer contact details from employer contact table
							EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
							if(employerContactData != null) {
								this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
							}
						}else {
							appealInfoBean.setEmployerAddressBean(appealPartyDataForOpponent.getMailingAddress().getAddressBean());
							appealInfoBean.setEmployerEan(GlobalConstants.NA);
						}
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.EMPLOYER.getName());
					}
				}
                Set decisionsSet = miscAppealData.getDecisions();
				
				if(decisionsSet != null){
					for (Iterator it = decisionsSet.iterator(); it.hasNext(); ) {
						//	get the appealparty data
						DecisionData decisionData = (DecisionData)it.next();
						if(decisionData.getActiveFlag().equals(GlobalConstants.DB_ANSWER_YES) && decisionData.getDecisionStatus().equals(DecisionStatusEnum.BOARD_OF_REVIEW.getName())){
							appealInfoBean.setDecisionStDate(DateFormatUtility.format(decisionData.getDecisionStartDate()));
							appealInfoBean.setDecisionEndDate(DateFormatUtility.format(decisionData.getDecisionEndDate()));
							appealInfoBean.setDecision(decisionData.getAppealDecisionDetailData().getAppealDecisionCode());
							appealInfoBean.setDecisionNotes(decisionData.getAppealDecisionDetailData().getDecisionNote());
							appealInfoBean.setCustomizeDecision(decisionData.getAppealDecisionDetailData().getCustomizedDecision());
						}
					}
				}
			}
			objAssembly.addBean(appealInfoBean,true);
			objAssembly.addComponent(appealBO.getAppealData(),true);
			return objAssembly;
		}
		
		/**
		 * This method verifies an issue to be created
		 */
		@Override
		public IObjectAssembly  getIssueType(IObjectAssembly objAssembly) {
			//use the issue type handler to recycle the values
			String issueCategory = (String)objAssembly.getData(TransientSessionConstants.NONMON_ISSUE_TYPE_HANDLER);
			MstIssueMaster mstIssueMasterData = Issue.getIssueMasterData(issueCategory,"%");
			if(mstIssueMasterData!=null) {
				objAssembly.addData(TransientSessionConstants.NONMON_ISSUE_TYPE_HANDLER,mstIssueMasterData.getIssueType());
			}
			return objAssembly;
		}
		
		// this method is used to determine the decions of BOR depending upon the majority of decisions
		
		@Override
		public IObjectAssembly  decideBorDecisiononMajorityBasis(IObjectAssembly objAssembly) throws BaseApplicationException, CloneNotSupportedException {
			List  decisionList = new ArrayList();
			 decisionList = (List)objAssembly.getBeanList(BORDecisionBean.class);

			int affirmCount = 0;
			int reverseCount = 0;
			int remandforInfoCount = 0;
			int remandforNewDecisionCount = 0;
			int modifyCount= 0;
			int recuseCount = 0;
			int affirmWithEditCount = 0;
			int dismissCount = 0;
			int remandOnMeritCount = 0;
			ArrayList decisionLists= new ArrayList();
			String boardOfReviewDecision = null;
			// this contains the decision desc
			String boardOfReviewDecisionValue = null;
			// this contains the decision value

			if(decisionList != null && decisionList.size() != 0){
				for (Iterator i = decisionList.iterator(); i.hasNext(); ) {
					BORDecisionBean borDecisionBean = (BORDecisionBean) i.next();
					if(BORAppealDecisionEnum.AFFIRM.getName().equals(borDecisionBean.getDecision())){
						affirmCount = affirmCount + 1;
					}else if(BORAppealDecisionEnum.REVERSE.getName().equals(borDecisionBean.getDecision())){
						reverseCount = reverseCount + 1;
					}else if(BORAppealDecisionEnum.REMAND_FOR_INFORMATION.getName().equals(borDecisionBean.getDecision())){
						remandforInfoCount = remandforInfoCount + 1;
					}else if(BORAppealDecisionEnum.REMAND_FOR_NEW_DECISION.getName().equals(borDecisionBean.getDecision())){
						remandforNewDecisionCount = remandforNewDecisionCount + 1;
					}else if(BORAppealDecisionEnum.MODIFY.getName().equals(borDecisionBean.getDecision())){
						modifyCount = modifyCount + 1;
					}else if(BORAppealDecisionEnum.RECUSE.getName().equals(borDecisionBean.getDecision())){
						recuseCount = recuseCount + 1;
					}else if(BORAppealDecisionEnum.AFFIRM_WITH_EDIT.getName().equals(borDecisionBean.getDecision())){
						affirmWithEditCount = affirmWithEditCount + 1;
					}else if(BORAppealDecisionEnum.REMAND_ON_MERIT.getName().equals(borDecisionBean.getDecision())){
						remandOnMeritCount = remandOnMeritCount + 1;
					}/*CQ 4797 else if(BORAppealDecisionEnum.DISMISS.getName().equals(borDecisionBean.getDecision())){
						dismissCount = dismissCount + 1;
					}*/
				}
			}

			AppealDecisionCompareBean appealDecisionReverseBean = new AppealDecisionCompareBean();
			appealDecisionReverseBean.setDecision(BORAppealDecisionEnum.REVERSE.getDescription());
			appealDecisionReverseBean.setDecisionValue(BORAppealDecisionEnum.REVERSE.getName());
			appealDecisionReverseBean.setDecisionCount(reverseCount);
			decisionLists.add(appealDecisionReverseBean);

			AppealDecisionCompareBean appealDecisionAffirmBean = new AppealDecisionCompareBean();
			appealDecisionAffirmBean.setDecision(BORAppealDecisionEnum.AFFIRM.getDescription());
			appealDecisionAffirmBean.setDecisionValue(BORAppealDecisionEnum.AFFIRM.getName());
			appealDecisionAffirmBean.setDecisionCount(affirmCount);
			decisionLists.add(appealDecisionAffirmBean);

			AppealDecisionCompareBean appealDecisionRemandForInfoBean = new AppealDecisionCompareBean();
			appealDecisionRemandForInfoBean.setDecision(BORAppealDecisionEnum.REMAND_FOR_INFORMATION.getDescription());
			appealDecisionRemandForInfoBean.setDecisionValue(BORAppealDecisionEnum.REMAND_FOR_INFORMATION.getName());
			appealDecisionRemandForInfoBean.setDecisionCount(remandforInfoCount);
			decisionLists.add(appealDecisionRemandForInfoBean);

			AppealDecisionCompareBean appealDecisionRemandForNewDecisionBean = new AppealDecisionCompareBean();
			appealDecisionRemandForNewDecisionBean.setDecision(BORAppealDecisionEnum.REMAND_FOR_NEW_DECISION.getDescription());
			appealDecisionRemandForNewDecisionBean.setDecisionValue(BORAppealDecisionEnum.REMAND_FOR_NEW_DECISION.getName());
			appealDecisionRemandForNewDecisionBean.setDecisionCount(remandforNewDecisionCount);
			decisionLists.add(appealDecisionRemandForNewDecisionBean);

			AppealDecisionCompareBean appealDecisionModifyBean = new AppealDecisionCompareBean();
			appealDecisionModifyBean.setDecision(BORAppealDecisionEnum.MODIFY.getDescription());
			appealDecisionModifyBean.setDecisionValue(BORAppealDecisionEnum.MODIFY.getName());
			appealDecisionModifyBean.setDecisionCount(modifyCount);
			decisionLists.add(appealDecisionModifyBean);

			AppealDecisionCompareBean appealDecisionRecuseBean = new AppealDecisionCompareBean();
			appealDecisionRecuseBean.setDecision(BORAppealDecisionEnum.RECUSE.getDescription());
			appealDecisionRecuseBean.setDecisionValue(BORAppealDecisionEnum.RECUSE.getName());
			appealDecisionRecuseBean.setDecisionCount(recuseCount);
			decisionLists.add(appealDecisionRecuseBean);
			
			AppealDecisionCompareBean appealDecisionAffirmEditBean = new AppealDecisionCompareBean();
			appealDecisionAffirmEditBean.setDecision(BORAppealDecisionEnum.AFFIRM_WITH_EDIT.getDescription());
			appealDecisionAffirmEditBean.setDecisionValue(BORAppealDecisionEnum.AFFIRM_WITH_EDIT.getName());
			appealDecisionAffirmEditBean.setDecisionCount(affirmWithEditCount);
			decisionLists.add(appealDecisionAffirmEditBean);
			
			AppealDecisionCompareBean appealDecisionRemandOnMeritEditBean = new AppealDecisionCompareBean();
			appealDecisionRemandOnMeritEditBean.setDecision(BORAppealDecisionEnum.REMAND_ON_MERIT.getDescription());
			appealDecisionRemandOnMeritEditBean.setDecisionValue(BORAppealDecisionEnum.REMAND_ON_MERIT.getName());
			appealDecisionRemandOnMeritEditBean.setDecisionCount(remandOnMeritCount);
			decisionLists.add(appealDecisionRemandOnMeritEditBean);
			
			/*CQ 4797
			AppealDecisionCompareBean appealDecisionDismissBean = new AppealDecisionCompareBean();
			appealDecisionDismissBean.setDecision(BORAppealDecisionEnum.DISMISS.getDescription());
			appealDecisionDismissBean.setDecisionValue(BORAppealDecisionEnum.DISMISS.getName());
			appealDecisionDismissBean.setDecisionCount(dismissCount);
			decisionLists.add(appealDecisionDismissBean);
			*/

			Collections.sort(decisionLists);
			// checking for decison count
			if(decisionLists != null && decisionLists.size() >= 2){
				AppealDecisionCompareBean cmprBean1 = (AppealDecisionCompareBean)decisionLists.get(0);
				AppealDecisionCompareBean cmprBean2 = (AppealDecisionCompareBean)decisionLists.get(1);
				if(cmprBean1.getDecisionCount() == cmprBean2.getDecisionCount() ){
					//objAssembly.removeComponent(BORRecommendationData.class);
					throw new BaseApplicationException("error.access.app.bor.recommendation");
				}else{
					boardOfReviewDecision = cmprBean1.getDecision();
					boardOfReviewDecisionValue = cmprBean1.getDecisionValue();
				}
			}
			if(decisionLists != null && decisionLists.size() == 1){
				AppealDecisionCompareBean cmprBean1 = (AppealDecisionCompareBean)decisionLists.get(0);
				boardOfReviewDecision = cmprBean1.getDecision();
				boardOfReviewDecisionValue = cmprBean1.getDecisionValue();
			}

           // adding the decion taken to the object assembly
			AppealInfoBean appealInfoBean =(AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
		    objAssembly.addData(AppealConstants.BOR_REVIEW_DECISION_DESC,boardOfReviewDecision);
		    objAssembly.addData(AppealConstants.BOR_REVIEW_DECISION_VALUE,boardOfReviewDecisionValue);
		    if (appealInfoBean!=null){
		    	if (appealInfoBean.getEmployerName()!=null){
		    		objAssembly.addData(AppealConstants.REMAND_INFO_PARTY_NAME,appealInfoBean.getEmployerName());
		    	}else if (appealInfoBean.getClaimantName()!=null){
		    		objAssembly.addData(AppealConstants.REMAND_INFO_PARTY_NAME,appealInfoBean.getClaimantName());
		    	}
		    }
		    /*
		     * This will delete all the existing bor recommendation attached to the appeal
		     * Purpose of doing this is people who r not there in the meeting those recommendation should
		     * be deleted so that it does not appear in the bor meeting workitem screen
		     * */
		    AppealData appealData = (AppealData)objAssembly.getFirstComponent(AppealData.class, true);
		    List borRecommendationDataList = objAssembly.getComponentList(BORRecommendationData.class);
		    
		    //Recommendation should be deleted only if the final decision is a Affirm
//		    if(AppealDecisionCodeEnum.AFFIRM.equals(boardOfReviewDecisionValue)){
			Set recommendationSet =  appealData.getBorRecommendationSet();
			Iterator borRecoSetIt = recommendationSet.iterator();
			List borRecoListForDelete = new ArrayList();
			// List listOfRecoFsUserIds = (List) objAssembly.getData("listOfRecoFsUserIds");
			
			while(borRecoSetIt.hasNext()){
				boolean meetingAttendedFlag = false;
			    BORRecommendationData borRecoData = (BORRecommendationData) borRecoSetIt.next();
			    
				for (int i=0;i<borRecommendationDataList.size();i++){
					BORRecommendationData borRecoDataTobeDeleted = (BORRecommendationData) borRecommendationDataList.get(i);
				    if (borRecoDataTobeDeleted.getFsUserData().getMdesUserId().equals(borRecoData.getFsUserData().getMdesUserId())){
				    	meetingAttendedFlag = true;
				    	/*
				    	 * All the recommendations for Affirm needs to be deleted except the one given as recuse.
				    	 */
				    	if(!AppealDecisionCodeEnum.AFFIRM.getName().equals(boardOfReviewDecisionValue)
				    			|| BORAppealDecisionEnum.RECUSE.getName().equals(borRecoData.getBorRecommenation())){
				    		borRecoDataTobeDeleted.setRemoveRecoYesOrNo(GlobalConstants.DB_ANSWER_NO);
				    		break;
				    	}
				    }
				}
			    
			    if (!meetingAttendedFlag){
			    	borRecoListForDelete.add(borRecoData);
			    }
//			    }
			    BorRecommendation.deleteRecommendation(borRecoListForDelete);
		    }
		    /*
		     * In BOR Meeting if decision taken is affirm then save decision here and remove recommendation for
		     * the same,out of majority if any decision is recuse then do not delete that recommendation
		     * if no recommeddation is there for the same,then insert one
		     * if decision taken is affirm with edit then create work item for the same,insert recommendation
		     * for the same if no reco is there
		     * for any other decision create work item either insert recommendation or do not delete
		     * individual recommendation
		     * */
		    if (BORAppealDecisionEnum.AFFIRM.getName().equals(boardOfReviewDecisionValue)){
		    	//AppealData appealData = (AppealData)objAssembly.getFirstComponent(AppealData.class, true);
		    	appealData.getBorRecommendationSet().clear();
		    	//Appeal appealBO = Appeal.getAppealBoByAppealData(appealData);
		    	List borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
		    	BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
		    	// in case of affirm ReverseDecisionBean will be having affirm decision
		    	// on check of this affirm decision correspondence and decision letter will be issued in BO
		    	ReverseDecisionBean bean = new ReverseDecisionBean();
		    	bean.setDecision(boardOfReviewDecisionValue);
		    	
		    	prepareReverseDecisionBeanForDecisionLetter(objAssembly, bean);
		    	
		    	// last parameter is additional remaks,from BOR meeting additional remarks is not there
		    	// so blank quotes are being set
		    	if (appealData instanceof NonMonAppealData){
		    		NonMonAppeal nonMonAppealBO = new NonMonAppeal(appealData);
		    		// for affirm decision straight way last 2 parameters will be blank
		    		nonMonAppealBO.saveBORAffirmDecision("",bean);
		    	}else if (appealData instanceof MiscAppealData){
		    		MiscAppeal miscAppealBO = new MiscAppeal(appealData);
		    		miscAppealBO.saveAffirmDecisionForBor("",bean);
		    	}else if (appealData instanceof TaxAppealData){
		    		TaxAppeal taxAppealBO = new TaxAppeal(appealData);
		    		taxAppealBO.saveBORAffirmDecision("",bean);
		    	}else if (appealData instanceof LateDeniedAppealData){
		    		LateDeniedAppeal lateDeniedBO = new LateDeniedAppeal((LateDeniedAppealData)appealData);
		    		//lateDeniedBO.saveAffirmDecisionForBor(borMeetingData,"");
		    	}
          } else if (BORAppealDecisionEnum.REMAND_ON_MERIT.getName().equals(boardOfReviewDecisionValue)){
		    	appealData.getBorRecommendationSet().clear();
		    	
		    	ReverseDecisionBean bean = new ReverseDecisionBean();
		    	bean.setDecision(boardOfReviewDecisionValue);
		    	
		    	prepareReverseDecisionBeanForDecisionLetter(objAssembly, bean);
		    	
		    	// last parameter is additional remaks,from BOR meeting additional remarks is not there
		    	// so blank quotes are being set
		    	if (appealData instanceof NonMonAppealData){
		    		NonMonAppeal nonMonAppealBO = new NonMonAppeal(appealData);
		    		// for affirm decision straight way last 2 parameters will be blank
		    		nonMonAppealBO.saveBORRemandForHearingAndDecision("",bean);
		    	}else if (appealData instanceof MiscAppealData){
		    		MiscAppeal miscAppealBO = new MiscAppeal(appealData);
		    		miscAppealBO.saveBORRemandForHearingAndDecision("",bean);
		    	}else if (appealData instanceof TaxAppealData){
		    		TaxAppeal taxAppealBO = new TaxAppeal(appealData);
		    		taxAppealBO.saveBORRemandForHearingAndDecision("",bean);
		    	}else if (appealData instanceof LateDeniedAppealData){
		    		LateDeniedAppeal lateDeniedBO = new LateDeniedAppeal((LateDeniedAppealData)appealData);
		    		//lateDeniedBO.saveAffirmDecisionForBor(borMeetingData,"");
		    	}
        }else if (BORAppealDecisionEnum.RECUSE.getName().equals(boardOfReviewDecisionValue)){
       	     throw new BaseApplicationException("error.access.app.bor.recommendationasrecuse");
          }else{
        	  // for any other decision code  create work item
		    	String selectMemberForWorkItem = (String)objAssembly.getData("selectMemberForWorkItem");
		    	if (StringUtility.isNotBlank(selectMemberForWorkItem)){
		    		/*
			    	 * From this work item appeal info screen will open up with all the members dedcision
			    	 * as disabled.On submit of that decision will be saved and work item will be closed
			    	 * */
			    	//AppealData appealData = (AppealData)objAssembly.getFirstComponent(AppealData.class, true);
			    	//AppealInfoBean appealInfoBean =(AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
			    	String ssnOrEan = null;
			    	String name = null;
			    	if (AppellantOrOpponentEnum.CLAIMANT.getName().equals(appealInfoBean.getAppellantValue())){
			    		if (StringUtility.isNotBlank(appealInfoBean.getClaimantSsn())){
			    			ssnOrEan = appealInfoBean.getClaimantSsn();
			    			name=appealInfoBean.getClaimantName();
			    		}
			    	}else if (AppellantOrOpponentEnum.EMPLOYER.getName().equals(appealInfoBean.getAppellantValue())){
			    		if(StringUtility.isNotBlank(appealInfoBean.getEmployerEan())){
			    			ssnOrEan = appealInfoBean.getEmployerEan();
			    			name = appealInfoBean.getEmployerName();
			    		} else {
			    			//CQ 6202 must put some value
			    			if(StringUtility.isNotBlank(appealInfoBean.getEmployerName())){
				    			name = appealInfoBean.getEmployerName();
				    		} else if(StringUtility.isNotBlank(appealInfoBean.getHeaderEmployerName())) {
				    			name = appealInfoBean.getHeaderEmployerName();
				    		}
			    		}
			    	}
//				    if (StringUtility.isNotBlank(appealInfoBean.getClaimantSsn())){
//				    	ssnOrEan = appealInfoBean.getClaimantSsn();
//				    }else if(StringUtility.isNotBlank(appealInfoBean.getEmployerEan())){
//				    	ssnOrEan = appealInfoBean.getEmployerEan();
//				    }
			    	appealData.setBorRecommendationSet(null);
			    	appealData.setAppealStatus(AppealStatusEnum.REVIEWED.getName());
			    	// it is reviewed now,to be closed after resolving work item
			    	Appeal appealBO =  Appeal.getAppealBoByAppealData(appealData);
					appealBO.saveOrUpdate();
			    	// this will get the user to whom work item to be assigned
			    	FSUserData fsUserDataObj = FSUser.getFSUserByUserId((String) objAssembly.getData("selectMemberForWorkItem"));
			    	IBaseWorkflowDAO workflowItem = DAOFactory.instance.getBaseWorkflowDAO();
					BaseAccessDsContainerBean obj = new BaseAccessDsContainerBean();
					GlobalDsContainerBean contBean = obj.getNewGlobalDsContainerBean();
					contBean.setBusinessKey(String.valueOf(appealData.getAppealId()));
					if(ssnOrEan != null){
						contBean.setSsneanfein(ssnOrEan);
					}else {
						//CQ 6202 must put some value, it crashes if null
						contBean.setSsneanfein("N/A");
					}
					if (AppellantOrOpponentEnum.CLAIMANT.getName().equals(appealInfoBean.getAppellantValue())){
						contBean.setTypeAsSsn();
					}else if (AppellantOrOpponentEnum.EMPLOYER.getName().equals(appealInfoBean.getAppellantValue())){
						contBean.setTypeAsEan();
					}
					
//					contBean.setName(StringUtility.getFullName(fsUserDataObj.getFirstName(),fsUserDataObj.getLastName()));
					contBean.setName(name);
					obj.setUserId(fsUserDataObj.getUserId());
					workflowItem.createAndStartProcessInstance(WorkflowProcessTemplateConstants.AppealWorkflowConstants.BOR_MEETING, obj);
					
					GenericWorkflowSearchBean bean = (GenericWorkflowSearchBean)objAssembly.fetchORCreateBean(GenericWorkflowSearchBean.class);
					bean.setGlobalContainerMemberLike("ssneanfein", ssnOrEan);
					bean.setGlobalContainerMember("type", "ssn");
					bean.setReceivedTimeBetween(new Date(), DateUtility.getNextDay(new Date()));
					//String userId = fsUserDataObj.getUserId();
					
				    //FSUserData fsUserDataObj = FSUser.getFSUserByUserId(userId);
//					List listOfworkItems = Appeal.listOfworkItems(bean);
//					for (int i=0;i<listOfworkItems.size();i++){
//						WorkflowItemBean workFlowItemBean =  (WorkflowItemBean) listOfworkItems.get(i);
//						RegularClaim.reassignWorkItems(workFlowItemBean.getPersistentOid(),userId);
//					}
		    	}else{
		    		throw new BaseApplicationException("error.access.app.bor.borusernull");
		    	}
		    }
		    
           // for affirm if reco is there then remove the same,if recuse either insert or keep reco as it is
		    		    
		    if (borRecommendationDataList!=null && borRecommendationDataList.size()>0
		    		&& borRecommendationDataList.size()> borRecoListForDelete.size()){

		         BorRecommendation.saveOrUpdateOrDelete(borRecommendationDataList);
		    }
		    
		    /*Loading the previous decision data object*/
		    //Appeal appealBO = Appeal.findByPrimaryKey(Long.valueOf(objAssembly.getPrimaryKey().toString()));
		    //List list = (List) Appeal.fetchAppealDecisionDetail(appealBO.getAppealData().getAppealledAgainstDecision().getDecisionID());
		    //AppealDecisionDetailData appealDecisionDetailData = (AppealDecisionDetailData)list.get(0);
		    //Decision decisionBO = Decision.getDecisionAndMstIssueMasterDataByDecisionId(appealBO.getAppealData().getAppealledAgainstDecision().getDecisionID());
		   // objAssembly.addComponent(appealDecisionDetailData.getDecisonData(),true);
		    //List appealBOforAppealDecisionDetailList = Appeal.fetchAppealDecisionDetail(appealBO.getAppealData().getAppealledAgainstDecision().getDecisionID());
		   // objAssembly.addComponent(appealDecisionDetailData,true);
		    ///////////////////////////////////////////////////////////////////////////////////////
		    /*on the basis of remove recommendation as Yes or No recommendation will be inserted or updated
		     *
		     * If there is no recommendation and Remove Recommendation is selected as Yes then no Insert or update
		     * else if
		     * there is recommendation and Remove Recommendation is selected as Yes then that recommendation is to be deleted
		     * else if
		     * If there is no recommendation and Remove Recommendation is selected as No then new recommendation to be inserted
		     * else if
		     * there is recommendation and Remove Recommendation is selected as No then that recommendation is to be updated
		     * */
		    /*List borRecommendationDataList = objAssembly.getComponentList(BORRecommendationData.class);
		    if (borRecommendationDataList!=null && borRecommendationDataList.size()>0){

		         BorRecommendation.saveOrUpdateOrDelete(borRecommendationDataList);
		    }*/

			// adding the decion taken to the object assembly
		    //objAssembly.addData("boardOfReviewDecision",boardOfReviewDecision);
			return objAssembly;
		}
		
		private void prepareReverseDecisionBeanForDecisionLetter(IObjectAssembly objAssembly, ReverseDecisionBean bean){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method prepareReverseDecisionBeanForDecisionLetter");
		}
//			getBORDecisionLetterReportParams(objAssembly);
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			AppealData appealData = (AppealData)objAssembly.getFirstComponent(AppealData.class, true);
			/*CIF_02727 || DEFECT_3847|| Commented as not required*/
			/*AppealsDecisionLettersParagraphBuilder builder = new AppealsDecisionLettersParagraphBuilder();
			builder.addParam(AppealConstants.BOR_APPEAL_FILE_DATE,DateFormatUtility.format(appealData.getAppealDate()));
			builder.addParam(AppealConstants.BOR_APPEAL_FILE_DUE_DATE, AppealConstants.DEFAULT_PARAM);
			builder.addParam(AppealConstants.ALJ_DECISION_DATE,DateFormatUtility.format(appealData.getAppealledAgainstDecision().getDecisionGivenDate()));
			builder.addParam(AppealConstants.BOR_REVIEW_DATE,DateFormatUtility.format(borMeetingData.getReviewDate()));*/
			
			String docketType = (String)objAssembly.getData("searchDocketType");
			if(StringUtility.isBlank(docketType)){
				docketType = (String)objAssembly.getData("docketType");
			}
			String decision=bean.getDecision();
//			if (MasterDocketTypeEnum.CONTINUED.getName().equalsIgnoreCase(appealData.getAppealType())){
//				docketType = appealData.getAppealType();
//			}
			// only in case of continued appeal status get appeal type ,
			// since there is no decision letter templete with docket type as COND
			 if (MasterDocketTypeEnum.CONTINUED.getName().equalsIgnoreCase(appealData.getAppealStatus())){
				    
				    docketType = appealData.getAppealType();
				    if(AppealTypeEnum.NON_MON.getName().equalsIgnoreCase(appealData.getAppealType())){
				     docketType = MasterDocketTypeEnum.REGULAR.getName();
				    }else if (AppealTypeEnum.MISC.getName().equalsIgnoreCase(appealData.getAppealType())){
				     docketType = MasterDocketTypeEnum.MISC.getName();
				    }else if (AppealTypeEnum.LATE.getName().equalsIgnoreCase(appealData.getAppealType())){
				     docketType = MasterDocketTypeEnum.LATE_BOR.getName();
				    }else if (AppealTypeEnum.LDAD.getName().equalsIgnoreCase(appealData.getAppealType())){
				     docketType = MasterDocketTypeEnum.LATE_ALJ.getName();
				    
				    /*CIF_00393,Desc:Commented TAXA and TAX_INT from the Master Docket Type
				    else if (AppealTypeEnum.TAXA.getName().equalsIgnoreCase(appealData.getAppealType())){
				     docketType = MasterDocketTypeEnum.TAX.getName();
				    }else if (AppealTypeEnum.TAX_INT.getName().equalsIgnoreCase(appealData.getAppealType())){
				     docketType = MasterDocketTypeEnum.TAXI.getName();
				    }*/
				    }else if (AppealTypeEnum.NOAP.getName().equalsIgnoreCase(appealData.getAppealType())){
				     docketType = MasterDocketTypeEnum.NO_APPEARANCE.getName();
				    }
			 }
			/*MstAppDecMaster appealDecisionMaster = Appeal
			.fetchAppealDecisionMasterByDocketTypeAndDecision(docketType, decision);
			
	    	bean.setCaseHistoryParagraph(getArrayListToString(builder.buildCaseHistoryParagraph(appealDecisionMaster.getAppDecMasterId())));
			bean.setReasonAndConclusionParagraph(getArrayListToString(builder.buildDecisionReasonParagraph(appealDecisionMaster.getAppDecMasterId())));
			bean.setFactFindingParagraph(getArrayListToString(builder.buildFactFindingParagraph(appealDecisionMaster.getAppDecMasterId())));
			bean.setIssueParagraph(getArrayListToString(builder.buildIssueParagraph(appealDecisionMaster.getAppDecMasterId())));
			bean.setAppealRightsParagraph(getArrayListToString(builder.buildAppealRightsParagraph(appealDecisionMaster.getAppDecMasterId())));
			bean.setDecisionReasonParagraph(getArrayListToString(builder.buildDecisionParagraph(appealDecisionMaster.getAppDecMasterId())));*/
			
			//CQ - 10200.  If if it a tax status appeal, the appeal rights paragraph will change.
			if(AppealTypeEnum.TAXA.getName().equalsIgnoreCase(appealData.getAppealType())){
				TaxAppealData taxData = (TaxAppealData)appealData;
				if(TaxAppealIssueEnum.LIABILITY_RELATIONSHIP.getName().equals(taxData.getTaxAppealIssueCode())){
					bean.setAppealRightsParagraph("3");
					//3 will tell the JReport to pull the correct correspondence for this scenerio
				}
				
			}
		}
		/**
		 * @param list refers to ArrayList
		 * @return String
		 */
		private String getArrayListToString(ArrayList list){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getArrayListToString");
		}
			String sepIdentifier=AppealConstants.DECISION_LETTER_PARA_SEP;
			String listString="";
			if(list!=null && !list.isEmpty()){
			Iterator t=list.iterator();
			while(t.hasNext()){
				listString += ((MessageHolderBean)t.next()).getMessage();
				if(t.hasNext()) {
					listString +=sepIdentifier;
				}
			}
			}
			return listString;
			
		}
		
		//		 this method is used to save bor Remand decision
		@Override
		public IObjectAssembly saveBORRemandForInfoDecision(IObjectAssembly objAssembly) throws CloneNotSupportedException, BaseApplicationException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveBORRemandForInfoDecision");
		}
			List borMemberList = new ArrayList();
			//get the NonMonData from the object assembly
	        NonMonAppealData nonMonAppealData = (NonMonAppealData)objAssembly.getFirstComponent(NonMonAppealData.class);
	        //get the taxappeal data from the obj assembly
	        TaxAppealData taxAppealData = (TaxAppealData)objAssembly.getFirstComponent(TaxAppealData.class);
	        MiscAppealData miscAppealData = (MiscAppealData)objAssembly.getFirstComponent(MiscAppealData.class);
	        
	        LateAppealData lateAppealData = (LateAppealData)objAssembly.getFirstComponent(LateAppealData.class);
	        
	        NonAppearanceAppealData nonAppearanceAppealData = (NonAppearanceAppealData)objAssembly.getFirstComponent(NonAppearanceAppealData.class);
	        //get the reversedecision bean from the object assembly;
	        RemandForInfoBean boardOfReviewRemandForInfoBean = (RemandForInfoBean)objAssembly.getFirstBean(RemandForInfoBean.class);
	        //get the bor meeting data
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			
			String remandType = (String)objAssembly.getData(AppealConstants.BOR_REVIEW_DECISION_VALUE);			
			
			boardOfReviewRemandForInfoBean.setRemandDecision(remandType);
			
			if(remandType.equals(AppealDecisionCodeEnum.REMI.getName())){
				boardOfReviewRemandForInfoBean.setRemandType(AppealRemandedForEnum.INFO.getName());
			}else{
				boardOfReviewRemandForInfoBean.setRemandType(AppealRemandedForEnum.DECN.getName());
			}
			boolean isTaxAppeal=Boolean.FALSE;
			
			AppealData appealData = null;
			DecisionData newDecisionData = null;
			if(nonMonAppealData != null){
				appealData = nonMonAppealData;
	        	// get the BOR decision list frm the object assembly
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	nonMonAppealData.setBorRecommendationSet(null);
	        	// create nonMonBO
	        	NonMonAppeal nonMonAppealBO = new NonMonAppeal(nonMonAppealData);
	        	// call the business method for saving remand decison
	        	newDecisionData = nonMonAppealBO.saveBORRemandForInfoDecision(borMemberList,boardOfReviewRemandForInfoBean,borMeetingData);
	        	
	        }else if(taxAppealData != null){
	        	appealData = taxAppealData;
	        	//get the BOR decision list from the object assembly
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	taxAppealData.setBorRecommendationSet(null);
	        	// create a new taxappealBO
	        	TaxAppeal taxAppealBO = new TaxAppeal(taxAppealData);
	        	//call the business method for saving remand decison
	        	newDecisionData = taxAppealBO.saveBORRemandForInfoDecision(borMemberList,boardOfReviewRemandForInfoBean,borMeetingData);
	        	isTaxAppeal=Boolean.TRUE;
	        	
	        }else if(miscAppealData != null){
	        	appealData = miscAppealData;
                borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
                miscAppealData.setBorRecommendationSet(null);
	        	MiscAppeal  miscAppealBO = new MiscAppeal(miscAppealData);
	        	newDecisionData = miscAppealBO.saveRemandForInfoDecisionForBor(borMeetingData,boardOfReviewRemandForInfoBean);
	        }else if(lateAppealData != null){
	        	appealData = lateAppealData;
                borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
                lateAppealData.setBorRecommendationSet(null);
	        	LateAppeal lateAppealBO = new LateAppeal(lateAppealData);
	        	newDecisionData = lateAppealBO.saveRemandForInfoDecisionForBor(borMeetingData,boardOfReviewRemandForInfoBean);
	        	if(null != lateAppealData.getParentAppealData() && AppealTypeEnum.TAXA.getName().equals(lateAppealData.getParentAppealData()))
				{
					isTaxAppeal=Boolean.TRUE;
				}
	        }else if(nonAppearanceAppealData != null){
	        	appealData = nonAppearanceAppealData;
                borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
                nonAppearanceAppealData.setBorRecommendationSet(null);
                NonApperanceAppeal nonApperanceAppealBO = new NonApperanceAppeal(nonAppearanceAppealData);
	        	newDecisionData = nonApperanceAppealBO.saveRemandForInfoDecisionForBor(borMeetingData,boardOfReviewRemandForInfoBean);
	        	isTaxAppeal=Boolean.FALSE;
	        }
			
			//CQ 3666 Generate the correspondence
			
			Appeal appealBO = Appeal.getAppealBoByAppealData(appealData);
			//because this is a Remand for Information, decision is null
			if(appealData!=null)
			{
				AppealInfoBean appealInfoBean = (AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
				String lircDecisionMailDate="";
				if(appealInfoBean.getDecisionMailDate()!=null)
				{
					lircDecisionMailDate=DateFormatUtility.format(appealInfoBean.getDecisionMailDate());
					appealData.setLircDecisionMailDate(lircDecisionMailDate);
				}
			}
			//DecisionData activeDecisionDataGivenForAppeal = Decision.getActiveDecisionDataByAppealId(appealData.getAppealId());
			//R4UAT00012464
			if (!isTaxAppeal)
            {
				appealBO.generateCorrespondence(appealData,newDecisionData,CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
			}else{
				appealBO.generateCorrespondence(appealData,newDecisionData,CorrespondenceCodeEnum.BOR_EMPLOYER_DECISION_LETTER.getName());
					
			}
			  
			//CIF_02060	|| Defect_2827|| Correspondences for MODES-4680, MODES-4680-3, MODES-4680-5 to be generated.
             
			  
			  /*
			   * commented as of CIF_02333
			   * appealBO.generateCorrespondence(appealData,null,CorrespondenceCodeEnum.MODES_4680_5.getName());*/
	         Appeal.generateCorr4680_5(appealData);
			
			/*this part will save the recommendation data*/
	        List borRecommendationDataList = objAssembly.getComponentList(BORRecommendationData.class);
		    if (borRecommendationDataList!=null && borRecommendationDataList.size()>0){

		        BorRecommendation.saveOrUpdateOrDeleteRecommendationOnSaveDecision(borRecommendationDataList);
		    }
		    
		    UploadDocumentService docService=new UploadDocumentService();
			docService.uploadLircDecisionLetterinFileServer(objAssembly, appealData, newDecisionData);
					    
		    
	        //closing the work item
		    /*Removing workitems as for Remand,no work items are used. */
	       /*  WorkflowItemBean workflowItemBean = (WorkflowItemBean) objAssembly.getFirstBean(WorkflowItemBean.class);*/
		   /* IBaseWorkflowDAO baseWorkflowDAO = DAOFactory.instance.getBaseWorkflowDAO();
		    baseWorkflowDAO.startWorkItem(workflowItemBean.getPersistentOid());*/
	        
	        // CIF_00982|| Code change w.r.t new BRMS approach
	       /* Map<String , Object> mapValues = new HashMap<String , Object>();
            
            mapValues.put("persistenceOid",workflowItemBean.getPersistentOid());

            WorkflowTransactionService wf = new WorkflowTransactionService();
                       
            wf.invokeWorkFlowOperation(WorkFlowOperationsEnum.Start.getName(), mapValues);  */
            
	        return objAssembly;
		}
		
		//	this method is used to save save customized decision
		@Override
		public IObjectAssembly saveCustomizeDecision(IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveCustomizeDecision");
		}
			// get the appealinfo bean from the object assembly
			AppealInfoBean appealInfoBean =(AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
			// get appeal info
			//Appeal appealBO = Appeal.findByPrimaryKey(Long.valueOf(appealInfoBean.getAppealId()));
			// if instance of tax
			if (AppealTypeEnum.TAXA.getName().equalsIgnoreCase(appealInfoBean.getAppealType())){
				// get the decision data
				TaxAppealData taxAppealData = (TaxAppealData)objAssembly.getFirstComponent(TaxAppealData.class);
				//TaxAppealData taxAppealData = (TaxAppealData)appealBO.getAppealData();
				if(taxAppealData.getDecisions() != null){
					for (Iterator it = taxAppealData.getDecisions().iterator(); it.hasNext(); ) {
						//	get the appealparty data
						DecisionData decisionData = (DecisionData)it.next();
						if(decisionData.getActiveFlag().equals(GlobalConstants.DB_ANSWER_YES) && decisionData.getDecisionStatus().equals(DecisionStatusEnum.BOARD_OF_REVIEW.getName())){
							decisionData.getAppealDecisionDetailData().setCustomizedDecision(appealInfoBean.getCustomizeDecision());
						}
					}
				}
				//OtherAppealDecisionData otherAppealDecisionData = OtherAppealDecision.getOtherAppealByPkId(appealInfoBean.getActiveOtherDecision());
				// set the customized decision
				//otherAppealDecisionData.setCustomizedDecision(appealInfoBean.getCustomizeDecision());
				// save the decision
				TaxAppeal taxAppealBo = new TaxAppeal(taxAppealData);
				//OtherAppealDecision otherAppealDecision = new  OtherAppealDecision(otherAppealDecisionData);
				taxAppealBo.saveOrUpdate();
			}else if(AppealTypeEnum.NON_MON.getName().equalsIgnoreCase(appealInfoBean.getAppealType())){
				// becoz of lazy initialization problem we have to fetch the data one more time
				NonMonAppealData nonMonAppealData =(NonMonAppealData)objAssembly.getFirstComponent(NonMonAppealData.class);
				if(nonMonAppealData.getDecisions() != null){
					for (Iterator it = nonMonAppealData.getDecisions().iterator(); it.hasNext(); ) {
						//	get the appealparty data
						DecisionData decisionData = (DecisionData)it.next();
						if(decisionData.getActiveFlag().equals(GlobalConstants.DB_ANSWER_YES) && decisionData.getDecisionStatus().equals(DecisionStatusEnum.BOARD_OF_REVIEW.getName())){
							decisionData.getAppealDecisionDetailData().setCustomizedDecision(appealInfoBean.getCustomizeDecision());
						}
					}
				}
				NonMonAppeal nonMonAppealBO = new NonMonAppeal(nonMonAppealData);
				nonMonAppealBO.saveOrUpdate();
			}else if (AppealTypeEnum.MISC.getName().equalsIgnoreCase(appealInfoBean.getAppealType())){
				MiscAppealData miscAppealData =(MiscAppealData)objAssembly.getFirstComponent(MiscAppealData.class);
				if(miscAppealData.getDecisions() != null){
					for (Iterator it = miscAppealData.getDecisions().iterator(); it.hasNext(); ) {
						//	get the appealparty data
						DecisionData decisionData = (DecisionData)it.next();
						if(decisionData.getActiveFlag().equals(GlobalConstants.DB_ANSWER_YES) && decisionData.getDecisionStatus().equals(DecisionStatusEnum.BOARD_OF_REVIEW.getName())){
							decisionData.getAppealDecisionDetailData().setCustomizedDecision(appealInfoBean.getCustomizeDecision());
						}
					}
				}
				MiscAppeal miscAppealBo = new MiscAppeal(miscAppealData);
				miscAppealBo.saveOrUpdate();
				
			}
			if(appealInfoBean.getCloseWorkItem().booleanValue() == true){
				WorkflowItemBean workflowItemBean = (WorkflowItemBean)objAssembly.getFirstBean(WorkflowItemBean.class);
				// call close work item for customization method
				Appeal.closeCustomizationWorkItem(workflowItemBean);
			}
			return objAssembly;
		}
		
		// this method is used to get information for remand for info using work item
		@Override
		public IObjectAssembly getAppealInfoForRemandForInfo(IObjectAssembly objAssembly) throws ParseException, BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getAppealInfoForRemandForInfo");
		}
			AddressBean employerAddress = new AddressBean();
			String pkey = (String)objAssembly.getPrimaryKey();
			//	for  diaplaying  Appeal info
			AppealInfoBean appealInfoBean = new AppealInfoBean();
			// getting the appleal business objet by appealid
			Long appealId = Long.valueOf(pkey);
			Appeal appealBO = Appeal.findByPrimaryKey(appealId);
			Map issueMap=Appeal.getIssueDescriptionAndDecisionDateFromAppealData(appealBO.getAppealData());
			if(issueMap!=null && issueMap.containsKey(AppealConstants.ISSUE_DESCRIPTION)){
				appealInfoBean.setIssueDescriptionDetailsString(String.valueOf(issueMap.get(AppealConstants.ISSUE_DESCRIPTION)));
			}else{
				appealInfoBean.setIssueDescriptionDetailsString(ViewConstants.NOT_APPLICABLE);
			}
			
			Map claimantEmployerInfoMap = AppealUtil.getClaimantAndEmployerInformationByAppealId(appealBO.getAppealData());
			appealInfoBean.setHeaderClaimantName((String)claimantEmployerInfoMap.get(AppealConstants.KEY_CLAIMANT_NAME));
			appealInfoBean.setHeaderClaimantSSN((String)claimantEmployerInfoMap.get(AppealConstants.KEY_CLAIMANT_SSN));
			appealInfoBean.setHeaderEAN((String)claimantEmployerInfoMap.get(AppealConstants.KEY_MDES_EAN));
			appealInfoBean.setHeaderEmployerName((String)claimantEmployerInfoMap.get(AppealConstants.KEY_EMPLOYER_NAME));
			
			if (appealBO.getAppealData() instanceof NonMonAppealData){
				NonMonAppealData nonMonAppealData = (NonMonAppealData)appealBO.getAppealData();
				// get the Nonmon decision ID
				//Long nonMonDecisionId = nonMonAppealData.getNonMonDecisionId();
				
				DecisionData nondecisiondata = nonMonAppealData.getAppealledAgainstDecision();
				ClaimantData claimantData = nondecisiondata.getIssueData().getClaimData().getClaimantData();
				// Employer Data
				EmployerData employerData =nondecisiondata.getIssueData().getFactFindingData().getEmployerData();
				// setting appeal info
				appealInfoBean.setIssueId(nondecisiondata.getIssueData().getIssueId().toString());
				appealInfoBean.setAppealReason(nonMonAppealData.getAppealReason());
				//appealInfoBean.setAppellant((String)CacheUtility.getCachePropertyValue("T_MST_USER_TYPES","key",nonMonAppealData.getAppellant(),"description"));
				appealInfoBean.setAppellant(nonMonAppealData.getAppellant());
				appealInfoBean.setOpponent(nonMonAppealData.getOpponent());
				if(nonMonAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(nonMonAppealData.getInterpreter());
				}else{
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				appealInfoBean.setDocketNumber(nonMonAppealData.getDocketNumber().toString());
				appealInfoBean.setDecisionDescription((String)CacheUtility.getCachePropertyValue("ISSUE_CATEGORY_DISPLAY","key",nondecisiondata.getIssueData().getMstIssueMaster().getIssueCategory(),"description"));
				// setting claimant info
				if(claimantData !=null){
					appealInfoBean.setClaimantName(claimantData.getClaimantFullName());
					appealInfoBean.setClaimantSsn(claimantData.getSsn());
					appealInfoBean.setClaimantTelephone(claimantData.getClaimantProfileData().getPrimaryPhone());
					// setting claimant Address
					appealInfoBean.setClaimantAddressBean(claimantData.getMailingAddress().getAddrData().getAddressBean());
				}
				// setting Employer info
				if(employerData != null){
					appealInfoBean.setEmployerName(employerData.getEmployerName());
					appealInfoBean.setEmployerEan(employerData.getEan());
					
					// getting employer contact details from employer contact table
					EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
					if(employerContactData != null){
						this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
						
					}
				}
				appealInfoBean.setDecision((String)CacheUtility.getCachePropertyValue("T_MST_BOR_RECOMMENDATION","key",nondecisiondata.getDecisionCode(),"description"));
				appealInfoBean.setDecisionStDate(nondecisiondata.getDecisionStartDate().toString());
				//	for getting the remand decisions for the appeal
				Set appealRemandetailsSet = nonMonAppealData.getAppealRemandDetailSet();
				if(appealRemandetailsSet != null){
					Date cmprDate = new Date();
					cmprDate = new SimpleDateFormat("MM/dd/yyyy").parse("12/12/1900");
					for (Iterator it = appealRemandetailsSet.iterator(); it.hasNext(); ) {
						//	get the appealRemandDetailData data
						AppealRemandDetailData appealRemandDetailData = (AppealRemandDetailData)it.next();
						if(cmprDate.before(appealRemandDetailData.getRemandDate())){
							cmprDate = (Date)appealRemandDetailData.getRemandDate().clone();
						}
					}
					for (Iterator it = appealRemandetailsSet.iterator(); it.hasNext(); ) {
						//	get the appealRemandDetailData data
						AppealRemandDetailData appealRemandDetailData = (AppealRemandDetailData)it.next();
						if(cmprDate.equals(appealRemandDetailData.getRemandDate())){
							appealInfoBean.setRemandDate(DateFormatUtility.format(appealRemandDetailData.getRemandDate()));
							appealInfoBean.setRemandReason(appealRemandDetailData.getRemandReason());
							appealInfoBean.setRemandResponse(appealRemandDetailData.getRemandResponse());
							appealInfoBean.setAppealRemandId(appealRemandDetailData.getAppealRemandDetailId());
						}
					}
					
				}
				// adding the bean in the object assembly
				objAssembly.addBean(appealInfoBean,true);
				// adding decision data to the object assembly
				objAssembly.addComponent(nondecisiondata,true);
				//	adding issuedata to object assembly
				objAssembly.addComponent(nonMonAppealData,true);
			}else if(appealBO.getAppealData() instanceof TaxAppealData){
				// typecast with taxappeal data
				TaxAppealData taxAppealData =(TaxAppealData)appealBO.getAppealData();
				// set the docket number
				appealInfoBean.setDocketNumber(taxAppealData.getDocketNumber().toString());
				if(taxAppealData.getInterpreter() != null){
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(taxAppealData.getInterpreter()).getDescription());
				}else{
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				// get the taxappeal issue type
				appealInfoBean.setDecisionDescription(taxAppealData.getTaxAppealIssueCode());
				// get the Appeallant
				appealInfoBean.setAppellant(taxAppealData.getAppellant());
				appealInfoBean.setAppellantValue(taxAppealData.getAppellant());
				// get the employer details
				Set taxAppealPartySet = taxAppealData.getAppealPartySet();
				//Assuming that we have only one Appelant and that is employer
				if(taxAppealPartySet != null){
					for (Iterator it = taxAppealPartySet.iterator(); it.hasNext(); ) {
						// get the appealparty data
						AppealPartyData appealPartyData = (AppealPartyData)it.next();
						if(AppealPartyTypeEnum.APPELLANT.getName().equals(appealPartyData.getPartyType())){
							EmployerData employerData = (EmployerData)appealPartyData.getEmployerData();
							//	setting Employer info
							if(employerData != null){
								appealInfoBean.setEmployerName(employerData.getEmployerName());
								appealInfoBean.setEmployerEan(employerData.getEan());
								// getting employer contact details from employer contact table
								EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
								if(employerContactData != null){
									employerAddress.setAddress1(employerContactData.getAddressLine1());
									employerAddress.setAddress2(employerContactData.getAddressLine2());
									employerAddress.setCity(employerContactData.getCity());
									employerAddress.setCountry(employerContactData.getCountry());
									employerAddress.setZip(employerContactData.getZip());
									employerAddress.setState(employerContactData.getState());
									employerAddress.setPostalBarCode(employerContactData.getBarcode());
									if(employerContactData.getPhone() != null){
										appealInfoBean.setEmployerTelephone(employerContactData.getPhone());
									}else{
										appealInfoBean.setEmployerTelephone(GlobalConstants.NOT_APPLICABLE);
									}
									appealInfoBean.setEmployerAddressBean(employerAddress);
								}//end if of employerContactData
							}//end if of employer data
						}//end  if of appealPartyData.getPartyType()
					}// end for looop
				}// end if taxAppealPartySet
				
				appealInfoBean.setDecisionDescription(taxAppealData.getTaxAppealIssueCode());
				// set reason for appeal
				appealInfoBean.setAppealReason(taxAppealData.getAppealReason());
				// for getting the remand decisions for the appeal
				Set appealRemandetailsSet = taxAppealData.getAppealRemandDetailSet();
				if(appealRemandetailsSet != null){
					Date cmprDate = new Date();
					cmprDate = new SimpleDateFormat("MM/dd/yyyy").parse("12/12/1900");
					for (Iterator it = appealRemandetailsSet.iterator(); it.hasNext(); ) {
						//	get the appealRemandDetailData data
						AppealRemandDetailData appealRemandDetailData = (AppealRemandDetailData)it.next();
						if(cmprDate.before(appealRemandDetailData.getRemandDate())){
							cmprDate = (Date)appealRemandDetailData.getRemandDate().clone();
						}
					}
					for (Iterator it = appealRemandetailsSet.iterator(); it.hasNext(); ) {
						//	get the appealRemandDetailData data
						AppealRemandDetailData appealRemandDetailData = (AppealRemandDetailData)it.next();
						if(cmprDate.equals(appealRemandDetailData.getRemandDate())){
							appealInfoBean.setRemandDate(DateFormatUtility.format(appealRemandDetailData.getRemandDate()));
							appealInfoBean.setRemandReason(appealRemandDetailData.getRemandReason());
							appealInfoBean.setRemandResponse(appealRemandDetailData.getRemandResponse());
							if (RemandReasonEnum.OTHR.getName().equalsIgnoreCase(appealRemandDetailData.getRemandReason())){
								appealInfoBean.setOtherRemandReason(appealRemandDetailData.getOtherRemandReason());
							}
							appealInfoBean.setAppealRemandId(appealRemandDetailData.getAppealRemandDetailId());
						}
					}
					
				}
				//	adding the bean in the object assembly
				objAssembly.addBean(appealInfoBean,true);
				// adding Taxappealdata to object assembly
				objAssembly.addComponent(taxAppealData,true);
			}else if (appealBO.getAppealData() instanceof MiscAppealData){
				
				//MiscAppeal miscAppealBo = (MiscAppeal)appealBO;
				MiscAppealData miscAppealData = (MiscAppealData)appealBO.getAppealData();
				DecisionData decisiondata = miscAppealData.getAppealledAgainstDecision();
				// get the Nonmon decision ID
				appealInfoBean.setAppealReason(miscAppealData.getAppealReason());
				appealInfoBean.setAppellant(miscAppealData.getAppellant());
				appealInfoBean.setAppellantValue(miscAppealData.getAppellant());
				appealInfoBean.setOpponent(miscAppealData.getAppellant());
				if(miscAppealData.getInterpreter() != null) {
					//appealInfoBean.setInterpreter(InterpreterEnum.getEnum(nonMonAppealData.getInterpreter()).getDescription());
					appealInfoBean.setInterpreter(InterpreterEnum.getEnum(miscAppealData.getInterpreter()).getDescription());
				}
				else {
					appealInfoBean.setInterpreter(GlobalConstants.NOT_APPLICABLE);
				}
				appealInfoBean.setDocketNumber(miscAppealData.getDocketNumber().toString());
				//appealInfoBean.setDecisionDescription((String)CacheUtility.getCachePropertyValue("ISSUE_CATEGORY_DISPLAY","key",decisiondata.getIssueData().getMstIssueMaster().getIssueCategory(),"description"));
				appealInfoBean.setDecisionDescription(GlobalConstants.NA);
				// for misc appeal data issue desc will be NA
				List appellantInfoList = AppealUtil.getAppllantInformation(miscAppealData);
				//ClaimantData claimantData = null;
				AppealPartyData appealPartyDataForAppellant = (AppealPartyData)appellantInfoList.get(0);
				if (appealPartyDataForAppellant.getSsn()!=null) {
					//ClaimantData claimantData = appealPartyDataForAppellant.getClaimantData();
                    // setting claimant Address
					appealInfoBean.setClaimantName(appealPartyDataForAppellant.getPartyName());
					appealInfoBean.setClaimantSsn(appealPartyDataForAppellant.getSsn());
					if (appealPartyDataForAppellant.getPhoneNumber()!=null){
						appealInfoBean.setClaimantTelephone(appealPartyDataForAppellant.getPhoneNumber());
					}
					appealInfoBean.setClaimantAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.CLAIMANT.getName());
				}else {
					EmployerData employerData = appealPartyDataForAppellant.getEmployerData();
					appealInfoBean.setEmployerName(appealPartyDataForAppellant.getPartyName());
					
					// getting employer contact details from employer contact table
					if (appealPartyDataForAppellant.getEmployerData()!=null){
						appealInfoBean.setEmployerEan(appealPartyDataForAppellant.getEmployerData().getEan());
						EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
						if(employerContactData != null) {
							this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
						}
					}else{
						appealInfoBean.setEmployerAddressBean(appealPartyDataForAppellant.getMailingAddress().getAddressBean());
						appealInfoBean.setEmployerEan(GlobalConstants.NA);
					}
					appealInfoBean.setAppellant(AppellantOrOpponentEnum.EMPLOYER.getName());
				}
				List opponentInfoList = AppealUtil.getOpponentInformation(miscAppealData);
				if(opponentInfoList != null && !opponentInfoList.isEmpty()) {
					AppealPartyData appealPartyDataForOpponent= (AppealPartyData)opponentInfoList.get(0);
					if (appealPartyDataForOpponent.getSsn()!=null) {
						//ClaimantData claimantData = appealPartyDataForAppellant.getClaimantData();
	                    // setting claimant Address
						appealInfoBean.setClaimantName(appealPartyDataForOpponent.getPartyName());
						appealInfoBean.setClaimantSsn(appealPartyDataForOpponent.getSsn());
						if (appealPartyDataForOpponent.getPhoneNumber()!=null){
							appealInfoBean.setClaimantTelephone(appealPartyDataForOpponent.getPhoneNumber());
						}
						
						appealInfoBean.setClaimantAddressBean(appealPartyDataForOpponent.getMailingAddress().getAddressBean());
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.CLAIMANT.getName());
					}else {
						EmployerData employerData = appealPartyDataForOpponent.getEmployerData();
						appealInfoBean.setEmployerName(appealPartyDataForOpponent.getPartyName());
						if (employerData!=null){
							appealInfoBean.setEmployerEan(employerData.getEan());
                            // getting employer contact details from employer contact table
							EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(employerData.getEmployerId());
							if(employerContactData != null) {
								this.setEmployerDetails(employerAddress,employerContactData,appealInfoBean);
							}
						}else {
							appealInfoBean.setEmployerAddressBean(appealPartyDataForOpponent.getMailingAddress().getAddressBean());
							appealInfoBean.setEmployerEan(GlobalConstants.NA);
						}
						appealInfoBean.setOpponent(AppellantOrOpponentEnum.EMPLOYER.getName());
					}
				}
				appealInfoBean.setDecision((String)CacheUtility.getCachePropertyValue("T_MST_BOR_RECOMMENDATION","key",decisiondata.getDecisionCode(),"description"));
				appealInfoBean.setDecisionStDate(decisiondata.getDecisionStartDate().toString());
				//	for getting the remand decisions for the appeal
				Set appealRemandetailsSet = miscAppealData.getAppealRemandDetailSet();
				if(appealRemandetailsSet != null){
					Date cmprDate = new Date();
					cmprDate = new SimpleDateFormat("MM/dd/yyyy").parse("12/12/1900");
					for (Iterator it = appealRemandetailsSet.iterator(); it.hasNext(); ) {
						//	get the appealRemandDetailData data
						AppealRemandDetailData appealRemandDetailData = (AppealRemandDetailData)it.next();
						if(cmprDate.before(appealRemandDetailData.getRemandDate())){
							cmprDate = (Date)appealRemandDetailData.getRemandDate().clone();
						}
					}
					for (Iterator it = appealRemandetailsSet.iterator(); it.hasNext(); ) {
						//	get the appealRemandDetailData data
						AppealRemandDetailData appealRemandDetailData = (AppealRemandDetailData)it.next();
						if(cmprDate.equals(appealRemandDetailData.getRemandDate())){
							appealInfoBean.setRemandDate(DateFormatUtility.format(appealRemandDetailData.getRemandDate()));
							appealInfoBean.setRemandReason(appealRemandDetailData.getRemandReason());
							if (RemandReasonEnum.OTHR.getName().equalsIgnoreCase(appealRemandDetailData.getRemandReason())){
								appealInfoBean.setOtherRemandReason(appealRemandDetailData.getOtherRemandReason());
							}
							appealInfoBean.setRemandResponse(appealRemandDetailData.getRemandResponse());
							appealInfoBean.setAppealRemandId(appealRemandDetailData.getAppealRemandDetailId());
						}
					}
					
				}
				// adding the bean in the object assembly
				objAssembly.addBean(appealInfoBean,true);
				// adding decision data to the object assembly
				objAssembly.addComponent(decisiondata,true);
				//	adding issuedata to object assembly
				objAssembly.addComponent(miscAppealData,true);
			}
			return objAssembly;
		}
	
		
		//	this method is used to save response for remand for information decision
		@Override
		public IObjectAssembly saveRemandForInfoResponse(IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveRemandForInfoResponse");
		}
			// get the appealinfo bean from the object assembly
			AppealInfoBean appealInfoBean =(AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
			// getting the TaxAppeal Data
			TaxAppealData taxAppealData = (TaxAppealData)objAssembly.getFirstComponent(TaxAppealData.class);
			// getting the NonMonAppealData
			NonMonAppealData nonMonAppealData = (NonMonAppealData)objAssembly.getFirstComponent(NonMonAppealData.class);
			// getting misc appeal data
			MiscAppealData miscAppealData = (MiscAppealData)objAssembly.getFirstComponent(MiscAppealData.class);
			
			//Set appealRemandDetailSet = new HashSet();
			
			if(taxAppealData != null){
				this.saveRemandInformationForBor(taxAppealData,appealInfoBean,objAssembly);
//				appealRemandDetailSet =taxAppealData.getAppealRemandDetailSet();
//				if(appealRemandDetailSet != null ){
//					for (Iterator it = appealRemandDetailSet.iterator(); it.hasNext(); ) {
//						//	get the appealRemandDetailData data
//						AppealRemandDetailData appealRemandDetailData = (AppealRemandDetailData)it.next();
//						if(appealRemandDetailData.getAppealRemandDetailId().equals(appealInfoBean.getAppealRemandId())){
//							// set the new response
//							appealRemandDetailData.setRemandResponse(appealInfoBean.getRemandResponse());
//							// if submit button is pressed then set reamnd complete date
//							if(appealInfoBean.getCloseWorkItem().booleanValue() ){
//								appealRemandDetailData.setRemandCompletedDate(new Date());
//							}
//						}
//					}
//				}
//				//	this block decides whther to close the work item or not
//				if(appealInfoBean.getCloseWorkItem().booleanValue() ){
//					WorkflowItemBean workflowItemBean = (WorkflowItemBean)objAssembly.getFirstBean(WorkflowItemBean.class);
//					// set the status to completed
//					taxAppealData.setAppealStatus(AppealStatusEnum.REMAND_COMPLETED.getName());
//					//	call close work item for customization method
//					Appeal.closeRemandForInfoWorkItem(workflowItemBean);
//				}
//				// save the tax appeal
//				TaxAppeal taxAppealBO = new TaxAppeal(taxAppealData);
//				taxAppealBO.saveOrUpdate();
			}else if(nonMonAppealData != null){
				this.saveRemandInformationForBor(nonMonAppealData,appealInfoBean,objAssembly);
			}else if (miscAppealData !=null){
				this.saveRemandInformationForBor(miscAppealData,appealInfoBean,objAssembly);
			}
			/*List borRecommendationDataList = objAssembly.getComponentList(BORRecommendationData.class);
			if (borRecommendationDataList!=null && borRecommendationDataList.size()>0){
			    	
			         BorRecommendation.saveOrUpdateOrDelete(borRecommendationDataList);
			}*/
			
			return objAssembly;
		}
		
		//	this method is used to save bor Affirm decision
		@Override
		public IObjectAssembly saveBorRemandForNewDecision(IObjectAssembly objAssembly) throws CloneNotSupportedException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveBorRemandForNewDecision");
		}
			List borMemberList = new ArrayList();
			// get the reversedecision bean from the object assembly;
			RemandForNewDecisionBean remandForNewDecisionBean = (RemandForNewDecisionBean)objAssembly.getFirstBean(RemandForNewDecisionBean.class);
			//get the NonMonData from the object assembly
	        NonMonAppealData nonMonAppealData = (NonMonAppealData)objAssembly.getFirstComponent(NonMonAppealData.class);
			// get the taxappeal data from the obj assembly
	        TaxAppealData taxAppealData = (TaxAppealData)objAssembly.getFirstComponent(TaxAppealData.class);
	        // TaxIntercept Appeal Data
	       // TaxInterceptAppealData taxInterceptAppealData = (TaxInterceptAppealData)objAssembly.getFirstComponent(TaxInterceptAppealData.class);
	        // get the bor meeting data
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
	        
	        //TODO get other types of data objects (taxintercept ,taxappeal when ready)
	        if(nonMonAppealData != null){
	        	// get the BOR decision list frm the object assembly
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	// create nonMonBO
	        	NonMonAppeal nonMonAppealBO = new NonMonAppeal(nonMonAppealData);
	        	// call the business method for saving affirm decison
	        	nonMonAppealData.setBorRecommendationSet(null);
	        	nonMonAppealBO.saveBorRemandForNewDecision(borMemberList,remandForNewDecisionBean,borMeetingData);
	        					
	        }else if(taxAppealData != null){
	        	// get the BOR decision list from the object assembly
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	
	        	TaxAppeal taxAppealBO = new TaxAppeal(taxAppealData);
	        	
	        	taxAppealBO.saveRemandForNewDecision(borMemberList,remandForNewDecisionBean,borMeetingData);
	        	
	        	//
	       
	        }
	        List borRecommendationDataList = objAssembly.getComponentList(BORRecommendationData.class);
		    if (borRecommendationDataList!=null && borRecommendationDataList.size()>0){
		    	
		         BorRecommendation.saveOrUpdateOrDelete(borRecommendationDataList);
		    }
		    return objAssembly;
		}
		
		//	this method is used to savelate appeal decision
		public IObjectAssembly saveLateAppealDecision(IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveLateAppealDecision");
		}
			//	get the reversedecision bean from the object assembly;
			//LateAppealDecisionBean lateAppealDecisionBean = (LateAppealDecisionBean)objAssembly.getFirstBean(RemandForNewDecisionBean.class);
			
			return objAssembly;
		}
		
		@Override
		public IObjectAssembly saveTaxInterceptBorMeetingDecision(IObjectAssembly objAssembly) throws CloneNotSupportedException, Exception {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveTaxInterceptBorMeetingDecision");
		}
			TaxInterceptAppealData appealData = (TaxInterceptAppealData)objAssembly.getFirstComponent(TaxInterceptAppealData.class);
			TaxInterceptAppeal taxInterceptBO = new TaxInterceptAppeal(appealData);
			List decisionDetailList = objAssembly.getComponentList(AppealDecisionDetailData.class);
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			
			this.getBORDecisionLetterReportParams(objAssembly);
			AppealsDecisionLettersParagraphBuilder builder= (AppealsDecisionLettersParagraphBuilder)objAssembly.getData("AppealsDecisionLettersParagraphBuilder");
			String docketType = (String) objAssembly.getData("docketType");
//			Appeal appealBo = Appeal.getAppealBoByAppealData(appealData);
//			ReverseDecisionBean revBean = appealBo.populateDecisionLetterBeanForBorInTaxIntercept();
			taxInterceptBO.saveDecisionDetailsForBorMeeting(decisionDetailList, borMeetingData,docketType,builder);
			return objAssembly;
		}
		/*this method will save any kind of BOR decision*/
		@Override
		public IObjectAssembly saveBorDecision(IObjectAssembly objAssembly) throws CloneNotSupportedException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveBorDecision");
		}
			
			List borMemberList = new ArrayList();
			//get the NonMonData from the object assembly
	        NonMonAppealData nonMonAppealData = (NonMonAppealData)objAssembly.getFirstComponent(NonMonAppealData.class);
	        //get the taxappeal data from the obj assembly
	        TaxAppealData taxAppealData = (TaxAppealData)objAssembly.getFirstComponent(TaxAppealData.class);
	        
	        MiscAppealData miscAppealData  = (MiscAppealData)objAssembly.getFirstComponent(MiscAppealData.class);
	        //get the bor meeting data
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			// get the additional reamarks
			String addRemarks = (String)objAssembly.getData("additionalRemarks");
			//String borDecision = (String)objAssembly.getData(AppealConstants.BOR_REVIEW_DECISION_DESC);
			String borDecisionValue = (String)objAssembly.getData(AppealConstants.BOR_REVIEW_DECISION_VALUE);
			ReverseDecisionBean boardOfReviewReverseDecisionBean = (ReverseDecisionBean)objAssembly.getFirstBean(ReverseDecisionBean.class);
	        if(nonMonAppealData != null){
	        	// get the BOR decision list frm the object assembly
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	nonMonAppealData.setBorRecommendationSet(null);
	        	// create nonMonBO
	        	NonMonAppeal nonMonAppealBO = new NonMonAppeal(nonMonAppealData);
	        	// call the business method for saving affirm decison
	        	if (AppealDecisionCodeEnum.AFFIRM.getName().equalsIgnoreCase(borDecisionValue)){
	        		nonMonAppealBO.saveBORAffirmDecision(boardOfReviewReverseDecisionBean.getAdditionalRemarksForArfmWithEdit(),boardOfReviewReverseDecisionBean);
	        	}else if (AppealDecisionCodeEnum.REVERSE.getName().equalsIgnoreCase(borDecisionValue)){
	        		nonMonAppealBO.saveBORReverseDecision(borMemberList,boardOfReviewReverseDecisionBean,borMeetingData);
	        	}else if (AppealDecisionCodeEnum.MODIFY.getName().equalsIgnoreCase(borDecisionValue)){
	        		nonMonAppealBO.saveBORReverseDecision(borMemberList,boardOfReviewReverseDecisionBean,borMeetingData);
	        	}else if (AppealDecisionCodeEnum.REMX.getName().equalsIgnoreCase(borDecisionValue)){
	        		// remand for new decision
	        		RemandForNewDecisionBean remandForNewDecisionBean = (RemandForNewDecisionBean)objAssembly.getFirstBean(RemandForNewDecisionBean.class);
	        		nonMonAppealBO.saveBorRemandForNewDecision(borMemberList,remandForNewDecisionBean,borMeetingData);
	        	}else if (AppealDecisionCodeEnum.REMI.getName().equalsIgnoreCase(borDecisionValue)){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

	        		
	        	}
	        }else if(taxAppealData != null){
	        	//get the BOR decision list from the object assembly
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	taxAppealData.setBorRecommendationSet(null);
	        	TaxAppeal taxAppealBO = new TaxAppeal(taxAppealData);
	        	//taxAppealBO.saveBORAffirmDecision(borMemberList,borMeetingData,addRemarks);
	        	if (AppealDecisionCodeEnum.AFFIRM.getName().equalsIgnoreCase(borDecisionValue)){
	        		taxAppealBO.saveBORAffirmDecision(addRemarks,boardOfReviewReverseDecisionBean);
	        	}else if (AppealDecisionCodeEnum.REVERSE.getName().equalsIgnoreCase(borDecisionValue)){
	        		taxAppealBO.saveBORReverseDecision(borMemberList,boardOfReviewReverseDecisionBean,borMeetingData);
	        	}else if (AppealDecisionCodeEnum.MODIFY.getName().equalsIgnoreCase(borDecisionValue)){
	        		
	        		taxAppealBO.saveBORReverseDecision(borMemberList,boardOfReviewReverseDecisionBean,borMeetingData);
	        	}else if (AppealDecisionCodeEnum.REMX.getName().equalsIgnoreCase(borDecisionValue)){
	        		// remand for new decision
	        		RemandForNewDecisionBean remandForNewDecisionBean = (RemandForNewDecisionBean)objAssembly.getFirstBean(RemandForNewDecisionBean.class);
	        		//taxAppealBO.saveBorRemandForNewDecision(borMemberList,remandForNewDecisionBean,borMeetingData);
	        	}else if (AppealDecisionCodeEnum.REMI.getName().equalsIgnoreCase(borDecisionValue)){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

	        		
	        	}
	        	
	        }else if(miscAppealData != null){
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	miscAppealData.setBorRecommendationSet(null);
	        	MiscAppeal miscAppealBo = new MiscAppeal(miscAppealData);
	        	if (AppealDecisionCodeEnum.AFFIRM.getName().equalsIgnoreCase(borDecisionValue)){
	        		miscAppealBo.saveAffirmDecisionForBor(addRemarks,boardOfReviewReverseDecisionBean);
	        	}else if (AppealDecisionCodeEnum.REVERSE.getName().equalsIgnoreCase(borDecisionValue)){
	        		miscAppealBo.saveReverseDecisionForBor(borMeetingData,boardOfReviewReverseDecisionBean);
	        	}else if (AppealDecisionCodeEnum.MODIFY.getName().equalsIgnoreCase(borDecisionValue)){
	        		miscAppealBo.saveReverseDecisionForBor(borMeetingData,boardOfReviewReverseDecisionBean);
	        	}else if (AppealDecisionCodeEnum.REMX.getName().equalsIgnoreCase(borDecisionValue)){
	        		// remand for new decision
	        		RemandForInfoBean remandForInfoBean= (RemandForInfoBean)objAssembly.getFirstBean(RemandForInfoBean.class);
	        		miscAppealBo.saveRemandForNewDecisionForBor(borMeetingData,remandForInfoBean);
	        	}else if (AppealDecisionCodeEnum.REMI.getName().equalsIgnoreCase(borDecisionValue)){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

	        		
	        	}
	        	
	        }
	        /*this part will save the recommendation data*/
	        List borRecommendationDataList = objAssembly.getComponentList(BORRecommendationData.class);
		    if (borRecommendationDataList!=null && borRecommendationDataList.size()>0){

		         BorRecommendation.saveOrUpdateOrDeleteRecommendationOnSaveDecision(borRecommendationDataList);
		    }
		    // closing the work item
		    WorkflowItemBean workflowItemBean = (WorkflowItemBean) objAssembly.getFirstBean(WorkflowItemBean.class);
		    /*IBaseWorkflowDAO baseWorkflowDAO = DAOFactory.instance.getBaseWorkflowDAO();
		    baseWorkflowDAO.startWorkItem(workflowItemBean.getPersistentOid());*/
		    // CIF_00982|| Code change w.r.t new BRMS approach
	        Map<String , Object> mapValues = new HashMap<String , Object>();
            
            mapValues.put("persistenceOid",workflowItemBean.getPersistentOid());

            WorkflowTransactionService wf = new WorkflowTransactionService();
                       
            wf.invokeWorkFlowOperation(WorkFlowOperationsEnum.START.getName(), mapValues); 
			return objAssembly;
		}
		
		/*This private method is invoked from getAppealInfoBorMeeting for setting employer address*/
		private void setEmployerDetails(AddressBean employerAddress,EmployerContactData employerContactData,AppealInfoBean appealInfoBean){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method setEmployerDetails");
		}
			
			employerAddress.setAddress1(employerContactData.getAddressLine1());
			employerAddress.setAddress2(employerContactData.getAddressLine2());
			employerAddress.setCity(employerContactData.getCity());
			employerAddress.setCountry(employerContactData.getCountry());
			employerAddress.setZip(employerContactData.getZip());
			employerAddress.setState(employerContactData.getState());
			employerAddress.setPostalBarCode(employerContactData.getBarcode());
			if(employerContactData.getPhone() != null) {
				appealInfoBean.setEmployerTelephone(employerContactData.getPhone());
			}
			else {
				appealInfoBean.setEmployerTelephone(GlobalConstants.NOT_APPLICABLE);
			}
			appealInfoBean.setEmployerAddressBean(employerAddress);
		
		}
		
		//For Federal empoyers
		private void setEmployerDetails(AddressBean employerAddress, AddressComponentData employerContactData, AppealInfoBean appealInfoBean){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method setEmployerDetails");
		}
			
			employerAddress.setAddress1(employerContactData.getLine1());
			employerAddress.setAddress2(employerContactData.getLine2());
			employerAddress.setCity(employerContactData.getCity());
			employerAddress.setCountry(employerContactData.getCountry());
			employerAddress.setZip(employerContactData.getZip());
			employerAddress.setState(employerContactData.getState());
			employerAddress.setPostalBarCode(employerContactData.getPostalBarCode());
			appealInfoBean.setEmployerTelephone(GlobalConstants.NOT_APPLICABLE);
			appealInfoBean.setEmployerAddressBean(employerAddress);
			
		}

		//CQ 19203, returns true if employer contact info was found and set
		private boolean setEmployerContactDetails(AddressBean employerAddress, EmployerData empData, AppealInfoBean appealInfoBean){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method setEmployerContactDetails");
		}
			if(EmployerTypeEnum.FED.getName().equals(empData.getEmployerType())){
				FederalEmployer fedEmployer = FederalEmployer.fetchFedEmprByPkId(empData.getEmployerId());
				setEmployerDetails(employerAddress, fedEmployer.getFederalEmployerData().getContactAddress(), appealInfoBean);
			}
			else {
				EmployerContactData employerContactData = EmployerContact.getEmplrContactByEmployerId(empData.getEmployerId());
				if(employerContactData == null){
					return false;
				}
				setEmployerDetails(employerAddress, employerContactData, appealInfoBean);
			}
			return true;
		}
		
		private void setSpouseDetailsForTaxIntercept(AppealPartyData appealPartyData,AppealInfoBean appealInfoBean){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method setSpouseDetailsForTaxIntercept");
		}
			
			appealInfoBean.setSpouseName(appealPartyData.getPartyName());
			appealInfoBean.setSpouseSsn(appealPartyData.getSsn());
			appealInfoBean.setSpousePhoneNum((appealPartyData.getPhoneNumber()));
			appealInfoBean.setSpouseAddressBean(appealPartyData.getMailingAddress().getAddressBean());
		}
		
		private void setClaimantDetailsForTaxIntercept(AppealPartyData appealPartyData,AppealInfoBean appealInfoBean){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method setClaimantDetailsForTaxIntercept");
		}
			
			appealInfoBean.setClaimantName(appealPartyData.getPartyName());
			appealInfoBean.setClaimantSsn(appealPartyData.getSsn());
			appealInfoBean.setClaimantTelephone(appealPartyData.getPhoneNumber());
			appealInfoBean.setClaimantAddressBean(appealPartyData.getMailingAddress().getAddressBean());
			
		}
		
		
		@Override
		public IObjectAssembly getALJDecisionTobeDisplayedForBorScreens(IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getALJDecisionTobeDisplayedForBorScreens");
		}
			
			String appealId = (String)objAssembly.getPrimaryKey(AppealData.class);
			AppealData appealData = Appeal.fetchAppealAlongWithAppealChildByAppealId(Long.valueOf(appealId));
			objAssembly.addComponent(appealData,true);
			return objAssembly;
		}
		
		@Override
		public IObjectAssembly  decideBorDecisiononMajorityBasisForLateAppeal(IObjectAssembly objAssembly) throws BaseApplicationException, CloneNotSupportedException {
		    
			List  decisionList = new ArrayList();
			decisionList = (List)objAssembly.getBeanList(BORDecisionBean.class);
			AppealData appealData = (AppealData)objAssembly.getFirstComponent(AppealData.class, true);
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			int allowCount = 0;
			int denyCount = 0;
			if(decisionList != null && decisionList.size() != 0){
				for (Iterator i = decisionList.iterator(); i.hasNext(); ) {
					BORDecisionBean borDecisionBean = (BORDecisionBean) i.next();
					if(AppealDecisionCodeEnum.ALLOWED.getName().equals(borDecisionBean.getDecision())){
						allowCount = allowCount + 1;
					}else if(AppealDecisionCodeEnum.DENY.getName().equals(borDecisionBean.getDecision())){
						denyCount = denyCount + 1;
					}
					
				}
			}
			this.getBORDecisionLetterReportParams(objAssembly);
			AppealsDecisionLettersParagraphBuilder builder= (AppealsDecisionLettersParagraphBuilder)objAssembly.getData("AppealsDecisionLettersParagraphBuilder");
			String docketType = (String) objAssembly.getData("docketType");
			Appeal appealBO = Appeal.getAppealBoByAppealData(appealData);
			appealBO.saveBORdecisionForLateAppeal(borMeetingData,allowCount,denyCount, builder, docketType);
			//LateAppeal appealBO =  new LateAppeal((LateAppealData)appealData);
			//appealBO.saveBORdecisionForLateAppeal(borMeetingData,allowCount,denyCount);
			return objAssembly;
			
		}
		
		@Override
	    public IObjectAssembly  findBorMeetingDataByReviewDate(IObjectAssembly objAssembly)throws  BaseApplicationException{
			
			List borMeetingList = BORMeeting.findBorMeetingData(new Date());
			if (borMeetingList!=null && borMeetingList.size()>0){
				objAssembly.addComponentList(borMeetingList,true);
			}else{
				throw new BaseApplicationException("error.access.app.borappealstoscheduleforrecommendation.records.null");
			}
			return objAssembly;
		}
	
        @Override
		public IObjectAssembly saveOrUpdateAppealBorMeetingData(IObjectAssembly objAssembly) throws BaseApplicationException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveOrUpdateAppealBorMeetingData");
		}
			
//			System.out.println("saveOrUpdateAppealBorMeetingData");
			
			BORMeeting borMeetingBO = null;
			String masterDocketType=String.valueOf(objAssembly.getData("MASTER_DOCKET_TYPE"));
			
			BORMeetingData borMeetingData = (BORMeetingData)
					objAssembly.fetchORCreate(BORMeetingData.class);
			borMeetingData.setStatus(BorMeetingStatusEnum.SCHEDULED.getName());
			
			borMeetingBO = BORMeeting.findBorMeetingDataByReviewDateA(borMeetingData.getReviewDate(), masterDocketType);
			if (borMeetingBO == null) {
				borMeetingBO = new BORMeeting(borMeetingData);
				
			}else if (BorMeetingStatusEnum.COMPLETED.getName().equals(borMeetingBO.getBORMeetingData().getStatus())) {
				throw new BaseApplicationException("error.access.app.bormeetingcompleted");
			}
			
			Set borMasterDocket =borMeetingBO.getBORMeetingData().getBorMasterDocketDataSet();
			BORMasterDocketData borMasterDocketData=null;
			if(borMasterDocket!=null && !borMasterDocket.isEmpty()){
				Iterator iterate=borMasterDocket.iterator();
				int i=0;
				while(iterate.hasNext()){
					borMasterDocketData= (BORMasterDocketData)iterate.next();
					if(masterDocketType.equals(borMasterDocketData.getDocketType())){
						i++;
						break;
					}
				}
				if(i<=0){
					borMasterDocketData= new BORMasterDocketData();
					borMasterDocketData.setBorMeetingData(borMeetingBO.getBORMeetingData());
				}
				
			}else{
				borMasterDocketData= new BORMasterDocketData();
				borMasterDocketData.setBorMeetingData(borMeetingBO.getBORMeetingData());
			}
			
			borMasterDocketData.setDocketType(masterDocketType);
			if(borMasterDocket==null){
				borMeetingBO.getBORMeetingData().setBorMasterDocketDataSet(new HashSet());
				borMasterDocket =borMeetingBO.getBORMeetingData().getBorMasterDocketDataSet();
			}
			borMasterDocket.add(borMasterDocketData);
			
//			borMeetingBO.getBORMeetingData().setDocketType(masterDocketType);
//			borMeetingBO.saveOrUpdate();
//			borMeetingBO.flush();
			
			
			
			String[] appealIdArray = (String[]) objAssembly.getData("APPEAL_ID_ARRAY");
			
			for (int i = 0; i < appealIdArray.length; i++) {
				// to find & update Appeal
				StringTokenizer st = new StringTokenizer(appealIdArray[i], GlobalConstants.PIPE_SEPARATOR);
				String appealId = st.nextToken();
				Appeal appealBO = Appeal.findByPrimaryKey(Long.valueOf(appealId));
				appealBO.getAppealData().setHearingScheduled(GlobalConstants.DB_ANSWER_YES);
				// if appeal status is continued then dont change the status to OPEN,status COND needs to
				// be compared while giving decision at BOR Meeting, discussed with Venu
				if (!AppealStatusEnum.CONTINUED.getName().equalsIgnoreCase(appealBO.getAppealData().getAppealStatus())){
					appealBO.getAppealData().setAppealStatus(AppealStatusEnum.OPEN.getName());
				}
				appealBO.saveOrUpdate();
//				appealBO.flush();
				
				// to create & save BORMeetingAppealMapData
				BORMeetingAppealMapData map = new BORMeetingAppealMapData();
				map.setAppealData(appealBO.getAppealData());
				map.setBorMasterDocketData(borMasterDocketData);
				BORMeeting bo = new BORMeeting(null);
				bo.saveOrUpdate(map);
			}
			
			return objAssembly;
		}
		
		private void saveRemandInformationForBor(AppealData appealData,AppealInfoBean appealInfoBean,
				IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveRemandInformationForBor");
		}
			
			//Set appealRemandDetailSet = new HashSet();
			Set appealRemandDetailSet =appealData.getAppealRemandDetailSet();
			if(appealRemandDetailSet != null ){
				for (Iterator it = appealRemandDetailSet.iterator(); it.hasNext(); ) {
					//	get the appealRemandDetailData data
					AppealRemandDetailData appealRemandDetailData = (AppealRemandDetailData)it.next();
					if(appealRemandDetailData.getAppealRemandDetailId().equals(appealInfoBean.getAppealRemandId())){
						// set the new response
						appealRemandDetailData.setRemandResponse(appealInfoBean.getRemandResponse());
						// if submit button is pressed then set reamnd complete date
						if(appealInfoBean.getCloseWorkItem().booleanValue() ){
							appealRemandDetailData.setRemandCompletedDate(new Date());
						}
					}
				}
			}
			//	this block decides whther to close the work item or not
			if(appealInfoBean.getCloseWorkItem().booleanValue() ){
				WorkflowItemBean workflowItemBean = (WorkflowItemBean)objAssembly.getFirstBean(WorkflowItemBean.class);
				// set the status to completed
				appealData.setAppealStatus(AppealStatusEnum.REMAND_COMPLETED.getName());
				appealData.setPriorityFlag(GlobalConstants.DB_ANSWER_YES);
				appealData.setHearingScheduled(GlobalConstants.DB_ANSWER_NO);
				//	call close work item for customization method
				Appeal.closeRemandForInfoWorkItem(workflowItemBean);
			}
			// save the appeal
			Appeal appealBO = Appeal.getAppealBoByAppealData(appealData);
			//new Appeal(appealData);
			appealBO.saveOrUpdate();
		
		}
		/*
		 * This method will be fired from BOR decision screen where decision is either Reverse or Remand for Info
		 * or Recuse or Modify
		 * This will identify the majority and save decision at the BOR level
		 * */
		@Override
		public IObjectAssembly  decideBorDecisiononMajorityBasisAndSaveDecision(IObjectAssembly objAssembly) throws BaseApplicationException, CloneNotSupportedException {
			List  decisionList = new ArrayList();
			 decisionList = (List)objAssembly.getBeanList(BORDecisionBean.class);
			
			int affirmCount = 0;
			int reverseCount = 0;
			int remandforInfoCount = 0;
			int remandforNewDecisionCount = 0;
			int modifyCount= 0;
			int recuseCount = 0;
			int affirmEditCount = 0;
			int withdrawCount = 0;
			ArrayList decisionLists= new ArrayList();
			String boardOfReviewDecision = null;
			// this contains the decision desc
			String boardOfReviewDecisionValue = null;
			// this contains the decision value
			String csrDecisionValue = (String) objAssembly
					.getData(AppealConstants.BOR_REVIEW_DECISION_VALUE);
			/*if(decisionList != null && decisionList.size() != 0){
				for (Iterator i = decisionList.iterator(); i.hasNext(); ) {
					BORDecisionBean borDecisionBean = (BORDecisionBean) i.next();
					if(BORAppealDecisionEnum.AFFIRM.getName().equals(borDecisionBean.getDecisionValue())){
						affirmCount = affirmCount + 1;
					}else if(BORAppealDecisionEnum.REVERSE.getName().equals(borDecisionBean.getDecisionValue())){
						reverseCount = reverseCount + 1;
					}else if(BORAppealDecisionEnum.REMAND_FOR_INFORMATION.getName().equals(borDecisionBean.getDecisionValue())){
						remandforInfoCount = remandforInfoCount + 1;
					}else if(BORAppealDecisionEnum.REMAND_FOR_NEW_DECISION.getName().equals(borDecisionBean.getDecisionValue())){
						remandforNewDecisionCount = remandforNewDecisionCount + 1;
					}else if(BORAppealDecisionEnum.MODIFY.getName().equals(borDecisionBean.getDecisionValue())){
						modifyCount = modifyCount + 1;
					}else if(BORAppealDecisionEnum.RECUSE.getName().equals(borDecisionBean.getDecisionValue())){
						recuseCount = recuseCount + 1;
					}else if(BORAppealDecisionEnum.AFFIRM_WITH_EDIT.getName().equals(borDecisionBean.getDecisionValue())){
						affirmEditCount = affirmEditCount + 1;
					}else if(BORAppealDecisionEnum.WITHDRAWAL_ON_RECORD.getName().equals(borDecisionBean.getDecisionValue())){
						withdrawCount = withdrawCount + 1;
					}
				}
			}*/
			
			
			
			
			/*
			AppealDecisionCompareBean appealDecisionReverseBean = new AppealDecisionCompareBean();
			appealDecisionReverseBean.setDecision(BORAppealDecisionEnum.REVERSE.getDescription());
			appealDecisionReverseBean.setDecisionValue(BORAppealDecisionEnum.REVERSE.getName());
			appealDecisionReverseBean.setDecisionCount(reverseCount);
			decisionLists.add(appealDecisionReverseBean);
			
			AppealDecisionCompareBean appealDecisionAffirmBean = new AppealDecisionCompareBean();
			appealDecisionAffirmBean.setDecision(BORAppealDecisionEnum.AFFIRM.getDescription());
			appealDecisionAffirmBean.setDecisionValue(BORAppealDecisionEnum.AFFIRM.getName());
			appealDecisionAffirmBean.setDecisionCount(affirmCount);
			decisionLists.add(appealDecisionAffirmBean);
			
			AppealDecisionCompareBean appealDecisionRemandForInfoBean = new AppealDecisionCompareBean();
			appealDecisionRemandForInfoBean.setDecision(BORAppealDecisionEnum.REMAND_FOR_INFORMATION.getDescription());
			appealDecisionRemandForInfoBean.setDecisionValue(BORAppealDecisionEnum.REMAND_FOR_INFORMATION.getName());
			appealDecisionRemandForInfoBean.setDecisionCount(remandforInfoCount);
			decisionLists.add(appealDecisionRemandForInfoBean);
			
			AppealDecisionCompareBean appealDecisionRemandForNewDecisionBean = new AppealDecisionCompareBean();
			appealDecisionRemandForNewDecisionBean.setDecision(BORAppealDecisionEnum.REMAND_FOR_NEW_DECISION.getDescription());
			appealDecisionRemandForNewDecisionBean.setDecisionValue(BORAppealDecisionEnum.REMAND_FOR_NEW_DECISION.getName());
			appealDecisionRemandForNewDecisionBean.setDecisionCount(remandforNewDecisionCount);
			decisionLists.add(appealDecisionRemandForNewDecisionBean);
			
			AppealDecisionCompareBean appealDecisionModifyBean = new AppealDecisionCompareBean();
			appealDecisionModifyBean.setDecision(BORAppealDecisionEnum.MODIFY.getDescription());
			appealDecisionModifyBean.setDecisionValue(BORAppealDecisionEnum.MODIFY.getName());
			appealDecisionModifyBean.setDecisionCount(modifyCount);
			decisionLists.add(appealDecisionModifyBean);
			
			AppealDecisionCompareBean appealDecisionRecuseBean = new AppealDecisionCompareBean();
			appealDecisionRecuseBean.setDecision(BORAppealDecisionEnum.RECUSE.getDescription());
			appealDecisionRecuseBean.setDecisionValue(BORAppealDecisionEnum.RECUSE.getName());
			appealDecisionRecuseBean.setDecisionCount(recuseCount);
			decisionLists.add(appealDecisionRecuseBean);
			
			AppealDecisionCompareBean appealDecisionAffirmWithEditBean = new AppealDecisionCompareBean();
			appealDecisionAffirmWithEditBean.setDecision(BORAppealDecisionEnum.AFFIRM_WITH_EDIT.getDescription());
			appealDecisionAffirmWithEditBean.setDecisionValue(BORAppealDecisionEnum.AFFIRM_WITH_EDIT.getName());
			appealDecisionAffirmWithEditBean.setDecisionCount(affirmEditCount);
			decisionLists.add(appealDecisionAffirmWithEditBean);
			
			AppealDecisionCompareBean appealDecisionWithdrawBean = new AppealDecisionCompareBean();
			appealDecisionWithdrawBean.setDecision(BORAppealDecisionEnum.WITHDRAWAL_ON_RECORD.getDescription());
			appealDecisionWithdrawBean.setDecisionValue(BORAppealDecisionEnum.WITHDRAWAL_ON_RECORD.getName());
			appealDecisionWithdrawBean.setDecisionCount(withdrawCount);
			decisionLists.add(appealDecisionWithdrawBean);
			
			Collections.sort(decisionLists);
			// checking for decison count
			if(decisionLists != null && decisionLists.size() >= 2){
				AppealDecisionCompareBean cmprBean1 = (AppealDecisionCompareBean)decisionLists.get(0);
				AppealDecisionCompareBean cmprBean2 = (AppealDecisionCompareBean)decisionLists.get(1);
				if(cmprBean1.getDecisionCount() == cmprBean2.getDecisionCount() ){
					// if decision count is same
					//objAssembly.removeComponent(BORRecommendationData.class);
					throw new BaseApplicationException("error.access.app.bor.recommendation");
				}else{
					boardOfReviewDecision = cmprBean1.getDecision();
					boardOfReviewDecisionValue = cmprBean1.getDecisionValue();
				}
			}
			if(decisionLists != null && decisionLists.size() == 1){
				AppealDecisionCompareBean cmprBean1 = (AppealDecisionCompareBean)decisionLists.get(0);
				boardOfReviewDecision = cmprBean1.getDecision();
				boardOfReviewDecisionValue = cmprBean1.getDecisionValue();
			}*/
			
			//@cif_wy(impactNumber = "P1-APP-27" , requirementId = "FR_1632", designDocName = "04 UIC", designDocSection = "4.7", dcrNo = "", mddrNo = "DCR_MDDR_153", impactPoint = "Start") 
			Object lObject = MultiStateClassFactory.getObject(this.getClass()
					.getName(), BaseOrStateEnum.STATE, null, null, Boolean.TRUE);
			
			if(lObject != null){
				List<BORRecommendationData> borRecommendationDataList  =  objAssembly.getComponentList(BORRecommendationData.class);
				for(BORRecommendationData recommendationData:borRecommendationDataList){
					 BorRecommendation borRecommendation= new BorRecommendation(recommendationData);
					 borRecommendation.saveOrUpdate();
				}
			}
			//@cif_wy(impactNumber = "P1-APP-27" , requirementId = "FR_1632", designDocName = "04 UIC", designDocSection = "4.7", dcrNo = "", mddrNo = "DCR_MDDR_153", impactPoint = "End")
            // adding the decion taken to the object assembly
			AppealInfoBean appealInfoBean =(AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
		    //objAssembly.addData(AppealConstants.BOR_REVIEW_DECISION_DESC,boardOfReviewDecision);
		    //objAssembly.addData(AppealConstants.BOR_REVIEW_DECISION_VALUE,boardOfReviewDecisionValue);
			 objAssembly.addData(AppealConstants.BOR_REVIEW_DECISION_DESC,AppealDecisionCodeEnum.getEnum(csrDecisionValue).getDescription());
		    objAssembly.addData(AppealConstants.BOR_REVIEW_DECISION_VALUE,csrDecisionValue);
			
			
		    if (appealInfoBean!=null){
		    	if (appealInfoBean.getEmployerName()!=null){
		    		objAssembly.addData(AppealConstants.REMAND_INFO_PARTY_NAME,appealInfoBean.getEmployerName());
		    	}else if (appealInfoBean.getClaimantName()!=null){
		    		objAssembly.addData(AppealConstants.REMAND_INFO_PARTY_NAME,appealInfoBean.getClaimantName());
		    	}
		    }
		return objAssembly;
		}
		
		/*
		 * This method will save Affirm decision at BOR level after editing the decision remarks
		 * */
		@Override
		public IObjectAssembly  saveBorAffirmWithEditDecision(IObjectAssembly objAssembly) throws BaseApplicationException, CloneNotSupportedException {
			
			List borMemberList = new ArrayList();
			//get the NonMonData from the object assembly
	        NonMonAppealData nonMonAppealData = (NonMonAppealData)objAssembly.getFirstComponent(NonMonAppealData.class);
	        //get the taxappeal data from the obj assembly
	        TaxAppealData taxAppealData = (TaxAppealData)objAssembly.getFirstComponent(TaxAppealData.class);
	        
	        MiscAppealData miscAppealData  = (MiscAppealData)objAssembly.getFirstComponent(MiscAppealData.class);
	        
	        LateAppealData lateAppealData  = (LateAppealData)objAssembly.getFirstComponent(LateAppealData.class);
	        
	        NonAppearanceAppealData nonAppearanceAppealData  = (NonAppearanceAppealData)objAssembly.getFirstComponent(NonAppearanceAppealData.class);
	        //get the bor meeting data
			//BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			// get the additional reamarks
			String addRemarks = (String)objAssembly.getData("additionalRemarks");
			/*
			 * saving or update of appeal decision will  be treversed from decision as well as
			 * proof of reading screen
			 * this parameter will determine flow : Decision or Proof Reading
			 * if that is blank  then flow is from proof reading
			 * */
			String determineAppealFlow = null;
	        determineAppealFlow =  (String)objAssembly.getData("determineAppealFlow");
	        ReverseDecisionBean boardOfReviewReverseDecisionBean = (ReverseDecisionBean)objAssembly.getFirstBean(ReverseDecisionBean.class);
	        /*Modified BORAppealDecisionEnum.AFFIRM_WITH_EDIT to BORAppealDecisionEnum.AFFIRM */
	        //boardOfReviewReverseDecisionBean.setDecision(BORAppealDecisionEnum.AFFIRM_WITH_EDIT.getName());
	        
	        
	        boardOfReviewReverseDecisionBean.setDecision((String)objAssembly.getData(AppealConstants.BOR_REVIEW_DECISION_VALUE));
	        //boardOfReviewReverseDecisionBean.setDecision(BORAppealDecisionEnum.AFFIRM.getName());
	        
	        
	        UploadDocumentIntoDmsBean uploadBean = objAssembly.getFirstBean(UploadDocumentIntoDmsBean.class);
			AppealInfoBean appealInfoBean =(AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
			/*if(appealInfoBean.getDocketNumber()!=null)
			{
				Long docketNo=Long.parseLong(appealInfoBean.getDocketNumber());
				uploadBean.setDocketNumber(docketNo);
			}
			if(uploadBean!=null) 
			{
				//boardOfReviewReverseDecisionBean.setFileName(uploadBean.getFileName().getFileName());
				
				String basePath=ApplicationProperties.OUTPUT_REPORT;
				//String relativePath=File.separator;
				String relativePath=GlobalConstants.EMPTY_STRING;
				//String relativePath=ApplicationProperties.IR_FILE_UPLOAD_LOCATION;
				String documentPath=basePath+relativePath;
				//String documentPath="C:\\Sabya-Tools\\File_Upload\\";
				String currFileName="";
				FormFile currFile=uploadBean.getFileName();		
				currFileName = currFile.toString();
				String newFileName="";
				String newFileExt="";
				int i=currFileName.lastIndexOf(".");
				if(i>0)
				{
					newFileName=currFileName.substring(0, i);
					newFileExt=currFileName.substring(i+1);
				}
		    	newFileName=newFileName+"_"+uploadBean.getDocketNumber().toString()+"."+newFileExt;
				documentPath=documentPath+newFileName;
				File newfile = new File(documentPath);
		    	boardOfReviewReverseDecisionBean.setFileName(documentPath);
		    	try {
				     FileOutputStream fos=new FileOutputStream(newfile);
				     if(uploadBean.getInputStream()!=null)
				     {
				       IOUtils.copy(uploadBean.getInputStream(), fos);
				     }
				   } catch (IOException e) {
					// TODO Auto-generated catch block

			if (LOGGER.isEnabledFor(Level.ERROR)) {
				LOGGER.error("error", e);
			}

				  }
		    	
		    	objAssembly.addBean(uploadBean);
		    	attachUploadDocumentIntoDms(objAssembly);
			}*/
	        
	        
	        
	        
	        if(nonMonAppealData != null){ 
	        	// get the BOR decision list frm the object assembly
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	nonMonAppealData.setBorRecommendationSet(null);
	        	// create nonMonBO
	        	NonMonAppeal nonMonAppealBO = new NonMonAppeal(nonMonAppealData);
	        	nonMonAppealBO.saveBORAffirmDecision(boardOfReviewReverseDecisionBean.getAdditionalRemarksForArfmWithEdit(),boardOfReviewReverseDecisionBean);
	        	// null is bor meeting data which is no more used
	        	
	        }else if(taxAppealData != null){
	        	//get the BOR decision list from the object assembly
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	taxAppealData.setBorRecommendationSet(null);
	        	TaxAppeal taxAppealBO = new TaxAppeal(taxAppealData);
	        	//taxAppealBO.saveBORAffirmDecision(borMemberList,borMeetingData,addRemarks);
	        	taxAppealBO.saveBORAffirmDecision(addRemarks,boardOfReviewReverseDecisionBean);
	        	
	        }else if(miscAppealData != null){
	        	borMemberList = (List)objAssembly.getBeanList(BORDecisionBean.class);
	        	miscAppealData.setBorRecommendationSet(null);
	        	MiscAppeal miscAppealBo = new MiscAppeal(miscAppealData);
	        	miscAppealBo.saveAffirmDecisionForBor(addRemarks,boardOfReviewReverseDecisionBean);

	        }else if(lateAppealData != null){
	        	LateAppeal lateAppealBO = new LateAppeal(lateAppealData);
	        	lateAppealData.setBorRecommendationSet(null);
	        	//uploadBean.setDocketNumber(miscAppealData.getDocketNumber());
	        	lateAppealBO.saveAffirmDecisionForBor(addRemarks,boardOfReviewReverseDecisionBean);
            }else if(nonAppearanceAppealData != null){
	        	NonApperanceAppeal nonApperanceAppealBO = new NonApperanceAppeal(nonAppearanceAppealData);
	        	nonAppearanceAppealData.setBorRecommendationSet(null);
	        	//uploadBean.setDocketNumber(miscAppealData.getDocketNumber());
	        	nonApperanceAppealBO.saveAffirmDecisionForBor(addRemarks,boardOfReviewReverseDecisionBean);
            }  
	        /*this part will save the recommendation data*/
	        List borRecommendationDataList = objAssembly.getComponentList(BORRecommendationData.class);
		    if (borRecommendationDataList!=null && borRecommendationDataList.size()>0){

		         BorRecommendation.saveOrUpdateOrDeleteRecommendationOnSaveDecision(borRecommendationDataList);
		    }
	        // closing the work item created for affirm with Edit decision
		    // next time same code will be used to close work item for proof of rading
//	        WorkflowItemBean workflowItemBean = (WorkflowItemBean) objAssembly.getFirstBean(WorkflowItemBean.class);
//		    IBaseWorkflowDAO baseWorkflowDAO = DAOFactory.instance.getBaseWorkflowDAO();
//		    baseWorkflowDAO.startWorkItem(workflowItemBean.getPersistentOid());
			return objAssembly;
		}
		
        // this method is used to save BOR Reverse Affirm Modify Decision
		@Override
		public IObjectAssembly saveBORReverseAffirmModifyDecision(IObjectAssembly objAssembly)
				throws BaseApplicationException, CloneNotSupportedException  {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveBORReverseAffirmModifyDecision");
		}
			NonMonAppealData nonMonAppealData = (NonMonAppealData)objAssembly.getFirstComponent(NonMonAppealData.class);
		    TaxAppealData taxAppealData = (TaxAppealData)objAssembly.getFirstComponent(TaxAppealData.class);
		    MiscAppealData miscAppealData  = (MiscAppealData)objAssembly.getFirstComponent(MiscAppealData.class);
		    LateAppealData lateAppealData  = (LateAppealData)objAssembly.getFirstComponent(LateAppealData.class);
		    NonAppearanceAppealData nonAppearanceAppealData  = (NonAppearanceAppealData)objAssembly.getFirstComponent(NonAppearanceAppealData.class);
		    AppealInfoBean appealInfoBean =(AppealInfoBean)objAssembly.getFirstBean(AppealInfoBean.class);
		    ReverseDecisionBean boardOfReviewReverseDecisionBean = (ReverseDecisionBean) objAssembly.getFirstBean(ReverseDecisionBean.class);
		    String ssnOrEan = null;
		    if(null != boardOfReviewReverseDecisionBean && StringUtility.isNotBlank(boardOfReviewReverseDecisionBean.getDecision())
		    		&& AppealDecisionCodeEnum.MODIFY.getName().equals(boardOfReviewReverseDecisionBean.getDecision()) &&
		    		null != appealInfoBean && StringUtility.isBlank(appealInfoBean.getInFavorAppellant()))
		    {
		    	throw new BaseApplicationException("access.app.verifylircdecision.infavorappellant.error");
		    }
		    if (AppellantOrOpponentEnum.CLAIMANT.getName().equals(appealInfoBean.getAppellantValue())){
	    		if (StringUtility.isNotBlank(appealInfoBean.getClaimantSsn())){
	    			ssnOrEan = appealInfoBean.getClaimantSsn();
	    		}
	    	}else if (AppellantOrOpponentEnum.EMPLOYER.getName().equals(appealInfoBean.getAppellantValue())){
	    		if(StringUtility.isNotBlank(appealInfoBean.getEmployerEan())){
	    			ssnOrEan = appealInfoBean.getEmployerEan();
	    		}
	    	}
        // CIF_03288
        // GeneralBean applBean= objAssembly.getFirstBean(GeneralBean.class);
		    
		   // boardOfReviewReverseDecisionBean.setProofReadingFlag(GlobalConstants.DB_ANSWER_YES);
//		    if (StringUtility.isNotBlank(appealInfoBean.getClaimantSsn())){
//		    	ssnOrEan = appealInfoBean.getClaimantSsn();
//		    }else if(StringUtility.isNotBlank(appealInfoBean.getEmployerEan())){
//		    	ssnOrEan = appealInfoBean.getEmployerEan();
//		    }
		    if (nonMonAppealData!=null){
		    	nonMonAppealData.setBorRecommendationSet(null);
			}else if (taxAppealData!=null){
				taxAppealData.setBorRecommendationSet(null);
			}else if (miscAppealData!=null){
				miscAppealData.setBorRecommendationSet(null);
			}else if (lateAppealData!=null){
				lateAppealData.setBorRecommendationSet(null);
			}else if (nonAppearanceAppealData!=null){
				nonAppearanceAppealData.setBorRecommendationSet(null);
			}
			// determineAppealFlow will not be blank when coming from appeal decision screen
			// and if it is not blank createWorkItemForProofOfReading will create work item for proof reading
			String determineAppealFlow =  (String)objAssembly.getData("determineAppealFlow");
			String decisionValue = (String)objAssembly.getData(AppealConstants.BOR_REVIEW_DECISION_VALUE);
			// this will contain decision value
			//ReverseDecisionBean boardOfReviewReverseDecisionBean = (ReverseDecisionBean) objAssembly.getFirstBean(ReverseDecisionBean.class);
			/* Modified BORAppealDecisionEnum.AFFIRM_WITH_EDIT to BORAppealDecisionEnum.AFFIRM*/
			/*CIF_02871||Defect_4092||SIT||Appeals||Decision like Dismiss, Other, Vacant,Deny Application for Review, Not Sitting etc are having issues.*/
			if (BORAppealDecisionEnum.REVERSE.getName().equals(decisionValue)
					|| BORAppealDecisionEnum.MODIFY.getName().equals(decisionValue)) {
				
				this.saveBORReverseDecision(objAssembly);
			}else if (BORAppealDecisionEnum.AFFIRM.getName().equals(decisionValue)) {
				this.saveBorAffirmWithEditDecision(objAssembly);
			}else if (BORAppealDecisionEnum.WITHDRAWAL_ON_RECORD.getName().equals(decisionValue)) {
				this.saveBorAffirmWithEditDecision(objAssembly);
			}else if (AppealDecisionCodeEnum.NTST.getName().equals(decisionValue)
					|| AppealDecisionCodeEnum.OTHR.getName().equals(decisionValue)
					|| AppealDecisionCodeEnum.VACANT.getName().equals(decisionValue)
					|| AppealDecisionCodeEnum.DISMISS.getName().equals(decisionValue)
					|| AppealDecisionCodeEnum.DENY_APPLICATION_REVIEW.getName().equals(decisionValue)
					|| AppealDecisionCodeEnum.CORRECTED.getName().equals(decisionValue)
					|| AppealDecisionCodeEnum.RECONSIDERED.getName().equals(decisionValue)
					|| AppealDecisionCodeEnum.SET_ASIDE.getName().equals(decisionValue)
					|| AppealDecisionCodeEnum.REMI.getName().equals(decisionValue)
					|| AppealDecisionCodeEnum.REMX.getName().equals(decisionValue)
					|| AppealDecisionCodeEnum.SHOW_CAUSE.getName().equals(decisionValue)) {
				this.saveBorAffirmWithEditDecision(objAssembly);
			}
			// closing work item for bor meeting
		/*	WorkflowItemBean workflowItemBean = (WorkflowItemBean) objAssembly.getFirstBean(WorkflowItemBean.class);
		    IBaseWorkflowDAO baseWorkflowDAO = DAOFactory.instance.getBaseWorkflowDAO();
		    baseWorkflowDAO.startWorkItem(workflowItemBean.getPersistentOid());*/
		    
		    DecisionData decisionMadeForAppealData = null;
		    Boolean isTaxAppeal=Boolean.FALSE;
		    AppealData appealData = null;
			if (nonMonAppealData!=null){
				appealData = nonMonAppealData;
				decisionMadeForAppealData = nonMonAppealData.getActiveDecisionData();
			}else if (taxAppealData!=null){
				appealData = taxAppealData;
				decisionMadeForAppealData = taxAppealData.getActiveDecisionData();
				isTaxAppeal=Boolean.TRUE;
			}else if (miscAppealData!=null){
				appealData = miscAppealData;
				decisionMadeForAppealData = miscAppealData.getActiveDecisionData();
			}else if (lateAppealData!=null){
				appealData = lateAppealData;
				decisionMadeForAppealData = lateAppealData.getActiveDecisionData();
				if(null != lateAppealData.getParentAppealData() && AppealTypeEnum.TAXA.getName().equals(lateAppealData.getParentAppealData()))
				{
					isTaxAppeal=Boolean.TRUE;
				}
				
			}else if (nonAppearanceAppealData!=null){
				appealData = nonAppearanceAppealData;
				decisionMadeForAppealData = nonAppearanceAppealData.getActiveDecisionData();
			}
		    
			
		    //AppealDecisionLetterData appealDecisionLetterData= (AppealDecisionLetterData)objAssembly.getFirstComponent(AppealDecisionLetterData.class, true);
			//appealDecisionLetterData.setProofreadingStatus(AppealProofReadingStatusEnum.CLOSED.getName());
			//AppealDecisionLetterBO appealLetterBO= new AppealDecisionLetterBO(appealDecisionLetterData);
			//appealLetterBO.saveOrUpdate();
			//AppealData appealData= appealDecisionLetterData.getDecisionData().getDecisionForAppealData();
			//DecisionData decisionData=appealDecisionLetterData.getDecisionData();
			Appeal appealBO=Appeal.getAppealBoByAppealData(appealData);
			//appealBO.generateCorrespondence(appealData, decisionData, CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
			DecisionData activeDecisionDataGivenForAppeal = Decision.getActiveDecisionDataByAppealId(appealData.getAppealId());
		    		    		    
			//R4UAT00012464 changed the top 4 lines starting with appealBO.generateCorrespondence(
			//to use activeDecisionDataGivenForAppeal instead of decisionMadeForAppealData
			
			if(appealData!=null)
			{
				String lircDecisionMailDate="";
				if(boardOfReviewReverseDecisionBean.getAppealInfoBean().getDecisionMailDate()!=null)
				{
					lircDecisionMailDate=DateFormatUtility.format(boardOfReviewReverseDecisionBean.getAppealInfoBean().getDecisionMailDate());
					appealData.setLircDecisionMailDate(lircDecisionMailDate);
				}
				//Set appeal alternate address in correspondence if alternate address is there in previous level
				 if(appealData!=null && null != appealData.getAppealledAgainstDecision() && null != appealData.getAppealledAgainstDecision().getDecisionForAppealData()
						 && null != appealData.getAppealledAgainstDecision().getDecisionForAppealData().getAppealAlternateAddressData())
				 {
					 appealData.setAppealAlternateAddressData(appealData.getAppealledAgainstDecision().getDecisionForAppealData().getAppealAlternateAddressData());
				 }
			}
			
			
        // CIF_03322 || Defect_5607
        String isFlowBenefits = "NO";
        if (objAssembly.getData(ViewConstants.CURRENT_APPLICATION_BENEFIT) != null)
        {
            isFlowBenefits = objAssembly.getData(ViewConstants.CURRENT_APPLICATION_BENEFIT).toString();
        }
		 // parent decision is the decision which has been recalled
			if (activeDecisionDataGivenForAppeal!=null){
				// parent decision only be there if decision has been recalled
				if (activeDecisionDataGivenForAppeal.getParentDecisionData()!=null){
					// not null means decision has been recalled
					List recallDecisionList = Decision.getRecallDecisionDataList(activeDecisionDataGivenForAppeal.getParentDecisionData().getDecisionID());
					if (recallDecisionList!=null && recallDecisionList.size()>0){
						RecallDecisionDetailData recallDecisionDetailData = (RecallDecisionDetailData) recallDecisionList.get(0);
						DecisionData lastDecisionDataMadeForAppeal = recallDecisionDetailData.getDecisionData();
						if (lastDecisionDataMadeForAppeal.getDecisionMailDate()!=null){
							appealBO.generateCorrespondence(appealData, activeDecisionDataGivenForAppeal, CorrespondenceCodeEnum.BOR_RECALL_LETTER.getName());
						}else{
							//CIF_P2_00782
                        // CIF_03288
                        if (!isTaxAppeal)
                        {
								appealBO.generateCorrespondence(appealData, activeDecisionDataGivenForAppeal, CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
								Appeal.generateCorr4680_5(appealData);
                        }
                        else
                        {
								appealBO.generateCorrespondence(appealData, activeDecisionDataGivenForAppeal, CorrespondenceCodeEnum.BOR_EMPLOYER_DECISION_LETTER.getName());
								Appeal.generateCorr4680_5_T(appealData);
                        }
						}
					}else{
						// docket has not been recalled, so function normally
                    // // CIF_03288
                    if (!isTaxAppeal)
                    {
							appealBO.generateCorrespondence(appealData, activeDecisionDataGivenForAppeal, CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
							Appeal.generateCorr4680_5(appealData);	
						}else{
							appealBO.generateCorrespondence(appealData, activeDecisionDataGivenForAppeal, CorrespondenceCodeEnum.BOR_EMPLOYER_DECISION_LETTER.getName());
							Appeal.generateCorr4680_5_T(appealData);	
						}
					}
				}else{
					// means decision has not been recalled- docket has not been recalled, so function normally
                // // CIF_03288
                if (!isTaxAppeal)
                {
						appealBO.generateCorrespondence(appealData, activeDecisionDataGivenForAppeal, CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
						Appeal.generateCorr4680_5(appealData);	
                }else{
						appealBO.generateCorrespondence(appealData, activeDecisionDataGivenForAppeal, CorrespondenceCodeEnum.BOR_EMPLOYER_DECISION_LETTER.getName());
						Appeal.generateCorr4680_5_T(appealData);	
                }
				}
			}else{
				// means decision has not been recalled- docket has not been recalled, so function normally
            // // CIF_03288
            if (!isTaxAppeal)
            {
					appealBO.generateCorrespondence(appealData, decisionMadeForAppealData, CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
					Appeal.generateCorr4680_5(appealData);
			}else{
					appealBO.generateCorrespondence(appealData, decisionMadeForAppealData, CorrespondenceCodeEnum.BOR_EMPLOYER_DECISION_LETTER.getName());
					Appeal.generateCorr4680_5_T(appealData);
			}
			}
			//CIF_02060	|| Defect_2827|| Correspondences for MODES-4680, MODES-4680-3, MODES-4680-5 to be generated.
			
			/*
			 * commented as of CIF_02333
			 * appealBO.generateCorrespondence(appealData,null,CorrespondenceCodeEnum.MODES_4680_5.getName());*/
	         
        // CIF_03288 || Defect_5607
			/*GenericWorkflowSearchBean bean = (GenericWorkflowSearchBean)objAssembly.fetchORCreateBean(GenericWorkflowSearchBean.class);
			bean.setGlobalContainerMemberLike("ssneanfein", ssnOrEan);
			bean.setGlobalContainerMember("type", "ssn");
			bean.setReceivedTimeBetween(new Date(), DateUtility.getNextDay(new Date()));
			String userId = super.getContextUserId();*/
		    //FSUserData fsUserDataObj = FSUser.getFSUserByUserId(userId);
			/*List listOfworkItems = Appeal.listOfworkItems(bean);
			for (int i=0;i<listOfworkItems.size();i++){
				WorkflowItemBean workFlowItemBean =  (WorkflowItemBean) listOfworkItems.get(i);
				RegularClaim.reassignWorkItems(workFlowItemBean.getPersistentOid(),userId);
			}*/
			
			
			/* Upload Document-Starts*/
		if (LOGGER.isInfoEnabled()) {
        LOGGER.info("Starting upload function info");
		}
		if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Starting upload function debug");
		}
			UploadDocumentService docService=new UploadDocumentService();
			objAssembly=docService.uploadLircDecisionLetterinFileServer(objAssembly,appealData, activeDecisionDataGivenForAppeal);
			/* Upload Document-Ends*/
			
			
			return objAssembly;
		}
		/*
		 * Method to load appeal decision letter details in flow of process proof reading
		 * */
		@Override
		public IObjectAssembly getBorProofReadingDetails(IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getBorProofReadingDetails");
		}
			
			NonMonAppealData nonMonAppealData = (NonMonAppealData)objAssembly.getFirstComponent(NonMonAppealData.class);
		    TaxAppealData taxAppealData = (TaxAppealData)objAssembly.getFirstComponent(TaxAppealData.class);
		    MiscAppealData miscAppealData  = (MiscAppealData)objAssembly.getFirstComponent(MiscAppealData.class);
		    Long appealId =null;
		    if (nonMonAppealData!=null){
		    	appealId = nonMonAppealData.getAppealId();
			}else if (taxAppealData!=null){
				appealId = taxAppealData.getAppealId();
			}else if (miscAppealData!=null){
				appealId = miscAppealData.getAppealId();
			}
			//Long appealId = Long.valueOf(workflowIteamBean.getBusinessKey());
			AppealDecisionLetterData appealDecisionLetterData= Appeal.fetchAppealDecisionLetterDetail(appealId);
			objAssembly.addComponent(appealDecisionLetterData, true);
			return objAssembly;
		}
		/*
		 * Method to update decision letter details in flow of process proof reading
		 * */
		@Override
		public IObjectAssembly saveBorProofReadingDetails(IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method saveBorProofReadingDetails");
		}
			
			AppealDecisionLetterData appealDecisionLetterData= (AppealDecisionLetterData)objAssembly.getFirstComponent(AppealDecisionLetterData.class, true);
			appealDecisionLetterData.setProofreadingStatus(AppealProofReadingStatusEnum.CLOSED.getName());
			AppealDecisionLetterBO appealLetterBO= new AppealDecisionLetterBO(appealDecisionLetterData);
			appealLetterBO.saveOrUpdate();
			AppealData appealData= appealDecisionLetterData.getDecisionData().getDecisionForAppealData();
			DecisionData decisionData=appealDecisionLetterData.getDecisionData();
			Appeal appealBO=Appeal.getAppealBoByAppealData(appealData);
			//appealBO.generateCorrespondence(appealData, decisionData, CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
			DecisionData activeDecisionDataGivenForAppeal = Decision.getActiveDecisionDataByAppealId(appealData.getAppealId());
			
			// parent decision is the decision which has been recalled
			if (activeDecisionDataGivenForAppeal!=null){
				// parent decision only be there if decision has been recalled
				if (activeDecisionDataGivenForAppeal.getParentDecisionData()!=null){
					// not null means decision has been recalled
					List recallDecisionList = Decision.getRecallDecisionDataList(activeDecisionDataGivenForAppeal.getParentDecisionData().getDecisionID());
					if (recallDecisionList!=null && recallDecisionList.size()>0){
						RecallDecisionDetailData recallDecisionDetailData = (RecallDecisionDetailData) recallDecisionList.get(0);
						DecisionData lastDecisionDataMadeForAppeal = recallDecisionDetailData.getDecisionData();
						if (lastDecisionDataMadeForAppeal.getDecisionMailDate()!=null){
							appealBO.generateCorrespondence(appealData, decisionData, CorrespondenceCodeEnum.BOR_RECALL_LETTER.getName());
						}else{
		                    appealBO.generateCorrespondence(appealData, decisionData, CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
						}
					}else{
						// docket has not been recalled, so function normally
						appealBO.generateCorrespondence(appealData, decisionData, CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
					}
				}else{
					// means decision has not been recalled- docket has not been recalled, so function normally
					appealBO.generateCorrespondence(appealData, decisionData, CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
				}
			}else{
				// means decision has not been recalled- docket has not been recalled, so function normally
				appealBO.generateCorrespondence(appealData, decisionData, CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
			}
			
			//appealBO.generateCorrespondence(appealData, decisionData, CorrespondenceCodeEnum.BOR_DECISION_LETTER.getName());
			WorkflowItemBean workflowItemBean = (WorkflowItemBean) objAssembly.getFirstBean(WorkflowItemBean.class);
			
			/*  IBaseWorkflowDAO baseWorkflowDAO = DAOFactory.instance.getBaseWorkflowDAO();
		    baseWorkflowDAO.startWorkItem(workflowItemBean.getPersistentOid());*/
			
			  // CIF_00982|| Code change w.r.t new BRMS approach
	        Map<String , Object> mapValues = new HashMap<String , Object>();
            
            mapValues.put("persistenceOid",workflowItemBean.getPersistentOid());

            WorkflowTransactionService wf = new WorkflowTransactionService();
                       
            wf.invokeWorkFlowOperation(WorkFlowOperationsEnum.START.getName(), mapValues); 
			return objAssembly;
		}
		
		@Override
		public IObjectAssembly getBORDecisionLetterReportParams(IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getBORDecisionLetterReportParams");
		}
			getAppealDecisionMaster(objAssembly);
			AppealData borAppealData  = (AppealData)objAssembly.getFirstComponent(AppealData.class, true);
			
			BORMeetingAppealMapData borMeetingAppealMapData= (BORMeetingAppealMapData) objAssembly.getData("borMeetingAppealMapData");
    		if(borMeetingAppealMapData == null){
			 borMeetingAppealMapData = BORMeetingAppealMap.fetchBORDecisionLetterReportParams(borAppealData.getAppealId());
    		}
    		
			AppealsDecisionLettersParagraphBuilder builder = new AppealsDecisionLettersParagraphBuilder();
			
			if(borMeetingAppealMapData!=null){
				BORMasterDocketData borMasterDocketData= borMeetingAppealMapData.getBorMasterDocketData();
				BORMeetingData borMeetingData= borMasterDocketData.getBorMeetingData();
//				Appeal tempAppeal= Appeal.findByPrimaryKey(borMeetingAppealMapData.getAppealData().getAppealId());
				
				DecisionData aljDecisionData= borAppealData.getAppealledAgainstDecision();
			
				ReverseDecisionBean boardOfReviewReverseDecisionBean = (ReverseDecisionBean)objAssembly.getFirstBean(ReverseDecisionBean.class);
				String borDecision= (String)objAssembly.getData(AppealConstants.BOR_REVIEW_DECISION_VALUE);
				
				
				// Logic for setting parameters for Affirm or Affirm Edit Decision Letter for Misc Appeal
//				if((AppealTypeEnum.MISC.getName().equals(borAppealData.getAppealType())
//							|| AppealTypeEnum.NON_MON.getName().equals(borAppealData.getAppealType()))
//						&& (BORAppealDecisionEnum.AFFIRM_WITH_EDIT.getName().equals(borDecision)
//							||BORAppealDecisionEnum.AFFIRM.getName().equals(borDecision))){
//
//					String additionalRemarks=boardOfReviewReverseDecisionBean.getAdditionalRemarksForArfmWithEdit();
//
//
//					builder.addParam(AppealConstants.BOR_APPEAL_FILE_DATE,DateFormatUtility.format(borAppealData.getAppealDate()));
//					builder.addParam(AppealConstants.BOR_APPEAL_FILE_DUE_DATE,"<MailDate>");
//					builder.addParam(AppealConstants.BOR_DECISION, additionalRemarks);
//					builder.addParam(AppealConstants.ALJ_DECISION_DATE,DateFormatUtility.format(aljDecisionData.getDecisionGivenDate()));
//					builder.addParam(AppealConstants.BOR_REVIEW_DATE,DateFormatUtility.format(borMeetingData.getReviewDate()));
//					objAssembly.addData("AppealsDecisionLettersParagraphBuilder", builder);
//				}
				
				String additionalRemarks="";
				if(boardOfReviewReverseDecisionBean !=null
						&& boardOfReviewReverseDecisionBean.getAdditionalRemarksForArfmWithEdit()!=null) {
					additionalRemarks=boardOfReviewReverseDecisionBean.getAdditionalRemarksForArfmWithEdit();
				}
				
				if(borAppealData!=null) {
					builder.addParam(AppealConstants.BOR_APPEAL_FILE_DATE,DateFormatUtility.format(borAppealData.getAppealDate()));
				}
				
				builder.addParam(AppealConstants.BOR_APPEAL_FILE_DUE_DATE,AppealConstants.DEFAULT_PARAM);
				builder.addParam(AppealConstants.BOR_DECISION, additionalRemarks);
				if(aljDecisionData!=null) {
					builder.addParam(AppealConstants.ALJ_DECISION_DATE,DateFormatUtility.format(aljDecisionData.getDecisionGivenDate()));
				} else if(borAppealData instanceof LateAppealData){
					LateAppealData lateAppealData = (LateAppealData)borAppealData;
					if(lateAppealData!=null && lateAppealData.getParentAppealData()!=null
							&& lateAppealData.getParentAppealData().getAppealledAgainstDecision()!=null
							&& lateAppealData.getParentAppealData().getAppealledAgainstDecision().getDecisionGivenDate()!=null){
						builder.addParam(AppealConstants.ALJ_DECISION_DATE,DateFormatUtility.format(lateAppealData.getParentAppealData().getAppealledAgainstDecision().getDecisionGivenDate()));
					}
				}
				if(borMeetingData!=null) {
					builder.addParam(AppealConstants.BOR_REVIEW_DATE,DateFormatUtility.format(borMeetingData.getReviewDate()));
				}
				
//				if((AppealTypeEnum.MISC.getName().equals(borAppealData.getAppealType())
//						|| AppealTypeEnum.NON_MON.getName().equals(borAppealData.getAppealType()))){
//					if (BORAppealDecisionEnum.AFFIRM_WITH_EDIT.getName().equals(borDecision)
//							||BORAppealDecisionEnum.AFFIRM.getName().equals(borDecision)){
//
//						String additionalRemarks="";
//						if(boardOfReviewReverseDecisionBean !=null
//								&& boardOfReviewReverseDecisionBean.getAdditionalRemarksForArfmWithEdit()!=null)
//							additionalRemarks=boardOfReviewReverseDecisionBean.getAdditionalRemarksForArfmWithEdit();
//
//
//						builder.addParam(AppealConstants.BOR_APPEAL_FILE_DATE,DateFormatUtility.format(borAppealData.getAppealDate()));
//						builder.addParam(AppealConstants.BOR_APPEAL_FILE_DUE_DATE,"<ENTER>");
//						builder.addParam(AppealConstants.BOR_DECISION, additionalRemarks);
//						builder.addParam(AppealConstants.ALJ_DECISION_DATE,DateFormatUtility.format(aljDecisionData.getDecisionGivenDate()));
//						builder.addParam(AppealConstants.BOR_REVIEW_DATE,DateFormatUtility.format(borMeetingData.getReviewDate()));
//
//					}else if (BORAppealDecisionEnum.REVERSE.getName().equals(borDecision)||
//							BORAppealDecisionEnum.MODIFY.getName().equals(borDecision)){
//						builder.addParam(AppealConstants.BOR_APPEAL_FILE_DUE_DATE,"<ENTER>");
//					}
//				}
			}else{
				builder.addParam(AppealConstants.BOR_APPEAL_FILE_DATE,AppealConstants.DEFAULT_PARAM);
				builder.addParam(AppealConstants.BOR_APPEAL_FILE_DUE_DATE,AppealConstants.DEFAULT_PARAM);
				builder.addParam(AppealConstants.BOR_DECISION, AppealConstants.DEFAULT_PARAM);
				builder.addParam(AppealConstants.ALJ_DECISION_DATE,AppealConstants.DEFAULT_PARAM);
				builder.addParam(AppealConstants.BOR_REVIEW_DATE,AppealConstants.DEFAULT_PARAM);
			}
			objAssembly.addData("AppealsDecisionLettersParagraphBuilder", builder);
			return objAssembly;
		}
        
        @Override
		public IObjectAssembly  decideBorDecisiononMajorityBasisForNoAppearanceAppeal(IObjectAssembly objAssembly) throws BaseApplicationException, CloneNotSupportedException {
		    
			List  decisionList = new ArrayList();
			decisionList = (List)objAssembly.getBeanList(BORDecisionBean.class);
			AppealData appealData = (AppealData)objAssembly.getFirstComponent(AppealData.class, true);
			//BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			int affirmCount = 0;
			int remandOnMeritCount = 0;
			if(decisionList != null && decisionList.size() != 0){
				for (Iterator i = decisionList.iterator(); i.hasNext(); ) {
					BORDecisionBean borDecisionBean = (BORDecisionBean) i.next();
					if(AppealDecisionCodeEnum.AFFIRM.getName().equals(borDecisionBean.getDecision())){
						affirmCount = affirmCount + 1;
					}/*else if(AppealDecisionCodeEnum.REMAND_ON_MERIT.getName().equals(borDecisionBean.getDecision())){
					//Defect_913 Starts,Commenetd out REMAND_ON_MERIT as not used in MO
						remandOnMeritCount = remandOnMeritCount + 1;
					}*/
					
				}
			}
			Appeal appealBO = Appeal.getAppealBoByAppealData(appealData);
			ReverseDecisionBean bean = new ReverseDecisionBean();
			
			String finalBorDecisionForLateAppeal = null;
			
			if (affirmCount>remandOnMeritCount){
	  			finalBorDecisionForLateAppeal = AppealDecisionCodeEnum.AFFIRM.getName();
	  			bean.setDecision(AppealDecisionCodeEnum.AFFIRM.getName());
	  		}else if (affirmCount<remandOnMeritCount){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

	  			//Defect_913 Starts,Commenetd out REMAND_ON_MERIT as not used in MO 
	  			//finalBorDecisionForLateAppeal = AppealDecisionCodeEnum.REMAND_ON_MERIT.getName();
	  			
	  		}else{
	  			// majority is not established
	  			throw new BaseApplicationException("error.access.app.bor.recommendation");
	  			// message to say that majority is not established
	  		}
			
			bean.setDecision(finalBorDecisionForLateAppeal);
			prepareReverseDecisionBeanForDecisionLetter(objAssembly, bean);
			
			appealBO.saveBORdecisionForNoAppearanceAppeal(affirmCount,remandOnMeritCount,bean);
			//LateAppeal appealBO =  new LateAppeal((LateAppealData)appealData);
			//appealBO.saveBORdecisionForLateAppeal(borMeetingData,allowCount,denyCount);
			return objAssembly;
			
		}
        
        //CQ 3666
        @Override
		public IObjectAssembly  loadRemandForInformationLetterData(IObjectAssembly objAssembly) throws BaseApplicationException, CloneNotSupportedException {
		    
        	getBORDecisionLetterReportParams(objAssembly);
        	AppealsDecisionLettersParagraphBuilder builder = (AppealsDecisionLettersParagraphBuilder) objAssembly.getData("AppealsDecisionLettersParagraphBuilder");
			BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
			AppealData appealData = (AppealData)objAssembly.getFirstComponent(AppealData.class, true);
			//AppealsDecisionLettersParagraphBuilder builder = new AppealsDecisionLettersParagraphBuilder();
			//builder.addParam(AppealConstants.BOR_APPEAL_FILE_DATE,DateFormatUtility.format(appealData.getAppealDate()));
			//builder.addParam(AppealConstants.BOR_APPEAL_FILE_DUE_DATE, AppealConstants.DEFAULT_PARAM);
			//builder.addParam(AppealConstants.ALJ_DECISION_DATE,DateFormatUtility.format(appealData.getAppealledAgainstDecision().getDecisionGivenDate()));
			//builder.addParam(AppealConstants.BOR_REVIEW_DATE,DateFormatUtility.format(borMeetingData.getReviewDate()));
			///
			//AppealsDecisionLettersParagraphBuilder
			String docketType = (String)objAssembly.getData("docketType");
						
			String decision=AppealDecisionCodeEnum.REMI.getName();
			//Remand for information
    
		    docketType = appealData.getAppealType();
		    if(AppealTypeEnum.NON_MON.getName().equalsIgnoreCase(appealData.getAppealType())){
		     docketType = MasterDocketTypeEnum.REGULAR.getName();
		    }else if (AppealTypeEnum.MISC.getName().equalsIgnoreCase(appealData.getAppealType())){
		     docketType = MasterDocketTypeEnum.MISC.getName();
		    }else if (AppealTypeEnum.LATE.getName().equalsIgnoreCase(appealData.getAppealType())){
		     docketType = MasterDocketTypeEnum.LATE_BOR.getName();
		    }else if (AppealTypeEnum.LDAD.getName().equalsIgnoreCase(appealData.getAppealType())){
		     docketType = MasterDocketTypeEnum.LATE_ALJ.getName();
		    
		    /*CIF_00393,Desc:Commented TAXA and TAX_INT from the Master Docket Type
		    else if (AppealTypeEnum.TAXA.getName().equalsIgnoreCase(appealData.getAppealType())){
		     docketType = MasterDocketTypeEnum.TAX.getName();
		    }else if (AppealTypeEnum.TAX_INT.getName().equalsIgnoreCase(appealData.getAppealType())){
		     docketType = MasterDocketTypeEnum.TAXI.getName();
		    }*/
		    }else if (AppealTypeEnum.NOAP.getName().equalsIgnoreCase(appealData.getAppealType())){
		     docketType = MasterDocketTypeEnum.NO_APPEARANCE.getName();
		    }
			 
			MstAppDecMaster appealDecisionMaster = Appeal
			.fetchAppealDecisionMasterByDocketTypeAndDecision(docketType, decision);
			
			ReverseDecisionBean bean = new ReverseDecisionBean();
			
			bean.setDecision(decision);
			if(appealDecisionMaster!=null)
			{
				bean.setCaseHistoryParagraph(getArrayListToString(builder.buildCaseHistoryParagraph(appealDecisionMaster.getAppDecMasterId())));
				bean.setReasonAndConclusionParagraph(getArrayListToString(builder.buildDecisionReasonParagraph(appealDecisionMaster.getAppDecMasterId())));
				bean.setFactFindingParagraph(getArrayListToString(builder.buildFactFindingParagraph(appealDecisionMaster.getAppDecMasterId())));
				bean.setIssueParagraph(getArrayListToString(builder.buildIssueParagraph(appealDecisionMaster.getAppDecMasterId())));
				bean.setAppealRightsParagraph(getArrayListToString(builder.buildAppealRightsParagraph(appealDecisionMaster.getAppDecMasterId())));
				bean.setDecisionReasonParagraph(getArrayListToString(builder.buildDecisionParagraph(appealDecisionMaster.getAppDecMasterId())));
			}
	    	
		
			objAssembly.addData("remandDecisionBean",bean);
			
			return objAssembly;
			
		}
        
        public IObjectAssembly getAppealDecisionMaster(IObjectAssembly objAssembly) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getAppealDecisionMaster");
		}

    		AppealData appealData = (AppealData) objAssembly
    				.getFirstComponent(AppealData.class, true);
    		AppealInfoBean appealInfoBean = (AppealInfoBean) objAssembly
			.getFirstBean(AppealInfoBean.class);
    		
    		//CQ 9597  Data may have been preloaded.
    		BORMeetingAppealMapData borMeetingAppealMapData= (BORMeetingAppealMapData) objAssembly.getData("borMeetingAppealMapData");
    		if(borMeetingAppealMapData == null){
    			borMeetingAppealMapData = BORMeetingAppealMap.fetchBORDecisionLetterReportParams(appealData.getAppealId());
    		}
    		
    		String docketType="";
    		String decision=(String)objAssembly.getData(AppealConstants.BOR_REVIEW_DECISION_VALUE);
    		if(borMeetingAppealMapData!=null && borMeetingAppealMapData.getBorMasterDocketData()!=null
    				&& borMeetingAppealMapData.getBorMasterDocketData().getDocketType()!=null)
    		{
    			docketType=borMeetingAppealMapData.getBorMasterDocketData().getDocketType();
    			// only in case of continued appeal status get appeal type ,
    			// since there is no decision letter templete with docket type as COND
    			 if (MasterDocketTypeEnum.CONTINUED.getName().equalsIgnoreCase(docketType)){
    				    
    				    docketType = appealData.getAppealType();
    				    if(AppealTypeEnum.NON_MON.getName().equalsIgnoreCase(appealData.getAppealType())){
    				     docketType = MasterDocketTypeEnum.REGULAR.getName();
    				    }else if (AppealTypeEnum.MISC.getName().equalsIgnoreCase(appealData.getAppealType())){
    				     docketType = MasterDocketTypeEnum.MISC.getName();
    				    }else if (AppealTypeEnum.LATE.getName().equalsIgnoreCase(appealData.getAppealType())){
    				     docketType = MasterDocketTypeEnum.LATE_BOR.getName();
    				    }else if (AppealTypeEnum.LDAD.getName().equalsIgnoreCase(appealData.getAppealType())){
    				     docketType = MasterDocketTypeEnum.LATE_ALJ.getName();
    				    
    				    /*CIF_00393,Desc:Commented TAXA and TAX_INT from the Master Docket Type
    				    else if (AppealTypeEnum.TAXA.getName().equalsIgnoreCase(appealData.getAppealType())){
    				     docketType = MasterDocketTypeEnum.TAX.getName();
    				    }else if (AppealTypeEnum.TAX_INT.getName().equalsIgnoreCase(appealData.getAppealType())){
    				     docketType = MasterDocketTypeEnum.TAXI.getName();
    				    }*/
    				    }else if (AppealTypeEnum.NOAP.getName().equalsIgnoreCase(appealData.getAppealType())){
    				     docketType = MasterDocketTypeEnum.NO_APPEARANCE.getName();
    				    }
    			 }
    			
    		}
    		
    		
    		MstAppDecMaster appealDecisionMaster =
    			Appeal.fetchAppealDecisionMasterByDocketTypeAndDecision(
    					docketType, decision);
    		if(appealDecisionMaster!=null) {
				objAssembly.addComponent(appealDecisionMaster, true);
			}
    		return objAssembly;
    	}
		
		/*
		 * This private method will create work item for Proof of Reading
		 * */
		private void createWorkItemForProofOfReading(AppealData appealData,String determineAppealFlow,String ssnOrEan){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method createWorkItemForProofOfReading");
		}
             //	 creating worik item for proof reading
			// determineAppealFlow not blank  means its has to create work item for prrof reading
			// this flow will be traversed gain from proof reading,at that time it  sholud not create any
			// more work item,in proof reading flow determineAppealFlow will be blank
			appealData = Appeal.findAppealandAppealPartySetbyAppealId(appealData.getAppealId());
			Map objClaimantEmployerInfo = AppealUtil.getClaimantAndEmployerInformationByAppealId(appealData);
			
			if (StringUtility.isNotBlank(determineAppealFlow)){
				IBaseWorkflowDAO workflowItem = DAOFactory.instance.getBaseWorkflowDAO();
		        String userId = super.getContextUserId();
		        FSUserData fsUserDataObj = FSUser.getFSUserByUserId(userId);
				BaseAccessDsContainerBean obj = new BaseAccessDsContainerBean();
				obj.setUserId(userId);
				GlobalDsContainerBean contBean = obj.getNewGlobalDsContainerBean();
				contBean.setBusinessKey(String.valueOf(appealData.getAppealId()));

				if(StringUtility.isNotBlank(ssnOrEan)){
					contBean.setSsneanfein(ssnOrEan);
				}
				
				if (AppellantOrOpponentEnum.CLAIMANT.getName().equals(appealData.getAppellant())){
					contBean.setTypeAsSsn();
					contBean.setName((String) objClaimantEmployerInfo.get(AppealConstants.KEY_CLAIMANT_NAME));
				}
				else if (AppellantOrOpponentEnum.EMPLOYER.getName().equals(appealData.getAppellant())){
					contBean.setTypeAsEan();
					contBean.setName((String) objClaimantEmployerInfo.get(AppealConstants.KEY_EMPLOYER_NAME));
				}
				
//				contBean.setName(StringUtility.getFullName(fsUserDataObj.getFirstName(),fsUserDataObj.getLastName()));
				workflowItem.createAndStartProcessInstance(WorkflowProcessTemplateConstants.AppealWorkflowConstants.BOR_PROOF_READING, obj);
			}
		}
		
        @Override
		public IObjectAssembly  saveLateToALJAtBORLevel(IObjectAssembly objAssembly) throws BaseApplicationException {

        	LateAppealData aljLateAppealData = (LateAppealData)objAssembly.getFirstComponent(LateAppealData.class);
        	
        	LateDeniedAppealData lateDeniedAppealData = (LateDeniedAppealData)objAssembly.getFirstComponent(LateDeniedAppealData.class);
        	
        	LateDeniedAppeal lateDeniedAppealBO = new LateDeniedAppeal(lateDeniedAppealData);
        	
        	//CQ 9550 Check to make sure the page was not submitted twice.
       	 	if(lateDeniedAppealData != null && lateDeniedAppealData.getAppealId() != null){
		if (LOGGER.isInfoEnabled()) {
	       		LOGGER.info("Refresh Warning:File Appeal tried to save twice.");
		}
	       		return objAssembly;
            }
        	
    		//Long docketNumber=lateDeniedAppealBO.getNextSequenceValueForDocketNumber("SEQ_T_DOCKET_NUMBER");
    		
    		lateDeniedAppealData.setDocketNumber(aljLateAppealData.getDocketNumber());
    		
        	lateDeniedAppealBO.saveOrUpdate();
        	
        	//Update the Active Flag of ALJ LateAppealData
        	aljLateAppealData.setActiveFlag(GlobalConstants.DB_ANSWER_NO);
        	LateAppeal lateAppealBO = new LateAppeal(aljLateAppealData);
        	
        	lateAppealBO.saveOrUpdate();
        	
        	if(lateDeniedAppealData.getAppealType()!=null && lateDeniedAppealData.getAppealType().equals(AppealTypeEnum.TAXA.getName()))
			{
        		lateDeniedAppealBO.generateAcknowledgementLetters(lateDeniedAppealData, CorrespondenceCodeTaxEnum.BOR_ACKNOWLEDGEMENT_TAX.getName(), "NONE");
			}
			else
			{
				// CIF_P2_01121
		        lateDeniedAppealBO.generateAcknowledgementLetters(lateDeniedAppealData, CorrespondenceCodeEnum.BOR_ACKNOWLEDGEMENT.getName(), "NONE");
			}
        	
        	
        
        	
        	// added code for correspondence end
        	lateAppealBO.createBusinessComments(lateDeniedAppealData,AppealConstants.APP_FILED_LATETOALJ_BOR, true);
			return objAssembly;
		}
        
        @Override
		public IObjectAssembly  saveLateAppealOfLateToAljAppeal(IObjectAssembly objAssembly) throws BaseApplicationException {

        	List listOfLateAppeals = (List)objAssembly.getComponentList(LateAppealData.class);
        	
        	LateDeniedAppealData lateDeniedAppealData = (LateDeniedAppealData)objAssembly.getFirstComponent(LateDeniedAppealData.class);
        	
        	LateDeniedAppeal lateDeniedAppealBO = new LateDeniedAppeal(lateDeniedAppealData);
        	lateDeniedAppealData.setAppealStatus(AppealStatusEnum.LATE_APPEALED.getName());
        	lateDeniedAppealData.setActiveFlag(GlobalConstants.DB_ANSWER_NO);
        	lateDeniedAppealBO.saveOrUpdate();
        	
        	Iterator appIter = listOfLateAppeals.iterator();
    		while (appIter.hasNext()) {
    			LateAppealData parentLateAppData = (LateAppealData)appIter.next();

	        	//Update the Active Flag of ALJ LateAppealData
	    		if(DecisionStatusEnum.APPEALS_JUDGE.getName().equalsIgnoreCase(parentLateAppData.getAppealLevel())){
	    			parentLateAppData.setActiveFlag(GlobalConstants.DB_ANSWER_NO);
		        	LateAppeal lateAppealBO = new LateAppeal(parentLateAppData);
		        	lateAppealBO.saveOrUpdate();
	    		}else{
		        	LateAppeal lateAppealBO = new LateAppeal(parentLateAppData);
		        	lateAppealBO.saveOrUpdate();
	    		}
	    		lateDeniedAppealBO.createBusinessComments(lateDeniedAppealData,AppealConstants.APP_FILED_LATE_BOR, true);
    		}
    		
    		if(lateDeniedAppealData.getAppealType()!=null && lateDeniedAppealData.getAppealType().equals(AppealTypeEnum.TAXA.getName()))
			{
    			lateDeniedAppealBO.generateAcknowledgementLetters(lateDeniedAppealData, CorrespondenceCodeTaxEnum.BOR_ACKNOWLEDGEMENT_TAX.getName(), "NONE");
			}
			else
			{
				// CIF_P2_01121
		        lateDeniedAppealBO.generateAcknowledgementLetters(lateDeniedAppealData, CorrespondenceCodeEnum.BOR_ACKNOWLEDGEMENT.getName(), "NONE");
			}
    		
    		
        
    		
        	//TODO Insert Correspondence Records
    		
			return objAssembly;
			
		}
        //This method is used to get the correspondence id for the docket number entered in BOR Appeal Information Screen
        private IObjectAssembly getCorrespondenceIdForBORRecommendation(IObjectAssembly objectAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getCorrespondenceIdForBORRecommendation");
		}
        	
        	Long appealId = objectAssembly.getPrimaryKeyAsLong();
        	AppealData appealData = Appeal.fetchAppealAlongWithAppealChildByAppealId(appealId);
        	List appealList = Appeal.getAllAppealListByDocketNumber(appealData.getDocketNumber());
        	Long decisionId = null;
        	 
        	List correspondenceList = new ArrayList();
        	List decisionList = new ArrayList();
        	if (appealList!=null && appealList.size()>0){
        		for (int i=0;i<appealList.size();i++){
        			
        			AppealData appealDataFromList = (AppealData)appealList.get(i);
        			
        			if (appealDataFromList.getAppealledAgainstDecision()!=null){
        				if (DecisionStatusEnum.APPEALS_JUDGE.getName().equals(appealDataFromList.getAppealLevel())){
        					AppealCorrespondenceBean appealCorrespondenceBean = new AppealCorrespondenceBean();
            				decisionId =appealDataFromList.getAppealledAgainstDecision().getDecisionID();// adjucator decision
            				if (AppealTypeEnum.NON_MON.getName().equals(appealDataFromList.getAppealType())){
            					Long issueId = appealDataFromList.getAppealledAgainstDecision().getIssueData().getIssueId();
                				objectAssembly.setPrimaryKey(IssueData.class,issueId);
            				}
            				appealCorrespondenceBean.setDecisionId(decisionId.toString());
            				appealCorrespondenceBean.setAppealType(appealDataFromList.getAppealType());
            				appealCorrespondenceBean.setDecisionLevel(DecisionStatusEnum.ADJUDICATED.getName());
            				decisionList.add(appealCorrespondenceBean);
            				Set aljDecisionSet = appealDataFromList.getDecisions();
            				Iterator it = aljDecisionSet.iterator();
            				while(it.hasNext()){
            					AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = new AppealCorrespondenceBean();
            					DecisionData aljDecisionData = (DecisionData) it.next();
            					appealCorrespondenceBeanForAljDesc.setDecisionId(aljDecisionData.getDecisionID().toString());
            					appealCorrespondenceBeanForAljDesc.setDecisionLevel(DecisionStatusEnum.APPEALS_JUDGE.getName());
            					appealCorrespondenceBeanForAljDesc.setAppealType(appealDataFromList.getAppealType());
            					decisionList.add(appealCorrespondenceBeanForAljDesc);
            					}
            				}
            			}
        			else { //other than non mon appeals.
        				if (DecisionStatusEnum.APPEALS_JUDGE.getName().equals(appealDataFromList.getAppealLevel())){
            				Set aljDecisionSet = appealDataFromList.getDecisions();
            				Iterator it = aljDecisionSet.iterator();
            				while(it.hasNext()){
            					AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = new AppealCorrespondenceBean();
            					DecisionData aljDecisionData = (DecisionData) it.next();
            					appealCorrespondenceBeanForAljDesc.setDecisionId(aljDecisionData.getDecisionID().toString());
            					appealCorrespondenceBeanForAljDesc.setDecisionLevel(DecisionStatusEnum.APPEALS_JUDGE.getName());
            					appealCorrespondenceBeanForAljDesc.setAppealType(appealDataFromList.getAppealType());
            					decisionList.add(appealCorrespondenceBeanForAljDesc);
            					}
            				}
            			
        			}
        			}
        		}
        		for (int j=0;j<decisionList.size();j++){
    				AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = (AppealCorrespondenceBean) decisionList.get(j);
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
    							}
    						}
    						if (AppealPartyTypeEnum.CLAIMANT.getName().equalsIgnoreCase(appealData.getAppellant()) ||
    								AppealPartyTypeEnum.CLAIMANT.getName().equalsIgnoreCase(appealData.getOpponent())){// claimant is involved
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
    						}
    					}else{
    						appealCorrespondenceBean = getCorrespondenceDataByParameter6(appealCorrespondenceBeanForAljDesc);
    					}
    				}else{// for all other appeal types
   						appealCorrespondenceBean = getCorrespondenceDataByParameter6(appealCorrespondenceBeanForAljDesc);
    				}
    				correspondenceList.add(appealCorrespondenceBean);
    			}
        		if(correspondenceList!=null && correspondenceList.size()>0){
        			objectAssembly.addBeanList(correspondenceList,true);
        		}
	        	objectAssembly.setPrimaryKey(appealId);
	        	objectAssembly.addComponent(appealData,true);
	        	return objectAssembly;
        }
        
    	private AppealCorrespondenceBean getCorrespondenceDataByParameter6(AppealCorrespondenceBean appealCorrespondenceBean){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getCorrespondenceDataByParameter6");
		}
    	
    	List correspondenceList = new ArrayList();
    	List correspondenceDataByParameter6 = Correspondence.getCorrespondenceDataByParameter6(appealCorrespondenceBean.getDecisionId(),CorrespondenceParameterEnum.DECISION_ID.getName());
		if (correspondenceDataByParameter6!=null && correspondenceDataByParameter6.size()>0){
			CorrespondenceData corrData = (CorrespondenceData)correspondenceDataByParameter6.get(0);
			appealCorrespondenceBean.setCorrespondenceId(corrData.getCorrespondenceId().toString());
		}
		return appealCorrespondenceBean;
    }
        private IObjectAssembly getCorrespondenceIdForBORMeeting(IObjectAssembly objectAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getCorrespondenceIdForBORMeeting");
		}
        	
        	Long appealId = objectAssembly.getPrimaryKeyAsLong();
        	AppealData appealData = Appeal.fetchAppealAlongWithAppealChildByAppealId(appealId);
        	List appealList = Appeal.getAllAppealListByDocketNumber(appealData.getDocketNumber());
        	Long decisionId = null;
        	 
        	List correspondenceList = new ArrayList();
        	List decisionList = new ArrayList();
        	if (appealList!=null && appealList.size()>0){
        		for (int i=0;i<appealList.size();i++){
        			
        			AppealData appealDataFromList = (AppealData)appealList.get(i);
        			
       				if (DecisionStatusEnum.APPEALS_JUDGE.getName().equals(appealDataFromList.getAppealLevel())){
           				Set aljDecisionSet = appealDataFromList.getDecisions();
           				Iterator it = aljDecisionSet.iterator();
           				while(it.hasNext()){
           					AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = new AppealCorrespondenceBean();
           					DecisionData aljDecisionData = (DecisionData) it.next();
           					appealCorrespondenceBeanForAljDesc.setDecisionId(aljDecisionData.getDecisionID().toString());
           					appealCorrespondenceBeanForAljDesc.setDecisionLevel(DecisionStatusEnum.APPEALS_JUDGE.getName());
           					appealCorrespondenceBeanForAljDesc.setAppealType(appealDataFromList.getAppealType());
           					decisionList.add(appealCorrespondenceBeanForAljDesc);
          					}
           				}
        		
        			}
        		}
        		for (int j=0;j<decisionList.size();j++){
    				AppealCorrespondenceBean appealCorrespondenceBeanForAljDesc = (AppealCorrespondenceBean) decisionList.get(j);
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
    							}
    						}
    						if (AppealPartyTypeEnum.CLAIMANT.getName().equalsIgnoreCase(appealData.getAppellant()) ||
    								AppealPartyTypeEnum.CLAIMANT.getName().equalsIgnoreCase(appealData.getOpponent())){// claimant is involved
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
    						}
    					}else{
    						appealCorrespondenceBean = getCorrespondenceDataByParameter6(appealCorrespondenceBeanForAljDesc);
    					}
    				}else{// for all other appeal types
   						appealCorrespondenceBean = getCorrespondenceDataByParameter6(appealCorrespondenceBeanForAljDesc);
    				}
    				correspondenceList.add(appealCorrespondenceBean);
    			}
        		if(correspondenceList!=null && correspondenceList.size()>0){
        			objectAssembly.addBeanList(correspondenceList,true);
        		}
	        	objectAssembly.setPrimaryKey(appealId);
	        	objectAssembly.addComponent(appealData,true);
	        	return objectAssembly;
        }
        
    	
//    	 Method to retrieve the BOR Appeal which is active.

   
    	@Override
		public IObjectAssembly getBORAppealToRecall(IObjectAssembly objAssembly) throws BaseApplicationException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getBORAppealToRecall");
		}

    		String docketNumber = (String) objAssembly.getData("docketNumber");
    		//Get the BOR Level Appeal which is Closed and Active
    		AppealData appealData = Appeal.fetchBORAppealToRecall(Long.valueOf(docketNumber));
    		
    		if(appealData != null){
    			objAssembly.setPrimaryKey(appealData.getAppealId());
    		}
    		if (appealData == null) {
    			throw new BaseApplicationException("access.app.recallbordecision.noappeal");
    		}
    		
    		//Check if there is an Active BOR Decision
    		if(appealData.getActiveDecisionData() == null){
    			throw new BaseApplicationException("access.app.recallbordecision.noactivedecision");
    		}
    		
    		Appeal appealBO = Appeal.getAppealBoByAppealData(appealData);
    		
    		//Evict the Appeal Data So that in the following method we can load the appeal data with full details
    		appealBO.evictAppealDataFromSession();
    		
    		//Get the Appeal information if the Appeal is Active and Decision can be recalled
    		objAssembly = getAppealInfoForBORMeeting(objAssembly);
    		
    		return objAssembly;
    	}
    	
    	//Process BOR Appeal Decision for Recall
    	
    	@Override
		public IObjectAssembly processBORDecisionRecall(IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method processBORDecisionRecall");
		}
    		
    		AppealData appealData = (AppealData)objAssembly.getFirstComponent(AppealData.class,true);
    		//CQ 16225, CQ 18534
			IAppealDAO appealDao = DAOFactory.instance.getAppealDAO();
			appealDao.evict(appealData);
			appealData = appealDao.findByAppealId(appealData.getAppealId());

    		RecallDecisionDetailData recallData = (RecallDecisionDetailData)objAssembly.getFirstComponent(RecallDecisionDetailData.class);

    		appealData.setAppealStatus(AppealStatusEnum.CONTINUED.getName());
    		appealData.setHearingScheduled(GlobalConstants.DB_ANSWER_NO);
    		
    		appealData.getDecisions();
//    		recallDecisionData
    		appealData.getActiveDecisionData().setRecallDecisionData(recallData);
    		
    		recallData.setDecisionData(appealData.getActiveDecisionData());
    		
    		Appeal objAppealBO = Appeal.getAppealBoByAppealData(appealData);
    		
    		/*
    		 * All changes done for defect 9291
    		 * If appeal is being recalled on the same day no need to insert record into correspondence
    		 * */
    		if (appealData.getActiveDecisionData()!=null){
    			if (appealData.getActiveDecisionData().getDecisionMailDate()!=null){
    				// mail date is not blank means last decision has been mailed out
    				// so generate BOR Recall notice in that case
    				objAppealBO.generateCorrespondence(appealData, appealData.getActiveDecisionData(), CorrespondenceCodeEnum.BOR_NOTICE_RECALL.getName());
    			}else{
    				// decision mail date is blank means recall is happening on the same day
    				/*
    				 * If recall is happenning on the same day the while recalling delete existing correspondence records from T_CORRESPONDENCE
    				 * so that if user recalls today and makes the final decision tomorrow those BOR decision letters does not get picked up
    				 * by Correspondence batch
    				 * */
    				List correspondenceListForBOR = new ArrayList();
					correspondenceListForBOR = Correspondence.getCorrespondenceDataByParameter6WithMailDateNull(appealData.getActiveDecisionData().getDecisionID().toString(),
        	    			CorrespondenceParameterDescriptionEnum.DECISION_ID.getName());
        	    	        			
        			if (correspondenceListForBOR!=null && correspondenceListForBOR.size()>0){
    	    			for (int i=0;i<correspondenceListForBOR.size();i++){
    	    				CorrespondenceData corrData = (CorrespondenceData) correspondenceListForBOR.get(i);
    		    			Correspondence correspondenceBO = new Correspondence(corrData);
    		    			correspondenceBO.delete();
    	    			}
    	    		}
    			}
    		}
    		
    		/* Previous deision will not be made inacative as long as new decision is not given out
    		 when new decision is given out existing active decision will be made inactive
    		 this change is done to handle BOR Recall for defect 9291
    		 While giving decision in case of recall code in NonMon or MIsc etc looks for active decision data
    		 to get the decision as active decision while recalling decision wont be made inactive*/
    		
    		//appealData.getActiveDecisionData().setActiveFlag(GlobalConstants.DB_ANSWER_NO);
    		
    		objAppealBO.saveOrUpdate();
    		
    		//adding history comment
    		objAppealBO.createBusinessComments(appealData,AppealConstants.APP_RECALL_DEC_BOR);
    		   		
    		
    		recallBorChildAppealsDecision(appealData,recallData);
    		
    		
    		return objAssembly;
    	}
    	
    	
    	private static void recallBorChildAppealsDecision(AppealData parentAppealData, RecallDecisionDetailData parentRecallData){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method recallBorChildAppealsDecision");
		}

			
			if(AppealTypeEnum.NON_MON.getName().equalsIgnoreCase(parentAppealData.getAppealType()))
			{
				IAppealDAO dao = DAOFactory.instance.getAppealDAO();
				List childAppList = dao.getAllLeadAppeals(parentAppealData.getAppealId());
				//pulls all child appeals, even closed ones
				if (childAppList!=null && childAppList.size()>0){
					for (int i=0;i<childAppList.size();i++){
						NonMonAppealData nonMonAppealDataForChildAppeal = (NonMonAppealData)childAppList.get(i);
						DecisionData decisionDataForChildAppeal = nonMonAppealDataForChildAppeal.getActiveDecisionData();
						if (!(decisionDataForChildAppeal==null ||
								(AppealDecisionCodeEnum.WITHDRAWAL.getName().equalsIgnoreCase(decisionDataForChildAppeal.getAppealDecisionDetailData().getAppealDecisionCode()) ||
										AppealDecisionCodeEnum.WITHDRAWAL_ON_RECORD.getName().equalsIgnoreCase(decisionDataForChildAppeal.getAppealDecisionDetailData().getAppealDecisionCode())))){
							Appeal appealBO =Appeal.getAppealBoByAppealData(nonMonAppealDataForChildAppeal);
							Set deciDataSet = nonMonAppealDataForChildAppeal.getDecisions();
							//Assuming lazy is false. decision data will be there
							Iterator deciIterator = deciDataSet.iterator();
							//Find the child's active decision, create the recall record, and prepare the correspondences.
							while(deciIterator.hasNext()){
								DecisionData deciData = (DecisionData)deciIterator.next();
								if (GlobalConstants.DB_ANSWER_YES.equals(deciData.getActiveFlag())) {

									boolean isMailedFlag = true;
									if (deciData.getDecisionMailDate() == null) {
										isMailedFlag = false;
									}

									//copy the majority of the recall information from the parent's recall
									RecallDecisionDetailData childRecallData = new RecallDecisionDetailData();
									childRecallData.setDecisionData(deciData);
									childRecallData.setRecallAction(parentRecallData.getRecallAction());
									childRecallData.setRecallDate(new Date());
									childRecallData.setRecallReason(parentRecallData.getRecallReason());
									childRecallData.setRecallExplanation(parentRecallData.getRecallExplanation());
									BaseHibernateDAO baseHibDao = new BaseHibernateDAO();
									baseHibDao.saveOrUpdate(childRecallData);


									if(isMailedFlag){
										appealBO.generateCorrespondence(nonMonAppealDataForChildAppeal, deciData, CorrespondenceCodeEnum.BOR_RECALL_LETTER.getName());
									} else {
										List correspondenceListForBOR = new ArrayList();
										correspondenceListForBOR = Correspondence.getCorrespondenceDataByParameter6WithMailDateNull(deciData.getDecisionID().toString(),
												CorrespondenceParameterDescriptionEnum.DECISION_ID.getName());

										if (correspondenceListForBOR!=null && correspondenceListForBOR.size()>0){
											for (int j=0;j<correspondenceListForBOR.size();j++){
												CorrespondenceData corrData = (CorrespondenceData) correspondenceListForBOR.get(j);
												Correspondence correspondenceBO = new Correspondence(corrData);
												correspondenceBO.delete();
											}
										}
									}


									break; 
									//Typically only one decision can be active in an Appeal. So set that to inactive and break
								}
							}

							nonMonAppealDataForChildAppeal.setAppealStatus(AppealStatusEnum.APPEALED.getName());

							IAppealDAO appDao = DAOFactory.instance.getAppealDAO();
							appDao.saveOrUpdate(nonMonAppealDataForChildAppeal,true);
							//For Historr Comment.

							appealBO.createBusinessComments(nonMonAppealDataForChildAppeal,AppealConstants.APP_RECALL_DEC_BOR_PAR);

						}
					}
				}
			}
		}
    	   	   	
    	
    	// this method will check the appeal status and if status is Closed or reviewed or Remanded to Lower level
    	// then throw businnes error
    	//CIF _00477 Starts
    	@Override
	    public IObjectAssembly checkAppealStatus(IObjectAssembly objAssembly) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method checkAppealStatus");
		}
    		
    		String appealId = (String) objAssembly.getPrimaryKey();
    		AppealData appealData = Appeal.findByAppealId(Long.valueOf(appealId));
    		objAssembly.addData("currentAppeal",appealData);
    		/*CIF_00476 Starts,Desc:Checked for Appeal Status */
    		//CIF_01395 Veracode Change
    		//if(appealData.getAppealStatus()!=null && appealData.getAppealStatus()!=""){   
    		//||AppealStatusEnum.CLOSED.getName().equalsIgnoreCase(appealData.getAppealStatus())
    		if(appealData.getAppealStatus()!=null && !"".equalsIgnoreCase(appealData.getAppealStatus())){
    		if (AppealStatusEnum.REVIEWED.getName().equalsIgnoreCase(appealData.getAppealStatus())||
    				AppealStatusEnum.REMAND_LOWER.getName().equalsIgnoreCase(appealData.getAppealStatus())){
    			throw new BaseApplicationException("access.app.bordecision.appealstatus");
    		}
    		/*CIF_00476 Ends */
    		}
    	
    		return objAssembly;
    	}
    	//CIF _00477 Ends
    	//Defect #9074 added this validation for userid
    	@Override
		public IObjectAssembly validateBorMemberInRecommendationProcess(IObjectAssembly objAssembly) throws BaseApplicationException{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method validateBorMemberInRecommendationProcess");
		}
    		
    		BorAppealsToScheduleBean borAppealsToScheduleBean=(BorAppealsToScheduleBean)objAssembly.getFirstBean(BorAppealsToScheduleBean.class);
    		String userId = super.getUserContext().getUserId();
			FSUserData fsuserData = FSUser.getFSUserByUserId(userId);
    		if (!GlobalConstants.DB_ANSWER_NO.equals(borAppealsToScheduleBean.getBorRecommendationUserId())){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

    			/* Commented below code as to allow LIRC to recommend multiple times.*/
    			/*if (!fsuserData.getMdesUserId().equals(borAppealsToScheduleBean.getBorRecommendationUserId())){
    				throw new BaseApplicationException("access.app.borrecommendation.userid");
    			}*/
    		}
    		return objAssembly;
    	}
   	
    /**
     * 
     * @param objAssembly
     * @return
     * @throws BaseApplicationException
     */
    @Override
	public IObjectAssembly getBORMasterDocketCountsByDocketType(IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getBORMasterDocketCountsByDocketType");
		}
    	IBaseJdbcDAO baseJdbcDAO = DAOFactory.instance.getBaseJdbcDAO();
    	List<DynaBean> masterDocketCountList = baseJdbcDAO.executeNamedQueryAsDynaBean("getBORMasterDocketCountsByDocketType", null);
    	List<MasterDocketCountBean> returnList = new ArrayList<MasterDocketCountBean>();
    	
    	for(DynaBean dynaBean : masterDocketCountList){
    		MasterDocketCountBean masterDocketCountBean = new MasterDocketCountBean();
    		masterDocketCountBean.setMasterDocketCode((String) dynaBean.get("dockettype"));
    		masterDocketCountBean.setCount((Integer) dynaBean.get("countno"));
    		returnList.add(masterDocketCountBean);
    	}
    	
    	objAssembly.addBeanList(returnList, true);
   		return objAssembly;
   	}
    
    /**
     * 
     * @param objAssembly
     * @return
     */
    @Override
	public IObjectAssembly getBoardMemberDocketCountsByDocketType(IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getBoardMemberDocketCountsByDocketType");
		}
    	IBaseJdbcDAO baseJdbcDAO = DAOFactory.instance.getBaseJdbcDAO();
    	String[] listForAppealTypes = (String[])objAssembly.getData("AppealTypesForBoardMemberDocketCountSearch");
    	BorAppealsToScheduleBean borAppealsToScheduleBean = (BorAppealsToScheduleBean)objAssembly.getFirstBean(BorAppealsToScheduleBean.class);
    	
    	while(listForAppealTypes.length < 8){
    		listForAppealTypes[listForAppealTypes.length] = ""; 
    		//9 params are there in SQL.
    	}
    	String[] params = new String[9];
    	int i=0;
    	for(String s : listForAppealTypes){
    		params[i] = s;
    		i++;
    	}
    	params[8] = borAppealsToScheduleBean.getBorMeetingId();//Last param in SQL is BorMeetingId
    	
    	List<DynaBean> memberDocketCountList =
    		baseJdbcDAO.executeNamedQueryAsDynaBean("getBoardMemberDocketCountsByDocketType",
    				params);
    	
    	List<MasterDocketCountBean> returnList = new ArrayList<MasterDocketCountBean>();
    	List<MasterDocketTypeEnum> masterDocketTypeEnumList = MasterDocketTypeEnum.getEnumList();
    	
		for(MasterDocketTypeEnum masterDocketTypeEnum : masterDocketTypeEnumList ){//Query Result may not contain ALL types
			//Set count for ALL types(from Enum) to display
			String docketCodeFromEnum = masterDocketTypeEnum.getName();
			Integer count = 0;
			for(DynaBean dynaBean : memberDocketCountList){
				if(docketCodeFromEnum.equals(dynaBean.get("dockettype"))){
					count = (Integer) dynaBean.get("countno");
					break;
				}
			}
			MasterDocketCountBean masterDocketCountBean = new MasterDocketCountBean();
			masterDocketCountBean.setMasterDocketCode(docketCodeFromEnum);
			masterDocketCountBean.setCount(count);
			returnList.add(masterDocketCountBean);
		}
    	
    	objAssembly.addBeanList(returnList, true);
   		return objAssembly;
   	}
    
    /**
     * 
     */
    //CIF _00477 Starts
    @Override
    public IObjectAssembly getDocketCountsForBORmeetingByDocketType(IObjectAssembly objAssembly){
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method getDocketCountsForBORmeetingByDocketType");
		}
    	IBaseJdbcDAO baseJdbcDAO = DAOFactory.instance.getBaseJdbcDAO();
    	List<String> appealTypeList = (List)objAssembly.getData("appealTypeList");
    	while(appealTypeList.size() < 8){
    		appealTypeList.add("");
    	}
    	BORMeetingData borMeetingData = (BORMeetingData)objAssembly.getFirstComponent(BORMeetingData.class);
    	String[] params = new String[9];
    	params[0] = borMeetingData.getBorMeetingId().toString();//First param is the MeetingId
    	int i = 1;
    	for(String s : appealTypeList){
    		params[i] = s;
    		i++;
    	}   	
    	
    	List<DynaBean> memberDocketCountList = 
    		baseJdbcDAO.executeNamedQueryAsDynaBean("getDocketCountsForBORmeetingByDocketType", 
    				params);
    	List<MasterDocketCountBean> returnList = new ArrayList<MasterDocketCountBean>();    	
    	List<MasterDocketTypeEnum> masterDocketTypeEnumList = MasterDocketTypeEnum.getEnumList();
    	
		for(MasterDocketTypeEnum masterDocketTypeEnum : masterDocketTypeEnumList ){
			//Query Result may not contain ALL types
			//Set count for ALL types(from Enum) to display
			String docketCodeFromEnum = masterDocketTypeEnum.getName();
			Integer count = 0;			
			for(DynaBean dynaBean : memberDocketCountList){
				if(docketCodeFromEnum.equals(dynaBean.get("dockettype"))){
					count = (Integer) dynaBean.get("countno");
					break;
				}
			}
			MasterDocketCountBean masterDocketCountBean = new MasterDocketCountBean();
			masterDocketCountBean.setMasterDocketCode(docketCodeFromEnum);
			masterDocketCountBean.setCount(count);
			returnList.add(masterDocketCountBean);		
		}
    	
		while(appealTypeList.indexOf("") > 0){
			//Remove the unnecessary empty strings
			appealTypeList.remove(appealTypeList.indexOf(""));
		}		
		
    	objAssembly.addBeanList(returnList, true);
    	return objAssembly;
    }
    //CIF _00477 Ends
    
    /**
     * This is the overridder method to get the Users from the Database
     * 
     */
  @cif_wy(impactNumber = "P1-APP-27" , requirementId = "FR_1632", designDocName = "04 UIC", designDocSection = "4.7", dcrNo = "", mddrNo = "DCR_MDDR_153", impactPoint = "New")
    protected List<FSUserData> getUserDataListForUicCommesionMembers(String userName){
    	return null;	
    }
}