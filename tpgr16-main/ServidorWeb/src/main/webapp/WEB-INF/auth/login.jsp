<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    	<meta name="description" content="Tarea 2 Taller de Programacion">
    	<meta name="author" content="Grupo 16">

        <title>Trabajo.uy</title>

        <link rel="stylesheet" href="resources/lib/css/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/login.css">

        <script src="resources/lib/js/font-awesome.js"></script>
    </head>

    <body>
        <section class="vh-100" style="background-color: #a6b6f3;">
            <div class="container py-5 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col col-xl-10">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="row g-0">
                                <div class="col-md-6 col-lg-5 d-none d-md-block clipart-container">
                                    <img src="resources/media/login-img.png" alt="login form" class="img-fluid clipart"/>
                                </div>
                                <div class="col-md-6 col-lg-7 d-flex align-items-center">
                                    <div class="card-body p-4 p-lg-5 text-black">
                                        <form action="login" method="POST">
                                            <div class="d-flex align-items-center mb-3 pb-1">
                                                <i class="fas fa-cubes fa-2x me-3" style="color: #6063ff;"></i>
                                                <span class="h1 fw-bold mb-0">Trabajo.uy</span>
                                            </div>

                                            <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Inicia sesión en tu cuenta</h5>

											<% if (request.getAttribute("errorMessage") != null) { %>
											    <div class="alert alert-danger alert-dismissible fade show" role="alert">
											        <%= request.getAttribute("errorMessage") %>
											        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
												    	<span aria-hidden="true">&times;</span>
												  	</button>
											    </div>
											<% } %>

                                            <div class="form-outline mb-4">
                                                <input name="emailOrNickname" type="text" id="form2Example17" class="form-control form-control-lg" required />
                                                <label class="form-label" for="form2Example17">Email o nickname</label>
                                            </div>

                                            <div class="form-outline mb-4">
                                                <input name="password" type="password" id="form2Example27" class="form-control form-control-lg" required />
                                                <label class="form-label" for="form2Example27">Contraseña</label>
                                            </div>

                                            <div class="pt-1 mb-4">
                                                <button class="btn btn-dark btn-lg btn-block login-button" type="submit">Login</button>
                                            </div>

                                            <div class="pt-1 mb-4">
                                                <button class="btn btn-secondary btn-lg btn-block" type="button" onclick="cancelLogin()">Cancelar</button>
                                            </div>

                                            <a class="small text-muted" href="#!">¿Olvidaste la contraseña?</a>
                                            <p class="mb-5 pb-lg-2" style="color: #2c30ae;">No tienes cuenta? 
                                                <a href="signup" style="color: #2c30ae;">Registrate aquí</a>
                                            </p>
                                            <a href="#!" class="small text-muted">Términos de uso.</a>
											<a href="#!" class="small text-muted">Política de privacidad</a>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>

    <script>
        function cancelLogin() {
            window.location.href = "home";
        }
    </script>

    <script src="resources/lib/js/jquery-3.3.1.slim.min.js"></script>
    <script src="resources/lib/js/popper.min.js"></script>
    <script src="resources/lib/js/bootstrap.min.js"></script>
</html>