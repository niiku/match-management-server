package com.match.management.server;

import com.match.management.application.MatchService;
import com.match.management.domain.MatchStateChangedEvent;
import com.match.management.domain.TTTEvent;
import com.match.management.domain.match.GameResult;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.match.Result;
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
import org.springframework.test.web.servlet.MockMvc;
import reactor.bus.EventBus;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;
import static reactor.bus.selector.Selectors.$;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MatchFinishingTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchService matchService;

    @Autowired
    EventBus eventBus;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void matchFinished_AssignNull_HappyFlow() {
        AtomicReference<Object> catchEvents = new AtomicReference<>();
        eventBus.on($(TTTEvent.class), e -> {
            if (e.getData() instanceof MatchStateChangedEvent) catchEvents.set(e.getData());
        });

        //given Match exist on table
        Table table = tableRepository.findTable(new TableId("1"));
        Match activeMatch = matchRepository.findById(table.getActiveMatch());
        matchService.updateResult(activeMatch,
                new Result(Arrays.asList(new GameResult(7, 11),
                        new GameResult(5, 11),
                        new GameResult(11, 9),
                        new GameResult(2, 11))));

        //when: Match finished
        matchService.updateState(activeMatch, Match.State.FINISHED);

        //then: set current match on table to null and raise table event
        await().atMost(5, TimeUnit.SECONDS).until(() -> catchEvents.get() != null);
    }
}
