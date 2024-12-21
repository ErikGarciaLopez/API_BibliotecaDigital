const API_URL = 'http://localhost:8080/api'; // Ajusta según tu configuración

function mostrarSeccion(seccion) {
    document.querySelectorAll('.seccion').forEach(s => s.style.display = 'none');
    document.getElementById(`seccion-${seccion}`).style.display = 'block';
    
    // Cargar datos según la sección
    switch(seccion) {
        case 'libros':
            cargarLibros();
            break;
        case 'autores':
            cargarAutores();
            break;
        case 'usuarios':
            cargarUsuarios();
            break;
        case 'prestamos':
            cargarPrestamos();
            break;
    }
}

// Función para manejar errores de la API
function manejarError(error) {
    console.error('Error:', error);
    alert('Ha ocurrido un error. Por favor, intenta nuevamente.');
} 