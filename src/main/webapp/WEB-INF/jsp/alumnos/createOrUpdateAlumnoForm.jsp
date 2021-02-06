<%@ page session="false" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="alumno">
    <h2>
        <c:if test="${alumno['new']}">Nuevo </c:if> Alumno
    </h2>
    <form:form modelAttribute="alumno" class="form-horizontal" id="add-alumno-form" enctype="multipart/form-data">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Apellidos" name="apellidos"/>
            <c:if test="${alumno['new']}">
            	<petclinic:inputField label="Email" name="email"/> 
            </c:if>
            <petclinic:inputField label="Contraseña" name="pass" type="password"/>
            <c:if test="${not alumno['new']}">
            	<input type="hidden" name="email" value="${alumno.email}"/>
            	<form:checkbox path="compartir" label ="Compartir soluciones de Problemas resueltos" />
            </c:if>
            <table>
            <form:form enctype="multipart/form-data">
             <tr><td>Image to upload:</td><td><input type="file" name="image" /></td></tr>
            </form:form>
			</table>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${alumno['new']}">
                        <button class="btn btn-default" type="submit">Añadir alumno</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar alumno</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 