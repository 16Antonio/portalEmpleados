import { useState, useEffect } from 'react';
import { api } from '../services/api';

export default function Empleados() {
    // 1. NUESTRA LIBRETA DE NOTAS (Estados)
    const [empleados, setEmpleados] = useState([]); // Lista de trabajadores
    const [formulario, setFormulario] = useState({
        idEmpleado: null,
        dni: '',
        nombre: '',
        apellidos: '',
        puesto: '',
        disponible: true,
        observaciones: ''
    });

    const cargarEmpleados = () => {
        api.obtenerEmpleados()
            .then(datos => setEmpleados(datos))
            .catch(error => console.error("Error al cargar:", error));
    };

    // 2. LA PREPARACIÓN (Cargar datos al entrar)
    useEffect(() => {
        cargarEmpleados();
    }, []); // Los corchetes vacíos significan: "Haz esto solo una vez al abrir"



    // 3. MANEJAR EL FORMULARIO (Cuando escribes en los inputs)
    const manejarCambio = (e) => {
        const { name, value, type, checked } = e.target;
        setFormulario({
            ...formulario, // Copiamos lo que ya había
            [name]: type === 'checkbox' ? checked : value // Actualizamos solo el campo que ha cambiado
        });
    };

    // 4. GUARDAR EN JAVA (Al darle al botón)
    const guardarEmpleado = (e) => {
        e.preventDefault(); // Evita que la página se recargue

        // ¿Tenemos un ID en la libreta? Entonces estamos EDITANDO
        if (formulario.idEmpleado) {
            api.actualizarEmpleado(formulario.idEmpleado, formulario)
                .then(() => {
                    alert("¡Empleado actualizado con éxito!");
                    cargarEmpleados(); // Refrescamos la tabla
                    // Vaciamos la libreta y volvemos al modo "Crear"
                    setFormulario({ idEmpleado: null, dni: '', nombre: '', apellidos: '', puesto: '', disponible: true, observaciones: '' });
                })
                .catch(error => alert("Error al actualizar: " + error.message));

        } else {
            // Si NO hay ID, entonces estamos CREANDO uno nuevo (lo que ya tenías)
            api.crearEmpleado(formulario)
                .then(() => {
                    alert("¡Empleado guardado con éxito!");
                    cargarEmpleados();
                    setFormulario({ idEmpleado: null, dni: '', nombre: '', apellidos: '', puesto: '', disponible: true, observaciones: '' });
                })
                .catch(error => alert("Error al guardar: " + error.message));
        }
    };

    const prepararEdicion = (empleadoSeleccionado) => {
        // Sobrescribimos el formulario con los datos del empleado que hemos clicado
        setFormulario({
            idEmpleado: empleadoSeleccionado.idEmpleado, // ¡Importante añadir esto al estado inicial también!
            dni: empleadoSeleccionado.dni,
            nombre: empleadoSeleccionado.nombre,
            apellidos: empleadoSeleccionado.apellidos,
            puesto: empleadoSeleccionado.puesto,
            disponible: empleadoSeleccionado.disponible,
            observaciones: empleadoSeleccionado.observaciones || '' // Por si las observaciones vienen en null desde Java
        });
    };

    const borrarEmpleado = (id) => {
        if (window.confirm("¿Estás seguro de que quieres despedir a este empleado y borrar sus datos?")) {
            api.eliminarEmpleado(id)
                .then(() => {
                    alert("Empleado eliminado.");
                    cargarEmpleados(); // Recargamos la tabla para que desaparezca visualmente
                })
                .catch(error => alert("Error al eliminar: " + error.message));
        }
    }

    return (
        <div className="pantalla-empleados">
            <h2>👥 Gestión de Plantilla</h2>

            {/* FORMULARIO DE ALTA */}
            <form onSubmit={guardarEmpleado} className="formulario-caja">
                <h3>Alta de Nuevo Empleado</h3>
                <input type="text" name="dni" placeholder="DNI" value={formulario.dni} onChange={manejarCambio} required />
                <input type="text" name="nombre" placeholder="Nombre" value={formulario.nombre} onChange={manejarCambio} required />
                <input type="text" name="apellidos" placeholder="Apellidos" value={formulario.apellidos} onChange={manejarCambio} required />
                <input type="text" name="puesto" placeholder="Puesto (ej. Camarero)" value={formulario.puesto} onChange={manejarCambio} required />

                <label>
                    <input type="checkbox" name="disponible" checked={formulario.disponible} onChange={manejarCambio} />
                    ¿Está disponible para trabajar?
                </label>

                <textarea name="observaciones" placeholder="Observaciones..." value={formulario.observaciones} onChange={manejarCambio}></textarea>

                <button type="submit">Guardar Empleado</button>
            </form>

            {/* TABLA DE EMPLEADOS */}
            <h3>Plantilla Actual</h3>
            <table border="1" style={{ width: '100%', textAlign: 'left', marginTop: '20px' }}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>DNI</th>
                        <th>Nombre y Apellidos</th>
                        <th>Puesto</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {empleados.map(emp => (
                        <tr key={emp.idEmpleado}>
                            <td>{emp.idEmpleado}</td>
                            <td>{emp.dni}</td>
                            <td>{emp.nombre} {emp.apellidos}</td>
                            <td>{emp.puesto}</td>
                            <td>{emp.disponible ? "✅ Activo" : "❌ Baja/Inactivo"}</td>
                            <td>
                                <button onClick={() => prepararEdicion(emp)}>✏️ Editar</button>
                                <button onClick={() => borrarEmpleado(emp.idEmpleado)} style={{ marginLeft: '10px', backgroundColor: '#ff4d4d', color: 'white' }}>🗑️ Borrar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}