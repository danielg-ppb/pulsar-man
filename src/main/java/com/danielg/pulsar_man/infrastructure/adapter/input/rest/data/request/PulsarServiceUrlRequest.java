package com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PulsarServiceUrlRequest {
    private String serviceUrl;
}
