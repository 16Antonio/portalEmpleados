import { useState, useEffect } from 'react';
import './App.css';
import TipoTurno from './components/Turno'; // Asegúrate de que la ruta coincida con el nombre de tu archivo
import Empleados from './components/Empleados'; 
import AsignarTurno from './components/AsignarTurno';
import Cuadrantes from './components/Cuadrantes';
import Login from './components/Login'; // 👈 Importamos la nueva pantalla

export default function App() {
  // 1. NUEVO ESTADO: ¿El usuario tiene la llave?
  const [estaAutenticado, setEstaAutenticado] = useState(false);
  const [vistaActual, setVistaActual] = useState('cuadrante'); 

  // 2. Comprobar al abrir la web si ya nos habíamos logueado antes
  useEffect(() => {
    const token = localStorage.getItem('token_gestor');
    if (token) {
      // eslint-disable-next-line react-hooks/set-state-in-effect
      setEstaAutenticado(true);
    }
  }, []);

  // 3. Función para destruir la llave y salir
  const cerrarSesion = () => {
    localStorage.removeItem('token_gestor'); // Borramos el token
    setEstaAutenticado(false); // Volvemos a bloquear la puerta
  };

  // 4. EL CANDADO: Si no está autenticado, SOLO mostramos el Login
  if (!estaAutenticado) {
    return <Login onLoginExitoso={() => setEstaAutenticado(true)} />;
  }

  // 5. Si pasa el candado, mostramos el restaurante entero
  return (
    <main className="app-container">
      <header className="navbar">
        <h1>🍽️ Gestor de Turnos</h1>
        <nav>
          <button onClick={() => setVistaActual('cuadrante')}>📅 Ver Cuadrante</button>
          <button onClick={() => setVistaActual('asignar')}>➕ Asignar Turno</button>
          <button onClick={() => setVistaActual('empleados')}>👥 Empleados</button>
          <button onClick={() => setVistaActual('turnos')}>⏰ Tipos de Turno</button>
          
          {/* NUEVO BOTÓN: Cerrar Sesión */}
          <button 
            onClick={cerrarSesion} 
            style={{ backgroundColor: '#c0392b', marginLeft: '15px' }}
          >
            🚪 Salir
          </button>
        </nav>
      </header>

      <section className="contenido">
        {vistaActual === 'cuadrante' && <Cuadrantes />}
        {vistaActual === 'asignar' && <AsignarTurno />}
        {vistaActual === 'empleados' && <Empleados />} 
        {vistaActual === 'turnos' && <TipoTurno />}
      </section>
    </main>
  );
}