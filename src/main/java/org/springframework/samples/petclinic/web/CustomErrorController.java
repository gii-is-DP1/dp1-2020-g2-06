package org.springframework.samples.petclinic.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/error")
@Controller
public class CustomErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@GetMapping()
	public String handleError(HttpServletRequest request, Exception ex) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				request.setAttribute("error_mensaje", "La página a la que quiere acceder no existe");
			}
			else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				request.setAttribute("error_mensaje", "Parece que tenemos problema. Inténtelo de nuevo más tarde");
			}
			else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				request.setAttribute("error_mensaje", "Parece que no tiene permisos");
			}
			else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				request.setAttribute("error_mensaje", "El párametro no es válido");	
			}
			return "exception";
		}
		request.setAttribute("error_mensaje", "¡Vaya! Ha ocurrido un error...");
		return "exception";
	}
	
}
