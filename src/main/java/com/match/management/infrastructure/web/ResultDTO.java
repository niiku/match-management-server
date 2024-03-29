package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.match.management.domain.match.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class ResultDTO {
    private List<GameResultDTO> games;
    private int gamesWonPlayerA;
    private int gamesWonPlayerB;

    public static ResultDTO from(Result result) {
        return ResultDTO.builder()
                .games(result.getGames().stream()
                        .map(GameResultDTO::from)
                        .collect(toList()))
                .gamesWonPlayerA(result.getGamesWonPlayerA())
                .gamesWonPlayerB(result.getGamesWonPlayerB())
                .build();
    }

    public static Result to(ResultDTO result) {
        if (result == null) {
            return null;
        }
        return new Result(result.getGames().stream()
                .map(GameResultDTO::to)
                .collect(toList()));
    }
}
