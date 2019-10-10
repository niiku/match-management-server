package com.match.management.domain.table;

import com.match.management.domain.ddd.Aggregate;
import com.match.management.domain.match.MatchId;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Aggregate
public class Table {
    private TableId id;
    private TableManagerId tableManagerId;
    private final List<MatchId> matches = new ArrayList<>();

    public MatchId getActiveMatch() {
        return (matches.isEmpty()) ? null : matches.get(0);
    }

    public boolean removeMatch(MatchId id) {
        return matches.remove(id);
    }

    public void addMatch(MatchId id) {
        matches.add(id);
    }
}
