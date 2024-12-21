async function cargarLibros() {
    try {
        const response = await fetch(`${API_URL}/libro`);
        const libros = await response.json();
        mostrarLibros(libros);
    } catch (error) {
        manejarError(error);
    }
}

function mostrarLibros(libros) {
    const contenedor = document.getElementById('lista-libros');
    contenedor.innerHTML = '';

    libros.forEach(libro => {
        const card = document.createElement('div');
        card.className = 'col-md-4 libro-card';
        card.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">${libro.titulo}</h5>
                    <p class="card-text">${libro.descripcion}</p>
                    <p class="card-text"><small class="text-muted">Autor: ${libro.autor.nombre}</small></p>
                    <p class="card-text"><small class="text-muted">Género: ${libro.genero}</small></p>
                    <button class="btn btn-primary btn-sm" onclick="editarLibro(${libro.id})">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="eliminarLibro(${libro.id})">Eliminar</button>
                </div>
            </div>
        `;
        contenedor.appendChild(card);
    });
}

async function eliminarLibro(id) {
    if (confirm('¿Estás seguro de que deseas eliminar este libro?')) {
        try {
            const response = await fetch(`${API_URL}/libro/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || `Error ${response.status}: ${response.statusText}`);
            }

            await cargarLibros();

        } catch (error) {
            console.error('Error completo:', error);
            manejarError(`Error al eliminar el libro: ${error.message}`);
        }
    }
}

function mostrarFormularioLibro(libro = null) {
    const modal = document.createElement('div');
    modal.className = 'modal fade';
    modal.id = 'libroModal';
    
    const titulo = libro ? 'Editar Libro' : 'Nuevo Libro';
    
    modal.innerHTML = `
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">${titulo}</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="libroForm">
                        <input type="hidden" id="libroId" value="${libro ? libro.id : ''}">
                        <div class="mb-3">
                            <label for="titulo" class="form-label">Título</label>
                            <input type="text" class="form-control" id="titulo" required value="${libro ? libro.titulo : ''}">
                        </div>
                        <div class="mb-3">
                            <label for="descripcion" class="form-label">Descripción</label>
                            <textarea class="form-control" id="descripcion" rows="3">${libro ? libro.descripcion : ''}</textarea>
                        </div>
                        <div class="mb-3">
                            <label for="autor" class="form-label">Autor</label>
                            <input type="text" class="form-control" id="autor" required value="${libro ? libro.autor.nombre : ''}">
                        </div>
                        <div class="mb-3">
                            <label for="genero" class="form-label">Género</label>
                            <select class="form-select" id="genero" required>
                                <option value="">Seleccione un género</option>
                                <option value="CIENCIA" ${libro && libro.genero === 'CIENCIA' ? 'selected' : ''}>Ciencia</option>
                                <option value="FICCION" ${libro && libro.genero === 'FICCION' ? 'selected' : ''}>Ficción</option>
                                <option value="NO_FICCION" ${libro && libro.genero === 'NO_FICCION' ? 'selected' : ''}>No Ficción</option>
                                <option value="HISTORIA" ${libro && libro.genero === 'HISTORIA' ? 'selected' : ''}>Historia</option>
                                <option value="OTROS" ${libro && libro.genero === 'OTROS' ? 'selected' : ''}>Otros</option>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-primary" onclick="guardarLibro()">Guardar</button>
                </div>
            </div>
        </div>
    `;

    document.body.appendChild(modal);
    const modalInstance = new bootstrap.Modal(modal);
    modalInstance.show();

    modal.addEventListener('hidden.bs.modal', function () {
        modal.remove();
    });
}

async function guardarLibro() {
    const libroId = document.getElementById('libroId').value;
    const autorNombre = document.getElementById('autor').value;

    try {
        // Primero, buscar o crear el autor
        const autorResponse = await fetch(`${API_URL}/autores/buscar-o-crear`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nombre: autorNombre })
        });

        if (!autorResponse.ok) {
            const errorData = await autorResponse.json();
            throw new Error(errorData.message || 'Error al procesar el autor');
        }

        const autorResponseData = await autorResponse.json();
        const autor = autorResponseData.data; // Extraer el autor del campo 'data' de ApiResponse

        // Luego, crear o actualizar el libro con el ID del autor
        const libro = {
            titulo: document.getElementById('titulo').value,
            descripcion: document.getElementById('descripcion').value,
            autor: {
                id: autor.id,
                nombre: autor.nombre
            },
            genero: document.getElementById('genero').value
        };

        const url = libroId 
            ? `${API_URL}/libro/${libroId}`
            : `${API_URL}/libro`;
            
        const method = libroId ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(libro)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || `Error ${response.status}: ${response.statusText}`);
        }

        // Cerrar el modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('libroModal'));
        modal.hide();

        // Recargar la lista de libros
        await cargarLibros();

    } catch (error) {
        console.error('Error completo:', error);
        manejarError(`Error al guardar el libro: ${error.message}`);
    }
}

async function editarLibro(id) {
    try {
        const response = await fetch(`${API_URL}/libro/${id}`);
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || `Error ${response.status}: ${response.statusText}`);
        }

        const responseData = await response.json();
        const libro = responseData.data; // Extraer el libro del campo 'data' de ApiResponse
        
        // Mostrar el formulario con los datos del libro
        mostrarFormularioLibro(libro);

    } catch (error) {
        console.error('Error completo:', error);
        manejarError(`Error al cargar el libro: ${error.message}`);
    }
} 