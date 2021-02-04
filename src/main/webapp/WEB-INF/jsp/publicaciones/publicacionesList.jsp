<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="envios">

    <h2>Mensajes</h2>
          
	
	<sec:authorize access="hasAuthority('alumno')">
		<h2>Escribe tu mensaje</h2>
		<form:form action="/foro/new" modelAttribute="publicacion" class="form-horizontal" id="add-owner-form">
	        <div class="form-group has-feedback">
	            <petclinic:textArea label="Texto" name="texto" rows="12"/>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	                  <button class="btn btn-default" type="submit">Publicar</button>
	            </div>
	        </div>
	    </form:form>
	 </sec:authorize>
    
    
    
    
    <div id="ListaPublicaciones">
    	
    </div>
    
    <div style="text-align: center;" id="paginas">
    	<img id="izquierda-publicacion" width="11px" style="cursor:pointer;"></img> <span id="numero-publicacion"></span> <img id="derecha-publicacion" width="11px" style="cursor:pointer;"></img>
    </div>
    
    <script>
//////////////////////////////////
    
	///// paginacion publicaciones 
	
    function publicacionespaginable(page){
    	var publicacionespag = paginate(page,'/api/PageablePublicaciones?page=');
    	var nextpublicacionpag = paginate(page+1,'/api/PageablePublicaciones?page=');

	    $("#numero-publicacion").text(page);
	    if(page>1){
	    	$("#izquierda-publicacion").attr("src","/resources/images/leftrow.svg");
	    }
	    else
	    	{
	    	$("#izquierda-publicacion").attr("src","");
	    	}

	    if(nextpublicacionpag.length!=0){
	     $("#derecha-publicacion").attr("src","/resources/images/rightrow.svg");
	    }
	    else{
	    	$("#derecha-publicacion").attr("src","");
	    }

	    $("#ListaPublicaciones").html("");

	    $("#ListaPublicaciones").append("<tbody>");
	    for(var i = 0; i < publicacionespag.length; i++){

	    	$("#ListaPublicaciones").append("<table class='table table-striped'> <tr> <td> <img src='/" + publicacionespag[i]['alumno']['imagen'] + "'id='Imagen' width='50' style='border-radius:100%'/>&nbsp;<a href='/alumnos/"+publicacionespag[i]['alumno']['id']+"'>"+ publicacionespag[i]['alumno']['nombre'] + "  " + publicacionespag[i]['alumno']['apellidos'] +"</a> </td> </tr>" + "<tr> <td> <p>" + publicacionespag[i]['texto'] + "</p> </td> </tr> </table>");
	    		  
	    }
	   
	    $("#ListaPublicaciones").append("</tbody>");
	    
    }
    
    var publicacionespage = 1;
    
    publicacionespaginable(publicacionespage);
    
    document.getElementById("izquierda-publicacion").onclick = function(){
    	publicacionespage--;
    	publicacionespaginable(publicacionespage);
    }
    document.getElementById("derecha-publicacion").onclick = function(){
    	publicacionespage++;
    	publicacionespaginable(publicacionespage);
    };
    
    
   //////////////////////////////////
    </script>
    
	
</petclinic:layout>
