<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="problemas">
    <h2>Problemas vigentes</h2>

	<c:forEach items="${problemasVigentes}" var="problemaVigente">
		<table id="problemasTable" class="table table-striped">
			<tr>
				<th><a href="/problemas/${problemaVigente.id}"> <c:out value="${problemaVigente.name}" /> </a></th>
				<th><a href="/problemas/${problemaVigente.id}/edit"> <span
						class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				</a></th>
				<th><a href="/problemas/${problemasVigente.id}/delete"> <span
						class="glyphicon glyphicon-trash" aria-hidden="true"></span>
				</a></th>
			</tr>
		</table>
	</c:forEach>
	
	<h2>Problemas no vigentes</h2>
	
	<c:forEach items="${problemasNoVigentes}" var="problemaNoVigente">
		<table id="problemasTable" class="table table-striped">
			<tr>
				<th><a href="/problemas/${problemaNoVigente.id}"> <c:out value="${problemaNoVigente.name}" /> </a></th>
				<th><a href="/problemas/${problemaNoVigente.id}/delete"> <span
						class="glyphicon glyphicon-trash" aria-hidden="true"></span>
				</a></th>
			</tr>
		</table>
	</c:forEach>

	<%-- <sec:authorize access="hasAuthority('admin')">  --%>
			<a class="btn btn-default" href='<spring:url value="/problemas/new" htmlEscape="true"/>'>Añadir Problema</a>
		<%-- </sec:authorize> --%>
</petclinic:layout>
