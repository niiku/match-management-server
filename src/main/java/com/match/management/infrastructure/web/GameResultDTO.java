package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GameResultDTO {

    private int playerAScore;
    private int playerBScore;

    public static GameResultDTO from(GameResult gameResult) {
        return GameResultDTO.builder()
                .playerAScore(gameResult.getPlayerAScore())
                .playerBScore(gameResult.getPlayerBScore())
                .build();
    }

    public static GameResult to(GameResultDTO gameResultDTO) {
        return (gameResultDTO == null) ?
                null : new GameResult(gameResultDTO.playerAScore, gameResultDTO.playerBScore);
    }
}
