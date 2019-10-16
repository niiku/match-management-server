package com.match.management.domain.match;

import com.match.management.domain.ddd.Aggregate;
import com.match.management.domain.ddd.AggregateId;
import lombok.*;

@Getter
@Aggregate
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Match {

    public enum State {
        ASSIGNED,
        STARTED,
        FINISHED
    }

    @AggregateId
    private MatchId id;

    private State state = State.ASSIGNED;
    private Classification classification;
    private Stage stage;
    private Player playerA;
    private Player playerB;
    private Result result;

    public void updateResult(Result result) {
        this.result = result;
    }

    public Match finish() {
        this.result.assertResultIsComplete();
        state = State.FINISHED;
        return this;
    }

    public Match start() {
        state = State.STARTED;
        return this;
    }

    public void callPlayerA() {
        this.playerA = this.playerA.increaseCallCount();
    }

    public void callPlayerB() {
        this.playerB = this.playerB.increaseCallCount();
    }

    public boolean isStarted() {
        return this.state == State.STARTED;
    }

    public Match playerAWonByDefault(){
        Result wonByDefaultResult = Result.wonByDefaultByPlayerA();
        return setResultAndFinishMatch(wonByDefaultResult);
    }

    public Match playerBWonByDefault() {
        Result wonByDefaultResult = Result.wonByDefaultByPlayerB();
        return setResultAndFinishMatch(wonByDefaultResult);
    }

    private Match setResultAndFinishMatch(Result wonByDefaultResult) {
        updateResult(wonByDefaultResult);
        return finish();
    }

}
