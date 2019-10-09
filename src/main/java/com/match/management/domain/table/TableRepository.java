package com.match.management.domain.table;

import java.util.List;

public interface TableRepository {

    List<Table> getTables();

    Table findTable(TableId tableId);

    List<Table> findTablesByTableManager(TableManagerId tableManagerId);

    void save(Table table);
}
