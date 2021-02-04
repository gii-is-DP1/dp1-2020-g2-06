<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'INICIO'}" url="/"
					title="INICIO">
				<!--
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
				-->
					<span>INICIO</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'PROBLEMAS'}" url="/problemas"
					title="PROBLEMAS">
				<!--
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
				-->
					<span>PROBLEMAS</span>
				</petclinic:menuItem>

				

				<petclinic:menuItem active="${name eq 'APRENDIZAJE'}" url="/articulos"
					title="ARTICULOS">
				<!--
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
				-->
					<span>ARTICULOS</span>
				</petclinic:menuItem>
				
				<petclinic:menuItem active="${name eq 'COMUNIDAD'}" url="/diseases"
					title="COMUNIDAD" dropdown="${true}">																			
						<ul class="dropdown-menu">
							<li>								
									<div class="row">
										<div class="text-center">																					
												<a href="<c:url value="/foro" />">FORO</a>
										</div>																					
									</div>						
							</li>
							<li class="divider"></li>
							<li>								
									<div class="row">
										<div class="text-center">																					
												<a href="<c:url value="/alumnos" />">ALUMNOS</a>
										</div>																					
									</div>						
							</li>		
							<li class="divider"></li>
							<li>								
									<div class="row">
										<div class="text-center">																					
												<a href="<c:url value="/tutores" />">TUTORES</a>
										</div>																					
									</div>						
							</li>			
							<li class="divider"></li>
							<li>								
									<div class="row">
										<div class="text-center">																					
												<a href="<c:url value="/creadores" />">CREADORES</a>
										</div>																					
									</div>						
							</li>															
						</ul>						
				</petclinic:menuItem>	
				

			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/alumnos/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li class="divider"></li>
 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="<c:url value="/perfil" />" class="btn btn-primary btn-block">Mi perfil</a>
												<a href="<c:url value="/logout" />" class="btn btn-danger btn-block">Cerrar Sesión</a>
											</p>
										</div>
									</div>
								</div>
							</li>

						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
