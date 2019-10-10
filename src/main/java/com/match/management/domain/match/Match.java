package com.match.management.domain.match;

import com.match.management.domain.ddd.Aggregate;
import com.match.management.domain.ddd.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Aggregate
@AllArgsConstructor
public class Match {

    @AggregateId
    private MatchId id;

    private Classification classification;
    private Player playerA;
    private Player playerB;
    private Result result;



    public void updateResult(Result result) {
        this.result = result;
    }

}
