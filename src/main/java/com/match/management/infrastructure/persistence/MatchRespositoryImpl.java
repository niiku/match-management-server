package com.match.management.infrastructure.persistence;

import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
}
