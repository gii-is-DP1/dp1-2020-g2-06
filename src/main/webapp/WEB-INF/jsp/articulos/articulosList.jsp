<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<petclinic:layout pageName="articulos">
    <h2>Artículos</h2>
   <p><b>Aquí tienes todos los artículos que nuestros <a href="/tutores/">tutores</a> escriben para que aprendas cosas nuevas y saques todo el partido a los problemas que te propongas ;)</b> </p>
   <br>
    <div>
    <table class="table table-striped" id="articuloss">
    	
    </table>
    </div>
    <div style="text-align: center;" id="paginas">
    	<img id="izquierda-art" width="11px" style="cursor:pointer;"></img> <span id="numero-art"></span> <img id="derecha-art" width="11px" style="cursor:pointer;"></img>
    </div>
        
        <sec:authorize access="hasAuthority('tutor')"> 
       		<a class="btn btn-default" href='<spring:url value="/articulos/new" htmlEscape="true"/>'>Crear Articulo</a>
       	</sec:authorize>
       	
	 <script>
   
    
   //////////////////////////////////
    
	///// paginacion articulos 
	
    function articulospaginable(page){
    	scroll(0,0);
    	
    	var articulospag = paginate(page,'/api/articulospage'+'?page=');
    	var nextarticulospag = paginate(page+1,'/api/articulospage'+'?page=');
    
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
	    	
	    	$("#articuloss").append("<tr> <td> <h3><a href='/articulos/"+articulospag[i]['id']+"'>"+ articulospag[i]['name']+" </a> </h3>"+articulospag[i]['fechaPublicacionFormat']+"</td> </tr> <tr><td>"+ articulospag[i]['texto'].substring(0,500) +"... <a href='/articulos/"+articulospag[i]['id']+"'> Seguir leyendo </a> </td></tr>");
	    	
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
