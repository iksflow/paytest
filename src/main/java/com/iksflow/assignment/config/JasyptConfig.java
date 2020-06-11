package com.iksflow.assignment.config;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Security;

@Configuration
public class JasyptConfig {

    @Bean
    public StringEncryptor stringEncryptor() {
        Security.addProvider(new BouncyCastleProvider());
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setAlgorithm("PBEWITHSHA256AND256BITAES-CBC-BC");
        config.setPassword("testpassword");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
//        config.setProvider(Security.getProvider());
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("hexadecimal");
        encryptor.setConfig(config);

        return encryptor;
    }

}
