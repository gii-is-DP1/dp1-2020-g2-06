<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="creadores">
    <h2>
        <c:if test="${creadores['new']}">New </c:if> Creadores
    </h2>
    <form:form modelAttribute="creador" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
        	<petclinic:inputField label="Email" name="email"/>
    		<petclinic:inputField label="Nombre" name="nombre"/>
          	<petclinic:inputField label="Apellidos" name="apellidos"/>
          	<petclinic:inputField label="Pass" name="pass"/>
          	<petclinic:inputField label="Foto" name="foto"/>          
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${creador['new']}">
                        <button class="btn btn-default" type="submit">Añadir Creador</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Creador</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 