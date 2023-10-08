package dgn.com.br.sgco.dto;

import dgn.com.br.sgco.entity.Endereco;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EnderecoDTO {
    @NotEmpty(message = "Campo não pode ser vazio")
    private String cep;

    @NotEmpty(message = "Campo não pode ser vazio")
    private String numero;

    @NotEmpty(message = "Campo não pode ser vazio")
    private String logradouro;

    @NotEmpty(message = "Campo não pode ser vazio")
    private String bairro;

    @NotEmpty(message = "Campo não pode ser vazio")
    private String cidade;

    @NotEmpty(message = "Campo não pode ser vazio")
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
