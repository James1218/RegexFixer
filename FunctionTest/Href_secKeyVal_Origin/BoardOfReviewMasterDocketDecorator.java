
package gov.state.uim.web.search;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Level;

import gov.state.uim.ApplicationProperties;
import gov.state.uim.GlobalConstants;
import gov.state.uim.SessionConstants;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.domain.enums.AppealDecisionCodeEnum;
import gov.state.uim.domain.enums.AppealStatusEnum;
import gov.state.uim.domain.enums.AppealTypeEnum;
import gov.state.uim.domain.enums.AppellantOrOpponentEnum;
import gov.state.uim.framework.exception.BaseApplicationException;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.util.lang.DateFormatUtility;
import gov.state.uim.util.lang.StringUtility;
import gov.state.uim.web.taglib.function.SecureValues;

public class BoardOfReviewMasterDocketDecorator extends TabDecorator {
	private final static AccessLogger LOGGER = ServiceLocator.instance
			.getLogger(BoardOfReviewMasterDocketDecorator.class);	
	private int rowNum = 1;

	public String getAppealid() {
		String appealId = ((Long)((DynaBean)this.getCurrentRowObject()).get("appealid")).toString();
		((DynaBean)this.getCurrentRowObject()).get("borid");
		String appealType = ((DynaBean)this.getCurrentRowObject()).get("appealtype").toString();
		String appealStatus = ((DynaBean)this.getCurrentRowObject()).get("appealstatus").toString();
		//(AppealStatusEnum.CLOSED.getName().equalsIgnoreCase(appealStatus))||
		if(!((AppealStatusEnum.REVIEWED.getName().equalsIgnoreCase(appealStatus)))
				||AppealStatusEnum.REMAND_LOWER.getName().equalsIgnoreCase(appealStatus)) {
			String radio = null;
			if(rowNum == 1){
				radio="<input id=\"BoardOfReviewMasterDocketDecoratorId"+rowNum+"\" type=\"radio\" name=\"radioButton\" value=\""+appealId+"#"+appealType+"\"/>";
			}else{
				radio="<input title=\"BoardOfReviewMasterDocketDecoratorId"+rowNum+"\" type=\"radio\" name=\"radioButton\" value=\""+appealId+"#"+appealType+"\"/>";
			}
			rowNum++; 
			return radio;
		}
		else {
			String radio = null;
			if(rowNum == 1){
				radio="<input id=\"BoardOfReviewMasterDocketDecoratorId"+rowNum+"\" type=\"radio\" name=\"radioButton\" value=\""+appealId+"#"+appealType+"\" disabled=\"true\"/>";
			}else{
				radio="<input title=\"BoardOfReviewMasterDocketDecoratorId"+rowNum+"\" type=\"radio\" name=\"radioButton\" value=\""+appealId+"#"+appealType+"\" disabled=\"true\"/>";
			}
			rowNum++; 
			return radio;
		}
	}
	
	public String getAppealdate() {
		//Date appealDate = ((Date)((DynaBean)this.getCurrentRowObject()).get("appealdate"));
		//return DateFormatUtility.format(appealDate);
		String appealDate = ((Date)((DynaBean)this.getCurrentRowObject()).get("appealdate")).toString();
    	String decisionDateFormatted = DateFormatUtility.format(DateFormatUtility.parse(appealDate,GlobalConstants.DB_DATE_FORMAT)).toString();
    	return decisionDateFormatted;
	}
	
	public String getAppellant() {
		String appellant = ((String)((DynaBean)this.getCurrentRowObject()).get("appellant"));
		return AppellantOrOpponentEnum.getEnum(appellant).getDescription();
	}
	
	public String getAppealdecisioncode() {
		String appealDecisionCode = ((String)((DynaBean)this.getCurrentRowObject()).get("appealdecisioncode"));
		if(StringUtility.isNotBlank(appealDecisionCode)) {
			AppealDecisionCodeEnum appealEnum = AppealDecisionCodeEnum.getEnum(appealDecisionCode);
			if(appealEnum != null) {
				return appealEnum.getDescription();
			}
		}
		return "";
	}
	
	public String getAppealtype() {
		String appealType = ((String)((DynaBean)this.getCurrentRowObject()).get("appealtype"));
		return AppealTypeEnum.getEnum(appealType).getDescription();
	}
	
	public String getDocketnumber() throws DisplayDecoratorException {
		String appealId = ((Long)((DynaBean)this.getCurrentRowObject()).get("appealid")).toString();
		String docketnumber = (((DynaBean)this.getCurrentRowObject()).get("docketnumber")).toString();
		String contextRoot = ((HttpServletRequest)this.getPageContext().getRequest()).getContextPath();
		String select = null;
		if (StringUtility.isNotBlank(appealId))
		{
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

			/*CIF_00477 Starts ,Desc:Modified the path to redirect the page to lircmeetingappealinfo */
			//select = "<a href=\"" + contextRoot + "/appeallinkaction.do?forwardName=viewappealinformation&amp;appealId=" + appealId +"\">" + docketnumber + "</a>";
			String encKey = (String) super.getPageContext().getSession().getAttribute(SessionConstants.ENCRYPTION_KEY);
            if(StringUtility.isBlank(encKey)){
                            encKey = ApplicationProperties.PROTECTED_KEY_STRING;
            }

			SecureValues secValues = new SecureValues(encKey);
			try {
				//select = "<a href=\"" + contextRoot + "/appeallinkaction.do?forwardName=lircmeetingappealinfo&amp;"+secValues.encryptValue("appealId")+"=" + secValues.encryptValue(appealId) +"\">" + docketnumber + "</a>";
			
				select = "<a href=\"" + contextRoot + "/appeallinkaction.do?"+secValues.encryptValue("forwardName")
                        +"="+secValues.encryptValue("lircmeetingappealinfo")+"&amp;"+secValues.encryptValue("appealId")+"=" +secValues.encryptValue(appealId)
                        +"&amp;"+secValues.encryptValue(SessionConstants.ENCRYPTION_KEY)+"=" +secValues.encryptValue(encKey) +"\">"+docketnumber+"</a>";

			
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
			/*CIF_00477 Ends.*/
		}
		return select;
	}
	
	public Long getdocketNumberSort() throws DisplayDecoratorException {

		Long docketNumber=Long.valueOf(((DynaBean)this.getCurrentRowObject()).get("docketnumber").toString());
		return docketNumber;
	}
	
}