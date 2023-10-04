package dgn.com.br.sgco.config;

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
public class SpringSecurity {
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
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123"));
    }
}