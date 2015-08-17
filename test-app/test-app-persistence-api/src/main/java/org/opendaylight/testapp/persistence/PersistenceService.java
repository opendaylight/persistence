/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.persistence;

import java.util.List;

import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.store.ObjectStore;
import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.persistence.util.common.type.Sort;
import org.opendaylight.testapp.common.model.NetworkDevice;
import org.opendaylight.testapp.common.model.NetworkDeviceFilter;
import org.opendaylight.testapp.common.model.NetworkDeviceSortKey;
import org.opendaylight.testapp.common.model.TsdrMetric;
import org.opendaylight.testapp.common.model.TsdrMetricFilter;
import org.opendaylight.testapp.common.model.TsdrMetricSortKey;
import org.opendaylight.testapp.common.model.User;
import org.opendaylight.testapp.common.model.UserFilter;
import org.opendaylight.testapp.common.type.SerialNumber;
import org.opendaylight.testapp.common.type.Username;

/**
 * Persistence Service.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public interface PersistenceService {

    /**
     * Gets the network device persistence service.
     * 
     * @return the network device persistence service
     */
    NetworkDevicePersistenceService networkDevice();

    /**
     * Gets the user persistence service.
     * 
     * @return the user persistence service
     */
    UserPersistenceService user();

    /**
     * Gets the TSDR metric persistence service.
     * 
     * @return the TSDR metric persistence service
     */
    TsdrMetricPersistenceService tsdrMetric();

    /**
     * Network device persistence service.
     */
    public interface NetworkDevicePersistenceService extends
            ObjectStore<SerialNumber, NetworkDevice, NetworkDeviceFilter, NetworkDeviceSortKey> {

        /**
         * Stores a network device.
         * 
         * @param device device to store
         * @throws PersistenceException if persistence errors occur while executing the operation
         */
        public void store(NetworkDevice device) throws PersistenceException;

        /**
         * Loads a network device.
         * 
         * @param id device's id
         * @return the device if found, {@code null} otherwise
         * @throws PersistenceException if persistence errors occur while executing the operation
         */
        public NetworkDevice get(Id<NetworkDevice, SerialNumber> id) throws PersistenceException; //Id<NetworkDevice, SerialNumber> id

        /**
         * Finds network devices.
         * 
         * @param filter filter
         * @param sort sort specification
         * @return found devices
         * @throws PersistenceException if persistence errors occur while executing the operation
         */
        //revisted later to implement
        public List<NetworkDevice> find(NetworkDeviceFilter filter,
        		List<Sort<NetworkDeviceSortKey>> sort) throws PersistenceException;

        /**
         * Deletes a network device.
         * 
         * @param id device's id
         * @throws PersistenceException if persistence errors occur while executing the operation
         */
        public void delete(Id<NetworkDevice, SerialNumber> id) throws PersistenceException;
    }

    /**
     * User persistence service.
     */
    public interface UserPersistenceService extends ObjectStore<Username, User, UserFilter, Void> {
        
        /**
         * Stores a user.
         * 
         * @param user user to store
         * @throws PersistenceException if persistence errors occur while executing the operation
         */
        public void store(User user) throws PersistenceException;

        /**
         * Loads a user.
         * 
         * @param id user's id
         * @return the user if found, {@code null} otherwise
         * @throws PersistenceException if persistence errors occur while executing the operation
         */
        public User get(Id<User, Username> id) throws PersistenceException;

        /**
         * Find users.
         * 
         * @param filter filter
         * @return found users
         * @throws PersistenceException if persistence errors occur while executing the operation
         */
        public List<User> find(UserFilter filter) throws PersistenceException;

        /**
         * Deletes a user.
         * 
         * @param id user's id
         * @throws PersistenceException if persistence errors occur while executing the operation
         */
        public void delete(Id<User, Username> id) throws PersistenceException;
    }

    /**
     * TSDR persistence service.
     */
    public interface TsdrMetricPersistenceService extends
            ObjectStore<Long, TsdrMetric, TsdrMetricFilter, TsdrMetricSortKey> {

        // Define custom persistence functionality here
    }
}
