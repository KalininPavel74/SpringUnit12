package home.kalinin.web_client1;

import home.kalinin.web_client1.access.User1Repository;
import home.kalinin.web_client1.access.security.User1;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Aspect
@Slf4j
@SpringBootApplication
public class MainWebClient1 {
    public static void main(String[] args) {
        SpringApplication.run(MainWebClient1.class, args);
    }
    /**
     * Создание экземпляра простого синхронного клиента REST API, предоставляемое ядром Spring Framework.
     * @return экземпляр REST клиента
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner dataLoader(PasswordEncoder passwordEncoder, User1Repository userRepo) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                User1 user1 = new User1("1",passwordEncoder.encode("1"),"Пользователь по умолчанию");
                userRepo.save(user1);
            }
        };
    }
}