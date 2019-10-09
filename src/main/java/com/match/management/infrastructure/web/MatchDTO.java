package com.match.management.infrastructure.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchDTO {
    private final long matchId;
    private final String classification;
    private final PlayerDTO playerA;
    private final PlayerDTO playerB;
    private final ResultDTO result;
}
