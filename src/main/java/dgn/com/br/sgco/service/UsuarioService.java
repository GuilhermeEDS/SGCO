package dgn.com.br.sgco.service;

import dgn.com.br.sgco.arq.ValidacaoEntidadeException;
import dgn.com.br.sgco.dto.RegistroPacienteDTO;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.repository.EnderecoRepository;
import dgn.com.br.sgco.repository.PacienteRepository;
import dgn.com.br.sgco.repository.PessoaRepository;
import dgn.com.br.sgco.repository.UsuarioRepository;
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
    EnderecoRepository enderecoRepository;

    public Usuario registrarPaciente(RegistroPacienteDTO registroPacienteDto) {
        Optional<Usuario> u = usuarioRepository.findByEmail(registroPacienteDto.getPessoaDTO().getEmail());
        if (u.isPresent()) {
            throw new ValidacaoEntidadeException("pessoaDTO.email", "E-mail já existe");
        }

        u = usuarioRepository.findByCpf(registroPacienteDto.getPessoaDTO().getCpf());
        if (u.isPresent()) {
            throw new ValidacaoEntidadeException("pessoaDTO.cpf", "CPF já existe");
        }

        Usuario usuario = registroPacienteDto.toUsuario();

        enderecoRepository.save(usuario.getPessoa().getEndereco());
        pessoaRepository.save(usuario.getPessoa());
        pacienteRepository.save(usuario.getPaciente());
        return usuarioRepository.save(usuario);
    }
}
