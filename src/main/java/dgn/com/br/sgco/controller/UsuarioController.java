package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.dto.RegistroPacienteDto;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.repository.UsuarioRepository;
import lombok.NonNull;
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
            return "admin";
        }

        String cpf = auth.getName();
        Usuario usuario = usuarioRepository.findByCpf(cpf);
        model.addAttribute("usuario", usuario);

        return "index";
    }

    @GetMapping("/registro")
    public String paginaRegistro(@NonNull Model model) {
        RegistroPacienteDto registroPacienteDto = new RegistroPacienteDto();
        model.addAttribute("registroPacienteDto", registroPacienteDto);
        return "registro";
    }
}
