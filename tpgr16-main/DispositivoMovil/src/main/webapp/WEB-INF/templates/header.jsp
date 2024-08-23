<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Tarea 2 Taller de Programacion">
    <meta name="author" content="Grupo 16">
    <title>Trabajo.uy</title>
    <link rel="icon" type="image/x-icon" href="resources/favicon.ico">
    <link rel="stylesheet" href="resources/lib/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/css/header.css">
    <link rel="stylesheet" href="resources/css/index.css">
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="logo">
        <a class="navbar-brand" href="home"><i class="fas  fa-cubes fa-2x me-3" style="color: #6063ff;"></i> Trabajo.uy</a>
    </div>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    	<span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav sessionBtns">
            <li class="nav-item1">
           		<%= ((DtUsuario) session.getAttribute("usuario_logueado")).getNickname() %> 
                <img class="perfil-img" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(((DtUsuario) session.getAttribute("usuario_logueado")).getImage()) %>">
            </li>
            <li class="nav-item1 mt-2">
            	<a class="nav-link1" href="ofertas">
                    <i class="fas fa-home" aria-hidden="true"></i> Ofertas Laborales
                </a>
            </li>
            <li class="nav-item1 mt-2">
            	<a class="nav-link1" href="/DispositivoMovil/postulaciones">
             	<i class="fa fa-address-book"></i> Postulaciones
             </a>
            </li>
            <li class="nav-item1 mt-2">
            	<a class="nav-link1" href="logout">
                	<i class="fas fa-sign-out-alt"></i> Cerrar Sesión  
                </a>
            </li>
        </ul>
    </div>
</nav>

<script src="resources/lib/js/font-awesome.js"></script>

</body>
</html>

