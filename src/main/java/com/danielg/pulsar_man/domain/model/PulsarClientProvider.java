package com.danielg.pulsar_man.domain.model;

import com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request.PulsarServiceUrlRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.PulsarClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@AllArgsConstructor
public class PulsarClientProvider {
    private static final Logger logger = LoggerFactory.getLogger(PulsarClientProvider.class);

    private PulsarClient pulsarClient;
}
