package com.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration // classe de configuração do spring
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // habilitar função de restrição nos métodos
public class ResourceServeConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
               /* .authorizeRequests() //inicia configurações de autorização de acesso as requisições
                    .antMatchers(HttpMethod.POST, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
                    .antMatchers(HttpMethod.PUT, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
                    .antMatchers(HttpMethod.GET, "/v1/cozinhas/**").authenticated()
                    .anyRequest().denyAll()  // nega todos acessos que não esteja autenticando os de cima
                .and()*/
                .cors().and()
                .oauth2ResourceServer() //habilitando resource server na api
                    .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

    }

    // converte a lista de String authorities do token jwt em Granted Authorities para transformar em permissões
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwt.getClaimAsStringList("authorities");

            if (authorities == null) {
                authorities = Collections.emptyList();
            }

            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });

        return jwtAuthenticationConverter;
    }


}
