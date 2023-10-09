package dgn.com.br.sgco.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AgendamentoDentistaDTO {

    @NotEmpty(message = "Campo não pode ser vazio")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Horárío deve estar no formato 00:00")
    private String horaFim;

    private String observacoesDentista;
}
