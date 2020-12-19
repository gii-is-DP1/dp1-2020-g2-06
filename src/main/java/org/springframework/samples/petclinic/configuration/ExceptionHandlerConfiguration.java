package org.springframework.samples.petclinic.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * This advice is necessary because MockMvc is not a real servlet environment, therefore it does not redirect error
 * responses to [ErrorController], which produces validation response. So we need to fake it in tests.
 * It's not ideal, but at least we can use classic MockMvc tests for testing error response + document it.
 */
@ControllerAdvice
public class ExceptionHandlerConfiguration 
{
	@Autowired
	private BasicErrorController errorController;
    // add any exceptions/validations/binding problems

   @ExceptionHandler(Exception.class)
   public String defaultErrorHandler(HttpServletRequest request,  Exception ex)  {
        request.setAttribute("javax.servlet.error.request_uri", request.getPathInfo());
        request.setAttribute("javax.servlet.error.status_code", 400);
        request.setAttribute("exeption", ex);
        return "exception";
    }
   
   //ExceptionHandler para subida de zips
   @ExceptionHandler(MaxUploadSizeExceededException.class)
   public ModelAndView handleMaxSizeException (MaxUploadSizeExceededException exc,HttpServletRequest request,HttpServletResponse response){
	   ModelAndView modelAndView = new ModelAndView("problemas");
       modelAndView.getModel().put("message", "File too large!");
       return modelAndView;
   }
}