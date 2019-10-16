package com.match.management.domain;

import com.match.management.domain.match.Match;
import com.match.management.domain.table.TableId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CallForMissingPlayerRequestedEvent implements TTTEvent {

    private TableId tableId;
    private Match match;
}
