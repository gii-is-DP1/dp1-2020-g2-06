<%@ page session="false" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="administrador">
	<div style="display: inline-block;">
    	<a class="btn btn-default" href='<spring:url value="/tutores/new" htmlEscape="true"/>'>Añadir Tutores</a>
    	<a class="btn btn-default" href='<spring:url value="/creadores/new" htmlEscape="true"/>'>Añadir Creadores</a>
    </div>
</petclinic:layout> 