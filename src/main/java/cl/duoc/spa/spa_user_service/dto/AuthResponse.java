package cl.duoc.spa.spa_user_service.dto;

import cl.duoc.spa.spa_user_service.model.UsuarioModel;

public record AuthResponse(
        boolean exito,
        String mensaje,
        Long id,
        String nombres,
        String apellidos,
        String email,
        String telefono,
        String region,
        String comuna,
        UsuarioModel.RolUsuario rol,
        UsuarioModel.EstadoUsuario estado
) {}
