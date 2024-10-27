package com.danielg.pulsar_man.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class TopicListDto {
    private List<String> topics;

    public TopicListDto(List<String> topics) {
        this.topics = topics;
    }
}
