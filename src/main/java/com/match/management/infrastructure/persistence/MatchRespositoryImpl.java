package com.match.management.infrastructure.persistence;

import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MatchRespositoryImpl implements MatchRepository {

    private static Map<MatchId, Match> matches = new HashMap<>();

    @Override
    public Match findById(MatchId matchId) {
        return matches.get(matchId);
    }

    @Override
    public void save(Match match) {
        matches.put(match.getId(), match);
    }

    @Override
    public void remove(MatchId matchId) {
        matches.remove(matchId);
    }

}
