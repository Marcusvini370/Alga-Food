package com.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Setter
@Getter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    // Atribuimos FAKE como padrão
    // Isso evita o problema de enviar e-mails de verdade caso você esqueça
    // de definir a propriedade
    private Implementacao impl = Implementacao.FAKE;
    private Sandbox sandbox = new Sandbox();

    @NotNull
    private String remetente;


    public enum Implementacao {
        SMTP, FAKE, SANDBOX
    }

    @Getter
    @Setter
    public class Sandbox {

        private String destinatario;

    }

}
