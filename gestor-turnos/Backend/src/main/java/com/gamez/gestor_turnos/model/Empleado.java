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
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "empleados") 
@Getter
@Setter
@NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    @NotBlank(message = "El DNI es obligatorio y no puede estar vacío")
    @Column(unique = true, nullable = false)
    private String dni;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;
    
    private String puesto;

    private boolean disponible;

    private String observaciones;

    private String password;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;

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