<%@page import="java.util.Collection" %>
<%@page import="com.trabajouy.models.TipoUsuario" %>
<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" href="resources/css/compraPaquete.css">
<body>

	<jsp:include page="/WEB-INF/templates/header.jsp" />
	
	<!-- Paquetes -->
	<div class="page">
		<jsp:include page="../templates/sidebar.jsp" />
		<div class="main-container2">
			<h1>Paquetes de Tipos de Publicación de Ofertas Laborales </h1>
			<div class="productos-container">
		    	<%
				DtPaquetePub[] paquetes = (DtPaquetePub[]) request.getAttribute("paquetes");
				for (DtPaquetePub paq : paquetes){
				%>
				<div class="item">
		    		<div class="titulo">
		        		<b><%=paq.getNombre() %></b>
					</div>                   
					<img width="150px" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(paq.getImage()) %>" alt="<%=paq.getNombre()%>">
					<br>
					<div class="contenido">
		    			<a>Período: <%=paq.getPeriodoValidez() %> días</a>
						<a>Descuento: <%=paq.getDescuento() %> %</a>
						<a>Costo: <span class="text-success"><%=paq.getCosto() %> <small>$UYU</small></span></a>
		        	<div class="botones">
		           		<a class="btn btn-primary" href="/ServidorWeb/paquetes?paquete=<%=paq.getNombre() %>" type="button">Más Info</a>
		        	</div>
		    		</div>
				</div>
				<% } %>
			</div>
		</div>
	</div>


	<jsp:include page="/WEB-INF/templates/footer.jsp" />

</body>
</html>