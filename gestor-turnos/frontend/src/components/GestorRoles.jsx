import { useState, useEffect } from 'react';
import { api } from '../services/api';

export default function GestorRoles() {
    // Estados para guardar los datos del backend
    const [roles, setRoles] = useState([]);
    const [permisos, setPermisos] = useState([]);
    
    // Estados para el formulario de crear un rol nuevo
    const [nombreRol, setNombreRol] = useState('');
    const [permisosSeleccionados, setPermisosSeleccionados] = useState([]);

    // 1. Cargar datos al entrar a la pantalla
    useEffect(() => {
        api.obtenerRoles()
            .then(datos => setRoles(datos))
            .catch(error => console.error("Error cargando roles", error));
        
        api.obtenerPermisos()
            .then(datos => setPermisos(datos))
            .catch(error => console.error("Error cargando permisos", error));
        
    }, []);

    // 2. Manejar los checkboxes
    const manejarCheckbox = (permiso) => {
        if (permisosSeleccionados.some(p => p.id === permiso.id)) {
            // Si ya estaba marcado, lo quitamos
            setPermisosSeleccionados(permisosSeleccionados.filter(p => p.id !== permiso.id));
        } else {
            // Si no estaba marcado, lo añadimos
            setPermisosSeleccionados([...permisosSeleccionados, permiso]);
        }
    };

    // 3. Enviar el formulario
    const guardarRol = (e) => {
        e.preventDefault();
        const nuevoRol = {
            nombre: nombreRol.toUpperCase(), // Lo ponemos en mayúsculas por convención
            permisos: permisosSeleccionados
        };

        api.crearRol(nuevoRol)
            .then(() => {
                alert("Rol creado con éxito");
                setNombreRol('');
                setPermisosSeleccionados([]);
                api.obtenerRoles()
                    .then(datos => setRoles(datos))
                    .catch(error => console.error("Error al cargar los roles:", error));
            })
            .catch(error => alert("Error: " + error.message));
    };

    return (
        <div className="pantalla-empleados">
            <h2>🛡️ Gestor de Roles y Permisos</h2>
            
            {/* Formulario para crear un rol */}
            <form onSubmit={guardarRol} className="formulario-estilizado" style={{ marginBottom: '30px' }}>
                <input 
                    type="text" 
                    placeholder="Nombre del nuevo rol (ej: SUPERVISOR)" 
                    value={nombreRol}
                    onChange={(e) => setNombreRol(e.target.value)}
                    required
                />
                
                <h3>Selecciona sus permisos:</h3>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px', textAlign: 'left', margin: '15px 0' }}>
                    {permisos.map(permiso => (
                        <label key={permiso.id}>
                            <input 
                                type="checkbox"
                                checked={permisosSeleccionados.some(p => p.id === permiso.id)}
                                onChange={() => manejarCheckbox(permiso)}
                            /> 
                            {permiso.nombre}
                        </label>
                    ))}
                </div>

                <button type="submit" className="btn-guardar">💾 Guardar Rol</button>
            </form>

            {/* Tabla con los roles que ya existen */}
            <h3>Roles Existentes</h3>
            <table className="tabla-estilizada">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre del Rol</th>
                        <th>Nº de Permisos</th>
                    </tr>
                </thead>
                <tbody>
                    {roles.map(rol => (
                        <tr key={rol.id}>
                            <td>{rol.id}</td>
                            <td>{rol.nombre}</td>
                            <td>{rol.permisos ? rol.permisos.length : 0} permisos</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}