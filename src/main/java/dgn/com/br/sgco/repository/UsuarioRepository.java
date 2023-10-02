package dgn.com.br.sgco.repository;

import dgn.com.br.sgco.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.pessoa.cpf = ?1")
    Usuario findByCpf(String cpf);
}
