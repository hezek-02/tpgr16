<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Base64" %>
<%@ page import="java.net.URLEncoder" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<body>
	<!-- Header -->
	<jsp:include page="/WEB-INF/templates/header.jsp" />
	
	<div class="main-container">

		<!-- Lista de Ofertas -->
		<div class="generic-section "> 
           <h2 class="text-center">Postulaciones</h2>
				<div class="generic-container" id="putOfertas">
			    	<% if (request.getAttribute("errorMessage") != null) { %>
					    <div class="alert alert-danger alert-dismissible fade show" role="alert">
					        <%= request.getAttribute("errorMessage") %>
					        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
						    	<span aria-hidden="true">&times;</span>
						  	</button>
					    </div>
					<% } %>
		        	<%
						DtOferta[] colOfertas = (DtOferta[]) request.getAttribute("ofertas");
	                    for(DtOferta oferta: colOfertas){
	                %>
	                	<div class="card flex-row" >
	                	<!--codifica en Base64 y devuelve una cadena de texto que contiene la representación Base64 de esos bytes -->
	                	<img class="card-img-left" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(oferta.getImage()) %>" alt="<%=oferta.getNombre()%>">
							<div class="card-body">   
							   <% if (request.getAttribute("vista") != null && request.getAttribute("vista").equals("empresa_ofertas")) { %>
							      <h5><a class="card-title" href="/DispositivoMovil/consulta-postu?ofertaPostulantes=<%=oferta.getNombre() %>"><%= oferta.getNombre() %></a> </h5>
							      <p class="card-text"><%= oferta.getDescripcion()%></p>
							   <% } else { %>
							      <h5 class="card-title"><%= oferta.getNombre() %></h5>
							      <p class="card-text"><%= oferta.getDescripcion()%></p>
							      <a href="/DispositivoMovil/ofertas?oferta=<%=URLEncoder.encode(oferta.getNombre(), "UTF-8") %>" class="btn btn-primary">Leer más</a>
							   <% } %>
							</div>
	                	</div>
	           		<% } %>
				</div>
		</div>
	</div>

	<!-- Footer -->
	<jsp:include page="/WEB-INF/templates/footer.jsp" />

</body>

</html>
