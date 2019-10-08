package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDTO {
    private final String playerId;
    private final String club;
    private final String firstName;
    private final String lastName;
}
