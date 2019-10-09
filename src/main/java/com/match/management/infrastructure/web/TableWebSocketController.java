package com.match.management.infrastructure.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class TableWebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedRate = 1000)
    public void tableUpdate() {
        System.out.println(">>>>>>>>>>>>>> sending table to client ");
        template.convertAndSend("/topic/table", TableDTO.builder().build());
    }

}
