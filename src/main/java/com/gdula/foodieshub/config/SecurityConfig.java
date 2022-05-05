package com.gdula.foodieshub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * method: configureGlobalSecurity
     * Konfiguracja zabezpieczeń aplikacji
     */
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setPasswordEncoder(passwordEncoder);
        dap.setUserDetailsService(customUserDetailsService);
        auth.authenticationProvider(dap);
    }

    /**
     * method: configure
     * Konfiguracja zabezpieczeń aplikacji
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable() // wyłączenie zabezpieczenia przed CSRF
                .authorizeRequests() // autoryzuj wszystkie żądania
                .antMatchers("/admin/**").hasRole("ADMIN") // tylko użytkownik z rolą ADMIN ma dostęp do /admin/**
                .antMatchers("/create-user/**").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated() // wszystkie pozostałe zapytania dostępne tylko dla zalogowanych
                .and()
                .formLogin() // generowanie formularza logowania
                .loginPage("/login.html")
                .loginProcessingUrl("/login.html")
                .defaultSuccessUrl("/", true)
                .usernameParameter("login")
                .passwordParameter("pass")
                .permitAll()
                .and()
                .logout(); // dodanie wylogowania pod adresem /logout
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/vendor/**","/fonts/**");
    }
}
