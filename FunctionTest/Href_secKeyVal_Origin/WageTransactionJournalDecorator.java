package gov.state.uim.web.search;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.displaytag.decorator.TableDecorator;

import gov.state.uim.domain.bean.CalendarBean;
import gov.state.uim.domain.enums.WageTransactionJournalEnum;
import gov.state.uim.util.lang.DateFormatUtility;

public class WageTransactionJournalDecorator extends TableDecorator{

	public String getBatchIdentifier() throws DisplayDecoratorException
	{
		String select="";
		String contextRoot = ((HttpServletRequest)this.getPageContext().getRequest()).getContextPath();
		String batchidentifier = (String)((DynaBean)this.getCurrentRowObject()).get("batchidentifier");
		Integer batchnumber = (Integer)((DynaBean)this.getCurrentRowObject()).get("batchnumber");
		Date importDate = (Date)((DynaBean)this.getCurrentRowObject()).get("importdate");
		CalendarBean cbImportDate = new CalendarBean(importDate);
		String strImportDate = cbImportDate.getDateAsDBString();
		
		Date batchDate = (Date)((DynaBean)this.getCurrentRowObject()).get("batchdate");
		CalendarBean cbBatchDate = new CalendarBean(batchDate);
		String strTransactionDate = cbBatchDate.getDateAsString();
		
		String transactiontype;
		if (batchidentifier != null && !batchidentifier.equals("")){
			String bnumber="";
			transactiontype = "ACCESS";
			if(batchnumber!=null){
				bnumber = batchnumber.toString();
			}

			select = "<a href=JavaScript:openPopupWindow('" + contextRoot + "/inqlinkaction.do?forwardName=wagetransactionjournaldtls&amp;transactiontype="+transactiontype+"&amp;batchidentifier=" + batchidentifier+"&amp;batchnumber=" + bnumber+"&amp;importdate="+strImportDate+"&amp;batchdate="+strTransactionDate+"');  >"+ WageTransactionJournalEnum.getDescription(batchidentifier) + "</a>";
		}
		else if (batchnumber!=null) {
			transactiontype = "RPS";
			batchidentifier="";
			select = "<a href=JavaScript:openPopupWindow('" + contextRoot + "/inqlinkaction.do?forwardName=wagetransactionjournaldtls&amp;transactiontype="+transactiontype+"&amp;batchidentifier=" + batchidentifier+"&amp;batchnumber=" + batchnumber+"&amp;importdate="+strImportDate+"&amp;batchdate="+strTransactionDate+"');  >"+ batchnumber + "</a>";
			
			//sb.append(batchnumber.toString());
		}
		//sb.append("</html:link>");
		return select;
	}
	
	public String getBatchDate() throws DisplayDecoratorException
	{
		Date date = (Date)((DynaBean)this.getCurrentRowObject()).get("batchdate");
		
		return DateFormatUtility.format(date ,"MM/dd/yyyy");
	}
	
	public String getSource()
	{
		Integer batchnumber = (Integer)((DynaBean)this.getCurrentRowObject()).get("batchnumber");
	
		//String bacthnumber = ((DynaBean)this.getCurrentRowObject()).get("batchnumber").toString();
		
		if (batchnumber != null){
			
			return "RPS";
		}
		else{
			return "ACCESS";
		}
	}
	public String getPrematch()
	{
		String select="";
		String contextRoot = ((HttpServletRequest)this.getPageContext().getRequest()).getContextPath();
		String batchidentifier = (String)((DynaBean)this.getCurrentRowObject()).get("batchidentifier");
		Integer batchnumber = (Integer) ((DynaBean)this.getCurrentRowObject()).get("batchnumber");
		Date date = (Date)((DynaBean)this.getCurrentRowObject()).get("batchdate");
		
		CalendarBean cb = new CalendarBean(date);
		String tranactiondate=cb.getDateAsDBString();
		String tranactiondatedis=cb.getDateAsString();
		
		Date importDate = (Date)((DynaBean)this.getCurrentRowObject()).get("importdate");
		String importdate = "";
		if( importDate != null )
		{
			CalendarBean cbImportDate = new CalendarBean(importDate);
			importdate = cbImportDate.getDateAsDBString();
		}
			
		if (batchnumber != null && !batchnumber.equals("")){
			String bidentifier="";
			if (batchidentifier!=null){
				bidentifier=batchidentifier;
			}
			select = "<a href=\"" + contextRoot + "/inqlinkaction.do?forwardName=prematchemployeraccount&amp;batchidentifier=" + bidentifier+"&amp;batchnumber=" + batchnumber+"&amp;transactiondate="+tranactiondate+"&amp;transactiondatedis="+tranactiondatedis+"&amp;importdate=" + importdate + "&amp;backscreen=wagetransaction \">View Details</a>";
			
		}
		return select;
	}



}
