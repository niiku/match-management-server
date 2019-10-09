package com.match.management;

import com.match.management.infrastructure.web.*;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.api.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerApplication {

    @Autowired
    MockingResource mockingResource;

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

    @EventListener
    public void initRepositoriesWithMockData(ApplicationStartedEvent event) {
        for(int i = 0; i < 15; i++) {
            mockingResource.postEvent(EventDTO.builder()
                .id(EventId.MATCH_ASSIGNED_TO_TABLE)
                .payload(EventPayloadDTO.builder()
                        .tableId(String.valueOf(i))
                        .match(MatchDTO.builder()
                                .matchId(i * 10)
                                .classification("classification x")
                                .playerA(PlayerDTO.builder()
                                        .playerId(i * 100)
                                        .firstName("Peter")
                                        .lastName("Lustig" + i)
                                        .club("TSV Basel")
                                        .build())
                                .playerB(PlayerDTO.builder()
                                        .playerId(i * 100 + 1)
                                        .firstName("Willi")
                                        .lastName("Meier" + i)
                                        .club("TSV Zürich")
                                        .build())
                                .result(ResultDTO.builder().build())
                                .build())
                        .build())
                .build());
        }
    }
}
