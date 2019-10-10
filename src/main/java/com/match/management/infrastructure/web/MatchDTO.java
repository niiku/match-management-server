package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.match.management.domain.match.Classification;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.Stage;
import lombok.*;
import org.springframework.util.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(SnakeCaseStrategy.class)
public class MatchDTO {

    private long matchId;
    private String classification;
    private String state;
    private String stage;
    private PlayerDTO playerA;
    private PlayerDTO playerB;
    private ResultDTO result;

    public static MatchDTO from(Match match) {
        if (match == null) return null;
        return MatchDTO.builder()
                .matchId(match.getId().getValue())
                .state(match.getState() != null ? match.getState().toString() : null)
                .classification(match.getClassification().getValue())
                .stage(match.getStage().getValue())
                .playerA(PlayerDTO.from(match.getPlayerA()))
                .playerB(PlayerDTO.from(match.getPlayerB()))
                .result(ResultDTO.from(match.getResult()))
                .build();
    }

    public static Match to(MatchDTO match) {
        return Match.builder()
                .id(new MatchId(match.getMatchId()))
                .state(StringUtils.isEmpty(match.state) ? Match.State.ASSIGNED : Match.State.valueOf(match.state))
                .classification(new Classification(match.getClassification()))
                .stage(new Stage(match.getStage()))
                .playerA(PlayerDTO.to(match.getPlayerA()))
                .playerB(PlayerDTO.to(match.getPlayerB()))
                .result(ResultDTO.to(match.getResult()))
                .build();
    }
}
