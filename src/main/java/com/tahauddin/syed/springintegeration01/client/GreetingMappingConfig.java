package com.tahauddin.syed.springintegeration01.client;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway()
public interface GreetingMappingConfig {

    @Gateway(requestChannel = "messageChannelThree", replyChannel = "messageChannelFour")
    String greet(String name);

    @Gateway(requestChannel = "messageChannelFive", replyChannel = "messageChannelSix")
    void copyFiles(String name);
}
