<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Base64" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">					


<body>
	<!-- Header -->
	<jsp:include page="/WEB-INF/templates/header.jsp" />
	
	<div class="main-container">
	
		<!-- SideBar -->
		<jsp:include page="/WEB-INF/templates/sidebar.jsp" />	

		<!-- Lista de Ofertas -->
		<div class="generic-section "> 
           	<h2 class="text-center">Ofertas Laborales y Empresas</h2>
			<div class="generic-container" id="putOfertas">
				<div class="d-flex justify-content-center align-items-center mt-3"> 
				   	<span class="mr-2"><%=request.getAttribute("cantidad_resultados")%> resultados</span>
				    <div class="dropdown">
				        <button class="btn btn-secondary dropdown-toggle ml-2" type="button" id="ordenarMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				            Ordenar por:
				        </button>
				        <div class="dropdown-menu" aria-labelledby="ordenarMenu">
				            <a class="dropdown-item" href="search?busqueda=<%=request.getParameter("busqueda")%>&orden=alfabetico">Alfabéticamente (A-Z a-z)</a>
							<a class="dropdown-item" href="search?busqueda=<%=request.getParameter("busqueda")%>&orden=default">Por defecto</a>
				        </div>
				    </div>
				</div>
				
				<div class="d-flex justify-content-center align-items-center" role="group">
					<button onclick="mostrar('ofertas')" class="btn btn-primary mr-4 ">Ofertas</button> 
				    <button onclick="mostrar('empresas')" class="btn btn-secondary">Empresas</button>
				</div>
				
				<% if (request.getAttribute("error") != null){%> 
		        	<jsp:include page="/WEB-INF/templates/errorMessage.jsp"></jsp:include>
		        <%}%>
		        
		        <div id="contenedorOfertas">
			        <%
						DtOferta[] colOfertas = (DtOferta[]) request.getAttribute("ofertas");
		                for(DtOferta oferta: colOfertas){
		            %>
		           		<div class="card flex-row mb-4" >
		                    <img class="card-img-left" style="width: 200px" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(oferta.getImage()) %>" alt="<%=oferta.getNombre()%>">
		                    <div class="card-body">
		                     	<h5 class="card-title"><%= oferta.getNombre() %></h5>
		                     	<p class="card-text"><%= oferta.getDescripcion()%></p>
		                    	<a href="/ServidorWeb/consulta-oferta?oferta=<%=oferta.getNombre()%>" class="btn btn-primary">Leer más</a>
		                	</div>
		            	</div>
		           	<% } %>
	           	</div>
	           	
	           	<div id="contenedorEmpresas" style="display: none;">
		           	<%
						DtEmpresa[] colEmpresa = (DtEmpresa[]) request.getAttribute("empresas");
		                for(DtEmpresa empresa: colEmpresa){
		            %>
		            	<div class="card flex-row mb-4" >
		                    <img class="card-img-left" style="width: 200px"  src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(empresa.getImage()) %>" alt="<%=empresa.getNombre()%>">
		                    <div class="card-body">
		                     	<h5 class="card-title"><%= empresa.getNombre() %></h5>
		                     	<p class="card-text"><%= empresa.getDescripcion()%></p>
		                    	<a href="/ServidorWeb/profile?nickname=<%=empresa.getNickname()%>" class="btn btn-primary">Leer más</a>
		                	</div>
		            	</div>
		            <% } %>
	            </div>
	            
			</div>
		</div>
	</div>

	<!-- Footer -->
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
	
	<script>
	    function mostrar(tipo) {
	        var contenedorOfertas = document.getElementById('contenedorOfertas');
	        var contenedorEmpresas = document.getElementById('contenedorEmpresas');
	
	        if (tipo === 'ofertas') {
	            contenedorOfertas.style.display = 'block';
	            contenedorEmpresas.style.display = 'none';
	        } else if (tipo === 'empresas') {
	            contenedorOfertas.style.display = 'none';
	            contenedorEmpresas.style.display = 'block';
	        }
	    }
	
	    // Por defecto, mostrar solo las ofertas al cargar la página
	    window.onload = function() {
	        mostrar('ofertas');
	    }
	</script>

</body>

</html>
