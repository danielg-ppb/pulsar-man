package com.danielg.pulsar_man.service.admin.impl;

import com.danielg.pulsar_man.dto.response.PartitionListResponseDto;
import com.danielg.pulsar_man.dto.response.TopicListResponseDto;
import com.danielg.pulsar_man.service.admin.PulsarAdminConnectionService;
import com.danielg.pulsar_man.state.PulsarAdminManager;
import jakarta.annotation.PreDestroy;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PulsarAdminConnectionServiceImpl implements PulsarAdminConnectionService {
    private static final Logger logger = LoggerFactory.getLogger(PulsarAdminConnectionServiceImpl.class);

    private PulsarAdminManager pulsarAdminState;

    public PulsarAdminConnectionServiceImpl(PulsarAdminManager pulsarAdminState) {
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
