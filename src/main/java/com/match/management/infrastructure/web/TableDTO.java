package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.table.Table;
import lombok.*;

import java.util.function.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(SnakeCaseStrategy.class)
public class TableDTO {
    private String tableId;
    private int tableManagerId;
    private MatchDTO currentMatch;

    public static TableDTO from(Table table, Function<MatchId, Match> matchResolver) {
        return TableDTO.builder()
                .tableId(table.getId().getValue())
                .tableManagerId(table.getTableManagerId().getValue())
                .currentMatch(MatchDTO.from(matchResolver.apply(table.getActiveMatch())))
                .build();
    }
}
