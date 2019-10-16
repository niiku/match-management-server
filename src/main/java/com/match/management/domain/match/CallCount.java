package com.match.management.domain.match;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CallCount {
    private final int value;
    private final LocalDateTime timeOfLastCall;

    public CallCount increaseCallCount() {
        return new CallCount(this.getValue() + 1, LocalDateTime.now());
    }
}
