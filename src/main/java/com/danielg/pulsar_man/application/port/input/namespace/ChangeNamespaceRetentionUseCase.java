package com.danielg.pulsar_man.application.port.input.namespace;

public interface ChangeNamespaceRetentionUseCase {
    void changeNamespaceRetention(String tenant, String namespace, int retention);
}
