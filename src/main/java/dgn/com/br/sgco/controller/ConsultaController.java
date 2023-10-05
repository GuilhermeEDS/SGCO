package dgn.com.br.sgco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsultaController {
    
    @GetMapping("/consulta")
    public String getConsulta(){
        return "consulta";
    }
}
