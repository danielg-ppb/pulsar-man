package com.danielg.pulsar_man.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchemaStructure {
    private String fileName;
    private String outerClassName;
    private String mainInnerClassName;
}
