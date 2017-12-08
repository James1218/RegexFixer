package gov.state.uim.tra.struts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import gov.state.uim.domain.bean.UploadTrainingFieldStudyBean;
import gov.state.uim.framework.struts.BaseValidatorForm;

/**
 * @struts.form name = "taauploadtrainingfieldstudyfileform"
 */
public class TAAUploadTrainingFieldStudyFileForm extends BaseValidatorForm {
	private static final Logger LOGGER = Logger.getLogger(TAAUploadTrainingFieldStudyFileForm.class);


	private static final long serialVersionUID = -9053797539025894902L;
	private FormFile fileName;
	private UploadTrainingFieldStudyBean lObjBean ;
	private List lObjList = new ArrayList();
	
	
	public FormFile getFileName() {
		return fileName;
	}
	public void setFileName(FormFile fileName) {
		this.fileName = fileName;
	}
	
	public List getLObjList() {
		return lObjList;
	}
	public void setLObjList(List objList) {
		lObjList = objList;
	}
	
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1)
	{
		ActionErrors errors = null;
//			try
//			{
				errors = super.validate(arg0, arg1);
				
				if(errors.size() > 0){
					return errors;
				} 
				if((this.fileName != null) && (!this.fileName.getFileName().equals("")))
				{
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Empty if/else Statement");
			}

						//do operation
			    }
				else
				{
					  errors.add("fileName", new ActionMessage(
						"error.required", this.getApplicationMessages(arg1, "error.access.taa.uploadtrainingfacilityfile.nofileselected")));
				}				
//			}
//			catch (IOException e) 
//			{
//			}
			return errors;
	}
}