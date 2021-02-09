<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  

<petclinic:layout pageName="noticias">
<div id=globalcontainer>



<div id="rankings" style='float: left; width: 40%;padding-right:30px; '>
 <h2>¡Bienvenido a codeUS!</h2>
        <p><b>La plataforma de programación competitiva de la US para mejorar tus habilidades programando.</b></p>
        <p><b>Lee las <a href="/normasWeb">normas de la web</a> para saber las condiciones y funcionamiento para disfrutar de la experiencia al máximo.</b></p>
        <br>
		 <div id="rankingtemporada">
        <h2>Ranking temporada ${temporada}&nbsp;${temporadaYear}</h2>  
        <p><b>¿A qué esperas para resolver unos problemas y ponerte a la cabeza de la clasificación?</b></p>
        <br>
        <table class='table table-striped'>
        
        <tr>
        <th> Puesto </th>
        <th> Nombre </th>
        <th> Puntuación</th>
        </tr>
        
        <c:set var="count" value="1" scope="page" />
        
        <c:forEach items="${ranking_temp}" var="elements">
        <tr>
        <td>
        <c:out value="${count}"/>
        </td>
        <td>
        <a href="/alumnos/${elements.first.id}"> ${elements.first.nombre}&nbsp;${elements.first.apellidos} </a>
        </td>
        
        <td>
        	${elements.second}
        	</td>
        <tr>
        <c:set var="count" value="${count + 1}" scope="page"/>
        </c:forEach>
        </table>
        </div>
		 
        
        <div id="rankinganual">
        <h2>Ranking anual</h2>  
        <table class='table table-striped'>
        
        <tr>
        <th> Puesto </th>
        <th> Nombre </th>
        <th> Puntuación</th>
        </tr>
        <c:set var="count" value="1" scope="page" />
        <c:forEach items="${ranking_anual}" var="elements">
        <tr>
        <td>
        <c:out value="${count}"/>
        </td>
        <td>
        	<a href="/alumnos/${elements.first.id}"> ${elements.first.nombre}&nbsp;${elements.first.apellidos} </a>
        </td>
        
        <td>
        	${elements.second}
        	</td>
        <tr>
        <c:set var="count" value="${count + 1}" scope="page"/>
        </c:forEach>
        </table>
        </div>

	
        
        <div id="rankingtotal">
       
        <h2>Ranking general</h2>   
        <table class='table table-striped'>
        
        <tr>
        <th> Puesto </th>
        <th> Nombre </th>
        <th> Puntuación</th>
        </tr>
        <c:set var="count" value="1" scope="page" />
        <c:forEach items="${ranking_total}" var="elements">
        <tr>
        <td>
        <c:out value="${count}"/>
        </td>
        <td>
        	<a href="/alumnos/${elements.first.id}"> ${elements.first.nombre}&nbsp;${elements.first.apellidos} </a>
        </td>
        
        <td>
        	${elements.second}
        	</td>
        <tr>
        <c:set var="count" value="${count + 1}" scope="page"/>
        </c:forEach>
        </table>
        </div>
        </div>
        
        <div id="noticias_container" style='float: left; width: 60%;'>
    <h2>Noticias</h2>   
        
        <div id="noticias">
		</div>
		<div style="text-align: center;" id="paginas">
    		<img id="izquierda" width="11px" style="cursor:pointer;"></img> <span id="numero"></span> <img id="derecha" width="11px" style="cursor:pointer;"></img>
    	</div>
        
        
        
        <sec:authorize access="hasAuthority('tutor')"> 
			<a class="btn btn-default" href='<spring:url value="/noticias/new" htmlEscape="true"/>'>Añadir Noticia</a>
		</sec:authorize>
		</div>
        
</div>

		<script>
    
    
	///// paginacion problemas no vigentes
	
    function noticiaspaginable(page){
    	scroll(0,0);
    	var noticiaspag = paginate(page,'/api/noticias/?page=');
    	var nextnoticiaspag = paginate(page+1,'/api/noticias/?page=');
    	if(!(page==1 && nextnoticiaspag.length==0))
	    	$("#numero").text(page);
	    if(page>1){
	    	$("#izquierda").attr("src","/resources/images/leftrow.svg");
	    }
	    else
	    	{
	    	$("#izquierda").attr("src","");
	    	}
	    
	    if(nextnoticiaspag.length!=0){
	     $("#derecha").attr("src","/resources/images/rightrow.svg");
	    }
	    else{
	    	$("#derecha").attr("src","");
	    }
	    
	    
	    $("#noticias").html("");
	    
	    $("#noticias").append("<tbody>");
	    for(var i = 0; i < noticiaspag.length; i++){
	    	
	    	$("#noticias").append("<table class='table table-striped'> <tr> <td> <h3> <a href='/noticias/"+noticiaspag[i]['id']+"'>"+ noticiaspag[i]['name'] + 
	    			 "</h3></a> "+noticiaspag[i]['fechaPublicacionFormat'] +"</td> </tr> <tr> <td> <p style='text-align:center'> <img src='/"+noticiaspag[i]['imagen']+"'width='400px'> </p> </td> </tr> <tr><td>"+ noticiaspag[i]['texto'].substring(0,500) +"... <a href='/noticias/"+noticiaspag[i]['id']+"'> Seguir leyendo </a> </td></tr></table>");
	    	
	    }
	    console.log(noticias);
	    $("#noticias").append("</tbody>");
	    
    }
    
    var page = 1;
    
    noticiaspaginable(page);
    
    document.getElementById("izquierda").onclick = function(){
    	page--;
    	noticiaspaginable(page);
    }
    document.getElementById("derecha").onclick = function(){
    	page++;
    	noticiaspaginable(page);
    };
    
    
   //////////////////////////////////
    
	</script>
	
</petclinic:layout> 
