<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<petclinic:layout pageName="tutores">
    <h2>Tutores</h2>
<p><b>Nuestros tutores elaboran artículos, noticias, responden a tus dudas y... ¡te guían en tu camino hacia la excelencia!</b> </p>
   <br>
    <div>
    	<table class="table table-striped" id="tutores">
    	
    	</table>
    </div>
    <div style="text-align: center;" id="paginas">
    	<img id="izquierda-tut" width="11px" style="cursor:pointer;"></img> <span id="numero-tut"></span> <img id="derecha-tut" width="11px" style="cursor:pointer;"></img>
    </div>
    
    <sec:authorize access="hasAuthority('administrador')">
    	<a class="btn btn-default" href='<spring:url value="/tutores/new" htmlEscape="true"/>'>Crear Tutor</a>
    </sec:authorize>
    
    <script>
    
function tutorespageable(page){
		scroll(0,0);
    	var tutorespag = paginate(page,'/api/tutores/?page=');
    	var nexttutorespag = paginate(page+1,'/api/tutores/?page=');
    	if(!(page==1 && nexttutorespag.length==0))
	    	$("#numero-tut").text(page);
    
	    if(page>1){
	    	$("#izquierda-tut").attr("src","/resources/images/leftrow.svg");
	    }
	    else
	    	{
	    	$("#izquierda-tut").attr("src","");
	    	}
	    
	    if(nexttutorespag.length!=0){
	     $("#derecha-tut").attr("src","/resources/images/rightrow.svg");
	    }
	    else{
	    	$("#derecha-tut").attr("src","");
	    }
	    
	    
	    $("#tutores").html("");
	    
	    $("#tutores").append("<tbody>");
	    for(var i = 0; i < tutorespag.length; i++){
	    	
	    	$("#tutores").append("<tr> <td> <img src="+tutorespag[i]['imagen']+" ' width='80' style='border-radius:100%'/>"+" "+"<a href='/tutores/"+tutorespag[i]['id']+"'>"+" "+ tutorespag[i]['nombre']+"  "+tutorespag[i]['apellidos']+"</a> </td> </tr>");
	    	
	    }
	    console.log(tutorespag);
	    $("#tutores").append("</tbody>");
	    
    }
    
    var tutorespage = 1;
    
    tutorespageable(tutorespage);
    
    document.getElementById("izquierda-tut").onclick = function(){
    	tutorespage--;
    	tutorespageable(tutorespage);
    }
    document.getElementById("derecha-tut").onclick = function(){
    	tutorespage++;
    	tutorespageable(tutorespage);
    };
    </script>
    
</petclinic:layout>