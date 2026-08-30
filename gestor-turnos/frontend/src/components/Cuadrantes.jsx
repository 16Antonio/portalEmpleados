import { useState, useEffect } from 'react';
import { api } from '../services/api';

export default function Cuadrantes ( ){
    const [cuadrantes, setCuadrnates] = useState([]);
    const [empleados, setEmpleados] = useState([]);
    const [tipoTurno, setTipoTurno] = useState([]);

    const cargarTurnos = () => {
        api.obtenerTiposTurno()
            .then(datos => setTipoTurno(datos))
            .catch(error => console.error("Error al cargar los turnos:", error))
    };

    const cargarEmpleados = () => {
        api.obtenerEmpleados()
            .then(datos => setEmpleados(datos))
            .catch(error => console.error("Error al cargar los empleados:", error));
    };

    const cargarCuadrantes = () =>  {
        api.obtenerCuadrantes()
            .then(datos => setCuadrnates(datos))
            .catch(error => console.error("Error al cargar los turnos:", error))
    }

    useEffect( () =>{
        cargarCuadrantes();
        cargarTurnos();
        cargarEmpleados();
    },[]);

    const getNombreEmpleado = (empleadoId) => {
        const empleado = empleados.find(emp => emp.idEmpleado === empleadoId);
        // Si lo encuentra, devuelve el nombre. Si no (o si aún está cargando), muestra 'Cargando...'
        return empleado ? `${empleado.nombre} ${empleado.apellidos}` : 'Cargando...';
    };

    const getNombreTurno = (turnoId) => {
        const turno = tipoTurno.find(tur => tur.idTipoTurno === turnoId);
        return turno ? turno.nombre : 'Cargando...';
    };

    const borrarTurno = (turnoId) => {
        if (window.confirm("¿Estás seguro de que quieres borrar este turno?")) {
            api.eliminarCuadrnate(turnoId)
                .then(() => {
                    alert("Turno eliminado.");
                    cargarCuadrantes(); // Recargamos la tabla para que desaparezca visualmente
                })
                .catch(error => alert("Error al eliminar el turno: " + error.message));
        }
    }

    return (

        <div className="pantalla-empleados">
            <h2>📅 Cuadrante Semanal</h2>
            <table className="tabla-estilizada">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Fecha</th>
                        <th>Empleado</th>
                        <th>Turno</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {cuadrantes.map(cua => (
                        
                        <tr key={cua.idCuadrante}>
                            <td>{cua.idCuadrante}</td>
                            <td>{cua.fecha}</td>
                            <td>{getNombreEmpleado(cua.empleado.idEmpleado)} </td>
                            <td>{getNombreTurno(cua.tipoTurno.idTipoTurno)}</td>
                            <td><button className="btn-borrar" onClick={() => borrarTurno(cua.idCuadrante)}>🗑️ Borrar</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}