package dgn.com.br.sgco.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AgendamentoDTO {
    @Min(value = 0, message = "Selecione um dentista.")
    private Long idDentista;

    @Valid
    private TipoAgendamentoDTO tipo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Data do agendamento deve ser uma data futura.")
    @NotNull(message = "Data do agendamento não pode ser vazia.")
    private Date dataConsulta;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "Selecione a hora do agendamento.")
    private Date horaConsulta;

    private String observacoesPaciente;

    @Valid
    private FormaPagamentoDTO formaPagamento;
}
