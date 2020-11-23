<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="noticias">
    <h2>Noticias</h2>
   
	    
       
        <c:forEach items="${noticias}" var="noticia">
        <table id="noticiasTable" class="table table-striped">
            <tr>
                <th>                    
                    <c:out value="${noticia.name}"/>&nbsp;<c:out value="${noticia.fechaPublicacion}"/>
                </th>
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
            </tr>
                
            <tr>
                <td>
               <p style="text-align:center"> <img src="${noticia.imagen}" width=400px/></p>
                    <c:out value="${noticia.texto}"/>
                </td>                

            </tr>
             </table>
        </c:forEach>
       
   

</petclinic:layout> 
