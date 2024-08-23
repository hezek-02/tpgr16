<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<link rel="stylesheet" href="resources/css/altaOfertaLaboral.css">
    <link rel="stylesheet" href="resources/css/consultaOferta.css">
</head>
<body> 
  <jsp:include page="./templates/header.jsp"></jsp:include>
  <!-- Contenedor -->
  <div class="main-container">
    <div class="oferta-section"> 
      <div class="main-container-oferta">       
      
        <div class="form-container col-12">
        <% 
          if (request.getAttribute("error") != null){%> 
        <jsp:include page="./templates/errorMessage.jsp"></jsp:include>
        <%}
          DtOferta oferta = (DtOferta) request.getAttribute("ofertaSeleccionada"); 
          DtEmpresa empresa = (DtEmpresa) request.getAttribute("empresaDeOferta"); 
          %>
                <form method="POST" >
                    <h2>Postularse a Oferta Laboral</h2>
                    <input type="hidden" name="accion" value="postularse">
                    
              <div class="card mb-3" >
                   <div class="row g-0">
                          <div class="col-md-4">
                              <img src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(oferta.getImage()) %>" alt="<%=oferta.getNombre() %>" class="img-fluid">
                          </div>
                           <div class="col-md-8">
                              <div class="card-body">
                                  <h3 class="card-title"><%=oferta.getNombre() %></h3>
                                  <p class="card-text">
                                      <strong>Horario:</strong><%=oferta.getHorarioTexto()%> <br>
                                      <strong>Remuneración:</strong> <span class="text-success"><%=oferta.getRemuneracion() %> <small>$UYU</small></span><br>
                                      <strong>Ciudad:</strong> <%=oferta.getCiudad() %><br>
                                      <strong>Empresa:</strong>
		                          	 <%=empresa.getNickname() %>
		                         	 <img class="perfil-img ml-2" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(empresa.getImage()) %>">
                             </div>
                         </div>
              		</div>
              </div>
          
              
        
              <div class="form-outline mb-4">
                  <label class="form-label" for="descripcion-postulacion">Cv reducido:</label>
                  <textarea id="descripcion-postulacion" required  name="cvReducido" class="form-control form-control-lg" style="height: 160px; overflow-y: scroll;"></textarea>
              </div>
                
              <div class="form-outline mb-4">
                  <label class="form-label" for="motivacion-postulacion">Motivación:</label>
                  <textarea id="motivacion-postulacion" name="motivacion" required class="form-control form-control-lg" style="height: 160px; overflow-y: scroll;"></textarea>
              </div>
      
							<div class="form-outline mb-4">
							    <label class="form-label" for="motivacion-postulacion">Vídeo de postulación (opcional):</label>
							    <input type="text" id="motivacion-postulacion" name="videoLink" class="form-control form-control-lg">
							</div>

      
                    <!-- Botones -->
              <!-- Button trigger modal -->
              <div class="btn-section d-flex justify-content-center m-2 mt-3">
                    <button type="button" class="btn btn-primary m-2" data-toggle="modal" data-target="#staticBackdrop">
                  Postularse
                </button>
                      <a class="btn btn-secondary cancel-button m-2" href="/DispositivoMovil/home" type="button">Cancelar</a>
                  </div>
              <!-- Modal -->
              <div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="staticBackdropLabel">Confirme la postulación</h5>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
                    <div class="modal-body">
                      ¿Está seguro que desea postularse?.
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
  
  <jsp:include page="./templates/footer.jsp"></jsp:include>
</body>
</html>
