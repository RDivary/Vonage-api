package com.divary.vonageapi.configuration;

import com.vonage.client.VonageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class VonageClientConfiguration {

    @Value("${vonage-app-key-sms}")
    String appKey;

    @Value("${vonage-app-secret-sms}")
    String appSecret;

    @Value("${vonage-app-id-voice}")
    String appId;

    @Value("${vonage-private-key-path}")
    String privateKeyPath;

    @Bean
    public VonageClient vonageClientSms() {

        return VonageClient.builder().apiKey(appKey).apiSecret(appSecret).build();
    }

    @Bean
    public VonageClient vonageClientVoice() throws IOException {

        return VonageClient.builder()
                .applicationId(appId)
                .privateKeyPath(privateKeyPath)
                .build();
    }
}
