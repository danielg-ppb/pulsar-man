package com.danielg.pulsar_man.service.admin;

import com.danielg.pulsar_man.dto.response.TopicListResponseDto;
import org.apache.pulsar.common.policies.data.TopicStats;

import java.util.List;

public interface PulsarAdminTopicService {
    public TopicListResponseDto listTopics(String tenant, String namespace);
    public List<TopicStats> getPartitionedTopicStats(String tenant, String namespace, String topic);
}
