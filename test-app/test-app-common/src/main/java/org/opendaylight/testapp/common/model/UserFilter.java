/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.common.model;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.util.common.filter.EqualityCondition;
import org.opendaylight.testapp.common.type.Password;
import org.opendaylight.testapp.common.type.Username;

import com.google.common.base.MoreObjects;

/**
 * User filter.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class UserFilter {

    /*
     * The filter is the contract of supported queries (Supported conditions and their combination).
     * Visitor pattern is used to get the actual filter.
     */
    private Filter filter;

    private UserFilter() {
    }

    /**
     * Creates a neutral filter that returns all network devices
     * 
     * @return a neutral filter
     */
    public static UserFilter all() {
        UserFilter filter = new UserFilter();
        filter.filter = new All();
        return filter;
    }

    /**
     * Creates a filter to retrieve users by enabled status
     * 
     * @param enabled enabled status
     * @return a filter
     */
    public static UserFilter byEnabledStatus(boolean enabled) {
        UserFilter filter = new UserFilter();
        filter.filter = new ByEnabledStatus(enabled);
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
        protected EqualityCondition<Username> usernameCondition;
        protected EqualityCondition<Password> passwordCondition;
        protected EqualityCondition<Boolean> enabledCondition;

        protected abstract <T> T accept(@Nonnull Visitor<T> visitor);

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("usernameCondition", this.usernameCondition)
                    .add("passwordCondition", this.passwordCondition).add("enabledCondition", this.enabledCondition)
                    .toString();
        }
    }

    /**
     * Neutral filter that returns all users.
     */
    public static class All extends Filter {

        @Override
        protected <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Filter that retrieves users by enabled status.
     */
    public static class ByEnabledStatus extends Filter {

        /**
         * Creates a filter
         * 
         * @param enabled enabling status
         */
        public ByEnabledStatus(boolean enabled) {
            this.enabledCondition = EqualityCondition.equalTo(Boolean.valueOf(enabled));
        }

        /**
         * Gets the enabled status condition.
         * 
         * @return the condition
         */
        public EqualityCondition<Boolean> getEnabledStatusCondition() {
            return this.enabledCondition;
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
        public T visit(@Nonnull All filter);

        /**
         * Visits the filter.
         * 
         * @param filter filter to visit
         * @return the result of the visit
         */
        public T visit(@Nonnull ByEnabledStatus filter);
    }
}
