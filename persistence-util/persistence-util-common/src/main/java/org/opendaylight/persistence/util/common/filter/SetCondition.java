/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.filter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.opendaylight.persistence.util.common.Converter;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * Set filter condition.
 * 
 * @param <D>
 *            type of the attribute to apply the filter to
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class SetCondition<D> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Set<D> values;
    private final Mode mode;

    private SetCondition(Set<D> values, Mode mode) {
        this.values = ImmutableSet.copyOf(Preconditions.checkNotNull(values,
                "values"));
        this.mode = mode;
    }

    /**
     * Creates an "in" filter condition.
     * 
     * @param values
     *            value to compare to
     * @return a set condition
     */
    public static <D> SetCondition<D> in(Set<D> values) {
        return new SetCondition<D>(values, Mode.IN);
    }

    /**
     * Creates an "in" filter condition.
     * 
     * @param values
     *            value to compare to
     * @return a set condition
     */
    @SafeVarargs
    public static <D> SetCondition<D> in(D... values) {
        return SetCondition.in(ImmutableSet.copyOf(values));
    }

    /**
     * Creates a "not in" filter condition.
     * 
     * @param values
     *            value to compare to
     * @return a set condition
     */
    public static <D> SetCondition<D> notIn(Set<D> values) {
        return new SetCondition<D>(values, Mode.NOT_IN);
    }

    /**
     * Creates a "not in" filter condition.
     * 
     * @param values
     *            value to compare to
     * @return a set condition
     */
    @SafeVarargs
    public static <D> SetCondition<D> notIn(D... values) {
        return SetCondition.notIn(ImmutableSet.copyOf(values));
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
     * @param converter
     *            value converter
     * @return converted condition
     */
    public <T> SetCondition<T> convert(Converter<D, T> converter) {
        Preconditions.checkNotNull(converter, "converter");

        Set<T> convertedValues = new HashSet<T>();
        for (D value : this.values) {
            convertedValues.add(converter.convert(value));
        }

        return new SetCondition<T>(convertedValues, this.mode);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("values", this.values)
                .add("mode", this.mode).toString();
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
