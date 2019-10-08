package com.match.management.domain;

import com.match.management.domain.ddd.Aggregate;
import lombok.Data;


@Data
@Aggregate
public class Table {

    private TableId id;
    private TableManagerId tableManagerId;
    private MatchId activeMatch;

}
