package org.opendaylight.testapp.persistence.model.persistence.jpa.query;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.jpa.JpaContext;
import org.opendaylight.persistence.jpa.JpaDataStore;
import org.opendaylight.testapp.persistence.impl.PersistenceServiceImpl;


public class JpaPersistenceService extends PersistenceServiceImpl<JpaContext> {

    /**
     * Creates a JPA Persistence Service
     * 
     * @param loggerProvider logger provider
     */
    public JpaPersistenceService(JpaDataStore jpaDataStore) {
        super(jpaDataStore, new JpaQueryFactory());
    }

	@Override
	public TsdrMetricPersistenceService tsdrMetric() {
		// TODO Auto-generated method stub
		return null;
	}
}
