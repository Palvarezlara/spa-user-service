package cl.duoc.spa.spa_user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // Datos personales
    // =========================
    @Column(nullable = false, length = 80)
    private String nombres;

    @Column(nullable = false, length = 80)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    // Nunca se expone al frontend
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(length = 20)
    private String telefono;

    @Column(length = 80)
    private String region;

    @Column(length = 80)
    private String comuna;

    private LocalDate fechaNacimiento;

    // =========================
    // Rol y estado
    // =========================
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RolUsuario rol = RolUsuario.CLIENTE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;

    // =========================
    // Auditoría básica
    // =========================
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        if (this.rol == null) {
            this.rol = RolUsuario.CLIENTE;
        }
        if (this.estado == null) {
            this.estado = EstadoUsuario.ACTIVO;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // =========================
    // Enums internos
    // =========================
    public enum RolUsuario {
        CLIENTE,
        ADMIN,
        TERAPEUTA,
        RECEPCIONISTA
    }

    public enum EstadoUsuario {
        ACTIVO,
        INACTIVO,
        BLOQUEADO
    }
}
