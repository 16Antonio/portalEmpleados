package com.gamez.gestor_turnos.dto;

import com.gamez.gestor_turnos.model.Empleado;
import com.gamez.gestor_turnos.model.Rol;

import lombok.Data;

@Data
public class EmpleadoResponseDTO {
    
    private Long idEmpleado;
    private String dni;
    private String nombre;
    private String apellidos;
    private String puesto;
    private boolean disponible;
    private Rol rol;

    // MAGIA: Un constructor que coge al empleado de la base de datos 
    // y copia solo lo que nos interesa al sobre.
    public EmpleadoResponseDTO(Empleado empleado) {
        this.idEmpleado = empleado.getIdEmpleado();
        this.dni = empleado.getDni();
        this.nombre = empleado.getNombre();
        this.apellidos = empleado.getApellidos();
        this.puesto = empleado.getPuesto();
        this.disponible = empleado.isDisponible();
        this.rol = empleado.getRol(); 
    }
}