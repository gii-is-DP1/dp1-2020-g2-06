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
		System.out.println(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				request.setAttribute("error_mensaje", "La URl que esta tratando de buscar no existe o no se encuentra");
			}
			else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				request.setAttribute("error_mensaje", "Parece que tenemos problema. Inténtelo de nuevo más tarde");
			}
			else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				log.warn("Un usuario ha intentado acceder a informacion, a la cual no le esta permitida, con sesion: "+request.getSession());
				request.setAttribute("error_mensaje", "Parece que no tiene permisos para realizar dicha acción");
			}
			else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				log.warn("Un usuario ha introducido un dato incorrecto, con sesion: "+request.getSession());
				request.setAttribute("error_mensaje", "El párametro que ha introducido no es válido o tiene un formato incorrecto");	
			}
			return "exception";
		}
		request.setAttribute("error_mensaje", "¡Vaya! Ha ocurrido un error...");
		return "exception";
	}
	
}
