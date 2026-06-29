package com.uni.lostandfound.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Note: CSRF disabled for H2 console access in dev, but ideally should be enabled in prod as per requirements. We will just disable for simplicity here, but the PDF asked to "Implement CSRF protection for form posts". Spring Security enables it by default. So let's enable it and ignore h2-console or exempt it.
            // Let's re-enable CSRF and exempt h2-console
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
            )
            .authorizeHttpRequests((authorize) ->
                    authorize.requestMatchers("/register/**").permitAll()
                            .requestMatchers("/index").permitAll()
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/page/**", "/about", "/privacy", "/terms", "/contact").permitAll()
                            .requestMatchers("/css/**", "/js/**", "/images/**", "/uploads/**").permitAll()
                            .requestMatchers("/h2-console/**").permitAll()
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                            .anyRequest().authenticated()
            ).formLogin(
                    form -> form
                            .loginPage("/login")
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/", true)
                            .permitAll()
            ).logout(
                    logout -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .permitAll()
            )
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // For H2 Console

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
