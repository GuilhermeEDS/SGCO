package dgn.com.br.sgco.model.entity;

import dgn.com.br.sgco.arq.Entidade;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Pessoa extends Entidade {
    private String nome;

    private String email;

    private String celular;

    private String cpf;

    private String rg;

    private Date dataNascimento;

    @ManyToOne
    private Genero genero;

    private String observacoes; // Talvez não precise

    @OneToOne
    private Endereco endereco;
}
