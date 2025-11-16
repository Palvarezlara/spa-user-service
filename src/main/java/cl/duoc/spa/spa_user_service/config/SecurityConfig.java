package cl.duoc.spa.spa_user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Para no pelear con tokens ni sesiones por ahora
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Swagger abierto
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api-docs/**"
                        ).permitAll()
                        // Endpoints de tu API de usuarios abiertos por ahora
                        .requestMatchers("/api/v1/users/**").permitAll()
                        // Cualquier otra ruta, si la hubiera, requiere autenticación
                        .anyRequest().authenticated()
                )
                // Desactivamos el formulario de login por defecto
                .formLogin(AbstractHttpConfigurer::disable)
                // Dejamos HTTP Basic por si luego quieres probar algo rápido con Postman
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

