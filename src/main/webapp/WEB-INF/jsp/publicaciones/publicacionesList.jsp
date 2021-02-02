<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="envios">

    <h2>Mensajes</h2>
          
      <c:forEach items="${publicaciones}" var="publicacion">
	<table class="table table-striped">
    <tr>
    <td><img src="/<c:out value="${publicacion.alumno.imagen}"/>" id="Imagen" width="50" style="border-radius:100%"/>&nbsp;
    <a href="/alumnos/${publicacion.alumno.id}">
    <c:out value="${publicacion.alumno.nombre} ${publicacion.alumno.apellidos}"/>
    </a></td>
    </tr>
  
    	<tr>
    	<td><c:out value="${publicacion.texto}"/><td></tr>
    	
    	</table>
	</c:forEach>
	
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
    
    
    
    
    <table class="table table-striped" id="ListaPublicaciones">
    	
    </table>
    
    <div style="text-align: center;" id="paginas">
    	<img id="izquierda-publicacion" width="11px"></img> <span id="numero-publicacion"></span> <img id="derecha-publicacion" width="11px"></img>
    </div>
    
    <script>
//////////////////////////////////
    
	///// paginacion publicaciones 
	
    function creadorespaginable(page){
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

	    if(nextcreadorpag.length!=0){
	     $("#derecha-publicacion").attr("src","/resources/images/rightrow.svg");
	    }
	    else{
	    	$("#derecha-publicacion").attr("src","");
	    }

	    
	    $("#ListaPublicaciones").html("");
	    
	    $("#ListaPublicaciones").append("<tbody>");
	    for(var i = 0; i < creadorespag.length; i++){
	    	
	    	$("#ListaPublicaciones").append("<tr> <td> <a href='/foro/"+publicacionespag[i]['id']+"'>"+ publicacionespag[i]['texto'] + "  " + publicacionespag[i]['fecha'] +"</a> </td> </tr>");

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
