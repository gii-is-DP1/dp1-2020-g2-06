<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="creador">

	<h2>
    <img src="<c:out value="${creador.foto}"/>" width="200" style="border-radius:100%"/>&nbsp;
	<c:out value="${creador.nombre}"/>&nbsp;<c:out value="${creador.apellidos}"/>
	</h2>

	 <table class="table table-striped">
	 <tr>	 
            <th>Email</th>
            <td><c:out value="${creador.email}"/></td>
        </tr>
       
    </table>
	
   
    <spring:url value="/creadores/${creador.id}/edit" var="editUrl"> </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Creador</a>

</petclinic:layout>