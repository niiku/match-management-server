package com.match.management.domain;

import com.match.management.domain.match.MatchId;
import com.match.management.domain.table.TableId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchAssignedToTableEvent implements Event {
    private TableId tableId;
    private MatchId matchId;
}
