<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<jsp:include page="/WEB-INF/templates/header.jsp"/>

    <body>
        <div class="d-flex align-items-center justify-content-center vh-100">
            <div class="text-center">
                <h1 class="display-1 fw-bold">500</h1>
                <p class="fs-3"> <span class="text-danger">Opps!</span> Ha ocurrido un error interno</p>
                <p class="lead">
                	Por favor, contáctenos para obtener ayuda adicional.
                </p>
                <a href="/ServidorWeb/home" class="btn btn-primary" style="background-color: #5360ff !important;">Volver</a>
            </div>
        </div>
    </body>

	<jsp:include page="/WEB-INF/templates/footer.jsp"/>

</html>