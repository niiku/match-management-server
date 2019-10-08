package com.match.management.infrastructure.web;

import com.match.management.domain.match.Player;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDTO {
    private final long playerId;
    private final String club;
    private final String firstName;
    private final String lastName;

    public static PlayerDTO from(Player player){
        return PlayerDTO.builder()
                .club(player.getClub().getName())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .playerId(player.getId().getValue())
                .build();
    }
}
