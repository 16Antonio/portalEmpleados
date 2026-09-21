package com.gamez.gestor_turnos.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gamez.gestor_turnos.exception.ConflictoException;
import com.gamez.gestor_turnos.exception.RecursoNoEncontradoException;
import com.gamez.gestor_turnos.model.Cuadrante;
import com.gamez.gestor_turnos.model.Empleado;
import com.gamez.gestor_turnos.model.TipoTurno;
import com.gamez.gestor_turnos.repository.CuadrantesRepository;
import com.gamez.gestor_turnos.repository.EmpleadoRepository;
import com.gamez.gestor_turnos.repository.TipoTurnoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CuadranteService {

    private final CuadrantesRepository cuadrantesRepository;
    private final EmpleadoRepository empleadoRepository;
    private final TipoTurnoRepository tipoTurnoRepository;

    public List<Cuadrante> obtenerTodos() {
        return cuadrantesRepository.findAll();
    }

    public Cuadrante asignarTurno(Cuadrante nuevoCuadrante) {
        validarReglasDeTurno(nuevoCuadrante, null);
        return cuadrantesRepository.save(nuevoCuadrante);
    }

    public void eliminarCuadrante(Long id) {
        cuadrantesRepository.deleteById(id);
    }

    public Cuadrante actualizarCuadrante(Long id, Cuadrante cuadranteActualizado) {
        validarReglasDeTurno(cuadranteActualizado, id);
        cuadranteActualizado.setIdCuadrante(id);
        return cuadrantesRepository.save(cuadranteActualizado);
    }

    private void validarReglasDeTurno(Cuadrante cuadrante, Long idAIgnorar) {
        LocalDate fecha = cuadrante.getFecha();
        Empleado empleado = empleadoRepository.findById(cuadrante.getEmpleado().getIdEmpleado())
                .orElseThrow(() -> new RecursoNoEncontradoException("Error: El empleado no existe"));

        if (!empleado.isDisponible()) {
            throw new ConflictoException("OPERACIÓN DENEGADA: El empleado no está disponible.");
        }

        TipoTurno turnoNuevo = tipoTurnoRepository.findById(cuadrante.getTipoTurno().getIdTipoTurno())
                .orElseThrow(() -> new RecursoNoEncontradoException("Error: El tipo de turno no existe"));

        List<Cuadrante> turnosEseDia = cuadrantesRepository.findByFechaAndEmpleado(fecha, empleado);

        if (idAIgnorar != null) {
            turnosEseDia.removeIf(c -> c.getIdCuadrante().equals(idAIgnorar));
        }

        LocalTime inicioNuevo = turnoNuevo.getHoraInicio();
        LocalTime finNuevo = turnoNuevo.getHoraFin();

        if (!turnosEseDia.isEmpty()) {
            if (turnosEseDia.size() == 1) {
                LocalTime inicioExistente = turnosEseDia.get(0).getTipoTurno().getHoraInicio();
                LocalTime finExistente = turnosEseDia.get(0).getTipoTurno().getHoraFin();

                if (inicioNuevo.isBefore(finExistente) && finNuevo.isAfter(inicioExistente)) {
                    throw new ConflictoException("OPERACIÓN DENEGADA: Solapamiento detectado. "
                            + "Ya tiene un turno de " + inicioExistente + " a " + finExistente);
                }
            } else if (turnosEseDia.size() > 1) {
                throw new ConflictoException("OPERACIÓN DENEGADA: El empleado " + empleado.getNombre() + " ya tiene dos turnos asignados para la fecha " + fecha);
            }
        }
    }
}