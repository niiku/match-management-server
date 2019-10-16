package com.match.management.server;

import com.match.management.application.MatchService;
import com.match.management.domain.MatchFinishedEvent;
import com.match.management.domain.match.*;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableId;
import com.match.management.domain.table.TableRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static reactor.bus.selector.Selectors.$;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MatchFinishingTest {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchService matchService;

    @Autowired
    private EventBus eventBus;

    @Before
    public void setUp() {

    }

    @Test
    public void matchFinished_AssignNull_HappyFlow() {
        List<Event> catchEvents = new ArrayList<>();
        eventBus.on($(MatchFinishedEvent.class), catchEvents::add);

        Table table = tableRepository.findTable(new TableId("1"));
        Optional<Match> activeMatch = table.getMatches().stream()
                .map(matchRepository::findById)
                .filter(match -> match.getState() == Match.State.ASSIGNED)
                .findFirst();
        assertTrue(activeMatch.isPresent());
        matchService.updateResult(activeMatch.get().getId(),
                new Result(Arrays.asList(new GameResult(7, 11),
                        new GameResult(5, 11),
                        new GameResult(11, 9),
                        new GameResult(2, 11))));

        matchService.finish(activeMatch.get().getId());

        await().atMost(5, TimeUnit.SECONDS).until(() -> !catchEvents.isEmpty());
    }

    @Test
    public void matchFinished_playerBWonByDefault_matchIsWonByDefaultByPlayerB() {
        List<Event> catchEvents = new ArrayList<>();
        eventBus.on($(MatchFinishedEvent.class), catchEvents::add);
        MatchId currentMatchId = new MatchId(10);

        matchService.playerAWonByDefault(currentMatchId);

        await().atMost(5, TimeUnit.SECONDS).until(() -> !catchEvents.isEmpty());
        assertThat(catchEvents).hasSize(1);
        Match updatedMatch = matchRepository.findById(currentMatchId);
        assertThat(updatedMatch.getResult()).isEqualTo(new Result(
                Arrays.asList(
                        new GameResult(11, 0),
                        new GameResult(11, 0),
                        new GameResult(11, 0))));
    }

    @Test
    public void matchFinished_playerAWonByDefault_matchIsWonByDefaultByPlayerA() {
        List<Event> catchEvents = new ArrayList<>();
        eventBus.on($(MatchFinishedEvent.class), catchEvents::add);
        MatchId currentMatchId = new MatchId(15);

        matchService.playerBWonByDefault(currentMatchId);

        await().atMost(5, TimeUnit.SECONDS).until(() -> !catchEvents.isEmpty());
        assertThat(catchEvents).hasSize(1);
        Match updatedMatch = matchRepository.findById(currentMatchId);
        assertThat(updatedMatch.getResult()).isEqualTo(new Result(
                Arrays.asList(
                        new GameResult(0, 11),
                        new GameResult(0, 11),
                        new GameResult(0, 11)
                )));
    }

}
