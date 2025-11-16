package cl.duoc.spa.spa_user_service.dto;

import java.time.LocalDate;

public record UpdateUsuarioRequest(
        String nombres,
        String apellidos,
        String telefono,
        String region,
        String comuna,
        LocalDate fechaNacimiento
) {}
