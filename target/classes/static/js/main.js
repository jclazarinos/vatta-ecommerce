// main.js

// Función para manejar el formulario de búsqueda
document.querySelector('#search-form').addEventListener('submit', function(event) {
    event.preventDefault();
    let query = document.querySelector('#search-input').value;
    console.log("Buscando: " + query);
    // Lógica para realizar búsqueda, puede ser mediante AJAX o redirección.
});
