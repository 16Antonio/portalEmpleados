import { useState, useEffect } from 'react';
import { api } from '../services/api';

export default function Empleados() {
    const [empleados, setEmpleados] = useState([]); 
    const [roles, setRoles] = useState([]); // 🚀 1. Estado para guardar los roles posibles

    const [formulario, setFormulario] = useState({
        idEmpleado: null,
        dni: '',
        nombre: '',
        apellidos: '',
        puesto: '',
        disponible: true,
        observaciones: '',
        password: '',
        rolId: '' // 🚀 2. Añadimos el campo para guardar el ID del rol seleccionado
    });

    const cargarEmpleados = () => {
        api.obtenerEmpleados()
            .then(datos => setEmpleados(datos))
            .catch(error => console.error("Error al cargar los empleados:", error));
    };

    useEffect(() => {
        cargarEmpleados();
        // 🚀 3. Cargamos los roles al entrar a la pantalla
        api.obtenerRoles()
            .then(datos => setRoles(datos))
            .catch(error => console.error("Error al cargar roles:", error));
    }, []); 

    const manejarCambio = (e) => {
        const { name, value, type, checked } = e.target;
        setFormulario({
            ...formulario, 
            [name]: type === 'checkbox' ? checked : value 
        });
    };

    const guardarEmpleado = (e) => {
        e.preventDefault(); 

        // 🚀 4. TRUCO PRO: Transformamos el rolId simple en el objeto que Java espera { id: X }
        const datosAEnviar = {
            ...formulario,
            rol: formulario.rolId !== '' ? { id: formulario.rolId } : null
        };

        if (formulario.idEmpleado) {
            api.actualizarEmpleado(formulario.idEmpleado, datosAEnviar)
                .then(() => {
                    alert("¡Empleado actualizado con éxito!");
                    cargarEmpleados(); 
                    vaciarFormulario();
                })
                .catch(error => alert("Error al actualizar empleado: " + error.message));
        } else {
            api.crearEmpleado(datosAEnviar)
                .then(() => {
                    alert("¡Empleado guardado con éxito!");
                    cargarEmpleados();
                    vaciarFormulario();
                })
                .catch(error => alert("Error al guardar empleado: " + error.message));
        }
    };

    const vaciarFormulario = () => {
        setFormulario({ idEmpleado: null, dni: '', nombre: '', apellidos: '', puesto: '', disponible: true, observaciones: '', password: '', rolId: '' });
    };

    const prepararEdicion = (empleadoSeleccionado) => {
        setFormulario({
            idEmpleado: empleadoSeleccionado.idEmpleado,
            dni: empleadoSeleccionado.dni,
            nombre: empleadoSeleccionado.nombre,
            apellidos: empleadoSeleccionado.apellidos,
            puesto: empleadoSeleccionado.puesto,
            disponible: empleadoSeleccionado.disponible,
            observaciones: empleadoSeleccionado.observaciones || '',
            password: '', // Dejamos la contraseña en blanco por seguridad al editar
            rolId: empleadoSeleccionado.rol ? empleadoSeleccionado.rol.id : '' // 🚀 5. Cargamos el rol que tenía
        });
    };

    const borrarEmpleado = (id) => {
        if (window.confirm("¿Estás seguro de que quieres despedir a este empleado y borrar sus datos?")) {
            api.eliminarEmpleado(id)
                .then(() => {
                    alert("Empleado eliminado.");
                    cargarEmpleados(); 
                })
                .catch(error => alert("Error al eliminar: " + error.message));
        }
    };

    return (
        <div className="pantalla-empleados">
            <h2>👥 Gestión de Plantilla</h2>

            {/* FORMULARIO DE ALTA / EDICIÓN */}
            <form onSubmit={guardarEmpleado} className="formulario-caja">
                <h3>{formulario.idEmpleado ? "Editar Empleado" : "Alta de Nuevo Empleado"}</h3>
                
                <input type="text" name="dni" placeholder="DNI" value={formulario.dni} onChange={manejarCambio} required />
                <input type="text" name="nombre" placeholder="Nombre" value={formulario.nombre} onChange={manejarCambio} required />
                <input type="text" name="apellidos" placeholder="Apellidos" value={formulario.apellidos} onChange={manejarCambio} required />
                <input type="text" name="puesto" placeholder="Puesto (ej. Camarero)" value={formulario.puesto} onChange={manejarCambio} required />
                
                {/* 🚀 6. Solo pedimos contraseña si estamos CREANDO uno nuevo */}
                {!formulario.idEmpleado && (
                    <input type='password' name="password" placeholder="Contraseña" value={formulario.password} onChange={manejarCambio} required />
                )}

                {/* 🚀 7. NUEVO SELECTOR DE ROLES DINÁMICO */}
                <select name="rolId" value={formulario.rolId} onChange={manejarCambio} required>
                    <option value="" disabled>-- Selecciona un Rol --</option>
                    {roles.map(rol => (
                        <option key={rol.id} value={rol.id}>{rol.nombre}</option>
                    ))}
                </select>

                <label>
                    <input type="checkbox" name="disponible" checked={formulario.disponible} onChange={manejarCambio} />
                    ¿Está disponible para trabajar?
                </label>

                <textarea name="observaciones" placeholder="Observaciones..." value={formulario.observaciones} onChange={manejarCambio}></textarea>

                <button type="submit">{formulario.idEmpleado ? "Guardar Cambios" : "Guardar Nuevo Empleado"}</button>
                
                {/* Botón para cancelar la edición */}
                {formulario.idEmpleado && (
                    <button type="button" onClick={vaciarFormulario} style={{backgroundColor: '#7f8c8d', marginTop: '10px'}}>Cancelar</button>
                )}
            </form>

            {/* TABLA DE EMPLEADOS */}
            <h3>Plantilla Actual</h3>
            <table className="tabla-estilizada">
                <thead>
                    <tr>
                        <th>DNI</th>
                        <th>Nombre y Apellidos</th>
                        <th>Rol</th>
                        <th>Puesto</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {empleados.map(emp => (
                        <tr key={emp.idEmpleado}>
                            <td>{emp.dni}</td>
                            <td>{emp.nombre} {emp.apellidos}</td>
                            <td>{emp.rol ? emp.rol.nombre : "Sin Rol"}</td> {/* 🚀 Mostramos el rol en la tabla */}
                            <td>{emp.puesto}</td>
                            <td>{emp.disponible ? "✅ Activo" : "❌ Baja/Inactivo"}</td>
                            <td>
                                <button className="btn-editar" onClick={() => prepararEdicion(emp)}>✏️ Editar</button>
                                <button className="btn-borrar" onClick={() => borrarEmpleado(emp.idEmpleado)}>🗑️ Borrar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}