<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Collection"%>
<%@page import="com.trabajouy.models.TipoUsuario"%>
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
				<div class="title-tp">
				<% 
		          if (request.getAttribute("error") != null){ %>
		        	<jsp:include page="../templates/errorMessage.jsp"></jsp:include>
	       		<%}else if (request.getAttribute("success") != null){ %>
					<jsp:include page="../templates/successMessage.jsp"></jsp:include>
				<%}
				boolean vencidaConfirmada = (Boolean) request.getAttribute("vencidaConfirmada");
				DtOferta oferta = (DtOferta) request.getAttribute("oferta");
				DtPostulacion post = (DtPostulacion) request.getAttribute("Postulante");
				%>
					<h2> Postulación a <%=oferta.getNombre() %>: </h2> 
				</div>
				<div class="tp-info">
					<p><strong>Postulante: </strong> <a href="profile?nickname=<%=post.getNicknamePostulante()%>"><%=post.getNicknamePostulante()%></a></p>			
					<p><strong>Oferta: </strong><a href="consulta-oferta?oferta=<%=oferta.getNombre()%>"> <%=post.getNombreOferta() %> </a></p>	
					<p><strong>Motivación: </strong> <%=post.getMotivacion() %></p>
					<p><strong>Cv Reducido: </strong> <%=post.getCvReducido() %></p>
         			<p><strong>Fecha Postulacion: </strong> <%=post.getFechaPostulacionTexto() %></p>
				</div>
         			
         			<%
					String videoUrl = post.getVideoUrl();
					if (videoUrl != null && !videoUrl.isEmpty() && videoUrl.startsWith("http") ) {
					%>
	 				<div class="video-container embed-responsive embed-responsive-16by9" >
	                    <iframe class="embed-responsive-item" src="<%= videoUrl %>" ></iframe>
	                </div>
					<%}%>
					<% if (post.getRanking() > 0 && vencidaConfirmada){ %>
				    <div class="border mt-5 p-3 mb-3">
				        <h5>Fecha de selección:</h5>
				        <p><%= post.getFechaRankingTexto() %></p>
				        <h5>Puesto en postulación a la oferta:</h5>
				        <p><strong><%= post.getRanking() %></strong></p>
				    </div>
					<%}else if (oferta.getEstado().equals(EstadoOferta.FINALIZADA)){%>
						<div class="alert alert-danger mt-5" role="alert">
					        La oferta de la actual postulación ha finalizado.
					    </div>
					<%} %>

		</div>
	</div>
<jsp:include page="../templates/footer.jsp"></jsp:include>
</body>
</html>