package Config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).authenticated()
                        .requestMatchers("/users/reg").permitAll()
                        .requestMatchers("/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cats/**").permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());


        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.DELETE, "/cats").hasRole("ADMIN")
                .requestMatchers("/cats").authenticated()
                .requestMatchers("/cats/**").authenticated()

        );

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.DELETE, "/users").hasRole("ADMIN")
                .requestMatchers("/users").authenticated()
                .requestMatchers("/users/**").authenticated()

        );

        http.authorizeHttpRequests(auth -> auth.anyRequest().denyAll());

        return http.build();
    }
}
