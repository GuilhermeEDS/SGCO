package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.arq.Mensagens;
import dgn.com.br.sgco.dto.AgendamentoDTO;
import dgn.com.br.sgco.dto.AgendamentoDentistaDTO;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.enumeration.FormaPagamento;
import dgn.com.br.sgco.enumeration.TipoAgendamento;
import dgn.com.br.sgco.service.AgendamentoService;
import dgn.com.br.sgco.service.DentistaService;
import dgn.com.br.sgco.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.NonNull;
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

import java.text.ParseException;

@Controller
public class AgendamentoController {
    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DentistaService dentistaService;

    @GetMapping("/agendamento")
    public String paginaAgendamento(@NonNull Model model) {
        AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
        model.addAttribute("agendamentoDTO", agendamentoDTO);
        model.addAttribute("formasPagamento", FormaPagamento.values());
        model.addAttribute("tiposAgendamento", TipoAgendamento.values());
        model.addAttribute("dentistas", dentistaService.todos());
        return "agendamento/index";
    }

    @PostMapping("/agendamento")
    public RedirectView agendar(RedirectAttributes redirectAttributes, final @Valid AgendamentoDTO agendamentoDto, @NonNull BindingResult result, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cpf = auth.getName();
        Usuario usuario = usuarioService.porCpf(cpf).get();

        if (result.hasErrors()) {
            model.addAttribute("agendamentoDto", agendamentoDto);
            model.addAttribute("formasPagamento", FormaPagamento.values());
            model.addAttribute("tiposAgendamento", TipoAgendamento.values());
            model.addAttribute("dentistas", dentistaService.todos());

            return new RedirectView("agendamento/index", true);
        }

        agendamentoService.agendar(agendamentoDto, usuario.getPaciente());

        Mensagens mensagens = new Mensagens();
        mensagens.adicionaSucesso("Agendamento realizado com sucesso!");

        redirectAttributes.addFlashAttribute("mensagens", mensagens);

        return new RedirectView("/", true);
    }

    @GetMapping("/agendamento/{id}")
    public String paginaConfirmarAgendamento(@PathVariable("id") long id, @NonNull Model model) {
        AgendamentoDentistaDTO agendamentoDTO = new AgendamentoDentistaDTO();
        model.addAttribute("agendamentoDTO", agendamentoDTO);
        model.addAttribute("agendamento", agendamentoService.porId(id).get());
        return "agendamento/dentista";
    }

    @PostMapping("/agendamento/{id}")
    public RedirectView confirmarAgendamento(RedirectAttributes redirectAttributes, @PathVariable("id") long id, final @Valid AgendamentoDentistaDTO agendamentoDto,
            @NonNull BindingResult result, Model model) throws ParseException {

        if (result.hasErrors()) {
            model.addAttribute("agendamentoDTO", agendamentoDto);
            model.addAttribute("agendamento", agendamentoService.porId(id).get());
            return new RedirectView("agendamento/dentista", true);
        }

        agendamentoService.confirmarAgendamento(id, agendamentoDto);

        Mensagens mensagens = new Mensagens();
        mensagens.adicionaSucesso("Agendamento confirmado com sucesso!");
        redirectAttributes.addFlashAttribute("mensagens", mensagens);

        return new RedirectView("/", true);
    }

    @GetMapping("/agendamento/recusar/{idAgendamento}")
    public String paginaRecusarAgendamentoModal(@PathVariable Long idAgendamento, Model model) {
        model.addAttribute("idAgendamento", idAgendamento);
        return "agendamento/recusarModal";
    }

    @PostMapping("/agendamento/recusar/{idAgendamento}")
    public RedirectView removerAgendamento(RedirectAttributes redirectAttributes, @PathVariable Long idAgendamento, Model model) {
        agendamentoService.recusarAgendamento(idAgendamento);

        Mensagens mensagens = new Mensagens();
        mensagens.adicionaSucesso("Agendamento recusado com sucesso!");
        redirectAttributes.addFlashAttribute("mensagens", mensagens);

        return new RedirectView("/", true);
    }
}
