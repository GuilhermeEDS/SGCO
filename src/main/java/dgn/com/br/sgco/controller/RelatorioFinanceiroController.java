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

    @GetMapping("/relatorio")
    public String dadosFinanceiros(@NonNull Model model) {
        Map<String, Integer[]> mesesValores = new LinkedHashMap<>();
        mesesValores.put("Setembro", new Integer[]{19, 18, 17});
        mesesValores.put("Outubro", new Integer[]{14, 15, 16});
        mesesValores.put("Novembro", new Integer[]{13, 12, 13});
        
        // Adicionando o mapa ao modelo
        model.addAttribute("mesesValores", agendamentoService.porMes());

        return "relatorioFinanceiro";
    }
    
}
