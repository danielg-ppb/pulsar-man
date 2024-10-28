package com.danielg.pulsar_man.dto.response;

import lombok.Data;

@Data
public class PartitionNumberResponseDto {
    private Integer numberOfPartitions;

    public PartitionNumberResponseDto(Integer numberOfPartitions) {
        this.numberOfPartitions = numberOfPartitions;
    }
}
