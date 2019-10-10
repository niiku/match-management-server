package com.match.management.domain.match;

import lombok.Value;

@Value
public final class MatchState {

    public enum State {
        FINISHED
    }

    private final State value;
}
