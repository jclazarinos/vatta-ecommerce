package com.vatta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                // Rutas públicas que no requieren autenticación
                .requestMatchers(
                    "/",
                    "/home",
                    "/shop/**",
                    "/products/**",          // Permitir acceso a productos
                    "/product/**",           // Por si usas esta variante de la URL
                    "/product-detail/**",    // Para ver detalles de productos
                    "/auth/**",
                    "/register/**"
                ).permitAll()
                // Recursos estáticos
                .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/webjars/**",
                    "/error",
                    "/favicon.ico"
                ).permitAll()
                // Rutas que requieren autenticación
                .requestMatchers("/cart/**").authenticated()     // El carrito requiere usuario logueado
                .requestMatchers("/profile/**").authenticated()  // Perfil requiere usuario logueado
                .requestMatchers("/checkout/**").authenticated() // Checkout requiere usuario logueado
                // Rutas administrativas
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login-process")
                .defaultSuccessUrl("/", true)
                .failureUrl("/auth/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .logoutSuccessUrl("/?logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            )
            .exceptionHandling(handling -> handling
                .accessDeniedPage("/auth/access-denied")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}