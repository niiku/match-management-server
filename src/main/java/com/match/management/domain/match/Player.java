package com.match.management.domain.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class Player {
    @NonNull
    private final PlayerId id;
    private final String firstName;
    private final String lastName;
    private final Club club;

    private CallCount callCount = new CallCount(1, LocalDateTime.now());

    public Player(@NonNull PlayerId id, String firstName, String lastName, Club club) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.club = club;
    }

    public void missing() {
        this.callCount = callCount.increaseCallCount();
    }


}
