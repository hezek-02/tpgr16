document.getElementById('confirmPassword').addEventListener('input', function() {
	this.setCustomValidity(''); // Clear any previous custom validity messages
});

function toggleExtraFields(value) {
    const postulanteFields = document.getElementById('postulanteFields');
    const empresaFields = document.getElementById('empresaFields');
    const descriptionField = document.getElementById('description');
    const websiteLinkField = document.getElementById('websiteLink');
    const birthDateField = document.getElementById('birthDate');
    const nationalityField = document.getElementById('nationality');

    if (value === 'POSTULANTE') {
        postulanteFields.style.display = 'block';
        empresaFields.style.display = 'none';

        birthDateField.setAttribute('required', true);
        nationalityField.setAttribute('required', true);
        descriptionField.removeAttribute('required');
        websiteLinkField.removeAttribute('required');
    } else if (value === 'EMPRESA') {
        postulanteFields.style.display = 'none';
        empresaFields.style.display = 'block';

        descriptionField.setAttribute('required', true);
        birthDateField.removeAttribute('required');
        nationalityField.removeAttribute('required');
    }
}

function cancelRegistration() {
    window.location.href = "home";
}

function validatePassword() {
    const passwordField = document.getElementById('password');
    const confirmPasswordField = document.getElementById('confirmPassword');
    
    confirmPasswordField.setCustomValidity('');

    if (passwordField.value !== confirmPasswordField.value) {
        confirmPasswordField.setCustomValidity('Las contraseñas no coinciden.');
        confirmPasswordField.reportValidity();
        return false;
    } else {
        confirmPasswordField.setCustomValidity('');
        return true;
    }
}
        
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
        
        
     // Función para realizar la verificación AJAX y actualizar la interfaz de usuario
function checkAvailability(type, value) {
    var xhr = new XMLHttpRequest();
    var url = 'signup?' + type + '=' + encodeURIComponent(value);
    xhr.open('GET', url, true);

    xhr.onload = function() {
        // Seleccionar el elemento de estado basado en el tipo de verificación
        var statusElement = document.getElementById(type + 'Status');
        
        if (xhr.status === 200) {
            // Actualizar la interfaz de usuario con la respuesta del servidor
            var responseText = xhr.responseText.trim(); // Trim para remover espacios en blanco alrededor de la cadena
            if(responseText === "DISPONIBLE") {
                statusElement.textContent = 'Disponible';
                statusElement.className = 'form-text text-success'; // Clase para texto de éxito
            } else {
                statusElement.textContent = responseText; // Mostrará "NICKNAME EN USO" o "EMAIL EN USO"
                statusElement.className = 'form-text text-danger'; // Clase para texto de error
            }
        } else {
            // Manejar otros códigos de estado HTTP aquí, como un error 500
            statusElement.textContent = 'Error al verificar la disponibilidad.';
            statusElement.className = 'form-text text-warning'; // Clase para texto de advertencia
        }
    };

    xhr.onerror = function() {
        // Manejar errores de red aquí
        var statusElement = document.getElementById(type + 'Status');
        statusElement.textContent = 'Error de red, por favor intenta nuevamente.';
        statusElement.className = 'form-text text-warning'; // Clase para texto de advertencia
    };

    xhr.send();
}

// Event listener para el nickname
document.getElementById('nickname').addEventListener('input', function() {
    checkAvailability('nickname', this.value);
});

// Event listener para el email
document.getElementById('email').addEventListener('input', function() {
    checkAvailability('email', this.value);
});

        