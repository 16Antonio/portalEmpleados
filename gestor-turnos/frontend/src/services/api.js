const URL_BASE = 'http://localhost:8080/api/v1';

// 💡 Esta función busca el token y prepara la cabecera
const getAuthHeaders = () => {
    const token = localStorage.getItem('token_gestor'); 
    return {
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : '' 
    };
};

export const api = {
    // ==========================================
    // 1. EMPLEADOS
    // ==========================================
    obtenerEmpleados: () => fetch(`${URL_BASE}/empleados`, { 
        headers: getAuthHeaders() // 👈 ¡Enseñamos la pulsera!
    }).then(res => res.json()),

    crearEmpleado: (datos) => fetch(`${URL_BASE}/empleados`, {
        method: 'POST',
        headers: getAuthHeaders(), // 👈 ¡Enseñamos la pulsera!
        body: JSON.stringify(datos)
    }).then(res => res.json()),

    actualizarEmpleado: (id, datos) => fetch(`${URL_BASE}/empleados/${id}`, {
        method: 'PUT',
        headers: getAuthHeaders(),
        body: JSON.stringify(datos)
    }).then(res => res.json()),

    eliminarEmpleado: (id) => fetch(`${URL_BASE}/empleados/${id}`, { 
        method: 'DELETE',
        headers: getAuthHeaders() 
    }),

    // ==========================================
    // 2. TIPOS DE TURNO
    // ==========================================
    obtenerTiposTurno: () => fetch(`${URL_BASE}/turnos`, { 
        headers: getAuthHeaders() 
    }).then(res => res.json()),

    crearTipoTurno: (datos) => fetch(`${URL_BASE}/turnos`,{
        method: 'POST',
        headers: getAuthHeaders(),
        body: JSON.stringify(datos)
    }).then(res => res.json()),

    actualizarTipoTurno: (id, datos) => fetch(`${URL_BASE}/turnos/${id}`, {
        method: 'PUT',
        headers: getAuthHeaders(),
        body: JSON.stringify(datos)
    }).then(res => res.json()),

    eliminarTipoTurno: (id) => fetch(`${URL_BASE}/turnos/${id}`, { 
        method: 'DELETE',
        headers: getAuthHeaders() 
    }),

    // ==========================================
    // 3. CUADRANTES (Asignaciones)
    // ==========================================
    obtenerCuadrantes: () => fetch(`${URL_BASE}/cuadrantes`, { 
        headers: getAuthHeaders() 
    }).then(res => res.json()),

    asignarTurno: (datos) => fetch(`${URL_BASE}/cuadrantes`, {
        method: 'POST',
        headers: getAuthHeaders(),
        body: JSON.stringify(datos)
    }).then(async res => {
        if (!res.ok) {
            const error = await res.json();
            throw new Error(error.error || 'Error al asignar turno');
        }
        return res.json();
    }),

    actualizarCuadrante: (id, datos) => fetch(`${URL_BASE}/cuadrantes/${id}`, {
        method: 'PUT',
        headers: getAuthHeaders(),
        body: JSON.stringify(datos)
    }).then(res => res.json()),

    eliminarCuadrante: (id) => fetch(`${URL_BASE}/cuadrantes/${id}`, { // 👈 Corregido el nombre
        method: 'DELETE',
        headers: getAuthHeaders()
    }),

    // ==========================================
    // 4. AUTENTICACIÓN (LOGIN)
    // ==========================================
    login: (credenciales) => fetch(`${URL_BASE}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' }, // 👈 Aquí NO enviamos token, venimos a pedirlo
        body: JSON.stringify(credenciales)
    }).then(async res => {
        if (!res.ok) throw new Error("DNI o contraseña incorrectos");
        return res.json();
    }),
};