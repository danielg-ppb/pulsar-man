package com.danielg.pulsar_man.state;

import com.danielg.pulsar_man.model.PulsarConsumer;
import com.danielg.pulsar_man.dto.PulsarConsumerDto;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.apache.pulsar.client.api.Consumer;
import org.springframework.stereotype.Service;

@Service
@Getter
public class InMemoryPulsarConsumerState {
    private PulsarConsumer pulsarConsumer;

    public synchronized void initializePulsarConsumerState(Consumer<?> consumer, PulsarConsumerDto pulsarConsumerDto) {
        if (this.pulsarConsumer != null) {
            closeConsumer(); // You might want to implement a close method for cleanup
        }

        this.pulsarConsumer = new PulsarConsumer(consumer, pulsarConsumerDto.getTopicName(), pulsarConsumerDto.getSubscriptionName(), pulsarConsumerDto.getSchemaType(), pulsarConsumerDto.getInitialPosition());
    }

    public synchronized void closeConsumer() {
        if (this.pulsarConsumer != null && this.pulsarConsumer.getConsumer() != null) {
            try {
                this.pulsarConsumer.getConsumer().close(); // Close the Pulsar consumer
            } catch (Exception e) {
                e.printStackTrace();
                // Consider using a logger instead of printing stack trace in production
            } finally {
                this.pulsarConsumer = null; // Ensure the reference is cleared
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        closeConsumer(); // Close the Pulsar client during shutdown
    }
}
