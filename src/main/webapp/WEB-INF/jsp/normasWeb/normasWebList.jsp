<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="normasWeb">
    <h2>Normas Web</h2>

    <c:forEach items="${normas_web}" var="normasWeb">
        <table id="diseasesTable" class="table table-striped">
            <tr>
                <th>                    
                    <c:out value="${normasWeb.name}"/>&nbsp;<c:out value="${normasWeb.descripcion}"/>
                </th>
                <th>
                	<a href="/normasWeb/${normasWeb.id}/edit">
                	<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                	</a>
                </th>
                <th>
                	<a href="/normasWeb/${normasWeb.id}/delete">
                		<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                	</a>
                </th>
            </tr>
                
            <tr>
                <td>
                    <c:out value="${normasWeb.descripcion}"/>
                </td>                

            </tr>
             </table>
        </c:forEach>
</petclinic:layout>
