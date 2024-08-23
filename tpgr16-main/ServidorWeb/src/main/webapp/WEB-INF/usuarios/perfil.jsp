<%@page import="com.trabajouy.webservices.*"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.Base64" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<link rel="stylesheet" href="resources/css/perfil.css">
<body>

	
	<jsp:include page="/WEB-INF/templates/header.jsp" />
	
	<div class="main-container">
	
	<!--SideBar -->
	<jsp:include page="/WEB-INF/templates/sidebar.jsp" />
		
	<!--Zona de perfil -->
	<!-- Main content -->
    <div class="generic-container mt-5">
     <div class="container-perfil">
	      <%if (request.getAttribute("error") != null){%> 
	      <jsp:include page="/WEB-INF/templates/errorMessage.jsp"></jsp:include>
	        <% }
	      	boolean isEmpresa = (boolean)request.getAttribute("isEmpresa");
	      	DtUsuario usuario = null;
	      	DtPostulante postulante = null;
	      	DtEmpresa empresa = null;
	      	if(isEmpresa){
	      	    empresa  = (DtEmpresa)request.getAttribute("usuPerfil");
	      		usuario = empresa;
	      	}
	      	else{
	      		postulante = (DtPostulante)request.getAttribute("usuPerfil");
	      		usuario = postulante;
	      	}
        	DtUsuario usuarioLog = (DtUsuario) request.getSession().getAttribute("usuario_logueado");
        	@SuppressWarnings("unchecked")
        	List<DtOferta> colOfertasIngresadas = (List<DtOferta>) request.getAttribute("ofertasIngresadas");
        	@SuppressWarnings("unchecked")
        	List<DtOferta> colOfertasConfirmadas = (List<DtOferta>) request.getAttribute("ofertasConfirmadas");
        	@SuppressWarnings("unchecked")
        	List<DtOferta> colOfertasRechazadas = (List<DtOferta>) request.getAttribute("ofertasRechazadas");
        	@SuppressWarnings("unchecked")
        	List<DtOferta> colOfertasPostulado = (List<DtOferta>) request.getAttribute("ofertasPostulado");
        	@SuppressWarnings("unchecked")
        	List<DtPaquetePub> colPaquetes = (List<DtPaquetePub>) request.getAttribute("paquetesDeEmpresa");
        	@SuppressWarnings("unchecked")
        	List<DtPostulacion> listaPostulacionesUsuario = (List<DtPostulacion>) request.getAttribute("postulacionesUsuario");
        	//ofertas finalizadas
            @SuppressWarnings("unchecked")
            List<DtOferta> colOfertasFinalizadas = (List<DtOferta>) request.getAttribute("ofertasFinalizadas");
       		@SuppressWarnings("unchecked")
        	List<DtUsuario> colSeguidores = (List<DtUsuario>) request.getAttribute("Seguidores");
            @SuppressWarnings("unchecked")
            List<DtUsuario> colSeguidos = (List<DtUsuario>) request.getAttribute("Seguidos");
        	
            boolean isPostulante = !isEmpresa;
            boolean isLoggedInUser = usuarioLog != null && usuario.getNickname().equals(usuarioLog.getNickname());
            boolean yaSeguido = (Boolean) request.getAttribute("yaSeguido");
		%>

            <!-- Perfil -->
            
            <img src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(usuario.getImage()) %>">
            <div class="flex-grow-1 p-2"><%=usuario.getNickname() %></div>
            <%if (usuarioLog != null && !isLoggedInUser && !yaSeguido) {%>
				<form method="post" action="profile">
				    <input type="hidden" name="modo" value="seguir">
				    <input type="hidden" name="nickname" value="<%=usuario.getNickname()%>">
				    <button class="btn btn-primary rounded-pill" type="submit">
				    	<i class="fa fa-user-plus"></i> Seguir
				    </button>
				</form>
			<%}else if (usuarioLog != null && !isLoggedInUser &&  yaSeguido) {%>
				<form method="post" action="profile">
				    <input type="hidden" name="modo" value="dejarDeSeguir">
					<input type="hidden" name="nickname" value="<%=usuario.getNickname()%>">
			   		<button class="btn btn-danger rounded-pill" type="submit">
				    	<i class="fa fa-user-minus"></i> Dejar de seguir
				    </button> 
				</form>
			<%} %>
             <ul class="nav nav-tabs" id="myTab" role="tablist">
                    <li class="nav-item1 active">
                        <a class="nav-link1 active" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Perfil</a>
                    </li>
                    <% if (isEmpresa) { %>
                        <li class="nav-item1">
                            <a class="nav-link1" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">Ofertas laborales</a>
                        </li>
                        <% if (isLoggedInUser) { %>
                            <li class="nav-item1">
                                <a class="nav-link1" id="paquete-tab" data-toggle="tab" href="#paquete" role="tab" aria-controls="paquete" aria-selected="false">Paquetes comprados</a>
                            </li>
                        <% } %>
                    <% } if (isLoggedInUser) { %>
	                        <%if(isPostulante)	{ %>
	                        <li class="nav-item1">
	                            <a class="nav-link1" id="contact-tab" data-toggle="tab" href="#contact" role="tab" aria-controls="contact" aria-selected="false">Postulaciones</a>
	                        </li>
	                        <% } %>
                        <li class="nav-item1">
                            <a class="nav-link1" id="modificar-tab" data-toggle="tab" href="#modificar" role="tab" aria-controls="modificar" aria-selected="false">Editar Perfil</a>
                        </li>
      
                    <% } %>
                    	
                    	<li class="nav-item1">
                            <a class="nav-link1" id="seguidores-tab" data-toggle="tab" href="#seguidores" role="tab" aria-controls="seguidores" aria-selected="false">Seguidores</a>
                        </li>
                        
                       <li class="nav-item1">
                            <a class="nav-link1" id="seguidos-tab" data-toggle="tab" href="#seguidos" role="tab" aria-controls="seguidos" aria-selected="false">Seguidos</a>
                        </li> 
                        
                </ul>
            
            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                    <!-- Perfil listing headers -->   
                    <div class="d-flex justify-content-center" >
                        <!-- Postulantes datos-->
                        <div class="d-flex justify-content-between border-bottom mb-2 flex-column">
                            <div class="flex-grow-1 p-2"><b>Nombre: </b><%=usuario.getNombre()%></div>
                            <div class="flex-grow-1 p-2"><b>Apellido: </b><%=usuario.getApellido()%></div>
                            <div class="flex-grow-1 p-2"><b>Email: </b><%=usuario.getCorreo()%></div>
                            
                            <% if (isEmpresa){
                            %>
                            <div class="flex-grow-1 p-2"><b>Descripción: </b><%=empresa.getDescripcion() %></div>
                            <div class="flex-grow-1 p-2"><b>Sitio web: </b><%=empresa.getLinkSitioWeb() %></div>
                       		<%}else {                      			
                       		%>
                                <div class="flex-grow-1 p-2"><b>Fecha de Nacimiento: </b><%=postulante.getFechaNacimientoTexto() %></div>
                           		<div class="flex-grow-1 p-2"><b>Nacionalidad: </b><%=postulante.getNacionalidad() %></div>
                           <%}%> 

                        </div>
                    </div>
                </div>
                
                <% if (isEmpresa){
                %>
                <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
                        <div><b>Confirmadas:</b></div>
                         <% 
                         for (DtOferta oferta: colOfertasConfirmadas){
              			 %>
                        <div><b>Oferta:</b><a class="nav-link1" href="/ServidorWeb/consulta-oferta?oferta=<%= oferta.getNombre()%>">
                            <%=oferta.getNombre() %>
                       </a></div>
                         <%}%> 
     					 <% if(isLoggedInUser){ 
                		 %>
                		 
                        <br>
                        <div><b>Ingresados:</b></div>
                         <% 
                         for (DtOferta oferta: colOfertasIngresadas){
              			 %>
                        <div><b>Oferta:</b><a class="nav-link1" href="/ServidorWeb/consulta-oferta?oferta=<%= oferta.getNombre()%>">
                            <%=oferta.getNombre() %>
                       </a></div>
                         <%}%> 
                        
                        <br>
                        <div><b>Rechazados:</b></div>
                         <% 
                         for (DtOferta oferta: colOfertasRechazadas){
              			 %>
              			 <div><b>Oferta:</b><a class="nav-link1" href="/ServidorWeb/consulta-oferta?oferta=<%= oferta.getNombre()%>">
                            <%=oferta.getNombre() %>
                       </a></div>
                       <%}%>
                        
                        <br>
                        <div><b>Finalizadas:</b></div>
                        <% 
                         for (DtOferta oferta: colOfertasFinalizadas){
              			 %>
              			 <div><b>Oferta:</b><a class="nav-link1" href="/ServidorWeb/consulta-oferta?oferta=<%= oferta.getNombre()%>">
                            <%=oferta.getNombre() %>
                       </a></div>
                        <%}%>
                       
                        
                       <%}%> 
                </div>
                <% if(isLoggedInUser){ 
                 %>
                 <div class="tab-pane fade" id="paquete" role="tabpanel" aria-labelledby="paquete-tab">
                    <div><b>Paquetes adquiridos:</b></div>
                      <% for (DtPaquetePub paquete: colPaquetes) { %>
                    <div><b>Paquete:</b><a class="nav-link1" href="/ServidorWeb/paquetes?paquete=<%=paquete.getNombre() %>">
                       <%=paquete.getNombre() %>
                   </a></div>
                     <% } %> 
                </div>
                <% }  %> 
                <% }else { 
                	 if(isLoggedInUser) {
                 %> 
                  <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
                         <%for (DtPostulacion postulacion: listaPostulacionesUsuario){
                        	  %>
                        <div><br> 
                        <div><b>Oferta:</b><a class="nav-link1" href="/ServidorWeb/consulta-oferta?oferta=<%= postulacion.getNombreOferta()%>">
                             <%=postulacion.getNombreOferta()%>
                       </a></div>
                        <div><b>Información de la Postulación:</b><a class="nav-link1" href="/ServidorWeb/PostOL?o=<%= postulacion.getNombreOferta()%>">
                             Info
                       </a></div>
                        </div>
                        <br>
                    
                        <% } %> 
                </div>
                  <% } } %> 
            
          <% if(isLoggedInUser){//aca es modificar 
            %>
				<div class="tab-pane fade" id="modificar" role="tabpanel" aria-labelledby="modificar-tab">
				    <form action="modificarPerfil" method="post"  onsubmit="return validateForm();" enctype="multipart/form-data">
				        <div class="row">
				            <div class="col-md-6 form-group">
				                <label for="nombre">Nombre</label>
				                <input type="text" id="nombre" name="nombre" value="<%=usuario.getNombre()%>" class="form-control" required />
				            </div>
				            <div class="col-md-6 form-group">
				                <label for="apellido">Apellido</label>
				                <input type="text" id="apellido" name="apellido" value="<%=usuario.getApellido()%>" class="form-control"  required/>
				            </div>
				            <div class="col-12 form-group">
				                <label for="imagen">Imagen (opcional)</label>
				                <input type="file" id="imagen" name="imagen" class="form-control-file" />
				            </div>
                          <% if (isEmpresa){
                                //DtEmpresa empresa = (DtEmpresa) usuario;  
                          %>
                            <div class="col-12 form-group">
                                <label for="descripcion">Descripción</label>
                                <textarea id="descripcion"  name="descripcion" class="form-control"  required rows="3"><%=empresa.getDescripcion()%></textarea>
                            </div>
                            <div class="col-12 form-group">
                                <label for="sitio_web">Link de Empresa</label>
                                <input type="text" id="sitio_web" name="sitio_web" class="form-control" value="<%=empresa.getLinkSitioWeb()%>" />
                            </div>
                    							            
								          <%} else{
								        	  //DtPostulante postulante = (DtPostulante) usuario;  
                          %>
                            <input type="hidden" id="userType" value="POSTULANTE">
								            <div class="col-md-6 form-group">
								                <label for="nacionalidad">Nacionalidad</label>
								                <input type="text" id="nacionalidad" name="nacionalidad" value="<%=postulante.getNacionalidad() %>" class="form-control" required/>
								            </div>
                            <div class="col-md-6 form-outline mb-4">
                                <label class="form-label" for="birthDate">Fecha de nacimiento</label>
                                <input type="date" id="birthDate" name="nacimiento"  value="<%=postulante.getFechaNacimiento()%>" class="form-control form-control-lg"  required />          
                            </div>
                          <%} %>
				            <div class="pt-1 mb-4 col-12">
				                <button class="btn btn-dark btn-lg btn-block login-button" type="submit">Confirmar Modificación</button>
				            </div>
				        </div>
				    </form>
				</div>
              <% } %>
                 
                 <div class="tab-pane fade" id="seguidores" role="tabpanel" aria-labelledby="seguidores-tab">
                    <% if (colSeguidores != null){  %>
                    <div><b>Seguidores:</b></div>
                      <% for (DtUsuario usu : colSeguidores) { %>
                    <div><b>Seguidor:</b><a class="nav-link1" href="/ServidorWeb/profile?nickname=<%=usu.getNickname()%>">
                       <%=usu.getNickname() %>
                   </a></div>
                     <% } %> 
                   <% }else{ %>
                   <b>No posee seguidores</b>
                   <%} %>
                 </div> 
                 
             	<div class="tab-pane fade" id="seguidos" role="tabpanel" aria-labelledby="seguidos-tab">
	                 <% if (colSeguidos != null){  %>
                    <div><b>Seguidos:</b></div>
                      <% for (DtUsuario usu : colSeguidos) { %>
                    <div><b>Seguido:</b><a class="nav-link1" href="/ServidorWeb/profile?nickname=<%=usu.getNickname()%>">
                       <%=usu.getNickname() %>
                   </a></div>
                     <% } %> 
                   <% }else{ %>
                   <b>No posee seguidos</b>
                   <%} %>
                  </div>
              
          </div>     
          </div>   
        </div>
    </div>
	
	<script>
	function validateForm() {
        if (!validatePassword()) return false;
      
      const userType = document.getElementById('userType').value;
        
        if (userType === 'POSTULANTE') {
            const birthDateField = document.getElementById('birthDate');
            
            const birthDate = new Date(birthDateField.value);
            const currentDate = new Date();

            let age = currentDate.getFullYear() - birthDate.getFullYear();
            const m = currentDate.getMonth() - birthDate.getMonth();
            
            if (m < 0 || (m === 0 && currentDate.getDate() < birthDate.getDate())) {
                age--;
            }

            if (age > 90 || age < 18) {
                birthDateField.setCustomValidity('La fecha de nacimiento proporcionada no es válida.');
                birthDateField.reportValidity();  
                return false;
            } else {
                birthDateField.setCustomValidity('');
            }
        }
        return true;
    }
	</script>

<jsp:include page="/WEB-INF/templates/footer.jsp" />

</body>
</html>