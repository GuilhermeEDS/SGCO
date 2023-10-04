package dgn.com.br.sgco.dto;

import dgn.com.br.sgco.enumeration.TipoAgendamento;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class TipoAgendamentoDTO {
    @Min(value = 0, message = "Selecione um tipo de agendamento.")
    private Integer id;

    public TipoAgendamento toTipoAgendamento() {
        return TipoAgendamento.values()[id];
    }
}
