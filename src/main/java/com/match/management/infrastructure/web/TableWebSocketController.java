package com.match.management.infrastructure.web;

import com.match.management.domain.MatchAssignedToTableEvent;
import com.match.management.domain.ResultUpdatedEvent;
import com.match.management.domain.TTTEvent;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.table.TableId;
import com.match.management.domain.table.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class TableWebSocketController implements Consumer<Event<TTTEvent>> {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private MatchRepository matchRepository;

    private void tableUpdate(MatchId matchId) {
        tableUpdate(tableRepository.findTable(matchId).getId());
    }

    private void tableUpdate(TableId tableId) {
        template.convertAndSend(
                "/topic/table",
                TableDTO.from(tableRepository.findTable(tableId), matchId -> matchRepository.findById(matchId))
        );
    }

    @Override
    public void accept(Event<TTTEvent> event) {
        if (event.getData() instanceof ResultUpdatedEvent) {
            tableUpdate(((ResultUpdatedEvent)event.getData()).getMatchId());
        } else if (event.getData() instanceof MatchAssignedToTableEvent) {
            tableUpdate(((MatchAssignedToTableEvent)event.getData()).getTableId());
        }
    }

}
