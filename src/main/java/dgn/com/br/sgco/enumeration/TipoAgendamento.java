package dgn.com.br.sgco.enumeration;

import lombok.Getter;

public enum TipoAgendamento {
    CONSULTA("Consulta"), CIRURGIA("Cirurgia"), ACOMPANHAMENTO("Acompanhamento");

    @Getter
    private final String descricao;

    TipoAgendamento(String descricao) {
        this.descricao = descricao;
    }
}
