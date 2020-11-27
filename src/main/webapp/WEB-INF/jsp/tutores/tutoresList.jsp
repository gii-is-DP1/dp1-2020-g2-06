<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="tutores">
    <h2>Tutores</h2>

    <table id="tutoresTable" class="table table-striped">
    	<tbody>
    	<tr>
    		<th>
    			Foto
    		</th>
			<th>
				Nombre
			</th>
			<th>
				Apellidos
			</th>
			<th>
				Email
			</th>
		</tr>
       <c:forEach items="${tutores}" var="tutor">
        	<tr>
        	<td>
        		<img src="<c:out value="${tutor.foto}"/>" id="Imagen" width="165" height="100">
        	</td>
            <td>                    
               <c:out value="${tutor.nombre}"/>
            </td>
            <td>
             	<c:out value="${tutor.apellidos}"/>
         	</td>
         	<td>
         		<c:out value="${tutor.email}"/>
         	</td>
         	<th>
                	<a href="/tutores/${tutor.email}/edit">
                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </th>
         	</tr>     
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/tutores/new" htmlEscape="true"/>'>Crear Tutor</a>
</petclinic:layout>