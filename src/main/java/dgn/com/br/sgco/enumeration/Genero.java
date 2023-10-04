package dgn.com.br.sgco.enumeration;

import lombok.Getter;

public enum Genero {
    NAO_INFORMADO("Não informado"), Feminino("Feminino"), Masculino("Masculino"), Outro("Outro");

    @Getter
    private final String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }
}
