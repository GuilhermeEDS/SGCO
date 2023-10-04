package dgn.com.br.sgco.service;

import dgn.com.br.sgco.arq.ValidacaoEntidadeException;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    DentistaRepository dentistaRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        Optional<Usuario> u = usuarioRepository.findByEmail(usuario.getPessoa().getEmail());
        if (u.isPresent()) {
            throw new ValidacaoEntidadeException("pessoaDTO.email", "E-mail já existe");
        }

        u = usuarioRepository.findByCpf(usuario.getPessoa().getCpf());
        if (u.isPresent()) {
            throw new ValidacaoEntidadeException("pessoaDTO.cpf", "CPF já existe");
        }

        if (usuario.getPessoa().getEndereco() != null) {
            enderecoRepository.save(usuario.getPessoa().getEndereco());
        }
        pessoaRepository.save(usuario.getPessoa());

        if (usuario.getPaciente() != null) {
            pacienteRepository.save(usuario.getPaciente());
        }
        if (usuario.getDentista() != null) {
            dentistaRepository.save(usuario.getDentista());
        }

        return usuarioRepository.save(usuario);
    }
}
