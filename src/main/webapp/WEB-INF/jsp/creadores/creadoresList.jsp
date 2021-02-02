<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<petclinic:layout pageName="creadores">
    <h2>Creadores</h2>

    <table id="creadoresTable" class="table table-striped">
    	<tbody>
       <c:forEach items="${creadores}" var="creador">
        	<tr>
                <td>                    
                    <img src="/<c:out value="${creador.imagen}"/>" id="Imagen" width="100px" style="border-radius:100%;"/>&nbsp;
					<a href="/creadores/${creador.id}">
					<c:out value="${creador.nombre}"/>&nbsp;<c:out value="${creador.apellidos}"/> 
               		</a>
                </td>
                <sec:authorize access="hasAuthority('creador')">
	                <td>
	                	<a href="/creadores/${creador.id}/edit">
	                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
	                	</a>
	                </td>
	          	</sec:authorize>
            </tr>     
        </c:forEach>
        </tbody>
    </table>
    
    <sec:authorize access="hasAuthority('administrador')">
    	<a class="btn btn-default" href='<spring:url value="/creadores/new" htmlEscape="true"/>'>Crear Creador</a>
    </sec:authorize>
</petclinic:layout>