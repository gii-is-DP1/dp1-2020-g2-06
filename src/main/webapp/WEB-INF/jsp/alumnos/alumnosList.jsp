<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<petclinic:layout pageName="alumnos">
    <h2>Alumnos</h2>
    <div>
    <table class="table table-striped" id="alumnoss">
    	
    </table>
    </div>
    <div style="text-align: center;" id="paginas">
    	<img id="izquierda-art" width="11px" style="cursor:pointer;"></img> <span id="numero-art"></span> <img id="derecha-art" width="11px" style="cursor:pointer;"></img>
    </div>
 
 <script>

    //////////////////////////////////
    
	///// paginacion alumnos 
	
    function alumnospaginable(page){
    	
    	scroll(0,0);
    	var alumnospag = paginate(page,'/api/alumnospage'+'?page=');
    	var nextalumnospag = paginate(page+1,'/api/alumnospage'+'?page=');
	    $("#numero-art").text(page);
	    if(page>1){
	    	$("#izquierda-art").attr("src","/resources/images/leftrow.svg");
	    }
	    else
	    	{
	    	$("#izquierda-art").attr("src","");
	    	}
	    
	    if(nextalumnospag.length!=0){
	     $("#derecha-art").attr("src","/resources/images/rightrow.svg");
	    }
	    else{
	    	$("#derecha-art").attr("src","");
	    }
	    
	    
	    $("#alumnoss").html("");
	    
	    $("#alumnoss").append("<tbody>");

	    for(var i = 0; i < alumnospag.length; i++){
	    	
	    	$("#alumnoss").append("<tr> <td> <img src="+alumnospag[i]['imagen']+" ' width='100px' style='border-radius:100%'/>"+" "+"<a href='/alumnos/"+alumnospag[i]['id']+"'>"+alumnospag[i]['nombre']+" - "+alumnospag[i]['apellidos']+"</a> </td> </tr>");
	  
	    }
	   
	    $("#alumnoss").append("</tbody>");
	    
    }
    
    var alumnospage = 1;
    
    alumnospaginable(alumnospage);
    
    document.getElementById("izquierda-art").onclick = function(){
    	alumnospage--;
    	alumnospaginable(alumnospage);
    }
    document.getElementById("derecha-art").onclick = function(){
    	alumnospage++;
    	alumnospaginable(alumnospage);
    };
    
    
   //////////////////////////////////
   
   

   	
    </script>       	
       	

</petclinic:layout>