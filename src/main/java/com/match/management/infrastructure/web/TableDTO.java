package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableDTO {
    private final String tableId;
    private final String tableManagerId;
    private final MatchDTO currentMatch;
}
