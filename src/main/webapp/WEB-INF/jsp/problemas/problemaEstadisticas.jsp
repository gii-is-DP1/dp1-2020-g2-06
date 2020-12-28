<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="problemas">

    <h2><c:out value="${problema.name}"/></h2>
	<br>
	<h2></h2>
	<table class="table table-striped">
	<c:forEach items="${resoluciones}" var="resolucion">
	<tr>
		<td>De los <c:out value="${totalEnvios}"></c:out> envios totales, <c:out value="${resolucion.value}"></c:out> son del tipo: 
		<c:out value="${resolucion.key}"></c:out> (<script type="text/javascript">
		var a = <c:out value="${totalEnvios}"></c:out>;
		var b = <c:out value="${resolucion.value}"></c:out>;
		var c = (b/a)*100;
		document.write(c.toFixed());
		</script>%)</td>
	<tr>
	</c:forEach>
	</table>
	
</petclinic:layout>