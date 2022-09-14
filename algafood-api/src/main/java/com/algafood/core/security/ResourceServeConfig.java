package com.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration // classe de configuração do spring
@EnableWebSecurity
public class ResourceServeConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //inicia configurações de autorização de acesso as requisições
                    .anyRequest().authenticated() //autoriza todas requisições autenticada
                .and()
                .cors().and()
                .oauth2ResourceServer() //habilitando resource server na api
                    .jwt();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        var secretKey = new SecretKeySpec("d9721qghe982gqhr98qwhaouidhas89dh80hj389024y890sahd98iuawsgh81q2".getBytes(),
                "HmacSHA256");

        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

}
