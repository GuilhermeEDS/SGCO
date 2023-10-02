package dgn.com.br.sgco.arq;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
@Data
public abstract class Entidade {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    private Date dataCadastro;

    public Entidade(Long id) {
        this.id = id;
        this.dataCadastro = new Date();
    }

    public Entidade() {
        this.dataCadastro = new Date();
    }
}
