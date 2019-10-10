package com.match.management.domain.match;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public final class MatchState {

    public enum State {
        FINISHED
    }

    private final State value;
}
