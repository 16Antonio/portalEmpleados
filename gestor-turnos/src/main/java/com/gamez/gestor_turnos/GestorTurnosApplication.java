package com.gamez.gestor_turnos; // (Tu paquete original, déjalo como esté)

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.gamez.gestor_turnos.model.Empleado;
import com.gamez.gestor_turnos.repository.EmpleadoRepository;

@SpringBootApplication
public class GestorTurnosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestorTurnosApplication.class, args);
    }

    // NUEVO: Este código se ejecuta automáticamente justo al arrancar el servidor
    @Bean
    public CommandLineRunner initData(EmpleadoRepository repository) {
        return args -> {
            // Si la base de datos está vacía, creamos el usuario de rescate
            if (repository.count() == 0) {
                Empleado admin = new Empleado();
                admin.setDni("admin");
                admin.setPassword("1234");
                admin.setNombre("Super");
                admin.setApellidos("Administrador");
                admin.setRol("ROLE_ADMIN");
                admin.setDisponible(true);
                
                repository.save(admin);
                System.out.println("✅ Creado usuario administrador por defecto (DNI: admin / Pass: 1234)");
            }
        };
    }
}