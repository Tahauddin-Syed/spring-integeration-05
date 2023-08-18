package com.tahauddin.syed.springintegeration01.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MessageChannelConfig {

    @Bean
    public MessageChannel messageChannelOne() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public MessageChannel messageChannelTwo() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public DirectChannel messageChannelThree() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public DirectChannel messageChannelFour() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public DirectChannel messageChannelFive() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public DirectChannel messageChannelSix() {
        return MessageChannels.direct().getObject();
    }
}
