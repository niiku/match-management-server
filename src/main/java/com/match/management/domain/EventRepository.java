package com.match.management.domain;

public interface EventRepository {

    void publishEvent(Event event);

    void subscribe(EventListener eventListener);
}
