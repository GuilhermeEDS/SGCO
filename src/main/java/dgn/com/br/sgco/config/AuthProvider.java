package dgn.com.br.sgco.config;

import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String cpf = authentication.getName();
        String senha = authentication.getCredentials().toString();
        Usuario usuario = usuarioRepository.findByCpf(cpf);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(cpf, senha, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
