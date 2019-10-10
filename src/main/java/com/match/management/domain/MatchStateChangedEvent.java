package com.match.management.domain;

import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchStateChangedEvent implements TTTEvent {
    private MatchId matchId;
    private Match.State state;
}
