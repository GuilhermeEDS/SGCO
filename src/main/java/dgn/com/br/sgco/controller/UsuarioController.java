package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.entity.Agendamento;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.repository.AgendamentoRepository;
import dgn.com.br.sgco.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AgendamentoRepository agendamentoRepository;

    @GetMapping("/login")
    public String paginaLogin(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error != null);

        return "login";
    }

    @GetMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cpf = auth.getName();
        Usuario usuario = usuarioRepository.findByCpf(cpf).get();

        model.addAttribute("usuario", usuario);

        switch (usuario.getPapel()) {
            case ADMIN -> {
                return "indexAdmin";
            }
            case DENTISTA -> {
                List<Agendamento> agendamentos =agendamentoRepository.findAllAgendamentoConfirmedByDentista(usuario.getDentista());
                SimpleDateFormat sdt = new SimpleDateFormat("HH:mm");
                String jason = "[";
                for(Agendamento agendamento : agendamentos){
                    jason += "{\"day\": " + agendamento.getWeek() + ", \"start\": \"" + sdt.format(agendamento.getHoraConsulta()) + "\", \"end\": \"13:00\", \"title\" : \"" + agendamento.getPaciente().getPessoa().getNome() + "\" , \"color\": \"#779ECB\"},";
                }
                if(!agendamentos.isEmpty()){
                    jason = jason.substring(0, jason.length() - 1);
                }
                jason += "]";
                System.out.println(jason);
                model.addAttribute("consultas", jason);
                model.addAttribute("agendamentos", agendamentoRepository.findAllAgendamentoNotConfirmedByDentista(usuario.getDentista()));
                return "indexDentista";
            }
            default -> {
                return "indexPaciente";
            }
        }
    }
}
