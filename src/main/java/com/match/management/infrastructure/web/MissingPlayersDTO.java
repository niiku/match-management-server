package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
class MissingPlayersDTO {
    private List<Integer> playerIds;
}
