package com.danielg.pulsar_man.service;

import java.util.List;

public interface PulsarTopicService {
    public List<String> listTopics(String tenant, String namespace);
}
