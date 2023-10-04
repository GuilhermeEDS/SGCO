package dgn.com.br.sgco.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.util.Date;

@Data
public class AgendamentoDTO {
    @Min(value = 0, message = "Selecione um dentista válido.")
    private Long idDentista;

    @Valid
    private TipoAgendamentoDTO tipo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    @NotNull(message = "Data do agendamento não pode ser vazia.")
    private Date dataConsulta;

    private Duration tempoEstimado;

    private String observacoesPaciente;

    private String observacoesDentista;

    @Valid
    private FormaPagamentoDTO formaPagamento;
}
