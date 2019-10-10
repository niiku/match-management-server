package com.match.management.domain;

import com.match.management.domain.table.TableId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchAssignmentEvent implements TTTEvent {
    private TableId tableId;
}
