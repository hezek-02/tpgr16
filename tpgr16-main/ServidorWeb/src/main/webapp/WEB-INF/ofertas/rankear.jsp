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
		<% 
          if (request.getAttribute("error") != null){ %>
        	<jsp:include page="../templates/errorMessage.jsp"></jsp:include>
      		<%}else if (request.getAttribute("success") != null){ %>
			<jsp:include page="../templates/successMessage.jsp"></jsp:include>
		<%}
		DtOferta oferta = (DtOferta) request.getAttribute("ofertaLab");
		boolean vencidaConfirmada = (Boolean) request.getAttribute("vencidaConfirmada");
		boolean propietario = (Boolean) request.getAttribute("propietario");
		%>
			<div class="container mt-5">
			    <div class="row justify-content-center">
			        <div class="col-10">
			            <h2 class="text-center mb-4">Postulantes de la oferta: <%=oferta.getNombre()%></h2>
			
			            <div class="row">
			                <% 
			                DtPostulacion[] postulantes = (DtPostulacion[]) request.getAttribute("listaPostulantes");
			                for(DtPostulacion postulante: postulantes){
			                %>
			                <div class="col-12 mb-4">
			                    <div class="card">
			                        <div class="card-body">
			                            <h4 class="card-title">Postulante: <a href="/ServidorWeb/profile?nickname=<%=postulante.getNicknamePostulante()%>">
			                            <%=postulante.getNicknamePostulante()%> </a></h4>
			                             <h5 class="card-title"> <a href="/ServidorWeb/PostOL?o=<%=postulante.getNombreOferta()%>&post=<%=postulante.getNicknamePostulante()%>">
			                            Ver Postulación </a></h5>
			                            <form action="rankear" method="post">
			                                <input type="hidden" name="ofertaSelected" value="<%=oferta.getNombre()%>">
			                                <input type="hidden" name="nickPostulante" value="<%=postulante.getNicknamePostulante()%>">
			
			                                <% if (postulante.getRanking() <= 0 && vencidaConfirmada && propietario){ %>
			                                <div class="form-group">
			                                    <label for="rank">Otorgar ranking</label>
			                                    <input type="number" class="form-control" name="rank" min="1" step="1" required>
			                                </div>
			                                <button type="submit" class="btn btn-success">Otorgar ranking</button>
			                                
			                                <% } else if (postulante.getRanking() > 0 && vencidaConfirmada) { %>
			                                <div class="mt-4">
			                                    <h5>Fecha de selección:</h5>
			                                    <p><%= postulante.getFechaRankingTexto() %></p>
			                                    <h5>Puesto en postulación a la oferta:</h5>
			                                    <p><strong><%= postulante.getRanking() %></strong></p>
			                                </div>
			                                <% } %>
			                            </form>
			                        </div>
			                    </div>
			                </div>
			                <% } %>
			            </div>
			        </div>
			    </div>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<jsp:include page="/WEB-INF/templates/footer.jsp" />

</body>
</html>