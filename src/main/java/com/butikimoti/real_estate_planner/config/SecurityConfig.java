package com.butikimoti.real_estate_planner.config;

import com.butikimoti.real_estate_planner.repository.UserEntityRepository;
import com.butikimoti.real_estate_planner.service.util.CurrentUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

                                //access to all
                                .requestMatchers("/", "/users/login", "/users/login-error").permitAll()

                                //access to only ADMIN role users
                                .requestMatchers(
                                        "/companies", "/companies/register",
                                        "/users"
                                ).hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/companies/*",
                                        "/users/*"
                                ).hasRole("ADMIN")

                                //access GET requests to all logged users
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/users/*", "/users/*/edit", "/users/*/changePassword",
                                        "/properties/sales", "/properties/rents", "/properties/*"
                                    ).hasAnyRole("ADMIN", "COMPANY_ADMIN", "USER")
                                //access PATCH requests to all logged users
                                .requestMatchers(HttpMethod.PATCH, "/users/*", "/users/*/changePassword").hasAnyRole("ADMIN", "COMPANY_ADMIN", "USER")
                                //access POST requests to all logged users
                                .requestMatchers(HttpMethod.POST, "/properties/*/add-comment").hasAnyRole("ADMIN", "COMPANY_ADMIN", "USER")

                                //access GET requests ADMIN and COMPANY_ADMIN
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/companies/*", "/companies/*/edit",
                                        "/properties/add", "/properties/*/edit"
                                    ).hasAnyRole("ADMIN", "COMPANY_ADMIN")
                                //access PATCH requests ADMIN and COMPANY_ADMIN
                                .requestMatchers(
                                        HttpMethod.PATCH,
                                        "/companies/*",
                                        "/properties/*"
                                    ).hasAnyRole("ADMIN", "COMPANY_ADMIN")
                                //access PUT requests ADMIN and COMPANY_ADMIN
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/companies/*/replace-logo"
                                    ).hasAnyRole("ADMIN", "COMPANY_ADMIN")
                                //access POST requests ADMIN and COMPANY_ADMIN
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/companies/*/upload-logo",
                                        "/properties/add", "/properties/*/upload-picture"
                                    ).hasAnyRole("ADMIN", "COMPANY_ADMIN")
                                //access DELETE requests ADMIN and COMPANY_ADMIN
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/properties/*/delete-picture/*"
                                    ).hasAnyRole("ADMIN", "COMPANY_ADMIN")

                                

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
