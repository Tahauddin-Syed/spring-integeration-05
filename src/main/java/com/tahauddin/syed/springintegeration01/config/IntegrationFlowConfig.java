package com.tahauddin.syed.springintegeration01.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.dsl.FileInboundChannelAdapterSpec;
import org.springframework.integration.file.dsl.Files;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.SystemPropertyUtils;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class IntegrationFlowConfig {


    private final MessageChannelConfig messageChannelConfig;


    @Bean
    public IntegrationFlow flowOne(){
        return IntegrationFlow.
                from((MessageSource<String>) () -> MessageBuilder.withPayload("Time Is :: @" + Instant.now()).build(),
                        poller -> poller.poller(pm -> pm.fixedRate(Duration.ofSeconds(2))))
                .handle((GenericHandler<String>) (payload, headers) -> {
                    log.info("flowOne -- Payload is :: {}", payload);
                    return null;
                }).get();
    }


    @Bean
    public IntegrationFlow flowTwo(){
        return IntegrationFlow.
                from(messageChannelConfig.messageChannelOne())
                .transform((GenericTransformer<String, String>) source -> source.toUpperCase())
                .handle((GenericHandler<String>) (payload, headers) -> {
                    log.info("Message Channel One Incoming Payload is :: {}", payload);
                    return null;
                }).get();
    }

    @Bean
    public IntegrationFlow flowThree(){
        return IntegrationFlow.
                from((MessageSource<String>) () -> MessageBuilder.withPayload("This is the message to channel 1 :: @" + Instant.now()).build(),
                        poller -> poller.poller(pm -> pm.fixedRate(Duration.ofSeconds(3))))
                .channel(messageChannelConfig.messageChannelOne())
                .get();
    }

    @Bean
    public IntegrationFlow flowFour(){
        return IntegrationFlow.
                from(messageChannelConfig.messageChannelThree())
                .transform((GenericTransformer<String, String>) source -> source.toUpperCase())
                .channel(messageChannelConfig.messageChannelFour())
                .get();
    }

    /*@Bean
    public IntegrationFlow flowFive(){
        return IntegrationFlow.
                from((MessageSource<String>) () -> MessageBuilder.withPayload("SAAD").build(),
                        sourcePollingChannelAdapterSpec -> sourcePollingChannelAdapterSpec.poller(pm -> pm.fixedRate(Duration.ofSeconds(3))))
                .channel(messageChannelConfig.messageChannelThree())
                .get();
    }*/

    @Bean
    public IntegrationFlow flowSix(){
        File file = new File(SystemPropertyUtils.resolvePlaceholders("${HOME}/Desktop/in"));
        FileInboundChannelAdapterSpec inboundChannelAdapterSpec = Files.inboundAdapter(file).autoCreateDirectory(true);
        return IntegrationFlow.
                from(inboundChannelAdapterSpec, poller -> poller.poller(pm -> pm.fixedRate(Duration.ofSeconds(5))))
                .channel(messageChannelConfig.messageChannelFive())
                .get();
    }

    @Bean
    public IntegrationFlow flowSeven(){
        File file = new File(SystemPropertyUtils.resolvePlaceholders("${HOME}/Desktop/out"));
        return IntegrationFlow
                .from(messageChannelConfig.messageChannelSix())
                .handle(Files.outboundAdapter(file).autoCreateDirectory(true))
                .get();
    }

    @Bean
    public IntegrationFlow flowEight(){
        return IntegrationFlow
                .from(messageChannelConfig.messageChannelFive())
                .handle((GenericHandler<File>) (payload, headers) -> {
                    log.info("New Files Found :: {}", payload);
                    return payload;
                })
                .channel(messageChannelConfig.messageChannelSix())
                .get();
    }


}
