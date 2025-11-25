package cl.duoc.spa.spa_user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex.getMessage().toLowerCase().contains("no encontrado")) {
            status = HttpStatus.NOT_FOUND;
        }
        if (ex.getMessage().toLowerCase().contains("contraseña")) {
            status = HttpStatus.UNAUTHORIZED;
        }
        if (ex.getMessage().toLowerCase().contains("registrado")) {
            status = HttpStatus.CONFLICT;
        }

        return ResponseEntity.status(status)
                .body(Map.of("message", ex.getMessage()));
    }
}
