package com.match.management.application;

import com.match.management.domain.CallForMissingPlayerRequestedEvent;
import com.match.management.domain.MatchFinishedEvent;
import com.match.management.domain.MatchStartedEvent;
import com.match.management.domain.ResultUpdatedEvent;
import com.match.management.domain.match.*;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MatchService {

    private final MatchRepository matchRepository;

    private final TableRepository tableRepository;

    private final EventBus eventBus;

    public void callPlayers(Match match, List<PlayerId> playerIds) {
        Match calledMatch =  match.callPlayers(playerIds);
        matchRepository.save(calledMatch);

        Table table = tableRepository.findTable(match.getId());
        CallForMissingPlayerRequestedEvent event = new CallForMissingPlayerRequestedEvent(table.getId(), match);
        eventBus.notify(CallForMissingPlayerRequestedEvent.class, Event.wrap(event));
    }

    public void updateResult(MatchId matchId, Result result) {
        Match match = matchRepository.findById(matchId);
        if (!result.isValid()) {
            throw new InvalidResultException();
        }
        match.updateResult(result);
        eventBus.notify(ResultUpdatedEvent.class, Event.wrap(new ResultUpdatedEvent(match.getId())));
    }

    public void finish(MatchId matchId) {
        Match match = matchRepository.findById(matchId);
        Match finishedMatch = match.finish();
        matchRepository.save(finishedMatch);
        eventBus.notify(MatchFinishedEvent.class, Event.wrap(new MatchFinishedEvent(match.getId())));
    }

    public void start(MatchId matchId) {
        Match match = matchRepository.findById(matchId);
        assertNoOtherGameAlreadyStarted(match.getId());
        Match startedMatch = match.start();
        matchRepository.save(startedMatch);
        eventBus.notify(MatchStartedEvent.class, Event.wrap(new MatchStartedEvent(match.getId())));
    }

    /**
     * @throws IllegalStateException in case of any invalid result
     */
    private void assertNoOtherGameAlreadyStarted(MatchId matchId) {
        Table table = tableRepository.findTable(matchId);
        boolean otherMatchAlreadyStarted = table.getMatches().stream()
                .filter(id -> id != matchId)
                .map(matchRepository::findById)
                .anyMatch(Match::isStarted);

        if (otherMatchAlreadyStarted) {
            throw new IllegalStateException("Another Match already started on selected table");
        }
    }

    public void playerAWonByDefault(MatchId matchId) {
        Match match = matchRepository.findById(matchId);
        Match wonByDefaultMatch = match.playerAHasWonByDefault();
        matchRepository.save(wonByDefaultMatch);
        eventBus.notify(MatchFinishedEvent.class, Event.wrap(new MatchFinishedEvent(matchId)));
    }

    public void playerBWonByDefault(MatchId currentMatchId) {
        Match match = matchRepository.findById(currentMatchId);
        Match wonByDefaultMatch = match.playerBHasWonByDefault();
        matchRepository.save(wonByDefaultMatch);
        eventBus.notify(MatchFinishedEvent.class, Event.wrap(new MatchFinishedEvent(currentMatchId)));
    }


}
