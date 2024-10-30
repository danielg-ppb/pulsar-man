package com.danielg.pulsar_man.utils;

import org.apache.pulsar.client.api.Schema;

public class SchemaProvider {

    public static Schema<?> getSchema(String schemaType) {
        return switch (schemaType.toLowerCase()) {
            case "string" -> Schema.STRING;
            case "bytes" -> Schema.BYTES;
            case "protobuf" -> null;
            // Add more cases here for other schemas, such as Avro or JSON.
            default -> throw new IllegalArgumentException("Unsupported schema type: " + schemaType);
        };
    }


}
