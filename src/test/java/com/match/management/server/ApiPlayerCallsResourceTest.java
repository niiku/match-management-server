package com.match.management.server;

import com.match.management.domain.CallForMissingPlayerRequestedEvent;
import com.match.management.domain.TTTEvent;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.match.Player;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.bus.selector.Selectors.$;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiPlayerCallsResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EventBus eventBus;

    @Test
    public void updateResult_happy_flow() throws Exception {
        List<Event> catchEvents = new ArrayList<>();
        eventBus.on($(TTTEvent.class), event -> {
            if(event.getData() instanceof CallForMissingPlayerRequestedEvent) {
                catchEvents.add(event);
            }
        });

        Match match = matchRepository.findById(new MatchId(15));
        Player playerA = match.getPlayerA();
        mvc.perform(post("/playercall/" + match.getId().getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"player_ids\":["+ playerA.getId().getValue() +"]}"))
                .andExpect(status().isOk());

        match = matchRepository.findById(new MatchId(15));
        assertThat(match.getPlayerA().getCallCount().getValue())
                .isEqualTo(1);
        await().atMost(5, TimeUnit.SECONDS).until(() -> !catchEvents.isEmpty());
        assertThat(catchEvents.get(0).getData()).isInstanceOf(CallForMissingPlayerRequestedEvent.class);
    }

}
