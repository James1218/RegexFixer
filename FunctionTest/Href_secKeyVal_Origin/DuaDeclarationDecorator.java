package gov.state.uim.web.search;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Level;

import gov.state.uim.ApplicationProperties;
import gov.state.uim.SessionConstants;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.framework.exception.BaseApplicationException;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.util.lang.StringUtility;
import gov.state.uim.web.taglib.function.SecureValues;


/**
 * 
 * @author TCS
 *
 */
public class DuaDeclarationDecorator extends TabDecorator{
	private final static AccessLogger LOGGER = ServiceLocator.instance
			.getLogger(DuaDeclarationDecorator.class);	
		
	/**
	 * 
	 * @return
	 * @throws DisplayDecoratorException
	 */
	public String getDescription() throws DisplayDecoratorException{
		
		String encKey = (String) super.getPageContext().getSession().getAttribute(SessionConstants.ENCRYPTION_KEY);
        if(StringUtility.isBlank(encKey)){
                        encKey = ApplicationProperties.PROTECTED_KEY_STRING;
        }
		Long duaDeclarationId = (Long)((DynaBean)this.getCurrentRowObject()).get("duadeclarationid");
		String description = (String)((DynaBean)this.getCurrentRowObject()).get("description");
		String contextRoot = ((HttpServletRequest)this.getPageContext().getRequest()).getContextPath();
//		String select = "<a href=\"" + contextRoot + "/linkaction.do?forwardName=duadeclaration&amp;duaDeclarationId=" + duaDeclarationId + "\" >" + description + "</a>" ;
//		String select = "<a href=\"" + contextRoot + "/linkaction.do?forwardName=updateduadeclaration&amp;duaDeclarationId=" + duaDeclarationId + "\" >" + description + "</a>" ;//New forward for CR
		//	CIF-00018 Added new forward link for hyperlink.
		
		// @cif_wy(impactNumber="P1-BSP-007", requirementId="FR_1210",
		// designDocName="01 DUA Declaration.docx",designDocSection="2.4",
		// dcrNo="", mddrNo="",
		// impactPoint="Start")
		SecureValues secValues = new SecureValues(encKey);
		// @cif_wy(impactNumber="P1-BSP-007", requirementId="FR_1210",
		// designDocName="01 DUA Declaration.docx",designDocSection="2.4",
		// dcrNo="", mddrNo="",
		// impactPoint="End")
		String select = null;
		
		String duaDeclarationIdStr = (null != duaDeclarationId) ? duaDeclarationId.toString() : null;
		try {
			//select = "<a href=\"" + contextRoot + "/linkaction.do?forwardName=updduadeclaration&amp;" + secValues.encryptValue("duaDeclarationId")+"=" + secValues.encryptValue(duaDeclarationIdStr) + "\" >" + description + "</a>";
			select = "<a href=\"" + contextRoot + "/linkaction.do?"+secValues.encryptValue("forwardName")+"="+secValues.encryptValue("updduadeclaration")+"&amp;"
						+ secValues.encryptValue("duaDeclarationId")+"=" + secValues.encryptValue(duaDeclarationIdStr) +"&amp;"+secValues.encryptValue(SessionConstants.ENCRYPTION_KEY)+"=" +secValues.encryptValue(encKey)  + "\" >" + description + "</a>";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		if (LOGGER.isEnabledFor(Level.ERROR)) {
			LOGGER.error("Message ",e);
		}
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
		if (LOGGER.isEnabledFor(Level.ERROR)) {
			LOGGER.error("Message ",e);
		}
		} catch (BaseApplicationException e) {
			// TODO Auto-generated catch block
		if (LOGGER.isEnabledFor(Level.ERROR)) {
			LOGGER.error("Message ",e);
		}
		}
		return select;
	}
}