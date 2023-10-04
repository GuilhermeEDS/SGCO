package dgn.com.br.sgco.dto;

import dgn.com.br.sgco.entity.Dentista;
import dgn.com.br.sgco.entity.Pessoa;
import dgn.com.br.sgco.entity.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CadastroDentistaDTO {
    @Valid
    private PessoaDTO pessoaDTO;

    @NotEmpty(message = "Senha não pode ser vazia")
    private String senha;

    public Usuario toUsuario() {
        Pessoa pessoa = getPessoaDTO().toPessoa();

        Dentista dentista = new Dentista();
        dentista.setPessoa(pessoa);

        Usuario usuario = new Usuario();
        usuario.setSenha(getSenha());
        usuario.setPessoa(pessoa);
        usuario.setDentista(dentista);
        usuario.setPaciente(null);

        return usuario;
    }
}
