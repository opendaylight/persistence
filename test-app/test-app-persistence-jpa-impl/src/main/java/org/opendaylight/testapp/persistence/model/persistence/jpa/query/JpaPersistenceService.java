/*
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

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
