package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.arq.ValidacaoEntidadeException;
import dgn.com.br.sgco.dto.RegistroPacienteDTO;
import dgn.com.br.sgco.entity.Genero;
import dgn.com.br.sgco.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String paginaRegistro(@NonNull Model model) {
        RegistroPacienteDTO registroPacienteDto = new RegistroPacienteDTO();
        model.addAttribute("registroPacienteDTO", registroPacienteDto);
        model.addAttribute("generos", Genero.values());
        return "registro";
    }

    @PostMapping("/registro")
    public String cadastrar(final @Valid RegistroPacienteDTO registroPacienteDTO, @NonNull BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registroPacienteDTO", registroPacienteDTO);
            model.addAttribute("generos", Genero.values());
            return "/registro";
        }

        try {
            usuarioService.registrarPaciente(registroPacienteDTO);
        } catch (ValidacaoEntidadeException e) {
            result.rejectValue(e.getCampo(), "", e.getMessage());

            model.addAttribute("registroPacienteDTO", registroPacienteDTO);
            model.addAttribute("generos", Genero.values());
            return "/registro";
        }

        return "/login";
    }
}
