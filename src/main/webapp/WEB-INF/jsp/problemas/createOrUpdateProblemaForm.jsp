<%@ page session="false" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="problemas">
    <h2>
        <c:if test="${problema['new']}">Nuevo </c:if> Problema
    </h2>
    <form:form modelAttribute="problema" class="form-horizontal" id="add-problema-form"  enctype="multipart/form-data">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre del problema" name="name"/>
            <petclinic:inputField label="Descripción" name="descripcion"/>
            <petclinic:inputField label="Dificultad" name="dificultad"/>
            <petclinic:inputField label="Puntuacion" name="puntuacion"/>
            <petclinic:inputField label="Casos prueba" name="casos_prueba"/>
            <petclinic:inputField label="Salida esperada" name="salida_esperada"/>
            <petclinic:inputField label="Season" name="season"/>
            <petclinic:inputField label="Season year" name="seasonYear"/>
            <table>
            <form:form enctype="multipart/form-data">
             <tr><td>Image to upload:</td><td><input type="file" name="image" /></td></tr>  
             <tr><td>File to upload:</td><td><input type="file" name="zipo" /></td></tr>
            </form:form>
			</table>
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${problema['new']}">
                        <button class="btn btn-default" type="submit">Añadir Problema</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Problema</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 