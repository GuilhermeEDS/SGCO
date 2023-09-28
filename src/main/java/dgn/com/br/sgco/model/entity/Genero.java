package dgn.com.br.sgco.model.entity;

import dgn.com.br.sgco.arq.Entidade;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Genero extends Entidade {
    private String descricao;
}
