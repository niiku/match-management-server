package com.match.management.application;

import com.match.management.domain.MatchAssignedToTableEvent;
import com.match.management.domain.TTTEvent;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableId;
import com.match.management.domain.table.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

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
        if (table == null) {
            table = new Table(tableId, null, match.getId());
        } else {
            table.setActiveMatch(match.getId());
        }
        tableRepository.save(table);

        eventBus.notify(TTTEvent.class, Event.wrap(new MatchAssignedToTableEvent(tableId, match.getId())));
    }
}
