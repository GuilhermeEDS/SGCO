package dgn.com.br.sgco.dto;

import dgn.com.br.sgco.enumeration.Genero;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GeneroDTO {
    @Min(value = 0, message = "Gênero não pode ser vazio")
    private Integer id;

    public Genero toGenero() {
        return Genero.values()[id];
    }
}
