package com.match.management.domain;

import com.match.management.domain.match.MatchId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchStartedEvent implements TTTEvent {
    private MatchId matchId;
}
