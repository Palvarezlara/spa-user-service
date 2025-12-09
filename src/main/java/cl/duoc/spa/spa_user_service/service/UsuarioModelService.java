package cl.duoc.spa.spa_user_service.service;

import cl.duoc.spa.spa_user_service.dto.AuthResponse;
import cl.duoc.spa.spa_user_service.dto.RegisterRequest;
import cl.duoc.spa.spa_user_service.dto.UpdateUsuarioRequest;
import cl.duoc.spa.spa_user_service.dto.UsuarioResponse;
import cl.duoc.spa.spa_user_service.model.UsuarioModel;
import cl.duoc.spa.spa_user_service.repository.IUsuarioModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioModelService {
    private final IUsuarioModelRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ========================================
    // Registro
    // ========================================
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        // 1) validar duplicado por email
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        // 2) crear entidad
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNombres(request.nombres());
        usuario.setApellidos(request.apellidos());
        usuario.setEmail(request.email());
        usuario.setPasswordHash(passwordEncoder.encode(request.password()));
        usuario.setTelefono(request.telefono());
        usuario.setRegion(request.region());
        usuario.setComuna(request.comuna());
        usuario.setFechaNacimiento(request.fechaNacimiento());

        // 3) guardar
        usuarioRepository.save(usuario);

        // 4) responder con los 11 campos
        return new AuthResponse(
                true,
                "Registro exitoso",
                usuario.getId(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getRegion(),
                usuario.getComuna(),
                usuario.getRol(),
                usuario.getEstado()
        );
    }


    // ========================================
    // Login sin JWT
    // ========================================
    public AuthResponse login(String email, String password) {

        UsuarioModel usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (usuario.getEstado() == UsuarioModel.EstadoUsuario.BLOQUEADO) {
            throw new RuntimeException("Usuario bloqueado");
        }

        return new AuthResponse(
                true,
                "Login exitoso",
                usuario.getId(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getRegion(),
                usuario.getComuna(),
                usuario.getRol(),
                usuario.getEstado()
        );
    }


    // ========================================
    // CRUD básico
    // ========================================
    public List<UsuarioResponse> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioResponse findById(Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse update(Long id, UpdateUsuarioRequest request) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (request.nombres() != null) usuario.setNombres(request.nombres());
        if (request.apellidos() != null) usuario.setApellidos(request.apellidos());
        if (request.telefono() != null) usuario.setTelefono(request.telefono());
        if (request.region() != null) usuario.setRegion(request.region());
        if (request.comuna() != null) usuario.setComuna(request.comuna());
        if (request.fechaNacimiento() != null) usuario.setFechaNacimiento(request.fechaNacimiento());

        if (request.fechaNacimiento() != null) {
            LocalDate hoy = LocalDate.now();
            int edad = Period.between(request.fechaNacimiento(), hoy).getYears();

            if (edad < 18) {

                throw new IllegalArgumentException("El usuario debe ser mayor de 18 años");
            }

            usuario.setFechaNacimiento(request.fechaNacimiento());
        }


        UsuarioModel saved = usuarioRepository.save(usuario);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    // ========================================
    // Cambiar rol / estado (para admin)
    // ========================================
    @Transactional
    public UsuarioResponse cambiarRol(Long id, UsuarioModel.RolUsuario nuevoRol) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setRol(nuevoRol);
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse cambiarEstado(Long id, UsuarioModel.EstadoUsuario nuevoEstado) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEstado(nuevoEstado);
        return toResponse(usuarioRepository.save(usuario));
    }

    // ========================================
    // Mapper a DTO
    // ========================================
    private UsuarioResponse toResponse(UsuarioModel u) {
        return new UsuarioResponse(
                u.getId(),
                u.getNombres(),
                u.getApellidos(),
                u.getEmail(),
                u.getTelefono(),
                u.getRegion(),
                u.getComuna(),
                u.getFechaNacimiento(),
                u.getRol(),
                u.getEstado(),
                u.getCreatedAt()
        );
    }
}
