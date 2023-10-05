package dgn.com.br.sgco.service;

import dgn.com.br.sgco.arq.ValidacaoEntidadeException;
import dgn.com.br.sgco.dto.AgendamentoDTO;
import dgn.com.br.sgco.entity.*;
import dgn.com.br.sgco.repository.AgendamentoRepository;
import dgn.com.br.sgco.repository.DentistaRepository;
import dgn.com.br.sgco.repository.PacienteRepository;
import dgn.com.br.sgco.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AgendamentoService {
    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    DentistaRepository dentistaRepository;

    @Autowired
    AgendamentoRepository agendamentoRepository;

    public Agendamento agendar(AgendamentoDTO agendamentoDto, Paciente paciente) {

        Optional<Dentista> dentista = dentistaRepository.findById(agendamentoDto.getIdDentista());

        if(agendamentoRepository.findByPacienteId(paciente.getId()).isPresent()){
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
}
