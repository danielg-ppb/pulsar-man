package com.danielg.pulsar_man.utils;

import org.apache.pulsar.client.api.Schema;

public class SchemaProvider {

    public static Schema<?> getSchema(String schemaType) {
        switch (schemaType.toLowerCase()) {
            case "string":
                return Schema.STRING;
            case "bytes":
                return Schema.BYTES;
            //case "protobuf":
            //    return Schema.PROTOBUF(MyProtobufMessage.class);
            // Add more cases here for other schemas, such as Avro or JSON.
            default:
                throw new IllegalArgumentException("Unsupported schema type: " + schemaType);
        }
    }
}
