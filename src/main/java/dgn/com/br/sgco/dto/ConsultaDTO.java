package dgn.com.br.sgco.dto;

import dgn.com.br.sgco.entity.Agendamento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConsultaDTO{
   
    private String descricao;
}