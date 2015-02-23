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
import org.opendaylight.persistence.IntegrityConstraintViolationException;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.BaseDao;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * A facade that executes methods of {@link BaseDao} in the context of a {@link Query}.
 * <p>
 * A DAO should be used by {@link Query queries}; it assists the {@link Query} to do its job.
 * However, a {@link Query} is not restricted to a DAO. A {@link Query} could use one, more than
 * one, or no DAO at all - If the DAO does not provide functionality required by the {@link Query}
 * or when native database functionality is required. However, most of the times a DAO is sufficient
 * and a single method call on the DAO will suffice. For those cases this factory could be used to
 * provide queries to the persistence logic's consumer hiding queries' implementation details.
 * <p>
 * This is a convenient facade to make the persistence logic's consumer unaware of the the context
 * provided to {@link Query queries} by the {@link DataStore}. This allows replacing the persistence
 * logic without affecting its consumer (Business logic for example).
 * 
 * @param <I> type of the identifiable object's id. This type should be immutable and it is critical
 *            it implements {@link Object#equals(Object)} and {@link Object#hashCode()} correctly.
 * @param <T> type of the identifiable object (object to store in the data store)
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public interface BaseObjectStore<I extends Serializable, T extends Identifiable<I>> {

    /**
     * Adds the given object in the data store.
     * <p>
     * Depending on implementations if the object already exists an {@link IllegalArgumentException}
     * could be thrown.
     * 
     * @param identifiable object to store
     * @return the object added which may include auto-generated primary keys
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    T add(@Nonnull T identifiable) throws PersistenceException;

    /**
     * Updates the given object in the data store. If the object
     * <p>
     * Depending on implementations if the object does not exist a {@link IllegalArgumentException}
     * could be thrown.
     * 
     * @param identifiable object to store
     * @return the object updated which may include auto-generated values
     * @throws IntegrityConstraintViolationException if an integrity constraint defined in the
     *             underlying database or data store provider is violated
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    T update(@Nonnull T identifiable) throws PersistenceException;

    /**
     * Deletes an object from the data store.
     * 
     * @param id object's id
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    void delete(@Nonnull I id) throws PersistenceException;

    /**
     * Loads the object with the given id from the data store.
     * 
     * @param id object's id
     * @return the object if found, {@code null} otherwise
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    public T get(@Nonnull I id) throws PersistenceException;

    /**
     * Verifies if an object with the given id exists in the data store.
     * 
     * @param id object's id
     * @return {@code true} if an object with the given id already exists, {@code false} otherwise
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    boolean exist(@Nonnull I id) throws PersistenceException;
}
