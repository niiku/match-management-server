package com.match.management.domain.match;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Player {
    private final PlayerId id;
    private final String firstName;
    private final String lastName;
    private final Club club;
    private final CallCount callCount;
}
