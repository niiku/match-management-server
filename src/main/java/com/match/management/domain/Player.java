package com.match.management.domain;

import lombok.Data;

@Data
public class Player {

    private PlayerId id;
    private String firstName;
    private String lastName;
    private Club clubName;

}
