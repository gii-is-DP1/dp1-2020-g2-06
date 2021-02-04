<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<html>
<head>
</head>
<body>
	<petclinic:layout pageName="error">
		<h3 style="text-align: center; padding-bottom: 50px">${error_mensaje}</h3>
		<a href="/" style="display: flex;justify-content: center;align-items: center;"><button class="btn btn-default" >Volver al inicio</button></a>
	</petclinic:layout>
</body>
</html>