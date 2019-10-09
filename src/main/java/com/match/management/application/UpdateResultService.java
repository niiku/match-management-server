package com.match.management.application;

import com.match.management.domain.EventRepository;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.match.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateResultService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    EventRepository eventRepository;

    public void updateResult(MatchId matchId, Result result) {
        Match match = matchRepository.findById(matchId);

        // TODO validate

        match.updateResult(result);

        // TODO publish event
        eventRepository.publishEvent(matchId);
    }

}
