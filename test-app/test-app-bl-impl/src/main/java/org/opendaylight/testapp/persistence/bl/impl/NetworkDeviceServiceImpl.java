package org.opendaylight.testapp.persistence.bl.impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.persistence.util.common.type.SortSpecification;
import org.opendaylight.testapp.common.model.NetworkDevice;
import org.opendaylight.testapp.common.model.NetworkDeviceFilter;
import org.opendaylight.testapp.common.model.NetworkDeviceSortKey;
import org.opendaylight.testapp.common.type.IpAddress;
import org.opendaylight.testapp.common.type.Location;
import org.opendaylight.testapp.common.type.MacAddress;
import org.opendaylight.testapp.common.type.ReachabilityStatus;
import org.opendaylight.testapp.common.type.SerialNumber;
import org.opendaylight.testapp.persistence.PersistenceService;
import org.opendaylight.testapp.persistence.bl.NetworkDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NetworkDeviceServiceImpl implements NetworkDeviceService {

    private final PersistenceService persistenceService;
    private final AtomicLong idCount;
    private final Random random;
    private final Logger logger;
     
    public NetworkDeviceServiceImpl(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
        this.idCount = new AtomicLong(1);
        this.random = new Random();
        this.logger = LoggerFactory.getLogger(getClass());
    }
    

    @Override
    public NetworkDevice discover(IpAddress ipAddress) {
        SerialNumber serialNumber = SerialNumber.valueOf(String.valueOf(this.idCount.getAndIncrement()));
        Id<NetworkDevice, SerialNumber> id = Id.valueOf(serialNumber);
        MacAddress macAddress = getMacAddress();
        NetworkDevice device = new NetworkDevice(id, serialNumber, macAddress, ReachabilityStatus.REACHABLE);// revisit id.getValue()
        device.setIpAddress(ipAddress);
        try {
            this.persistenceService.networkDevice().store(device);
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to discover device: " + ipAddress, e);
            throw new RuntimeException("Unable to discover device: " + ipAddress);
        }
        return device;
    }

    @Override
    public void setFriendlyName(Id<NetworkDevice, SerialNumber> id, String friendlyName) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }

        NetworkDevice device = null;
        try {
            device = this.persistenceService.networkDevice().get(id);
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to load device with id " + id, e);
            throw new RuntimeException("Unable to load device with id " + id);
        }

        device.setFriendlyName(friendlyName);

        try {
            this.persistenceService.networkDevice().store(device);
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to store device with id " + id, e);
            throw new RuntimeException("Unable to store device with id " + id);
        }
    }

    @Override
    public void setLocation(Id<NetworkDevice, SerialNumber> id, Location location) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }

        NetworkDevice device = null;
        try {
            device = this.persistenceService.networkDevice().get(id);
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to load device with id " + id, e);
            throw new RuntimeException("Unable to load device with id " + id);
        }

        device.setLocation(location);

        try {
            this.persistenceService.networkDevice().store(device);
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to store device with id " + id, e);
            throw new RuntimeException("Unable to store device with id " + id);
        }
    }

    @Override
    public List<NetworkDevice> getReachable() {
        return getByReachabilityStatus(ReachabilityStatus.REACHABLE);
    }

    @Override
    public List<NetworkDevice> getUnreachable() {
        return getByReachabilityStatus(ReachabilityStatus.UNREACHABLE);
    }

    private List<NetworkDevice> getByReachabilityStatus(ReachabilityStatus reachabilityStatus) {
        NetworkDeviceFilter filter = NetworkDeviceFilter.byReachabilityStatus(reachabilityStatus);
        try {
        	SortSpecification<NetworkDeviceSortKey> sortSpecification = null;
            sortSpecification = new SortSpecification<NetworkDeviceSortKey>();
        	
            return this.persistenceService.networkDevice().find(filter, sortSpecification.getSortComponents());
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to retrieve devices", e);
            throw new RuntimeException("Unable to retrieve devices");
        }
    }

    @Override
    public List<NetworkDevice> getByLocation(Location location) {
        NetworkDeviceFilter filter = NetworkDeviceFilter.byLocation(location);
        try {
        	SortSpecification<NetworkDeviceSortKey> sortSpecification = null;
            sortSpecification = new SortSpecification<NetworkDeviceSortKey>();
            //sortSpecification.addSortComponent(NetworkDeviceSortKey.FRIENDLY_NAME, SortOrder.ASCENDING);
            return this.persistenceService.networkDevice().find(filter, sortSpecification.getSortComponents());
        }
        catch (PersistenceException e) {
            this.logger.error("Unable to retrieve devices", e);
            throw new RuntimeException("Unable to retrieve devices");
        }
    }

    private MacAddress getMacAddress() {
        int intValue = this.random.nextInt();
        byte[] intBytes = new byte[] { (byte) (intValue >>> 24), (byte) (intValue >>> 16), (byte) (intValue >>> 8),
                (byte) intValue };
        return MacAddress.valueOfOctets((byte) 0, (byte) 0, intBytes[0], intBytes[1], intBytes[2], intBytes[3]);
    }
}
