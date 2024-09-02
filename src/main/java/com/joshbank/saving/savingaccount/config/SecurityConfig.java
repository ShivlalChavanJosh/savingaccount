package com.joshbank.saving.savingaccount.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());


//                .csrf(csrf -> csrf.disable())  // Disable CSRF protection
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/customer/admin").permitAll()
//                        .anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
                //.formLogin(Customizer.withDefaults()); //

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        String encodedPassword = passwordEncoder().encode("admin@123");
          logger.info("Encoded password for 'admin': {}", encodedPassword);

        UserDetails admin = User.withUsername("admin")
                .password(encodedPassword)
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
