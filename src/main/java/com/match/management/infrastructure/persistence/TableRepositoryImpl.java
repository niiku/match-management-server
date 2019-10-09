package com.match.management.infrastructure.persistence;

import com.match.management.domain.match.MatchId;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableId;
import com.match.management.domain.table.TableManagerId;
import com.match.management.domain.table.TableRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TableRepositoryImpl implements TableRepository {

    private static Map<TableId, Table> tables = new HashMap<>();

    @Override
    public List<Table> getTables() {
        return new ArrayList<>(tables.values());
    }

    @Override
    public Table findTable(TableId tableId) {
        return tables.get(tableId);
    }

    @Override
    public List<Table> findTables(TableManagerId tableManagerId) {
        return getTables().stream()
                .filter(t -> t.getTableManagerId().equals(tableManagerId))
                .collect(Collectors.toList());
    }

    @Override
    public Table findTable(MatchId matchId) {
        return getTables().stream()
                .filter(t -> t.getActiveMatch().equals(matchId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Table table) {
        Table existingTable = findTable(table.getId());
        if (existingTable == null) {
            table.setTableManagerId(new TableManagerId(((tables.size() + 1) / 5) + 1));
        }
        tables.put(table.getId(), table);
    }
}
