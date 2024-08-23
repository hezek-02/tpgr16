<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.trabajouy.models.TipoUsuario" %>
<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<link rel="stylesheet" href="resources/css/consultas.css">

<body>
	<jsp:include page="/WEB-INF/templates/header.jsp" />

	<!-- Contenedor -->
	<div class="main-container">
		<jsp:include page="../templates/sidebar.jsp"></jsp:include>
		<div class="generic-section "> 
			<%if (request.getAttribute("error") != null){%>
				<jsp:include page="/WEB-INF/templates/errorMessage.jsp"></jsp:include>
			<% } %>
			
			<%
			DtPaquetePub paq = (DtPaquetePub) request.getAttribute("paquete");
			%>
			
			<div class="title-paquete">
				<img src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(paq.getImage()) %>" alt="<%=paq.getNombre()%>">
				<h2> Paquete: <%=paq.getNombre() %></h2>
			</div>

			<form method="POST">
				<input type="hidden" name="accion" value="comprar">
				<div class="tp-info">
					<p><strong>Descripción: </strong> <%=paq.getDescripcion() %></p>
					<p><strong>Costo: </strong> <span class="text-success"><%= paq.getCosto() %> <small>$UYU</small></span></p>
					<p><strong>Descuento: </strong> <%=paq.getDescuento() %>%</p>
					<p><strong>Periodo Validez: </strong> <%=paq.getPeriodoValidez() %> días</p>
					<p><strong>Fecha Alta: </strong> <%=paq.getFechaAltaTexto()%></p>
					
					<p><strong>Tipos de Publicación:</strong></p>
					<ul>
						<%
						@SuppressWarnings("unchecked")
						Collection<DtPaqPub> tiposEnPaquete = (Collection<DtPaqPub>) request.getAttribute("tiposEnPaquete");
						for (DtPaqPub paqPub : tiposEnPaquete){
							String nombreTipo = paqPub.getTipoPub();
						%>
						<li><a href="tipos-publicacion?tipo=<%=nombreTipo%>"><%=nombreTipo%></a> (<%= paqPub.getCantidad() %>)</li>
						<% } %>
					</ul>
				</div>
				
				<%if (request.getSession().getAttribute("tipo_usuario")== TipoUsuario.EMPRESA){ %>
					<div class="boton">
					<%if (request.getAttribute("comprado") != null && (Boolean) request.getAttribute("comprado")){ %>
						<div class="alert alert-danger"> Ya ha comprado este paquete </div>
							  
						<%
						DtCompraPaquete compra = (DtCompraPaquete) request.getAttribute("InfoCompra");
						if (compra != null){
						%>
							<div class="tp-info">
								<p><strong>Fecha de Compra: </strong> <%=compra.getFechaDeCompraTexto() %>
								
								<p><strong>Fecha de Vencimiento:</strong> <%=compra.getFechaDeVencimientoTexto() %>
								
							</div>
						<% } %>
						
					<%}else{ %>
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#staticBackdrop">Comprar</button>
						<% } %>
						<a class="btn btn-secondary cancel-button" href="/ServidorWeb/home" type="button">Cancelar</a>
					</div>
				<% } %>
				
				<div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="staticBackdropLabel">Confirme la compra</h5>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								¿Está seguro que desea comprar este paquete?.
							</div>
							<div class="modal-footer">
								<div class="btn-section d-flex justify-content-between mt-3">
									<button class="btn btn-primary confirm-button mr-1" type="submit">Confirmar</button>
									<button class="btn btn-secondary cancel-button" data-dismiss="modal" type="button">Cancelar</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
<jsp:include page="../templates/footer.jsp"></jsp:include>
</body>
</html>