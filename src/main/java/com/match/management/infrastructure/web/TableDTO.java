package com.match.management.infrastructure.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableDTO {
    private final String tableId;
    private final int tableManagerId;
    private final MatchDTO currentMatch;
}
