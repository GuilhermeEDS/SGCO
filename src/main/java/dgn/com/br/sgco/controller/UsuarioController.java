package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.entity.Agendamento;
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

import java.text.SimpleDateFormat;
import java.util.Collection;

@Controller
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AgendamentoService agendamentoService;

    @GetMapping("/login")
    public String paginaLogin(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error != null);

        return "login";
    }

    @GetMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cpf = auth.getName();
        Usuario usuario = usuarioService.porCpf(cpf).get();

        model.addAttribute("usuario", usuario);

        switch (usuario.getPapel()) {
            case ADMIN -> {
                return "indexAdmin";
            }
            case DENTISTA -> {
                Iterable<Agendamento> agendamentos = agendamentoService.porDentistaConfirmados(usuario.getDentista());
                SimpleDateFormat sdt = new SimpleDateFormat("HH:mm");
                StringBuilder jason = new StringBuilder("[");
                for (Agendamento agendamento : agendamentos) {
                    jason.append("{\"day\": ").append(agendamento.getWeek()).append(", \"start\": \"").append(sdt.format(agendamento.getHoraConsulta())).append("\", \"end\": \"").append(sdt.format(agendamento.getHoraFim())).append("\", \"title\" : \"").append(agendamento.getPaciente().getPessoa().getNome()).append("\" , \"color\": \"#779ECB\"},");
                }
                if (!((Collection<Agendamento>) agendamentos).isEmpty()) {
                    jason = new StringBuilder(jason.substring(0, jason.length() - 1));
                }
                jason.append("]");
                System.out.println(jason);
                model.addAttribute("consultas", jason.toString());
                model.addAttribute("agendamentos", agendamentoService.porDentistaNaoConfirmados(usuario.getDentista()));
                return "indexDentista";
            }
            default -> {
                return "indexPaciente";
            }
        }
    }
}
