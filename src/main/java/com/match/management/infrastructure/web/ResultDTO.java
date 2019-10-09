package com.match.management.infrastructure.web;

import com.match.management.domain.match.Result;
import lombok.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultDTO {
    private List<Pair<Integer, Integer>> games;

    public static ResultDTO from(Result result) {
        return ResultDTO.builder()
                .games(result.getSets())
                .build();
    }

    public static Result to(ResultDTO result) {
        if (result == null) {
            return null;
        }
        return new Result(result.getGames());
    }
}
