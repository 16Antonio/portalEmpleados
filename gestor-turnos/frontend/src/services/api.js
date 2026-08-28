const URL_BASE = 'http://localhost:8080/api/v1';

export const api = {
    // 1. Empleados
    obtenerEmpleados: () => fetch(`${URL_BASE}/empleados`).then(res => res.json()),
    crearEmpleado: (datos) => fetch(`${URL_BASE}/empleados`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(datos)
    }).then(res => res.json()),

    // 2. Tipos de Turno
    obtenerTiposTurno: () => fetch(`${URL_BASE}/turnos`).then(res => res.json()),

    // 3. Cuadrantes (Asignaciones)
    obtenerCuadrantes: () => fetch(`${URL_BASE}/cuadrantes`).then(res => res.json()),
    asignarTurno: (datos) => fetch(`${URL_BASE}/cuadrantes`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(datos)
    }).then(async res => {
        if (!res.ok) {
            const error = await res.json();
            throw new Error(error.error || 'Error al asignar turno');
        }
        return res.json();
    }),

    actualizarEmpleado: (id, datos) => fetch(`${URL_BASE}/empleados/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(datos)
    }).then(res => res.json()),
};