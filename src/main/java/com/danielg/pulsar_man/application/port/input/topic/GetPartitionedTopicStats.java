package com.danielg.pulsar_man.application.port.input.topic;

import org.apache.pulsar.common.policies.data.TopicStats;

import java.util.List;

public interface GetPartitionedTopicStats {
    public List<TopicStats> getPartitionedTopicStats(String tenant, String namespace, String topic);
}
