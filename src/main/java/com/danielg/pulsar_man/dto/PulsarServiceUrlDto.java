package com.danielg.pulsar_man.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class PulsarServiceUrlDto {
    private String serviceUrl;
}
