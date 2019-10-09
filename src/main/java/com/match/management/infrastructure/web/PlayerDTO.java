package com.match.management.infrastructure.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDTO {
    private final long playerId;
    private final String club;
    private final String firstName;
    private final String lastName;
}
