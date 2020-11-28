<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="creadores">
    <h2>Creadores</h2>

    <table id="creadoresTable" class="table table-striped">
    	<tbody>
       <c:forEach items="${creadores}" var="creador">
        	<tr>
                <td>                    
                    <img src="${creador.foto}" width="100px" style="border-radius:100%;"/>&nbsp;
					<a href="/creadores/${creador.id}">
					<c:out value="${creador.nombre}"/>&nbsp;<c:out value="${creador.apellidos}"/> 
               		</a>
                </td>
                <td>
                	<a href="/creadores/${creador.id}/edit">
                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </td>
            </tr>     
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/creadores/new" htmlEscape="true"/>'>Crear Creador</a>
</petclinic:layout>