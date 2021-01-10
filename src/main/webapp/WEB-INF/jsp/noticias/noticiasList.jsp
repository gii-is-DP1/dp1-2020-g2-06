<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<petclinic:layout pageName="noticias">
    <h2>Noticias</h2>
   
	    
       
        <c:forEach items="${noticias}" var="noticia">
        <table id="noticiasTable" class="table table-striped">
            <tr>
                <th>  
                	<a href="/noticias/${noticia.id}">                  
                    <c:out value="${noticia.name}"/>&nbsp;<c:out value="${noticia.fechaPublicacion}"/>
                    </a>
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
        </c:forEach>
        
        <sec:authorize access="hasAuthority('tutor')"> 
			<a class="btn btn-default" href='<spring:url value="/noticias/new" htmlEscape="true"/>'>Añadir Noticia</a>
		</sec:authorize>

</petclinic:layout> 
