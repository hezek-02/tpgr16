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
        <div class="generic-section"> 
            <h2 class="text-center">Ofertas Laborales</h2>
            <div class="generic-container" id="putOfertas">
            
            	<% if (request.getAttribute("error") != null) { %>  
                       <jsp:include page="/WEB-INF/templates/errorMessage.jsp"></jsp:include>
				<% } %>
				
				<div class="d-flex flex-row">
	            	<%
					    // Obtener los parámetros actuales de la URL
					    String currentEmpresa = request.getParameter("empresa");
					    String currentKeyword = request.getParameter("keyword");
					    String baseUrl = "ofertas"; // La URL base para tus enlaces
					%>

					<div class="dropdown show mr-2">
					    <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLinkEmpresa" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					        Empresas
					    </a>
					    <div class="dropdown-menu" aria-labelledby="dropdownMenuLinkEmpresa">
					        <%
					        	DtEmpresa[] empresas = (DtEmpresa[]) request.getAttribute("empresas");
					            for (DtEmpresa empresa : empresas) {
					                // Construir la URL con el parámetro de empresa y, si existe, el de keyword
					                String url = baseUrl + "?empresa=" + URLEncoder.encode(empresa.getNickname(), "UTF-8");
					                if (currentKeyword != null && !currentKeyword.isEmpty()) {
					                    url += "&keyword=" + URLEncoder.encode(currentKeyword, "UTF-8");
					                }
					        %>
					        <a class="dropdown-item" href="<%=url%>"><%=empresa.getNickname()%></a>
					        <% } %>
					        <div class="dropdown-divider"></div>
    						<%
					            // Crear la URL para "Todas" las empresas
					            String urlTodasEmpresas = baseUrl;
					            if (currentKeyword != null && !currentKeyword.isEmpty()) {
					                urlTodasEmpresas += "?keyword=" + URLEncoder.encode(currentKeyword, "UTF-8");
					            }
        					%>
       						<a class="dropdown-item" href="<%=urlTodasEmpresas%>">Todas</a>
					    </div>
					</div>
				
					<div class="dropdown show ml-2">
					    <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLinkKeyword" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					        Keywords
					    </a>
					    <div class="dropdown-menu" aria-labelledby="dropdownMenuLinkKeyword">
					        <%
					        	DtKeyWord[] keywords = (DtKeyWord[]) request.getAttribute("keywords");
					            for (DtKeyWord key : keywords) {
					                // Construir la URL con el parámetro de keyword y, si existe, el de empresa
					                String url = baseUrl + "?keyword=" + URLEncoder.encode(key.getNombreKey(), "UTF-8");
					                if (currentEmpresa != null && !currentEmpresa.isEmpty()) {
					                    url += "&empresa=" + URLEncoder.encode(currentEmpresa, "UTF-8");
					                }
					        %>
					        <a class="dropdown-item" href="<%=url%>"><%=key.getNombreKey()%></a>
					        <% } %>
					        <div class="dropdown-divider"></div>
   						   	<%
					            // Crear la URL para "Todas" las keywords
					            String urlTodasKeywords = baseUrl;
					            if (currentEmpresa != null && !currentEmpresa.isEmpty()) {
					                urlTodasKeywords += "?empresa=" + URLEncoder.encode(currentEmpresa, "UTF-8");
					            }
        					%>
        					<a class="dropdown-item" href="<%=urlTodasKeywords%>">Todas</a>
					   	</div>
					</div>
            	</div>
               
                <%  
                    DtOferta[] colOfertas = (DtOferta[]) request.getAttribute("ofertas");
                    for (DtOferta oferta : colOfertas) {
                %>
                    <div class="card flex-row">
                        <!--codifica en Base64 y devuelve una cadena de texto que contiene la representación Base64 de esos bytes -->
                        <img class="card-img-left" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(oferta.getImage()) %>" alt="<%= oferta.getNombre() %>">
                        <div class="card-body">
                            <h5 class="card-title"><%= oferta.getNombre() %></h5>
                            <p class="card-text"><%= oferta.getDescripcion() %></p>
                            <a href="/DispositivoMovil/ofertas?oferta=<%= URLEncoder.encode(oferta.getNombre(), "UTF-8") %>" class="btn btn-primary">Leer más</a>
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
