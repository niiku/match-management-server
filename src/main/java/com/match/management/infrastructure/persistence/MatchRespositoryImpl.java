package com.match.management.infrastructure.persistence;

import com.match.management.domain.match.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class MatchRespositoryImpl implements MatchRepository {

    public static final Player playerA = new Player(new PlayerId(1), "Hans", "Maier", new Club("TTV Entenhausen"));
    public static final Player playerB = new Player(new PlayerId(1), "Jürgen", "Müller", new Club("TTV Basel"));

    private static Map<MatchId, Match> matches = new HashMap<>();

    static {
        MatchId matchId = new MatchId(1);
        matches.put(matchId, new Match(matchId,
                new Classification("Herren A"), playerA, playerB,
                new Result(new ArrayList<>()), null, null));
    }

    @Override
    public Match findById(MatchId matchId) {
        return matches.get(matchId);
    }

    @Override
    public void save(Match match) {
        matches.put(match.getId(), match);
    }

}
