
package gov.state.uim.nmon.struts;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
//CIF_P2_00281 VIEW CORRESPONDENCES - Removed unused import

import gov.state.uim.common.ServiceLocator;
import gov.state.uim.domain.bean.CalendarBean;
import gov.state.uim.domain.data.EmployerData;
import gov.state.uim.domain.data.RegisteredEmployerData;
import gov.state.uim.framework.cache.CacheUtility;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.search.GenericSearchBean;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.struts.BaseSearchLookUpDispatchAction;
import gov.state.uim.util.lang.DateUtility;
import gov.state.uim.util.lang.StringUtility;

/**
 * @struts.action 
 * 		path="/employercorrespondencerecentresponses" name="employercorrespondencerecentresponsesform" scope="request"
 *      input=".employercorrespondencerecentresponses" validate="true" parameter="method"
 *   
 * @struts.action-set-property value = "true" property = "load"
 * @struts.action-set-property value = "true" property = "onErrorSamePage"
 * 
 *  @struts.action-forward	name="home" path= ".home"	redirect="false"
 *
 * @struts.action-set-property 
 * 		value = "gov.state.uim.nmon.service.NonMonCorrespondenceService" 
 *     	property = "bizServiceClass"
 */

public class EmployerCorrespondenceRecentResponsesAction extends BaseSearchLookUpDispatchAction {
	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(EmployerCorrespondenceRecentResponsesAction.class);	
	
	
	public void load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method load");
		}
		EmployerCorrespondenceRecentResponsesForm currentForm =(EmployerCorrespondenceRecentResponsesForm)form;
		List holidayList = CacheUtility.getHolidayCache();
		Calendar cal=Calendar.getInstance();
		cal=DateUtility.addBusinessDays(cal, 1, holidayList);
		CalendarBean toCal=new CalendarBean(cal.getTime());
		currentForm.setCorrSentDateTo(toCal);
		currentForm.setCorrSentDateFrom(new CalendarBean(
				DateUtility.addCalendarDays(cal.getTime(), -30)));
	}
	
	/**
	 *  Method to map buttons and corresponding actions
	 */
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("access.search","search");
		map.put("access.home", "home");
		map.put("access.popup", "popup");
		return map;
	}
	
	/**
	 * Method to map actions and corresponding service methods
	 */
	protected Map getServiceKeyMethodName() {
		HashMap map=new HashMap();
		return map;
	}

	/**
	 * Method to map non-validate buttons
	 */	
	protected List getNonValidateKey() {
		List<String> list = new ArrayList<String>();
		list.add("access.popup");
		return list;
	}
	
	
		
	/**
	 * Pre-method for search button. The continue button forwards 
	 * to the Effective Date Screen.
	 */
	public IObjectAssembly presearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method presearch");
		}
		IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request);
		GenericSearchBean bean = new GenericSearchBean();
		objAssembly.addBean(bean, true);
		EmployerCorrespondenceRecentResponsesForm currentForm =(EmployerCorrespondenceRecentResponsesForm)form;
		//CIF_P2_00281 VIEW CORRESPONDENCES - Commented out code	
		//CorrespondenceSearchTypeEnum corrEnum = CorrespondenceSearchTypeEnum.getEnum(currentForm.getSelectedCorrType());
		EmployerData regEmployerData=(EmployerData)objAssembly.getFirstComponent(RegisteredEmployerData.class);
		if(regEmployerData!=null) {
			//bean.addCriteria("employerid",regEmployerData.getEmployerId());	
			bean.addBindParameter(regEmployerData.getEan());
		}
		
		//CIF_P2_00281 VIEW CORRESPONDENCES  Start		
		if (currentForm.getUnread() == 'o')
		{
			
			if(currentForm.getSsnBean()!= null && StringUtility.isNotBlank(currentForm.getSsnBean().getSsn()))
			{
				//bean.addCriteria("ssn",currentForm.getSsnBean().getSsn());
				bean.addBindParameter(currentForm.getSsnBean().getSsn());
				bean.setSearchName("searchemployercorrespondencerecentresponsesforeanssnunread");
			}
			else
			{
				bean.setSearchName("searchemployercorrespondencerecentresponseswithoutssnunread");
			}
			
			
		}
		
		else{
		
		//if(CorrespondenceSearchTypeEnum.BENEFITS.equals(corrEnum)) {
			if(currentForm.getSsnBean()!= null && StringUtility.isNotBlank(currentForm.getSsnBean().getSsn()))
			{
				//bean.addCriteria("ssn",currentForm.getSsnBean().getSsn());
				bean.addBindParameter(currentForm.getSsnBean().getSsn());
				bean.setSearchName("searchemployercorrespondencerecentresponsesforeanssn");
			}
			else
			{
				bean.setSearchName("searchemployercorrespondencerecentresponseswithoutssn");
			}
		}
	//	}
		//else if(CorrespondenceSearchTypeEnum.APPEALS.equals(corrEnum)) {
		//	bean.setSearchName("searchemployercorrespondencerecentappealsforean");
	//	}
		
		bean.addCriteria("corrsentdatefrom",currentForm.getCorrSentDateFrom().getValue());
		bean.addCriteria("corrsentdateto",currentForm.getCorrSentDateTo().getValue());
		//bean.addCriteria("ean",currentForm.getEan());
		currentForm.setEan(objAssembly.getEan());
		
	    request.getSession().setAttribute("employercorrespondencerecentresponsesform", currentForm);
	  //CIF_P2_00281 end		
        return objAssembly;    			
	}
	
	
	
	
	/*
	 * Pre-method for continue button. The continue button forwards 
	 * to the Effective Date Screen.
	 */
	public IObjectAssembly prehome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method prehome");
		}
		IObjectAssembly objAssembly = getObjectAssemblyFromSession(request,true);
		
		setObjectAssemblyInSession(request, objAssembly);
		return objAssembly;		
	}

	/*
	 * Method for continue button. The continue button forwards 
	 * to the Effective Date Screen.
	 */
	public ActionForward home(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method home");
		}
		
		// Session handling		
		IObjectAssembly objAssembly = getObjectAssemblyFromSession(request);
		setObjectAssemblyInSession(request, objAssembly);

		return (mapping.findForward("home"));

	}


	public IObjectAssembly prepopup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method preback");
		}
		IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request);
		return objAssembly;
	}

	public ActionForward popup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method back");
		}
		IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request);
		return mapping.getInputForward();
	}
}