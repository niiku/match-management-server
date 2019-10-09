package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(SnakeCaseStrategy.class)
public class EventDTO {
    private EventId id;
    private EventPayloadDTO payload;
}
