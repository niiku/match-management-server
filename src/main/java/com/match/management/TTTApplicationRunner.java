package com.match.management;

import com.match.management.application.MatchService;
import com.match.management.domain.match.GameResult;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.Result;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableId;
import com.match.management.domain.table.TableRepository;
import com.match.management.infrastructure.web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class TTTApplicationRunner {

    @Autowired
    private MockingResource mockingResource;

    @Autowired
    private MatchService matchService;

    @Autowired
    private TableRepository tableRepository;

    @EventListener
    public void initRepositoriesWithMockData(ApplicationStartedEvent event) {
        for (int i = 1; i < 26; i++) {
            mockingResource.postEvent(ExternalEventDTO.builder()
                    .id(ExternalEventId.MATCHES_ASSIGNED_TO_TABLE)
                    .payload(EventPayloadDTO.builder()
                            .tableId(String.valueOf(i))
                            .matches(Arrays.asList(
                                    MatchDTO.builder()
                                            .matchId(i * 10)
                                            .classification("Herren A")
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
                                            .classification("Herren A")
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
        tableRepository.save(Table.builder().id(new TableId("15")).build());

        matchService.updateResult(
                new MatchId(10),
                new Result(Arrays.asList(
                        new GameResult(8, 11),
                        new GameResult(12, 10),
                        new GameResult(29, 27),
                        new GameResult(11, 0)
                ))
        );
        matchService.finish(new MatchId(10));

    }

}
