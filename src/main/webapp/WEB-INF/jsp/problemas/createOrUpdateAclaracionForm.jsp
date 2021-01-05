<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="problemas">

    <h2><c:out value="${problema.name}"/></h2>
    <img src="<c:out value="${problema.imagen}"/>" id="Imagen" width="300" height="225">
    


    <table class="table table-striped">
    	<tr>
            <th>Descripcion</th>
            <td><c:out value="${problema.descripcion}"/></td>
        </tr>
        <tr>
            <th>Puntuacion</th>
            <td><c:out value="${problema.puntuacion}"/></td>
        </tr>
        <tr>
            <th>Temporada</th>
            <td><c:out value="${problema.season}"/>&nbsp;<c:out value="${problema.seasonYear}"/></td>
        </tr>
        <tr>
            <th>Casos de Prueba</th>
            <td><c:out value="${problema.casos_prueba}"/></td>
        </tr>
        <tr>
            <th>Salida Esperada</th>
            <td><c:out value="${problema.salida_esperada}"/></td>
        </tr>
        <tr>
            <th>Puntuación de los alumnos</th>
            <c:forEach items="${problema.puntuacionesProblema}" var="puntuacionProblema">
            	<td><c:out value="${puntuacionProblema.puntuacion}"/></td>
   			</c:forEach>
        </tr>
        <tr>
            <th>Aclaraciones de los tutores</th>
            <c:forEach items="${problema.aclaraciones}" var="aclaracion">
            	<td><c:out value="${aclaracion.tutor.nombre}"/> <c:out value=" ${aclaracion.tutor.apellidos}"/> 
            	<br> 
            	<c:out value="${aclaracion.texto}"/>
            	<br></td>
   			</c:forEach>
        </tr>
    </table>
	 <c:if test="${editarTrue == 1}">
    <spring:url value="{problemaId}/edit" var="editUrl"> <spring:param name="problemaId" value="${problema.id}"/> </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Problema</a>
    </c:if>
    
    <c:forEach items="${ultimosEnvios}" var="envio">
    	<spring:url value="envios/{envioId}" var="editUrl"> <spring:param name="envioId" value="${envio.id}"/> </spring:url>
    </c:forEach>
			
	<a class="btn btn-default" href='<spring:url value="/aclaraciones/new" htmlEscape="true"/>'>Añadir Aclaración</a>











    <h2>
        <c:if test="${aclaracion['new']}">Nueva </c:if> Aclaracion
    </h2>
    <form:form modelAttribute="problema" class="form-horizontal" id="add-problema-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre del problema" name="name"/>
            <petclinic:inputField label="Descripción" name="descripcion"/>
            <petclinic:inputField label="Puntuacion" name="puntuacion"/>
            <petclinic:inputField label="Casos_prueba" name="casos_prueba"/>
            <petclinic:inputField label="Salida_esperada" name="salida_esperada"/>
            <petclinic:inputField label="Imagen" name="imagen"/>
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