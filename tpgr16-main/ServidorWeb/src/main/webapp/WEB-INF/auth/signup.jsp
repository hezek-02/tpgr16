<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Tarea 2 Taller de Programacion">
    <meta name="author" content="Grupo 16">
    <title>Trabajo.uy - Registro</title>
    <link rel="stylesheet" href="resources/lib/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/signup.css">
    <script src="resources/lib/js/font-awesome.js"></script>
      <style>
        .compact-form .form-outline {
            margin-bottom: 0.5rem;
        }
        .compact-form .form-label {
            font-size: 0.9rem;
        }
        .compact-form .form-control {
            font-size: 0.9rem;
            padding: 0.375rem 0.75rem;
        }
    </style>
</head>

<body>
    <section class="vh-100" style="background-color: #a6b6f3;">
        <div class="container py-4 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-xl-10">
                    <div class="card compact-form" style="border-radius: 1rem;">
                        <div class="card-body p-3 p-lg-4 text-black">
                            <form action="signup" method="POST" id="signupForm" onsubmit="return validateForm();" enctype="multipart/form-data">
                                <div class="d-flex align-items-center mb-2 pb-1">
                                    <i class="fas fa-cubes fa-2x me-2" style="color: #6063ff;"></i>
                                    <span class="h1 fw-bold mb-0">Trabajo.uy</span>
                                </div>
                                <h5 class="fw-normal mb-2 pb-2" style="letter-spacing: 1px; font-size: 1.2rem;">Crea tu cuenta</h5>
                               	<% if (request.getAttribute("errorMessage") != null) { %>
							    <div class="alert alert-danger alert-dismissible fade show" role="alert">
							        <%= request.getAttribute("errorMessage") %>
							        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
								    	<span aria-hidden="true">&times;</span>
								  	</button>
							    </div>
								<% } %>
                                <div class="row">
                                    <!-- I've grouped fields and adjusted spacing -->
                                  	<div class="col-md-6 form-outline">
									    <input type="text" id="nickname" name="nickname" value="<%= request.getParameter("nickname") != null ? request.getParameter("nickname") : "" %>" class="form-control" required />
									    <div class="d-flex">
									    	<label class="form-label mr-4" for="nickname">Nickname</label>
									    	<small id="nicknameStatus" class="form-text"></small> <!-- Mensaje de disponibilidad para nickname -->
									    </div>
									</div>
									<div class="col-md-6 form-outline">
									    <input type="email" id="email" name="email" class="form-control" value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required />
									    <div class="d-flex">
									    	<label class="form-label mr-4" for="email">Correo electrónico</label>
									    	<small id="emailStatus" class="form-text"></small> <!-- Mensaje de disponibilidad para email -->
									    </div>
									</div>
                                    <div class="col-md-6 form-outline">
                                        <input type="text" id="name" name="nombre" class="form-control" value="<%= request.getParameter("nombre") != null ? request.getParameter("nombre") : "" %>" required />
                                        <label class="form-label" for="name">Nombre</label>
                                    </div>
                                    <div class="col-md-6 form-outline">
                                        <input type="text" id="lastname" name="apellido" class="form-control" value="<%= request.getParameter("apellido") != null ? request.getParameter("apellido") : "" %>" required />
                                        <label class="form-label" for="lastname">Apellido</label>
                                    </div>
                                    <div class="col-md-6 form-outline">
                                        <input type="password" id="password" name="password" class="form-control" value="<%= request.getParameter("password") != null ? request.getParameter("password") : "" %>" required />
                                        <label class="form-label" for="password">Contraseña</label>
                                    </div>
                                    <div class="col-md-6 form-outline">
                                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" value="<%= request.getParameter("confirmPassword") != null ? request.getParameter("confirmPassword") : "" %>" required />
                                        <label class="form-label" for="confirmPassword">Confirmar Contraseña</label>
                                    </div>
                                    <div class="col-12 form-outline">
                                        <input type="file" id="image" name="imagen" accept="image/*" class="form-control" />
                                        <label class="form-label" for="imagen">Imagen (opcional)</label>
                                    </div>
                                    <div class="col-12 form-outline">
                                        <label class="form-label mb-1" for="userType">¿Eres postulante o empresa?</label>
                                        <select id="userType" class="form-control" name="userType" onchange="toggleExtraFields(this.value)">
                                            <option value="POSTULANTE">Postulante</option>
                                            <option value="EMPRESA">Empresa</option>
                                        </select>
                                    </div>
                                </div>
                                
   								<div id="postulanteFields">
                                    <div class="row">
                                        <div class="col-md-6 form-outline mb-4">
                                            <input type="date" id="birthDate" name="nacimiento" class="form-control form-control-lg" value="<%= request.getParameter("nacimiento") != null ? request.getParameter("nacimiento") : "" %>" required />
                                            <label class="form-label" for="birthDate">Fecha de nacimiento</label>
                                        </div>
                                        <div class="col-md-6 form-outline mb-4">
                                            <input type="text" id="nationality" name="nacionalidad" class="form-control form-control-lg" value="<%= request.getParameter("nacionalidad") != null ? request.getParameter("nacionalidad") : "" %>" required />
                                            <label class="form-label" for="nationality">Nacionalidad</label>
                                        </div>
                                    </div>
                                </div>
                                
                                <div id="empresaFields" style="display: none;">
                                    <div class="row">
                                        <div class="col-12 form-outline mb-4">
                                            <textarea id="description" name="descripcion" class="form-control form-control-lg" rows="4" >
                                            	<%= request.getParameter("descripcion") != null ? request.getParameter("descripcion").trim() : "" %>
                                            </textarea>
                                            <label class="form-label" for="description">Descripción general</label>
                                        </div>
                                        <div class="col-12 form-outline mb-4">
                                            <input type="url" id="websiteLink" name="websiteLink" class="form-control form-control-lg" value="<%= request.getParameter("websiteLink") != null ? request.getParameter("websiteLink") : "" %>"/>
                                            <label class="form-label" for="websiteLink">Sitio web (opcional)</label>
                                        </div>
                                    </div>
                                </div>

                                <div class="pt-1 mb-4 row">
                                    <div class="col-md-6">
                                        <button class="btn btn-dark btn-lg w-100 login-button" type="submit">Registrar</button>
                                    </div>
                                    <div class="col-md-6">
                                        <button class="btn btn-secondary btn-lg w-100" type="button" onclick="cancelRegistration()">Cancelar</button>
                                    </div>
                                </div>


                                <p class="mb-5 pb-lg-2" style="color: #2c30ae;">Ya tienes cuenta? 
                                    <a href="login" style="color: #2c30ae;">Inicia sesión aquí</a>
                                </p>
                               	<a href="#!" class="small text-muted">Términos de uso.</a>
								<a href="#!" class="small text-muted">Política de privacidad</a>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    

   
    
	<script src="resources/js/signup.js"></script>
	<script src="resources/lib/js/jquery-3.3.1.slim.min.js"></script>
	<script src="resources/lib/js/popper.min.js"></script>
	<script src="resources/lib/js/bootstrap.min.js"></script>
</body>

</html>