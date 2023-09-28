package dgn.com.br.sgco.model.entity;

import dgn.com.br.sgco.arq.Entidade;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CredencialAcesso extends Entidade {
    @OneToOne
    private Pessoa pessoa;

    private String senha;
}
