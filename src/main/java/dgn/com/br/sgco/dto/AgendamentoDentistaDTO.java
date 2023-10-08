package dgn.com.br.sgco.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class AgendamentoDentistaDTO {
    @DateTimeFormat(pattern = "HH:mm")
    @NotEmpty(message = "Adicione a hora do agendamento.")
    private String horaFim;

    private String observacoesDentista;
}
