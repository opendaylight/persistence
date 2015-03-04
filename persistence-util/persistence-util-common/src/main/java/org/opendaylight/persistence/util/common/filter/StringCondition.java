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

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * String filter condition.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class StringCondition implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String value;
    private final Mode mode;

    private StringCondition(@Nullable String value, @Nonnull Mode mode) {
        this.value = value;
        this.mode = Preconditions.checkNotNull(mode, "mode");
    }

    /**
     * Creates an "equal" filter condition.
     * 
     * @param value value to compare to
     * @return a string condition
     */
    public static StringCondition equalTo(@Nullable String value) {
        return new StringCondition(value, Mode.EQUAL);
    }

    /**
     * Creates an "unequal" filter condition.
     * 
     * @param value value to compare to
     * @return a string condition
     */
    public static StringCondition unequalTo(@Nullable String value) {
        return new StringCondition(value, Mode.UNEQUAL);
    }

    /**
     * Creates a "start with" filter condition.
     * 
     * @param value value to compare to
     * @return a string condition
     */
    public static StringCondition startWith(@Nonnull String value) {
        return new StringCondition(value, Mode.STARTS_WITH);
    }

    /**
     * Creates a "contain" filter condition.
     * 
     * @param value value to compare to
     * @return a string condition
     */
    public static StringCondition contain(@Nonnull String value) {
        return new StringCondition(value, Mode.CONTAINS);
    }

    /**
     * Creates an "end with" filter condition.
     * 
     * @param value value to compare to
     * @return a string condition
     */
    public static StringCondition endWith(@Nonnull String value) {
        return new StringCondition(value, Mode.ENDS_WITH);
    }

    /**
     * Gets the value to filter by.
     * 
     * @return the value to filter by
     */
    public String getValue() {
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
        UNEQUAL,
        /**
         * Elements that start with the specified value are selected by the filter.
         */
        STARTS_WITH,
        /**
         * Elements that contain the specified value are selected by the filter.
         */
        CONTAINS,
        /**
         * Elements that ends with the specified value are selected by the filter.
         */
        ENDS_WITH,
    }
}
