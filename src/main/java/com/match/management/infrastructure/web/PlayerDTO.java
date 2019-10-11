package com.match.management.infrastructure.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.match.management.domain.match.CallCount;
import com.match.management.domain.match.Club;
import com.match.management.domain.match.Player;
import com.match.management.domain.match.PlayerId;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(SnakeCaseStrategy.class)
public class PlayerDTO {
    private long playerId;
    private String club;
    private String firstName;
    private String lastName;

    public static PlayerDTO from(Player player) {
        return PlayerDTO.builder()
                .club(player.getClub().getName())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .playerId(player.getId().getValue())
                .build();
    }

    public static Player to(PlayerDTO player) {
        if (player == null) {
            return null;
        }
        return Player.builder()
                .id(new PlayerId(player.getPlayerId()))
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .club(new Club(player.getClub()))
                .build();
    }
}
