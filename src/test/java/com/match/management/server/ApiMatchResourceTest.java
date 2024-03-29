package com.match.management.server;

import com.match.management.domain.MatchStateChangedEvent;
import com.match.management.domain.ResultUpdatedEvent;
import com.match.management.domain.TTTEvent;
import com.match.management.domain.match.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.bus.selector.Selectors.$;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiMatchResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EventBus eventBus;

    @Test
    public void getMatch_HappyFlow() throws Exception {
        mvc.perform(get("/matches/10"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMatch_NotFound404() throws Exception {
        mvc.perform(get("/matches/104"))
                .andExpect(status().is(404));
    }

    @Test
    public void updateResult_happy_flow() throws Exception {
        List<Event> catchEvents = new ArrayList<>();
        eventBus.on($(TTTEvent.class), event -> {
            if(event.getData() instanceof ResultUpdatedEvent) {
                catchEvents.add(event);
            }
        });

        Result result = new Result(singletonList(new GameResult(7, 11)));
        mvc.perform(put("/matches/10/result")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"games\":[{\"score_player_a\":7,\"score_player_b\":11}]}"))
                .andExpect(status().isOk());

        assertThat(matchRepository.findById(new MatchId(10)).getResult())
                .isEqualTo(result);
        await().atMost(5, TimeUnit.SECONDS).until(() -> !catchEvents.isEmpty());
        assertThat(catchEvents.get(0).getData()).isInstanceOf(ResultUpdatedEvent.class);
    }

    @Test
    public void updateState_Finished_Happy_Flow() throws Exception {
        List<Event> catchEvents = new ArrayList<>();
        eventBus.on($(TTTEvent.class), event -> {
            if(event.getData() instanceof MatchStateChangedEvent) {
                catchEvents.add(event);
            }
        });

        mvc.perform(put("/matches/10/state")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"state\" : \"FINISHED\"}"))
                .andExpect(status().isOk());

        Match match = matchRepository.findById(new MatchId(10));
        assertThat(match.getState())
                .isEqualTo(Match.State.FINISHED);

        await().atMost(5, TimeUnit.SECONDS).until(() -> !catchEvents.isEmpty());
        assertThat(catchEvents.get(0).getData()).isInstanceOf(MatchStateChangedEvent.class);
    }

 @Test
    public void updateResult_validation_error() throws Exception {
        mvc.perform(put("/matches/10/result")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"games\":[{\"score_player_a\":7,\"score_player_b\":8}]}"))
                .andExpect(status().is(400));
    }
}
