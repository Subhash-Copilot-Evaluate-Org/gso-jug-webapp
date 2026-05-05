package com.gso.jug;

import com.gso.jug.model.Poll;
import com.gso.jug.repository.PollRepository;
import com.gso.jug.service.PollService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testpolldb;DB_CLOSE_DELAY=-1",
        "server.port=0",
        "server.ssl.enabled=false",
        "server.port.http=0"
})
@AutoConfigureMockMvc
public class PollFeatureTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollService pollService;

    @Test
    public void testDefaultPollIsInitialized() {
        Poll activePoll = pollService.getActivePoll();
        assertNotNull("Active poll should exist", activePoll);
        assertEquals("Poll should have 4 options", 4, activePoll.getOptions().size());
        assertTrue("Poll should be active", activePoll.isActive());
    }

    @Test
    public void testPollPageLoads() throws Exception {
        mockMvc.perform(get("/poll"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPollVoteEndpoint() throws Exception {
        Poll activePoll = pollService.getActivePoll();
        Long optionId = activePoll.getOptions().get(0).getId();
        int initialVotes = activePoll.getOptions().get(0).getVoteCount();

        mockMvc.perform(post("/poll/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"optionId\": " + optionId + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.options[0].voteCount", is(initialVotes + 1)));
    }

    @Test
    public void testPollVoteWithInvalidOptionReturns400() throws Exception {
        mockMvc.perform(post("/poll/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"optionId\": 99999}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPollResultsEndpoint() throws Exception {
        Poll activePoll = pollService.getActivePoll();
        mockMvc.perform(get("/poll/results/" + activePoll.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", notNullValue()))
                .andExpect(jsonPath("$.options", hasSize(4)));
    }

    @Test
    public void testPollResultsWithInvalidIdReturns404() throws Exception {
        mockMvc.perform(get("/poll/results/99999"))
                .andExpect(status().isNotFound());
    }
}
