async function cargarUsuarios() {
    try {
        const response = await fetch(`${API_URL}/usuarios`);
        const usuarios = await response.json();
        mostrarUsuarios(usuarios);
    } catch (error) {
        manejarError(error);
    }
}

function mostrarUsuarios(usuarios) {
    const contenedor = document.getElementById('lista-usuarios');
    contenedor.innerHTML = '';

    usuarios.forEach(usuario => {
        const card = document.createElement('div');
        card.className = 'col-md-4';
        card.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">${usuario.nombre}</h5>
                    <p class="card-text">Email: ${usuario.email}</p>
                </div>
            </div>
        `;
        contenedor.appendChild(card);
    });
}

function mostrarFormularioUsuario() {
    const modal = document.createElement('div');
    modal.innerHTML = `
        <div class="modal fade" id="usuarioModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Nuevo Usuario</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <form id="usuarioForm">
                            <div class="mb-3">
                                <label class="form-label">Nombre</label>
                                <input type="text" class="form-control" id="nombreUsuario" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Email</label>
                                <input type="email" class="form-control" id="emailUsuario" required>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-primary" onclick="guardarUsuario()">Guardar</button>
                    </div>
                </div>
            </div>
        </div>
    `;
    document.body.appendChild(modal);
    const modalInstance = new bootstrap.Modal(document.getElementById('usuarioModal'));
    modalInstance.show();
}

async function guardarUsuario() {
    const usuario = {
        nombre: document.getElementById('nombreUsuario').value,
        email: document.getElementById('emailUsuario').value
    };

    try {
        const response = await fetch(`${API_URL}/usuarios`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(usuario)
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(document.getElementById('usuarioModal'));
            modal.hide();
            cargarUsuarios();
        }
    } catch (error) {
        manejarError(error);
    }
} 