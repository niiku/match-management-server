package com.match.management.infrastructure.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TableInfo {
    private final String tableId;
    private final TableState tableState;
    private final Match currentMatch;

}
