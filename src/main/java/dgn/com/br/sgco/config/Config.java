package dgn.com.br.sgco.config;

import dgn.com.br.sgco.entity.Dentista;
import dgn.com.br.sgco.entity.Paciente;
import dgn.com.br.sgco.entity.Pessoa;
import dgn.com.br.sgco.entity.Usuario;
import dgn.com.br.sgco.enumeration.Papel;
import dgn.com.br.sgco.repository.DentistaRepository;
import dgn.com.br.sgco.repository.PacienteRepository;
import dgn.com.br.sgco.repository.PessoaRepository;
import dgn.com.br.sgco.repository.UsuarioRepository;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class Config {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    DentistaRepository dentistaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    private AuthProvider authProvider;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authorize) -> {
            authorize.requestMatchers(mvc.pattern("/dentista**")).hasRole("dentista");
            authorize.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
            authorize.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll();
            authorize.requestMatchers(mvc.pattern("/login**"), mvc.pattern("/cadastro/paciente"), mvc.pattern("/js**"), mvc.pattern("/css**"), mvc.pattern("/images**")).permitAll();
            authorize.anyRequest().authenticated();
        });
        http.formLogin(form -> {
            form.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/", true);
            form.usernameParameter("cpf").passwordParameter("senha");
            form.failureUrl("/login?error=true");
        });
        http.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.deleteCookies("JSESSIONID");
        });

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
        criarUsuariosPadrao();
    }

    public void criarUsuariosPadrao() {
        Usuario admin = new Usuario();
        admin.setSenha("123");
        Pessoa pessoaAdmin = new Pessoa();
        pessoaAdmin.setCpf("admin");
        pessoaAdmin.setNome("Admin");
        admin.setPessoa(pessoaAdmin);
        admin.setPapel(Papel.ADMIN);
        pessoaRepository.save(admin.getPessoa());
        usuarioRepository.save(admin);

        Usuario dentista = new Usuario();
        dentista.setSenha("123");
        Pessoa pessoaDentista = new Pessoa();
        pessoaDentista.setCpf("123.456.789-09");
        pessoaDentista.setNome("Dentista");
        dentista.setPessoa(pessoaDentista);
        Dentista dentistaAux = new Dentista();
        dentistaAux.setPessoa(pessoaDentista);
        dentista.setDentista(dentistaAux);
        dentista.setPapel(Papel.DENTISTA);
        pessoaRepository.save(pessoaDentista);
        dentistaRepository.save(dentistaAux);
        usuarioRepository.save(dentista);

        Usuario paciente = new Usuario();
        paciente.setSenha("123");
        Pessoa pessoaPaciente = new Pessoa();
        pessoaPaciente.setCpf("135.477.713-15");
        pessoaPaciente.setNome("Paciente");
        paciente.setPessoa(pessoaPaciente);
        Paciente pacienteAux = new Paciente();
        pacienteAux.setPessoa(pessoaPaciente);
        paciente.setPaciente(pacienteAux);
        paciente.setPapel(Papel.PACIENTE);
        pessoaRepository.save(pessoaPaciente);
        pacienteRepository.save(pacienteAux);
        usuarioRepository.save(paciente);
    }
}