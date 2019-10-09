package com.match.management.domain;

public interface EventRepository {

    void publishEvent(Object event);

    void subscribe(EventListener eventListener);
}
