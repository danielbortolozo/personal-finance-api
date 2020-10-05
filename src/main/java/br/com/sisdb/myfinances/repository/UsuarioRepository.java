package br.com.sisdb.myfinances.repository;

import br.com.sisdb.myfinances.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

    //Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
