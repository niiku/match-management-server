package com.match.management.application;

import com.match.management.domain.MatchStateChangedEvent;
import com.match.management.domain.ResultUpdatedEvent;
import com.match.management.domain.TTTEvent;
import com.match.management.domain.match.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

@Service
public class MatchService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    EventBus eventBus;

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
        match.setState(state);

        eventBus.notify(TTTEvent.class, Event.wrap(new MatchStateChangedEvent(match.getId(), match.getState())));
    }
}
