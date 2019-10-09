package com.match.management.infrastructure.web;

import com.match.management.domain.match.Club;
import com.match.management.domain.match.Player;
import com.match.management.domain.match.PlayerId;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
        return new Player(
                new PlayerId(player.getPlayerId()),
                player.getFirstName(),
                player.getLastName(),
                new Club(player.getClub())
        );
    }
}
