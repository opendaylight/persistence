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
import org.opendaylight.persistence.util.common.filter.SetCondition;
import org.opendaylight.testapp.common.type.Location;
import org.opendaylight.testapp.common.type.ReachabilityStatus;

import com.google.common.base.MoreObjects;

/**
 * Free form network device filter.
 * <p>
 * At the end the filter is the composition of all conditions with the logical operator {@code AND}.
 * USeful for relational models where queries are of free form (Where clauses for SQL for example).
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class FreeFormNetworkDeviceFilter {

    private SetCondition<Location> locationCondition;
    private EqualityCondition<ReachabilityStatus> reachabilityStatusCondition;

    /**
     * Gets the reachability status condition.
     * 
     * @return the condition
     */
    public EqualityCondition<ReachabilityStatus> getReachabilityStatusCondition() {
        return this.reachabilityStatusCondition;
    }

    /**
     * Sets the reachability status condition.
     * 
     * @param reachabilityStatusCondition the condition
     */
    public void setReachabilityStatusCondition(
            @Nonnull EqualityCondition<ReachabilityStatus> reachabilityStatusCondition) {
        this.reachabilityStatusCondition = reachabilityStatusCondition;
    }

    /**
     * Gets the location condition.
     * 
     * @return the locationCondition the location condition
     */
    public SetCondition<Location> getLocationCondition() {
        return this.locationCondition;
    }

    /**
     * Sets the location condition.
     * 
     * @param locationCondition the condition
     */
    public void setLocationCondition(@Nonnull SetCondition<Location> locationCondition) {
        this.locationCondition = locationCondition;
    }

    @Override
    public String toString(){
        return MoreObjects.toStringHelper(this).add("locationCondition", this.locationCondition)
                .add("reachabilityStatusCondition", this.reachabilityStatusCondition).toString();
    }
}
