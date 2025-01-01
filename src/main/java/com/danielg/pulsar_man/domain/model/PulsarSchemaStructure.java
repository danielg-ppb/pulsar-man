package com.danielg.pulsar_man.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PulsarSchemaStructure {
    private String fileName;
    private String outerClassName;
    private String mainInnerClassName;
}
