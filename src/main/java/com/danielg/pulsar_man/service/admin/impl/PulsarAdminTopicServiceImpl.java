package com.danielg.pulsar_man.service.admin.impl;

import com.danielg.pulsar_man.dto.response.TopicListResponseDto;
import com.danielg.pulsar_man.service.admin.PulsarAdminTopicService;
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
public class PulsarAdminTopicServiceImpl implements PulsarAdminTopicService {
    private static final Logger logger = LoggerFactory.getLogger(PulsarAdminTopicServiceImpl.class);

    private PulsarAdminManager pulsarAdminManager;

    public PulsarAdminTopicServiceImpl(PulsarAdminManager pulsarAdminState) {
        this.pulsarAdminManager = pulsarAdminState;
    }

    @Override
    public TopicListResponseDto listTopics(String tenant, String namespace) {
        try {
            String namespacePath = tenant + "/" + namespace;
            //            return pulsarAdminState.getPulsarAdmin().topics().getList(namespacePath); -> with partitions

            return new TopicListResponseDto(pulsarAdminManager.getPulsarAdmin().topics().getPartitionedTopicList(namespacePath));
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
