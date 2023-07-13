package pl.gr.newspringsecurity.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.gr.newspringsecurity.user.Permission;
import pl.gr.newspringsecurity.user.Role;

import static pl.gr.newspringsecurity.user.Permission.*;
import static pl.gr.newspringsecurity.user.Role.ADMIN;
import static pl.gr.newspringsecurity.user.Role.MANAGER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    public static final String MANAGEMENT_PATH = "/api/v1/management/**";
    public static final String ADMINISTRATOR_PATH = "/api/v1/admin";
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String LOGIN_REGISTER_PATH = "/api/v1/auth/**";


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(LOGIN_REGISTER_PATH).permitAll()
                .requestMatchers(MANAGEMENT_PATH).hasAnyRole(ADMIN.name(), MANAGER.name())
                .requestMatchers(HttpMethod.GET, MANAGEMENT_PATH).hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                .requestMatchers(HttpMethod.POST, MANAGEMENT_PATH).hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                .requestMatchers(HttpMethod.PUT, MANAGEMENT_PATH).hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                .requestMatchers(HttpMethod.DELETE, MANAGEMENT_PATH).hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

             //   .requestMatchers(ADMINISTRATOR_PATH).hasRole(ADMIN.name())
            //    .requestMatchers(HttpMethod.GET, ADMINISTRATOR_PATH).hasAuthority(ADMIN_READ.name())
            //    .requestMatchers(HttpMethod.POST, ADMINISTRATOR_PATH).hasAuthority(ADMIN_CREATE.name())
            //    .requestMatchers(HttpMethod.PUT, ADMINISTRATOR_PATH).hasAuthority(ADMIN_UPDATE.name())
            //    .requestMatchers(HttpMethod.DELETE, ADMINISTRATOR_PATH).hasAuthority(ADMIN_DELETE.name())
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
