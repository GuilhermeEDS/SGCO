package dgn.com.br.sgco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("dgn.com.br.sgco.*")
@ComponentScan(basePackages = { "dgn.com.br.sgco.*" })
@EntityScan("dgn.com.br.sgco.*")
public class SgcoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SgcoApplication.class, args);
    }
}
