package com.match.management.domain.match;

import lombok.Data;

@Data
public class Player {
    private final PlayerId id;
    private final String firstName;
    private final String lastName;
    private final Club club;

}
