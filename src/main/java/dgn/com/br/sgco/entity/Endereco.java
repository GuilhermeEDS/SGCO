package dgn.com.br.sgco.entity;

import dgn.com.br.sgco.arq.Entidade;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class Endereco extends Entidade {
    private String cep;

    private String numero;

    private String logradouro;

    private String bairro;

    private String cidade;

    private String uf;
}
