/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.dao.query;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.KeyValueDao;

/**
 * Query that returns the number of objects from the data store.
 * 
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class SizeQuery<C> implements Query<Long, C> {

    private KeyValueDao<?, ?, C> dao;

    private SizeQuery(@Nonnull KeyValueDao<?, ?, C> dao) {
        this.dao = dao;
    }

    /**
     * Creates a query.
     * <p>
     * This method is a convenience to infer the generic types.
     * 
     * @param dao DAO to assist the query
     * @return the query
     */
    public static <C> Query<Long, C> createQuery(@Nonnull KeyValueDao<?, ?, C> dao) {
        return new SizeQuery<C>(dao);
    }

    @Override
    public Long execute(C context) throws PersistenceException {
        return Long.valueOf(this.dao.size(context));
    }
}
