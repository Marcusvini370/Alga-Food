package com.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // classe de configuração do spring
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()           // Usuário em memória
                .withUser("Marcus")
                    .password(passwordEncoder().encode("123"))
                    .roles("ADMIN")
                .and()
                .withUser("joao")
                    .password(passwordEncoder().encode("123"))
                    .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic() //tipo de autorização, pode ser adicionado o de form ou otros tbm
                .and()
                .authorizeRequests() //inicia configurações de autorização de acesso as requisições
                .antMatchers("/v1/cozinhas/**").permitAll() // Libera esse endpoint para todos,usa-se antes do any
                .anyRequest().authenticated() //autoriza todas requisições autenticada

                .and()
                    .sessionManagement()                                        // Desativa o cookio v
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // não vai usar session na segurança

                .and()
                    .csrf().disable(); //melhoria da segurança com basic authentication
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // método que vai produzir uma instância do método PasswordEncoder
        return new BCryptPasswordEncoder(); // método de criptografia com segurança grande
    }

}
