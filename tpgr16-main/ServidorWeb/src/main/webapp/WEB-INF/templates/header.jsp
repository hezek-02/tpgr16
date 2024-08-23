<%@page import="com.trabajouy.webservices.*"%>
<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light d-flex justify-content-between">
    <div class="logo">
        <a class="navbar-brand " href="home">
            <i class="fas  fa-cubes fa-2x me-3" style="color: #6063ff;"></i>
            Trabajo.uy
        </a>
    </div>
    <!-- Search bar -->
	<form class="input-group search-bar" role="search" action="search" method="GET">
	    <input type="text" name="busqueda" class="form-control" placeholder="Buscar Oferta Laboral..." aria-label="Search for jobs">
	    <div class="input-group-append">
	        <button class="btn btnSearch btn-primary" type="submit">Buscar</button>
	    </div>
	</form>


    <div class="d-flex">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav sessionBtns">
                <%
                DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
                if (usuario != null) {
                %>
                    <li class="nav-item1">
                        <a class="nav-link1" href="/ServidorWeb/profile?nickname=<%= usuario.getNickname()%>">
                            <%=usuario.getNickname()%> <img class="perfil-img" src="data:image/*;base64, <%= Base64.getEncoder().encodeToString(usuario.getImage()) %>">
                        </a>
                    </li>
                    <li class="nav-item1">
                        <a class="nav-link1" href="logout">
                            Cerrar Sesión  <i class="fas fa-sign-out-alt"></i>
                        </a>
                    </li>
                <% 
                } else { 
                %>
                    <li class="nav-item1">
                        <a class="nav-link1" href="signup">
                            Registro<i class="fas fa-user-plus"></i>
                        </a>
                    </li>
                    <li class="nav-item1">
                        <a class="nav-link1" href="login">
                            Login<i class="fas fa-user"></i>
                        </a>
                    </li>
                <% 
                } 
                %>
            </ul>
        </div>
    </div>
</nav>


<script src="resources/lib/js/font-awesome.js"></script>
