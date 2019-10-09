package com.match.management.domain;

import java.util.List;

public interface EventRepository {

    void publishEvent(Object event);

    void subscribe(EventListener eventListener);

    List<Object> getAllEvents();
}
