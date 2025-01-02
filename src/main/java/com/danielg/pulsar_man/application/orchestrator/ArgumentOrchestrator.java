package com.danielg.pulsar_man.application.orchestrator;

import com.danielg.pulsar_man.application.service.staticConfigurer.PulsarStaticConfigurerService;
import org.springframework.stereotype.Component;

@Component
public class ArgumentOrchestrator {
    private final PulsarStaticConfigurerService pulsarStaticConfigurerService;

    public ArgumentOrchestrator(PulsarStaticConfigurerService pulsarStaticConfigurerService) {
        this.pulsarStaticConfigurerService = pulsarStaticConfigurerService;
    }

    public void orchestrate(String[] args) {
        if (args.length > 0) {
            System.out.println("Arguments provided: " + args[0]);
            switch (args[0]) {
                case "staticConfig":
                    System.out.println("staticConfig");
                    this.pulsarStaticConfigurerService.configure();
                    break;
                default:
                    System.out.println("Default argument");
            }
        } else {
            System.out.println("No arguments provided. Please specify a workflow.");
        }
    }
}
