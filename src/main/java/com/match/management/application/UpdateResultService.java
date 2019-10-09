package com.match.management.application;

import com.match.management.domain.EventRepository;
import com.match.management.domain.ResultUpdatedEvent;
import com.match.management.domain.match.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateResultService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    EventRepository eventRepository;

    public void updateResult(MatchId matchId, Result result) {
        if(!result.isValid()) {
            throw new InvalidResultException();
        }

        Match match = matchRepository.findById(matchId);
        match.updateResult(result);

        eventRepository.publishEvent(new ResultUpdatedEvent(matchId));
    }
}
