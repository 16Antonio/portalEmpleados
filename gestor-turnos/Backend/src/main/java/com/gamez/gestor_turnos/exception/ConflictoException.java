package com.gamez.gestor_turnos.exception;


public class ConflictoException extends RuntimeException {
    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}