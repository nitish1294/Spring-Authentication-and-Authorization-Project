package com.securityjpa.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class securityconfigure {

    @Autowired
    private myuserdetailservice myUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .authorizeHttpRequests(registry -> {
                registry.requestMatchers("/").permitAll(); // Publicly accessible root
                registry.requestMatchers("/admin/**").hasRole("ADMIN"); // Admin access
                registry.requestMatchers("/user/**").hasRole("USER"); // User access
                registry.anyRequest().authenticated(); // All other requests require authentication
            })
            // Configure form login
            .formLogin(form -> form
                .loginPage("/login") // Custom login page
                .loginProcessingUrl("/login") // Login processing URL
                .successHandler(customSuccessHandler()) // Custom success handler for role-based redirection
                .failureUrl("/login?error=true") // Redirect if login fails
                .permitAll()
            )
            // Configure logout
            .logout(logout -> logout
                .logoutUrl("/logout") // The URL for logging out
                .logoutSuccessUrl("/login?logout=true") // Redirect after successful logout
                .invalidateHttpSession(true) // Invalidate the session
                .clearAuthentication(true) // Clear authentication
                .permitAll()
            )
            .csrf().disable() // Disable CSRF protection
            .build();
    }

    // Custom Authentication Success Handler for role-based redirection
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            authentication.getAuthorities().forEach(authority -> {
                try {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        response.sendRedirect("/admin"); // Redirect to admin page
                    } else if (authority.getAuthority().equals("ROLE_USER")) {
                        response.sendRedirect("/user"); // Redirect to user page
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return myUserDetailService;  // Return the custom user details service
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}