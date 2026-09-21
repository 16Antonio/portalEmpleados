package com.gamez.gestor_turnos.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Cuando algo NO SE ENCUENTRA -> Devolvemos un 404 (Not Found)
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 2. Cuando hay un CONFLICTO DE NEGOCIO (ej. Solapamiento) -> Devolvemos 409 (Conflict)
    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<Map<String, String>> manejarConflicto(ConflictoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // 3. Cuando ocurre CUALQUIER OTRA COSA -> 400 (Bad Request)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarErroresGenericos(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        
        // Extraemos todos los campos que han fallado y sus mensajes
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}