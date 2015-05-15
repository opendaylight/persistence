package org.opendaylight.testapp.persistence.model.persistence.jpa.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.opendaylight.persistence.jpa.dao.JpaMappedKeyDao;
import org.opendaylight.persistence.jpa.dao.JpaQueryPredicateGenerator;
import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.testapp.common.model.NetworkDevice;
import org.opendaylight.testapp.common.model.NetworkDeviceFilter;
import org.opendaylight.testapp.common.model.NetworkDeviceFilter.All;
import org.opendaylight.testapp.common.model.NetworkDeviceFilter.ByLocation;
import org.opendaylight.testapp.common.model.NetworkDeviceFilter.ByLocationAndReachabilityStatus;
import org.opendaylight.testapp.common.model.NetworkDeviceFilter.ByReachabilityStatus;
import org.opendaylight.testapp.common.model.NetworkDeviceSortKey;
import org.opendaylight.testapp.common.type.SerialNumber;
import org.opendaylight.testapp.persistence.model.persistence.jpa.entity.NetworkDeviceEntity;
import org.opendaylight.testapp.persistence.model.persistence.jpa.entity.NetworkDeviceEntity_;



/**
 * Network device DAO.
 * 
 * @author Fabiel Zuniga
 */
public class NetworkDeviceDao
        extends
        JpaMappedKeyDao<SerialNumber, NetworkDevice, String, NetworkDeviceEntity, NetworkDeviceFilter, NetworkDeviceSortKey> {

    /**
     * Creates a DAO.
     */
    public NetworkDeviceDao() {
        super(NetworkDeviceEntity.class);
    }

    @Override
    protected SerialNumber getId(NetworkDeviceEntity entity) {
        return entity.getId();
    }

    @Override
    protected String mapKey(SerialNumber key) {
        return key.getValue();
    }

    @Override
    protected NetworkDeviceEntity create(NetworkDevice device) {
        NetworkDeviceEntity entity = new NetworkDeviceEntity(device.getIdentifier(), device.getMacAddress(),
                device.getReachabilityStatus());
        entity.setIpAddress(device.getIpAddress());
        entity.setFriendlyName(device.getFriendlyName());
        entity.setLocation(device.getLocation());
        return entity;
    }

    @Override
    protected NetworkDevice doConvert(NetworkDeviceEntity source) {
        Id<NetworkDevice, SerialNumber> id = Id.valueOf(source.getId());
        NetworkDevice device = new NetworkDevice(id, id.getValue(), source.getMacAddress(), source.getReachabilityStatus());
        device.setIpAddress(source.getIpAddress());
        device.setFriendlyName(source.getFriendlyName());
        device.setLocation(source.getLocation());
        return device;
    }

    @Override
    protected void conform(NetworkDeviceEntity target, NetworkDevice source) {
        target.setIpAddress(source.getIpAddress());
        target.setFriendlyName(source.getFriendlyName());
        target.setLocation(source.getLocation());
        target.setReachabilityStatus(source.getReachabilityStatus());
    }

    @Override
    protected Predicate getQueryPredicate(NetworkDeviceFilter networkDeviceFilter, final CriteriaBuilder builder,
            final Root<NetworkDeviceEntity> root) {
        NetworkDeviceFilter.Visitor<Predicate> visitor = new NetworkDeviceFilter.Visitor<Predicate>() {

            @Override
            public Predicate visit(All filter) {
                return null;
            }

            @Override
            public Predicate visit(ByLocation filter) {
                return getQueryPredicateGenerator().getPredicate(filter.getLocationCondition(),
                        NetworkDeviceEntity_.location, builder, root);
            }

            @Override
            public Predicate visit(ByReachabilityStatus filter) {
                return getQueryPredicateGenerator().getPredicate(filter.getReachabilityStatusCondition(),
                        NetworkDeviceEntity_.reachabilityStatus, builder, root);
            }

            @Override
            public Predicate visit(ByLocationAndReachabilityStatus filter) {
                JpaQueryPredicateGenerator<NetworkDeviceEntity> predicateGenerator = getQueryPredicateGenerator();
                Predicate locationPredicate = predicateGenerator.getPredicate(filter.getLocationCondition(),
                        NetworkDeviceEntity_.location, builder, root);
                Predicate reachabilityStatusPredicate = predicateGenerator
                        .getPredicate(filter.getReachabilityStatusCondition(), NetworkDeviceEntity_.reachabilityStatus,
                                builder, root);
                               
                return predicateGenerator.and(builder, locationPredicate, reachabilityStatusPredicate);
                
            }
        };

        return nonnull(networkDeviceFilter).accept(visitor);
    }

	@Override
	protected javax.persistence.metamodel.SingularAttribute<? super NetworkDeviceEntity, ?> getSingularAttribute(
			NetworkDeviceSortKey sortKey) {
	       switch (sortKey) {
           case FRIENDLY_NAME:
               return NetworkDeviceEntity_.friendlyName;
       }
       return null;
	}


    private static NetworkDeviceFilter nonnull(NetworkDeviceFilter filter) {
        if (filter != null) {
            return filter;
        }
        return NetworkDeviceFilter.all();
    }

}
