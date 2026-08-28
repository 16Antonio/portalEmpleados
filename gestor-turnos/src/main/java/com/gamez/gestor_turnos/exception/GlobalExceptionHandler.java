package com.gamez.gestor_turnos.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Le dice a Spring: "Vigila todos los controladores"
public class GlobalExceptionHandler {

    // Le decimos que atrape cualquier RuntimeException que ocurra
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarErroresNegocio(RuntimeException ex) {
        
        // Creamos un JSON a mano con el mensaje de error
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getMessage());

        // Devolvemos el JSON con un código 400 (Bad Request) en lugar de un 500
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}