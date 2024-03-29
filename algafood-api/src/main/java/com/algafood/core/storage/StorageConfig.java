package com.algafood.core.storage;

import com.algafood.core.storage.StorageProperties.TipoStorage;
import com.algafood.domain.service.FotoStorageService;
import com.algafood.infracstruture.service.storage.LocalFotoStorageService;
import com.algafood.infracstruture.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean //método que produz uma instancia de amazonS3, se tornando um bean spring
    @ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta()); //chave que representa as credentials de acesso

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)) //passa as credenciais chave de acesso
                .withRegion(storageProperties.getS3().getRegiao()) //região que está ultilizando o serviço amazon
                .build(); //retorna uma instancia de amazon s3 podendo usa-lá com um component spring
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if (TipoStorage.S3.equals(storageProperties.getTipo())) {
            return new S3FotoStorageService();
        } else {
            return new LocalFotoStorageService();
        }
    }

}
