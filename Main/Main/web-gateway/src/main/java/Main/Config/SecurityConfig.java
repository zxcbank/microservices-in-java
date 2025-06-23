//package Config;
//
//import jakarta.servlet.DispatcherType;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(AbstractHttpConfigurer::disable)
////                .sessionManagement(session -> session
////                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .httpBasic(Customizer.withDefaults())
////                .authorizeHttpRequests(auth -> auth
////
////                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
////                        .requestMatchers("/api/v1/users/reg").permitAll()
////                        .requestMatchers("/api/v1/users/login").permitAll()
////
////
////                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
////                        .requestMatchers(HttpMethod.GET, "/api/v1/cats/**").permitAll()
////
////
////                        .requestMatchers(HttpMethod.DELETE, "/api/v1/cats").hasRole("ADMIN")
////                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users").hasRole("ADMIN")
////
////
////                        .requestMatchers("/api/v1/cats").authenticated()
////                        .requestMatchers("/api/v1/cats/**").authenticated()
////                        .requestMatchers("/api/v1/users").authenticated()
////                        .requestMatchers("/api/v1/users/**").authenticated()
////
////
////                        .anyRequest().denyAll()
////                );
////
////        return http.build();
//        http
//                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll() // Разрешаем все запросы
//                );
//        return http.build();
//    }
//}
