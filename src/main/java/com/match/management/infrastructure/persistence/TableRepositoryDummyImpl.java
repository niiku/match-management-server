package com.match.management.infrastructure.persistence;

import com.match.management.domain.TableDummy;
import com.match.management.domain.TableRepositoryDummy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TableRepositoryDummyImpl implements TableRepositoryDummy {

    private final TableDummy tableDummy = new TableDummy() {
    };

    @Override
    public List<TableDummy> findTablesByTableManager(String tableManagerId) {
        return Arrays.asList(tableDummy, tableDummy, tableDummy);
    }

    @Override
    public TableDummy findTableByTableId(String tableId) {
        return tableDummy;
    }
}
