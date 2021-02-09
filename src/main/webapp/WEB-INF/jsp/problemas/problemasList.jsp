<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 

<petclinic:layout pageName="problemas">
    <h2>Problemas temporada ${temporada}&nbsp;${temporadaYear}</h2>

	<c:forEach items="${problemasVigentes}" var="problemaVigente">
		<table id="problemasTable" class="table table-striped">
			<tr>
				<th><a href="/problemas/${problemaVigente.id}"> <c:out value="${problemaVigente.name}" /> </a></th>
				<sec:authorize access="hasAuthority('creador')">
					<th><a href="/problemas/${problemaVigente.id}/edit"> <span
						class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				</a></th>
				</sec:authorize>
			</tr>
		</table>
	</c:forEach>
	
	<h2>Problemas no vigentes</h2>
	<p><b>Prueba a resolver algunos de los problemas retirados. No puntuan, pero aprenderás solucionándolos. En la sección de <a href="/articulos"/>artículos</a> encontrarás algunas soluciones y consejos para resolverlos.</b></p>
	<br>
		<table id="problemasnovigentes" class="table table-striped">
			
		</table>
		<div style="text-align: center;" id="paginas">
    	<img id="izquierda" width="11px" style="cursor:pointer;"></img> <span id="numero"></span> <img id="derecha" width="11px" style="cursor:pointer;"></img>
    </div>
	
	<sec:authorize access="hasAuthority('creador')">
			<a class="btn btn-default" href='<spring:url value="/problemas/new" htmlEscape="true"/>'>Añadir Problema</a>
	</sec:authorize>
	
	    <script>
    
    
	///// paginacion problemas no vigentes
	
    function problemasnovigentespaginable(page){
    	
    	var problemaspag = paginate(page,'/api/problemasnovigentes/?page=');
    	var nextproblemaspag = paginate(page+1,'/api/problemasnovigentes/?page=');
    	if(!(page==1 && nextproblemaspag.length==0))
	    	$("#numero").text(page);
	    if(page>1){
	    	$("#izquierda").attr("src","/resources/images/leftrow.svg");
	    }
	    else
	    	{
	    	$("#izquierda").attr("src","");
	    	}
	    
	    if(nextproblemaspag.length!=0){
	     $("#derecha").attr("src","/resources/images/rightrow.svg");
	    }
	    else{
	    	$("#derecha").attr("src","");
	    }
	    
	    
	    $("#problemasnovigentes").html("");
	    
	    $("#problemasnovigentes").append("<tbody>");
	    for(var i = 0; i < problemaspag.length; i++){
	    	
	    	$("#problemasnovigentes").append("<tr> <td> <a href='/problemas/"+problemaspag[i]['id']+"'>"+ problemaspag[i]['name']+"</a> </td> </tr>");
	    	
	    }
	    console.log(problemaspag);
	    $("#problemasnovigentes").append("</tbody>");
	    
    }
    
    var page = 1;
    
    problemasnovigentespaginable(page);
    
    document.getElementById("izquierda").onclick = function(){
    	page--;
    	problemasnovigentespaginable(page);
    }
    document.getElementById("derecha").onclick = function(){
    	page++;
    	problemasnovigentespaginable(page);
    };
    
    
   //////////////////////////////////
    
	</script>
</petclinic:layout>
