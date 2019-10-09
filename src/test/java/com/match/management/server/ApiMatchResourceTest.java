package com.match.management.server;

import com.match.management.domain.match.GameResult;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.match.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiMatchResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MatchRepository matchRepository;

    @Test
    public void getMatch_HappyFlow() throws Exception {
        mvc.perform(get("/matches/0"))
                .andExpect(status().isOk());
    }

    /*
    // NOT YET IMPLEMENTED
    @Test
    public void getMatch_NotFound404() throws Exception {
        mvc.perform(get("/matches/104"))
                .andExpect(status().is(404));
    }
    */

    @Test
    public void updateResult_happy_flow() throws Exception {
        Result result = new Result(singletonList(new GameResult(7, 11)));
        // TODO: create json programmatically
        mvc.perform(put("/matches/0/result")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"games\":[{\"score_player_a\":7,\"score_player_b\":11}]}"))
                .andExpect(status().isOk());
        assertThat(matchRepository.findById(new MatchId(0)).getMatchSets())
                .isEqualTo(result);
    }
}
