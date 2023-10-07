package dgn.com.br.sgco.enumeration;

import lombok.Getter;

public enum FormaPagamento {
    DINHEIRO("Dinheiro"), CARTAO_DEBITO("Cartão de débito"), CARTAO_CREDITO("Cartão de crédito"), BOLETO("Boleto"), PIX("Pix");

    @Getter
    private final String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }
}
