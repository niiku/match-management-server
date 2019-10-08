package com.match.management.domain.ddd;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * Represents the aggregate id of an aggregate instance. Each aggregate contains one single annotated accessor method
 * providing access to the aggregate id.
 */
@Target(FIELD)
@Documented
public @interface AggregateId {

}
