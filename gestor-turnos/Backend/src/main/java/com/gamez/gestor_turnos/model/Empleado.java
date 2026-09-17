package com.gamez.gestor_turnos.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // 1. Le dice a Spring: "¡Oye, convierte esta clase en una tabla de MySQL!"
@Table(name = "empleados") // Opcional: Para forzar que la tabla se llame en plural
@Getter
@Setter
@NoArgsConstructor
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


    // 1. Relación con el Rol principal (Muchos empleados pueden tener el mismo Rol)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;

    // 2. Permisos específicos solo para este usuario (La excepción a la regla)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "empleados_permisos_extra",
        joinColumns = @JoinColumn(name = "empleado_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    private Set<Permiso> permisosExtra = new HashSet<>();


    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // 1. Extraemos los permisos que vienen heredados por el Rol
        if (this.rol != null && this.rol.getPermisos() != null) {
            for (Permiso permiso : this.rol.getPermisos()) {
                authorities.add(new SimpleGrantedAuthority(permiso.getNombre()));
            }
            // Opcional: Añadimos también el nombre del rol por si lo necesitas usar directamente
            authorities.add(new SimpleGrantedAuthority("ROLE_" + this.rol.getNombre()));
        }

        // 2. Sumamos los permisos extra específicos de este trabajador
        if (this.permisosExtra != null) {
            for (Permiso permiso : this.permisosExtra) {
                authorities.add(new SimpleGrantedAuthority(permiso.getNombre()));
            }
        }

        return authorities;
    }

    
}