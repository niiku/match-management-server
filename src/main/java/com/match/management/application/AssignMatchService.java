package com.match.management.application;

import com.match.management.domain.MatchAssignedToTableEvent;
import com.match.management.domain.TTTEvent;
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

import java.util.Arrays;
import java.util.List;

@Service
public class AssignMatchService {
    @Autowired
    TableRepository tableRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    EventBus eventBus;

    public void assignMatchToTable(Match match, TableId tableId) {
        // TODO: validation?

        matchRepository.save(match);
        Table table = tableRepository.findTable(tableId);
        List<MatchId> matches = Arrays.asList(match.getId());
        if (table == null) {
            table = new Table(tableId, null, matches);
        } else {
            table.setMatches(matches);
        }
        tableRepository.save(table);

        eventBus.notify(TTTEvent.class, Event.wrap(new MatchAssignedToTableEvent(tableId, match.getId())));
    }
}
