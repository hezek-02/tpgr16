<%@page import="com.trabajouy.models.TipoUsuario"%>
<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="resources/css/sidebar.css">
<div class="menu-options-container">
	<div class="menu-options">
		<h2 class="keyWords">Menú</h2>
		
		<div class="row">
		    <div class="col w-100">
		        <div class="list-group list-group-flush" id="list-tab">
		        	<a class="list-group-item text-decoration-none" href="home">
                        <i class="fas fa-home" aria-hidden="true"></i> <b>Ofertas Laborales</b>
                    </a>
		            <a class="list-group-item text-decoration-none" href="paquetes">
		                <i class="fas fa-box"></i> <b>Paquetes</b>
		            </a>

		            <a class="list-group-item text-decoration-none" href="tipos-publicacion">

		                <i class="fa fa-tags" aria-hidden="true"></i> <b>Tipos de Publicación</b>
		            </a>
		            <a class="list-group-item text-decoration-none" href="usuarios">
                        <i class="fa fa-address-book" aria-hidden="true"></i> <b>Usuarios</b>
                    </a>

		        	<!-- Navegación exclusiva empresa -->
		        	<% if (request.getSession().getAttribute("tipo_usuario")==TipoUsuario.EMPRESA){ %>
			            <a class="list-group-item text-decoration-none" href="/ServidorWeb/alta-oferta">
			                <i class="fas fa-briefcase"></i> <b>Alta Oferta Laboral</b>
			            </a>
			        <% } %>    
			        
              		<!-- Navegación exclusiva empresa/postulante -->
			        <% if (request.getSession().getAttribute("tipo_usuario")==TipoUsuario.POSTULANTE || request.getSession().getAttribute("tipo_usuario")==TipoUsuario.EMPRESA){	%>
                 		<a class="list-group-item text-decoration-none" href="/ServidorWeb/consulta-postu">
                      		<i class="fa fa-address-book"></i> <b>Postulaciones</b>
                  		</a>
		            <%}%>
		        </div>
		    </div>
		</div>

		<!--KEYWORDS-->
		<%  
		    DtKeyWord[] colKeys = (DtKeyWord[]) request.getAttribute("keys");
		    if(colKeys != null && request.getAttribute("NoCargaSidebarKeys") != "TRUE"){%>
		        <h2 class="keyWords">Keywords</h2>
		        <div class="row">
		            <div class="col w-100 d-flex flex-column align-items-star" >
		                <%for(DtKeyWord key: colKeys){ %>
		                    <div class="form-check">
		                        <input class="form-check-input" type="checkbox" name="keyword" 
		                        	id="keyword_<%= key.getNombreKey() %>" value="<%= key.getNombreKey() %>">
		                        <label class="form-check-label" for="keyword_<%= key.getNombreKey() %>">
		                            <%= key.getNombreKey() %>
		                        </label>
		                    </div>
		                <%} %>
		                <div class="row d-flex mt-3 justify-content-center">
				       		<button type="button" class="btn btn-primary" onclick="filtrar()">Filtrar</button>
				       		<div class="mr-3"></div>
				        	<button type="button" class="btn btn-secondary" onclick="limpiar()">Limpiar</button>
				    	</div>
		            </div>
				    
		        </div>
		<%} %>
	</div>
</div>
<script src="resources/js/controlesAltaOferta.js"></script>
<script src="resources/js/filtroKeyWords.js"></script>