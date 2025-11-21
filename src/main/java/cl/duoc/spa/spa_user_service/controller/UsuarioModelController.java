package cl.duoc.spa.spa_user_service.controller;

import cl.duoc.spa.spa_user_service.dto.RegisterRequest;
import cl.duoc.spa.spa_user_service.dto.UpdateUsuarioRequest;
import cl.duoc.spa.spa_user_service.dto.UsuarioResponse;
import cl.duoc.spa.spa_user_service.model.UsuarioModel;
import cl.duoc.spa.spa_user_service.service.UsuarioModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UsuarioModelController {
    private final UsuarioModelService usuarioService;

    // Registro público
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(usuarioService.register(request));
    }

    // Listar todos (para panel admin)
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    // Obtener uno
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    // Actualizar datos de usuario (perfil o admin)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateUsuarioRequest request
    ) {
        return ResponseEntity.ok(usuarioService.update(id, request));
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Cambiar ROL
    @PatchMapping("/{id}/rol")
    public ResponseEntity<UsuarioResponse> cambiarRol(
            @PathVariable Long id,
            @RequestBody RolDTO body
    ) {
        UsuarioModel.RolUsuario rol = UsuarioModel.RolUsuario.valueOf(body.rol());
        return ResponseEntity.ok(usuarioService.cambiarRol(id, rol));
    }

    // Cambiar ESTADO
    @PatchMapping("/{id}/estado")
    public ResponseEntity<UsuarioResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestBody EstadoDTO body
    ) {
        UsuarioModel.EstadoUsuario estado = UsuarioModel.EstadoUsuario.valueOf(body.estado());
        return ResponseEntity.ok(usuarioService.cambiarEstado(id, estado));
    }

    // DTOs internos para PATCH
    public record RolDTO(String rol) {}          // "ADMIN", "CLIENTE", "TERAPEUTA", "RECEPCIONISTA"
    public record EstadoDTO(String estado) {}    // "ACTIVO", "INACTIVO", "BLOQUEADO"
}
