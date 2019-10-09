package com.match.management.domain.table;

import com.match.management.domain.match.MatchId;

import java.util.List;

public interface TableRepository {

    List<Table> getTables();

    Table findTable(TableId tableId);

    List<Table> findTables(TableManagerId tableManagerId);

    Table findTable(MatchId matchId);

    void save(Table table);
}
