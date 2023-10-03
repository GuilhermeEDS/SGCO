package dgn.com.br.sgco.arq;

import lombok.Getter;

public class ValidacaoEntidadeException extends RuntimeException {
    @Getter
    String campo;

    public ValidacaoEntidadeException(String campo, String message) {
        super(message);
        this.campo = campo;
    }
}
