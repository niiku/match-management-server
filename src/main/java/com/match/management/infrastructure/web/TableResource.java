package com.match.management.infrastructure.web;

import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableManagerId;
import com.match.management.domain.table.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tables", produces = MediaType.APPLICATION_JSON_VALUE)
public class TableResource {

    @Autowired
    TableRepository tableRepository;

    @Autowired
    MatchRepository matchRepository;

    @GetMapping()
    public List<TableDTO> getTables(@RequestParam(name = "table_manager", required = false) Integer tableManagerId) {
        List<Table> tables;
        if(tableManagerId == null) {
            tables = tableRepository.getTables();
        } else {
            tables = tableRepository.findTables(new TableManagerId(tableManagerId.intValue()));
        }
        return tables.stream()
                .map(table -> TableDTO.from(table, matchId -> matchRepository.findById(matchId)))
                .collect(Collectors.toList());
    }

    @GetMapping("{tableId}")
    public TableDTO getTable(@PathVariable("tableId") String tableId) {
        return TableDTO.builder().build();
    }
}
