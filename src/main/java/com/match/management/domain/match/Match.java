package com.match.management.domain.match;

import com.match.management.domain.ddd.Aggregate;
import com.match.management.domain.ddd.AggregateId;
import lombok.*;

import java.time.LocalDateTime;

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

    public void incrementPlayerACallCount() {
        this.playerA = this.playerA.toBuilder().callCount(incrementCallCount(playerA.getCallCount())).build();
    }

    public void incrementPlayerBCallCount() {
        this.playerB = this.playerB.toBuilder().callCount(incrementCallCount(playerB.getCallCount())).build();
    }

    private CallCount incrementCallCount(CallCount cca) {
        return new CallCount(cca == null ? 1 : cca.getValue() + 1, LocalDateTime.now());
    }

    public boolean isStarted() {
        return this.state == State.STARTED;
    }

    public Match playerAWonByDefault(){
        Result wonByDefaultResult = Result.wonByDefaultPlayerA();
        updateResult(wonByDefaultResult);
        return finish();
    }

}
