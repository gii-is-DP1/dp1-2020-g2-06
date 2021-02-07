<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="normasWeb">
    <h2>Normas y funcionamiento de codeUS</h2>
    <p><b>Aquí puedes ver las normas de la web y cómo funciona la plataforma.</b></p>
    <br>
 <c:set var="count" value="1" scope="page" />
	<c:forEach items="${normas_web}" var="normasWeb">
		<table id="normasTable" class="table table-striped">
			<tr>
				<th><c:out value="${count}"/>.-<c:out value="${normasWeb.name}" /></th>
				<sec:authorize access="hasAuthority('tutor')">
					<th><a href="/normasWeb/${normasWeb.id}/edit"> <span
							class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</a></th>
					<th><a href="/normasWeb/${normasWeb.id}/delete"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"></span>
					</a></th>
				</sec:authorize>
			</tr>
			<tr>
				<td><c:out value="${normasWeb.descripcion}" /></td>
			</tr>
		</table>
		<c:set var="count" value="${count + 1}" scope="page"/>
	</c:forEach>

	<sec:authorize access="hasAuthority('tutor')">
		<a class="btn btn-default" href='<spring:url value="/normasWeb/new" htmlEscape="true"/>'>Añadir Norma Web</a>
	</sec:authorize>
</petclinic:layout>
