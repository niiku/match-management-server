package com.match.management.infrastructure.persistence;

import com.match.management.domain.match.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MatchRespositoryImpl implements MatchRepository {

    public final Player playerA = new Player(new PlayerId(1), "Hans", "Maier", new Club("TTV Entenhausen"));
    public final Player playerB = new Player(new PlayerId(1), "Jürgen", "Müller", new Club("TTV Basel"));
    private final Match firstMatch = new Match(new MatchId(1),
            new Classification("Herren A"), playerA, playerB,
            new Result(new ArrayList<>()), null, null);

    @Override
    public Match findById(MatchId matchId) {
        return firstMatch;
    }
}
