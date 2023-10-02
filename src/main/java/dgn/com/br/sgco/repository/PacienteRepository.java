package dgn.com.br.sgco.repository;

import dgn.com.br.sgco.entity.Paciente;
import org.springframework.data.repository.CrudRepository;

public interface PacienteRepository extends CrudRepository<Paciente, Long> {
}
