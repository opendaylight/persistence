/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.common.query;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.Dao;

/**
 * Query to get the number of objects from the data store that match the given filter.
 * 
 * @param <F> type of the associated filter
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class CountQuery<F, C> implements Query<Long, C> {

    private F filter;
    private Dao<?, ?, F, ?, C> dao;

    private CountQuery(@Nonnull F filter, @Nonnull Dao<?, ?, F, ?, C> dao) {
        this.filter = filter;
        this.dao = dao;
    }

    /**
     * Creates a query.
     * <p>
     * This method is a convenience to infer the generic types.
     * 
     * @param filter filter
     * @param dao DAO to assist the query
     * @return the query
     */
    public static <F, C> Query<Long, C> createQuery(@Nonnull F filter, @Nonnull Dao<?, ?, F, ?, C> dao) {
        return new CountQuery<F, C>(filter, dao);
    }

    @Override
    public Long execute(C context) throws PersistenceException {
        return Long.valueOf(this.dao.count(this.filter, context));
    }
}
