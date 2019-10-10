package com.match.management.infrastructure.web;

import com.match.management.application.InvalidResultException;
import com.match.management.application.MatchService;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.match.MatchState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/matches", produces = MediaType.APPLICATION_JSON_VALUE)
public class MatchResource {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    MatchService matchService;

    @PutMapping(path = "{match_id}/result", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateResult(@PathVariable(name = "match_id") long matchId, @RequestBody ResultDTO result) {
        Match match = findMatch(matchId);
        try {
            updateResultService.updateResult(match.getId(), ResultDTO.to(result));
        } catch (InvalidResultException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    private Match findMatch(@PathVariable(name = "match_id") long matchId) {
        Match match = matchRepository.findById(new MatchId(matchId));
        if (match == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        }
        return match;
    }

    @GetMapping(path = "{match_id}")
    public MatchDTO getMatch(@PathVariable(name = "match_id") long matchId) {
        return MatchDTO.from(findMatch(matchId));
    }

    @PutMapping(path = "{match_id}/state", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateState(@PathVariable(name = "match_id") long matchId, @RequestBody MatchStateDTO state) {
        Match match = findMatch(matchId);
        matchService.updateState(match, new MatchState(MatchState.State.valueOf(state.getState())));
    }

}
