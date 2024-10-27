package com.danielg.pulsar_man.state;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.stereotype.Component;

@Component
public class InMemoryPulsarAdminState {
    private PulsarAdmin pulsarAdmin;

    public synchronized void initializePulsarAdmin(String serviceUrl) throws PulsarClientException {
        if (this.pulsarAdmin != null) {
            this.pulsarAdmin.close();
        }
        this.pulsarAdmin = PulsarAdmin.builder().serviceHttpUrl(serviceUrl).build();
    }

    public synchronized PulsarAdmin getPulsarAdmin() {
        return this.pulsarAdmin;
    }
}
