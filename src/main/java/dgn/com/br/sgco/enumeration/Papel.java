package dgn.com.br.sgco.enumeration;

import lombok.Getter;

public enum Papel {
    ADMIN("Administrador"),
    DENTISTA("Dentista"),
    PACIENTE("Paciente");

    @Getter
    private final String descricao;

    Papel(String descricao) {
        this.descricao = descricao;
    }
}
