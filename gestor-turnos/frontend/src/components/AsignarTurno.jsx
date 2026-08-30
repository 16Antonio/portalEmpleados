import { useState, useEffect } from 'react';
import { api } from '../services/api';

export default function AsignarTurno() {
    const [empleados, setEmpleados] = useState([]);
    const [tipoTurno, setTipoTurno] = useState([]);
    
    // 1. Nuestra libreta guarda los 3 datos clave
    const [formulario, setFormulario] = useState({
        fecha: '',
        idEmpleado: '',
        idTipoTurno: ''
    });

    useEffect(() => {
        api.obtenerTiposTurno().then(datos => setTipoTurno(datos));
        api.obtenerEmpleados().then(datos => setEmpleados(datos));
    }, []);

    const manejarCambio = (e) => {
        const { name, value } = e.target;
        setFormulario({
            ...formulario,
            [name]: value
        });
    };

    // 2. Función para enviar los datos a Java
    const guardarAsignacion = (e) => {
        e.preventDefault();

        // 3. Transformamos la libreta al formato exacto que espera Java (como en Postman)
        const paqueteParaJava = {
            fecha: formulario.fecha,
            empleado: { idEmpleado: formulario.idEmpleado },
            tipoTurno: { idTipoTurno: formulario.idTipoTurno }
        };

        api.asignarTurno(paqueteParaJava)
            .then(() => {
                alert("¡Turno asignado correctamente!");
                setFormulario({ fecha: '', idEmpleado: '', idTipoTurno: '' }); // Limpiamos
            })
            .catch(error => {
                // AQUÍ ATRAPAMOS EL ERROR 400 DE SOLAPAMIENTO DE TU SERVICE
                alert("⛔ " + error.message);
            });
    };

    return (
        <section className="pantalla-empleados">
            <h2>➕ Asignar Nuevo Turno</h2>

            <form onSubmit={guardarAsignacion} className="formulario-caja formulario-centrado">
                
                <label>Fecha del Turno:</label>
                <input 
                    type='date' 
                    name="fecha" 
                    value={formulario.fecha} 
                    onChange={manejarCambio} 
                    required 
                />

                <label>Seleccionar Empleado:</label>
                <select name="idEmpleado" value={formulario.idEmpleado} onChange={manejarCambio} required>
                    <option value="">-- Elige un empleado --</option>
                    {/* Filtramos para que solo salgan los disponibles y creamos las opciones */}
                    {empleados.filter(emp => emp.disponible).map(emp => (
                        <option key={emp.idEmpleado} value={emp.idEmpleado}>
                            {emp.nombre} {emp.apellidos} ({emp.puesto})
                        </option>
                    ))}
                </select>

                <label>Seleccionar Horario:</label>
                <select name="idTipoTurno" value={formulario.idTipoTurno} onChange={manejarCambio} required>
                    <option value="">-- Elige un turno --</option>
                    {tipoTurno.map(tur => (
                        <option key={tur.idTipoTurno} value={tur.idTipoTurno}>
                            {tur.nombre} ({tur.horaInicio} a {tur.horaFin})
                        </option>
                    ))}
                </select>

                <button type="submit" style={{ marginTop: '20px' }}>Crear Asignación</button>
            </form>
        </section>
    );
}