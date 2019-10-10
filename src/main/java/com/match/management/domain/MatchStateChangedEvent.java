package com.match.management.domain;

import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchStateChangedEvent implements Event {
    private MatchId matchId;
    private MatchState state;
}
