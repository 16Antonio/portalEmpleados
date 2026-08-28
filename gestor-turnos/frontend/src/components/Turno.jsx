import { useState, useEffect } from 'react';
import { api } from '../services/api';

export default function TipoTurno (){
    const [tipoTurno, setTipoTurno] = useState([]);
    const [formulario, setFormulario] = useState({
        idTipoTurno: null,
        horaInicio: '',
        horaFin:  '',
        nombre: ''
    });

    const cargarTurnos = () => {
        api.obtenerTiposTurno()
            .then(datos => setTipoTurno(datos))
            .catch(error => console.error("Error al cargar los turnos:", error))
    }

    useEffect(()=> {
        cargarTurnos();
    }, [])

    return (
        <div className="pantalla-empleados">
            <h2>👥 Gestión de Plantilla</h2>

            {/* FORMULARIO DE ALTA 
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
            </form>*/}

            {/* TABLA DE EMPLEADOS */}
            <h3>Turnos Actual</h3>
            <table border="1" style={{ width: '100%', textAlign: 'left', marginTop: '20px' }}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Hora Inicio</th>
                        <th>Hora Fin</th>
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
                                <button >✏️ Editar</button>
                                <button  style={{ marginLeft: '10px', backgroundColor: '#ff4d4d', color: 'white' }}>🗑️ Borrar</button>
                            </td>
                        </tr>
                    ))}
                </tbody> 
            </table>
        </div>
    );
}
