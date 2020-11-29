<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="articulo">

	<table id="articuloTable" class="table table-striped">
            <tr>
                <th>                    
                    <c:out value="${articulo.name}"/>&nbsp;<c:out value="${articulo.fechaPublicacion}"/>
                </th>
                <th>
                	<a href="/articulos/${articulo.id}/edit">
                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </th>
                <th>
                	<a href="/articulos/${articulo.id}/delete">
                		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                	</a>
                </th>
            </tr>
                
            <tr>
                <td>
               <p style="text-align:center"> <img src="${articulo.imagen}" width=400px/></p>
                    <c:out value="${articulo.texto}"/>
                </td>                

            </tr>
             </table>
	
</petclinic:layout>
