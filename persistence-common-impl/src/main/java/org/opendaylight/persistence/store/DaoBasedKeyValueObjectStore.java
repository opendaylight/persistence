/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.store;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.KeyValueDao;
import org.opendaylight.persistence.query.ClearQuery;
import org.opendaylight.persistence.query.GetAllQuery;
import org.opendaylight.persistence.query.SizeQuery;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * {@link KeyValueObjectStore} implementation. Separating {@link KeyValueObjectStore} from this
 * implementation allows applications to define custom persistence functionality by extending
 * {@link KeyValueObjectStore} and adding custom methods. Then the final implementation of the
 * persistence service would extend this class and implement the custom methods.
 * 
 * @param <I> type of the identifiable object's id. This type should be immutable and it is critical
 *            it implements {@link Object#equals(Object)} and {@link Object#hashCode()} correctly.
 * @param <T> type of the identifiable object (object to store in the data store)
 * @param <C> type of the context provided to queries to enable execution
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class DaoBasedKeyValueObjectStore<I extends Serializable, T extends Identifiable<I>, C> extends
        DaoBasedBaseObjectStore<I, T, C> implements KeyValueObjectStore<I, T> {

    private final Query<Collection<T>, C> getAllQuery;
    private final Query<Long, C> sizeQuery;
    private final Query<Void, C> clearQuery;

    /**
     * Creates a persistence service.
     * 
     * @param dataStore data store service
     * @param dao the DAO to delegate persistence operations to
     */
    public DaoBasedKeyValueObjectStore(@Nonnull DataStore<C> dataStore, @Nonnull KeyValueDao<I, T, C> dao) {
        super(dataStore, dao);
        this.getAllQuery = GetAllQuery.createQuery(dao);
        this.sizeQuery = SizeQuery.createQuery(dao);
        this.clearQuery = ClearQuery.createQuery(dao);
    }

    @Override
    public Collection<T> getAll() throws PersistenceException {
        return getDataStore().execute(this.getAllQuery);
    }

    @Override
    public long size() throws PersistenceException {
        return getDataStore().execute(this.sizeQuery).longValue();
    }

    @Override
    public void clear() throws PersistenceException {
        getDataStore().execute(this.clearQuery);
    }
}
