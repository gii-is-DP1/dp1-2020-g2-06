<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<petclinic:layout pageName="creadores">
    <h2>Creadores</h2>

    
    <sec:authorize access="hasAuthority('administrador')">
    	<a class="btn btn-default" href='<spring:url value="/creadores/new" htmlEscape="true"/>'>Crear Creador</a>
    </sec:authorize>
    
    
    <table class="table table-striped" id="creadoress">
    	
    </table>
    
    <div style="text-align: center;" id="paginas">
    	<img id="izquierda-creador" width="11px"></img> <span id="numero-creador"></span> <img id="derecha-creador" width="11px"></img>
    </div>
    
    <script>
    
   //////////////////////////////////
    
	///// paginacion creadores 
	
    function creadorespaginable(page){
	   
    	scroll(0,0);
    	var creadorespag = paginate(page,'/api/PageableCreadores?page=');
    	var nextcreadorpag = paginate(page+1,'/api/PageableCreadores?page=');

	    $("#numero-creador").text(page);
	    if(page>1){
	    	$("#izquierda-creador").attr("src","/resources/images/leftrow.svg");
	    }
	    else
	    	{
	    	$("#izquierda-creador").attr("src","");
	    	}

	    if(nextcreadorpag.length!=0){
	     $("#derecha-creador").attr("src","/resources/images/rightrow.svg");
	    }
	    else{
	    	$("#derecha-creador").attr("src","");
	    }

	    
	    $("#creadoress").html("");
	    
	    $("#creadoress").append("<tbody>");
	    for(var i = 0; i < creadorespag.length; i++){
	    	
	    	$("#creadoress").append("<tr> <td> <a href='/creadores/"+creadorespag[i]['id']+"'>"+ creadorespag[i]['nombre'] + "  " + creadorespag[i]['apellidos'] +"</a> </td> </tr>");

	    }
	   
	    $("#creadoress").append("</tbody>");
	    
    }
    
    var creadorespage = 1;
    
    creadorespaginable(creadorespage);
    
    document.getElementById("izquierda-creador").onclick = function(){
    	creadorespage--;
    	creadorespaginable(creadorespage);
    }
    document.getElementById("derecha-creador").onclick = function(){
    	creadorespage++;
    	creadorespaginable(creadorespage);
    };
    
    
   //////////////////////////////////
   </script>
   
</petclinic:layout>