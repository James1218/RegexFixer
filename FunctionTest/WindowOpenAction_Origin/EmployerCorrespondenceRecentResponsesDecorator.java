package gov.state.uim.web.search;

import java.util.Calendar;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import gov.state.uim.ApplicationProperties;
import gov.state.uim.GlobalConstants;
import gov.state.uim.SessionConstants;
import gov.state.uim.domain.bean.SsnBean;
import gov.state.uim.nmon.struts.EmployerCorrespondenceRecentResponsesForm;
import gov.state.uim.util.lang.DateFormatUtility;
import gov.state.uim.util.lang.StringUtility;
import gov.state.uim.web.taglib.function.SecureValues;


public class EmployerCorrespondenceRecentResponsesDecorator extends TabDecorator {
	private static final Logger LOGGER = Logger.getLogger(EmployerCorrespondenceRecentResponsesDecorator.class);

	
	public String getTitle() throws DisplayDecoratorException {
		String encKey = (String) super.getPageContext().getSession().getAttribute(SessionConstants.ENCRYPTION_KEY);
		if(StringUtility.isBlank(encKey)){
			encKey = ApplicationProperties.PROTECTED_KEY_STRING;
		}
		String corrCode = (String)((DynaBean)this.getCurrentRowObject()).get("corrcode");
		corrCode=corrCode.trim();
		String titleDesc=corrCode;	
		//CIF_INT_03034 : Type should be correspondence code not description
		/*if(StringUtility.isNotBlank(corrCode) && CorrespondenceCodeEnum.getEnum(corrCode) != null 
				&& StringUtility.isNotBlank(CorrespondenceCodeEnum.getEnum(corrCode).getDescription()))
		{
			titleDesc = CorrespondenceCodeEnum.getEnum(corrCode).getDescription();
		}	*/
		//CIF_P2_00281 VIEW CORRESPONDENCES - Start		
		String documentId =(String)((DynaBean)this.getCurrentRowObject()).get("documentid")+"";
		String documentPath = (String)((DynaBean)this.getCurrentRowObject()).get("documentpath");
		//CIF_P2_00281 - end		
		SecureValues secValues = new SecureValues(encKey);
		String link = null;
		try {
			link = "opendocuments.do?method=Generate Report&amp;"+secValues.encryptValue("documentId")+"=" +secValues.encryptValue(documentId)
					+"&amp;"+secValues.encryptValue(SessionConstants.ENCRYPTION_KEY)+"=" +secValues.encryptValue(encKey)
					+"&amp;"+secValues.encryptValue("documentPath")+"="+secValues.encryptValue(documentPath);
		} catch (Exception e) {
		if (LOGGER.isDebugEnabled()) {
		LOGGER.debug(e);
		}
			
		}	
		String	select = "<a href=\"#\" onclick=\"window.open('"+link+"', '_blank','left=0,top=0,resizable=yes,scrollbars=yes,status=yes')\" >" + titleDesc + "</a>";
		return select;
	}
	
	public String getFullName() throws DisplayDecoratorException{	
		
		 String fname = ((String)((DynaBean)this.getCurrentRowObject()).get("firstname"));
		 String lname = ((String)((DynaBean)this.getCurrentRowObject()).get("lastname"));
		 String fullName=StringUtility.getFullName(fname,lname);
         return fullName;
	}
	
	public String getSsn() throws DisplayDecoratorException{	
		
		 String ssn = ((String)((DynaBean)this.getCurrentRowObject()).get("ssn"));
		 if(StringUtility.isNotBlank(ssn)){
			 SsnBean bean=new SsnBean(ssn);
		 	ssn=bean.getSsnWithDelimiter();
		 }
		 return ssn;
	}
	
	//CIF_P2_00281 VIEW CORRESPONDENCES  Start			
	public String getEan() throws DisplayDecoratorException{	
		
		EmployerCorrespondenceRecentResponsesForm currentForm =(EmployerCorrespondenceRecentResponsesForm)this.getPageContext().getSession().getAttribute("employercorrespondencerecentresponsesform");
		
		 String ean = (String)currentForm.getEan();
		
		 return StringUtility.formatEan(ean);
	}
	//CIF_P2_00281 end		
	
	public String getDuedate() throws DisplayDecoratorException{	
		
         //Due Date is 14 days from the sent date .   		
		 String dates = ((String)((DynaBean)this.getCurrentRowObject()).get("sentdate"));
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(DateFormatUtility.parse(dates, GlobalConstants.DB_DATE_FORMAT));
		 cal.add(Calendar.DAY_OF_YEAR,14);
		 	 
       return DateFormatUtility.format(cal);
	}
	
	public String getSentdate() throws DisplayDecoratorException{	
		
		 String dates = ((String)((DynaBean)this.getCurrentRowObject()).get("sentdate"));
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(DateFormatUtility.parse(dates, GlobalConstants.DB_DATE_FORMAT));
		 	 
		 return DateFormatUtility.format(cal, GlobalConstants.DATE_FORMAT);
	}
	
	
}