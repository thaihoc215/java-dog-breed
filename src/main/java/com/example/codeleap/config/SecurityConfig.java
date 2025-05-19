package com.example.codeleap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/secure/breeds/**").authenticated()
                        .anyRequest().permitAll())
                .httpBasic(withDefaults());
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordEncoder noPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("user")
                        .password("{noop}password")  // Use {noop} prefix for plain text
                        .roles("USER")
                        .build());
        return manager;
    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        SecretKeySpec secretKey = new SecretKeySpec(
//                "your-super-secret-key-your-super-secret-key".getBytes(),
//                "HmacSHA256");
//        return NimbusJwtDecoder.withSecretKey(secretKey).build();
//    }
}
