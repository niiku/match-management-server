package com.match.management.infrastructure.web;

import com.match.management.domain.TableDummy;
import com.match.management.domain.TableRepositoryDummy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tables", produces = MediaType.APPLICATION_JSON_VALUE)
public class TableResource {

    @Autowired
    TableRepositoryDummy tableRepository;

    @GetMapping()
    public List<TableDTO> getTables(@RequestParam(name = "table_manager", required = false) String tableManager) {
        return tableRepository.findTablesByTableManager(tableManager).stream().map(TableResource::fromTable).collect(Collectors.toList());
    }

    @GetMapping("{tableId}")
    public TableDTO getTable(@PathVariable("tableId") String tableId) {
        return fromTable(tableRepository.findTableByTableId(tableId));
    }

    private static TableDTO fromTable(TableDummy table) {
        return new TableDTO.TableDTOBuilder()
                .tableId("an id")
                .tableManagerId("a manager id")
                .currentMatch(
                        MatchDTO.builder()
                                .matchId("some match id")
                                .classification("Herren B")
                                .playerA(
                                        PlayerDTO.builder()
                                                .club("TST Stuttgart")
                                                .firstName("Nino")
                                                .lastName("Schurter")
                                                .build()
                                )
                                .playerB(
                                        PlayerDTO.builder()
                                                .club("FCB")
                                                .firstName("Hans")
                                                .lastName("Meier")
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
