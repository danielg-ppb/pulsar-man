package com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response;

import lombok.Data;

@Data
public class PartitionNumberResponse {
    private Integer numberOfPartitions;

    public PartitionNumberResponse(Integer numberOfPartitions) {
        this.numberOfPartitions = numberOfPartitions;
    }
}
