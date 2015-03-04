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
import org.opendaylight.testapp.common.type.Location;
import org.opendaylight.testapp.common.type.ReachabilityStatus;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Network device filter.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class NetworkDeviceFilter {

    /*
     * The filter is the contract of supported queries (Supported conditions and their combination).
     * Visitor pattern is used to get the actual filter.
     */
    private Filter filter;

    private NetworkDeviceFilter() {
    }

    /**
     * Creates a neutral filter that returns all network devices
     * 
     * @return a neutral filter
     */
    public static NetworkDeviceFilter all() {
        NetworkDeviceFilter filter = new NetworkDeviceFilter();
        filter.filter = new All();
        return filter;
    }

    /**
     * Creates a filter to retrieve network devices by location
     * 
     * @param location location
     * @return a filter
     */
    public static NetworkDeviceFilter byLocation(@Nonnull Location location) {
        NetworkDeviceFilter filter = new NetworkDeviceFilter();
        filter.filter = new ByLocation(location);
        return filter;
    }

    /**
     * Creates a filter to retrieve network devices by reachability status
     * 
     * @param reachabilityStatus reachability status
     * @return a filter
     */
    public static NetworkDeviceFilter byReachabilityStatus(@Nonnull ReachabilityStatus reachabilityStatus) {
        NetworkDeviceFilter filter = new NetworkDeviceFilter();
        filter.filter = new ByReachabilityStatus(reachabilityStatus);
        return filter;
    }

    /**
     * Creates a filter to retrieve network devices by location and reachability status
     * 
     * @param location location
     * @param reachabilityStatus reachability status
     * @return a filter
     */
    public static NetworkDeviceFilter byLocationAndByReachabilityStatus(@Nonnull Location location,
            @Nonnull ReachabilityStatus reachabilityStatus) {
        NetworkDeviceFilter filter = new NetworkDeviceFilter();
        filter.filter = new ByLocationAndReachabilityStatus(location, reachabilityStatus);
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
        protected EqualityCondition<Location> locationCondition;
        protected EqualityCondition<ReachabilityStatus> reachabilityStatusCondition;

        protected abstract <T> T accept(@Nonnull Visitor<T> visitor);
        
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("locationCondition", this.locationCondition)
                    .add("reachabilityStatusCondition", this.reachabilityStatusCondition).toString();
        }
    }

    /**
     * Neutral filter that returns all network devices.
     */
    public static class All extends Filter {

        @Override
        protected <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Filter that retrieves network devices by location.
     */
    public static class ByLocation extends Filter {

        /**
         * Creates a filter.
         * 
         * @param location location
         */
        public ByLocation(@Nonnull Location location) {
            Preconditions.checkNotNull(location, "location");
            this.locationCondition = EqualityCondition.equalTo(location);
        }

        /**
         * Gets the location condition.
         * 
         * @return the condition
         */
        public EqualityCondition<Location> getLocationCondition() {
            return this.locationCondition;
        }
        
        @Override
        protected <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Filter that retrieves network devices by reachability status.
     */
    public static class ByReachabilityStatus extends Filter {

        /**
         * Creates a filter
         * 
         * @param reachabilityStatus reachability status
         */
        public ByReachabilityStatus(@Nonnull ReachabilityStatus reachabilityStatus) {
            Preconditions.checkNotNull(reachabilityStatus, "reachabilityStatus");
            this.reachabilityStatusCondition = EqualityCondition.equalTo(reachabilityStatus);
        }

        /**
         * Gets the reachability status condition.
         * 
         * @return the condition
         */
        public EqualityCondition<ReachabilityStatus> getReachabilityStatusCondition() {
            return this.reachabilityStatusCondition;
        }
        
        @Override
        protected <T> T accept(Visitor<T> visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Filter that retrieves network devices by location and reachability status.
     */
    public static class ByLocationAndReachabilityStatus extends Filter {

        /**
         * Creates a filter.
         * 
         * @param location location
         * @param reachabilityStatus reachability status
         */
        public ByLocationAndReachabilityStatus(@Nonnull Location location,
                @Nonnull ReachabilityStatus reachabilityStatus) {
            Preconditions.checkNotNull(location, "location");
            Preconditions.checkNotNull(reachabilityStatus, "reachabilityStatus");
            this.locationCondition = EqualityCondition.equalTo(location);
            this.reachabilityStatusCondition = EqualityCondition.equalTo(reachabilityStatus);
        }

        /**
         * Gets the location condition.
         * 
         * @return the condition
         */
        public EqualityCondition<Location> getLocationCondition() {
            return this.locationCondition;
        }

        /**
         * Gets the reachability status condition.
         * 
         * @return the condition
         */
        public EqualityCondition<ReachabilityStatus> getReachabilityStatusCondition() {
            return this.reachabilityStatusCondition;
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
        public T visit(@Nonnull ByLocation filter);
        
        /**
         * Visits the filter.
         * 
         * @param filter filter to visit
         * @return the result of the visit
         */
        public T visit(@Nonnull ByReachabilityStatus filter);
        
        /**
         * Visits the filter.
         * 
         * @param filter filter to visit
         * @return the result of the visit
         */
        public T visit(@Nonnull ByLocationAndReachabilityStatus filter);
    }
}
