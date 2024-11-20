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
                // Rutas públicas (sin autenticación)
                .requestMatchers("/", "/home/**", "/shop/products/**", "/shop/product-detail/**", "/auth/login", "/auth/register")
                    .permitAll()
                .requestMatchers("/auth/**", "/error")
                    .permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/assets/**", "/webjars/**")
                    .permitAll()

                // Rutas del carrito y perfil de usuario que requieren autenticación
                .requestMatchers("/cart/**")
                    .authenticated()
                .requestMatchers("/profile/**", "/checkout/**")
                    .authenticated()

                .requestMatchers("/admin/**")  
                    .permitAll()

                // Cualquier otra ruta debe estar autenticada
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
