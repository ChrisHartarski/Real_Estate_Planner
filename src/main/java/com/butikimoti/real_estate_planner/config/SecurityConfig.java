package com.butikimoti.real_estate_planner.config;

import com.butikimoti.real_estate_planner.repository.UserEntityRepository;
import com.butikimoti.real_estate_planner.service.impl.CurrentUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                //grant access to static resources
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                //access to all users
                                .requestMatchers("/", "/users/login", "/users/register", "/companies/register", "/properties").permitAll()
                                //access to ADMIN role users
                                .requestMatchers("/admin-panel").hasRole("ADMIN")
                                //access to COMPANY_ADMIN role users
                                .requestMatchers("/companies/edit").hasRole("COMPANY_ADMIN")
                                //access to USER role users
                                .requestMatchers("/properties", "/properties/{id}").hasRole("USER")
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/users/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/users/login-error")
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/users/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                )
                .build();
    }

    @Bean
    public CurrentUserDetailsService currentUserDetailsService(UserEntityRepository userEntityRepository) {
        return new CurrentUserDetailsService(userEntityRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
