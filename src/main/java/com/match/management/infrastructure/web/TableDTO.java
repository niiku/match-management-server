package com.match.management.infrastructure.web;

import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.table.Table;
import lombok.Builder;
import lombok.Data;

import java.util.function.Function;

@Data
@Builder
public class TableDTO {
    private final String tableId;
    private final int tableManagerId;
    private final MatchDTO currentMatch;

    public static TableDTO from(Table table, Function<MatchId, Match> matchResolver) {
        return TableDTO.builder()
                .tableId(table.getId().getValue())
                .tableManagerId(table.getTableManagerId().getValue())
                .currentMatch(MatchDTO.from(matchResolver.apply(table.getActiveMatch())))
                .build();
    }
}
