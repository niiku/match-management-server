package com.match.management.infrastructure.web;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventDTO {
    private EventId id;
    private EventPayloadDTO payload;
}
