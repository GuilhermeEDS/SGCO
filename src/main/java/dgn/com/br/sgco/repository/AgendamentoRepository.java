package dgn.com.br.sgco.repository;

import dgn.com.br.sgco.entity.Agendamento;
import dgn.com.br.sgco.entity.Dentista;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Long> {

    @Query("SELECT a FROM Agendamento a WHERE a.confirmacao IS NULL and a.dentista = ?1")
    List<Agendamento> findByDentistaNaoConfirmados(Dentista dentista);

    @Query("SELECT a FROM Agendamento a WHERE a.confirmacao = true and a.dentista = ?1")
    List<Agendamento> findByDentistaConfirmados(Dentista dentista);

    @Query("SELECT a FROM Agendamento a WHERE a.paciente.id = ?1 and a.confirmacao is null")
    Optional<Agendamento> findByIdPaciente(long id);
}
