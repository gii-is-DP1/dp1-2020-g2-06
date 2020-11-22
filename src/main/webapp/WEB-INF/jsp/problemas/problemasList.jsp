<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="problemas">
    <h2>Problemas</h2>

	<c:forEach items="${problema}" var="problemas">
		<table id="problemasTable" class="table table-striped">
			<tr>
				<th><a href="/problemas/${problemas.id}"> <c:out value="${problemas.name}" /> </a></th>
				<th><a href="/problemas/${problemas.id}/edit"> <span
						class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				</a></th>
				<th><a href="/problemas/${problemas.id}/delete"> <span
						class="glyphicon glyphicon-trash" aria-hidden="true"></span>
				</a></th>
			</tr>
		</table>
	</c:forEach>

	<%-- <sec:authorize access="hasAuthority('admin')">  --%>
			<a class="btn btn-default" href='<spring:url value="/problemas/new" htmlEscape="true"/>'>Añadir Problema</a>
		<%-- </sec:authorize> --%>
</petclinic:layout>
