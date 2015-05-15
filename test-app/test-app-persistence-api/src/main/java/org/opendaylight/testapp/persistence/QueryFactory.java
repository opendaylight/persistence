package org.opendaylight.testapp.persistence;

import java.util.List;

import org.opendaylight.persistence.Query;
//import org.opendaylight.persistence.jpa.JpaContext;
import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.persistence.util.common.type.Sort;
import org.opendaylight.testapp.common.model.NetworkDevice;
import org.opendaylight.testapp.common.model.NetworkDeviceFilter;
import org.opendaylight.testapp.common.model.NetworkDeviceSortKey;
import org.opendaylight.testapp.common.model.User;
import org.opendaylight.testapp.common.model.UserFilter;
import org.opendaylight.testapp.common.type.SerialNumber;
import org.opendaylight.testapp.common.type.Username;


public interface QueryFactory<C> {

    /**
     * Gets the configuration query factory.
     * 
     * @return the network device query factory
     */
    public ConfigurationFactory<C> configuration();

    /**
     * Gets the network device query factory.
     * 
     * @return the network device query factory
     */
    public NetworkDeviceFactory<C> networkDevice();

    /**
     * Gets the user query factory.
     * 
     * @return the user query factory
     */
    public UserFactory<C> user();

    /**
     * Configuration factory.
     * 
     * @param <C> type of the context provided to queries to enable execution
     */
    public static interface ConfigurationFactory<C> {

        /**
         * Creates a query to create the database schema.
         * 
         * @return a query
         */
        public Query<Void, C> createSchema();
    }

    /**
     * Network device factory.
     * 
     * @param <C> type of the context provided to queries to enable execution
     */
    public static interface NetworkDeviceFactory<C> {

        /**
         * Creates a query to store a network device.
         * 
         * @param device device to store
         * @return a query
         */
        public Query<Void, C> store(NetworkDevice device);

        /**
         * Creates a query to load a network device.
         * 
         * @param id device's id
         * @return a query
         */
        public Query<NetworkDevice, C> get(Id<NetworkDevice, SerialNumber> id); //Id<NetworkDevice, SerialNumber> id

        /**
         * Creates a query to find network devices.
         * 
         * @param filter filter
         * @param sortSpecification sort specification
         * @return a query
         */
       // public Query<List<NetworkDevice>, C> find(NetworkDeviceFilter filter,
       //         SortSpecification<NetworkDeviceSortKey> sortSpecification);
        public Query<List<NetworkDevice>, C> find(NetworkDeviceFilter filter,List<Sort<NetworkDeviceSortKey>> sortSpecification); // revisit

        /**
         * Creates a query to delete a network device.
         * 
         * @param id device's id
         * @return a query
         */
        public Query<Void, C> delete(Id<NetworkDevice, SerialNumber> id);
    }

    /**
     * User factory.
     * 
     * @param <C> type of the context provided to queries to enable execution
     */
    public static interface UserFactory<C> {

        /**
         * Creates a query to store a user.
         * 
         * @param user user to store
         * @return a query
         */
        public Query<Void, C> store(User user);

        /**
         * Creates a query to load a user.
         * 
         * @param id user's id
         * @return a query
         */
        public Query<User, C> get(Id<User, Username> id);

        /**
         * Creates a query to find users.
         * 
         * @param filter filter
         * @return a query
         */
        public Query<List<User>, C> find(UserFilter filter);

        /**
         * Creates a query to delete a user.
         * 
         * @param id user's id
         * @return a query
         */
        public Query<Void, C> delete(Id<User, Username> id);
    }
}
