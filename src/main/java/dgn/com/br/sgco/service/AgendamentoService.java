package dgn.com.br.sgco.service;

import dgn.com.br.sgco.arq.ValidacaoEntidadeException;
import dgn.com.br.sgco.dto.AgendamentoDTO;
import dgn.com.br.sgco.entity.Agendamento;
import dgn.com.br.sgco.entity.Dentista;
import dgn.com.br.sgco.entity.Paciente;
import dgn.com.br.sgco.repository.AgendamentoRepository;
import dgn.com.br.sgco.repository.DentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AgendamentoService {
    @Autowired
    DentistaRepository dentistaRepository;

    @Autowired
    AgendamentoRepository agendamentoRepository;

    public Agendamento agendar(AgendamentoDTO agendamentoDto, Paciente paciente) {

        Optional<Dentista> dentista = dentistaRepository.findById(agendamentoDto.getIdDentista());

        if (agendamentoRepository.findByIdPaciente(paciente.getId()).isPresent()) {
            throw new ValidacaoEntidadeException("agendamentoDTO.formaPagamento", "Você já tem um agendamento em processamento");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setPaciente(paciente);
        agendamento.setDentista(dentista.get());
        agendamento.setTipo(agendamentoDto.getTipo().toTipoAgendamento());
        agendamento.setDataConsulta(agendamentoDto.getDataConsulta());
        agendamento.setHoraConsulta(agendamentoDto.getHoraConsulta());
        agendamento.setObservacoesPaciente(agendamentoDto.getObservacoesPaciente());
        agendamento.setFormaPagamento(agendamentoDto.getFormaPagamento().toFormaPagamento());

        return agendamentoRepository.save(agendamento);
    }

    public Agendamento confirmarAgendamento(Long id, String observacoesDentista, Date horaFim) {

        Agendamento agendamento = agendamentoRepository.findById(id).get();

        agendamento.setObservacoesDentista(observacoesDentista);
        agendamento.setHoraFim(horaFim);
        agendamento.setConfirmacao(true);

        return agendamentoRepository.save(agendamento);
    }

    public Optional<Agendamento> porId(Long id) {
        return agendamentoRepository.findById(id);
    }

    public Iterable<Agendamento> porDentistaConfirmados(Dentista dentista) {
        return agendamentoRepository.findByDentistaConfirmados(dentista);
    }

    public Iterable<Agendamento> porDentistaNaoConfirmados(Dentista dentista) {
        return agendamentoRepository.findByDentistaNaoConfirmados(dentista);
    }
}
