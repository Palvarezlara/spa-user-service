package cl.duoc.spa.spa_user_service.dto;

import cl.duoc.spa.spa_user_service.model.UsuarioModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nombres,
        String apellidos,
        String email,
        String telefono,
        String region,
        String comuna,
        LocalDate fechaNacimiento,
        UsuarioModel.RolUsuario rol,
        UsuarioModel.EstadoUsuario estado,
        LocalDateTime createdAt
) {}
