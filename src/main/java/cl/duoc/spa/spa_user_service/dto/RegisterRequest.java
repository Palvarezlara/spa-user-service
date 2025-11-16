package cl.duoc.spa.spa_user_service.dto;

import java.time.LocalDate;

public record RegisterRequest(
        String nombres,
        String apellidos,
        String email,
        String password,
        String telefono,
        String region,
        String comuna,
        LocalDate fechaNacimiento) {

}
