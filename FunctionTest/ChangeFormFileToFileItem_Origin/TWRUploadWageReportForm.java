//CIF_P2_00501 {New}
/**
 * This form class takes care of validates the file uploaded from 'File Contribution and Wage Report- Upload Wages - Wage Report' screen  
 * @author Tata Consultancy Services
 */
package gov.state.uim.twr.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.framework.struts.BaseValidatorForm;

/**
 * @struts.form name = "twruploadwagereportform"
 */
public class TWRUploadWageReportForm extends BaseValidatorForm {
	private static final Logger LOGGER = Logger.getLogger(TWRUploadWageReportForm.class);
	
	
	private static final long serialVersionUID = -1444067678752213214L;
	
	private FormFile fileName ;
		
	public TWRUploadWageReportForm(){
		super();		
	}
	
	public FormFile getFileName() {
		return fileName;
	}
	
	public void setFileName(FormFile fileName) {
		this.fileName = fileName;
	}

	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		
		ActionErrors errors = null;		
		errors = super.validate(arg0, arg1);			
		if(errors.size() > 0){
			return errors;
		}
		if((this.fileName != null) && (!this.fileName.getFileName().equals(""))){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

			
		 	  //do nothing
			
		}else{
			  
			errors.add("fileName", new ActionMessage(
				"error.required", this.getApplicationMessages(arg1, "access.twr.taxreporting.employees.uploadwagesfile.selectfile.number")));
		}			
		return errors;
	}
	
}