package dgn.com.br.sgco.arq;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@MappedSuperclass
@Data
public abstract class Entidade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date dataCadastro;

    @Column(nullable = false)
    private boolean ativo;

    public Entidade(Long id) {
        this.id = id;
        this.dataCadastro = new Date();
        this.ativo = true;
    }

    public Entidade() {
        this.dataCadastro = new Date();
        this.ativo = true;
    }
}
