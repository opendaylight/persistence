/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.query;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.KeyValueDao;

/**
 * Query to delete all objects from the data store.
 * 
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class ClearQuery<C> implements Query<Void, C> {

    private KeyValueDao<?, ?, C> dao;

    private ClearQuery(@Nonnull KeyValueDao<?, ?, C> dao) {
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
    public static <C> Query<Void, C> createQuery(@Nonnull KeyValueDao<?, ?, C> dao) {
        return new ClearQuery<C>(dao);
    }

    @Override
    public Void execute(C context) throws PersistenceException {
        this.dao.clear(context);
        return null;
    }
}
