package dgn.com.br.sgco.service;

import dgn.com.br.sgco.arq.EmailSender;
import dgn.com.br.sgco.arq.ValidacaoEntidadeException;
import dgn.com.br.sgco.dto.AgendamentoDTO;
import dgn.com.br.sgco.dto.AgendamentoDentistaDTO;
import dgn.com.br.sgco.entity.Agendamento;
import dgn.com.br.sgco.entity.Consulta;
import dgn.com.br.sgco.entity.Dentista;
import dgn.com.br.sgco.entity.Paciente;
import dgn.com.br.sgco.enumeration.TipoAgendamento;
import dgn.com.br.sgco.repository.AgendamentoRepository;
import dgn.com.br.sgco.repository.ConsultaRepository;
import dgn.com.br.sgco.repository.DentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;

@Service
public class AgendamentoService {
    @Autowired
    DentistaRepository dentistaRepository;

    @Autowired
    AgendamentoRepository agendamentoRepository;

    @Autowired
    ConsultaRepository consultaRepository;

    @Autowired
    EmailSender emailSender;

    String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

    public static boolean semanaAtual(Date data) {
        Calendar calendario = Calendar.getInstance();

        int semana = calendario.get(Calendar.WEEK_OF_YEAR);
        int ano = calendario.get(Calendar.YEAR);

        calendario.setTime(data);

        int semanaData = calendario.get(Calendar.WEEK_OF_YEAR);
        int calendarioAno = calendario.get(Calendar.YEAR);

        return semana == semanaData && ano == calendarioAno;
    }

    public Agendamento agendar(AgendamentoDTO agendamentoDto, Paciente paciente) {

        Optional<Dentista> optDentista = dentistaRepository.findById(agendamentoDto.getIdDentista());

        if (agendamentoRepository.findByIdPacienteNotConfirmed(paciente.getId()).isPresent()) {
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
        emailSender.enviarEmail(agendamento.getDentista().getPessoa().getEmail(), "Novo Agendamento", "Há um novo agendamento aguardando confirmação! Acesse nosso sistema para obter mais informações sobre o agendamento.");
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
        consulta.setAgendamento(agendamento);
        consulta = consultaRepository.save(consulta);
        agendamento.setConsulta(consulta);
        emailSender.enviarEmail(agendamento.getPaciente().getPessoa().getEmail(), "Agendamento Confirmado", "Por meio desta, gostaríamos de confirmar que seu agendamento com o doutor" + consulta.getAgendamento().getDentista().getPessoa().getNome() + "em questão está agendado para o dia " + consulta.getAgendamento().getDataConsulta() +" às "+ consulta.getAgendamento().getHoraConsulta() + ".");
        return agendamentoRepository.save(agendamento);
    }

    public Agendamento recusarAgendamento(Long id) {

        Optional<Agendamento> optAgendamento = agendamentoRepository.findById(id);

        if (optAgendamento.isEmpty()) {
            throw new ValidacaoEntidadeException("agendamentoDTO.observacoesDentista",
                    "Agendamento não encontrado.");
        }

        Agendamento agendamento = optAgendamento.get();
        agendamento.setConfirmacao(false);
        emailSender.enviarEmail(agendamento.getPaciente().getPessoa().getEmail(), "Agendamento Cancelado", "Por meio desta, desejamos comunicar que o agendamento previamente marcado com o doutor" + agendamento.getDentista().getPessoa().getNome() + " foi cancelado para o dia designado. Fique à vontade para efetuar um novo agendamento por meio do nosso sistema.");
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

    public Iterable<Agendamento> porIdPaciente(Long idPaciente) {
        return agendamentoRepository.findByIdPaciente(idPaciente);
    }

    public String porDentistaConfirmadosJson(Dentista dentista) throws Exception {
        Iterable<Agendamento> agendamentos = porDentistaConfirmados(dentista);
        SimpleDateFormat sdtd = new SimpleDateFormat("dd");
        SimpleDateFormat sdt = new SimpleDateFormat("HH:mm");
        StringBuilder json = new StringBuilder("[");
        for (Agendamento agendamento : agendamentos) {
                json.append("{\"dia\": ").append(sdtd.format(agendamento.getDataConsulta()).replaceFirst ("^0*", ""))
                        .append(", \"horaInicio\": \"")
                        .append(sdt.format(agendamento.getHoraConsulta()))
                        .append("\", \"horaFim\": \"")
                        .append(sdt.format(agendamento.getHoraFim()))
                        .append("\", \"titulo\" : \"")
                        .append(agendamento.getPaciente().getPessoa().getNome())
                        .append("\" , \"id\": \"")
                        .append(agendamento.getConsulta().getId())
                        .append("\"},");
        }

        if (!json.toString().equals("[")) {
            json = new StringBuilder(json.substring(0, json.length() - 1));
        }

        json.append("]");

        return json.toString();
    }
    
    public Map<String, Float[]> porMes(){
        Calendar calendar = Calendar.getInstance();
        Date fim = calendar.getTime();
        calendar.add(Calendar.MONTH, -5);
        Date inicio = calendar.getTime();


        Iterable<Agendamento> agendamentos = agendamentoRepository.findByMes(inicio, fim);
        
        float[][] contador = new float[6][3];
        float[] contadorValores = new float[6];
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 3; j++){
                contador[i][j] = 0;
            }
            contadorValores[i] = 0;
        }
        for(int i = 0; i < 6; i++){
            for(Agendamento agendamento : agendamentos){
                Calendar agenCalendar = Calendar.getInstance(); 
                agenCalendar.setTime(agendamento.getDataConsulta());
                if(agenCalendar.get(agenCalendar.MONTH) == calendar.get(calendar.MONTH)){
                    contadorValores[i] += agendamento.getConsulta().getValorProcedimento();
                    //contadorValores[i] += 1;
                    switch (agendamento.getTipo()) {
                        case ACOMPANHAMENTO:
                            contador[i][0]++;
                            break;
                        case CIRURGIA:
                            contador[i][1]++;
                            break;
                        case CONSULTA:
                            contador[i][2]++;
                            break;
                    }
                }
            }
            calendar.add(Calendar.MONTH, +1);

        }
        calendar.add(Calendar.MONTH, -6);
        Map<String, Float[]> mesesValores = new LinkedHashMap<>();
        for(int i = 0; i < 6; i++){

            mesesValores.put(meses[calendar.get(calendar.MONTH)], new Float[]{contador[i][0], contador[i][1], contador[i][2], contadorValores[i]});
            //mesesValores.put(meses[calendar.get(calendar.MONTH)], new Float[]{contador[i][0], contador[i][1], contador[i][2]});
            calendar.add(Calendar.MONTH, +1);
        }

        return mesesValores;
    }

}
