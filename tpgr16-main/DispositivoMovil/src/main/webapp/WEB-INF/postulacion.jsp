<%@ page import="com.trabajouy.models.TipoUsuario, com.trabajouy.webservices.*, java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="resources/css/consultas.css">
</head>
<body>
    <jsp:include page="/WEB-INF/templates/header.jsp" />
    
    <div class="main-container">
        <div class="generic-section"> 
            <div class="title-tp">
                <% 
                DtOferta oferta = (DtOferta) request.getAttribute("oferta");
                DtPostulacion post = (DtPostulacion) request.getAttribute("postulacion");
                %>
                <h2> Postulación a <%= oferta.getNombre() %>: </h2> 
            </div>
            <div class="tp-info">
                <p><strong>Postulante: </strong><%= post.getNicknamePostulante() %></p>          
                <p><strong>Oferta: </strong><a href="/DispositivoMovil/ofertas?oferta=<%= oferta.getNombre() %>"> <%= post.getNombreOferta() %> </a></p>  
                <p><strong>Motivación: </strong> <%= post.getMotivacion() %></p>
                <p><strong>Cv Reducido: </strong> <%= post.getCvReducido() %></p>
                <p><strong>Fecha Postulacion: </strong> <%= post.getFechaPostulacionTexto() %></p>
                <% 
                String videoUrl = post.getVideoUrl();
                TipoUsuario tipoUsuario = (TipoUsuario) request.getSession().getAttribute("tipo_usuario");
                if (videoUrl != null && !videoUrl.isEmpty() && videoUrl.startsWith("http") && tipoUsuario == TipoUsuario.POSTULANTE) {
                %>
                <div class="video-container embed-responsive embed-responsive-16by9" >
                    <iframe class="embed-responsive-item" src="<%= videoUrl %>" ></iframe>
                </div>
                <% } %>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/templates/footer.jsp" />
</body>
</html>