package dgn.com.br.sgco.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dgn.com.br.sgco.service.AgendamentoService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RelatorioFinanceiroController {
    @Autowired
    AgendamentoService agendamentoService;

    @GetMapping("/relatorioFinanceiro")
    public String dadosFinanceiros(@NonNull Model model) {

        model.addAttribute("mesesValores", agendamentoService.porMes());

        return "relatorioFinanceiro";
    }

    @RequestMapping(value = "/relatorio-financeiro/relatorio", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> dadosFinanceirosExcel() {
        Map<String, Float[]> mesesValores = agendamentoService.porMes();
        StringBuilder relatorioCsv = new StringBuilder("Mês; Acompanhamentos; Cirurgias; Consultas; Rendimentos\n");

        mesesValores.entrySet().stream()
                .map((mes) -> mes.getKey() + "; " + mes.getValue()[0] + "; " + mes.getValue()[1] + "; " + mes.getValue()[2] + "; " + mes.getValue()[3] + "\n")
                .forEach(relatorioCsv::append);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/csv");
        responseHeaders.set("Content-Encoding", "UTF-8");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(relatorioCsv.toString());
    }
}
