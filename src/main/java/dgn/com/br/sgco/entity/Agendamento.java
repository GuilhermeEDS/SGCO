package dgn.com.br.sgco.entity;

import dgn.com.br.sgco.arq.Entidade;
import dgn.com.br.sgco.enumeration.FormaPagamento;
import dgn.com.br.sgco.enumeration.TipoAgendamento;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Data
@EqualsAndHashCode
public class Agendamento extends Entidade {
    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Dentista dentista;

    private Boolean confirmacao;

    private TipoAgendamento tipo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataConsulta;

    @DateTimeFormat(pattern = "HH:mm")
    private Date horaConsulta;

    private Date horaFim;

    private String observacoesPaciente;

    private String observacoesDentista;

    private FormaPagamento formaPagamento;

    public int getWeek() {
        int dayWeek = 0;
        GregorianCalendar gc = new GregorianCalendar();
        try {
            gc.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(this.getDataConsulta().toString()));
            switch (gc.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    dayWeek = 0;
                    break;
                case Calendar.MONDAY:
                    dayWeek = 1;
                    break;
                case Calendar.TUESDAY:
                    dayWeek = 2;
                    break;
                case Calendar.WEDNESDAY:
                    dayWeek = 3;
                    break;
                case Calendar.THURSDAY:
                    dayWeek = 4;
                    break;
                case Calendar.FRIDAY:
                    dayWeek = 5;
                    break;
                case Calendar.SATURDAY:
                    dayWeek = 6;

            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dayWeek;
    }
}
