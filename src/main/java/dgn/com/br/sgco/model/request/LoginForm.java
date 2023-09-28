package dgn.com.br.sgco.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginForm {
    private String login;
    private String senha;
}
