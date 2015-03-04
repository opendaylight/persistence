/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.filter;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.util.common.Converter;
import org.opendaylight.persistence.util.common.type.Interval;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Interval filter condition.
 * 
 * @param <D> type of the attribute to apply the filter to
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class IntervalCondition<D extends Comparable<D>> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Interval<D> value;
    private final Mode mode;

    private IntervalCondition(@Nonnull Interval<D> value, @Nonnull Mode mode) {
        this.value = Preconditions.checkNotNull(value, "value");
        this.mode = Preconditions.checkNotNull(mode, "mode");
    }

    /**
     * Creates an "in" filter condition.
     * 
     * @param value value to compare to
     * @return an equality condition
     */
    public static <D extends Comparable<D>> IntervalCondition<D> in(@Nonnull Interval<D> value) {
        return new IntervalCondition<D>(value, Mode.IN);
    }

    /**
     * Creates a "not in" filter condition.
     * 
     * @param value value to compare to
     * @return an equality condition
     */
    public static <D extends Comparable<D>> IntervalCondition<D> notIn(@Nonnull Interval<D> value) {
        return new IntervalCondition<D>(value, Mode.NOT_IN);
    }

    /**
     * Gets the value to filter by.
     * 
     * @return the value to filter by
     */
    public Interval<D> getValue() {
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
     * @param converter value converter
     * @return converted condition
     */
    public <T extends Comparable<T>> IntervalCondition<T> convert(@Nonnull Converter<D, T> converter) {
        Preconditions.checkNotNull(converter, "converter");
        return new IntervalCondition<T>(this.value.convert(converter), this.mode);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("value", this.value).add("mode", this.mode).toString();
    }

    /**
     * Filter mode.
     */
    public static enum Mode {
        /**
         * Elements inside the interval are selected by the filter.
         */
        IN,
        /**
         * Elements outside the interval are selected by the filter.
         */
        NOT_IN
    }
}
