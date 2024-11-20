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
                        // Páginas públicas
                        .requestMatchers("/", "/home/**", "/shop/products/**", "/shop/product-detail/**", "/auth/login", "/auth/register")
                        .permitAll()
                        // Autenticación y recursos relacionados
                        .requestMatchers("/auth/**", "/error")
                        .permitAll()
                        // Recursos estáticos
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/assets/**", "/webjars/**")
                        .permitAll()
                        // Carrito requiere usuario logueado
                        .requestMatchers("/cart/**")
                        .authenticated()
                        // Perfil y Checkout requieren usuario logueado
                        .requestMatchers("/profile/**", "/checkout/**")
                        .authenticated()
                        // Panel de administración requiere rol ADMIN
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        // Cualquier otra ruta requiere autenticación
                        .anyRequest()
                        .authenticated()
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
