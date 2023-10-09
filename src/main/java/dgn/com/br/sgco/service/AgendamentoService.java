package dgn.com.br.sgco.service;

import dgn.com.br.sgco.arq.ValidacaoEntidadeException;
import dgn.com.br.sgco.dto.AgendamentoDTO;
import dgn.com.br.sgco.dto.AgendamentoDentistaDTO;
import dgn.com.br.sgco.entity.Agendamento;
import dgn.com.br.sgco.entity.Consulta;
import dgn.com.br.sgco.entity.Dentista;
import dgn.com.br.sgco.entity.Paciente;
import dgn.com.br.sgco.repository.AgendamentoRepository;
import dgn.com.br.sgco.repository.ConsultaRepository;
import dgn.com.br.sgco.repository.DentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AgendamentoService {
    @Autowired
    DentistaRepository dentistaRepository;

    @Autowired
    AgendamentoRepository agendamentoRepository;

    @Autowired
    ConsultaRepository consultaRepository;

    public Agendamento agendar(AgendamentoDTO agendamentoDto, Paciente paciente) {

        Optional<Dentista> optDentista = dentistaRepository.findById(agendamentoDto.getIdDentista());

        if (agendamentoRepository.findByIdPaciente(paciente.getId()).isPresent()) {
            throw new ValidacaoEntidadeException("agendamentoDTO.formaPagamento",
                    "Você já tem um agendamento em processamento.");
        }

        if (optDentista.isEmpty()) {
            throw new ValidacaoEntidadeException("agendamentoDTO.formaPagamento",
                    "Dentista não encontrado.");
        }

        Dentista dentista = optDentista.get();

        Agendamento agendamento = new Agendamento();
        agendamento.setPaciente(paciente);
        agendamento.setDentista(dentista);
        agendamento.setTipo(agendamentoDto.getTipo().toTipoAgendamento());
        agendamento.setDataConsulta(agendamentoDto.getDataConsulta());
        agendamento.setHoraConsulta(agendamentoDto.getHoraConsulta());
        agendamento.setObservacoesPaciente(agendamentoDto.getObservacoesPaciente());
        agendamento.setFormaPagamento(agendamentoDto.getFormaPagamento().toFormaPagamento());

        return agendamentoRepository.save(agendamento);
    }

    public Agendamento confirmarAgendamento(Long id, AgendamentoDentistaDTO agendamentoDentista) throws ParseException {

        Optional<Agendamento> optAgendamento = agendamentoRepository.findById(id);

        if (optAgendamento.isEmpty()) {
            throw new ValidacaoEntidadeException("agendamentoDTO.observacoesDentista",
                    "Agendamento não encontrado.");
        }

        Agendamento agendamento = optAgendamento.get();

        agendamento.setObservacoesDentista(agendamentoDentista.getObservacoesDentista());

        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        Date data = formato.parse(agendamentoDentista.getHoraFim());
        agendamento.setHoraFim(data);
        agendamento.setConfirmacao(true);

        Consulta consulta = new Consulta();
        consultaRepository.save(consulta);
        agendamento.setConsulta(consulta);

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

    public String porDentistaConfirmadosJson(Dentista dentista) throws Exception{
        Iterable<Agendamento> agendamentos = porDentistaConfirmados(dentista);

        SimpleDateFormat sdt = new SimpleDateFormat("HH:mm");
        StringBuilder json = new StringBuilder("[");

        for (Agendamento agendamento : agendamentos) {
            if (semanaAtual(agendamento.getDataConsulta())) {
                json.append("{\"day\": ").append(agendamento.getWeek())
                        .append(", \"start\": \"")
                        .append(sdt.format(agendamento.getHoraConsulta()))
                        .append("\", \"end\": \"")
                        .append(sdt.format(agendamento.getHoraFim()))
                        .append("\", \"title\" : \"")
                        .append(agendamento.getPaciente().getPessoa().getNome())
                        .append("\" , \"color\": \"#779ECB\"},");
            }
        }

        if (!json.toString().equals("[")){
            json = new StringBuilder(json.substring(0, json.length() - 1));
        };

        json.append("]");

        return json.toString();
    }

    public static boolean semanaAtual(Date data) {
        Calendar calendario = Calendar.getInstance();

        int semana = calendario.get(Calendar.WEEK_OF_YEAR);
        int ano = calendario.get(Calendar.YEAR);

        calendario.setTime(data);

        int semanaData = calendario.get(Calendar.WEEK_OF_YEAR);
        int calendarioAno = calendario.get(Calendar.YEAR);

        return semana == semanaData && ano == calendarioAno;
    }
}
