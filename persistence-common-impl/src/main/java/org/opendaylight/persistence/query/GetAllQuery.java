/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.query;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.KeyValueDao;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * Query to load all objects from the data store.
 * 
 * @param <T> type of the identifiable object (object to store in the data store)
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class GetAllQuery<T extends Identifiable<?>, C> implements Query<Collection<T>, C> {

    private KeyValueDao<?, T, C> dao;

    private GetAllQuery(@Nonnull KeyValueDao<?, T, C> dao) {
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
    public static <T extends Identifiable<?>, C> Query<Collection<T>, C> createQuery(@Nonnull KeyValueDao<?, T, C> dao) {
        return new GetAllQuery<T, C>(dao);
    }

    @Override
    public Collection<T> execute(C context) throws PersistenceException {
        return this.dao.getAll(context);
    }
}
