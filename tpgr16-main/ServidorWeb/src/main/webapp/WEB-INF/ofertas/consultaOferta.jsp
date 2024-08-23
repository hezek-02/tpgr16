<%@page import="com.trabajouy.models.TipoUsuario"%>
<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Base64" %>
<%@page import="java.util.Collection" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" href="resources/css/consultaOferta.css">

<body>
  <jsp:include page="/WEB-INF/templates/header.jsp" />

  <div class="main-container">
  	<jsp:include page="/WEB-INF/templates/sidebar.jsp" />
        <div class="oferta-section">
        <% 
          if (request.getAttribute("error") != null){ %>
        	<jsp:include page="../templates/errorMessage.jsp"></jsp:include>
      		<%}else if (request.getAttribute("success") != null){ %>
			<jsp:include page="../templates/successMessage.jsp"></jsp:include>
		<%}
        	DtOferta oferta = (DtOferta) request.getAttribute("oferta");
        	DtPaquetePub paquete = (DtPaquetePub) request.getAttribute("paquete");
        	DtEmpresa empresa = (DtEmpresa) request.getAttribute("empresaDeOferta"); 
        %>
        	<h1><%=oferta.getNombre()%></h1> 
            <div style="width: 800px;">          	
            	<img class="rounded mt-2 image-fluid" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(oferta.getImage()) %>" alt="<%=oferta.getNombre()%>">
            </div> 

            <div class ="info-container">
            	<br>
                <b><%=oferta.getNombre() %>:</b> <%=oferta.getDescripcion() %>.
                <hr>
                 
                <div>
	                <b>Empresa:</b>
	                <a class="nav-link1 text-decoration-none m-2" href="/ServidorWeb/profile?nickname=<%=empresa.getNickname()%>">
	                	<%=empresa.getNickname() %>
	                   <img class="perfil-img ml-2" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(empresa.getImage()) %>">
	                </a>
                </div>
                <br>
                <p><b>Remuneración:</b> <span class="text-success"><%=oferta.getRemuneracion() %> <small>$UYU</small></span></p>
               	<p><b>Horario:</b> <%=oferta.getHorarioTexto()%></p>
                <p><b>Departamento:</b> <%=oferta.getDepartamento() %></p>
                <p><b>Ciudad:</b> <%=oferta.getCiudad() %></p>
                <p><b>Fecha de alta:</b> <%=oferta.getFechaAltaTexto()%></p>
                     	
               	<% if(oferta.getEstado() == EstadoOferta.FINALIZADA){ %>
                	<p><b>Fecha de baja:</b> <%=oferta.getFechaBajaTexto()%></p>
               	<%} %>
              
                <div class="title-oferta">
                  <% if(request.getSession().getAttribute("tipo_usuario")==TipoUsuario.EMPRESA && request.getAttribute("propietario") != null && (Boolean) request.getAttribute("propietario").equals(true)){ %>
                    <b>Costo:</b>
                        <%=oferta.getCoste() %>
                      <small>$UYU</small>
                    <% } %>
                </div>
                
                <div class="title-oferta">
                  <% if (oferta.getKeyWords() != null && oferta.getKeyWords().size() > 0){ %>
                      <b>Keywords:</b> <% for(KeyWord keys : oferta.getKeyWords()){ %> <%= keys.getNombreKey() %>, <% } %>
                  <% } %>
                </div>
                
            </div>
            
            <% DtPostulante[] postulantesFavoritos = (DtPostulante[]) request.getAttribute("postulantesFav");
            if (postulantesFavoritos != null || postulantesFavoritos.length <= 0){ 
            	int cant = postulantesFavoritos.length;
            	%>
            	<div class="title-oferta">
            		<b>Cantidad de Favoritos:</b> <%=cant%>
            	</div>
            <% } %>
               
			<%boolean vencida = (Boolean) request.getAttribute("vencida"); 
			TipoUsuario tipoUsu = (TipoUsuario) request.getSession().getAttribute("tipo_usuario");

				if (tipoUsu == TipoUsuario.POSTULANTE) { %>
			    <%
			    boolean no_postulado = (Boolean) request.getAttribute("no_postulado");
			    
			    %>
			    <% if (no_postulado && !vencida) { %>
			    
			     <div class="btn-section d-flex justify-content-center m-2  mt-5">
			       <a href="/ServidorWeb/postularse?oferta=<%=URLEncoder.encode(oferta.getNombre(), "UTF-8") %>" class="btn btn-success btn-lg">Postularse</a>
			      </div>
			      
			    <% } else if (!no_postulado) { %>
			    
			     <div class="btn-section d-flex justify-content-center m-2  mt-5">
			        <a href="/ServidorWeb/PostOL?o=<%=oferta.getNombre() %>" class="btn btn-primary btn-lg">Ver Postulacion</a>
			     </div>
			     
			    <% } %>
			<% }else if (tipoUsu == TipoUsuario.EMPRESA && oferta.getEstado().equals(EstadoOferta.CONFIRMADO)){  
			 	 if (request.getAttribute("propietario") != null && (Boolean) request.getAttribute("propietario").equals(true)  && vencida) { %>
			 	 <form method="post" action="ofertas">
			          <input type="hidden" name="finalizarOferta" value="<%=oferta.getNombre()%>"> <!-- Campo oculto -->
				 	 <!-- Button trigger modal -->
					<div class="btn-section d-flex justify-content-center m-2 mt-5">
					    <button type="button" class="btn btn-danger btn-lg mr-2" data-toggle="modal" data-target="#staticBackdrop">
					        Finalizar oferta
					    </button>
					    <a type="button" href="/ServidorWeb/rankear?oferta=<%=URLEncoder.encode(oferta.getNombre(), "UTF-8")%>" class="btn btn-primary btn-lg ml-2">
					        Rankear Postulantes
					    </a>
					</div>

				       <!-- Modal -->
		              <div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
		                <div class="modal-dialog">
		                  <div class="modal-content">
		                    <div class="modal-header">
		                      <h5 class="modal-title" id="staticBackdropLabel">Confirme finalización de la oferta</h5>
		                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                        <span aria-hidden="true">&times;</span>
		                      </button>
		                    </div>
		                    <div class="modal-body">
		                      ¿Está seguro que desea finalizar la oferta?.
		                    </div>
		                    <div class="modal-footer">
		                  	<div class="btn-section d-flex justify-content-between mt-3">
		                          <button class="btn btn-danger confirm-button" type="submit">Confirmar</button>
		                          <button class="btn btn-secondary cancel-button" data-dismiss="modal" type="button">Cancelar</button>
		                      </div>
		                    </div>
		                  </div>
		                </div>
		              </div>
	              </form>
			<%} %>
		<%} %>
        </div>
        
        
        <% if (request.getSession().getAttribute("tipo_usuario")==TipoUsuario.EMPRESA && request.getAttribute("propietario") != null && (Boolean) request.getAttribute("propietario").equals(true)){ %>
          <div class="right-column-section"> 
              <%
              @SuppressWarnings("unchecked")
            	Collection<DtUsuario> postulantes = (Collection<DtUsuario>) request.getAttribute("postulantes");
              if (postulantes != null){
              %>
                <div class="postulantes-container">
                  <h2 class="Postulantes"> Postulaciones </h2>
                  
                  <%
                  
                  for(DtUsuario post : postulantes){
                  %>
                      <div class="img-postulante">
                            <a href="/ServidorWeb/PostOL?o=<%=oferta.getNombre() %>&post=<%=post.getNickname()%>"><img src="data:image/*;base64,<%= Base64.getEncoder().encodeToString(post.getImage()) %>" ></a>
                        </div>
                   
                    <div><%= post.getNombre() %></div>
                <% }%>
                </div>
            <% } %>
            
            <% 
            if (paquete != null){
            %>
            <div class="paquete-container">
                  <h2 class="paquete"> Paquete </h2>
                  
                  <div class="img-postulante">
                    <a href="/ServidorWeb/paquetes?paquete=<%=paquete.getNombre() %>">
                    	<img src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(paquete.getImage()) %>" alt="<%=paquete.getNombre()%>">
                    </a>
                  </div>
                  <div><%=paquete.getNombre() %></div>
            </div>
            <% } %>
      </div> 
      <% } %>
      </div>
    

  <jsp:include page="/WEB-INF/templates/footer.jsp" />


</body>
</html>