<%@ page session="false" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<petclinic:layout pageName="tutor">

	<h2>
    <img src="/<c:out value="${tutor.imagen}"/>" id="Imagen" width="200" style="border-radius:100%"/>&nbsp;
	<c:out value="${tutor.nombre}"/>&nbsp;<c:out value="${tutor.apellidos}"/>
	</h2>

	 <table class="table table-striped">
	 <tr>	 
            <th>Email</th>
            <td><c:out value="${tutor.email}"/></td>
        </tr>
    </table>
    
    <c:if test="${me}">
    <spring:url value="/tutores/${tutor.id}/edit" var="editUrl"> </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Tutor</a>
   	</c:if>
   
    <br> <br>
    <br> <br>
   
    <h2> Noticias</h2>
    <div>
    <table class="table table-striped" id="noticias">
    	
    </table>
    </div>
    <div style="text-align: center;" id="paginas-not">
    	<img id="izquierda-not" width="11px"></img> <span id="numero-not"></span> <img id="derecha-not" width="11px"></img>
    </div>
   
    <br> <br>
   
    <h2> Articulos</h2>
    <div>
    <table class="table table-striped" id="articuloss">
    	
    </table>
    </div>
    <div style="text-align: center;" id="paginas">
    	<img id="izquierda-art" width="11px"></img> <span id="numero-art"></span> <img id="derecha-art" width="11px"></img>
    </div>
    
    <c:if test="${me}">
    <div>
    <table class="table table-striped" >
    	<c:forEach items="${preguntasTutor}" var="pregunta">
    	<tr>
    		<td>
   				<c:out value="Alumno : ${pregunta.alumno.email}"/>
    		<br>
    			<a href="/problemas/${pregunta.problema.id}"> Problema : <c:out value="${pregunta.problema.name}"/> </a>
    		<br>
    		<c:out value="Pregunta : ${pregunta.pregunta}"></c:out>
    		<br>
    		<form:form class="form-horizontal" id="add-problema-form" action="/preguntatutor/answer">
    			<input type=hidden name=idTutor value="${tutor.id}"/>
    			<input type=hidden name=preguntaTutor value="${pregunta.id}"/>
    			<textArea  name="respuesta" rows="6"> </textArea>
    			<button class="btn btn-default" type="submit">Responder</button>
    		</form:form>
    		</td>
    	</tr>
   	</c:forEach>
    </table>
    </div>
    
    </c:if>

    
    <script>
    
    
	///// paginacion noticias
	
    function noticiaspaginable(page){
    	
    	var noticiaspag = paginate(page,'http://localhost/api/noticias/bytutor/'+${tutor.id}+'?page=');
    	var nextnoticiaspag = paginate(page+1,'http://localhost/api/noticias/bytutor/'+${tutor.id}+'?page=');
    
	    $("#numero-not").text(page);
	    if(page>1){
	    	$("#izquierda-not").attr("src","/resources/images/leftrow.svg");
	    }
	    else
	    	{
	    	$("#izquierda-not").attr("src","");
	    	}
	    
	    if(nextnoticiaspag.length!=0){
	     $("#derecha-not").attr("src","/resources/images/rightrow.svg");
	    }
	    else{
	    	$("#derecha-not").attr("src","");
	    }
	    
	    
	    $("#noticias").html("");
	    
	    $("#noticias").append("<tbody>");
	    for(var i = 0; i < noticiaspag.length; i++){
	    	
	    	$("#noticias").append("<tr> <td> <a href='/noticias/"+noticiaspag[i]['id']+"'>"+ noticiaspag[i]['name']+" - "+noticiaspag[i]['fechaPublicacion']+"</a> </td> </tr>");
	    	
	    }
	    console.log(noticiaspag);
	    $("#noticias").append("</tbody>");
	    
    }
    
    var noticiaspage = 1;
    
    noticiaspaginable(noticiaspage);
    
    document.getElementById("izquierda-not").onclick = function(){
    	noticiaspage--;
    	noticiaspaginable(noticiaspage);
    }
    document.getElementById("derecha-not").onclick = function(){
    	noticiaspage++;
    	noticiaspaginable(noticiaspage);
    };
    
    
   //////////////////////////////////
    
	///// paginacion articulos 
	
    function articulospaginable(page){
    	
    	var articulospag = paginate(page,'http://localhost/api/articulos/bytutor/'+${tutor.id}+'?page=');
    	var nextarticulospag = paginate(page+1,'http://localhost/api/articulos/bytutor/'+${tutor.id}+'?page=');
    
	    $("#numero-art").text(page);
	    if(page>1){
	    	$("#izquierda-art").attr("src","/resources/images/leftrow.svg");
	    }
	    else
	    	{
	    	$("#izquierda-art").attr("src","");
	    	}
	    
	    if(nextarticulospag.length!=0){
	     $("#derecha-art").attr("src","/resources/images/rightrow.svg");
	    }
	    else{
	    	$("#derecha-art").attr("src","");
	    }
	    
	    
	    $("#articuloss").html("");
	    
	    $("#articuloss").append("<tbody>");
	    for(var i = 0; i < articulospag.length; i++){
	    	
	    	$("#articuloss").append("<tr> <td> <a href='/articulos/"+articulospag[i]['id']+"'>"+ articulospag[i]['name']+" - "+articulospag[i]['fechaPublicacion']+"</a> </td> </tr>");
	    	
	    }
	   
	    $("#articuloss").append("</tbody>");
	    
    }
    
    var articulospage = 1;
    
    articulospaginable(articulospage);
    
    document.getElementById("izquierda-art").onclick = function(){
    	articulospage--;
    	articulospaginable(articulospage);
    }
    document.getElementById("derecha-art").onclick = function(){
    	articulospage++;
    	articulospaginable(articulospage);
    };
    
    
   //////////////////////////////////
   
   

   	
    </script>
</petclinic:layout>