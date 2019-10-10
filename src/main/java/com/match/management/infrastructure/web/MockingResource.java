package com.match.management.infrastructure.web;

import com.match.management.application.AssignMatchService;
import com.match.management.domain.match.Match;
import com.match.management.domain.table.TableId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/mocking",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MockingResource {

    @Autowired
    AssignMatchService assignMatchService;

    @PostMapping("events")
    public void postEvent(@RequestBody ExternalEventDTO event) {
        assert event.getId() == ExternalEventId.MATCH_ASSIGNED_TO_TABLE;
        Match match = MatchDTO.to(event.getPayload().getMatch());
        TableId tableId = new TableId(event.getPayload().getTableId());
        assignMatchService.assignMatchToTable(match, tableId);
    }
}
