package com.match.management.infrastructure.web;

import com.match.management.domain.match.Match;
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

    public static MatchDTO from(Match match) {
        return MatchDTO.builder()
                .matchId(match.getId().getValue())
                .classification(match.getClassification().getValue())
                .playerA(PlayerDTO.from(match.getPlayerA()))
                .playerB(PlayerDTO.from(match.getPlayerB()))
                .result(new ResultDTO())
                .build();
    }
}
