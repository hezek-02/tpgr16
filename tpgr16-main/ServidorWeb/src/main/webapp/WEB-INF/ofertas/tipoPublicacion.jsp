<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Collection"%>
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
			<%
			DtTipoPub tp = (DtTipoPub) request.getAttribute("tipo");
			%>
			<div class="title-tp">
				<h2> Tipo de publicación: <%=tp.getNombre() %></h2>
			</div>
			<div class="tp-info">
				<p><strong>Descripción: </strong> <%=tp.getDescripcion() %></p>
				<p><strong>Exposición: </strong> <%=tp.getExposicion() %></p>
				<p><strong>Duración: </strong> <%=tp.getDuracion() %> días</p>
				<p><strong>Costo: </strong> <span class="text-success"><%= tp.getCosto() %> <small>$UYU</small></span></p>
				<p><strong>Fecha Alta: </strong> <%=tp.getFechaAltaTexto() %></p>
			</div>
		</div>
	</div>
<jsp:include page="../templates/footer.jsp"></jsp:include>
</body>
</html>