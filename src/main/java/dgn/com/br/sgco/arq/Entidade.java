package dgn.com.br.sgco.arq;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
public abstract class Entidade {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    private Date dataCadastro;

    public Entidade(Integer id) {
        this.id = id;
        this.dataCadastro = new Date();
    }

    public Entidade() {
        this.dataCadastro = new Date();
    }
}
