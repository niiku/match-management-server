package com.match.management.domain;

import com.match.management.domain.ddd.Aggregate;
import com.match.management.domain.ddd.AggregateId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Aggregate
public class Match {

    @AggregateId
    private MatchId id;

    private Classification classification;
    private Player playerA;
    private Player playerB;
    private Result matchSets;
    private LocalDateTime start;
    private LocalDateTime end;

}
