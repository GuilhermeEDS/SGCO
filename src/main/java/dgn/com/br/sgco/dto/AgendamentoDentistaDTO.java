package dgn.com.br.sgco.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class AgendamentoDentistaDTO {
    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "Hora do agendamento deve ser selecionado.")
    private Date horaFim;

    private String observacoesDentista;
}
