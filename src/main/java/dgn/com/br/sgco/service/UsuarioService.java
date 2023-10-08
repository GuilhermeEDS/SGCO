package dgn.com.br.sgco.service;

import dgn.com.br.sgco.arq.ValidacaoEntidadeException;
import dgn.com.br.sgco.entity.Endereco;
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

        Endereco endereco = usuario.getPessoa().getEndereco();
        if (endereco != null) {
            enderecoRepository.save(endereco);
        }

        pessoaRepository.save(usuario.getPessoa());

        switch (usuario.getPapel()) {
            case DENTISTA -> {
                dentistaRepository.save(usuario.getDentista());
            }
            case PACIENTE -> {
                pacienteRepository.save(usuario.getPaciente());
            }
        }

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> porCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    public Iterable<Usuario> todos() {
        return usuarioRepository.findAll();
    }
}
