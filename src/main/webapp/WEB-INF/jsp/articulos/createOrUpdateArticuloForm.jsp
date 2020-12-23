<%@ page session="false" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="noticias">
    <h2>
        <c:if test="${articulo['new']}">New </c:if> Articulo
    </h2>
    <form:form modelAttribute="articulo" class="form-horizontal" id="add-owner-form" enctype="multipart/form-data">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Título" name="name" />
			<petclinic:inputField label="Texto" name="texto" />
			<petclinic:inputField label="fechaPublicacion"
				name="fechaPublicacion" />
			<table>
				<form:form enctype="multipart/form-data">
					<tr>
						<td>Image to upload:</td>
						<td><input type="file" name="image" /></td>
					</tr>
				</form:form>
			</table>

			<br>
			<br>	
			
			<div class="formGroup">
				<div class="col-sm-10">
					<label>Autores:</label>
					<form:checkboxes items="${autores}" path="autores"
						delimiter="&nbsp;&nbsp;&nbsp;" itemLabel="nombre"
						style="border:10px;" />
				</div>
			</div>
		</div>
		<div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${articulo['new']}">
                        <button class="btn btn-default" type="submit">Añadir Articulo</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Articulo</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout> 