package dgn.com.br.sgco.enumeration;

import lombok.Getter;
import lombok.Setter;

public enum FormaPagamento {
    DINHEIRO( "Dinheiro"),
    CARTAO_DEBITO( "Cartão de débito"),
    CARTAO_CREDITO( "Cartão de crédito"),
    BOLETO( "Boleto"),
    PIX( "Pix");

    @Getter
    @Setter
    private String descricao;

    private FormaPagamento(String descricao) {
        this.setDescricao(descricao);
    }


}
