async function cargarPrestamos() {
    try {
        const response = await fetch(`${API_URL}/prestamos`);
        const prestamos = await response.json();
        mostrarPrestamos(prestamos);
    } catch (error) {
        manejarError(error);
    }
}

function mostrarPrestamos(prestamos) {
    const contenedor = document.getElementById('lista-prestamos');
    contenedor.innerHTML = '';

    prestamos.forEach(prestamo => {
        const card = document.createElement('div');
        card.className = 'col-md-4';
        card.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Préstamo #${prestamo.id}</h5>
                    <p class="card-text">Usuario: ${prestamo.usuario.nombre}</p>
                    <p class="card-text">Libro: ${prestamo.libro.titulo}</p>
                    <p class="card-text">Fecha Préstamo: ${prestamo.fechaPrestamo}</p>
                    <p class="card-text">Fecha Devolución: ${prestamo.fechaDevolucion}</p>
                </div>
            </div>
        `;
        contenedor.appendChild(card);
    });
}

async function mostrarFormularioPrestamo() {
    try {
        const [usuariosResponse, librosResponse] = await Promise.all([
            fetch(`${API_URL}/usuarios`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }),
            fetch(`${API_URL}/libro`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            })
        ]);
        
        if (!usuariosResponse.ok || !librosResponse.ok) {
            throw new Error('Error al cargar datos necesarios');
        }

        const usuariosData = await usuariosResponse.json();
        const librosData = await librosResponse.json();

        console.log('Respuesta usuarios:', usuariosData); // Para depuración
        console.log('Respuesta libros:', librosData);    // Para depuración

        // Extraer los datos del objeto ApiResponse
        const usuarios = usuariosData.data || usuariosData;
        const libros = librosData.data || librosData;

        console.log('Usuarios procesados:', usuarios); // Para depuración
        console.log('Libros procesados:', libros);    // Para depuración

        const modal = document.createElement('div');
        modal.innerHTML = `
            <div class="modal fade" id="prestamoModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Nuevo Préstamo</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <form id="prestamoForm">
                                <div class="mb-3">
                                    <label class="form-label">Usuario</label>
                                    <select class="form-select" id="usuarioPrestamo" required>
                                        <option value="">Seleccione un usuario</option>
                                        ${Array.isArray(usuarios) ? usuarios.map(u => 
                                            `<option value="${u.id}">${u.nombre}</option>`
                                        ).join('') : ''}
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Libro</label>
                                    <select class="form-select" id="libroPrestamo" required>
                                        <option value="">Seleccione un libro</option>
                                        ${Array.isArray(libros) ? libros.map(l => 
                                            `<option value="${l.id}">${l.titulo}</option>`
                                        ).join('') : ''}
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Fecha Devolución</label>
                                    <input type="date" class="form-control" id="fechaDevolucion" required 
                                           min="${new Date().toISOString().split('T')[0]}">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-primary" onclick="guardarPrestamo()">Guardar</button>
                        </div>
                    </div>
                </div>
            </div>
        `;
        document.body.appendChild(modal);
        const modalInstance = new bootstrap.Modal(document.getElementById('prestamoModal'));
        modalInstance.show();

        modal.addEventListener('hidden.bs.modal', function () {
            modal.remove();
        });
    } catch (error) {
        console.error('Error completo:', error);
        manejarError('Error al cargar el formulario de préstamo');
    }
}

async function guardarPrestamo() {
    // Obtener los valores de los campos
    const usuarioId = document.getElementById('usuarioPrestamo').value;
    const libroId = document.getElementById('libroPrestamo').value;
    const fechaDevolucion = document.getElementById('fechaDevolucion').value;

    // Validar que todos los campos requeridos estén presentes
    if (!usuarioId || !libroId || !fechaDevolucion) {
        manejarError('Todos los campos son requeridos');
        return;
    }

    const prestamo = {
        usuario: {
            id: parseInt(usuarioId)  // Convertir a número
        },
        libro: {
            id: parseInt(libroId)    // Convertir a número
        },
        fechaPrestamo: new Date().toISOString().split('T')[0],
        fechaDevolucion: fechaDevolucion,
        estado: 'PRESTADO'
    };

    console.log('Datos a enviar:', prestamo); // Para depuración

    try {
        const response = await fetch(`${API_URL}/prestamos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(prestamo)
        });

        const responseData = await response.json();
        
        if (!response.ok) {
            throw new Error(responseData.message || `Error ${response.status}: ${response.statusText}`);
        }

        console.log('Respuesta del servidor:', responseData); // Para depuración

        // Cerrar el modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('prestamoModal'));
        modal.hide();

        // Recargar la lista de préstamos
        await cargarPrestamos();

    } catch (error) {
        console.error('Error completo:', error);
        manejarError(`Error al guardar el préstamo: ${error.message}`);
    }
} 