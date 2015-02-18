/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.util.common.type.SortSpecification;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * Data Access Object.
 * <p>
 * A DAO should be used by {@link org.opendaylight.persistence.Query queries}.
 *
 * @param <I> type of the identifiable object's id. This type should be immutable and it is critical
 *            it implements {@link Object#equals(Object)} and {@link Object#hashCode()} correctly.
 * @param <T> type of the identifiable object (object to store in the data store)
 * @param <F> type of the associated filter. A DAO is responsible for translating this filter to any
 *            mechanism understood by the underlying data store or database technology. For example,
 *            predicates in JPA-based implementations, or WHERE clauses in SQL-base implementations.
 * @param <S> type of the associated sort attribute or sort key used to construct sort
 *            specifications. A DAO is responsible for translating this specification to any
 *            mechanism understood by the underlying data store or database technology. For example,
 *            ORDER BY clauses in SQL-based implementations.
 * @param <C> type of the query's execution context; the context managed by the
 *            {@link org.opendaylight.persistence.DataStore}
 * @author Robert Gagnon
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public interface Dao<I extends Serializable, T extends Identifiable<I>, F, S, C> extends
        KeyValueDao<I, T, C> {

    /*
     * Filter is @Nonnull to avoid considering null as an empty filter to request all data, since
     * some implementations may not support retrieving all objects. In case an implementation does
     * support it, the filter should allow creating an empty one (non-null).
     */

    /**
     * Gets the objects from the data store that match the given filter.
     *
     * @param filter filter to apply, {@code null} to retrieve all objects
     * @param sortSpecification sort specification
     * @param context data store context
     * @return the objects that match {@code filter} sorted as stated by {@code sortSpecification}
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    List<T> find(@Nonnull F filter, @Nullable SortSpecification<S> sortSpecification, C context)
            throws PersistenceException;

    /**
     * Gets the number of objects from the data store that match the given filter.
     *
     * @param filter filter to apply, {@code null} to count all objects
     * @param context data store context
     * @return the number of objects that match {@code filter}
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    long count(@Nonnull F filter, @Nonnull C context) throws PersistenceException;

    /**
     * Deletes all objects from the data store that match the given filter.
     *
     * @param filter filter to apply, {@code null} to delete all objects
     * @param context data store context
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    void delete(@Nonnull F filter, @Nonnull C context) throws PersistenceException;
}