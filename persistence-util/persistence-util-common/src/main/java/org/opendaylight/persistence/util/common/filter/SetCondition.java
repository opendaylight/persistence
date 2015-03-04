/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.filter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.util.common.Converter;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Set filter condition.
 * 
 * @param <D> type of the attribute to apply the filter to
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class SetCondition<D> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Set<D> values;
    private final Mode mode;

    private SetCondition(@Nonnull Set<D> values, @Nonnull Mode mode) {
        Preconditions.checkNotNull(values, "values");
        this.values = Collections.unmodifiableSet(new HashSet<D>(values));
        this.mode = Preconditions.checkNotNull(mode, "mode");
    }

    /**
     * Creates an "in" filter condition.
     * 
     * @param values value to compare to
     * @return a set condition
     */
    public static <D> SetCondition<D> in(@Nonnull Set<D> values) {
        return new SetCondition<D>(values, Mode.IN);
    }

    /**
     * Creates an "in" filter condition.
     * 
     * @param values value to compare to
     * @return a set condition
     */
    @SafeVarargs
    public static <D> SetCondition<D> in(@Nonnull D... values) {
        return SetCondition.in(new HashSet<D>(Arrays.asList(values)));
    }

    /**
     * Creates a "not in" filter condition.
     * 
     * @param values value to compare to
     * @return a set condition
     */
    public static <D> SetCondition<D> notIn(@Nonnull Set<D> values) {
        return new SetCondition<D>(values, Mode.NOT_IN);
    }

    /**
     * Creates a "not in" filter condition.
     * 
     * @param values value to compare to
     * @return a set condition
     */
    @SafeVarargs
    public static <D> SetCondition<D> notIn(@Nonnull D... values) {
        return SetCondition.notIn(new HashSet<D>(Arrays.asList(values)));
    }

    /**
     * Gets the value to filter by.
     * 
     * @return the value to filter by
     */
    public Set<D> getValues() {
        return this.values;
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
    public <T> SetCondition<T> convert(@Nonnull Converter<D, T> converter) {
        Preconditions.checkNotNull(converter, "converter");
        Set<T> convertedValues = new HashSet<T>();
        for (D value : this.values) {
            convertedValues.add(converter.convert(value));
        }

        return new SetCondition<T>(convertedValues, this.mode);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("values", this.values).add("mode", this.mode).toString();
    }


    /**
     * Filter mode.
     */
    public static enum Mode {
        /**
         * Elements that are in to the specified values are selected by the filter.
         */
        IN,
        /**
         * Elements that are not in the specified values are selected by the filter.
         */
        NOT_IN
    }
}
