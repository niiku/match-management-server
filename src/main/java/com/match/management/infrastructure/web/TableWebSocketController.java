package com.match.management.infrastructure.web;

import com.match.management.domain.MatchAssignmentEvent;
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
import reactor.bus.EventBus;
import reactor.fn.Consumer;

import javax.annotation.PostConstruct;

import static reactor.bus.selector.Selectors.$;

@Service
public class TableWebSocketController implements Consumer<Event<TTTEvent>> {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EventBus eventBus;

    @PostConstruct
    public void init() {
        eventBus.on($(TTTEvent.class), this);
    }

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
            tableUpdate(((ResultUpdatedEvent) event.getData()).getMatchId());
        } else if (event.getData() instanceof MatchAssignmentEvent) {
            tableUpdate(((MatchAssignmentEvent) event.getData()).getTableId());
        }
    }

}
