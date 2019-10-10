package com.match.management.application;

import com.match.management.domain.MatchStateChangedEvent;
import com.match.management.domain.ResultUpdatedEvent;
import com.match.management.domain.TTTEvent;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.match.Result;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MatchService {

    private final MatchRepository matchRepository;

    private final TableRepository tableRepository;

    private final EventBus eventBus;

    public void updateResult(MatchId matchId, Result result) {
        Match match = matchRepository.findById(matchId);
        updateResult(match, result);
    }

    public void updateResult(Match match, Result result) {
        if(!result.isValid()) {
            throw new InvalidResultException();
        }
        match.updateResult(result);

        eventBus.notify(TTTEvent.class, Event.wrap(new ResultUpdatedEvent(match.getId())));
    }

    public void updateState(MatchId matchId, Match.State state) {
        Match match = matchRepository.findById(matchId);
        updateState(match, state);
    }

    public void updateState(Match match, Match.State state) {
        if(state == Match.State.STARTED) {
            validateStarted(match);
        }
        match.setState(state);
        matchRepository.save(match);
        eventBus.notify(TTTEvent.class, Event.wrap(new MatchStateChangedEvent(match.getId(), match.getState())));
    }

    /**
     * @throws IllegalStateException in case of any invalid result
     */
    private void validateStarted(Match match) {
        Table table = tableRepository.findTable(match.getId());
        List<Match> otherTableMatches = table.getMatches().stream()
                .filter(matchId -> matchId != match.getId())
                .map(matchRepository::findById)
                .collect(Collectors.toList());

        if (otherTableMatches.stream().anyMatch(m -> m.getState() == Match.State.STARTED)) {
            throw new IllegalStateException("Another match already started on selected table");
        }
    }
}
