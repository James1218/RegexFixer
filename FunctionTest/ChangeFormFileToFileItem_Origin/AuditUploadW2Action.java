package gov.state.uim.audit.struts;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.audit.bean.AuditDetailsCommonBean;
import gov.state.uim.audit.bean.AuditDetailsFlowBean;
import gov.state.uim.audit.bean.AuditUploadW2Bean;
import gov.state.uim.audit.common.AuditUtils;
import gov.state.uim.audit.common.ReadAuditW2Excel;
import gov.state.uim.audit.data.AuditW2DataEntryData;
import gov.state.uim.audit.data.AuditW2SSNWageData;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.struts.BaseLookUpDispatchAction;
import gov.state.uim.util.lang.BigDecimalUtility;
import gov.state.uim.util.lang.StringUtility;

/**
 * @struts.action path="/audituploadw2" name="audituploadw2form" scope="request"
 *                input=".audituploadw2" validate="true" parameter="method"
 * 
 * @struts.action-set-property value = "true" property = "load"
 * 
 * @struts.action-set-property value =
 *                             "/auditw2w3reconcilation,/auditw2dataentry,/auditdetailentrylist,/audituploadw2"
 *                             property = "preLoadPath"
 * 
 * @struts.action-set-property value="continue,back,finishlater,samescreen"
 *                             property="forwards"
 * 
 * @struts.action-forward name="continue" path=".auditw2w3reconcilation"
 *                        redirect="false"
 * 
 * @struts.action-forward name="back" path=".auditw2dataentry" redirect="false"
 * 
 * @struts.action-forward name="finishlater" path= ".auditdetailentrylist"
 *                        redirect="false"
 * 
 * @struts.action-forward name="samescreen" path=".audituploadw2"
 *                        redirect="false"
 * 
 * @struts.action-set-property 
 *                             value="gov.state.uim.audit.service.AuditService"
 *                             property = "bizServiceClass"
 * @struts.action-set-property value="true" property="onErrorSamePage"
 * 
 * 
 */
public class AuditUploadW2Action extends BaseLookUpDispatchAction {
	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(AuditUploadW2Action.class);

