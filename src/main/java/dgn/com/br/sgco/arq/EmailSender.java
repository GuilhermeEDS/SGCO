package dgn.com.br.sgco.arq;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Environment environment;

    public boolean enviarEmail(String para, String titulo, String textoMensagem) {
        try {
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper ajudante = new MimeMessageHelper(mensagem);
            ajudante.setFrom(environment.getProperty("spring.mail.username"));
            ajudante.setTo(para);
            ajudante.setSubject(titulo);
            ajudante.setText(textoMensagem, true);
            javaMailSender.send(mensagem);
            return true;
        } catch (MessagingException exception) {
            exception.printStackTrace();
            return false;
        }
    }

}
