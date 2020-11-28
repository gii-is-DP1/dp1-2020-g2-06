<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="tutores">
    <h2>Tutores</h2>

    <table id="tutoresTable" class="table table-striped">
    	<tbody>
       <c:forEach items="${tutores}" var="tutor">
        	<tr>
                <td>                    
                    <img src="${tutor.foto}" width="100px" style="border-radius:100%;"/>&nbsp;
					<a href="/tutores/${tutor.id}">
					<c:out value="${tutor.nombre}"/>&nbsp;<c:out value="${tutor.apellidos}"/> 
               		</a>
                </td>
                <td>
                	<a href="/tutores/${tutor.id}/edit">
                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </td>
            </tr>     
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/tutores/new" htmlEscape="true"/>'>Crear Tutor</a>
</petclinic:layout>