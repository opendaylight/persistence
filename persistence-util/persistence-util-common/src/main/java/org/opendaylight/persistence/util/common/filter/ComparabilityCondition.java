/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.filter;

import java.io.Serializable;

import org.opendaylight.persistence.util.common.Converter;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Comparability filter condition.
 * 
 * @param <D>
 *            type of the attribute to apply the filter to
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class ComparabilityCondition<D extends Comparable<D>> implements
        Serializable {
    private static final long serialVersionUID = 1L;

    private final D value;
    private final Mode mode;

    private ComparabilityCondition(D value, Mode mode) {
        this.value = Preconditions.checkNotNull(value, "value");
        this.mode = Preconditions.checkNotNull(mode, "mode");
    }

    /**
     * Creates a "less than" filter condition.
     * 
     * @param value
     *            value to compare to
     * @return a comparability condition
     */
    public static <D extends Comparable<D>> ComparabilityCondition<D> lessThan(
            D value) {
        return new ComparabilityCondition<D>(value, Mode.LESS_THAN);
    }

    /**
     * Creates a "less than or equal to" filter condition.
     * 
     * @param value
     *            value to compare to
     * @return a comparability condition
     */
    public static <D extends Comparable<D>> ComparabilityCondition<D> lessThanOrEqualTo(
            D value) {
        return new ComparabilityCondition<D>(value, Mode.LESS_THAN_OR_EQUAL_TO);
    }

    /**
     * Creates an "equal" filter condition.
     * 
     * @param value
     *            value to compare to
     * @return a comparability condition
     */
    public static <D extends Comparable<D>> ComparabilityCondition<D> equalTo(
            D value) {
        return new ComparabilityCondition<D>(value, Mode.EQUAL);
    }

    /**
     * Creates a "greater than or equal to" filter condition.
     * 
     * @param value
     *            value to compare to
     * @return a comparability condition
     */
    public static <D extends Comparable<D>> ComparabilityCondition<D> greaterThanOrEqualTo(
            D value) {
        return new ComparabilityCondition<D>(value,
                Mode.GREATER_THAN_OR_EQUAL_TO);
    }

    /**
     * Creates a "greater than" filter condition.
     * 
     * @param value
     *            value to compare to
     * @return a comparability condition
     */
    public static <D extends Comparable<D>> ComparabilityCondition<D> greaterThan(
            D value) {
        return new ComparabilityCondition<D>(value, Mode.GREATER_THAN);
    }

    /**
     * Gets the value to filter by.
     * 
     * @return the value to filter by
     */
    public D getValue() {
        return this.value;
    }

    /**
     * Gets the filter mode.
     *
     * @return the filter mode
     */
    public Mode getMode() {
        return this.mode;
    }

    /**
     * Converts this condition to a different value type.
     * 
     * @param converter
     *            value converter
     * @return converted condition
     * @throws NullPointerException
     *             if {@code converter is null}
     */
    public <T extends Comparable<T>> ComparabilityCondition<T> convert(
            Converter<D, T> converter) {
        Preconditions.checkNotNull(converter, "converter");
        return new ComparabilityCondition<T>(converter.convert(this.value),
                this.mode);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("value", this.value)
                .add("mode", this.mode).toString();
    }

    /**
     * Filter mode.
     */
    public static enum Mode {
        /**
         * Elements that are less than the specified value are selected by the
         * filter.
         */
        LESS_THAN,
        /**
         * Elements that are less than or equal to the specified value are
         * selected by the filter.
         */
        LESS_THAN_OR_EQUAL_TO,
        /**
         * Elements that are equal to the specified value are selected by the
         * filter.
         */
        EQUAL,
        /**
         * Elements that are greater than or equal to the specified value are
         * selected by the filter.
         */
        GREATER_THAN_OR_EQUAL_TO,
        /**
         * Elements that are greater than the specified value are selected by
         * the filter.
         */
        GREATER_THAN
    }
}
