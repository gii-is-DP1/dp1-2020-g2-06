package org.springframework.samples.petclinic.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

			if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				request.setAttribute("error_mensaje", "La petición no se ha formulado correctamente.");	
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				request.setAttribute("error_mensaje", "Lo sentimos. No tiene permisos para realizar la acción");
			} else if (statusCode == HttpStatus.NOT_FOUND.value()) {
				request.setAttribute("error_mensaje", "Lo sentimos. El sitio al que quiere acceder no existe");
			} else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
				request.setAttribute("error_mensaje", "El método no está permitido");	
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				request.setAttribute("error_mensaje", "Tenemos problemas internos. Intentelo de nuevo más tarde!");

			}
			return "exception";
		}
		request.setAttribute("error_mensaje", "¡Ups! Parece que algo ha ido mal...");
		return "exception";
	}
	
}
