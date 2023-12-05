package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.arq.Mensagens;
import dgn.com.br.sgco.arq.ValidacaoEntidadeException;
import dgn.com.br.sgco.dto.CadastroDentistaDTO;
import dgn.com.br.sgco.dto.CadastroPacienteDTO;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.enumeration.Genero;
import dgn.com.br.sgco.enumeration.Papel;
import dgn.com.br.sgco.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CadastroController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastro/dentista")
    public String paginaCadastroDentista(@NonNull Model model) {
        CadastroDentistaDTO cadastroDentistaDTO = new CadastroDentistaDTO();
        model.addAttribute("cadastroDentistaDTO", cadastroDentistaDTO);
        model.addAttribute("generos", Genero.values());
        return "dentista/cadastro";
    }

    @PostMapping("/cadastro/dentista")
    public RedirectView cadastrarDentista(RedirectAttributes redirectAttributes, final @Valid CadastroDentistaDTO cadastroDentistaDTO, @NonNull BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cadastroDentistaDTO", cadastroDentistaDTO);
            model.addAttribute("generos", Genero.values());
            return new RedirectView("dentista/cadastro", true);
        }

        try {
            Usuario usuario = cadastroDentistaDTO.toUsuario();
            usuario.setPapel(Papel.DENTISTA);
            usuarioService.registrarUsuario(usuario);
        } catch (ValidacaoEntidadeException e) {
            result.rejectValue(e.getCampo(), "", e.getMessage());

            model.addAttribute("cadastroDentistaDTO", cadastroDentistaDTO);
            model.addAttribute("generos", Genero.values());
            return new RedirectView("dentista/cadastro", true);
        }

        Mensagens mensagens = new Mensagens();
        mensagens.adicionaSucesso("Dentista cadastrado com sucesso!");
        redirectAttributes.addFlashAttribute("mensagens", mensagens);

        return new RedirectView("/", true);
    }

    @GetMapping("/cadastro/paciente")
    public String paginaCadastroPaciente(@NonNull Model model) {
        CadastroPacienteDTO cadastroPacienteDTO = new CadastroPacienteDTO();
        model.addAttribute("cadastroPacienteDTO", cadastroPacienteDTO);
        model.addAttribute("generos", Genero.values());
        return "paciente/cadastro";
    }

    @PostMapping("/cadastro/paciente")
    public RedirectView cadastrarPaciente(RedirectAttributes redirectAttributes, final @Valid CadastroPacienteDTO cadastroPacienteDTO, @NonNull BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cadastroPacienteDTO", cadastroPacienteDTO);
            model.addAttribute("generos", Genero.values());
            return new RedirectView("paciente/cadastro", true);
        }

        try {
            Usuario usuario = cadastroPacienteDTO.toUsuario();
            usuario.setPapel(Papel.PACIENTE);
            usuarioService.registrarUsuario(usuario);
        } catch (ValidacaoEntidadeException e) {
            result.rejectValue(e.getCampo(), "", e.getMessage());

            model.addAttribute("cadastroPacienteDTO", cadastroPacienteDTO);
            model.addAttribute("generos", Genero.values());
            return new RedirectView("paciente/cadastro", true);
        }

        Mensagens mensagens = new Mensagens();
        mensagens.adicionaSucesso("Cadastrado com sucesso!");
        redirectAttributes.addFlashAttribute("mensagens", mensagens);

        return new RedirectView("/login", true);
    }
}
