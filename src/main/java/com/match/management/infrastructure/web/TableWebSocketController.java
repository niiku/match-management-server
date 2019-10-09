package com.match.management.infrastructure.web;

import com.match.management.domain.*;
import com.match.management.domain.match.MatchId;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.table.TableId;
import com.match.management.domain.table.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class TableWebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private MatchRepository matchRepository;

    @PostConstruct
    public void setup() {
        eventRepository.subscribe(event -> {
            if (event instanceof ResultUpdatedEvent) {
                tableUpdate(((ResultUpdatedEvent)event).getMatchId());
            } else if (event instanceof MatchAssignedToTableEvent) {
                tableUpdate(((MatchAssignedToTableEvent)event).getTableId());
            }
        });
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

}
