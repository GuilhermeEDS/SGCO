package dgn.com.br.sgco.repository;

import dgn.com.br.sgco.entity.Agendamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dgn.com.br.sgco.entity.Consulta;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends CrudRepository<Consulta, Long> {
    @Query("SELECT c FROM Consulta c WHERE c.agendamento.id = ?1 AND c.ativo = true")
    Optional<Consulta> findByIdAgendamento(long id);

    @Query("SELECT c FROM Consulta c WHERE c.agendamento.paciente.pessoa.cpf = ?1 AND c.ativo = true")
    Optional<Consulta> findAllByPacienteCpf(String cpf);
}
