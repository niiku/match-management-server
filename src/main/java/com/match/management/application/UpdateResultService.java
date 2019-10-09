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
        Match match = matchRepository.findById(matchId);

        validateResult(result);
        match.updateResult(result);

        eventRepository.publishEvent(new ResultUpdatedEvent(matchId));
    }

    /**
     * @throws InvalidGameResultException
     */
    void validateResult(Result result) {
        result.getGames().forEach(this::validateGame);
    }

    private void validateGame(GameResult game) {
        if (game.getScorePlayerA() < 11 && game.getScorePlayerB() < 11) {
            throw new InvalidGameResultException("At least one value greater or equal 11 expected.");
        }
        if (game.getScorePlayerA() < 0 || game.getScorePlayerB() < 0) {
            throw new InvalidGameResultException("Negative values are not allowed");
        }
    }
}
