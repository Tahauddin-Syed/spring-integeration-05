package com.tahauddin.syed.springintegeration01.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class IntegrationFlowRunner implements CommandLineRunner {

    private final IntegrationFlowContext integrationFlowContext;

    @Override
    public void run(String... args) throws Exception {
        IntegrationFlow integrationFlow1 = integrationFlow("Syed");
        IntegrationFlow integrationFlow2 = integrationFlow("Tahauddin");
        Set.of(integrationFlow1, integrationFlow2)
                .forEach( c -> integrationFlowContext.registration(c)
                        .register().start()
                );
    }

    public IntegrationFlow integrationFlow(String name){
        return IntegrationFlow
                .from((MessageSource<String>) () -> MessageBuilder.withPayload("Hi There From CLR:: @" +name).build(),
                        sourcePollingChannelAdapterSpec -> sourcePollingChannelAdapterSpec.poller(pm -> pm.fixedRate(Duration.ofSeconds(2))))
                .handle((GenericHandler<String>) (payload, headers) -> {
                    log.info("Payload Received is :: {}", payload);
                    return null;
                })
                .get();
    }




}
