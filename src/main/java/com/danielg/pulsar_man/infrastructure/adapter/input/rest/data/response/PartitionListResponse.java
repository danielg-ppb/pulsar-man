package com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response;

import lombok.Data;

import java.util.List;

@Data
public class PartitionListResponse {
    private List<String> partitions;

    public PartitionListResponse(List<String> partitions) {
        this.partitions = partitions;
    }
}
