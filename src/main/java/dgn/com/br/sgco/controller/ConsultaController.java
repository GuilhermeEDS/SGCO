package dgn.com.br.sgco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import dgn.com.br.sgco.dto.ConsultaDTO;
import dgn.com.br.sgco.entity.Consulta;
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

    @GetMapping("/consulta/{idConsulta}")
    public String paginaConsulta(@PathVariable("idConsulta") long idConsulta, @NonNull Model model) {
        // checar se dentista que está acessando, é o mesmo do agendamento

        ConsultaDTO consultaDTO = new ConsultaDTO();
        model.addAttribute("consultaDTO", consultaDTO);
        model.addAttribute("idConsulta", idConsulta);

        model.addAttribute("agendamento", consultaService.findConsulta(idConsulta).get().getAgendamento());
        return "dentista/consulta";
    }

    @PostMapping("/consulta/{idConsulta}")
    public String finalizarConsulta(@PathVariable("idConsulta") long idConsulta, final @Valid ConsultaDTO consultaDTO,
            @NonNull BindingResult result, Model model) {

        Consulta consulta = consultaService.salvar(consultaDTO, idConsulta);
        return "redirect:/";
    }
}
