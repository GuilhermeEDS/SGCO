package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.dto.AgendamentoDTO;
import dgn.com.br.sgco.entity.Agendamento;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.enumeration.FormaPagamento;
import dgn.com.br.sgco.enumeration.TipoAgendamento;
import dgn.com.br.sgco.repository.DentistaRepository;
import dgn.com.br.sgco.repository.PacienteRepository;
import dgn.com.br.sgco.repository.UsuarioRepository;
import dgn.com.br.sgco.service.AgendamentoService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/agendamento")
    public String paginaAgendamento(@NonNull Model model) {
        AgendamentoDTO agendamentoDTO  = new AgendamentoDTO();
        model.addAttribute("agendamentoDTO", agendamentoDTO);
        model.addAttribute("formasPagamento", FormaPagamento.values());
        model.addAttribute("tiposAgendamento", TipoAgendamento.values());
        model.addAttribute("dentistas", dentistaRepository.findAll());
        return "agendamento";
    }

    @PostMapping("/agendamento")
    public String agendar(final @Valid AgendamentoDTO agendamentoDto, @NonNull BindingResult result, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cpf = auth.getName();
        Usuario usuario = usuarioRepository.findByCpf(cpf).get();

        if (result.hasErrors()) {
            model.addAttribute("agendamentoDto", agendamentoDto);
            model.addAttribute("formasPagamento", FormaPagamento.values());
            model.addAttribute("tiposAgendamento", TipoAgendamento.values());
            model.addAttribute("dentistas", dentistaRepository.findAll());
            return "agendamento";
        }

        Agendamento agendamento = agendamentoService.agendar(agendamentoDto, usuario);
        return "/";
    }
}
