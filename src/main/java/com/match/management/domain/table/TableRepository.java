package com.match.management.domain.table;

import java.util.List;

public interface TableRepository {

    List<Table> getTables();

    List<Table> findTablesByTableManager(TableManagerId tableManagerId);
}