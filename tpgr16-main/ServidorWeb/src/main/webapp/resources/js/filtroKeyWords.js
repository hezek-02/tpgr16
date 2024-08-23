function filtrar() {
    const checkboxes = document.querySelectorAll('input[name="keyword"]:checked');//obtener todos las keyWords checked
    const selectedKeywords = Array.from(checkboxes).map(function(checkbox) { //itera sobre cada elemento, y para cada uno retorna su valor
        return checkbox.value
    })
    console.log(selectedKeywords)
    if (selectedKeywords.length > 0) {
        const newUrl = `/ServidorWeb/ofertas?keywords=${selectedKeywords.join('_')}`;
        window.location.href = newUrl;
    } else {
        const newUrl = `/ServidorWeb/ofertas`;
        window.location.href = newUrl;
    }
}

function limpiar() {
    const checkboxes = document.querySelectorAll('input[name="keyword"]:checked');
    checkboxes.forEach(checkbox => {
        checkbox.checked = false;
    });
    if (window.location != `/ServidorWeb/ofertas`){
		const newUrl = `/ServidorWeb/ofertas`;
        window.location.href = newUrl;
	}
}

document.addEventListener('DOMContentLoaded', function() {
	const urlParams = new URLSearchParams(window.location.search);
	const keywords = urlParams.get('keywords');
	// Verificar si hay keywords en la URL

	if (keywords) {
	  // Dividir las keywords en un array
	  const keywordArray = keywords.split('_')
	  // Iterar sobre los checkboxes
	  document.querySelectorAll('input[name="keyword"]').forEach(checkbox => {
	    // Verificar si el valor del checkbox está en el array de keywords
	    if (keywordArray.includes(checkbox.value)) {
	      checkbox.checked = true; // Marcar el checkbox
	    }
	  });
	}
});