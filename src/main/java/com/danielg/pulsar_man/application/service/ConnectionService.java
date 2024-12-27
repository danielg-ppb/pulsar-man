package com.danielg.pulsar_man.application.service;

import com.danielg.pulsar_man.application.port.input.connetion.InitializePulsarAdminConnectionUseCase;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.AdminFactory;
import jakarta.annotation.PreDestroy;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService implements InitializePulsarAdminConnectionUseCase {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionService.class);

    private AdminFactory adminFactory;

    public ConnectionService(AdminFactory adminFactory) {
        this.adminFactory = adminFactory;
    }

    public void initializePulsarAdmin(String serviceUrl) throws PulsarClientException {
        if (adminFactory != null && adminFactory.getPulsarAdmin() != null) {
            adminFactory.getPulsarAdmin().close();
        }

        if (adminFactory != null) {
            adminFactory.initializePulsarAdmin(serviceUrl);
        }
    }

    @PreDestroy
    public void closePulsarAdmin() {
        if (adminFactory != null && adminFactory.getPulsarAdmin() != null) {
            try {
                adminFactory.getPulsarAdmin().close();
                logger.info("PulsarAdmin connection closed successfully.");
            } catch (Exception e) {
                logger.error("Failed to close PulsarAdmin connection: " + e.getMessage());
            }
        }
    }
}
