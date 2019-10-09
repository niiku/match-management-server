package com.match.management.domain.table;

import com.match.management.domain.ddd.Aggregate;
import com.match.management.domain.match.MatchId;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@Aggregate
@AllArgsConstructor
public class Table {
    private TableId id;
    private TableManagerId tableManagerId;
    private MatchId activeMatch;
}
