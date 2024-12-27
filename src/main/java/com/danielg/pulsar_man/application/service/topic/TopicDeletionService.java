package com.danielg.pulsar_man.application.service.topic;

import com.danielg.pulsar_man.application.port.input.topic.DeleteNonPartitionedTopicUseCase;
import com.danielg.pulsar_man.application.port.input.topic.DeletePartitionedTopicUseCase;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.AdminFactory;
import com.danielg.pulsar_man.utils.PulsarTopicUtils;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TopicDeletionService implements DeleteNonPartitionedTopicUseCase, DeletePartitionedTopicUseCase {
    private static final Logger logger = LoggerFactory.getLogger(TopicDeletionService.class);

    private final AdminFactory pulsarAdminFactory;

    public TopicDeletionService(AdminFactory pulsarAdminState) {
        this.pulsarAdminFactory = pulsarAdminState;
    }

    @Override
    public void deleteNonPartitionedTopic(String tenant, String namespace, String topic) {
        String fullTopicName = PulsarTopicUtils.concatFullTopic(tenant, namespace, topic);
        try {
            pulsarAdminFactory.getPulsarAdmin().topics().delete(fullTopicName, true);
        } catch (PulsarAdminException e) {
            throw new RuntimeException("Failed to delete non-partitioned topic: " + topic, e);
        }
    }

    /**
     * WATCH OUT!!!!!
     *
     * @param tenant
     * @param namespace
     * @param topic
     */
    @Override
    public void deletePartitionedTopic(String tenant, String namespace, String topic) {
        String fullTopicName = PulsarTopicUtils.concatFullTopic(tenant, namespace, topic);
        try {
            pulsarAdminFactory.getPulsarAdmin().topics().deletePartitionedTopic(fullTopicName, true);
        } catch (PulsarAdminException e) {
            throw new RuntimeException("Failed to delete partitioned topic: " + topic, e);
        }
    }
}
