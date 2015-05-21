package org.opendaylight.testapp.persistence.bl;

import java.util.List;

import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.testapp.common.model.NetworkDevice;
import org.opendaylight.testapp.common.type.IpAddress;
import org.opendaylight.testapp.common.type.Location;
import org.opendaylight.testapp.common.type.SerialNumber;

/**
 * Provides all functionalities for the Network Device.
 * Provides methods to discover a network device,
 * get all the reachable or unreachable devices,
 * get devices by location etc. 
 */
public interface NetworkDeviceService {

    /**
     * Discovers a network device.
     * 
     * @param ipAddress IP Address of the device to discover
     * @return the discovered device
     */
    public NetworkDevice discover(IpAddress ipAddress);

    /**
     * Sets the device's friendly name.
     * 
     * @param id id of the device to update
     * @param friendlyName friendly name
     */
    public void setFriendlyName(Id<NetworkDevice, SerialNumber> id, String friendlyName); 

    /**
     * Sets the device's friendly name.
     * 
     * @param id id of the device to update
     * @param location location
     */
    public void setLocation(Id<NetworkDevice, SerialNumber> id, Location location);

    /**
     * Gets the reachable devices.
     * 
     * @return reachable devices
     */
    public List<NetworkDevice> getReachable();

    /**
     * Gets the unreachable devices.
     * 
     * @return unreachable devices
     */
    public List<NetworkDevice> getUnreachable();

    /**
     * Gets devices by location.
     * 
     * @param location location
     * @return devices located at {@code location}
     */
    public List<NetworkDevice> getByLocation(Location location);
}
