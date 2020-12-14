<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="competiciones">
    <h2>
        <c:if test="${competiciones['new']}">New </c:if> Competicion
    </h2>
    <form:form modelAttribute="competicion" class="form-horizontal" id="add-competicion-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Descripcion" name="descripcion"/>
            <petclinic:inputField label="Imagen" name="imagen"/>
            <petclinic:inputField label="Fecha Inicio" name="fecha_inicio"/>
            <petclinic:inputField label="Fecha Fin" name="fecha_fin"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${competicion['new']}">
                        <button class="btn btn-default" type="submit">Añadir Competicion</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Competicion</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 