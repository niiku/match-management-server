package com.match.management.infrastructure.web;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventPayloadDTO {
    private String tableId;
    private MatchDTO match;
}
