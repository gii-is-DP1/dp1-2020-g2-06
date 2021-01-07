<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="envios">

    <h2>Tablón de mensajes</h2>
          
      <c:forEach items="${publicaciones}" var="publicacion">
	<table class="table table-striped">
    <tr>
    <td><img src="/<c:out value="${publicacion.alumno.imagen}"/>" id="Imagen" width="50" style="border-radius:100%"/>&nbsp;
    <a href="/alumnos/${publicacion.alumno.id}">
    <c:out value="${publicacion.alumno.nombre} ${publicacion.alumno.apellidos}"/>
    </a></td>
    </tr>
  
    	<tr>
    	<td><c:out value="${publicacion.texto}"/><td></tr>
    	
    	</table>
	</c:forEach>
    
    
	
</petclinic:layout>
