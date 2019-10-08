package com.match.management.domain;

import lombok.Value;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Value
public final class Result {
    private final List<Pair<Integer, Integer>> sets;
}
