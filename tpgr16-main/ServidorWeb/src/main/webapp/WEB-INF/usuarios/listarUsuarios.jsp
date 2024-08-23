<%@page import="com.trabajouy.webservices.*"%>
<%@page import="java.util.Collection"%>
<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<body>
	<!-- Header -->
	<jsp:include page="/WEB-INF/templates/header.jsp" />
	
	<div class="main-container">
	
		<!-- SideBar -->
		<jsp:include page="/WEB-INF/templates/sidebar.jsp" />	

		<!-- Lista de Usuarios -->
		<div class="generic-section "> 
		<%DtOferta o = (DtOferta) request.getAttribute("ofertaL"); 
		if(o!=null){%>
			     <h2 class="text-center">Postulantes de la oferta: <%=o.getNombre()%> </h2>
		<% }else{ %>
		      <h2 class="text-center">Usuarios</h2>
		<% }%>
			<div class="generic-container" id="putOfertas">
				<% 
				 	@SuppressWarnings("unchecked")
			    	Collection<DtUsuario> colUsuario = (Collection<DtUsuario>) request.getAttribute("usuarios");
			    	for(DtUsuario usuario: colUsuario){
                %>
            	<div class="card flex-row" >
                 <img class="card-img-left" style="width: 200px" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(usuario.getImage()) %>" alt="<%=usuario.getNombre()%>">
                 <div class="card-body">
                 	<h5 class="card-title"><%=usuario.getNombre()%> <%=usuario.getApellido()%></h5>
                 	<%if (o !=null){ %>
                     <a  class="btn btn-primary" href="/ServidorWeb/PostOL?o=<%=o.getNombre() %>&post=<%=usuario.getNickname()%>">Ver postulación</a>
                    <% }else{ %>
                      <a href="/ServidorWeb/profile?nickname=<%= usuario.getNickname()%>" class="btn btn-primary">Ver más</a>
                  	<%} %>
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