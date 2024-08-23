<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Mensaje de error -->
<div class="alert alert-danger">
    <h3>
    	<%=(String) request.getAttribute("error")%>	
    </h3>
</div>