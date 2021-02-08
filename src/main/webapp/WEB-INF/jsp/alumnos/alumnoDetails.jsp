<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="alumno">

		<h2>
    	<img src="/<c:out value="${alumno.imagen}"/>" id="Imagen" width="200" style="border-radius:100%"/>&nbsp;
		<c:out value="${alumno.nombre}"/>&nbsp;<c:out value="${alumno.apellidos}"/>
		</h2>
	
		
	
	 <table class="table table-striped">
	 <tr>
            <th>Email</th>
            <td><c:out value="${alumno.email}"/></td>
        </tr>
    	<tr>
            <th>Puntuación Temporada</th>
            <td><c:out value="${puntostemporada}"/></td>
        </tr>
        <tr>
            <th>Puntuación Anual</th>
            <td><c:out value="${puntosanuales}"/></td>
        </tr>
        <tr>
            <th>Puntuación Total</th>
            <td><c:out value="${puntostotales}"/></td>
        </tr>
       
    </table>

	<c:if test="${me==true}">
		<spring:url value="/alumnos/${alumno.id}/edit" var="editUrl">
		</spring:url>
		<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar
			Alumno</a>
	</c:if>
	
	<br>
	<br>
	
	<h2>Logros</h2>
    <table class="table table-striped">
    <c:forEach items="${logros}" var="logro">
  		<tr>
			<th><img alt="" src="/<c:out value="${logro.imagen}"/>" id="Imagen" width="50" style="border-radius:100%"/>&nbsp;&nbsp;<c:out value="${logro.nombre}"/></th>
  		</tr>
  	</c:forEach>
    </table>
    
    <br>
	<br>
   
	<c:if test="${me==true}">
    <h2> Problemas resueltos (estando vigentes)</h2>
    <table class="table table-striped">
  
    	<c:forEach items="${problemasresueltos}" var="problem">
    	<tr>
    		<td>
    		<a href="/problemas/${problem.id}">
    		<c:out value="${problem.name}"/>
    		</a>
    		</td>
    		
    	</tr>
   		</c:forEach>
    </table>
    
    <h2> Preguntas a tutores No Respondidas</h2>
    <table class="table table-striped">	
    <c:forEach items="${preguntasTutorNoRespondidas}" var="pregunta">
    	<tr>
    		<td>
    		<a href="/problemas/${pregunta.problema.id}"> Problema : <c:out value="${pregunta.problema.name}"/> </a>
    		<br>
    		<c:out value="Pregunta : ${pregunta.pregunta}"/>
    		</td>
    		
    		
    	</tr>
   		</c:forEach>
    </table>
    
    <h2> Preguntas a tutores Respondidas</h2>
    <table class="table table-striped">	
    <c:forEach items="${preguntasTutorRespondidas}" var="pregunta">
    	<tr>
    		<td>
    		<a href="/problemas/${pregunta.problema.id}"> Problema : <c:out value="${pregunta.problema.name}"/> </a>
    		<br>
    		<c:out value="Pregunta : ${pregunta.pregunta}"/>
    		<br>
    		<c:out value="Tutor que responde : ${pregunta.tutor.email}"/>
    		<br>
    		<c:out value="Respuesta : ${pregunta.respuesta}"/>
    		</td>
    		
    		
    	</tr>
   		</c:forEach>
    </table>
    </c:if>
    
    <table class="table table-striped" id="envios">

		<tr>
			<th>Envío</th>
			<th>Problema</th>
			<th>Fecha y hora</th>
			<th>Veredicto</th>
		</tr>
		
	</table>
    
    <div style="text-align: center;" id="paginas">
    	<img id="izquierda" width="11px" style="cursor:pointer;"></img> <span id="numero"></span> <img id="derecha" width="11px" style="cursor:pointer;"></img>
    </div>
    
    <script>
    
    
	///// paginacion envios
	
    function enviospaginable(page){
    	
    	var enviospag = paginate(page,'/api/envios/byalumno/'+${alumno.id}+'?page=');
    	var nextenviospag = paginate(page+1,'/api/envios/byalumno/'+${alumno.id}+'?page=');
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
	    $("#envios").append("<tr><th>Envío</th><th>Problema</th><th>Fecha y hora</th><th>Veredicto</th></tr>");
	    
	    for(var i = 0; i < enviospag.length; i++){
	    	
	    $("#envios").append('<tr><td><a href="/envios/'+enviospag[i]["id"]+'">'+enviospag[i]["id"]+'</a></td><td><a href="/envios/'+enviospag[i]["problema"]["id"]+'">'+enviospag[i]["problema"]["name"]+'</a></td><td>'+enviospag[i]["fechaFormat"]+'</td><td>'+enviospag[i]["resolucion"]+'</td></tr>');
	    	
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
