package com.match.management.domain.match;

import com.match.management.domain.ddd.Aggregate;
import com.match.management.domain.ddd.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Aggregate
@AllArgsConstructor
public class Match {

    @AggregateId
    private MatchId id;

    private Classification classification;
    private Player playerA;
    private Player playerB;
    private Result matchSets;
    private LocalDateTime start;
    private LocalDateTime end;

    public void updateResult(Result result) {
        // TODO validate
        this.matchSets = result;
        // TODO publish event
    }

}
