package gov.state.uim.web.search;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;

import gov.state.uim.ApplicationProperties;
import gov.state.uim.SessionConstants;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.domain.ChargeInquiryTransactionBO;
import gov.state.uim.domain.enums.ClaimEmploymentChargeStatusEnum;
import gov.state.uim.employerchargeback.struts.ChargeInquiryTransactionClaimantsListForm;
import gov.state.uim.framework.exception.BaseApplicationException;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.util.lang.DateFormatUtility;
import gov.state.uim.util.lang.StringUtility;
import gov.state.uim.web.taglib.function.SecureValues;

public class SearchEmployerChargesDecorator extends TabDecorator{
	private final static AccessLogger LOGGER = ServiceLocator.instance
			.getLogger(SearchEmployerChargesDecorator.class);

	public String getSsn() throws DisplayDecoratorException {
		String tempString = ((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getSsn();
		String formattedSsn = StringUtility.formatSsnToDisplay(tempString);
		String contextRoot = ((HttpServletRequest)this.getPageContext().getRequest()).getContextPath();
		ChargeInquiryTransactionClaimantsListForm  form = (ChargeInquiryTransactionClaimantsListForm)this.getPageContext().getSession().getAttribute("chargeinquirytransactionclaimantslistform");
		
		Long claimId= ((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getClaimId();
		Long chargeabilityId=null;
		Long ib5Id  = null;
		if(claimId==null || claimId.longValue()==0){
		 claimId=Long.valueOf(0);
		 chargeabilityId= ((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getChargeabilityId();
		 //Added by Jitendra for SEB handling in view charges R4UAT00010673 -->
		 //If both the claim Id and Chargeability Id is null then fetch the ib5Id
		 if(chargeabilityId==null || chargeabilityId.longValue()==0){
			 ib5Id = ((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getIb5Id();
		 }
		}
		String encKey = (String) super.getPageContext().getSession().getAttribute(SessionConstants.ENCRYPTION_KEY);
		if(StringUtility.isBlank(encKey)){
			encKey = ApplicationProperties.PROTECTED_KEY_STRING;
		}
		SecureValues secValues = new SecureValues(encKey);
		//Adding one more request parameter ib5Id for EB claims.
		String select = null;
		String claimIdStr = (null != claimId) ? claimId.toString() : null;
		String chargeabilityIdStr = (null != chargeabilityId) ? chargeabilityId.toString() : null;
		String ib5IdStr = (null != ib5Id) ? ib5Id.toString() : null;
		try {
			//CIF_P2_00758
			select = "<a href=\"" + contextRoot + "/ib6disclinkaction.do?"+secValues.encryptValue("forwardName")+"="
			+secValues.encryptValue("viewEmployerCharges")+"&amp;"
					+secValues.encryptValue("ssn")+"=" + secValues.encryptValue(tempString) +"&amp;"
			+secValues.encryptValue("ean")+"=" + secValues.encryptValue(form.getEanBean().getEan()) 
			+ "&amp;"+secValues.encryptValue("year")+"=" 
			+ secValues.encryptValue(form.getYear())
			+"&amp;"+secValues.encryptValue(SessionConstants.ENCRYPTION_KEY)+"=" +secValues.encryptValue(encKey)
			+"&amp;"+ secValues.encryptValue("quarter")+"=" + secValues.encryptValue(form.getQuarter())+"&amp;"+ 
			secValues.encryptValue("claimId")+"="+secValues.encryptValue(claimIdStr)+"&amp;";
			if(StringUtility.isNotBlank(chargeabilityIdStr)){		
				select =select + secValues.encryptValue("chargeabilityId")+"="+secValues.encryptValue(chargeabilityIdStr)+"&amp;";
			}
			if(StringUtility.isNotBlank(ib5IdStr)){
				select =select + secValues.encryptValue("ib5Id")+"="+secValues.encryptValue(ib5IdStr);
			}
			select =select + "\">"+ formattedSsn + "</a>";
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
	
	public String getMaskSSN() throws DisplayDecoratorException {
		String tempString = ((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getSsn();
		String formattedSsn = StringUtility.formatSsnToMask(tempString);
		return formattedSsn;
	}
	
	public Long getSortSsn() throws DisplayDecoratorException
	{
     return  Long.valueOf(((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getSsn().toString());		
	}


	public String getBye() throws DisplayDecoratorException {
		Date tempDate = ((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getByeDate();
		return DateFormatUtility.format(tempDate);
	}

	public String getName() throws DisplayDecoratorException {
		String firstInitial = ((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getFirstName();
		String middleInitial = ((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getMiddleName();
		String lastName = ((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getLastName();
		String tempString = null;
		if (StringUtility.isNotBlank(middleInitial)) {
			tempString = firstInitial + " " + middleInitial + " " + lastName;
		} else {
			tempString = firstInitial + " " + lastName;
		}
		return tempString;
	}
	
	public String getStatus() throws DisplayDecoratorException {
		String tempString = null;
		tempString = ((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getChargeStatus();
 		return ClaimEmploymentChargeStatusEnum.getEnum(tempString).getDescription();
	}
	
	//CIF_INT_01656||BYE in the BYB table of the Employer Charge Transaction Inquiry Screen
	public String getClaimEffectiveDate() throws DisplayDecoratorException {
		Date tempDate = ((ChargeInquiryTransactionBO)this.getCurrentRowObject()).getClaimEffectiveDate();
		return DateFormatUtility.format(tempDate);
	}




}