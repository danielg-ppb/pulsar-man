package com.danielg.pulsar_man.model;

import com.danielg.pulsar_man.utils.PulsarSubcriptionUtils;
import com.danielg.pulsar_man.utils.SchemaProvider;
import lombok.Getter;
import lombok.Setter;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.shade.javax.annotation.PreDestroy;

@Getter
@Setter
public class PulsarClientProvider {
    private PulsarClient pulsarClient;

    public PulsarClientProvider(String serviceUrl) {
        try {
            this.pulsarClient = PulsarClient.builder()
                    .serviceUrl(serviceUrl)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
