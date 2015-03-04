/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.common.model;

import java.util.Date;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.util.common.filter.EqualityCondition;
import org.opendaylight.persistence.util.common.filter.IntervalCondition;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * TSDR metric filter.
 * 
 * @author Fabiel Zuniga
 */
public class TsdrMetricFilter {

    /*
     * The filter is the contract of supported queries (Supported conditions and their combination).
     * Visitor pattern is used to get the actual filter.
     */
    private Filter filter;

    private TsdrMetricFilter() {
    }

    /**
     * Creates a neutral filter that returns all metrics.
     * 
     * @return a neutral filter
     */
    public static TsdrMetricFilter all() {
        TsdrMetricFilter filter = new TsdrMetricFilter();
        filter.filter = new All();
        return filter;
    }

    /**
     * Creates a filter to retrieve metrics by name
     * 
     * @param name metric's name
     * @return a filter
     */
    public static TsdrMetricFilter byName(@Nonnull String name) {
        TsdrMetricFilter filter = new TsdrMetricFilter();
        filter.filter = new ByName(name);
        return filter;
    }

    /**
     * Creates a filter to retrieve metrics by timestamp
     * 
     * @param timestampCondition timestamp condition
     * @return a filter
     */
    public static TsdrMetricFilter byTimestamp(@Nonnull IntervalCondition<Date> timestampCondition) {
        TsdrMetricFilter filter = new TsdrMetricFilter();
        filter.filter = new ByTimestamp(timestampCondition);
        return filter;
    }

    /**
     * Creates a filter to retrieve metrics by timestamp
     * 
     * @param name metric's name
     * @param timestampCondition timestamp condition
     * @return a filter
     */
    public static TsdrMetricFilter byNameAndTimestamp(@Nonnull String name,
            @Nonnull IntervalCondition<Date> timestampCondition) {
        TsdrMetricFilter filter = new TsdrMetricFilter();
        filter.filter = new ByNameAndTimestamp(name, timestampCondition);
        return filter;
    }

    /**
     * Creates a filter to retrieve metrics by timestamp
     * 
     * @param valueCondition value condition
     * @return a filter
     */
    public static TsdrMetricFilter byValue(@Nonnull IntervalCondition<String> valueCondition) {
        TsdrMetricFilter filter = new TsdrMetricFilter();
        filter.filter = new ByValue(valueCondition);
        return filter;
    }

    /**
     * Accepts a visitor.
     * 
     * @param visitor visitor
     * @return the result of the visit
     */
    public <T> T accept(@Nonnull Visitor<T> visitor) {
        return this.filter.accept(visitor);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("filter", this.filter).toString();
    }

    private static abstract class Filter {
        protected EqualityCondition<String> nameCondition;
        protected IntervalCondition<String> valueCondition;
        protected IntervalCondition<Date> timestampCondition;

        protected abstract <T> T accept(@Nonnull Visitor<T> visitor);

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("nameCondition", this.nameCondition)
                    .add("valueCondition", this.valueCondition).add("timestampCondition", this.timestampCondition)
                    .toString();
        }
    }

    /**
     * Neutral filter that returns all metrics.
     */
    public static class All extends Filter {

        @Override
        protected <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Filter that retrieves metrics by name.
     */
    public static class ByName extends Filter {

        /**
         * Creates a filter.
         * 
         * @param name metric's name
         */
        public ByName(@Nonnull String name) {
            Preconditions.checkNotNull(name, "name");
            this.nameCondition = EqualityCondition.equalTo(name);
        }

        /**
         * Gets the name condition.
         * 
         * @return the condition
         */
        public EqualityCondition<String> getNameCondition() {
            return this.nameCondition;
        }

        @Override
        protected <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Filter that retrieves metrics by timestamp.
     */
    public static class ByTimestamp extends Filter {

        /**
         * Creates a filter.
         * 
         * @param timestampCondition timestamp condition
         */
        public ByTimestamp(@Nonnull IntervalCondition<Date> timestampCondition) {
            Preconditions.checkNotNull(timestampCondition, "timestampCondition");
            this.timestampCondition = timestampCondition;
        }

        /**
         * Gets the name condition.
         * 
         * @return the condition
         */
        public IntervalCondition<Date> getTimestampCondition() {
            return this.timestampCondition;
        }

        @Override
        protected <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Filter that retrieves metrics by name and timestamp.
     */
    public static class ByNameAndTimestamp extends Filter {

        /**
         * Creates a filter.
         * 
         * @param name metric's name
         * @param timestampCondition timestamp condition
         */
        public ByNameAndTimestamp(@Nonnull String name, @Nonnull IntervalCondition<Date> timestampCondition) {
            Preconditions.checkNotNull(name, "name");
            Preconditions.checkNotNull(timestampCondition, "timestampCondition");
            this.nameCondition = EqualityCondition.equalTo(name);
            this.timestampCondition = timestampCondition;
        }

        /**
         * Gets the name condition.
         * 
         * @return the condition
         */
        public EqualityCondition<String> getNameCondition() {
            return this.nameCondition;
        }

        @Override
        protected <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Filter that retrieves metrics by value.
     */
    public static class ByValue extends Filter {

        /**
         * Creates a filter.
         * 
         * @param valueCondition value condition
         */
        public ByValue(@Nonnull IntervalCondition<String> valueCondition) {
            Preconditions.checkNotNull(valueCondition, "valueCondition");
            this.valueCondition = valueCondition;
        }

        /**
         * Gets the name condition.
         * 
         * @return the condition
         */
        public IntervalCondition<String> getValueCondition() {
            return this.valueCondition;
        }

        @Override
        protected <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Filter visitor.
     * 
     * @param <T> type of the result of the visit
     */
    public static interface Visitor<T> {

        /**
         * Visits the filter.
         * 
         * @param filter filter to visit
         * @return the result of the visit
         */
        T visit(@Nonnull All filter);

        /**
         * Visits the filter.
         * 
         * @param filter filter to visit
         * @return the result of the visit
         */
        T visit(@Nonnull ByName filter);

        /**
         * Visits the filter.
         * 
         * @param filter filter to visit
         * @return the result of the visit
         */
        T visit(@Nonnull ByTimestamp filter);

        /**
         * Visits the filter.
         * 
         * @param filter filter to visit
         * @return the result of the visit
         */
        T visit(@Nonnull ByNameAndTimestamp filter);

        /**
         * Visits the filter.
         * 
         * @param filter filter to visit
         * @return the result of the visit
         */
        T visit(@Nonnull ByValue filter);
    }
}
