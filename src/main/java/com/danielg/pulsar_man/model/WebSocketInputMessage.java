package com.danielg.pulsar_man.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WebSockerInputMessage {
    private Date publishTime;
    private String message;

    public WebSockerInputMessage(Date publishTime, String message) {
        this.publishTime = publishTime;
        this.message = message;
    }
}
