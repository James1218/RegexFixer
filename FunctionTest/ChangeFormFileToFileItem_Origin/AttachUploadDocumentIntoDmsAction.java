package gov.state.uim.app.struts;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import gov.state.uim.GlobalConstants;
import gov.state.uim.common.ServiceLocator;
import gov.state.uim.domain.bean.UploadDocumentIntoDmsBean;
import gov.state.uim.framework.logging.log4j.AccessLogger;
import gov.state.uim.framework.service.IObjectAssembly;
import gov.state.uim.framework.struts.BaseLookUpDispatchAction;


/**
 * @struts.action 
 * 		path="/taxattachuploaddocumentintodms" name="taxattachuploaddocumentintodmsform" scope="request"
 *      input=".taxattachuploaddocumentintodms" validate="true" parameter="method"
 *   
 * @struts.action-set-property value = "true" property = "load"
 * 
 * @struts.action-set-property value = "back" property = "forwards"
 * 
 * @struts.action-set-property value = "/taxuploaddocumentintodms" property = "preLoadPath"
 * 
 * @struts.action-forward name="back" path= ".taxuploaddocumentintodms" redirect="false"
 * 
 * @struts.action-set-property value ="gov.state.uim.app.service.TaxDMSService"
 *                             property = "bizServiceClass"
 *                             
 * @struts.action-set-property value="true" 
 *                                     property="onErrorSamePage"     
 *    
 */

public class AttachUploadDocumentIntoDmsAction extends BaseLookUpDispatchAction {
	private static final AccessLogger LOGGER = ServiceLocator.instance.getLogger(AttachUploadDocumentIntoDmsAction.class);

	public void load(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	     throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method load");
		}

		IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request, true);
         
         if (objAssembly == null) {
        	 
          objAssembly = ServiceLocator.instance.getObjectAssembly();
         }  
         
       setObjectAssemblyInSession(request, objAssembly);
     }

	
	@Override
	protected Map<String, String> getServiceKeyMethodName() {
		Map map = new HashMap();
	    map.put("submit", "attachUploadDocumentIntoDms");
		
	  return map;
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
	    map.put("access.submit", "submit");    
	    map.put("access.back", "back");
	    
	  return map;
	}
	
	@Override
	protected List<String> getNonValidateKey() {
		List<String> list = new ArrayList<String>();
		list.add("access.back");
		return list;
	}
	
	public  IObjectAssembly presubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException  {

		 AttachUploadDocumentIntoDmsForm currentForm = (AttachUploadDocumentIntoDmsForm)form;

         IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request,true);
         
         if (objAssembly == null) {
             objAssembly = ServiceLocator.instance.getObjectAssembly();
         }
         
         UploadDocumentIntoDmsBean documentBean = objAssembly.getFirstBean(UploadDocumentIntoDmsBean.class);
         documentBean.setCorrespondenceName(currentForm.getCorrId());
         documentBean.setFileName(currentForm.getFileName());
         
         this.uploadFile(currentForm.getFileName(),documentBean);
         objAssembly.addBean(documentBean, true);
         
      return objAssembly;	
   }


    public ActionForward submit(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	       throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method submit");
		}

        getObjectAssemblyFromSession(request, true);

        super.setSuccessPageHeading(request,"access.attach.correspondence.into.dms.complete.title");
        super.addSuccessMessage(request,"access.attach.correspondence.into.dms.confirmation");
        
      return (super.successMessageForward(mapping));
    }

    
    
	  /**
	 * Method for Back button.
	 */
    public IObjectAssembly preback(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
                throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method preback");
		}
		 
		IObjectAssembly objAssembly = getObjectAssemblyFromSession(request);
		objAssembly.removeAllComponent();
		return objAssembly; 
	 }
	
    
    public ActionForward back(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	          throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method back");
		}
		
		return super.backForward(mapping);
	 }
    

    private void uploadFile(FormFile avFile,UploadDocumentIntoDmsBean uploadBean) throws IOException
    {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method uploadFile");
		} 	
    	String uploadFormat = "";
		InputStream inputStream = null;
		StringTokenizer stoken = new StringTokenizer(avFile.getFileName(), ".");
		if (stoken.hasMoreTokens()){
			 stoken.nextToken();
			 uploadFormat = stoken.nextToken();
		}
		
		if(GlobalConstants.DOC_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){			
			 inputStream = avFile.getInputStream();
			 uploadBean.setInputStream(inputStream); 
			 uploadBean.setMimeType("application/msword");
			
		}else if(GlobalConstants.WORD_DOCX_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
		     inputStream = avFile.getInputStream();
		     uploadBean.setInputStream(inputStream);
		     uploadBean.setMimeType("application/msword");
	  
		}else if(GlobalConstants.PDF_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
			 inputStream = avFile.getInputStream();
			 uploadBean.setInputStream(inputStream);
			 uploadBean.setMimeType("application/pdf");		 
			 
	    }else if(GlobalConstants.EXCEL_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
			 inputStream = avFile.getInputStream();
			 uploadBean.setInputStream(inputStream);
			 uploadBean.setMimeType("application/vnd.ms-excel");
			 
	    }else if(GlobalConstants.XLSX_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
			 inputStream = avFile.getInputStream();
			 uploadBean.setInputStream(inputStream);
			 uploadBean.setMimeType("application/vnd.ms-excel");
			 	 
	    }else if(GlobalConstants.JPEG_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
			 inputStream = avFile.getInputStream();
			 uploadBean.setInputStream(inputStream);
			 uploadBean.setMimeType("image/pjpeg");
					 
        }else if(GlobalConstants.TIFF_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
			 inputStream = avFile.getInputStream();
			 uploadBean.setInputStream(inputStream);
			 uploadBean.setMimeType("image/x-tiff");
			 
        }else if(GlobalConstants.JPE_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
	        inputStream = avFile.getInputStream();
		    uploadBean.setInputStream(inputStream);
		    uploadBean.setMimeType("image/jpeg");  
		 
        }else if(GlobalConstants.JPG_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
	        inputStream = avFile.getInputStream();
	        uploadBean.setInputStream(inputStream);
	        uploadBean.setMimeType("image/jpeg");
	     
        }else if(GlobalConstants.JFIF_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
	       inputStream = avFile.getInputStream();
	       uploadBean.setInputStream(inputStream);
	       uploadBean.setMimeType("image/jpeg");  
	       
        }else if(GlobalConstants.TIF_FILE_EXTENSION.equalsIgnoreCase("."+uploadFormat)){
	       inputStream = avFile.getInputStream();
	       uploadBean.setInputStream(inputStream);
	       uploadBean.setMimeType("image/x-tiff");  
      }	
  }
}
    