/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.query;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.BaseDao;

/**
 * Query to verify if an object with the given id exists in the data store.
 * 
 * @param <I> type of the identifiable object's id
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class ExistQuery<I extends Serializable, C> implements Query<Boolean, C> {

    private I id;
    private BaseDao<I, ?, C> dao;

    private ExistQuery(@Nonnull I id, @Nonnull BaseDao<I, ?, C> dao) {
        this.id = id;
        this.dao = dao;
    }

    /**
     * Creates a query.
     * <p>
     * This method is a convenience to infer the generic types.
     * 
     * @param id the object's id
     * @param dao DAO to assist the query
     * @return the query
     */
    public static <I extends Serializable, C> Query<Boolean, C> createQuery(@Nonnull I id, @Nonnull BaseDao<I, ?, C> dao) {
        return new ExistQuery<I, C>(id, dao);
    }

    @Override
    public Boolean execute(C context) throws PersistenceException {
        return Boolean.valueOf(this.dao.exist(this.id, context));
    }
}
