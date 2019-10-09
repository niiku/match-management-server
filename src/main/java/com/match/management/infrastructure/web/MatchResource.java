package com.match.management.infrastructure.web;

import com.match.management.application.UpdateResultService;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
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
    UpdateResultService updateResultService;

    @PutMapping(path = "{match_id}/result", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateResult(@PathVariable(name = "match_id") long matchId, @RequestBody ResultDTO result) {
        Match match = findMatch(matchId);
        updateResultService.updateResult(match.getId(), ResultDTO.to(result));
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

}
