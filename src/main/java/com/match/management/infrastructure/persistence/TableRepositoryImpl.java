package com.match.management.infrastructure.persistence;

import com.match.management.domain.match.MatchId;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableId;
import com.match.management.domain.table.TableManagerId;
import com.match.management.domain.table.TableRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TableRepositoryImpl implements TableRepository {

    private static Map<TableId, Table> tables = new HashMap<>();

    @Override
    public List<Table> getTables() {
        ArrayList<Table> tables = new ArrayList<>(TableRepositoryImpl.tables.values());
        tables.sort(Comparator.comparing(id -> String.format("%1$3s", id.getId())));
        return tables;
    }

    @Override
    public Table findTable(TableId tableId) {
        return tables.get(tableId);
    }

    @Override
    public List<Table> findTables(TableManagerId tableManagerId) {
        return getTables().stream()
                .filter(t -> tableManagerId.equals(t.getTableManagerId()))
                .collect(Collectors.toList());
    }

    @Override
    public Table findTable(MatchId matchId) {
        return getTables().stream()
                .filter(t -> t.getMatches().contains(matchId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Table table) {
        Table existingTable = findTable(table.getId());
        if (existingTable == null) {
            table.setTableManagerId(new TableManagerId(((tables.size()) / 5) + 1));
        }
        tables.put(table.getId(), table);
    }
}
