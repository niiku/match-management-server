package com.match.management.application;

import com.match.management.domain.ResultUpdatedEvent;
import com.match.management.domain.TTTEvent;
import com.match.management.domain.match.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

@Service
public class UpdateResultService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    private EventBus eventBus;

    public void updateResult(MatchId matchId, Result result) {
        if(!result.isValid()) {
            throw new InvalidResultException();
        }

        Match match = matchRepository.findById(matchId);
        match.updateResult(result);

        eventBus.notify(TTTEvent.class, Event.wrap(new ResultUpdatedEvent(matchId)));
    }
}
