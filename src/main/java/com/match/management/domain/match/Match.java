package com.match.management.domain.match;

import com.match.management.domain.ddd.Aggregate;
import com.match.management.domain.ddd.AggregateId;
import lombok.*;

@Getter
@Aggregate
@Builder
@NoArgsConstructor
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

    public void setState(State state) {
        if(state == null) {
            this.state = State.ASSIGNED;
        } else {
            switch(state) { // used switch for any future state
                case FINISHED:
                    validateFinishState();
                case ASSIGNED:
                default:
                    this.state = state;
            }
        }
    }

    /**
     * @throws IllegalStateException in case of any invalid result
     */
    private void validateFinishState() {
        if (this.result == null || this.result.getGames() == null || this.result.getGames().isEmpty()) {
            throw new IllegalStateException("Match without result cannot be set to finished");
        }
        if ( Math.max(result.getGamesWonPlayerA(), result.getGamesWonPlayerB()) < 3) {
            throw new IllegalStateException("Match without 3 won games by any player cannot be set to finished");
        }
    }

}
