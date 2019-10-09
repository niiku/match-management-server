package com.match.management.domain.match;

import lombok.Value;

import java.util.List;

@Value
public final class Result {
    private final List<GameResult> games;
}
