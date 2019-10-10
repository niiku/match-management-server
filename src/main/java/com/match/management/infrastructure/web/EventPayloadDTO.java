package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(SnakeCaseStrategy.class)
public class EventPayloadDTO {
    private String tableId;
    private List<MatchDTO> matches;
}
