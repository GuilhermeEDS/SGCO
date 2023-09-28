package dgn.com.br.sgco.model.entity;

import dgn.com.br.sgco.arq.Entidade;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class FormaPagamento extends Entidade {
    /*
    Como deixar registrado na entidade de uma forma ótima? ¯\_(ツ)_/¯

    - Dinheiro
    - Cheque?
    - Cartão de débito
    - Cartão de crédito
    - Boleto
    - Pix
    */
    private String descricao;
}
