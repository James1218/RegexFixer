package gov.state.uim.web.search;

import java.util.Date;

import org.apache.commons.beanutils.DynaBean;

import gov.state.uim.ApplicationProperties;
import gov.state.uim.SessionConstants;
import gov.state.uim.domain.bean.EanBean;
import gov.state.uim.domain.enums.CorrespondenceCodeTaxEnum;
import gov.state.uim.util.lang.StringUtility;
import gov.state.uim.web.taglib.function.SecureValues;

public class SearchDocumentDecorator extends TabDecorator {
	//CIF_P2_00301 Start Search Document,  CIF_P2_01367 changes made
	public String getTitle() throws DisplayDecoratorException {
		
		String corrCode = (String)((DynaBean)this.getCurrentRowObject()).get("correspondencecode");
		corrCode=corrCode.trim();
		String titleDesc=corrCode;	
		/*if(StringUtility.isNotBlank(corrCode) && CorrespondenceCodeEnum.getEnum(corrCode) != null 
				&& StringUtility.isNotBlank(CorrespondenceCodeEnum.getEnum(corrCode).getDescription()))
		{
			titleDesc = CorrespondenceCodeEnum.getEnum(corrCode).getDescription();
		}	*/
		
		//CIF_P2_00281 VIEW CORRESPONDENCES  Start		
        String documentId = (String) ((DynaBean) this.getCurrentRowObject()).get("documentid");
		String documentPath = (String)((DynaBean)this.getCurrentRowObject()).get("documentpath");
        // CIF_P2_00281 end
		String encKey = (String) super.getPageContext().getSession().getAttribute(SessionConstants.ENCRYPTION_KEY);
        if(StringUtility.isBlank(encKey)){
                        encKey = ApplicationProperties.PROTECTED_KEY_STRING;
        }
        SecureValues secValues = new SecureValues(encKey);
        //SecureValues secValues = new SecureValues();
        String link = null;
        try
        {
            link = "opendocuments.do?method=Generate Report&" + secValues.encryptValue("documentId") + "=" + secValues.encryptValue(documentId) + "&" + secValues.encryptValue("documentPath") + "=" + secValues.encryptValue(documentPath)
            		 +"&amp;"+secValues.encryptValue(SessionConstants.ENCRYPTION_KEY)+"=" +secValues.encryptValue(encKey);
        }
        catch (Exception e)
        {
            return "";
        }
		
        // String link = "searchbenefitdocument.do?method=Generate Report&documentId=" + documentId +"&documentPath=" + documentPath;
		
		String	select = "<a href=\"#\" onclick=\"window.open('"+link+"', '_blank','left=0,top=0,resizable=yes,scrollbars=yes,status=yes')\" >" + titleDesc + "</a>";
		
		return select;
	}
	
	
		public String getCorrtitle() throws DisplayDecoratorException {
		
			String corrCode = (String)((DynaBean)this.getCurrentRowObject()).get("correspondencecode");
			if(corrCode!=null){
				corrCode=corrCode.trim();
			}
			return corrCode;
		}
	
	
	
	public String getCheckbox() throws DisplayDecoratorException {
		//CIF_INT_02946 handling Incoming document.
		String checkbox = null;
		if(null != ((DynaBean)this.getCurrentRowObject()).get("corrid")){
		final String reponseId = (String)((DynaBean)this.getCurrentRowObject()).get("corrid").toString();
		checkbox = "<input type=\"checkbox\" name=\"reponseId\" value=\""+ reponseId + "\" />";
		}else{
			checkbox = "<input type=\"checkbox\" name=\"reponseId\" value=\"0\" />";
		}
		
		return checkbox;
	}
	//CIF_P2_01612	||Defect_6470
	public String getDesc() throws DisplayDecoratorException {
		String corrCode = (String)((DynaBean)this.getCurrentRowObject()).get("correspondencecode");
		corrCode=corrCode.trim();
		String titleDesc=corrCode;	
		if(StringUtility.isNotBlank(corrCode) && CorrespondenceCodeTaxEnum.getEnum(corrCode) != null 
				&& StringUtility.isNotBlank(CorrespondenceCodeTaxEnum.getEnum(corrCode).getDescription()))
		{
			titleDesc = CorrespondenceCodeTaxEnum.getEnum(corrCode).getDescription();
		}
		return titleDesc;
	}


	//CIF_P2_01612	||Defect_6470
	public String getEan() throws DisplayDecoratorException{
		String ean = (String)((DynaBean)this.getCurrentRowObject()).get("ean");
		if(ean!=null && StringUtility.isNotBlank(ean)){
		ean = new EanBean().formatEan(ean);
		}
		
		return ean;
	}


	public Date getDate() throws DisplayDecoratorException{
		Date dateFromQuery =(Date)((DynaBean)this.getCurrentRowObject()).get("corrdate");
		
		if (dateFromQuery != null){
			return dateFromQuery;
		}else{
			return null;
		}
	}


/*

	public String getMailDate() throws DisplayDecoratorException {
		Calendar mailDate = ((CorrespoandenceDmsBean)this.getCurrentRowObject()).getMailDate();
		
		if (mailDate != null){
			return DateFormatUtility.format(mailDate);
		}else{
			return null;
		}
			







	}*/
	/*
	public String getDirection() throws DisplayDecoratorException {
		String direction = ((CorrespoandenceDmsBean)this.getCurrentRowObject()).getDirection();
		
		if (CorrespoandenceDmsBean.INBOUND_DIRECTION.equals(direction)){
			return "Incoming";
		}else if (CorrespoandenceDmsBean.OUTBOUND_DIRECTION.equals(direction)){
			return "Outgoing";
		}else {
			return "";
		}
	}*/
	
	//CIF_P2_00301 End
}
