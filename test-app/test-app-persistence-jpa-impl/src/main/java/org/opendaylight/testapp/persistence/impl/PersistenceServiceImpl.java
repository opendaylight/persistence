/*
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.testapp.persistence.impl;

import java.util.Collection;
import java.util.List;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.persistence.util.common.type.Sort;
import org.opendaylight.testapp.common.model.NetworkDevice;
import org.opendaylight.testapp.common.model.NetworkDeviceFilter;
import org.opendaylight.testapp.common.model.NetworkDeviceSortKey;
import org.opendaylight.testapp.common.model.User;
import org.opendaylight.testapp.common.model.UserFilter;
import org.opendaylight.testapp.common.type.SerialNumber;
import org.opendaylight.testapp.common.type.Username;
import org.opendaylight.testapp.persistence.PersistenceService;
import org.opendaylight.testapp.persistence.QueryFactory;

public abstract class PersistenceServiceImpl<C> implements PersistenceService {

    private final DataStore<C> dataStore;
    private final QueryFactory<C> queryFactory;
    //private final NetworkDeviceFactory networkDeviceFactory;
    private final NetworkDevicePersistenceService networkDevicePersistenceService;
    private final UserPersistenceService userPersistenceService;

    public PersistenceServiceImpl(DataStore<C> dataStore, QueryFactory<C> queryFactory) {
        if (dataStore == null) {
            throw new NullPointerException("dataStore cannot be null");
        }

        if (queryFactory == null) {
            throw new NullPointerException("queryFactory cannot be null");
        }

        this.dataStore = dataStore;
        this.queryFactory = queryFactory;
        //this.networkDeviceFactory = new NetworkDeviceFactoryImpl();
        this.networkDevicePersistenceService = new NetworkDevicePersistenceServiceImpl();
        this.userPersistenceService = new UserPersistenceServiceImpl();
    }

    @Override
    public NetworkDevicePersistenceService networkDevice() {
        return this.networkDevicePersistenceService;
    }

    @Override
    public UserPersistenceService user() {
        return this.userPersistenceService;
    }

    protected DataStore<C> getDataStore() {
        return this.dataStore;
    }

    protected QueryFactory<C> getQueryFactory() {
    	//Network new NetworkDeviceFactoryImpl();
    	return this.queryFactory;
   }

    private class NetworkDevicePersistenceServiceImpl implements NetworkDevicePersistenceService {

        @Override
        public void store(NetworkDevice device) throws PersistenceException {
        	System.out.println("NetworkDevicePersistenceServiceImpl: IP >> "+ device.getIpAddress());

            getDataStore().execute(getQueryFactory().networkDevice().store(device));
        }

        @Override
        public NetworkDevice get(Id<NetworkDevice, SerialNumber> id) throws PersistenceException { ////Id<NetworkDevice, SerialNumber> id
            return getDataStore().execute(getQueryFactory().networkDevice().get(id));
        }

       /* @Override
        public List<NetworkDevice> find(NetworkDeviceFilter filter,
                SortSpecification<NetworkDeviceSortKey> sortSpecification) throws PersistenceException {
            return getDataStore().execute(getQueryFactory().networkDevice().find(filter, sortSpecification));
        }*/

        @Override
        public void delete(Id<NetworkDevice, SerialNumber> id) throws PersistenceException {
            getDataStore().execute(getQueryFactory().networkDevice().delete(id));
        }

		/*@Override
		public List<NetworkDevice> find(NetworkDeviceFilter filter,
				  SortSpecification<NetworkDeviceSortKey> sortSpecification)
				throws PersistenceException {
			return getDataStore().execute(getQueryFactory().networkDevice().find(filter, sortSpecification.getSortComponents()));
		}*/

		@Override
		public long count(NetworkDeviceFilter filter)
				throws PersistenceException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void delete(NetworkDeviceFilter filter)
				throws PersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public Collection<NetworkDevice> getAll() throws PersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long size() throws PersistenceException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void clear() throws PersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public NetworkDevice add(NetworkDevice identifiable)
				throws PersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NetworkDevice update(NetworkDevice identifiable)
				throws PersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void delete(SerialNumber id) throws PersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public NetworkDevice get(SerialNumber id) throws PersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean exist(SerialNumber id) throws PersistenceException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<NetworkDevice> find(NetworkDeviceFilter filter,
				List<Sort<NetworkDeviceSortKey>> sort)
				throws PersistenceException {
			return getDataStore().execute(getQueryFactory().networkDevice().find(filter, sort));
		}
    }

    private class UserPersistenceServiceImpl implements UserPersistenceService {

        @Override
        public void store(User user) throws PersistenceException {
            getDataStore().execute(getQueryFactory().user().store(user));
        }

        @Override
        public User get(Id<User, Username> id) throws PersistenceException {
            return getDataStore().execute(getQueryFactory().user().get(id));
        }

        @Override
        public List<User> find(UserFilter filter) throws PersistenceException {
            return getDataStore().execute(getQueryFactory().user().find(filter));
        }

        @Override
        public void delete(Id<User, Username> id) throws PersistenceException {
            getDataStore().execute(getQueryFactory().user().delete(id));
        }

		@Override
		public List<User> find(UserFilter filter, List<Sort<Void>> sort)
				throws PersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long count(UserFilter filter) throws PersistenceException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void delete(UserFilter filter) throws PersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public Collection<User> getAll() throws PersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long size() throws PersistenceException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void clear() throws PersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public User add(User identifiable) throws PersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public User update(User identifiable) throws PersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void delete(Username id) throws PersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public User get(Username id) throws PersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean exist(Username id) throws PersistenceException {
			// TODO Auto-generated method stub
			return false;
		}
    }
}

