package com.match.management.domain.match;

import lombok.Value;

@Value
public class GameResult {
    private final int scorePlayerA;
    private final int scorePlayerB;

    public boolean isValid() {
        return (scorePlayerA >= 0 && scorePlayerB >= 0)
                && (Math.max(scorePlayerA,scorePlayerB) >= 11)
                && (Math.abs(scorePlayerA - scorePlayerB) >= 2);
    }
}
