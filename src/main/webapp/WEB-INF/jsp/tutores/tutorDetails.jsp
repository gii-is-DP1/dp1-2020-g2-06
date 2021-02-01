<%@ page session="false" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<petclinic:layout pageName="tutor">

	<h2>
    <img src="/<c:out value="${tutor.imagen}"/>" id="Imagen" width="200" style="border-radius:100%"/>&nbsp;
	<c:out value="${tutor.nombre}"/>&nbsp;<c:out value="${tutor.apellidos}"/>
	</h2>

	 <table class="table table-striped">
	 <tr>	 
            <th>Email</th>
            <td><c:out value="${tutor.email}"/></td>
        </tr>
    </table>
    
    <c:if test="${me}">
    <spring:url value="/tutores/${tutor.id}/edit" var="editUrl"> </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Tutor</a>
   	</c:if>
   
    <br> <br>
    <br> <br>
   
    <h2> Noticias</h2>
    <table class="table table-striped">
    <c:forEach items="${noticiasTutor}" var="noticia">
    	<tr>
    		<td>
    		<a href="/noticias/${noticia.id}">
    		<c:out value="${noticia.name}"/>&nbsp;&nbsp;<c:out value="${noticia.fechaPublicacion}"></c:out>
    		</a>
    		</td>
    	</tr>
   	</c:forEach>
    </table>
    <div style="text-align: center;">
    	<c:if test="${!esPrimeraPaginaNoticia}">
    		<a id="anterior" href="/tutores/${tutor.id}/?page-art=${pageartactual}&page-not=${ppnoticia}">«</a>
    	</c:if>
    		<c:out value="${pagenotactual}"></c:out>
    	<c:if test="${!esUltimaPaginaNoticia}">
    		<a id="siguiente" href="/tutores/${tutor.id}/?page-art=${pageartactual}&page-not=${npnoticia}">»</a>
    	</c:if>
    </div>
   
    <br> <br>
   
    <h2> Articulos</h2>
    <div>
    <table class="table table-striped">
    	<c:forEach items="${articulosTutor}" var="articulo">
    	<tr>
    		<td>
    		<a href="/articulos/${articulo.id}">
    		<c:out value="${articulo.name}"/>&nbsp;&nbsp;<c:out value="${articulo.fechaPublicacion}"></c:out>
    		</a>
    		</td>
    	</tr>
   	</c:forEach>
    </table>
    </div>
    <div style="text-align: center;">
    	<c:if test="${!esPrimeraPaginaArticulo}">
    		<a id="anterior" href="/tutores/${tutor.id}/?page-art=${pparticulo}&page-not=${pagenotactual}">«</a>
    	</c:if>
    		<c:out value="${pageartactual}"></c:out>
    	<c:if test="${!esUltimaPaginaArticulo}">
    		<a id="siguiente" href="/tutores/${tutor.id}/?page-art=${nparticulo}&page-not=${pagenotactual}">»</a>
    	</c:if>
    </div>
    
    <c:if test="${me}">
	    <div>
	    <table class="table table-striped">
	    	<c:forEach items="${preguntasTutor}" var="pregunta">
	    	<tr>
	    		<td>
	   				<c:out value="Alumno : ${pregunta.alumno.email}"/>
	    		<br>
	    			<a href="/problemas/${pregunta.problema.id}"> Problema : <c:out value="${pregunta.problema.name}"/> </a>
	    		<br>
	    		<c:out value="Pregunta : ${pregunta.pregunta}"></c:out>
	    		<br>
	    		<form:form class="form-horizontal" id="add-problema-form" action="/preguntatutor/answer">
	    			<input type=hidden name=idTutor value="${tutor.id}"/>
	    			<input type=hidden name=preguntaTutor value="${pregunta.id}"/>
	    			<textArea  name="respuesta" rows="6"> </textArea>
	    			<button class="btn btn-default" type="submit">Responder</button>
	    		</form:form>
	    		</td>
	    	</tr>
	   	</c:forEach>
	    </table>
	    </div>
    </c:if>
</petclinic:layout>