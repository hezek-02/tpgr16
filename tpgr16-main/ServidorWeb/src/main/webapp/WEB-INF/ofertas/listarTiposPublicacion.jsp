<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<link rel="stylesheet" href="resources/css/tiposPublicacion.css">

<body>
	<jsp:include page="/WEB-INF/templates/header.jsp" />

	<!-- Contenedor -->
	<div class="main-container">
		<jsp:include page="../templates/sidebar.jsp"></jsp:include>
		<div class="generic-section-tipos"> 
			<h2>Tipos de Publicación</h2>
			<div class="tipos-publicacion mt-4" id="list-tp">
				<%
				@SuppressWarnings("unchecked")
				Collection<DtTipoPub> tpPubs = (Collection<DtTipoPub>) request.getAttribute("tipos_publicacion");
    			for(DtTipoPub tp: tpPubs){
				%>
					<a class="nombres mt-2" href="/ServidorWeb/tipos-publicacion?tipo=<%=tp.getNombre()%>">
						<%= tp.getNombre() %>
					</a>	
				<% } %>	
			</div>
		</div>
	</div>
	
	<jsp:include page="../templates/footer.jsp"></jsp:include>
</body>
</html>