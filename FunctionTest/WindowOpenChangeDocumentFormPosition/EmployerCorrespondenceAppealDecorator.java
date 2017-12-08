package gov.state.uim.web.search;

import org.apache.commons.beanutils.DynaBean;

import gov.state.uim.domain.enums.CorrespondenceCodeEnum;
import gov.state.uim.util.lang.StringUtility;

public class EmployerCorrespondenceAppealDecorator extends TabDecorator {

	public String getTitle() throws DisplayDecoratorException {
		String corrCode = (String)((DynaBean)this.getCurrentRowObject()).get("corrcode");
		corrCode=corrCode.trim();
		String titleDesc=corrCode;	
		if(StringUtility.isNotBlank(corrCode) && CorrespondenceCodeEnum.getEnum(corrCode) != null 
				&& StringUtility.isNotBlank(CorrespondenceCodeEnum.getEnum(corrCode).getDescription()))
		{
			titleDesc = CorrespondenceCodeEnum.getEnum(corrCode).getDescription();
		}		
		String corrId =(Long)((DynaBean)this.getCurrentRowObject()).get("id")+"";
		String docket = (String)((DynaBean)this.getCurrentRowObject()).get("docket");
		
		String link =  "opendocument.do?method=Generate Report&docketno=" + docket +"&corrId=" + corrId;	
		
		String	select = "<a href=\"#\" onclick=\"document.forms[0].method[0].click();window.open('"+link+"', '_blank','left=0,top=0,resizable=yes,scrollbars=yes,status=yes')\" >" + titleDesc + "</a>";
		
		return select;

	}
	
}