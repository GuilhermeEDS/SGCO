package dgn.com.br.sgco.repository;

import dgn.com.br.sgco.entity.Agendamento;
import dgn.com.br.sgco.entity.Dentista;
import dgn.com.br.sgco.enumeration.TipoAgendamento;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Long> {

    @Query("SELECT a FROM Agendamento a WHERE a.confirmacao IS NULL AND a.dentista = ?1 AND a.ativo = true")
    List<Agendamento> findByDentistaNaoConfirmados(Dentista dentista);

    @Query("SELECT a FROM Agendamento a WHERE a.confirmacao = true and a.dentista = ?1 AND a.ativo = true")
    List<Agendamento> findByDentistaConfirmados(Dentista dentista);

    @Query("SELECT a FROM Agendamento a WHERE a.paciente.id = ?1 and a.confirmacao is null AND a.ativo = true")
    Optional<Agendamento> findByIdPacienteNotConfirmed(long id);

    @Query("SELECT a FROM Agendamento a WHERE a.paciente.id = ?1 AND a.ativo = true")
    List<Agendamento> findByIdPaciente(long id);

    @Query("SELECT a FROM Agendamento a WHERE a.dataConsulta BETWEEN ?1 AND ?2 AND a.confirmacao = true")
    List<Agendamento> findByMes(Date inicio, Date fim);
}
