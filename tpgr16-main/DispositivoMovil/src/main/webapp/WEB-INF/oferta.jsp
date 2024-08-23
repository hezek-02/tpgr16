<%@ page import="com.trabajouy.models.*, com.trabajouy.webservices.*, java.util.*, java.net.URLEncoder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="resources/css/consultaOferta.css">
</head>
<body>
    <jsp:include page="/WEB-INF/templates/header.jsp" />
    
    <div class="main-container">
        <div class="oferta-section">
            <% 
	            DtOferta oferta = (DtOferta) request.getAttribute("oferta");
            	DtEmpresa empresa = (DtEmpresa) request.getAttribute("empresaDeOferta");
	            Boolean propietario = (Boolean) request.getAttribute("propietario");
	            TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipo_usuario");
	            Boolean noPostulado = (Boolean) request.getAttribute("no_postulado");
	            Boolean vencida = (Boolean) request.getAttribute("vencida");
            %>
            
            <div>
            	<!-- Título e Imagen -->
            	<h3> <%= oferta.getNombre() %></h3>
                <img class="rounded mt-2 mb-4" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(oferta.getImage()) %>" alt="<%= oferta.getNombre() %>">
            </div> 

            <div>
                <!-- Offer details -->
                <div class="d-flex">
                	<p><b>Empresa:</b> <%= empresa.getNickname() %></p>
                	<img class="perfil-img ml-2" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(empresa.getImage()) %>">
                </div>
                
                <p><b>Descripción:</b> <%= oferta.getDescripcion() %></p>
                <p><b>Remuneración:</b> <span class="text-success"><%= oferta.getRemuneracion() %> <small>$UYU</small></span></p>
                <p><b>Horario:</b> <%= oferta.getHorarioTexto() %></p>
                <p><b>Departamento:</b> <%= oferta.getDepartamento() %></p>
                <p><b>Ciudad:</b> <%= oferta.getCiudad() %></p>
                <p><b>Fecha de alta:</b> <%= oferta.getFechaAltaTexto() %></p>

                <% if(oferta.getEstado() == EstadoOferta.FINALIZADA) { %>
                    <p><b>Fecha de baja:</b> <%= oferta.getFechaBajaTexto() %></p>
                <% } %>

                <% if (oferta.getKeyWords() != null && !oferta.getKeyWords().isEmpty()) { %>
                    <div class="title-oferta">
                        <b>Keywords:</b>
                        <% for(KeyWord key : oferta.getKeyWords()) { %>
                            <%= key.getNombreKey() %><% if (!key.equals(oferta.getKeyWords().get(oferta.getKeyWords().size() - 1))) { %>, <% } %>
                        <% } %>
                    </div>
                <% } %>

                
                <% if (!vencida && noPostulado) { %>
                    <div class="btn-section d-flex justify-content-center mt-5">
                        <a href="/DispositivoMovil/postularse?oferta=<%= URLEncoder.encode(oferta.getNombre(), "UTF-8") %>" class="btn btn-success btn-lg">Postularse</a>
                    </div>
                <% } else if (vencida && noPostulado) { %>
                    <div class="alert alert-danger mt-2" role="alert">
                        La oferta ha vencido, no es posible postularse.
                    </div>
                <% } else if (!noPostulado) { %>
                    <div class="btn-section d-flex justify-content-center m-2 mt-5">
                        <a href="/DispositivoMovil/postulaciones?oferta=<%= oferta.getNombre() %>" class="btn btn-primary btn-lg">Ver Postulación</a>
                    </div>
                <% } %>
                
            </div> <!-- End of info-container -->
        </div> <!-- End of oferta-section -->
    </div> <!-- End of main-container -->

    <jsp:include page="/WEB-INF/templates/footer.jsp" />
</body>
</html>
                       
