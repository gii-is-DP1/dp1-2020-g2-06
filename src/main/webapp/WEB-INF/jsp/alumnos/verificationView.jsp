<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<petclinic:layout pageName="verificationView">
<h>Verifica tu correo electr�nico</h>
<p>Para poder acceder con tu cuenta a la p�gina, por favor, visita tu buzon de correo electr�nico y sigue los pasos de verificaci�n. Gracias!</p>

<br>
<br>
	<!--
    <form:form modelAttribute="alumno" class="form-horizontal" id="add-alumno-form" enctype="multipart/form-data">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Email" name="email"/>
            <petclinic:inputField label="Contrase�a" name="pass" type="password"/>
            <petclinic:inputField label="C�digo de verificaci�n" name="confirmation-token" type="text"/>
            <table>
			</table>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Verificar correo</button>
            </div>
        </div>
    </form:form>
    <form:form action="confirmation/new" modelAttribute="Confirmacion" class="form-horizontal" id="add-owner-form">
				<petclinic:inputField label="Email" name="email"/>
            	<petclinic:inputField label="Contrase�a" name="pass" type="password"/>
            	<petclinic:inputField label="C�digo de verificaci�n" name="confirmation-token" type="text"/>
				<button class="btn btn-default" type="submit">Verificar correo</button>
			</form:form>
    -->
</petclinic:layout> 