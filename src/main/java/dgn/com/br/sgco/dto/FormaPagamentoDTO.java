package dgn.com.br.sgco.dto;

import dgn.com.br.sgco.enumeration.FormaPagamento;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class FormaPagamentoDTO {
    @Min(value = 0, message = "Selecione a forma de pagamento.")
    private Integer id;

    public FormaPagamento toFormaPagamento() {
        return FormaPagamento.values()[id];
    }
}

