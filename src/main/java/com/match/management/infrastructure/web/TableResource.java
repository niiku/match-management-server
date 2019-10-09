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
            tables = tableRepository.findTablesByTableManager(new TableManagerId(tableManagerId.intValue()));
        }
        return tables.stream().map(table -> map(table)).collect(Collectors.toList());
    }

    @GetMapping("{tableId}")
    public TableDTO getTable(@PathVariable("tableId") String tableId) {
        return TableDTO.builder().build();
    }

    private TableDTO map(Table table) {
        return TableDTO.builder()
                .tableId(table.getId().getValue())
                .tableManagerId(table.getTableManagerId().getValue())
                .currentMatch(map(matchRepository.findById(table.getActiveMatch())))
                .build();
    }

    private MatchDTO map(Match match) {
        return MatchDTO.builder()
                .matchId(match.getId().getValue())
                .classification(match.getClassification().getValue())
                .playerA(map(match.getPlayerA()))
                .playerB(map(match.getPlayerB()))
                .result(new ResultDTO())
                .build();
    }

    private PlayerDTO map(Player player) {
        return PlayerDTO.builder()
                .club(player.getClub().getName())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .playerId(player.getId().getValue())
                .build();
    }
}
