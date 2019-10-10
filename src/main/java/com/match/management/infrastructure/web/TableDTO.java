package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.table.Table;
import lombok.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(SnakeCaseStrategy.class)
public class TableDTO {
    private String tableId;
    private Integer tableManagerId;
    private List<MatchDTO> matches;

    public static TableDTO from(Table table, Function<MatchId, Match> matchResolver) {
        return TableDTO.builder()
                .tableId(table.getId().getValue())
                .tableManagerId(table.getTableManagerId() == null ? null : table.getTableManagerId().getValue())
                .matches(table.getMatches().stream()
                        .map(matchResolver)
                        .map(MatchDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
