<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>

<petclinic:layout pageName="problemas">

    <h2><c:out value="${problema.name}"/></h2>
    <img src="/<c:out value="${problema.imagen}"/>" id="Imagen" width="300">
    
	<br>
	<br>

    <table class="table table-striped">
    	<tr>
            <th>Descripcion</th>
            <td><c:out value="${problema.descripcion}"/></td>
        </tr>
        <tr>
            <th>Puntuacion</th>
            <td><c:out value="${problema.puntuacion}"/></td>
        </tr>
        <tr>
            <th>Temporada</th>
            <td><c:out value="${problema.season}"/>&nbsp;<c:out value="${problema.seasonYear}"/></td>
        </tr>
        <tr>
            <th>Casos de Prueba</th>
            <td><c:out value="${problema.casos_prueba}"/></td>
        </tr>
        <tr>
            <th>Salida Esperada</th>
            <td><c:out value="${problema.salida_esperada}"/></td>
        </tr>
     
        <tr>
            <th>Puntuación de los alumnos</th>
            	<c:if test="${puntuacionMedia != -1.0}">
            		<td><c:out value="${puntuacionMedia}"/></td>
            	</c:if>
            	<c:if test="${puntuacionMedia == -1.0}">
            		<td>Problema sin puntuación por el momento</td>
            	</c:if>
        </tr>
        
        <tr>
            <th>Aclaraciones de los tutores</th>
            <c:forEach items="${problema.aclaraciones}" var="aclaracion">
            	<td><c:out value="${aclaracion.tutor.nombre}"/> <c:out value=" ${aclaracion.tutor.apellidos}"/> 
            	<br> 
            	<c:out value="${aclaracion.texto}"/>
            	<br></td>
   			</c:forEach>
        </tr>
    </table>
	 <c:if test="${editarTrue == 1}">
    <spring:url value="{problemaId}/edit" var="editUrl"> <spring:param name="problemaId" value="${problema.id}"/> </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Problema</a>
    </c:if>
    
    <c:forEach items="${ultimosEnvios}" var="envio">
    	<spring:url value="envios/{envioId}" var="editUrl"> <spring:param name="envioId" value="${envio.id}"/> </spring:url>
    </c:forEach>
			
	<a class="btn btn-default" href='<spring:url value="aclaraciones/new" htmlEscape="true"/>'>Añadir Aclaración</a>
	
	<select id="isTitles" name="isTitles">
   			<c:forEach items="${alumnos}" var="alumno">
			    <option value="${alumno.nombre}"><c:out value="${alumno.nombre}"/></option>
   			</c:forEach>
			</select>
			<select id="isTitles" name="isTitles">
   				<option value="0">0</option>
   				<option value="1">1</option>
   				<option value="2">2</option>
   				<option value="3">3</option>
   				<option value="4">4</option>
   				<option value="5">5</option>
   				<option value="6">6</option>
   				<option value="7">7</option>
   				<option value="8">8</option>
   				<option value="9">9</option>
   				<option value="10">10</option>
			</select>
	<br>
	<br>
	<h2>Estadisticas</h2>
	<div id="graficaDonut" style="height: 250px;"></div>
	<script>
	var morris1 = new Morris.Donut({
		  element: 'graficaDonut',
		  data: [
		    <c:forEach items="${resoluciones}" var="resolucion">
		    	{label: '<c:out value="${resolucion.key}"></c:out>', value: <c:out value="${resolucion.value}"></c:out>},
		    </c:forEach>
		  ],
		  resize: true
		});
	</script>
	<table class="table table-striped">
	<c:forEach items="${resoluciones}" var="resolucion">
	<tr>
		<td><c:out value="${resolucion.key}"></c:out>: <c:out value="${resolucion.value}"></c:out>/<c:out value="${totalEnvios}"></c:out>
		(<script type="text/javascript">
		var a = <c:out value="${totalEnvios}"></c:out>;
		var b = <c:out value="${resolucion.value}"></c:out>;
		var c = (b/a)*100;
		document.write(c.toFixed());
		</script>%)</td>
	<tr>
	</c:forEach>
	</table>
</petclinic:layout>
