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

import org.opendaylight.persistence.util.common.type.TimePeriod;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Time period filter condition.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class TimePeriodCondition implements Serializable {
    private static final long serialVersionUID = 1L;

    private final TimePeriod value;
    private final Mode mode;

    private TimePeriodCondition(@Nonnull TimePeriod value, @Nonnull Mode mode) {
        this.value = Preconditions.checkNotNull(value, "value");
        this.mode = Preconditions.checkNotNull(mode, "mode");
    }

    /**
     * Creates an "in" filter condition.
     * 
     * @param value value to compare to
     * @return an equality condition
     */
    public static TimePeriodCondition in(@Nonnull TimePeriod value) {
        return new TimePeriodCondition(value, Mode.IN);
    }

    /**
     * Creates a "not in" filter condition.
     * 
     * @param value value to compare to
     * @return an equality condition
     */
    public static TimePeriodCondition notIn(@Nonnull TimePeriod value) {
        return new TimePeriodCondition(value, Mode.NOT_IN);
    }

    /**
     * Gets the value to filter by.
     * 
     * @return the value to filter by
     */
    public TimePeriod getValue() {
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
         * Elements inside the time period are selected by the filter.
         */
        IN,
        /**
         * Elements outside the time period are selected by the filter.
         */
        NOT_IN
    }
}
