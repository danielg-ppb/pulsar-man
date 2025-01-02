package com.danielg.pulsar_man;

import com.danielg.pulsar_man.application.orchestrator.ArgumentOrchestrator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PulsarManApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PulsarManApplication.class, args);
        context.getBean(ArgumentOrchestrator.class).orchestrate(args);
    }

}
