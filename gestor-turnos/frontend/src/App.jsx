import { useState, useEffect } from 'react'
import './App.css'

export default function App() {
  // Aquí guardaremos los empleados que vengan de la base de datos
  const [empleados, setEmpleados] = useState([]);

  // useEffect hace que la llamada a la API se ejecute solo una vez al cargar la web
  useEffect(() => {
    fetch('http://localhost:8080/api/v1/empleados')
      .then(respuesta => respuesta.json()) // Traducimos la respuesta a JSON
      .then(datos => {
        console.log("Datos recibidos de Java:", datos);
        setEmpleados(datos); // Guardamos los datos en el estado
      })
      .catch(error => console.error("Error al conectar con Java:", error));
  }, []);

  return (
    <main>
      <h1>Cuadrante de Turnos</h1>
      <h2>Plantilla de Empleados</h2>
      
      <div className="lista-empleados">
        {empleados.length === 0 ? (
          <p>Cargando empleados...</p>
        ) : (
          empleados.map(empleado => (
            <div key={empleado.idEmpleado} style={{ border: '1px solid #ccc', margin: '10px', padding: '10px', borderRadius: '8px' }}>
              <h3>{empleado.nombre} {empleado.apellidos}</h3>
              <p><strong>Puesto:</strong> {empleado.puesto}</p>
              <p><strong>Estado:</strong> {empleado.disponible ? "🟢 Disponible" : "🔴 No Disponible"}</p>
              <p><em>{empleado.observaciones}</em></p>
            </div>
          ))
        )}
      </div>
    </main>
  )
}