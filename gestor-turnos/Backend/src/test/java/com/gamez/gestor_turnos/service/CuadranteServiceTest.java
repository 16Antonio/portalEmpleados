package com.gamez.gestor_turnos.service;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gamez.gestor_turnos.exception.ConflictoException;
import com.gamez.gestor_turnos.model.Cuadrante;
import com.gamez.gestor_turnos.model.Empleado;
import com.gamez.gestor_turnos.repository.CuadrantesRepository;
import com.gamez.gestor_turnos.repository.EmpleadoRepository;
import com.gamez.gestor_turnos.repository.TipoTurnoRepository;

@ExtendWith(MockitoExtension.class) 
public class CuadranteServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;
    @Mock
    private TipoTurnoRepository tipoTurnoRepository;
    @Mock
    private CuadrantesRepository cuadrantesRepository;


    @InjectMocks
    private CuadranteService cuadranteService;


    @Test
    void asignarTurno_CuandoEmpleadoNoDisponible_DebeLanzarConflictoException() {
        

        Empleado empleadoFalso = new Empleado();
        empleadoFalso.setIdEmpleado(1L);
        empleadoFalso.setDisponible(false); 


        Cuadrante peticionTurno = new Cuadrante();
        peticionTurno.setEmpleado(empleadoFalso);
        peticionTurno.setFecha(LocalDate.now());


        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleadoFalso));



        ConflictoException error = assertThrows(ConflictoException.class, () -> {
            cuadranteService.asignarTurno(peticionTurno);
        });

        assertEquals("OPERACIÓN DENEGADA: El empleado no está disponible.", error.getMessage());
    }
}