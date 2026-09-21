import { useState} from 'react';
import './App.css';
import TipoTurno from './components/Turno';
import Empleados from './components/Empleados';
import AsignarTurno from './components/AsignarTurno';
import Cuadrantes from './components/Cuadrantes';
import Login from './components/Login';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import GestorRoles from './components/GestorRoles';

export default function App() {
  // 1. NUEVO ESTADO: ¿El usuario tiene la llave?
  const [estaAutenticado, setEstaAutenticado] = useState(() => {
    return localStorage.getItem('token_gestor') !== null;
  });
  const [permisos, setPermisos] = useState(() => {
    const token = localStorage.getItem('token_gestor');
    if (token) {
      const tokenDecodificado = jwtDecode(token);
      return tokenDecodificado.permisos || [];
    }
    return []; // Si no hay token, no hay permisos
  });
 
  // 3. Función para destruir la llave y salir
  const cerrarSesion = () => {
    localStorage.removeItem('token_gestor'); // Borramos el token
    setEstaAutenticado(false); // Volvemos a bloquear la puerta
  };

  // 4. EL CANDADO: Si no está autenticado, SOLO mostramos el Login
  if (!estaAutenticado) {
    return <Login onLoginExitoso={() => {
      setEstaAutenticado(true); 
      const tokenGuardado = localStorage.getItem('token_gestor');
      if (tokenGuardado) {
        const tokenDecodificado = jwtDecode(tokenGuardado);
        setPermisos(tokenDecodificado.permisos || []);
      }
      
    }} />;
  }

  return ( 
    <BrowserRouter>
      <main className="app-container">
        <header className="navbar">
          <h1>🍽️ Gestor de Turnos</h1>
          <nav>

            {permisos.includes('VER_CUADRANTES') && (
              <Link to="/">📅 Ver Cuadrante</Link>
            )}

            {permisos.includes('ASIGNAR_TURNO') && (
              <Link to="/asignar">➕ Asignar Turno</Link>
            )}

            {permisos.includes('VER_EMPLEADOS') && (
              <Link to="/empleados">👥 Empleados</Link>
            )}

            {permisos.includes('VER_TIPOS_TURNO') && (
              <Link to="/turnos">⏰ Tipos de turno</Link>
            )}

            {permisos.includes('CREAR_EMPLEADO') && ( 
              <Link to="/roles"> 🛡️ Roles </Link>
            )}
            <button onClick={cerrarSesion} style={{ backgroundColor: '#c0392b', marginLeft: '15px' }}>
              🚪 Salir
            </button>
          </nav>
        </header>

        <section className="contenido">
          <Routes>
            <Route path="/" element={<Cuadrantes />} />
            <Route path="/asignar" element={<AsignarTurno />} />
            <Route path="/empleados" element={<Empleados />} />
            <Route path="/turnos" element={<TipoTurno />} />
            <Route path="/roles" element={<GestorRoles />}/>
          </Routes>
        </section>
      </main>
    </BrowserRouter>
  );
}