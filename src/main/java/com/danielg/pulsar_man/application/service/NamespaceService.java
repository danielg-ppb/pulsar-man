package com.danielg.pulsar_man.application.service;

import com.danielg.pulsar_man.application.port.input.namespace.ChangeNamespaceRetentionUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.output.pulsar.manager.PulsarAdminManager;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.common.policies.data.RetentionPolicies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NamespaceService implements ChangeNamespaceRetentionUseCase {
    private static final Logger logger = LoggerFactory.getLogger(NamespaceService.class);

    private final PulsarAdminManager pulsarAdminManager;

    public NamespaceService(PulsarAdminManager pulsarAdminState) {
        this.pulsarAdminManager = pulsarAdminState;
    }


    @Override
    public void changeNamespaceRetention(String tenant, String namespace, int retention) {
        String namespacePath = tenant + "/" + namespace;
        try {
            // Set retention policy with time and unlimited size
            pulsarAdminManager.getPulsarAdmin().namespaces().setRetention(namespacePath, new RetentionPolicies(retention, -1));
            logger.info("Retention policy set successfully.");
        } catch (PulsarAdminException e) {
            logger.error("Failed to set retention policy: " + e.getMessage());
            throw new RuntimeException("Failed to set retention policy: " + e.getMessage());
        }
    }
}