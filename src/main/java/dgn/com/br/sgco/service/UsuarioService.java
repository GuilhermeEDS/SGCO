package dgn.com.br.sgco.service;

import dgn.com.br.sgco.dto.RegistroPacienteDto;
import dgn.com.br.sgco.entity.Endereco;
import dgn.com.br.sgco.entity.Paciente;
import dgn.com.br.sgco.entity.Pessoa;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.repository.EnderecoRepository;
import dgn.com.br.sgco.repository.PacienteRepository;
import dgn.com.br.sgco.repository.PessoaRepository;
import dgn.com.br.sgco.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Usuario registrarPaciente(RegistroPacienteDto registroPacienteDto) {
        /*
        Checar se:
            - E-mail já é usado
            - Cpf já é usado
            - Rg já é usado
         */

        Endereco endereco = new Endereco();
        endereco.setCep(registroPacienteDto.getCep());
        endereco.setNumero(registroPacienteDto.getNumero());
        endereco.setLogradouro(registroPacienteDto.getLogradouro());
        endereco.setBairro(registroPacienteDto.getBairro());
        endereco.setCidade(registroPacienteDto.getCidade());
        endereco.setUf(registroPacienteDto.getUf());
        endereco = enderecoRepository.save(endereco);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(registroPacienteDto.getNome());
        pessoa.setEmail(registroPacienteDto.getEmail());
        pessoa.setCpf(registroPacienteDto.getCpf());
        pessoa.setRg(registroPacienteDto.getRg());
        pessoa.setDataNascimento(registroPacienteDto.getDataNascimento());
        pessoa.setGenero(registroPacienteDto.getGenero());
        pessoa.setEndereco(endereco);
        pessoa = pessoaRepository.save(pessoa);

        Paciente paciente = new Paciente();
        paciente.setPessoa(pessoa);
        paciente = pacienteRepository.save(paciente);

        Usuario usuario = new Usuario();
        usuario.setSenha(registroPacienteDto.getSenha());
        usuario.setPessoa(pessoa);
        usuario.setDentista(null);
        usuario.setPaciente(paciente);

        return usuarioRepository.save(usuario);
    }
}
