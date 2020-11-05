<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="inicio">
    <h2>Noticias</h2>
   
	    <table id="diseasesTable" class="table table-striped">
       
        <c:forEach items="${noticias}" var="noticia">
            <tr>
                <th>                    
                    <c:out value="${noticia.name}"/>
                </th>
                <td>
                    <c:out value="${noticia.texto}"/>
                </td>                

            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout> 
