package com.portfolio.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Дефинираме правилата за сигурност (Кой докъде има достъп)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/contact", "/css/**", "/js/**").permitAll() // Публични страници и статични файлове
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Само за потребители с роля ADMIN
                        .anyRequest().authenticated() // Всичко останало изисква логване
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/admin", true) // Къде да ни прати след успешен вход
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/?logout") // Пренасочване с параметър след изход
                        .permitAll()
                );

        return http.build();
    }

    // 2. Създаваме администраторски потребител в паметта на приложението
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin") // Твоето потребителско име
                .password(passwordEncoder.encode("run")) // Твоята парола (кодирана)
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    // 3. Казваме на Spring да използва BCrypt за сигурно хеширане на паролите
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}