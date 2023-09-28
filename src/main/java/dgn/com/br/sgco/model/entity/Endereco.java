package dgn.com.br.sgco.model.entity;

import dgn.com.br.sgco.arq.Entidade;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Endereco extends Entidade {
    private String cep;

    private String cidade;

    private String estado;

    private String endereco; // TODO: renomear para algo melhor descritivo

    private String numero; // TODO: renomear. Nem todas as casas possuem número
}
