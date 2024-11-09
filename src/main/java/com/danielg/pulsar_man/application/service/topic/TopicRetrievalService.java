package com.danielg.pulsar_man.application.service.topic;

import com.danielg.pulsar_man.application.port.input.topic.GetPartitionedTopicStats;
import com.danielg.pulsar_man.application.port.input.topic.ListNonPartitionedTopicsUseCase;
import com.danielg.pulsar_man.application.port.input.topic.ListPartitionedTopicsUseCase;
import com.danielg.pulsar_man.application.port.input.topic.ListTopicsUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.TopicListResponse;
import com.danielg.pulsar_man.infrastructure.pulsar.factory.PulsarAdminFactory;
import com.danielg.pulsar_man.utils.PulsarTopicUtils;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.common.partition.PartitionedTopicMetadata;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicRetrievalService implements ListTopicsUseCase, ListPartitionedTopicsUseCase, ListNonPartitionedTopicsUseCase, GetPartitionedTopicStats {
    private static final Logger logger = LoggerFactory.getLogger(TopicRetrievalService.class);

    private PulsarAdminFactory pulsarAdminFactory;

    public TopicRetrievalService(PulsarAdminFactory pulsarAdminState) {
        this.pulsarAdminFactory = pulsarAdminState;
    }

    @Override
    public TopicListResponse listTopics(String tenant, String namespace) {
        try {
            String namespacePath = tenant + "/" + namespace;
            //partitioned and non partitioned
            List<String> topics = pulsarAdminFactory.getPulsarAdmin().topics().getList(namespacePath);
            topics.addAll(pulsarAdminFactory.getPulsarAdmin().topics().getPartitionedTopicList(namespacePath));
            return new TopicListResponse(topics);
        } catch (Exception e) {
            logger.error("Failed to list topics", e);
            throw new RuntimeException("Failed to list topics", e);
        }
    }

    @Override
    public TopicListResponse listNonPartitionedTopics(String tenant, String namespace) {
        String namespacePath = tenant + "/" + namespace;
        try {
            // Get all topics in the namespace
            List<String> allTopics = pulsarAdminFactory.getPulsarAdmin().topics().getList(namespacePath);
            logger.info(allTopics.toString());

            // Filter out topics that are partitioned
            return new TopicListResponse(allTopics.stream()
                    .filter(topic -> !isPartitionedTopic(topic))
                    .collect(Collectors.toList()));

        } catch (PulsarAdminException e) {
            throw new RuntimeException("Failed to list non-partitioned topics", e);
        }
    }

    private boolean isPartitionedTopic(String topic) {
        // Check if the topic name ends with the partition suffix (e.g., "-partition-0")
        return topic.matches(".*-partition-\\d+$");
    }

    @Override
    public TopicListResponse listPartitionedTopics(String tenant, String namespace) {
        try {
            String namespacePath = tenant + "/" + namespace;
            //partitioned and non partitioned
            return new TopicListResponse(pulsarAdminFactory.getPulsarAdmin().topics().getPartitionedTopicList(namespacePath));
        } catch (Exception e) {
            logger.error("Failed to list topics", e);
            throw new RuntimeException("Failed to list topics", e);
        }
    }

    @Override
    public List<TopicStats> getPartitionedTopicStats(String tenant, String namespace, String topic) {
        String fullTopicName = PulsarTopicUtils.concatFullTopic(tenant, namespace, topic);
        List<TopicStats> partitionStats = new ArrayList<>();

        try {
            // Retrieve the partition metadata
            PartitionedTopicMetadata metadata = pulsarAdminFactory.getPulsarAdmin().topics().getPartitionedTopicMetadata(fullTopicName);

            // For each partition, get its stats
            for (int i = 0; i < metadata.partitions; i++) {
                String partitionName = fullTopicName + "-partition-" + i;
                partitionStats.add(pulsarAdminFactory.getPulsarAdmin().topics().getStats(partitionName));
            }

        } catch (PulsarAdminException e) {
            throw new RuntimeException("Failed to retrieve stats for partitioned topic: " + topic, e);
        }

        return partitionStats;
    }
}
