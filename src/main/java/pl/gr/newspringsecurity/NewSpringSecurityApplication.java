package pl.gr.newspringsecurity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.gr.newspringsecurity.auth.AuthenticationService;
import pl.gr.newspringsecurity.auth.RegisterRequest;
import pl.gr.newspringsecurity.user.Role;

import java.sql.SQLOutput;

@SpringBootApplication
public class NewSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewSpringSecurityApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(Role.ADMIN)
                    .build();
            System.out.println("Admin token:: " + service.register(admin).getToken());
            var manager = RegisterRequest.builder()
                    .firstname("manage")
                    .lastname("manage")
                    .email("manager@mail.com")
                    .password("pmanage")
                    .role(Role.MANAGER)
                    .build();
            System.out.println("Manager token: " + service.register(manager).getToken());

        };
    }
}
