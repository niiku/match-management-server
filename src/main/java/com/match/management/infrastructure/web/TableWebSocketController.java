package com.match.management.infrastructure.web;

import com.match.management.domain.EventListener;
import com.match.management.domain.EventRepository;
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

    @PostConstruct
    public void setup() {
        eventRepository.subscribe(new EventListener() {
            @Override
            public void eventOcurred(Object event) {
                tableUpdate();
            }
        });
    }

    public void tableUpdate() {
        template.convertAndSend("/topic/table", TableDTO.builder().build());
    }

}
