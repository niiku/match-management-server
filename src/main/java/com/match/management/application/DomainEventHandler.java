package com.match.management.application;

import com.match.management.domain.TableUpdatedEvent;
import com.match.management.domain.MatchFinishedEvent;
import com.match.management.domain.MatchStartedEvent;
import com.match.management.domain.match.MatchId;
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
        eventBus.on($(MatchFinishedEvent.class), event -> this.handle((MatchFinishedEvent) event.getData()));
        eventBus.on($(MatchStartedEvent.class), event -> this.handle((MatchStartedEvent) event.getData()));
    }

    private void handle(MatchFinishedEvent event) {
        MatchId finishedMatchId = event.getMatchId();
        Table table = tableRepository.findTable(finishedMatchId);
        table.removeMatch(finishedMatchId);
        notifyAboutTableChange(table);
    }

    private void handle(MatchStartedEvent event) {
        Table table = tableRepository.findTable(event.getMatchId());
        notifyAboutTableChange(table);
    }

    private void notifyAboutTableChange(Table table) {
        eventBus.notify(TableUpdatedEvent.class, Event.wrap(new TableUpdatedEvent(table.getId())));
    }
}
