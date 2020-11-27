<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="alumno">

	<h2>
    <img src="<c:out value="${alumno.imagen}"/>" width="200" style="border-radius:100%"/>&nbsp;
	<c:out value="${alumno.nombre}"/>&nbsp;<c:out value="${alumno.apellidos}"/>
	</h2>

	 <table class="table table-striped">
	 <tr>
            <th>Email</th>
            <td><c:out value="${alumno.email}"/></td>
        </tr>
    	<tr>
            <th>Puntuación Temporada</th>
            <td><c:out value="${alumno.puntosTemporada}"/></td>
        </tr>
        <tr>
            <th>Puntuación Anual</th>
            <td><c:out value="${alumno.puntosAnual}"/></td>
        </tr>
        <tr>
            <th>Puntuación Total</th>
            <td><c:out value="${alumno.puntosTotales}"/></td>
        </tr>
       
    </table>
	
   
    <spring:url value="/alumnos/${alumno.id}/edit" var="editUrl"> </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Alumno</a>

</petclinic:layout>
