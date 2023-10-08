package dgn.com.br.sgco.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dgn.com.br.sgco.dto.ConsultaDTO;
import dgn.com.br.sgco.entity.Agendamento;
import dgn.com.br.sgco.entity.Consulta;
import dgn.com.br.sgco.repository.AgendamentoRepository;
import dgn.com.br.sgco.repository.ConsultaRepository;

@Service
public class ConsultaService {
    @Autowired
    AgendamentoRepository agendamentoRepository;

    @Autowired
    ConsultaRepository consultaRepository;

    public Consulta salvar(ConsultaDTO consultaDTO, Long idConsulta){
        
        //validação do opcional
        Consulta consulta = consultaRepository.findById(idConsulta).get();
        consulta.setDescricao(consultaDTO.getDescricao());

        return consultaRepository.save(consulta);
    }

    public Optional<Consulta> findConsulta(Long idConsulta) {
        return consultaRepository.findById(idConsulta);
    }
}
