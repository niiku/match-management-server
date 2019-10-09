package com.match.management.infrastructure.web;

import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.table.Table;
import lombok.*;

import java.util.function.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TableDTO {
    private String tableId;
    private Integer tableManagerId;
    private MatchDTO currentMatch;

    public static TableDTO from(Table table, Function<MatchId, Match> matchResolver) {
        return TableDTO.builder()
                .tableId(table.getId().getValue())
                .tableManagerId(table.getTableManagerId() == null ? null : table.getTableManagerId().getValue())
                .currentMatch(MatchDTO.from(matchResolver.apply(table.getActiveMatch())))
                .build();
    }
}
