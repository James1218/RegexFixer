/* PaymentDeductionHistoryAction.java
 * This action corresponds to Claimant Payment Deduction History Screen 
 * jsp				: paymentdeductionhistory.jsp
 * Action			: PaymentDeductionHistoryAction.java
 * Form				: CinInqForm.java
 * tiles-defs.xml	: .paymentdeductionhistory (acccessms/WEB-INF/jsp)
 */

package gov.state.uim.cin.struts.inq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.state.uim.common.ServiceLocator;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.struts.BaseLookUpDispatchAction;

/**
 * @author - MDES
 * @version 1.0
 * Retrieves the payment deduction history of a claimant for a given claimantId.
 */

/**
 * @struts.action 
 * 		path="/paymentdeductionhistory" name="cininqform" scope="session"
 *      input=".paymentdeductionhistory" validate="true" parameter="method"
 *                
 * @struts.action-set-property value = "true" property = "load"
 * @struts.action-forward name="back" path= ".claimantdetails" redirect="false"
 * @struts.action-set-property value = "back" property = "forwards"
 * @struts.action-set-property value = "/claimantdetails" property = "preLoadPath"
 * @struts.action-forward name="failure" path= ".systemError" redirect="false"
 *                   
 * @struts.action-set-property value = "gov.state.uim.cin.service.CinInqService" 
 *     	property = "bizServiceClass"
 *     
 * @struts.action-set-property value = "getPaymentDeductionHistory" 
 * 		property = "loadServiceMethod"   
 */


public class PaymentDeductionHistoryAction extends BaseLookUpDispatchAction {
	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(PaymentDeductionHistoryAction.class);
	
	/**
	 * Method executed during the load of page. 
	 */
	public void load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method load");
		}
		
		CinInqForm currentForm = (CinInqForm) form;
		ArrayList details = new ArrayList();
		IObjectAssembly objAssembly = getObjectAssemblyFromSession(request);
		
		/*This gets the ObjectAssembly from the session and
		 * checks to see if it null. If the ObjectAssembly is null,
		 * it creates a new instance.*/
		
		if (objAssembly == null){
			objAssembly = ServiceLocator.instance.getObjectAssembly();
		}
		
		String claimantId = super.getParameter("claimantId",request);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("claimantId = " + claimantId);
 		}
		
		/* This calls the service method*/
		objAssembly = super.executeLoadServiceMethod(mapping, objAssembly,request);
		
		List listClaimant = (List) objAssembly.getData("claimant");
		
		 if ((listClaimant !=null) && (listClaimant.size() != 0)){
				
				DynaBean dynaBeanClaimant = (DynaBean)listClaimant.get(0);
				currentForm.setSsn(dynaBeanClaimant.get("ssn").toString());
				
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("claimantid = " + (dynaBeanClaimant.get("claimantid").toString()));
	 		}
			
			currentForm.setClaimantId(dynaBeanClaimant.get("claimantid").toString());
		
			details.add(details.size(),dynaBeanClaimant);
				
		} else {
				
			details.add(details.size(),null);
				
		}		
		
		List listPaymentDeductionInformation = (List) objAssembly.getData("paymentDeductionInformation");
		
		if((listPaymentDeductionInformation != null) && (listPaymentDeductionInformation.size() != 0)){
			//DynaBean dynaBeanPaymentDeduction = (DynaBean)listPaymentDeductionInformation.get(0);
			details.add(1,listPaymentDeductionInformation);
			
		} else {
			details.add(1,null);
		}
		
		currentForm.setDetails(details);
	}
	
	
	/**
	 *  Method to map buttons and corresponding actions
	 */
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("access.popup", "popup");
		return map;
	}
	
	/**
	 * Method to map actions and corresponding service methods
	 */
	protected Map getServiceKeyMethodName() {
		return null;
	}
	
	/**
	 * Method for cancel button. The cancel button takes  
	 * to the Claimant Details Screen.
	 */
	
	protected ActionForward cancelled(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method cancelled");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Cancelled method called in " + this.getClass().getName());
		}
		return (super.backForward(mapping));
	}		
	

	public List<String> getNonValidateKey() {
		List<String> list = new ArrayList<String>();
		list.add("access.popup");
		 return list;
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