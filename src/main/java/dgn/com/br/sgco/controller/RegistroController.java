package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.dto.RegistroPacienteDto;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public String cadastrar(final @Valid RegistroPacienteDto registroPacienteDto, @NonNull BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registroPacienteDto", registroPacienteDto);
            return "/registro";
        }

        Usuario usuario = usuarioService.registrarPaciente(registroPacienteDto);
        return "/login";
    }
}
