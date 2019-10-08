package com.match.management.infrastructure.persistence;

import com.match.management.domain.match.MatchId;
import com.match.management.domain.table.Table;
import com.match.management.domain.table.TableId;
import com.match.management.domain.table.TableManagerId;
import com.match.management.domain.table.TableRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TableRepositoryImpl implements TableRepository {

    private final Table tableDummy1 = new Table(new TableId("1"), new TableManagerId(1), new MatchId(1));
    private final Table tableDummy2 = new Table(new TableId("2"), new TableManagerId(1), new MatchId(2));
    private final Table tableDummy3 = new Table(new TableId("3"), new TableManagerId(1), new MatchId(3));

    @Override
    public List<Table> findTablesByTableManager(TableManagerId tableManagerId) {
        return Arrays.asList(tableDummy1, tableDummy2, tableDummy3);
    }
}
