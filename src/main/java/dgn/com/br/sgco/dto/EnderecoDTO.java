package dgn.com.br.sgco.dto;

import dgn.com.br.sgco.entity.Endereco;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EnderecoDTO {
    @NotEmpty(message = "CEP não pode ser vazio")
    private String cep;

    @NotEmpty(message = "Número não pode ser vazio")
    private String numero;

    @NotEmpty(message = "Logradouro não pode ser vazio")
    private String logradouro;

    @NotEmpty(message = "Bairro não pode ser vazio")
    private String bairro;

    @NotEmpty(message = "Cidade não pode ser vazia")
    private String cidade;

    @NotEmpty(message = "UF não pode ser vazia")
    private String uf;

    public Endereco toEndereco() {
        Endereco endereco = new Endereco();

        endereco.setCep(getCep());
        endereco.setNumero(getNumero());
        endereco.setLogradouro(getLogradouro());
        endereco.setBairro(getBairro());
        endereco.setCidade(getCidade());
        endereco.setUf(getUf());

        return endereco;
    }
}
