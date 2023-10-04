package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }

    @GetMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("admin")) {
            return "indexAdmin";
        }

        String cpf = auth.getName();
        Usuario usuario = usuarioRepository.findByCpf(cpf).get();
        model.addAttribute("usuario", usuario);

        if (usuario.getPaciente() != null) {
            return "indexPaciente";
        }
        return "indexDentista";
    }
}
