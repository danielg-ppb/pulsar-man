package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.admin;

import com.danielg.pulsar_man.application.port.input.schema.GetTopicSchemaUseCase;
import org.apache.pulsar.common.schema.SchemaInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/pulsar-admin/schema")
public class SchemaController {
    private final GetTopicSchemaUseCase getTopicSchemaUseCase;

    public SchemaController(GetTopicSchemaUseCase getTopicSchemaUseCase) {
        this.getTopicSchemaUseCase = getTopicSchemaUseCase;
    }

    @GetMapping
    public SchemaInfo getSchemaFromTopic(@RequestParam String tenant, @RequestParam String namespace, @RequestParam String topic) {
        //public and default
        return this.getTopicSchemaUseCase.getSchemaFromTopic(tenant, namespace, topic);
    }

}
