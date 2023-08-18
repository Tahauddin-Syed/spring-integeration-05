package com.tahauddin.syed.springintegeration01.runner;

import com.tahauddin.syed.springintegeration01.client.GreetingMappingConfig;
import com.tahauddin.syed.springintegeration01.config.MessageChannelConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class IntegrationRunnerTwo implements CommandLineRunner {

    private final GreetingMappingConfig greetingMappingConfig;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i< 20; i++) {
            String channelFour = greetingMappingConfig.greet("Hello Channel Three " + i);
            log.info("Input to Channel Four is  :: {}", channelFour);
            Thread.sleep(3000);
        }
    }
}
