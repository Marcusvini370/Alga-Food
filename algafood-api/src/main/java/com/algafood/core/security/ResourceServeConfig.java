package com.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // classe de configuração do spring
@EnableWebSecurity
public class ResourceServeConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //inicia configurações de autorização de acesso as requisições
                    .anyRequest().authenticated() //autoriza todas requisições autenticada
                .and()
                .oauth2ResourceServer() //habilitando resource server na api
                    .opaqueToken();
    }
}
