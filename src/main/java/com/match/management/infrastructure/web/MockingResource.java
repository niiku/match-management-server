package com.match.management.infrastructure.web;

import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableId;
import com.match.management.domain.table.TableRepository;
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
    TableRepository tableRepository;

    @Autowired
    MatchRepository matchRepository;

    @PostMapping("events")
    public void postEvent(@RequestBody EventDTO event) {
        assert event.getId() == EventId.MATCH_ASSIGNED_TO_TABLE;
        Match match = MatchDTO.to(event.getPayload().getMatch());
        matchRepository.save(match);

        TableId tableId = new TableId(event.getPayload().getTableId());
        Table table = tableRepository.findTable(tableId);
        if (table == null) {
            table = new Table(tableId, null, match.getId());
        } else {
            table.setActiveMatch(match.getId());
        }
        tableRepository.save(table);
    }
}