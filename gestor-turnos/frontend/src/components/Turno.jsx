import { useState, useEffect } from 'react';
import { api } from '../services/api';

export default function TipoTurno() {
    const [tipoTurno, setTipoTurno] = useState([]);
    const [formulario, setFormulario] = useState({
        idTipoTurno: null,
        horaInicio: '',
        horaFin: '',
        nombre: ''
    });

    const cargarTurnos = () => {
        api.obtenerTiposTurno()
            .then(datos => setTipoTurno(datos))
            .catch(error => console.error("Error al cargar los turnos:", error))
    };

    useEffect(() => {
        cargarTurnos();
    }, []);

    const manejarCambio = (e) => {
        const { name, value, type, checked } = e.target;
        setFormulario({
            ...formulario, // Copiamos lo que ya había
            [name]: type === 'checkbox' ? checked : value // Actualizamos solo el campo que ha cambiado
        });
    };

    const guardarTipoTurno = (e) => {
        e.preventDefault();

        if (formulario.idTipoTurno) {
            api.actualizarTipoTurno(formulario.idTipoTurno, formulario)
                .then(() => {
                    alert("Turno actualizado con éxito!");
                    cargarTurnos(); // Refrescamos la tabla
                    // Vaciamos la libreta y volvemos al modo "Crear"
                    setFormulario({ idTipoTurno: null, horaInicio: '', horaFin: '', nombre: '' });
                })
                .catch(error => alert("Error al actualizar turno: " + error.message));

        } else {
            // Si NO hay ID, entonces estamos CREANDO uno nuevo (lo que ya tenías)
            api.crearTipoTurno(formulario)
                .then(() => {
                    alert("Turno guardado con éxito!");
                    cargarTurnos();
                    setFormulario({ idTipoTurno: null, horaInicio: '', horaFin: '', nombre: '' });
                })
                .catch(error => alert("Error al guardar turno: " + error.message));
        }
    };

    const prepararEdicion = (turnoSeleccionado) => {
        // Sobrescribimos el formulario con los datos del empleado que hemos clicado
        setFormulario({
            idTipoTurno: turnoSeleccionado.idTipoTurno, // ¡Importante añadir esto al estado inicial también!
            horaInicio: turnoSeleccionado.horaInicio,
            horaFin: turnoSeleccionado.horaFin,
            nombre: turnoSeleccionado.nombre,

        });
    };

    const borrarTipoTurno = (id) => {
        if (window.confirm("¿Estás seguro de que quieres borrar este turno?")) {
            api.eliminarTipoTurno(id)
                .then(() => {
                    alert("Turno eliminado.");
                    cargarTurnos(); // Recargamos la tabla para que desaparezca visualmente
                })
                .catch(error => alert("Error al eliminar el turno: " + error.message));
        }
    }

    return (
        <div className="pantalla-empleados">
            <h2>📅 Gestión de Turnos</h2>

            {/* FORMULARIO DE ALTA */}
            <form onSubmit={guardarTipoTurno} className="formulario-caja">
                <h3>Alta de Nuevo Turno</h3>
                <input type="text" name="nombre" placeholder="Nombre del turno" value={formulario.nombre} onChange={manejarCambio} required />
                <input type="time" step="1" name="horaInicio" placeholder="Hora de Inicio" value={formulario.horaInicio} onChange={manejarCambio} required />
                <input type="time" step="1" name="horaFin" placeholder="Hora Fin" value={formulario.horaFin} onChange={manejarCambio} required />



                <button type="submit">Guardar Turno</button>
            </form>

            {/* TABLA DE EMPLEADOS */}
            <h3>Turnos Actual</h3>
            <table className="tabla-estilizada">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Hora Inicio</th>
                        <th>Hora Fin</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {tipoTurno.map(tur => (
                        <tr key={tur.idTipoTurno}>
                            <td>{tur.idTipoTurno}</td>
                            <td>{tur.nombre}</td>
                            <td>{tur.horaInicio}</td>
                            <td>{tur.horaFin}</td>
                            <td>
                                <button className="btn-editar" onClick={() => prepararEdicion(tur)}>✏️ Editar</button>
                                <button className="btn-borrar" onClick={() => borrarTipoTurno(tur.idEmpleado)}>🗑️ Borrar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
