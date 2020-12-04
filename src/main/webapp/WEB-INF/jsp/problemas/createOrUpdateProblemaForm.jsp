<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="problemas">
    <h2>
        <c:if test="${problema['new']}">Nuevo </c:if> Problema
    </h2>
    <form:form modelAttribute="problema" class="form-horizontal" id="add-problema-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre del problema" name="name"/>
            <petclinic:inputField label="Descripci�n" name="descripcion"/>
            <petclinic:inputField label="Puntuacion" name="puntuacion"/>
            <petclinic:inputField label="Temporada" name="temporada"/>
            <petclinic:inputField label="Casos_prueba" name="casos_prueba"/>
            <petclinic:inputField label="Salida_esperada" name="salida_esperada"/>
            <petclinic:inputField label="Imagen" name="imagen"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${problema['new']}">
                        <button class="btn btn-default" type="submit">A�adir Problema</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Problema</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 