	public void load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method load");
		}

		IObjectAssembly objectAssembly = getObjectAssemblyFromSession(request);
		if (objectAssembly == null) {
			objectAssembly = ServiceLocator.instance.getObjectAssembly();
		}
		AuditUploadW2Form thisForm = (AuditUploadW2Form) form;
		objectAssembly
				.getFirstBean(AuditDetailsFlowBean.class);
		super.removeForwardFromLoad(request);

		AuditDetailsCommonBean auditDetailsCommonBean = objectAssembly
				.getFirstBean(AuditDetailsCommonBean.class);
		AuditUtils.copyFromDataToForm(auditDetailsCommonBean, thisForm);
		setObjectAssemblyInSession(request, objectAssembly);

	}

	@Override
	protected Map<String, String> getServiceKeyMethodName() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("finishlater", "saveAuditDetailData");
		map.put("cont", "saveAuditDetailData");
		return map;
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("access.continue", "cont");
		map.put("access.finishlater", "finishlater");
		map.put("access.back", "back");
		return map;
	}

	protected List<String> getNonValidateKey() {
		List<String> list = new ArrayList<String>();
		list.add("access.back");
		list.add("access.finishlater");
		return list;
	}

	/*
	 * Method for Pre-Back button.
	 */
	public IObjectAssembly preback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method preback");
		}

		IObjectAssembly objAssembly = getObjectAssemblyFromSession(request,
				true);
		setObjectAssemblyInSession(request, objAssembly);
		return objAssembly;
	}

	/*
	 * Method for Back button.
	 */
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method back");
		}

		IObjectAssembly objAssembly = getObjectAssemblyFromSession(request,
				true);
		setObjectAssemblyInSession(request, objAssembly);
		return (backForward(mapping));

	}

	/*
	 * Pre-method for Continue button.
	 */
	@SuppressWarnings("unchecked")
	public IObjectAssembly precont(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method precont");
		}
		// Initialize the form
		AuditUploadW2Form currentform = (AuditUploadW2Form) form;
		// Session processing
		IObjectAssembly objectAssembly = getObjectAssemblyFromSession(request,
				true);
		if (objectAssembly == null) {
			objectAssembly = ServiceLocator.instance.getObjectAssembly();
		}
		// set the data entry method
		AuditDetailsFlowBean auditDetailsFlowBean = objectAssembly
				.getFirstBean(AuditDetailsFlowBean.class);
		auditDetailsFlowBean.setW2DataEntryMethod("auditenterw2s");

		// Initialize the corresponding entity
		AuditW2DataEntryData auditW2DataEntryData = objectAssembly
				.fetchORCreate(AuditW2DataEntryData.class);

		AuditDetailsCommonBean auditDetailsCommonBean = objectAssembly
				.getFirstBean(AuditDetailsCommonBean.class);
		String taxableWageBase = auditDetailsCommonBean.getTaxableWageBase();
		AuditUtils.copyFromFormToData(currentform, auditW2DataEntryData);

		// Read excel file
		ReadAuditW2Excel excel = new ReadAuditW2Excel();
		List<AuditUploadW2Bean> ssnWagesList = new ArrayList<AuditUploadW2Bean>();
		FormFile formFile = currentform.getFilePath();
		try {
			ssnWagesList = excel.getExcelData(formFile.getInputStream());
		} catch (Exception e) {
			ActionMessages errors = super.getActionMessages(request);
			errors.add("fileuploaded", new ActionMessage(
					"error.audit.audituploadw2.fileformat"));
			super.addErrors(request, errors);
			super.doNotExecuteService(request);
			return objectAssembly;
		}
		BigDecimal wagesSum = BigDecimalUtility.getZeroBigDecimal();
		BigDecimal taxableWagesSum = BigDecimalUtility.getZeroBigDecimal();
		objectAssembly.addBeanList(ssnWagesList, true);
		if (checkFormErrors(request, currentform, objectAssembly))
			return objectAssembly;
		if (ssnWagesList != null && !ssnWagesList.isEmpty()) {
			Set<AuditW2SSNWageData> ssnBeanDataSet = new HashSet<AuditW2SSNWageData>();
			for (AuditUploadW2Bean ssnwage : ssnWagesList) {
				AuditW2SSNWageData auditW2SSNWageData = new AuditW2SSNWageData();
				String ssn = ssnwage.getSsn().replaceAll("[\\s\\-()]", "");
				if (StringUtility.isNotBlank(ssn)) {
					if (StringUtility.isNumeric(ssn)
							&& (GenericValidator.minLength(ssn, 9))) {
						auditW2SSNWageData.setSsn(ssn);
					}
					if ((ssnwage.getAuditW2SSNWageId() != null)
							&& (StringUtility.isNotBlank(ssnwage
									.getAuditW2SSNWageId()))) {
						auditW2SSNWageData.setAuditW2SSNWageId(Long
								.parseLong(ssnwage.getAuditW2SSNWageId()));
					}
					auditW2SSNWageData.setWage((BigDecimalUtility
							.getBigDecimalValue(ssnwage.getWages())));
					wagesSum = wagesSum.add(BigDecimalUtility
							.getBigDecimalValue(ssnwage.getWages()));
					int res = BigDecimalUtility
							.getBigDecimalValue(ssnwage.getWages())
							.compareTo(
									BigDecimalUtility
											.getBigDecimalFromFormattedAmount(taxableWageBase));
					if (res == 1)// / first value is greater
					{
						auditW2SSNWageData
								.setTaxableWage(BigDecimalUtility
										.getBigDecimalFromFormattedAmount(taxableWageBase));
						taxableWagesSum = taxableWagesSum
								.add(BigDecimalUtility
										.getBigDecimalFromFormattedAmount(taxableWageBase));
					} else {
						auditW2SSNWageData.setTaxableWage(BigDecimalUtility
								.getBigDecimalValue(ssnwage.getWages()));
						taxableWagesSum = taxableWagesSum.add(BigDecimalUtility
								.getBigDecimalValue(ssnwage.getWages()));
					}
					auditW2SSNWageData
							.setAuditW2DataEntryData(auditW2DataEntryData);
					ssnBeanDataSet.add(auditW2SSNWageData);
				}
			}

			auditW2DataEntryData.setTotalWageBase(BigDecimalUtility
					.getBigDecimalFromFormattedAmount(taxableWageBase));
			auditW2DataEntryData.setTotalWage(wagesSum);
			auditW2DataEntryData.setTotalTaxableWage(taxableWagesSum);
			auditW2DataEntryData.replaceAuditW2SSNWageDataSet(ssnBeanDataSet);
			objectAssembly.addComponent(auditW2DataEntryData, true);
		}

		else {
//			System.out.println("No records found");
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("Empty else statement");
			}

		}

		if (request.getSession().getAttribute("isReviewed")
				.equals(Boolean.TRUE)) {
			// R4UAT00029637
			super.doNotExecuteService(request);
		}

		return objectAssembly;

	}

	/*
	 * Method for continue button. The continue button forwards to the Screen.
	 */
	public ActionForward cont(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method cont");
		}
		IObjectAssembly objAssembly = super
				.getObjectAssemblyFromSession(request);
		/**************** Audit Details Flow ****************/
		AuditDetailsFlowBean auditDetailsFlowBean = objAssembly
				.getFirstBean(AuditDetailsFlowBean.class);
		auditDetailsFlowBean.setContinueFlag(true);
		super.removeForwardFromLoad(request);
		/*************************************************/
		ActionMessages errors = super.getActionMessages(request);
		if (errors.size() > 0)
			return (mapping.findForward("samescreen"));
		if (objAssembly.hasBusinessError()) {
			super.addBusinessErrorInRequest(request, objAssembly
					.getFirstBusinessErrorInfo().getErrorCode(), objAssembly
					.getFirstBusinessErrorInfo().getErrorCode());
			objAssembly.removeBusinessError();
			return (mapping.findForward("samescreen"));
		}
		setObjectAssemblyInSession(request, objAssembly);
		return (super.continueForward(mapping));
	}

	/*
	 * Pre-method for finish later button.
	 */
	public IObjectAssembly prefinishlater(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method prefinishlater");
		}
		IObjectAssembly objAssembly = getObjectAssemblyFromSession(request,
				true);
		setObjectAssemblyInSession(request, objAssembly);
		return objAssembly;
	}

	/*
	 * Method for finish later button.
	 */
	public ActionForward finishlater(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method finishlater");
		}
		IObjectAssembly objAssembly = getObjectAssemblyFromSession(request,
				true);
		AuditUtils.cleanAllDataExceptPrimaryKey(objAssembly);
		setObjectAssemblyInSession(request, objAssembly);
		return (mapping.findForward("finishlater"));
	}

	private boolean checkFormErrors(HttpServletRequest request,
			AuditUploadW2Form form, IObjectAssembly objAssembly) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method checkFormErrors");
		}
		ActionMessages errors = super.getActionMessages(request);
		List<AuditUploadW2Bean> ssnWagesList = objAssembly
				.getBeanList(AuditUploadW2Bean.class);
		int len = 0;
		if ((ssnWagesList != null) && (!ssnWagesList.isEmpty())) {
			len = ssnWagesList.size();
			AuditUploadW2Bean auditUploadW2Bean = null;
			for (int i = 0; i < len; i++) {
				auditUploadW2Bean = (AuditUploadW2Bean) ssnWagesList.get(i);

				// If the all fields in row is blank should not validate them
				boolean blankRow = (GenericValidator
						.isBlankOrNull(auditUploadW2Bean.getSsn()) && GenericValidator
						.isBlankOrNull(auditUploadW2Bean.getWages()));
				boolean oneblankRow = (GenericValidator
						.isBlankOrNull(auditUploadW2Bean.getWages()) || (auditUploadW2Bean
						.getWages().contentEquals("0.0")));
				if (oneblankRow) {
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
							"access.auditupload.dataentry.excel"));
				}
				if (!blankRow) {
					String ssn = auditUploadW2Bean.getSsn();
					if ((GenericValidator.isBlankOrNull(ssn))) {
						errors
								.add(
										ActionErrors.GLOBAL_MESSAGE,
										new ActionMessage(
												"access.auditupload.dataentry.excel",
												"access.auditenterw2s.dataentry.tablename",
												String.valueOf(i + 1),
												"access.auditenterw2s.ssn"));
					} else if (!StringUtility.isNumeric(ssn)) {
						errors.add(ActionErrors.GLOBAL_MESSAGE,
								new ActionMessage(
										"error.access.table.ssn.notanumber",
										String.valueOf(i + 1)));

					} else if (!GenericValidator.minLength(ssn, Integer
							.parseInt(GlobalConstants.SSN_LENGTH))) {
						errors.add(ActionErrors.GLOBAL_MESSAGE,
								new ActionMessage(
										"error.access.audittable.minlength",
										String.valueOf(i + 1)));
					} else if ((!(GenericValidator
							.isBlankOrNull(auditUploadW2Bean.getSsn())))) {
						if ((GenericValidator.isBlankOrNull(auditUploadW2Bean
								.getWages()))
								|| ((auditUploadW2Bean.getWages()
										.contentEquals("0.0")))) {
							errors
									.add(
											ActionErrors.GLOBAL_MESSAGE,
											new ActionMessage(
													"access.auditupload.dataentry.excel",
													String.valueOf(i + 1)));
						}
						if (auditUploadW2Bean.getWages().matches("[a-zA-Z]")) {

							errors
									.add(
											ActionErrors.GLOBAL_MESSAGE,
											new ActionMessage(
													"access.auditupload.dataentry.excel",
													i + 1));

						}

					}

				}
			}
		} else {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"access.auditupload.dataentry.excel"));
		}

		if (errors.size() > 0) {
			super.addErrors(request, errors);
			return true;
		}
		return false;
	}

}