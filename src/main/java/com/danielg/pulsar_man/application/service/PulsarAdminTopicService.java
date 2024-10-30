package com.danielg.pulsar_man.application.service;

import com.danielg.pulsar_man.application.port.input.topic.GetPartitionedTopicStats;
import com.danielg.pulsar_man.application.port.input.topic.ListTopicsUseCase;
import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response.TopicListResponse;
import com.danielg.pulsar_man.state.PulsarAdminManager;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.common.partition.PartitionedTopicMetadata;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PulsarAdminTopicService implements ListTopicsUseCase, GetPartitionedTopicStats {
    private static final Logger logger = LoggerFactory.getLogger(PulsarAdminTopicService.class);

    private PulsarAdminManager pulsarAdminManager;

    public PulsarAdminTopicService(PulsarAdminManager pulsarAdminState) {
        this.pulsarAdminManager = pulsarAdminState;
    }

    @Override
    public TopicListResponse listTopics(String tenant, String namespace) {
        try {
            String namespacePath = tenant + "/" + namespace;
            //            return pulsarAdminState.getPulsarAdmin().topics().getList(namespacePath); -> with partitions

            return new TopicListResponse(pulsarAdminManager.getPulsarAdmin().topics().getPartitionedTopicList(namespacePath));
        } catch (Exception e) {
            logger.error("Failed to list topics", e);
            throw new RuntimeException("Failed to list topics", e);
        }
    }

    @Override
    public List<TopicStats> getPartitionedTopicStats(String tenant, String namespace, String topic) {
        String fullTopicName = "persistent://" + tenant + "/" + namespace + "/" + topic;
        List<TopicStats> partitionStats = new ArrayList<>();

        try {
            // Retrieve the partition metadata
            PartitionedTopicMetadata metadata = pulsarAdminManager.getPulsarAdmin().topics().getPartitionedTopicMetadata(fullTopicName);

            // For each partition, get its stats
            for (int i = 0; i < metadata.partitions; i++) {
                String partitionName = fullTopicName + "-partition-" + i;
                partitionStats.add(pulsarAdminManager.getPulsarAdmin().topics().getStats(partitionName));
            }

        } catch (PulsarAdminException e) {
            throw new RuntimeException("Failed to retrieve stats for partitioned topic: " + topic, e);
        }

        return partitionStats;
    }
}
