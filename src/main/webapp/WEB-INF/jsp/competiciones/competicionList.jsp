<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="competiciones">
    <h2>Competiciones</h2>
   
	    
       
        <c:forEach items="${competiciones}" var="competicion">
        <table id="noticiasTable" class="table table-striped">
            <tr>
                <td>  
                	<a href="/competiciones/${competicion.id}">                  
                    <c:out value="${competicion.nombre}"/>
                    </a>
                </td>
                <td>
                	<a href="/competiciones/${competicion.id}/edit">
                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </td>
            </tr>
             </table>
        </c:forEach>
       
   

</petclinic:layout> 
