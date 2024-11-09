package com.danielg.pulsar_man.application.service;

import com.danielg.pulsar_man.application.port.input.schema.GetTopicSchemaUseCase;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.PulsarAdminFactory;
import com.danielg.pulsar_man.utils.PulsarTopicUtils;
import org.apache.pulsar.common.schema.SchemaInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SchemaService implements GetTopicSchemaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(SchemaService.class);

    private PulsarAdminFactory pulsarAdminFactory;

    public SchemaService(PulsarAdminFactory pulsarAdminFactory) {
        this.pulsarAdminFactory = pulsarAdminFactory;
    }

    @Override
    public SchemaInfo getSchemaFromTopic(String tenant, String namespace, String topic) {
        String fullTopicName = PulsarTopicUtils.concatFullTopic(tenant, namespace, topic);
        try {
            return pulsarAdminFactory.getPulsarAdmin().schemas().getSchemaInfo(fullTopicName);
        } catch (Exception e) {
            logger.error("Failed to get schema", e);
            throw new RuntimeException("Failed to get schema", e);
        }
    }
}
