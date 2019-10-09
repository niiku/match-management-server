package com.match.management.domain;

import com.match.management.domain.match.MatchId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultUpdatedEvent implements Event {
    private MatchId matchId;
}
