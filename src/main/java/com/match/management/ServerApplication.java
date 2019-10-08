package com.match.management;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.api.OpenApiCustomiser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    public OpenApiCustomiser customize() {
        return new OpenApiCustomiser() {
            @Override
            public void customise(OpenAPI openAPI) {
                String url = openAPI.getServers().get(0).getUrl();
                if (isOpenShiftTTTUrl(url)) {
                    openAPI.getServers().get(0).setUrl("https:" + url.substring(5));
                }
            }
        };
    }

    private boolean isOpenShiftTTTUrl(String url) {
        return url.contains("ttt-match-management");
    }

}
