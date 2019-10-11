package com.match.management.application;


import com.match.management.domain.match.*;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableId;
import com.match.management.domain.table.TableRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import reactor.bus.EventBus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MatchServiceTest {

    private final Match match = Match.builder().id(new MatchId(0)).build();

    private Result result;

    private MatchService matchService;

    private MatchRepository matchRepositoryMock = mock(MatchRepository.class);
    private TableRepository tableRepositoryMock = mock(TableRepository.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        result = new Result(new ArrayList<>());
        matchService = new MatchService(matchRepositoryMock, tableRepositoryMock, mock(EventBus.class));
        when(matchRepositoryMock.findById(any())).thenReturn(match);
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

        matchService.updateState(match, Match.State.FINISHED);

        Assert.assertSame(Match.State.FINISHED, match.getState());
    }

    @Test
    public void setState_Finished_NotEnoughGames() {
        result.getGames().addAll(
                Arrays.asList(new GameResult(7, 11),
                        new GameResult(2, 11)));
        match.updateResult(result);

        thrown.expect(IllegalStateException.class);

        matchService.updateState(match, Match.State.FINISHED);
    }

    @Test
    public void setState_Finished_NotResults() {
        thrown.expect(IllegalStateException.class);

        matchService.updateState(match, Match.State.FINISHED);
    }

    @Test
    public void setState_Started_HappyCase() {
        when(tableRepositoryMock.findTable(match.getId())).thenReturn(Table.builder().id(new TableId("1")).build());

        matchService.updateState(match, Match.State.STARTED);

        Assert.assertSame(Match.State.STARTED, match.getState());
    }

    @Test
    public void setState_Started_Rejected() {
        Table table = Table.builder().id(new TableId("1")).build();
        table.getMatches().add(match.getId());
        MatchId id = new MatchId(55);
        Match match2 = Match.builder().id(id).state(Match.State.STARTED).build();
        table.getMatches().add(match2.getId());
        when(matchRepositoryMock.findById(id)).thenReturn(match2);
        when(tableRepositoryMock.findTable(match.getId())).thenReturn(table);

        thrown.expect(IllegalStateException.class);
        matchService.updateState(match, Match.State.STARTED);
    }

    @Test
    public void callPlayersA_happyflow() {
        PlayerId playerAId = new PlayerId(1);
        Match match = Match.builder().id(new MatchId(1))
                .playerA(Player.builder().id(playerAId).build())
                .playerB(Player.builder().id(new PlayerId(2)).build())
                .build();

        List<PlayerId> playerIds = Collections.singletonList(playerAId);

        matchService.callPlayers(match, playerIds);

        assertNotNull(match.getPlayerA().getCallCount().getTimeOfLastCall());
        assertNull(match.getPlayerB().getCallCount());
        assertThat(match.getPlayerA().getCallCount().getValue(), is(1));
    }

    @Test
    public void callPlayersB_happyflow() {
        PlayerId playerBId = new PlayerId(2);
        Match match = Match.builder().id(new MatchId(1))
                .playerA(Player.builder().id(new PlayerId(1)).build())
                .playerB(Player.builder().id(playerBId).build())
                .build();

        List<PlayerId> playerIds = Collections.singletonList(playerBId);

        matchService.callPlayers(match, playerIds);

        assertNotNull(match.getPlayerB().getCallCount().getTimeOfLastCall());
        assertNull(match.getPlayerA().getCallCount());
        assertThat(match.getPlayerB().getCallCount().getValue(), is(1));
    }

    @Test
    public void callAllPlayers_happyflow() {
        PlayerId playerAId = new PlayerId(1);
        PlayerId playerBId = new PlayerId(2);
        Match match = Match.builder().id(new MatchId(1))
                .playerA(Player.builder().id(playerAId).build())
                .playerB(Player.builder().id(playerBId).build())
                .build();

        List<PlayerId> playerIds = Arrays.asList(playerAId, playerBId);

        matchService.callPlayers(match, playerIds);

        assertNotNull(match.getPlayerA().getCallCount().getTimeOfLastCall());
        assertThat(match.getPlayerA().getCallCount().getValue(), is(1));
        assertNotNull(match.getPlayerB().getCallCount().getTimeOfLastCall());
        assertThat(match.getPlayerB().getCallCount().getValue(), is(1));
    }

    @Test
    public void callTwiceAllPlayers_happyflow() {
        PlayerId playerAId = new PlayerId(1);
        PlayerId playerBId = new PlayerId(2);
        Match match = Match.builder().id(new MatchId(1))
                .playerA(Player.builder().id(playerAId).build())
                .playerB(Player.builder()
                        .id(playerBId)
                        .callCount(new CallCount(1, LocalDateTime.now()))
                        .build())
                .build();

        List<PlayerId> playerIds = Arrays.asList(playerAId, playerBId);

        matchService.callPlayers(match, playerIds);
        matchService.callPlayers(match, playerIds);

        assertNotNull(match.getPlayerA().getCallCount().getTimeOfLastCall());
        assertThat(match.getPlayerA().getCallCount().getValue(), is(2));
        assertNotNull(match.getPlayerB().getCallCount().getTimeOfLastCall());
        assertThat(match.getPlayerB().getCallCount().getValue(), is(3));
    }
}