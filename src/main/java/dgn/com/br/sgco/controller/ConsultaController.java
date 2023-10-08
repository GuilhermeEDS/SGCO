package dgn.com.br.sgco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dgn.com.br.sgco.dto.AgendamentoDTO;
import dgn.com.br.sgco.dto.ConsultaDTO;
import dgn.com.br.sgco.entity.Agendamento;
import dgn.com.br.sgco.entity.Consulta;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.enumeration.FormaPagamento;
import dgn.com.br.sgco.enumeration.TipoAgendamento;
import dgn.com.br.sgco.service.UsuarioService;
import dgn.com.br.sgco.service.ConsultaService;
import jakarta.validation.Valid;
import lombok.NonNull;

@Controller
public class ConsultaController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConsultaService consultaService;

    @GetMapping("/consulta/{idAgendamento}")
    public String paginaConsulta(@PathVariable("idAgendamento") long idAgendamento, @NonNull Model model){
        //checar se dentista que está acessando, é o mesmo do  agendamento

        ConsultaDTO consultaDTO = new ConsultaDTO();
        model.addAttribute("consultaDTO", consultaDTO);
        model.addAttribute("idAgendamento", idAgendamento);
        return "consulta";
    }

    @PostMapping("/consulta/{idAgendamento}")
    public String finalizarConsulta(@PathVariable("idAgendamento") long idAgendamento, final @Valid ConsultaDTO consultaDTO, @NonNull BindingResult result, Model model){ 

        Consulta consulta = consultaService.salvar(consultaDTO, idAgendamento);
        return "redirect:/";
    }
}
