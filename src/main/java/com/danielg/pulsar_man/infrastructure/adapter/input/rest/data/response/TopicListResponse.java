package com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response;

import lombok.Data;

import java.util.List;

@Data
public class TopicListResponse {
    private List<String> topics;

    public TopicListResponse(List<String> topics) {
        this.topics = topics;
    }
}
