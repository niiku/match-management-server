package com.match.management.domain.match;

import lombok.Value;

@Value
public class GameResult {
    private final int playerAScore;
    private final int playerBScore;
}
