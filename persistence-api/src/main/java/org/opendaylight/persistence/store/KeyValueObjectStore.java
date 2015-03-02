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

import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.KeyValueDao;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * A facade that executes methods of {@link KeyValueDao} in the context of a {@link Query}.
 * 
 * @param <I> type of the identifiable object's id. This type should be immutable and it is critical
 *            it implements {@link Object#equals(Object)} and {@link Object#hashCode()} correctly.
 * @param <T> type of the identifiable object (object to store in the data store)
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public interface KeyValueObjectStore<I extends Serializable, T extends Identifiable<I>> extends BaseObjectStore<I, T> {

    /**
     * Loads all objects from the data store.
     * 
     * @return all the objects from the data store
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    Collection<T> getAll() throws PersistenceException;

    /**
     * Returns the number of objects in the data store.
     * 
     * @return the objects count
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    long size() throws PersistenceException;

    /**
     * Deletes all the objects from the data store.
     * 
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    void clear() throws PersistenceException;
}
