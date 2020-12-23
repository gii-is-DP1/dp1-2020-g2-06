<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="alumnos">
    <h2>Alumnos</h2>
<table id="noticiasTable" class="table table-striped">
	<c:forEach items="${alumnos}" var="alumno">
		
            <tr>
                <td>                    
                    <img src="/<c:out value="${alumno.imagen}"/>" id="Imagen" width="100px" style="border-radius:100%;"/>&nbsp;
                    <a href="/alumnos/${alumno.id}">
					<c:out value="${alumno.nombre}"/>&nbsp;<c:out value="${alumno.apellidos}"/> 
                    </a>
                </td>
                <td>
                	<a href="/alumnos/${alumno.id}/edit">
                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </td>
            </tr>
                
            
	</c:forEach>
 </table>
	<%-- <sec:authorize access="hasAuthority('admin')">  --%>
			<a class="btn btn-default" href='<spring:url value="/alumnos/new" htmlEscape="true"/>'>Añadir Alumno</a>
		<%-- </sec:authorize> --%>
</petclinic:layout>