package com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class PulsarServiceUrlRequest {
    private String serviceUrl;
    private String token;
}
