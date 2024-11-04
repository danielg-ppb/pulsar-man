package com.danielg.pulsar_man.infrastructure.adapter.input.rest.data.response;

import lombok.Data;

@Data
public class GenericResponse {
    private String message;

    public GenericResponse(String message) {
        this.message = message;
    }
}
