package com.match.management.application;

import com.match.management.domain.MatchAssignmentEvent;
import com.match.management.domain.MatchStateChangedEvent;
import com.match.management.domain.TTTEvent;
import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

import javax.annotation.PostConstruct;

import static reactor.bus.selector.Selectors.$;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DomainEventHandler {

    private final EventBus eventBus;

    private final TableRepository tableRepository;

    @PostConstruct
    void postConstruct() {
        eventBus.on($(TTTEvent.class), event -> this.handle((TTTEvent) event.getData()));
    }

    private void handle(TTTEvent event) {
        if (event instanceof MatchStateChangedEvent) {
            handleMatchStateChangedEvent((MatchStateChangedEvent) event);
        }
    }

    private void handleMatchStateChangedEvent(MatchStateChangedEvent event) {
        Table table = tableRepository.findTable(event.getMatchId());
        if( event.getState() == Match.State.FINISHED) {
            table.removeMatch(event.getMatchId());
        }
        eventBus.notify(TTTEvent.class, Event.wrap(new MatchAssignmentEvent(table.getId())));
    }
}
