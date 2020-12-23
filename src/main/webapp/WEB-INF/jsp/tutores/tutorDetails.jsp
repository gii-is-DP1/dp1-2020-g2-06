<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
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
    
    <spring:url value="/tutores/${tutor.id}/edit" var="editUrl"> </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Tutor</a>
   
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
    		<a href="/noticias/${articulo.id}">
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
</petclinic:layout>