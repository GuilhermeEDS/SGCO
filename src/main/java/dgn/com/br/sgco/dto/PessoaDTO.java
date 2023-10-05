package dgn.com.br.sgco.dto;

import dgn.com.br.sgco.entity.Pessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PessoaDTO {
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @NotEmpty(message = "CPF não pode ser vazio")
    @Pattern(regexp = "^$|^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF deve estar no formato 000.000.000-00")
    private String cpf;

    @NotEmpty(message = "RG não pode ser vazio")
    private String rg;

    @NotEmpty(message = "Número de celular não pode ser vazio")
    private String celular;

    @NotEmpty(message = "E-mail não pode ser vazio")
    @Email(message = "E-mail deve ser válido")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Data de nascimento não pode ser no futuro")
    @NotNull(message = "Data de nascimento não pode ser vazia")
    private Date dataNascimento;

    @Valid
    private GeneroDTO generoDTO;

    @Valid
    private EnderecoDTO enderecoDTO;

    public Pessoa toPessoa() {
        Pessoa pessoa = new Pessoa();

        pessoa.setNome(getNome());
        pessoa.setCpf(getCpf());
        pessoa.setRg(getRg());
        pessoa.setCelular(getCelular());
        pessoa.setEmail(getEmail());
        pessoa.setDataNascimento(getDataNascimento());
        pessoa.setGenero(getGeneroDTO().toGenero());
        if (getEnderecoDTO() != null) {
            pessoa.setEndereco(getEnderecoDTO().toEndereco());
        }

        return pessoa;
    }
}
