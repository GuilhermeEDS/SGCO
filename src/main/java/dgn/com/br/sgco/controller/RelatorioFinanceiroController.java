package dgn.com.br.sgco.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dgn.com.br.sgco.service.AgendamentoService;
import lombok.NonNull;

@Controller
public class RelatorioFinanceiroController {
    @Autowired
    AgendamentoService agendamentoService;

    @GetMapping("/relatorioFinanceiro")
    public String dadosFinanceiros(@NonNull Model model) {

        model.addAttribute("mesesValores", agendamentoService.porMes());

        return "relatorioFinanceiro";
    }
    
}
