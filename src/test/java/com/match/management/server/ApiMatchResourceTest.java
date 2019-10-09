package com.match.management.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiMatchResourceTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getMatch_HappyFlow() throws Exception {
        mvc.perform(get("/matches/1"))
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
}
