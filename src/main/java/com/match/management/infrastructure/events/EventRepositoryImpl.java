package com.match.management.infrastructure.events;

import com.match.management.domain.EventListener;
import com.match.management.domain.EventRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventRepositoryImpl implements EventRepository {
    private List<EventListener> listeners = new ArrayList<>();

    @Override
    public void publishEvent(Object event) {
        listeners.forEach(l -> l.eventOcurred(event));
    }

    @Override
    public void subscribe(EventListener eventListener) {
        listeners.add(eventListener);
    }

}
