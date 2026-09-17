import { useState, useEffect } from 'react';
import { api } from '../services/api';

export default function Cuadrantes ( ){
    const [cuadrantes, setCuadrantes] = useState([]);


    const cargarCuadrantes = () =>  {
        api.obtenerCuadrantes()
            .then(datos => setCuadrantes(datos))
            .catch(error => console.error("Error al cargar los turnos:", error))
    }

    useEffect( () =>{
        cargarCuadrantes();

    },[]);

    const eliminarCuadrante = (turnoId) => {
        if (window.confirm("¿Estás seguro de que quieres borrar este turno?")) {
            api.eliminarCuadrante(turnoId)
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
                            <td>{cua.empleado.nombre} {cua.empleado.apellidos}</td>
                            <td>{cua.tipoTurno.nombre}</td>
                            <td><button className="btn-borrar" onClick={() => eliminarCuadrante(cua.idCuadrante)}>🗑️ Borrar</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}