<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
            <td><c:out value="${puntostemporada}"/></td>
        </tr>
        <tr>
            <th>Puntuación Anual</th>
            <td><c:out value="${puntosanuales}"/></td>
        </tr>
        <tr>
            <th>Puntuación Total</th>
            <td><c:out value="${puntostotales}"/></td>
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
    
    
    <br>
	<br>
   
    <h2> Problemas resueltos (estando vigentes)</h2>
    <table class="table table-striped">
  
    	
    	<tr>
    	  <c:forEach items="${problemasresueltos}" var="problem">
    		<td>
    		<a href="/problemas/${problem.id}">
    		<c:out value="${problem.name}"/>
    		</a>
    		</td>
    		
    	</tr>
   		</c:forEach>
    </table>
    

</petclinic:layout>
