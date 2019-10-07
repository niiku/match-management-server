package com.match.management.infrastructure.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/table")
public class TableResource {

    @GetMapping(path = "{tableId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TableInfo getTableInfo(@PathVariable("tableId") String tableId) {
        return new TableInfo(tableId, TableState.ASSIGNED, null);
    }

}
