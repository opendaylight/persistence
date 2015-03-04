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
import javax.annotation.Nullable;

import org.opendaylight.persistence.util.common.Converter;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Equality filter condition.
 * 
 * @param <D> type of the attribute to apply the filter to
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class EqualityCondition<D> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final D value;
    private final Mode mode;

    private EqualityCondition(@Nullable D value, @Nonnull Mode mode) {
        this.value = value;
        this.mode = Preconditions.checkNotNull(mode, "mode");
    }

    /**
     * Creates an "equal" filter condition.
     * 
     * @param value value to compare to
     * @return an equality condition
     */
    public static <D> EqualityCondition<D> equalTo(@Nullable D value) {
        return new EqualityCondition<D>(value, Mode.EQUAL);
    }

    /**
     * Creates a "unequal" filter condition.
     * 
     * @param value value to compare to
     * @return an equality condition
     */
    public static <D> EqualityCondition<D> unequalTo(@Nullable D value) {
        return new EqualityCondition<D>(value, Mode.UNEQUAL);
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
     * @param converter value converter
     * @return converted condition
     */
    public <T> EqualityCondition<T> convert(@Nonnull Converter<D, T> converter) {
        Preconditions.checkNotNull(converter, "converter");
        return new EqualityCondition<T>(converter.convert(this.value), this.mode);
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
         * Elements that are equal to the specified value are selected by the filter.
         */
        EQUAL,
        /**
         * Elements that are not equal to the specified value are selected by the filter.
         */
        UNEQUAL
    }
}
