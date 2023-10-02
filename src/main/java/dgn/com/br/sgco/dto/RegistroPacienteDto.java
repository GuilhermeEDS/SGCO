package dgn.com.br.sgco.dto;

import dgn.com.br.sgco.entity.Genero;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RegistroPacienteDto {
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @NotEmpty(message = "CPF não pode ser vazio")
    private String cpf;

    @NotEmpty(message = "RG não pode ser vazio")
    private String rg;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private Date dataNascimento;

    private Genero genero;

    @NotEmpty(message = "Senha não pode ser vazia")
    private String senha;

    @NotEmpty(message = "Número de celular não pode ser vazio")
    private String celular;

    @NotEmpty(message = "E-mail não pode ser vazio")
    private String email;

    @NotEmpty(message = "CEP não pode ser vazio")
    private String cep;

    @NotEmpty(message = "Número não pode ser vazio")
    private String numero;

    @NotEmpty(message = "Logradouro não pode ser vazio")
    private String logradouro;

    @NotEmpty(message = "Bairro não pode ser vazio")
    private String bairro;

    @NotEmpty(message = "Cidade não pode ser vazio")
    private String cidade;

    @NotEmpty(message = "UF não pode ser vazio")
    private String uf;
}
