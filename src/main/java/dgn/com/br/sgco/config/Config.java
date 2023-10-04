package dgn.com.br.sgco.config;

import dgn.com.br.sgco.entity.Dentista;
import dgn.com.br.sgco.entity.Paciente;
import dgn.com.br.sgco.entity.Pessoa;
import dgn.com.br.sgco.entity.Usuario;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authorize) -> {
            authorize.requestMatchers("/dentista**").hasRole("dentista");
            authorize.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
            authorize.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll();
            authorize.requestMatchers("/login**", "/cadastro/paciente", "/cadastro/paciente", "/js**", "/css**", "/images**").permitAll();
            authorize.requestMatchers(HttpMethod.POST, "/cadastro/paciente").permitAll();
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
        admin.setPessoa(pessoaAdmin);
        pessoaRepository.save(admin.getPessoa());
        usuarioRepository.save(admin);

        Usuario dentista = new Usuario();
        dentista.setSenha("123");
        Pessoa pessoaDentista = new Pessoa();
        pessoaDentista.setCpf("123.456.789-09");
        dentista.setPessoa(pessoaDentista);
        dentista.setDentista(new Dentista());
        dentistaRepository.save(dentista.getDentista());
        pessoaRepository.save(pessoaDentista);
        usuarioRepository.save(dentista);

        Usuario paciente = new Usuario();
        paciente.setSenha("123");
        Pessoa pessoaPaciente = new Pessoa();
        pessoaPaciente.setCpf("135.477.713-15");
        paciente.setPessoa(pessoaPaciente);
        paciente.setPaciente(new Paciente());
        pacienteRepository.save(paciente.getPaciente());
        pessoaRepository.save(pessoaPaciente);
        usuarioRepository.save(paciente);
    }
}