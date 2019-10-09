package com.match.management.domain.match;

import lombok.Value;

import java.util.List;

@Value
public final class Result {
    private final List<GameResult> games;

    public int getGamesWonPlayerA() {
        if(games == null) return 0;
        return (int)games.stream()
                .filter(GameResult::isValid)
                .filter(g -> g.getScorePlayerA() > g.getScorePlayerB())
                .count();
    }

    public int getGamesWonPlayerB() {
        if(games == null) return 0;
        return (int)games.stream()
                .filter(GameResult::isValid)
                .filter(g -> g.getScorePlayerA() < g.getScorePlayerB())
                .count();
    }
}
