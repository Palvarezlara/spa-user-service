package cl.duoc.spa.spa_user_service.repository;

import cl.duoc.spa.spa_user_service.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUsuarioModelRepository extends JpaRepository<UsuarioModel, Long> {
    Optional<UsuarioModel> findByEmail(String email);

    boolean existsByEmail(String email);
}
