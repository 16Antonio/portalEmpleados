package com.gamez.gestor_turnos.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gamez.gestor_turnos.model.Permiso;
import com.gamez.gestor_turnos.model.Rol;
import com.gamez.gestor_turnos.repository.PermisoRepository;
import com.gamez.gestor_turnos.repository.RolRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;


    @Override
    public void run(String... args) throws Exception {
        
        
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

       
         rolRepository.findByNombre("ADMIN").orElseGet(() -> {
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

        rolRepository.findByNombre("EMPLEADO").orElseGet(() -> {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombre("EMPLEADO");
            
            Set<Permiso> permisosEmpleado = new HashSet<>();
            permisosEmpleado.add(verCuadrantes);
            permisosEmpleado.add(verTiposTurno);
            
            nuevoRol.setPermisos(permisosEmpleado);
            return rolRepository.save(nuevoRol);
        });
    }

    private Permiso crearPermisoSiNoExiste(String nombre) {
        return permisoRepository.findByNombre(nombre).orElseGet(() -> {
            Permiso nuevoPermiso = new Permiso();
            nuevoPermiso.setNombre(nombre);
            return permisoRepository.save(nuevoPermiso);
        });
    }
}