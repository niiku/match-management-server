package com.match.management;

import org.springdoc.api.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.Environment;
import reactor.bus.EventBus;

@Configuration
public class TTTConfiguration {

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

    @Bean
    public OpenApiCustomiser customize() {
        return openAPI -> {
            String url = openAPI.getServers().get(0).getUrl();
            if (isOpenShiftTTTUrl(url)) {
                openAPI.getServers().get(0).setUrl("https:" + url.substring(5));
            }
        };
    }

    private boolean isOpenShiftTTTUrl(String url) {
        return url.contains("ttt-match-management");
    }
}
