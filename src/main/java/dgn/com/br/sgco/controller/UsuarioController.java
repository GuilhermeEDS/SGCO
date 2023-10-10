package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.service.AgendamentoService;
import dgn.com.br.sgco.service.UsuarioService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/listagem/usuario")
    public String paginaListagemUsuarios(Model model) {
        Iterable<Usuario> usuarios = usuarioService.todos();
        model.addAttribute("usuarios", usuarios);

        return "listagemUsuarios";
    }

    @GetMapping("/")
    public String index(Model model) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String cpf = auth.getName();
        Usuario usuario = usuarioService.porCpf(cpf).get();

        model.addAttribute("usuario", usuario);

        switch (usuario.getPapel()) {
            case ADMIN -> {
                return "indexAdmin";
            }
            case DENTISTA -> {
                model.addAttribute("consultas",
                        agendamentoService.porDentistaConfirmadosJson(usuario.getDentista()));
                model.addAttribute("agendamentos", agendamentoService.porDentistaNaoConfirmados(usuario.getDentista()));
                return "indexDentista";
            }
            default -> {
                return "indexPaciente";
            }
        }
    }

    @GetMapping("/usuario/remover/{idUsuario}")
    public String paginaRemoverUsuarioModal(@PathVariable Integer idUsuario, Model model) {
        model.addAttribute("idUsuario", idUsuario);
        return "removerUsuarioModal";
    }

    @GetMapping("/usuario/detalhes/{idUsuario}")
    public String paginaDetalhesUsuarioModal(@PathVariable Long idUsuario, Model model) {
        Optional<Usuario> usuario = usuarioService.porId(idUsuario);
        System.out.println(usuario.get());
        model.addAttribute("usuario", usuario.get());
        return "detalhesUsuarioModal";
    }

    @PostMapping("/usuario/remover/{idUsuario}")
    public String removerUsuario(@PathVariable Long idUsuario, Model model) {
        usuarioService.removerUsuario(idUsuario);

        Iterable<Usuario> usuarios = usuarioService.todos();
        model.addAttribute("usuarios", usuarios);

        return "tabelaUsuarios";
    }
}
