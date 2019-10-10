package com.match.management.domain.match;

public interface MatchRepository {
    Match findById(MatchId matchId);

    void save(Match match);

    void remove(MatchId matchId);
}
