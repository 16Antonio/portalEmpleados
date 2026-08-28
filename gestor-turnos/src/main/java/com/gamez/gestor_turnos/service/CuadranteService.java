package com.gamez.gestor_turnos.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamez.gestor_turnos.model.Cuadrante;
import com.gamez.gestor_turnos.model.Empleado;
import com.gamez.gestor_turnos.model.TipoTurno;
import com.gamez.gestor_turnos.repository.CuadrantesRepository;
import com.gamez.gestor_turnos.repository.EmpleadoRepository;
import com.gamez.gestor_turnos.repository.TipoTurnoRepository;

@Service // Le dice a Spring: "Aquí están las reglas de negocio"
public class CuadranteService {

    @Autowired
    private CuadrantesRepository cuadrantesRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository; // Necesitamos esto para consultar al trabajador

    @Autowired
    private TipoTurnoRepository tipoTurnoRepository;

    public List<Cuadrante> obtenerTodos() {
        return cuadrantesRepository.findAll();
    }

    public Cuadrante asignarTurno(Cuadrante nuevoCuadrante) {
        // 1. Sacamos los datos
        LocalDate fechaDeseada = nuevoCuadrante.getFecha();
        Long idEmpleado = nuevoCuadrante.getEmpleado().getIdEmpleado();
        List<Cuadrante> turnosEseDia = cuadrantesRepository.findByFechaAndEmpleado(fechaDeseada, nuevoCuadrante.getEmpleado());

        // 2. Buscamos al empleado real
        Empleado empleadoReal = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new RuntimeException("Error: El empleado no existe"));

        if (!empleadoReal.isDisponible()) {
            throw new RuntimeException("OPERACIÓN DENEGADA: El empleado no está disponible.");
        }

        Long idTipoTurno = nuevoCuadrante.getTipoTurno().getIdTipoTurno();

// Buscamos el turno completo en MySQL
        TipoTurno turnoNuevoReal = tipoTurnoRepository.findById(idTipoTurno)
                .orElseThrow(() -> new RuntimeException("Error: El tipo de turno no existe"));

// ¡Ahora sí podemos sacar las horas de forma segura!
        LocalTime inicioNuevo = turnoNuevoReal.getHoraInicio();
        LocalTime finNuevo = turnoNuevoReal.getHoraFin();

        // Si la lista NO está vacía, significa que ya trabaja
        if (!turnosEseDia.isEmpty()) {
            if (turnosEseDia.size() == 1) {
                LocalTime inicioExistente = turnosEseDia.get(0).getTipoTurno().getHoraInicio();
                LocalTime finExistente = turnosEseDia.get(0).getTipoTurno().getHoraFin();

                if (inicioNuevo.isBefore(finExistente) && finNuevo.isAfter(inicioExistente)) {
                    throw new RuntimeException("OPERACIÓN DENEGADA: Solapamiento detectado. "
                            + "Ya tiene un turno de " + inicioExistente + " a " + finExistente);
                }
            } else if (turnosEseDia.size() > 1) {
                throw new RuntimeException("OPERACIÓN DENEGADA: El empleado " + empleadoReal.getNombre() + " ya tiene dos turnos asignados para la fecha " + fechaDeseada);
            }

        }

        // 4. Todo correcto, guardamos
        return cuadrantesRepository.save(nuevoCuadrante);
    }
}
