package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.repository.ConsultaRepository;
import dgn.com.br.sgco.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import dgn.com.br.sgco.arq.Mensagens;
import dgn.com.br.sgco.dto.ConsultaDTO;
import dgn.com.br.sgco.service.ConsultaService;
import jakarta.validation.Valid;
import lombok.NonNull;

@Controller
public class ConsultaController {
    @Autowired
    private ConsultaService consultaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ConsultaRepository consultaRepository;

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
    public RedirectView finalizarConsulta(RedirectAttributes redirectAttributes, @PathVariable("idConsulta") long idConsulta, final @Valid ConsultaDTO consultaDTO,
            @NonNull BindingResult result, Model model) {

        consultaService.salvar(consultaDTO, idConsulta);

        Mensagens mensagens = new Mensagens();
        mensagens.adicionaSucesso("Consulta salva com sucesso!");
        redirectAttributes.addFlashAttribute("mensagens", mensagens);

        return new RedirectView("/", true);
    }

    @GetMapping("/consulta/historico/{cpf}")
    public String paginaHistorico(@NonNull Model model, @PathVariable("cpf") String cpf){
        model.addAttribute("consultas", consultaRepository.findAllByPacienteCpf(cpf));
        return "/paciente/historico";
    }
}
