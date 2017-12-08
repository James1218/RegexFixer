package gov.state.uim.web.search;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;

import org.apache.commons.beanutils.DynaBean;

import gov.state.uim.ApplicationProperties;
import gov.state.uim.GlobalConstants;
import gov.state.uim.SessionConstants;
import gov.state.uim.framework.exception.BaseApplicationException;
import gov.state.uim.util.lang.DateFormatUtility;
import gov.state.uim.util.lang.StringUtility;
import gov.state.uim.web.taglib.function.SecureValues;

public class SearchReportDecorator extends TabDecorator {

	//CIF_00970
	public String getTitle() throws DisplayDecoratorException, UnsupportedEncodingException, GeneralSecurityException, BaseApplicationException {
		String encKey = (String) super.getPageContext().getSession().getAttribute(SessionConstants.ENCRYPTION_KEY);
		if(StringUtility.isBlank(encKey)){
			encKey = ApplicationProperties.PROTECTED_KEY_STRING;
		}
		String title = (String) (((DynaBean) this.getCurrentRowObject())
				.get("title"));
		String reportPath = (String) (((DynaBean) this.getCurrentRowObject())
				.get("reportpath"));
	/*	String contextRoot = ((HttpServletRequest) this.getPageContext()
				.getRequest()).getContextPath();*/
		reportPath=reportPath.replaceAll("\\\\", ",");
		String reportName = (String) (((DynaBean) this.getCurrentRowObject())
				.get("reportname"));
		String select;
		SecureValues secValues = new SecureValues(encKey);
		String link = "searchreport.do?method=Generate Report&"+ 
		secValues.encryptValue("report")+"=" +  secValues.encryptValue(title)
				+ "&amp;"+ secValues.encryptValue("reportpath")+"=" +  secValues.encryptValue(reportPath)
				+"&amp;"+secValues.encryptValue(SessionConstants.ENCRYPTION_KEY)+"=" +secValues.encryptValue(encKey)
				+ "&amp;"+ secValues.encryptValue("reportname")+"=" +  secValues.encryptValue(reportName);
////		 select = "<a href=\"" + contextRoot
////		 + "/searchreport.do?method=Generate Report&amp;report=" + title
////		 + "&amp;reportpath=" + reportPath + "\">" + title + "</a>";
		select = "<a href=\"#\" onclick=\"window.open('"
				+ link
				+ "', '_blank','left=0,top=0,resizable=yes,scrollbars=yes,status=yes')\" >"
				+ title + "</a>";
		//select = "<a href=\"" + contextRoot + "/searchreport.do?method=Generate Report&amp;report=" + title +"&amp;reportpath=" + reportPath + "\">" + title + "</a>";
		return select;
	}

	public String getReportDate() throws DisplayDecoratorException {
		Date reportDate = (Date) (((DynaBean) this.getCurrentRowObject())
				.get("reportdate"));
		if (reportDate != null) {
			//CIF_INT_01571|| Defect_94 Fix Date and Time format(MM/dd/yyyy hh:mm:ss) pattern.
			return DateFormatUtility.format(reportDate, GlobalConstants.DATE_FORMAT + " " + GlobalConstants.TIME_FORMAT);
		} else {
			return null;
		}
	}
}
