package com.danielg.pulsar_man.application.service.client;

import com.danielg.pulsar_man.application.port.input.client.GetClientStateUseCase;
import com.danielg.pulsar_man.application.service.ConsumerService;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.PulsarClientFactory;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.PulsarConsumerFactory;
import org.apache.pulsar.client.impl.conf.ClientConfigurationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClientStateService implements GetClientStateUseCase {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private final PulsarClientFactory pulsarClientManagerState;
    private final PulsarConsumerFactory pulsarConsumerState;

    public ClientStateService(PulsarClientFactory pulsarClientManagerState, PulsarConsumerFactory pulsarConsumerState) {
        this.pulsarClientManagerState = pulsarClientManagerState;
        this.pulsarConsumerState = pulsarConsumerState;
    }

    @Override
    public String getClientState() throws NoSuchFieldException, IllegalAccessException {
        if (this.pulsarClientManagerState.getPulsarClientProvider() != null && this.pulsarClientManagerState.getPulsarClientProvider().getPulsarClient() != null) {
            var confField = this.pulsarClientManagerState.getPulsarClientProvider().getPulsarClient().getClass().getDeclaredField("conf");
            confField.setAccessible(true);
            ClientConfigurationData configData = (ClientConfigurationData) confField.get(this.pulsarClientManagerState.getPulsarClientProvider().getPulsarClient());

            return "Service URL: " + configData.getServiceUrl() + "\n" +
                    this.pulsarConsumerState.getPulsarConsumer().toString();
        }

        logger.error("Pulsar client is not initialized.");

        return "";
    }
}
