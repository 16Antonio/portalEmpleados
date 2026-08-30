package com.gamez.gestor_turnos.model; // Ajusta esto si tu ruta es diferente

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // 1. Le dice a Spring: "¡Oye, convierte esta clase en una tabla de MySQL!"
@Table(name = "empleados") // Opcional: Para forzar que la tabla se llame en plural
public class Empleado {

    @Id // 2. Le indica que esta variable es la Clave Primaria (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 3. Le dice que sea Autoincrementalº
    private Long idEmpleado;

    @Column(unique = true, nullable = false) // 4. Restricciones: DNI único y obligatorio
    private String dni;

    private String nombre;
    
    private String apellidos;
    
    private String puesto;

    private boolean disponible; // Spring Boot convertirá esto en un TINYINT (0 o 1) en SQL

    private String observaciones;

    private String password;

    private String rol;

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idEmpleado == null) ? 0 : idEmpleado.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Empleado other = (Empleado) obj;
        if (idEmpleado == null) {
            if (other.idEmpleado != null)
                return false;
        } else if (!idEmpleado.equals(other.idEmpleado))
            return false;
        return true;
    }

    

    
}