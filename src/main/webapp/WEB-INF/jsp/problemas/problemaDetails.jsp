<%@ page language="java" 
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>

<petclinic:layout pageName="problemas">

    <h2><c:out value="${problema.name}"/></h2>
    <img src="/<c:out value="${problema.imagen}"/>" id="Imagen" width="300">
    
	<br>
	<br>
	
	<c:if test="${me}">
    	<spring:url value="/problemas/{problemaId}/edit" var="editUrl"> <spring:param name="problemaId" value="${problema.id}"/> </spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Problema</a>
    	<br>
    </c:if>

    <table class="table table-striped">
    	<tr>
            <th>Descripción</th>
            <td><c:out value="${problema.descripcion}" escapeXml="false"/></td>
        </tr>
        <tr>
            <th>Dificultad</th>
            <td><c:out value="${problema.dificultad}"/></td>
        </tr>
        <tr>
            <th>Puntos</th>
            <td><c:out value="${problema.puntuacion}"/></td>
        </tr>
        <tr>
            <th>Temporada</th>
            <td><c:out value="${problema.season.nombre}"/>&nbsp;<c:out value="${problema.seasonYear}"/></td>
        </tr>
        <tr>
            <th>Casos de Prueba</th>
            <td><c:out value="${problema.casos_prueba}" escapeXml="false"/></td>
        </tr>
        <tr>
            <th>Salida Esperada</th>
            <td><c:out value="${problema.salida_esperada}" escapeXml="false"/></td>
        </tr>
      
    </table>
    
     <h2>Aclaraciones</h2>
     
           
     <c:forEach items="${problema.aclaraciones}" var="aclaracion">
	<table class="table table-striped">
    <tr>
    <td><img src="/<c:out value="${aclaracion.tutor.imagen}"/>" id="Imagen" width="50" style="border-radius:100%"/>&nbsp;
    <a href="/tutores/${aclaracion.tutor.id}">
    <c:out value="${aclaracion.tutor.nombre} ${aclaracion.tutor.apellidos}"/>
    </a></td>
    </tr>
  
    	<tr>
    	<td><c:out value="${aclaracion.texto}"/><td></tr>
    	
    	</table>
	</c:forEach>


	<sec:authorize access="hasAuthority('tutor')">
		<div>
			<form:form action="/aclaraciones/new" modelAttribute="aclaracion" class="form-horizontal" id="add-owner-form">
				<petclinic:textArea label="Aclaracion" name="texto" rows="6" />
				<input type="hidden" name="idProblema" value="${problema.id}" />
				<button class="btn btn-default" type="submit">AÃ±adir AclaraciÃ³n</button>
			</form:form>
		</div>
	</sec:authorize>

	<h2>Realizar envío</h2>


	<sec:authorize access="hasAuthority('alumno')">

		<form:form action='/envios/send/${problema.id}'
			enctype="multipart/form-data">

			<div class="form-group has-feedback">


				<table>

					<tr>
						<td><input type="file" name="archivo" /></td>
					</tr>


				</table>
			</div>
			<button class="btn btn-default" type="submit">Enviar</button>

		</form:form>
	</sec:authorize>

	<sec:authorize access="!hasAuthority('alumno')">
		<div class="form-group has-feedback">
			<table>

				<tr>
					<td>Sólo los alumnos pueden realizar envíos. Inicia sesión
						para realizar un envío.</td>
				</tr>


			</table>
		</div>

	</sec:authorize>
	<br>
	<sec:authorize access="hasAuthority('alumno')">
		<div class="form-group has-feedback">
			<table>

				
				<tr>
					<form:form modelAttribute="preguntaTutor" class="form-horizontal" id="add-problema-form" action="/preguntatutor/new" >
						<petclinic:textArea label="Preguntanos lo que quieras" name="pregunta" rows="6" />
						<input type="hidden" name="idProblema" value="${problema.id}"/>
						<button class="btn btn-default" type="submit">Enviar</button>
					</form:form>
				</tr>
				


			</table>
		</div>

	</sec:authorize>


	<h2>Últimos envíos</h2>
	<table class="table table-striped" id="envios">

		<tr>
			<th>Envío</th>
			<th>Alumno</th>
			<th>Fecha y hora</th>
			<th>Veredicto</th>
		</tr>
		
	</table>
	
	<div style="text-align: center;" id="paginas">
    	<img id="izquierda" width="11px" style="cursor:pointer;"></img> <span id="numero"></span> <img id="derecha" width="11px" style="cursor:pointer;"></img>
    </div>

	
	<h2>Estadísticas</h2>
	<div id="graficaDonut" style="height: 250px;"></div>
	<script>
	var morris1 = new Morris.Donut({
		  element: 'graficaDonut',
		  data: [
		    <c:forEach items="${resoluciones}" var="resolucion">
		    	{label: '<c:out value="${resolucion.key}"></c:out>', value: <c:out value="${resolucion.value}"></c:out>},
		    </c:forEach>
		  ],
		  colors: ['#891637','#be9d56','#b1b1b1','#603c63','#3c635b'],
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
	

	<h2>Este problema ha sido resuelto por <c:out value="${conseguidos}"></c:out> alumnos</h2>
	
		    <script>
    
    
	///// paginacion envios
	
    function enviospaginable(page){
    	
    	var enviospag = paginate(page,'/api/envios/byproblema/'+${problema.id}+'?page=');
    	var nextenviospag = paginate(page+1,'/api/envios/byproblema/'+${problema.id}+'?page=');
    	if(!(page==1 && nextenviospag.length==0))
	    	$("#numero").text(page);
	    if(page>1){
	    	$("#izquierda").attr("src","/resources/images/leftrow.svg");
	    }
	    else
	    	{
	    	$("#izquierda").attr("src","");
	    	}
	    
	    if(nextenviospag.length!=0){
	     $("#derecha").attr("src","/resources/images/rightrow.svg");
	    }
	    else{
	    	$("#derecha").attr("src","");
	    }
	    
	    
	    $("#envios").html("");
	    
	    $("#envios").append("<tbody>");
	    $("#envios").append("<tr><th>EnvÃ­o</th><th>Alumno</th><th>Fecha y hora</th><th>Veredicto</th></tr>");
	    
	    for(var i = 0; i < enviospag.length; i++){
	    	
	    $("#envios").append('<tr><td><a href="/envios/'+enviospag[i]["id"]+'">'+enviospag[i]["id"]+'</a></td><td><a href="/alumnos/'+enviospag[i]["alumno"]["id"]+'">'+ enviospag[i]["alumno"]["nombre"]+'&nbsp;'+enviospag[i]["alumno"]["apellidos"]+'</a></td><td>'+enviospag[i]["fechaFormat"]+'</td><td>'+enviospag[i]["resolucion"]+'</td></tr>');
	    	
	    }
	    console.log(enviospag);
	    $("#envios").append("</tbody>");
	    
    }
    
    var page = 1;
    
    enviospaginable(page);
    
    document.getElementById("izquierda").onclick = function(){
    	page--;
    	enviospaginable(page);
    }
    document.getElementById("derecha").onclick = function(){
    	page++;
    	enviospaginable(page);
    };
    
    
   //////////////////////////////////
    
	</script>
</petclinic:layout>
