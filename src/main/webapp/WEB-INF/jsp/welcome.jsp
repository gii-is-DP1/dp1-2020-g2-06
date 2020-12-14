<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <div class="row">
    <h2>Project ${title}</h2>
    <h2>Group ${group}</h2>
    <p><ul>
    <c:forEach items="${persons}" var="person">
    <li>${person.firstName}&nbsp;${person.lastName}</li>
    </c:forEach>
    </ul></p>
    </div>
    
    <h2>Ranking Temporada</h2>
    <c:set var="count" value="0" scope="page" />
    <table id="rankingTTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px">Posición</th>
            <th >Nombre</th>
            <th >Apellidos</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${rankingTemporada}" var="alumnot">
            <tr>
                <td>
                    <c:out value="${count}" />
                </td>
                <td>
                    <c:out value="${alumnot.nombre}"/>
                </td>
                <td>
                    <c:out value="${alumnot.apellidos}"/>
                </td>
                
            </tr>
            <c:set var="count" value="${count + 1}" scope="page"/>
        </c:forEach>
        </tbody>
    </table>
    
    <h2>Ranking Anual</h2>
    <c:set var="count2" value="0" scope="page" />
    <table id="rankingATable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px">Posición</th>
            <th >Nombre</th>
            <th >Apellidos</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${rankingAnual}" var="alumnoA">
            <tr>
                <td>
                    <c:out value="${count2}" />
                </td>
                <td>
                    <c:out value="${alumnoA.nombre}"/>
                </td>
                <td>
                    <c:out value="${alumnoA.apellidos}"/>
                </td>
                
            </tr>
            <c:set var="count2" value="${count2 + 1}" scope="page"/>
        </c:forEach>
        </tbody>
    </table>
    
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/pets.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
        <div class="col-md-12 text-center">
    	 <img src="https://www.us.es/sites/default/files/logoPNG_3.png "/>
    	</div>
    </div>
</petclinic:layout>