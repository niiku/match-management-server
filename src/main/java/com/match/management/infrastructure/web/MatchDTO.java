package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.match.management.domain.match.Classification;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(SnakeCaseStrategy.class)
public class MatchDTO {
    
    private long matchId;
    private String classification;
    private PlayerDTO playerA;
    private PlayerDTO playerB;
    private ResultDTO result;

    public static MatchDTO from(Match match) {
        if (match == null) return null;
        return MatchDTO.builder()
                .matchId(match.getId().getValue())
                .classification(match.getClassification().getValue())
                .playerA(PlayerDTO.from(match.getPlayerA()))
                .playerB(PlayerDTO.from(match.getPlayerB()))
                .result(ResultDTO.from(match.getResult()))
                .build();
    }

    public static Match to(MatchDTO match) {
        return new Match(
                new MatchId(match.getMatchId()),
                new Classification(match.getClassification()),
                PlayerDTO.to(match.getPlayerA()),
                PlayerDTO.to(match.getPlayerB()),
                ResultDTO.to(match.getResult())
        );
    }
}
