package com.danielg.pulsar_man.infrastructure.adapter.input.rest.controller.admin;

import com.danielg.pulsar_man.application.port.input.namespace.ChangeNamespaceRetentionUseCase;
import com.danielg.pulsar_man.application.port.input.partition.GetNumberOfPartitionsFromTopicUseCase;
import com.danielg.pulsar_man.application.port.input.partition.ListPartitionsFromTopicUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/pulsar-admin/namespace")
public class NamespaceController {
    private final ChangeNamespaceRetentionUseCase changeNamespaceRetentionUseCase;

    @Autowired
    public NamespaceController(ChangeNamespaceRetentionUseCase changeNamespaceRetentionUseCase) {
        this.changeNamespaceRetentionUseCase = changeNamespaceRetentionUseCase;
    }

    @PutMapping("/retention")
    public void changeNamespaceRetention(@RequestParam String tenant, @RequestParam String namespace, @RequestParam int retention) {
        //public and default
        this.changeNamespaceRetentionUseCase.changeNamespaceRetention(tenant, namespace, retention);
    }


}
