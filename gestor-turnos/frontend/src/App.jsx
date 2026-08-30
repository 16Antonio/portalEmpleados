import { useState } from 'react'
import './App.css'
import TipoTurno from './components/Turno';
import Empleados from './components/Empleados' 
import AsignarTurno from './components/AsignarTurno'
import Cuadrantes from './components/Cuadrantes';

export default function App() {
  const [vistaActual, setVistaActual] = useState('empleados'); // <-- Lo ponemos por defecto para verlo rápido

  return (
    <main className="app-container">
      <header className="navbar">
        <h1>🍽️ Gestor de Turnos</h1>
        <nav>
          <button onClick={() => setVistaActual('cuadrante')}>📅 Ver Cuadrante</button>
          <button onClick={() => setVistaActual('asignar')}>➕ Asignar Turno</button>
          <button onClick={() => setVistaActual('empleados')}>👥 Empleados</button>
          <button onClick={() => setVistaActual('turnos')}>⏰ Tipos de Turno</button>
        </nav>
      </header>

      <section className="contenido">
        {vistaActual === 'cuadrante' && <Cuadrantes />}
        {vistaActual === 'asignar' && <AsignarTurno />}
        
        {/* 2. Aquí inyectamos tu componente real */}
        {vistaActual === 'empleados' && <Empleados />} 
        
        {vistaActual === 'turnos' && <TipoTurno />}
      </section>
    </main>
  )
}