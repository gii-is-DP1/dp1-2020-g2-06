<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<petclinic:layout pageName="noticia">

	<table id="noticiasTable" class="table table-striped">
            <tr>
                <th>                    
                    <c:out value="${noticia.name}"/>&nbsp;<c:out value="${noticia.fechaPublicacion}"/>
                </th>
                <sec:authorize access="hasAuthority('tutor')"> 
                <th>
                	<a href="/noticias/${noticia.id}/edit">
                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </th>
                <th>
                	<a href="/noticias/${noticia.id}/delete">
                		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                	</a>
                </th>
                </sec:authorize>
            </tr>
                
            <tr>
                <td>
               <p style="text-align:center"> <img src="/<c:out value="${noticia.imagen}"/>" id="Imagen" width="400px"></p>
                    <c:out value="${noticia.texto}"/>
                </td>                

            </tr>
             </table>
             
             <h2>Realizado por:</h2>
             <table>
             <c:forEach items="${noticia.autores}" var="autor">
             	<tr>
             		<td>
             			<c:out value="${autor.nombre}"/>&nbsp;<c:out value="${autor.apellidos}"/>
             		</td>             
             	</tr>
             </c:forEach>
             
             </table>
	
</petclinic:layout>
