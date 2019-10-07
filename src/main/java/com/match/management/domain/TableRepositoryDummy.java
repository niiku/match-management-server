package com.match.management.domain;

import java.util.List;

public interface TableRepositoryDummy {

    List<TableDummy> findTablesByTableManager(String tableManagerId);

    TableDummy findTableByTableId(String tableId);
}
