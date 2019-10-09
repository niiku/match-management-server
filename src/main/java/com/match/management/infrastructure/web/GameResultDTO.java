package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.match.management.domain.match.GameResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class GameResultDTO {

    private int scorePlayerA;
    private int scorePlayerB;

    public static GameResultDTO from(GameResult gameResult) {
        return GameResultDTO.builder()
                .scorePlayerA(gameResult.getScorePlayerA())
                .scorePlayerB(gameResult.getScorePlayerB())
                .build();
    }

    public static GameResult to(GameResultDTO gameResultDTO) {
        return (gameResultDTO == null) ?
                null : new GameResult(gameResultDTO.scorePlayerA, gameResultDTO.scorePlayerB);
    }
}
