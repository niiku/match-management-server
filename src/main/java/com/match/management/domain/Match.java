package com.match.management.domain;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Match {

    private long id;
    private String classification;
    private Player playerA;
    private Player playerB;
    private List<Pair<Integer,Integer>> matchSets;
    private LocalDateTime start;
    private LocalDateTime end;

}
