package com.match.management.infrastructure.web;

import com.match.management.application.MatchService;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.match.PlayerId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/playercall", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerCallsResource {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchService matchService;

    @PostMapping(path = "{match_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void callPlayers(@PathVariable(name = "match_id") long matchId, @RequestBody MissingPlayersDTO playerIds) {
        Match match = matchRepository.findById(new MatchId(matchId));
        if (match == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        }
        matchService.callPlayers(match,
                playerIds.getPlayerIds().stream().map(PlayerId::new).collect(Collectors.toList()));
    }
}
