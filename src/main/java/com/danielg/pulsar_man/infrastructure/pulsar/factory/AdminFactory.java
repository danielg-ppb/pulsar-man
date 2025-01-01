package com.danielg.pulsar_man.infrastructure.pulsar.factory;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminFactory {
    @Value("${pulsar.token}")
    private String token;

    private PulsarAdmin pulsarAdmin;

    public synchronized PulsarAdmin initializePulsarAdmin(String serviceUrl) throws PulsarClientException {
        if (this.pulsarAdmin != null) {
            this.pulsarAdmin.close();
        }

        System.out.println(token);
        this.pulsarAdmin = PulsarAdmin.builder()
                // .authentication(AuthenticationFactory.token("file:///Users/guilherme.daniel/Repos/pulsar-man/token.jwt"))
                .serviceHttpUrl(serviceUrl)
                .build();

        return this.pulsarAdmin;
    }

    public synchronized PulsarAdmin getPulsarAdmin() {
        return this.pulsarAdmin;
    }
}
