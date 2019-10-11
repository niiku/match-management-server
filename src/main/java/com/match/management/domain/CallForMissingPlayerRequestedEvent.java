package com.match.management.domain;

import com.match.management.domain.match.Match;
import com.match.management.domain.table.TableId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CallForMissingPlayerRequestedEvent implements TTTEvent {

    private TableId tableId;
    private Match match;

}
