package com.danielg.pulsar_man.application.port.input.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface GetRealTimePulsarMessagesUseCase {
    void consumeDynamicMessages(SseEmitter emitter);
}
