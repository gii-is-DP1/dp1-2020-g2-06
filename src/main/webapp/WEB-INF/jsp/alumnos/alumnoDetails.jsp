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
            <td></td>
        </tr>
        <tr>
            <th>Puntuación Anual</th>
            <td></td>
        </tr>
        <tr>
            <th>Puntuación Total</th>
            <td></td>
        </tr>
       
    </table>
	
   
    <spring:url value="/alumnos/${alumno.id}/edit" var="editUrl"> </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Alumno</a>
    
	<br>
	<br>
   
    <h2> Últimos envíos</h2>
    <table class="table table-striped">
  
    	<tr>
    	<th> Envío
    	</th>
    	<th>
    	Problema
    	</th>
    	<th>
    	Fecha y hora
    	</th>
    	<th>
    	Veredicto
    	</th>
    	</tr>
    	<tr>
    	  <c:forEach items="${alumno.envios}" var="envio">
    		<td>
    		<a href="/envios/${envio.id}">
    		<c:out value="${envio.id}"/>
    		</a>
    		</td>
    		<td>
    		<a href="/problemas/${envio.problema.id}">
    		<c:out value="${envio.problema.name}"/>
    		</a>
    		</td>
    		<td>
    		<c:out value="${envio.fecha}"/>
    		</td>
    		<td>
    		<c:out value="${envio.resolucion}"/>
    		
    	</tr>
   		</c:forEach>
    </table>
    

</petclinic:layout>
