package cl.duoc.spa.spa_user_service.dto;

public record LoginResponse(
        boolean success,
        UsuarioResponse usuario,
        String message
) {
}
