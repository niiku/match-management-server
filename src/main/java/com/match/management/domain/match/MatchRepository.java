package com.match.management.domain.match;

import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;

public interface MatchRepository {
    Match findById(MatchId matchId);
}
