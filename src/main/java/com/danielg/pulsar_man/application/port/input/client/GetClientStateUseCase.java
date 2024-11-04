package com.danielg.pulsar_man.application.port.input.client;

public interface GetClientStateUseCase {
    String getClientState() throws NoSuchFieldException, IllegalAccessException;
}
