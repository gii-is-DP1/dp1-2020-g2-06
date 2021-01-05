<%@ page session="false" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="competiciones">

	<jsp:attribute name="customScript">
        <script>
			$(function() {
				$("#fecha_inicio").datepicker({
					dateFormat : 'yy/mm/dd'
				});
				$("#fecha_fin").datepicker({
					dateFormat : 'yy/mm/dd'
				});
			});
		</script>
    </jsp:attribute>
	<jsp:body>
    <h2>
        <c:if test="${competiciones['new']}">New </c:if> Competicion
    </h2>
    <form:form modelAttribute="competicion" class="form-horizontal" id="add-competicion-form" enctype="multipart/form-data">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:textArea label="Descripcion" name="descripcion" rows="12"/>
            <petclinic:inputField label="Fecha de inicio" name="fecha_inicio" />
            <petclinic:inputField type="time" label="Hora de inicio" name="hora_inicio" />
            <petclinic:inputField label="Fecha de fin" name="fecha_fin" />
            <petclinic:inputField type="time" label="Hora de fin" name="hora_fin" />
            <table>
            <form:form enctype="multipart/form-data">
             <tr><td>Image to upload:</td><td><input type="file" name="image" /></td></tr>
            </form:form>
			</table>
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
    </jsp:body>
</petclinic:layout> 