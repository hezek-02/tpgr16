<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<link rel="stylesheet" href="resources/css/altaOfertaLaboral.css">

<body>
	<jsp:include page="/WEB-INF/templates/header.jsp" />

	<!-- Contenedor -->
	<div class="main-container">
		<jsp:include page="../templates/sidebar.jsp"></jsp:include>
	<div class="generic-section "> 
			<div class="main-container-oferta col-12">
				<div class="form-container col-12">
					<% 
   					if (request.getAttribute("error") != null){%>	
						<jsp:include page="../templates/errorMessage.jsp"></jsp:include>
					<%}else if (request.getAttribute("success") != null){ %>
						<jsp:include page="../templates/successMessage.jsp"></jsp:include>
					<%}

					%>
					<div class="alert alert-danger" role="alert" id="errorMensaje" style="display:none;">
					  	<p>¡ATENCION! Debe seleccionar un tipo de publicación.</p>
					</div>
					
				    <form action="alta-oferta" class="col-10" method="post" id="formularioOferta" onsubmit="validarDatos(event)" enctype="multipart/form-data">
				        <h2>Cree su oferta Laboral</h2>
				        <input type="hidden" name="accion" value="alta">
				        <!-- Tipos Pubs -->
				        <div class="form-group">
				            <label for="seleccionTipoPub">Selecciona un Tipo de publicación</label>
				            <select class="form-control " name="seleccionTipoPub" id="seleccionTipoPub" onchange="redireccionar(this.value)">
				                 <option value="" selected></option> <!-- Opción vacía -->
				                <%
				                String seleccionado = (String) request.getAttribute("tipoPubSelected");
				                DtTipoPub[] colTipoPubs = (DtTipoPub[]) request.getAttribute("tiposPub");
				                for (DtTipoPub tipoPubli : colTipoPubs) {
				                %>
				                <option value="<%=tipoPubli.getNombre()%>" id="<%=tipoPubli.getNombre()%>"
				                <% if (tipoPubli.getNombre().equals(seleccionado)){ %> selected <%} %>>
				                	<%=tipoPubli.getNombre()%>, Costo: <%=tipoPubli.getCosto()%> UYU
				                </option>
				                <%}%>
				            </select>
				        </div>
				
				        <!-- Nombre oferta -->
				        <div class="form-group">
				            <label class="form-label" oninput="verificarNombreEnUso(this.value)" for="nomOferta">Nombre de la oferta laboral:</label>
				            <input type="text" name="nombre" class="form-control " required />
				        </div>
				
				        <!-- Descripcion -->
				        <div class="form-group">
				            <label class="form-label" for="descripcionOferta">Descripción:</label>
				            <textarea name="descripcion" class="form-control" style="height: 200px; overflow-y: scroll;" required >
				           </textarea>
				        </div>
				
				        <!-- Horario -->
						<div class="form-row d-flex align-items-center ">
							<label for="horaDesde" class="mr-2">Horario desde:</label>
						    <div class="form-group ">
						        <input required type="time" name="horaDesde" class="col ">
						    </div><br>
						    <label for="horaHasta" class="ml-5 mr-2">hasta:</label>
						    <div class="form-group ">
						        <input required type="time" name="horaHasta" class="col">
						    </div>
						</div>
						
						<!-- Imagen -->
				        <div class="form-group">
				            <label for="imagen">Selecciona una imagen(opcional):</label>
				            <input type="file" name="imagen" accept="image/*" class="form-control" />
				        </div>
				
				        <!-- Ciudad -->
				        <div class="form-group">
				            <label class="form-label" for="ciudad">Ciudad:</label>
				            <input type="text" name="ciudad" class="form-control" required />
				        </div>
				
				        <!-- Departamento -->
				        <div class="form-group">
				            <label class="form-label" for="departamento">Departamento:</label>
				            <input type="text" name="departamento" class="form-control" required />
				        </div>
				
				        <!-- Remuneracion -->
				        <div class="form-group">
				            <label class="form-label" for="remuneracionForm">Remuneración (UYU):</label>
				            <small>(Solo se permiten números y hasta dos decimales.)</small>
				            <input type="number"  name="remuneracion" class="form-control" step="1" min="1" max="10000000"  required/>
				        </div>
											
						<!-- Método de Pago -->
						<div class="form-check">
						    <input class="form-check-input" type="radio" name="metodoPago" id="pagoGeneral" onclick="seleccionPago()" value="pagoGeneral" checked>
						    <label class="form-check-label" for="pagoGeneral">Pago General</label>
						</div>
						
						<% 
						DtPaquetePub[] colPaqs = (DtPaquetePub[]) request.getAttribute("paquetes");
						if (colPaqs != null && colPaqs.length>0){ %>
						<div class="form-check">
						    <input class="form-check-input" type="radio" name="metodoPago" id="pagoPaquete" onclick="seleccionPago()" value="pagoPaquete">
						    <label class="form-check-label" for="pagoPaquete">Pago con Paquete</label>
						</div>
						
						<!-- Paquetes -->
						
						<div class="form-group">
						    <label for="seleccionPaquete">Selecciona un Paquete</label>
						    <select class="form-control" name="Paquete" id="seleccionPaquete" disabled>
						        <% 
						        for(DtPaquetePub paq: colPaqs) { %>
						            <option value="<%=paq.getNombre() %>"><%=paq.getNombre()%>, descuento: <%=paq.getDescuento()%> % </option>
						        <% } %>
						    </select>
						</div>
						<%}else{%>
						    <label class="form-check-label text-warning" for="pagoPaquete">No posee paquetes disponibles</label>
							<br>
						<%} %>							
						
				        <!-- Keywords -->
				        <label class="mt-2 seleccionMultipleKeyWords">Selecciona la/s keywords:</label>
				        <div class="form-outline">
				            <select name="seleccionKeyWords" multiple class="form-control">
				                <% 
				                	DtKeyWord[] colKeys = (DtKeyWord[]) request.getAttribute("keys");
				                	for(DtKeyWord key: colKeys) { %>
				                    <option value="<%=key.getNombreKey()%>"><%=key.getNombreKey()%></option>
				                <% } %>
				            </select>
				        </div>
				
				        <!-- Botones -->
						<!-- Button trigger modal -->

						<div class="btn-section d-flex justify-content-center m-2 mt-3">
					        <button type="button" onClick="validarDatos(event)" class="btn btn-primary" data-toggle="modal" data-target="#staticBackdrop">
							  Dar de Alta
							</button>
				            <a class="btn btn-secondary cancel-button" href="/ServidorWeb/home" type="button">Cancelar</a>
				        </div>
						<!-- Modal -->
						<div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
						  <div class="modal-dialog">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="staticBackdropLabel">Confirme el alta</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          <span aria-hidden="true">&times;</span>
						        </button>
						      </div>
						      <div class="modal-body">
						        ¿Está seguro que desea dar el alta de la oferta laboral?, su oferta será evalúada en menos de 24hrs.
						      </div>
						      <div class="modal-footer">
								<div class="btn-section d-flex justify-content-between mt-3">
						            <button class="btn btn-primary confirm-button" type="submit">Confirmar</button>
						            <button class="btn btn-secondary cancel-button" data-dismiss="modal" type="button">Cancelar</button>
						        </div>
						      </div>
						    </div>
						  </div>
						</div>

				    </form>
				</div>

			</div>	
		</div>
	</div>
<jsp:include page="../templates/footer.jsp"></jsp:include>
</body>
</html>
