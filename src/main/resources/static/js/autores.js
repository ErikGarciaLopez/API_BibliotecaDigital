async function cargarAutores() {
    try {
        const response = await fetch(`${API_URL}/autores`);
        const autores = await response.json();
        mostrarAutores(autores);
    } catch (error) {
        manejarError(error);
    }
}

function mostrarAutores(autores) {
    const contenedor = document.getElementById('lista-autores');
    contenedor.innerHTML = '';

    autores.forEach(autor => {
        const card = document.createElement('div');
        card.className = 'col-md-4';
        card.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">${autor.nombre}</h5>
                    <p class="card-text">Nacionalidad: ${autor.nacionalidad}</p>
                    <p class="card-text"><small class="text-muted">${autor.biografia}</small></p>
                    <button class="btn btn-primary btn-sm" onclick="editarAutor(${autor.id})">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="eliminarAutor(${autor.id})">Eliminar</button>
                </div>
            </div>
        `;
        contenedor.appendChild(card);
    });
}

async function eliminarAutor(id) {
    if (confirm('¿Estás seguro de que deseas eliminar este autor?')) {
        try {
            await fetch(`${API_URL}/autores/${id}`, {
                method: 'DELETE'
            });
            cargarAutores();
        } catch (error) {
            manejarError(error);
        }
    }
}

function mostrarFormularioAutor(autor = null) {
    const modal = document.createElement('div');
    modal.innerHTML = `
        <div class="modal fade" id="autorModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">${autor ? 'Editar' : 'Nuevo'} Autor</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <form id="autorForm">
                            <input type="hidden" id="autorId" value="${autor?.id || ''}">
                            <div class="mb-3">
                                <label class="form-label">Nombre</label>
                                <input type="text" class="form-control" id="nombreAutor" value="${autor?.nombre || ''}" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Nacionalidad</label>
                                <input type="text" class="form-control" id="nacionalidadAutor" value="${autor?.nacionalidad || ''}" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Biografía</label>
                                <textarea class="form-control" id="biografiaAutor" rows="3">${autor?.biografia || ''}</textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-primary" onclick="guardarAutor(${autor?.id || 'null'})">Guardar</button>
                    </div>
                </div>
            </div>
        </div>
    `;

    document.body.appendChild(modal);
    const modalInstance = new bootstrap.Modal(document.getElementById('autorModal'));
    modalInstance.show();

    modal.addEventListener('hidden.bs.modal', function () {
        modal.remove();
    });
}

async function guardarAutor(id = null) {
    const autor = {
        nombre: document.getElementById('nombreAutor').value,
        nacionalidad: document.getElementById('nacionalidadAutor').value,
        biografia: document.getElementById('biografiaAutor').value
    };

    try {
        const url = id ? `${API_URL}/autores/${id}` : `${API_URL}/autores`;
        const method = id ? 'PUT' : 'POST';
        
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(autor)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || `Error ${response.status}: ${response.statusText}`);
        }

        // Cerrar el modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('autorModal'));
        modal.hide();

        // Recargar la lista de autores
        await cargarAutores();

    } catch (error) {
        console.error('Error completo:', error);
        manejarError(`Error al guardar el autor: ${error.message}`);
    }
}

async function editarAutor(id) {
    try {
        const response = await fetch(`${API_URL}/autores/${id}`);
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || `Error ${response.status}: ${response.statusText}`);
        }

        const responseData = await response.json();
        const autor = responseData.data; // Extraer el autor del campo 'data' de ApiResponse
        
        console.log('Autor a editar:', autor); // Para depuración
        
        // Mostrar el formulario con los datos del autor
        mostrarFormularioAutor(autor);

    } catch (error) {
        console.error('Error completo:', error);
        manejarError(`Error al cargar el autor: ${error.message}`);
    }
} 