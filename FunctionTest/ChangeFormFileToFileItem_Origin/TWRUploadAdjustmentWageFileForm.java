/**
 * This is the action form class corresponding to 'Detailed Employee Wages - File Upload' screen
 * @author Tata Consultancy Services
 */
package gov.state.uim.twr.struts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.framework.exception.BaseApplicationException;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.struts.BaseValidatorForm;
import gov.state.uim.twr.TWRUtil;

/**
 * @struts.form name = "twruploadadjustmentwagefileform"
 */
public class TWRUploadAdjustmentWageFileForm extends BaseValidatorForm {
	private static final long serialVersionUID = -1444067678752212514L;
	private String uploadFormat;
	private FormFile fileName ;
	private BigDecimal totalWage;
	private String emplName;
	private String emplAcntNumb;
	private String quaterYear;
	private String submittedBy;
	private static final Logger logger = Logger.getLogger(TWRUploadAdjustmentWageFileForm.class);	
	public TWRUploadAdjustmentWageFileForm(){
		super();
	}
	
	public String getUploadFormat() {
		return uploadFormat;
	}
	/**
	 * @struts.validator type = "required"
	 * 					  msgkey="error.required"
	 * 					  
	 * @struts.validator-args
	 * 					 arg0resource = "access.twr.taxreporting.employees.uploadwagesfile.selectformat.number"
	 */
	public void setUploadFormat(String uploadFormat) {
		this.uploadFormat = uploadFormat;
	}
	public FormFile getFileName() {
		return fileName;
	}
	
	public void setFileName(FormFile fileName) {
		this.fileName = fileName;
	}
	public BigDecimal getTotalWage() {
		return totalWage;
	}

	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		
		IObjectAssembly objAssembly  = null;
		ActionErrors errors = null;
		try{
			errors = super.validate(arg0, arg1);
			
			if(errors.size() > 0){
				return errors;
			}
			objAssembly = getObjectAssemblyFromSession(arg1,true);
		    /***** For validation *******/
			  if((this.fileName != null) && (!this.fileName.getFileName().equals(""))){
				  
			      InputStream iStream = this.fileName.getInputStream();
			      InputStreamReader iSReader = new InputStreamReader(iStream);
			      BufferedReader buffReader = new BufferedReader(iSReader);
			      if(buffReader.ready()){
				      if(this.uploadFormat.equalsIgnoreCase(GlobalConstants.MMREF)){
				    	  
				    	  try {
				    		  this.totalWage = TWRUtil.validateMMREFFile(buffReader, objAssembly.getEan(), quaterYear, this);
				    	  } catch (BaseApplicationException bex)
				    	  {
				    		  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(bex.getMessage()));
				    	  }//this.totalWage = TWRUtil.validateMMREFFile(buffReader, errors, objAssembly.getEan(), quaterYear, this);
					  }else if (this.uploadFormat.equalsIgnoreCase(GlobalConstants.NEW_ICESA)){
						  try {
							  this.totalWage = TWRUtil.validateNewICESAFile(buffReader, objAssembly.getEan(), quaterYear, this);
						  }catch (BaseApplicationException bex)
						  {
							  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(bex.getMessage()));
						  }//  this.totalWage = TWRUtil.validateNewICESAFile(buffReader, errors, objAssembly.getEan(), quaterYear, this);
					  }
			      }else{
			    	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"error.access.tax.twr.invalidfile"));			     
			      }
			  }else{
				  errors.add("fileName", new ActionMessage(
					"error.required", this.getApplicationMessages(arg1, "access.twr.taxreporting.employees.uploadwagesfile.selectfile.number")));
			  }
			  
			  
			  
		      
		}catch(IOException aIOEx){
			objAssembly.addBusinessError("error.access.tax.twr.wagereport.fileioerror");
		}
	      
		return errors;
	}
	public String getEmplAcntNumb() {
		return emplAcntNumb;
	}

	public void setEmplAcntNumb(String emplAcntNumb) {
		this.emplAcntNumb = emplAcntNumb;
	}

	public String getEmplName() {
		return emplName;
	}

	public void setEmplName(String emplName) {
		this.emplName = emplName;
	}

	public String getQuaterYear() {
		return quaterYear;
	}

	public void setQuaterYear(String quaterYear) {
		this.quaterYear = quaterYear;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	
	public void setTotalWageFromUtil(BigDecimal totalWage) {
		this.totalWage = totalWage;
	}
}