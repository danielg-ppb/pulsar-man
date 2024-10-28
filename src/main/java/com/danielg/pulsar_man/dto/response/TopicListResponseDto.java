package com.danielg.pulsar_man.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class TopicListResponseDto {
    private List<String> topics;

    public TopicListResponseDto(List<String> topics) {
        this.topics = topics;
    }
}
