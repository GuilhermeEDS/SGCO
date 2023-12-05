package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.arq.Mensagens;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.service.AgendamentoService;
import dgn.com.br.sgco.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AgendamentoService agendamentoService;

    @GetMapping("/login-success")
    public RedirectView loginSucesso(RedirectAttributes redirectAttributes) {
        Mensagens mensagens = new Mensagens();
        mensagens.adicionaSucesso("Agora você está logado");

        redirectAttributes.addFlashAttribute("mensagens", mensagens);

        return new RedirectView("/",true);
    }

    @GetMapping("/")
    public String index(Model model) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cpf = auth.getName();
        Usuario usuario = usuarioService.porCpf(cpf).get();

        model.addAttribute("usuario", usuario);

        switch (usuario.getPapel()) {
            case ADMIN -> {
                return "admin/index";
            }
            case DENTISTA -> {
                model.addAttribute("consultas",
                        agendamentoService.porDentistaConfirmadosJson(usuario.getDentista()));
                model.addAttribute("agendamentos", agendamentoService.porDentistaNaoConfirmados(usuario.getDentista()));
                return "dentista/index";
            }
            default -> {
                model.addAttribute("agendamentos", agendamentoService.porIdPaciente(usuario.getPaciente().getId()));
                return "paciente/index";
            }
        }
    }

    @GetMapping("/login")
    public String paginaLogin(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error != null);
        

        return "login";
    }
}
