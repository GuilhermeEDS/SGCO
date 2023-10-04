package dgn.com.br.sgco.service;

import dgn.com.br.sgco.dto.AgendamentoDTO;
import dgn.com.br.sgco.entity.*;
import dgn.com.br.sgco.repository.AgendamentoRepository;
import dgn.com.br.sgco.repository.DentistaRepository;
import dgn.com.br.sgco.repository.PacienteRepository;
import dgn.com.br.sgco.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgendamentoService {
    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    DentistaRepository dentistaRepository;

    @Autowired
    AgendamentoRepository agendamentoRepository;

    public Agendamento agendar(AgendamentoDTO agendamentoDto, Usuario usuario) {

        Optional<Dentista> dentista = dentistaRepository.findById(agendamentoDto.getIdDentista());

        Agendamento agendamento = new Agendamento();
        agendamento.setPaciente(usuario.getPaciente());
        agendamento.setDentista(dentista.get());
        agendamento.setTipo(agendamentoDto.getTipo().toTipoAgendamento());
        agendamento.setDataConsulta(agendamentoDto.getDataConsulta());
        agendamento.setTempoEstimado(agendamentoDto.getTempoEstimado());
        agendamento.setObservacoesPaciente(agendamentoDto.getObservacoesPaciente());
        agendamento.setObservacoesDentista(agendamentoDto.getObservacoesDentista());
        agendamento.setFormaPagamento(agendamentoDto.getFormaPagamento().toFormaPagamento());


        return agendamentoRepository.save(agendamento);
    }
}
