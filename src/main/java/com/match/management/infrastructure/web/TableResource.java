package com.match.management.infrastructure.web;

import com.match.management.domain.match.Match;
import com.match.management.domain.match.MatchRepository;
import com.match.management.domain.match.Player;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableManagerId;
import com.match.management.domain.table.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
            tables = tableRepository.findTablesByTableManager(new TableManagerId(tableManagerId.intValue()));
        }
        List<TableDTO> result = new ArrayList<>();
        for (Table table : tables) {
            Match currentMatch = matchRepository.findById(table.getActiveMatch());
            result.add(map(currentMatch, table));
        }
        return result;
    }

    private TableDTO map(Match currentMatch, Table table) {
        return new TableDTO(table.getId().getValue(),
                table.getTableManagerId().getValue(),
                new MatchDTO(currentMatch.getId().getValue(),
                        currentMatch.getClassification().getValue(),
                        PlayerDTO.from(currentMatch.getPlayerA()),
                        PlayerDTO.from(currentMatch.getPlayerB()),
                        new ResultDTO()));
    }

    @GetMapping("{tableId}")
    public TableDTO getTable(@PathVariable("tableId") String tableId) {
        return TableDTO.builder().build();
    }

}
