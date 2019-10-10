package com.match.management;

import com.match.management.application.UpdateResultService;
import com.match.management.domain.TTTEvent;
import com.match.management.domain.match.GameResult;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.Result;
import com.match.management.infrastructure.web.*;
import org.springdoc.api.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.Environment;
import reactor.bus.EventBus;

import java.util.Arrays;
import java.util.Collections;

import static reactor.bus.selector.Selectors.$;

@SpringBootApplication
@EnableScheduling
public class ServerApplication implements CommandLineRunner {

    @Autowired
    private MockingResource mockingResource;

    @Autowired
    private UpdateResultService updateResultService;

    @Autowired
    private EventBus eventBus;

    @Autowired
    private TableWebSocketController notificationConsumer;

    @Override
    public void run(String... args) {
        eventBus.on($(TTTEvent.class), notificationConsumer);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

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

    @EventListener
    public void initRepositoriesWithMockData(ApplicationStartedEvent event) {
        for(int i = 0; i < 15; i++) {
            mockingResource.postEvent(ExternalEventDTO.builder()
                .id(ExternalEventId.MATCHES_ASSIGNED_TO_TABLE)
                .payload(EventPayloadDTO.builder()
                        .tableId(String.valueOf(i))
                        .matches(Arrays.asList(
                                MatchDTO.builder()
                                    .matchId(i * 10)
                                    .classification("classification x")
                                    .stage("Gruppe " + i)
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
                                    .result(ResultDTO.builder()
                                            .games(Collections.emptyList())
                                            .build())
                                    .build(),
                                    MatchDTO.builder()
                                        .matchId(i * 10 + 5)
                                        .classification("classification x")
                                        .stage("Gruppe " + i)
                                        .playerA(PlayerDTO.builder()
                                                .playerId(i * 100 + 50)
                                                .firstName("Steve")
                                                .lastName("Jobs" + i)
                                                .club("TSV Cupertino")
                                                .build())
                                        .playerB(PlayerDTO.builder()
                                                .playerId(i * 100 + 51)
                                                .firstName("Bill")
                                                .lastName("Gates" + i)
                                                .club("TSV Richmond")
                                                .build())
                                        .result(ResultDTO.builder()
                                                .games(Collections.emptyList())
                                                .build())
                                        .build()
                                )
                        )
                        .build())
                .build());
        }

        updateResultService.updateResult(
                new MatchId(0),
                new Result(Arrays.asList(
                        new GameResult(8, 11),
                        new GameResult(12, 10),
                        new GameResult(29, 27),
                        new GameResult(11, 0)
                ))
        );

    }
}
