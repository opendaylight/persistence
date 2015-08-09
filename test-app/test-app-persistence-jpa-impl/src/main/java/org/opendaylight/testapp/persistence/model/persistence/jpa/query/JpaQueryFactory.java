/*
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.testapp.persistence.model.persistence.jpa.query;

import java.util.List;

import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.common.query.DeleteByIdQuery;
import org.opendaylight.persistence.common.query.FindQuery;
import org.opendaylight.persistence.common.query.GetQuery;
import org.opendaylight.persistence.common.query.StoreQuery;
import org.opendaylight.persistence.common.query.VoidQuery;
import org.opendaylight.persistence.jpa.JpaContext;
import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.persistence.util.common.type.Sort;
import org.opendaylight.testapp.common.model.NetworkDevice;
import org.opendaylight.testapp.common.model.NetworkDeviceFilter;
import org.opendaylight.testapp.common.model.NetworkDeviceSortKey;
import org.opendaylight.testapp.common.model.User;
import org.opendaylight.testapp.common.model.UserFilter;
import org.opendaylight.testapp.common.type.SerialNumber;
import org.opendaylight.testapp.common.type.Username;
import org.opendaylight.testapp.persistence.QueryFactory;
import org.opendaylight.testapp.persistence.model.persistence.jpa.dao.NetworkDeviceDao;
import org.opendaylight.testapp.persistence.model.persistence.jpa.dao.UserDao;


public class JpaQueryFactory implements QueryFactory<JpaContext>{

    /*
     * Custom queries should be implemented in this package and they should be package private. In
     * fact, everything in this module but this class could be package private: Entities, DAOs and
     * custom queries. This query factory is the only API the Business logic should interact with.
     * However, that would require everything to be contained in a single package. Thus, even though
     * entities and DAOs are not supposed to be used by anyone but this class, it was decided to
     * locate them in different packages for code organization purposes.
     */

    private final ConfigurationFactory<JpaContext> configurationFactory;
    private final NetworkDeviceFactory<JpaContext> networkDeviceFactory;
    private final UserFactory<JpaContext> userFactory;

    /**
     * Creates a query factory.
     */
    public JpaQueryFactory() {
        this.configurationFactory = new ConfigurationFactoryImpl();
        this.networkDeviceFactory = new NetworkDeviceFactoryImpl();
        this.userFactory = new UserFactoryImpl();
    }

    @Override
    public ConfigurationFactory<JpaContext> configuration() {
        return this.configurationFactory;
    }

    @Override
    public NetworkDeviceFactory<JpaContext> networkDevice() {
        return this.networkDeviceFactory;
    }

    @Override
    public UserFactory<JpaContext> user() {
        return this.userFactory;
    }

    private static class ConfigurationFactoryImpl implements ConfigurationFactory<JpaContext> {

        @Override
        public Query<Void, JpaContext> createSchema() {
            return VoidQuery.getInstance();
        }
    }

    private static class NetworkDeviceFactoryImpl<S> implements NetworkDeviceFactory<JpaContext> {
        private final NetworkDeviceDao dao;

        public NetworkDeviceFactoryImpl() {
            this.dao = new NetworkDeviceDao();
        }

        @Override
        public Query<Void, JpaContext> store(NetworkDevice device) {
            return StoreQuery.createQuery(device.getIdentifier(), device, this.dao);
        }

        public Query<NetworkDevice, JpaContext> get(Id<NetworkDevice, SerialNumber> id) {
            return GetQuery.createQuery(id.getValue(), this.dao);
        }

        public Query<List<NetworkDevice>, JpaContext> find(NetworkDeviceFilter filter,
        		List<Sort<NetworkDeviceSortKey>> sortSpecification) {
            return FindQuery.createQuery(filter, sortSpecification, this.dao);
        }

        public Query<Void, JpaContext> delete(Id<NetworkDevice, SerialNumber> id) {
            return DeleteByIdQuery.createQuery(id.getValue(), this.dao);
        }
    }

    private static class UserFactoryImpl implements UserFactory<JpaContext> {
        private final UserDao dao;

        public UserFactoryImpl() {
            this.dao = new UserDao();
        }

        @Override
        public Query<Void, JpaContext> store(User user) {
            return StoreQuery.createQuery(user.getIdentifier(), user, this.dao);
        }

        @Override
        public Query<User, JpaContext> get(Id<User, Username> id) {
            return GetQuery.createQuery(id.getValue(), this.dao);
        }

        @Override
        public Query<List<User>, JpaContext> find(UserFilter filter) {
            return FindQuery.createQuery(filter, null, this.dao);
        }

        @Override
        public Query<Void, JpaContext> delete(Id<User, Username> id) {
            return DeleteByIdQuery.createQuery(id.getValue(), this.dao);
        }
    }
}
