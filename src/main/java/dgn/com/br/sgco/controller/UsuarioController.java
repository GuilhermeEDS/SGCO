package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.arq.Mensagens;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.service.AgendamentoService;
import dgn.com.br.sgco.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AgendamentoService agendamentoService;

    @GetMapping("/listagem/usuario")
    public String paginaListagemUsuarios(Model model) {
        Iterable<Usuario> usuarios = usuarioService.todos();
        model.addAttribute("usuarios", usuarios);

        return "admin/listagemUsuarios";
    }

    @GetMapping("/usuario/remover/{idUsuario}")
    public String paginaRemoverUsuarioModal(@PathVariable Integer idUsuario, Model model) {
        model.addAttribute("idUsuario", idUsuario);
        return "admin/removerUsuarioModal";
    }

    @GetMapping("/usuario/detalhes/{idUsuario}")
    public String paginaDetalhesUsuarioModal(@PathVariable Long idUsuario, Model model) {
        Optional<Usuario> usuario = usuarioService.porId(idUsuario);
        model.addAttribute("usuario", usuario.get());
        return "admin/detalhesUsuarioModal";
    }

    @PostMapping("/usuario/remover/{idUsuario}")
    public String removerUsuario(@PathVariable Long idUsuario, Model model) {
        usuarioService.removerUsuario(idUsuario);

        Iterable<Usuario> usuarios = usuarioService.todos();
        model.addAttribute("usuarios", usuarios);

        Mensagens mensagens = new Mensagens();
        mensagens.adicionaSucesso("Usuário removido com sucesso!");
        model.addAttribute("mensagens", mensagens);

        return "admin/tabelaUsuarios";
    }
}
