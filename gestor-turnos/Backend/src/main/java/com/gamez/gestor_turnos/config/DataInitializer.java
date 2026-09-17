package com.gamez.gestor_turnos.config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gamez.gestor_turnos.model.Empleado;
import com.gamez.gestor_turnos.model.Permiso;
import com.gamez.gestor_turnos.model.Rol;
import com.gamez.gestor_turnos.repository.EmpleadoRepository;
import com.gamez.gestor_turnos.repository.PermisoRepository;
import com.gamez.gestor_turnos.repository.RolRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder; 

    @Override
    public void run(String... args) throws Exception {
        
        // --- 1. CREACIÓN DE PERMISOS ---
        Permiso verEmpleados = crearPermisoSiNoExiste("VER_EMPLEADOS");
        Permiso crearEmpleado = crearPermisoSiNoExiste("CREAR_EMPLEADO");
        Permiso editarEmpleado = crearPermisoSiNoExiste("EDITAR_EMPLEADO");
        Permiso borrarEmpleado = crearPermisoSiNoExiste("BORRAR_EMPLEADO");

        Permiso verCuadrantes = crearPermisoSiNoExiste("VER_CUADRANTES");
        Permiso asignarTurno = crearPermisoSiNoExiste("ASIGNAR_TURNO");
        Permiso editarCuadrante = crearPermisoSiNoExiste("EDITAR_CUADRANTE");
        Permiso borrarCuadrante = crearPermisoSiNoExiste("BORRAR_CUADRANTE");

        Permiso verTiposTurno = crearPermisoSiNoExiste("VER_TIPOS_TURNO");
        Permiso crearTipoTurno = crearPermisoSiNoExiste("CREAR_TIPO_TURNO");
        Permiso editarTipoTurno = crearPermisoSiNoExiste("EDITAR_TIPO_TURNO");
        Permiso borrarTipoTurno = crearPermisoSiNoExiste("BORRAR_TIPO_TURNO");

        // --- 2. CREACIÓN DE ROLES ---
        Rol rolAdmin = rolRepository.findByNombre("ADMIN").orElseGet(() -> {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre("ADMIN");
            
            Set<Permiso> permisosAdmin = new HashSet<>();
            permisosAdmin.add(verEmpleados); permisosAdmin.add(crearEmpleado);
            permisosAdmin.add(editarEmpleado); permisosAdmin.add(borrarEmpleado);
            permisosAdmin.add(verCuadrantes); permisosAdmin.add(asignarTurno);
            permisosAdmin.add(editarCuadrante); permisosAdmin.add(borrarCuadrante);
            permisosAdmin.add(verTiposTurno); permisosAdmin.add(crearTipoTurno);
            permisosAdmin.add(editarTipoTurno); permisosAdmin.add(borrarTipoTurno);
            
            nuevoRol.setPermisos(permisosAdmin);
            return rolRepository.save(nuevoRol);
        });

        Rol rolEmpleado = rolRepository.findByNombre("EMPLEADO").orElseGet(() -> {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre("EMPLEADO");
            
            Set<Permiso> permisosEmpleado = new HashSet<>();
            permisosEmpleado.add(verCuadrantes);
            permisosEmpleado.add(verTiposTurno);
            
            nuevoRol.setPermisos(permisosEmpleado);
            return rolRepository.save(nuevoRol);
        });

        // --- 3. RESCATE DE TU USUARIO ADMINISTRADOR ---
        String tuDni = "11111111A"; 
        
        Optional<Empleado> tuUsuario = empleadoRepository.findByDni(tuDni);
        if (tuUsuario.isPresent()) {
            Empleado empleado = tuUsuario.get();
            
            // Le asignamos el rol por si lo perdió
            empleado.setRol(rolAdmin);
            
            // Le machacamos la contraseña encriptando un "1234" fresco
            empleado.setPassword(passwordEncoder.encode("1234"));
            
            empleadoRepository.save(empleado);
            System.out.println("✅ LLAVE MAESTRA CREADA: Usuario " + tuDni + " reseteado con rol ADMIN y contraseña '1234'");
        } else {
            System.out.println("❌ ATENCIÓN: No existe ningún empleado con el DNI " + tuDni + " en la base de datos.");
        }
    }

    private Permiso crearPermisoSiNoExiste(String nombre) {
        return permisoRepository.findByNombre(nombre).orElseGet(() -> {
            Permiso nuevoPermiso = new Permiso();
            nuevoPermiso.setNombre(nombre);
            return permisoRepository.save(nuevoPermiso);
        });
    }
}