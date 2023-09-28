package dgn.com.br.sgco.controller;

import dgn.com.br.sgco.model.request.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {
    @PostMapping("/login")
    public void login(@RequestBody LoginForm loginForm) {

    }

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }
}
