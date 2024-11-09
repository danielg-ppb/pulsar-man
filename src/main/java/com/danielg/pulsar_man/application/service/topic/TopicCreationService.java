package com.danielg.pulsar_man.application.service.topic;

import com.danielg.pulsar_man.application.port.input.topic.CreateNonPartitionedTopicUseCase;
import com.danielg.pulsar_man.application.port.input.topic.CreatePartitionedTopicUseCase;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.PulsarAdminFactory;
import com.danielg.pulsar_man.utils.PulsarTopicUtils;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TopicCreationService implements CreateNonPartitionedTopicUseCase, CreatePartitionedTopicUseCase {
    private static final Logger logger = LoggerFactory.getLogger(TopicCreationService.class);

    private final PulsarAdminFactory pulsarAdminFactory;

    public TopicCreationService(PulsarAdminFactory pulsarAdminState) {
        this.pulsarAdminFactory = pulsarAdminState;
    }

    @Override
    public void createNonPartitionedTopic(String tenant, String namespace, String topic) {
        String fullTopicName = PulsarTopicUtils.concatFullTopic(tenant, namespace, topic);
        try {
            pulsarAdminFactory.getPulsarAdmin().topics().createNonPartitionedTopic(fullTopicName);
        } catch (PulsarAdminException e) {
            throw new RuntimeException("Failed to create non-partitioned topic: " + topic, e);
        }

    }

    @Override
    public void createPartitionedTopic(String tenant, String namespace, String topic, int partitions) {
        String fullTopicName = PulsarTopicUtils.concatFullTopic(tenant, namespace, topic);
        try {
            pulsarAdminFactory.getPulsarAdmin().topics().createPartitionedTopic(fullTopicName, partitions);
        } catch (PulsarAdminException e) {
            throw new RuntimeException("Failed to create partitioned topic: " + topic, e);
        }

    }
}
