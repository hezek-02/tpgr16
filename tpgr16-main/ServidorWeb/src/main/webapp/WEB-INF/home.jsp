<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Base64" %>
<%@ page import="java.net.URLEncoder" %>
<%@page import="com.trabajouy.models.TipoUsuario" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<body>
	<!-- Header -->
	<jsp:include page="/WEB-INF/templates/header.jsp" />
	
	<div class="main-container">
	
		<!-- SideBar -->
		<jsp:include page="/WEB-INF/templates/sidebar.jsp" />	

		<!-- Lista de Ofertas -->
		<div class="generic-section "> 
		  <%if (request.getAttribute("vista") != null && request.getAttribute("vista").equals("empresa_ofertas")) { %>
			     <h2 class="text-center">Ofertas Laborales de la empresa</h2>
			<%}else if (request.getAttribute("vista") != null && request.getAttribute("vista").equals("postulante_ofertas")){ %>
           <h2 class="text-center">Ofertas Laborales en las que se ha postulado</h2>
			<%}else{ %>
           <h2 class="text-center">Ofertas Laborales</h2>
			<%} %>
				<div class="generic-container" id="putOfertas">
					<% 
		          if (request.getAttribute("error") != null){%> 
		        <jsp:include page="/WEB-INF/templates/errorMessage.jsp"></jsp:include>
		        <%}
				DtOferta[] ofertasFavoritas = (DtOferta[]) request.getAttribute("favoritas");	
				DtOferta[] colOfertas = (DtOferta[]) request.getAttribute("ofertas");
					for(DtOferta oferta: colOfertas){
	         	%>
	               	<div class="card flex-row" >
	               	<!--codifica en Base64 y devuelve una cadena de texto que contiene la representación Base64 de esos bytes -->
	               	<img class="card-img-left" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(oferta.getImage()) %>" alt="<%=oferta.getNombre()%>">
						<div class="card-body">   
						   <% if (request.getAttribute("vista") != null && request.getAttribute("vista").equals("empresa_ofertas")) { %>
						      <h5><a class="card-title" href="/ServidorWeb/consulta-postu?ofertaPostulantes=<%=oferta.getNombre() %>"><%= oferta.getNombre() %></a> </h5>
						      <p class="card-text"><%= oferta.getDescripcion()%></p>
						   <% } else { %>
						      
						      
						      <% if (request.getSession().getAttribute("tipo_usuario") == TipoUsuario.POSTULANTE && oferta.getEstado() == EstadoOferta.CONFIRMADO) { 
									boolean fav = false;
									
									if (ofertasFavoritas != null){
										for (DtOferta favorito: ofertasFavoritas){
											if (favorito.getNombre().equals(oferta.getNombre())){
												fav = true;
											}	
										}
									}
									if (!fav) { %>
										<div class="card-title">
											<form method="post" action="home">
												<input type="hidden" name="modo" value="favorite">
												<input type="hidden" name="oferta" value="<%= oferta.getNombre()%>">
												<button class=" btn btn-link text-decoration-none  " type="submit">
												<span style=" color: black;">
													<i class="fa fa-heart-o" aria-hidden="true"></i>											
												</span>
												</button>
											</form>
										</div>
									<% }else{ %>
										<div class="card-title">
											<form method="post" action="home">
												<input type="hidden" name="modo" value="erase_favorite">
												<input type="hidden" name="oferta" value="<%= oferta.getNombre()%>">
												<button class="btn btn-link fa btn-link text-decoration-none " type="submit">
												<span style=" color: red;">
												<i class="fa fa-heart" aria-hidden="true"></i>
												</span>
												</button>
											</form>
										</div>
									<% } 
									} %>
							  <h5 class="card-title"><%= oferta.getNombre() %> </h5>  
						    
						      <p class="card-text"><%= oferta.getDescripcion()%></p>
						      <a href="/ServidorWeb/consulta-oferta?oferta=<%=URLEncoder.encode(oferta.getNombre(), "UTF-8") %>" class="btn btn-primary">Leer más</a>
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
