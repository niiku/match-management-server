package com.match.management.domain;

import lombok.Data;

@Data
public class Table {

    private long id;
    private long tableManagerId;
    private Match activeMatch;

}
