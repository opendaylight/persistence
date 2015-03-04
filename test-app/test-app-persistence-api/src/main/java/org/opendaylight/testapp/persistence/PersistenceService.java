/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.testapp.persistence;

import org.opendaylight.persistence.store.ObjectStore;
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
    public NetworkDevicePersistenceService networkDevice();

    /**
     * Gets the user persistence service.
     * 
     * @return the user persistence service
     */
    public UserPersistenceService user();

    /**
     * Gets the TSDR metric persistence service.
     * 
     * @return the TSDR metric persistence service
     */
    public TsdrMetricPersistenceService tsdrMetric();

    /**
     * Network device persistence service.
     */
    public static interface NetworkDevicePersistenceService extends
            ObjectStore<SerialNumber, NetworkDevice, NetworkDeviceFilter, NetworkDeviceSortKey> {

        // Define custom persistence functionality here
    }

    /**
     * User persistence service.
     */
    public static interface UserPersistenceService extends ObjectStore<Username, User, UserFilter, Void> {
        
        // Define custom persistence functionality here
    }

    /**
     * TSDR persistence service.
     */
    public static interface TsdrMetricPersistenceService extends
            ObjectStore<Long, TsdrMetric, TsdrMetricFilter, TsdrMetricSortKey> {

        // Define custom persistence functionality here
    }
}
