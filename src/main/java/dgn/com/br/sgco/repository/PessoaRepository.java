package dgn.com.br.sgco.repository;

import dgn.com.br.sgco.entity.Pessoa;
import org.springframework.data.repository.CrudRepository;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {
}
