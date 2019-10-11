package com.match.management.application;

import com.match.management.domain.TableUpdatedEvent;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableId;
import com.match.management.domain.table.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AssignMatchService {
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EventBus eventBus;

    public void assignMatchesToTable(List<Match> matches, TableId tableId) {
        // TODO: validation?

        Set<TableId> updatedTables = new HashSet<>();

        // Remove matches from previous tables
        for(Match match : matches) {
            Table otherTable = tableRepository.findTable(match.getId());
            if(otherTable != null && otherTable.removeMatch(match.getId())) {
                tableRepository.save(otherTable);
                updatedTables.add(otherTable.getId());
            }
        }

        // Create new empty table or clear existing
        Table table = tableRepository.findTable(tableId);
        if (table == null) {
            table = Table.builder().id(tableId).build();
        } else {
            for(MatchId matchId : table.getMatches()) {
                matchRepository.remove(matchId);
            }
        }

        // Add new matches
        for(Match match : matches) {
            matchRepository.save(match);
            table.addMatch(match.getId());
        }
        tableRepository.save(table);
        updatedTables.add(table.getId());

        // Send events for all updated tables
        updatedTables.forEach(t -> eventBus.notify(TableUpdatedEvent.class, Event.wrap(new TableUpdatedEvent(t))));
    }
}
