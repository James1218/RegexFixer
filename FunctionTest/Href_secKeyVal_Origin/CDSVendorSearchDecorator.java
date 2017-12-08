/**
 * File Created Name Author =================================================================== 02-17-2015 CDSVendorSearchDecorator TCS
 */
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

// CIF_P2_00087 - Search CDS Vendor
/**
 * @author TCS
 */
public class CDSVendorSearchDecorator extends TabDecorator
{
	private final static AccessLogger LOGGER = ServiceLocator.instance
			.getLogger(CDSVendorSearchDecorator.class);

    public String getselect() throws DisplayDecoratorException
    {

        String cdsVendorId = ((Long) ((DynaBean) this.getCurrentRowObject()).get("cdsvendorid")).toString();
        String cdsVendorName = ((String) ((DynaBean) this.getCurrentRowObject()).get("cdscompanyname"));
        // CIF_P2_00704 Selenium Code defects Commented as not required
        // String paramCdsVendorId = "&cdsVendorId=" + cdsVendorId;
        // String paramCdsVendorName = "&cdsVendorName=" + cdsVendorName;
        // String parameterPassed = paramCdsVendorId + paramCdsVendorName;

        String contextRoot = ((HttpServletRequest) this.getPageContext().getRequest()).getContextPath();
        // CIF_P2_00704 Selenium Code defects Commented as not required
        /*
         * String select = "<a href=\"" + contextRoot + "/cdsvendorsearch.do?method=select" + parameterPassed + "\">SELECT</a>";
         */
        // CIF_P2_00704 Selenium Code defects Added the follwoing code to encrypt the parameters

        String encKey = (String) super.getPageContext().getSession().getAttribute(SessionConstants.ENCRYPTION_KEY);
        if (StringUtility.isBlank(encKey))
        {
            encKey = ApplicationProperties.PROTECTED_KEY_STRING;
        }

        SecureValues secValues = new SecureValues(encKey);
        String select = null;
        try
        {
            select = "<a href=\"" + contextRoot + "/cdsvendorsearch.do?method=select&amp;" + secValues.encryptValue("cdsVendorId") + "=" + secValues.encryptValue(cdsVendorId) + "&amp;" + secValues.encryptValue(cdsVendorName) + "=" + secValues.encryptValue(cdsVendorName) + "&amp;"+secValues.encryptValue(SessionConstants.ENCRYPTION_KEY)+"=" +secValues.encryptValue(encKey)+"\">SELECT</a>";
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
		if (LOGGER.isEnabledFor(Level.ERROR)) {
            LOGGER.error("Message ",e);
		}
        }
        catch (GeneralSecurityException e)
        {
            // TODO Auto-generated catch block
		if (LOGGER.isEnabledFor(Level.ERROR)) {
            LOGGER.error("Message ",e);
		}
        }
        catch (BaseApplicationException e)
        {
            // TODO Auto-generated catch block
		if (LOGGER.isEnabledFor(Level.ERROR)) {
            LOGGER.error("Message ",e);
		}
        }
        return select;

    }
}