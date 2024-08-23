<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
	<jsp:include page="/WEB-INF/templates/header.jsp"/>

    <body>
        <div class="d-flex align-items-center justify-content-center vh-100">
            <div class="text-center">
                <h1 class="display-1 fw-bold">404</h1>
                <p class="fs-3"> <span class="text-danger">Opps!</span> Página no encontrada</p>
                <p class="lead">
                    La página que estás buscando no existe.
                  </p>
                <a href="/ServidorWeb/home" class="btn btn-primary" style="background-color: #5360ff !important;">Volver</a>
            </div>
        </div>
    </body>

	<jsp:include page="/WEB-INF/templates/footer.jsp"/>

</html>

    