package dgn.com.br.sgco.repository;

import dgn.com.br.sgco.entity.Agendamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dgn.com.br.sgco.entity.Consulta;

import java.util.List;
import java.util.Optional;

public interface ConsultaRepository extends CrudRepository<Consulta, Long> {
    @Query("SELECT c FROM Consulta c WHERE c.agendamento.id = ?1 AND c.ativo = true")
    Optional<Agendamento> findByIdAgendamento(long id);
}
