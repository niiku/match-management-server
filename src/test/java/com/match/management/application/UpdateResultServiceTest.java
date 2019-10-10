package com.match.management.application;


import com.match.management.domain.EventRepository;
import com.match.management.domain.match.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateResultServiceTest {

    private final Match t = new Match(new MatchId(0), null, null, null, null);

    private Result result;

    private UpdateResultService updateResultService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        result = new Result(new ArrayList<>());
        updateResultService = new UpdateResultService();
        updateResultService.matchRepository = mock(MatchRepository.class);
        when(updateResultService.matchRepository.findById(any()))
                .thenReturn(t);
        updateResultService.eventRepository =  mock(EventRepository.class);
    }

    @Test()
    public void updateResult_NonNegativeValues() {
        GameResult gameResult = new GameResult(-1, 11);
        result.getGames().add(gameResult);

        thrown.expect(InvalidGameResultException.class);

        updateResultService.updateResult(new MatchId(0), result);
    }

    @Test()
    public void updateResult_minOneValueGTE11() {
        GameResult gameResult = new GameResult(9, 5);
        result.getGames().add(gameResult);

        thrown.expect(InvalidGameResultException.class);

        updateResultService.updateResult(new MatchId(0), result);
    }

}