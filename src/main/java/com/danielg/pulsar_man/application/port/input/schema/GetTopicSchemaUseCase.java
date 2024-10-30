package com.danielg.pulsar_man.application.port.input.schema;

import org.apache.pulsar.common.schema.SchemaInfo;

public interface GetTopicSchemaUseCase {
    SchemaInfo getSchemaFromTopic(String tenant, String namespace, String topic);
}
