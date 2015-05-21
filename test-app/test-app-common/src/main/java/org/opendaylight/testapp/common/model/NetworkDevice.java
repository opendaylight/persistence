/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.common.model;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.opendaylight.persistence.util.common.model.SerializableAbstractIdentifiable;
import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.testapp.common.type.IpAddress;
import org.opendaylight.testapp.common.type.Location;
import org.opendaylight.testapp.common.type.MacAddress;
import org.opendaylight.testapp.common.type.ReachabilityStatus;
import org.opendaylight.testapp.common.type.SerialNumber;
import org.opendaylight.yangtools.concepts.Identifiable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Network Device.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class NetworkDevice extends SerializableAbstractIdentifiable<NetworkDevice, SerialNumber> implements Identifiable<SerialNumber>, Serializable {
    private static final long serialVersionUID = 1L;

    private final SerialNumber serialNumber;
    private final MacAddress macAddress;
    private IpAddress ipAddress;
    private Location location;
    private String friendlyName;
    private ReachabilityStatus reachabilityStatus;

    /**
     * Creates a network device.
     * 
     * @param serialNumber device's serial number
     * @param macAddress device's MAC address
     * @param reachabilityStatus reachability status
     */
    public NetworkDevice(Id<NetworkDevice, SerialNumber> id, @Nonnull SerialNumber serialNumber, @Nonnull MacAddress macAddress,
            @Nonnull ReachabilityStatus reachabilityStatus) {
    	super(id);
        this.serialNumber = Preconditions.checkNotNull(serialNumber, "serialNumber");

        
         /** In reality MAC Address may change, for for illustration purposes it is assumed it never
         * changes.*/
         
        this.macAddress = Preconditions.checkNotNull(macAddress, "macAddress");

        
         /** setReachabilityStatus is final and thus it is no longer a foreign method. So it is safe
         * to be called in a constructor.*/
         
        setReachabilityStatus(reachabilityStatus);
    }
    
    @Override
    public SerialNumber getIdentifier() {
        return this.serialNumber;
    }

    /**
     * @return the macAddress
     */
    public MacAddress getMacAddress() {
        return this.macAddress;
    }

    /**
     * @return the ipAddress
     */
    public IpAddress getIpAddress() {
        return this.ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(@Nullable IpAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(@Nullable Location location) {
        this.location = location;
    }

    /**
     * @return the friendlyName
     */
    public String getFriendlyName() {
        return this.friendlyName;
    }

    /**
     * @param friendlyName the friendlyName to set
     */
    public void setFriendlyName(@Nullable String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /**
     * @return the reachabilityStatus
     */
    public ReachabilityStatus getReachabilityStatus() {
        return this.reachabilityStatus;
    }

    /**
     * @param reachabilityStatus the reachabilityStatus to set
     */
    public final void setReachabilityStatus(ReachabilityStatus reachabilityStatus) {
        this.reachabilityStatus = Preconditions.checkNotNull(reachabilityStatus, "reachabilityStatus");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", this.getId().getValue())
                .add("macAddress", this.macAddress).add("ipAddress", this.ipAddress).add("location", this.location)
                .add("friendlyName", this.friendlyName).add("reachabilityStatus", this.reachabilityStatus).toString();
    }

}
