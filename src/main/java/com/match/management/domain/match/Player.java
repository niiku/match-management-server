package com.match.management.domain.match;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;


@Data
@Builder(toBuilder = true)
public class Player {
    @NonNull
    private final PlayerId id;
    private final String firstName;
    private final String lastName;
    private final Club club;
    @Builder.Default
    private final CallCount callCount = new CallCount(1, LocalDateTime.now());

    public Player increaseCallCount() {
        return this.toBuilder().callCount(callCount.increaseCallCount()).build();
    }


}
