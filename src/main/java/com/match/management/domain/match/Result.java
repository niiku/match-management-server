package com.match.management.domain.match;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public final class Result {
    private final List<GameResult> games;



    public int getGamesWonPlayerA() {
        return (int)games.stream()
                .filter(GameResult::isValid)
                .filter(g -> g.getScorePlayerA() > g.getScorePlayerB())
                .count();
    }

    public int getGamesWonPlayerB() {
        return (int)games.stream()
                .filter(GameResult::isValid)
                .filter(g -> g.getScorePlayerA() < g.getScorePlayerB())
                .count();
    }

    public boolean isValid() {
        return games.stream().allMatch(GameResult::isValid);
    }

    public void assertResultIsComplete() {
        if (getGames().isEmpty()) {
            throw new IllegalStateException("Match without result cannot be set to finished");
        }
        if (Math.max(getGamesWonPlayerA(), getGamesWonPlayerB()) < 3) {
            throw new IllegalStateException("Match without 3 won games by any player cannot be set to finished");
        }
    }
    
    
}
