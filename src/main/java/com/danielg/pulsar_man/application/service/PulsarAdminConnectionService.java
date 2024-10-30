package com.danielg.pulsar_man.application.service;

import com.danielg.pulsar_man.application.port.input.connetion.InitializePulsarAdminConnectionUseCase;
import com.danielg.pulsar_man.state.PulsarAdminManager;
import jakarta.annotation.PreDestroy;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PulsarAdminConnectionService implements InitializePulsarAdminConnectionUseCase {
    private static final Logger logger = LoggerFactory.getLogger(PulsarAdminConnectionService.class);

    private PulsarAdminManager pulsarAdminState;

    public PulsarAdminConnectionService(PulsarAdminManager pulsarAdminState) {
        this.pulsarAdminState = pulsarAdminState;
    }

    public void initializePulsarAdmin(String serviceUrl) throws PulsarClientException {
        if (pulsarAdminState != null && pulsarAdminState.getPulsarAdmin() != null) {
            pulsarAdminState.getPulsarAdmin().close();
        }

        if (pulsarAdminState != null) {
            pulsarAdminState.initializePulsarAdmin(serviceUrl);
        }
    }

    @PreDestroy
    public void closePulsarAdmin() {
        if (pulsarAdminState != null && pulsarAdminState.getPulsarAdmin() != null) {
            try {
                pulsarAdminState.getPulsarAdmin().close();
                logger.info("PulsarAdmin connection closed successfully.");
            } catch (Exception e) {
                logger.error("Failed to close PulsarAdmin connection: " + e.getMessage());
            }
        }
    }
}
