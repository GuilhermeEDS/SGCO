package dgn.com.br.sgco.dto;

import dgn.com.br.sgco.entity.Paciente;
import dgn.com.br.sgco.entity.Pessoa;
import dgn.com.br.sgco.entity.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CadastroPacienteDTO {
    @Valid
    private PessoaDTO pessoaDTO;

    @NotEmpty(message = "Campo não pode ser vazio")
    private String senha;

    public Usuario toUsuario() {
        Pessoa pessoa = getPessoaDTO().toPessoa();

        Paciente paciente = new Paciente();
        paciente.setPessoa(pessoa);

        Usuario usuario = new Usuario();
        usuario.setSenha(getSenha());
        usuario.setPessoa(pessoa);
        usuario.setDentista(null);
        usuario.setPaciente(paciente);

        return usuario;
    }
}
