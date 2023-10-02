package dgn.com.br.sgco.entity;

import dgn.com.br.sgco.arq.Entidade;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Data
@EqualsAndHashCode
public class Pessoa extends Entidade {
    private String nome;

    private String email;

    private String celular;

    private String cpf;

    private String rg;

    private Date dataNascimento;

    @ManyToOne
    private Genero genero;

    @OneToOne
    private Endereco endereco;
}
