package com.danielg.pulsar_man.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PartitionListResponseDto {
    private List<String> partitions;

    public PartitionListResponseDto(List<String> partitions) {
        this.partitions = partitions;
    }
}
