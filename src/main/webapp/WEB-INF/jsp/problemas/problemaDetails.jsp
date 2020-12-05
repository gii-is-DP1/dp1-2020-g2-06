<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <td><c:out value="${problema.season}"/></td>
        </tr>
        <tr>
            <th>Casos de Prueba</th>
            <td><c:out value="${problema.casos_prueba}"/></td>
        </tr>
        <tr>
            <th>Salida Esperada</th>
            <td><c:out value="${problema.salida_esperada}"/></td>
        </tr>
    </table>
	 <c:if test="${editarTrue == 1}">
    <spring:url value="{problemaId}/edit" var="editUrl"> <spring:param name="problemaId" value="${problema.id}"/> </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Problema</a>
    </c:if>

</petclinic:layout>
