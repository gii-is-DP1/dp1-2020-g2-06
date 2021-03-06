<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<petclinic:layout pageName="envios">

    <h2>Env�o n�mero <c:out value="${envio.id}"/></h2>
    
    <table class="table table-striped">
    	<tr>
            <th>Fecha de env�o</th>
            <td><c:out value="${envio.fechaFormat}"/></td>
        </tr>
        <tr>
            <th>Alumno</th>
            <td><a href="/alumnos/${envio.alumno.id}">
            <c:out value="${envio.alumno.nombre}"/>&nbsp;<c:out value="${envio.alumno.apellidos}"/>
            </a></td>
        </tr>
        <tr>
            <th>Problema</th>
            <td><a href="/problemas/${envio.problema.id}">
            <c:out value="${envio.problema.name}"/>
            </a></td>
        </tr>
        <tr>
            <th>Resoluci�n</th>
            <td><c:out value="${envio.resolucion}"/></td>
        </tr>
        <tr>
            <th>C�digo</th>
            <c:choose>
            <c:when test="${envio.problema.vigente || !envio.alumno.compartir || me==false}">
            
             <td>C�digo no disponible
               </td>
               
               </c:when>
               <c:otherwise>
               
               <td><c:forEach items="${codigo}" var="linea">
            <c:out value="${linea}"/><br>
               </c:forEach>
               </td>
               
               </c:otherwise>
               
               </c:choose>
         
        </tr>
        
        
    
        
    </table>
    
    
    <h3>Comentarios</h3>
          
      <c:forEach items="${envio.listaComentarios}" var="comentario">
	<table class="table table-striped">
    <tr>
    <td><img src="/<c:out value="${comentario.alumno.imagen}"/>" id="Imagen" width="50" style="border-radius:100%"/>&nbsp;
    <a href="/alumnos/${comentario.alumno.id}">
    <c:out value="${comentario.alumno.nombre} ${comentario.alumno.apellidos}"/>
    </a></td>
    </tr>
  
    	<tr>
    	<td><c:out value="${comentario.texto}"/><td></tr>
    	
    	</table>
	</c:forEach>
	
	<sec:authorize access="hasAuthority('alumno')">
		<div>
			<form:form action="/comentarios/new" modelAttribute="comentarioNuevo" class="form-horizontal" id="add-owner-form">
				<petclinic:textArea label="Comentario" name="texto" rows="6" />
				<input type="hidden" name="idEnvio" value="${envio.id}" />
				<button class="btn btn-default" type="submit">A�adir Comentario</button>
			</form:form>
		</div>
	</sec:authorize>
    
    
	
</petclinic:layout>
