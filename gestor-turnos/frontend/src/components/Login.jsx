import { useState } from 'react';
import { api } from '../services/api';

export default function Login({ onLoginExitoso }) {
    const [credenciales, setCredenciales] = useState({ dni: '', password: '' });
    const [error, setError] = useState('');

    const manejarCambio = (e) => {
        setCredenciales({ ...credenciales, [e.target.name]: e.target.value });
    };

    const iniciarSesion = (e) => {
        e.preventDefault();
        setError('');

        api.login(credenciales)
            .then(respuesta => {
                // 1. ¡Éxito! Guardamos el token en la memoria del navegador
                localStorage.setItem('token_gestor', respuesta.token);
                
                // 2. Avisamos a App.jsx de que ya podemos entrar
                onLoginExitoso();
            })
            .catch(err => {
                setError(err.message);
            });
    };

    return (
        <div className="formulario-centrado" style={{ marginTop: '100px' }}>
            <form onSubmit={iniciarSesion} className="formulario-caja">
                <h2 style={{ textAlign: 'center', marginBottom: '20px' }}>🔐 Acceso al Sistema</h2>
                
                {error && <p style={{ color: '#e74c3c', textAlign: 'center' }}>{error}</p>}
                
                <label>DNI del Empleado:</label>
                <input type="text" name="dni" value={credenciales.dni} onChange={manejarCambio} required />
                
                <label>Contraseña:</label>
                <input type="password" name="password" value={credenciales.password} onChange={manejarCambio} required />
                
                <button type="submit" style={{ marginTop: '20px' }}>Entrar</button>
            </form>
        </div>
    );
}