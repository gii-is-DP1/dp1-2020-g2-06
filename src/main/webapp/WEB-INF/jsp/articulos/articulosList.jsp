<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<petclinic:layout pageName="articulos">
    <h2>Artículos</h2>
   
    <div>
    <table class="table table-striped" id="articuloss">
    	
    </table>
    </div>
    <div style="text-align: center;" id="paginas">
    	<img id="izquierda-art" width="11px"></img> <span id="numero-art"></span> <img id="derecha-art" width="11px"></img>
    </div>
        
        <sec:authorize access="hasAuthority('tutor')"> 
       		<a class="btn btn-default" href='<spring:url value="/articulos/new" htmlEscape="true"/>'>Crear Articulo</a>
       	</sec:authorize>
       	
	 <script>
   
    
   //////////////////////////////////
    
	///// paginacion articulos 
	
    function articulospaginable(page){
    	
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
	    	
	    	$("#articuloss").append("<tr> <td> <a href='/articulos/"+articulospag[i]['id']+"'>"+ articulospag[i]['name']+" - "+articulospag[i]['fechaPublicacion']+"</a> </td> </tr> <tr><td>"+ articulospag[i]['texto'] +"</td></tr>");
	    	
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
