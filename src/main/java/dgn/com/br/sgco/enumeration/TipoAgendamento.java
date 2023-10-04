package dgn.com.br.sgco.enumeration;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public enum TipoAgendamento {
    CONSULTA( "Consulta"),
    CIRURGIA( "Cirurgia"),
    ACOMPANHAMENTO( "Acompanhamento");

    @Getter
    @Setter
    private String descricao;

    private TipoAgendamento(String descricao) { this.setDescricao(descricao); }
}
