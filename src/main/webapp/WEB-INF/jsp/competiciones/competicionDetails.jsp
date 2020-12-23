<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="competicion">

    <h2><c:out value="${competicion.nombre}"/></h2>
    <img src="/<c:out value="${competicion.imagen}"/>" id="Imagen" width="300" height="225">
    <br></br>


    <table class="table table-striped">
    	<tr>
            <th>Descripcion:</th>
            <td><c:out value="${competicion.descripcion}"/></td>
        </tr>
        <tr>
            <th>Fecha de inicio:</th>
            <td><c:out value="${competicion.fecha_inicio}"/></td>
        </tr>
        <tr>
            <th>Fecha Fin</th>
            <td><c:out value="${competicion.fecha_fin}"/></td>
        </tr>
        </table>
        <h1>Problemas:</h1>
        <table>
           <c:forEach items="${competicion.problemas}" var="problema">
           <tr>
           	<td>
           		<a href="/problemas/${problema.id}">
           		<c:out value="${problema.name}"></c:out>
           		</a>
           	</td>
           	</tr>
           </c:forEach>
    </table>
</petclinic:layout>

