package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchDTO {
    private final String matchId;
    private final String classification;
    private final PlayerDTO playerA;
    private final PlayerDTO playerB;
    private final ResultDTO result;
}
