package dgn.com.br.sgco.arq;

import java.util.ArrayList;

import lombok.Data;
import lombok.Getter;

@Data
public class Mensagens {
    @Getter
    private ArrayList<String> sucessos;

    @Getter
    private ArrayList<String> avisos;

    @Getter
    private ArrayList<String> erros;

    public Mensagens() {
        sucessos = new ArrayList<String>();
        avisos = new ArrayList<String>();
        erros = new ArrayList<String>();
    }

    public Mensagens(ArrayList<String> sucessos, ArrayList<String> avisos, ArrayList<String> erros) {
        this.sucessos = sucessos;
        this.avisos = avisos;
        this.erros = erros;
    }

    public void adicionaSucesso(String descricao) {
        sucessos.add(descricao);
    }

    public void adicionaAviso(String descricao) {
        avisos.add(descricao);
    }

    public void adicionaErro(String descricao) {
        erros.add(descricao);
    }
}
