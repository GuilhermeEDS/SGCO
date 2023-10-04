package dgn.com.br.sgco.repository;

import dgn.com.br.sgco.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.pessoa.cpf = ?1")
    Optional<Usuario> findByCpf(String cpf);

    @Query("SELECT u FROM Usuario u WHERE u.pessoa.email = ?1")
    Optional<Usuario> findByEmail(String email);
}
