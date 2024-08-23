function redireccionar(tipoSeleccionado){
    const urlParams = new URLSearchParams(window.location.search);
    const tipoPubActual = urlParams.get('tipoPub');
    var nuevaURL = `/ServidorWeb/alta-oferta?tipoPub=${tipoSeleccionado}`;
    if (tipoSeleccionado !== tipoPubActual) {
        // Guardar los valores del formulario
        const formulario = document.getElementById('formularioOferta');
        for (const elemento of formulario.elements) {
            if (elemento.name && elemento.type !== 'submit' && elemento.id !== 'seleccionTipoPub') {
                localStorage.setItem(elemento.name, elemento.value);
            }
        }
        localStorage.removeItem('seleccionTipoPub');
        window.location.href = nuevaURL;
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const formulario = document.getElementById('formularioOferta');
    const formData = new FormData(formulario);
    for (const [key, value] of formData.entries()) {
        const elemento = formulario.querySelector(`[name="${key}"]`);
        if (elemento) {
            const valorGuardado = localStorage.getItem(key);
            if (valorGuardado) {
                elemento.value = valorGuardado;
            }
        }
    }
});

function seleccionPago(){
	var metodoPagoPaquete = document.getElementById('pagoPaquete');
    var seleccionPaquete = document.getElementById('seleccionPaquete')
    if (metodoPagoPaquete.checked) {
        seleccionPaquete.disabled = false
    } else {
        seleccionPaquete.disabled = true
    }
}

function validarDatos(event){
	var seleccionTipoPub = document.getElementById('seleccionTipoPub')
	console.log(seleccionTipoPub.value)
	if (seleccionTipoPub.value.length === 0){
		document.getElementById('errorMensaje').style.display = 'block';
        event.preventDefault();
	}

    for (const elemento of formulario.elements) {
        if (elemento.name && elemento.type !== 'submit' && elemento.id !== 'seleccionTipoPub') {
            localStorage.setItem(elemento.name, elemento.value);
        }
    }
    localStorage.removeItem('seleccionTipoPub');
    window.location.href = nuevaURL;
}



