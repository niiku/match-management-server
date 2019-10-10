package com.match.management.application;


import com.match.management.domain.EventRepository;
import com.match.management.domain.match.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MatchServiceTest {

    private final Match match = new Match(new MatchId(0), null, null, null, null, null);

    private Result result;

    private MatchService matchService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        result = new Result(new ArrayList<>());
        matchService = new MatchService();
        matchService.matchRepository = mock(MatchRepository.class);
        when(matchService.matchRepository.findById(any()))
                .thenReturn(match);
        matchService.eventRepository =  mock(EventRepository.class);
    }

    @Test()
    public void updateResult_NonNegativeValues() {
        GameResult gameResult = new GameResult(-1, 11);
        result.getGames().add(gameResult);

        thrown.expect(InvalidResultException.class);

        matchService.updateResult(new MatchId(0), result);
    }

    @Test()
    public void updateResult_minOneValueGTE11() {
        GameResult gameResult = new GameResult(9, 5);
        result.getGames().add(gameResult);

        thrown.expect(InvalidResultException.class);

        matchService.updateResult(new MatchId(0), result);
    }

    @Test
    public void setState_Finished_HappyFlow() {
        result.getGames().addAll(
                Arrays.asList(new GameResult(7, 11),
                        new GameResult(5, 11),
                        new GameResult(11, 9),
                        new GameResult(2, 11)));
        match.updateResult(result);

        match.setState(new MatchState(MatchState.State.FINISHED));
    }

    @Test
    public void setState_Finished_NotEnoughGames() {
        result.getGames().addAll(
                Arrays.asList(new GameResult(7, 11),
                        new GameResult(2, 11)));
        match.updateResult(result);

        thrown.expect(IllegalStateException.class);

        match.setState(new MatchState(MatchState.State.FINISHED));
    }

    @Test
    public void setState_Finished_NotResults() {
        thrown.expect(IllegalStateException.class);

        match.setState(new MatchState(MatchState.State.FINISHED));
    }
}