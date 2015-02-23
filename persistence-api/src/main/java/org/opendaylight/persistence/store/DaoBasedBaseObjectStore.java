/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.store;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.dao.BaseDao;
import org.opendaylight.persistence.dao.query.AddQuery;
import org.opendaylight.persistence.dao.query.DeleteByIdQuery;
import org.opendaylight.persistence.dao.query.ExistQuery;
import org.opendaylight.persistence.dao.query.GetQuery;
import org.opendaylight.persistence.dao.query.UpdateQuery;
import org.opendaylight.yangtools.concepts.Identifiable;

import com.google.common.base.Preconditions;

/**
 * {@link BaseObjectStore} implementation. Separating {@link BaseObjectStore} from this
 * implementation allows applications to define custom persistence functionality by extending
 * {@link BaseObjectStore} and adding custom methods. Then the final implementation of the
 * persistence service would extend this class and implement the custom methods.
 * 
 * @param <I> type of the identifiable object's id. This type should be immutable and it is critical
 *            it implements {@link Object#equals(Object)} and {@link Object#hashCode()} correctly.
 * @param <T> type of the identifiable object (object to store in the data store)
 * @param <C> type of the context provided to queries to enable execution
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class DaoBasedBaseObjectStore<I extends Serializable, T extends Identifiable<I>, C> implements
        BaseObjectStore<I, T> {

    private final DataStore<C> dataStore;
    private final BaseDao<I, T, C> dao;

    /**
     * Creates a persistence service.
     * 
     * @param dataStore data store service
     * @param dao the DAO to delegate persistence operations to
     */
    public DaoBasedBaseObjectStore(@Nonnull DataStore<C> dataStore, @Nonnull BaseDao<I, T, C> dao) {
        this.dataStore = Preconditions.checkNotNull(dataStore, "dataStore");
        this.dao = Preconditions.checkNotNull(dao, "dao");
    }

    @Override
    public T add(T identifiable) throws PersistenceException {
        return this.dataStore.execute(AddQuery.createQuery(identifiable, this.dao));
    }

    @Override
    public T update(T identifiable) throws PersistenceException {
        return this.dataStore.execute(UpdateQuery.createQuery(identifiable, this.dao));
    }

    @Override
    public void delete(I id) throws PersistenceException {
        this.dataStore.execute(DeleteByIdQuery.createQuery(id, this.dao));
    }

    @Override
    public T get(I id) throws PersistenceException {
        return this.dataStore.execute(GetQuery.createQuery(id, this.dao));
    }

    @Override
    public boolean exist(I id) throws PersistenceException {
        return this.dataStore.execute(ExistQuery.createQuery(id, this.dao)).booleanValue();
    }

    /**
     * Returns the data store.
     * 
     * @return the data store
     */
    protected final DataStore<C> getDataStore() {
        return this.dataStore;
    }
}
