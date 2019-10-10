package com.match.management.domain.table;

import com.match.management.domain.ddd.Aggregate;
import com.match.management.domain.match.MatchId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Aggregate
@AllArgsConstructor
public class Table {
    private TableId id;
    private TableManagerId tableManagerId;
    private List<MatchId> matches;

    public MatchId getActiveMatch() {
        return (matches == null || matches.isEmpty()) ? null : matches.get(0);
    }

    public boolean removeMatch(MatchId id) {
        if (matches != null) {
            return matches.remove(id);
        }
        return false;
    }

    public void addMatch(MatchId id) {
        if(matches == null) matches = new ArrayList<>();
        matches.add(id);
    }
}
