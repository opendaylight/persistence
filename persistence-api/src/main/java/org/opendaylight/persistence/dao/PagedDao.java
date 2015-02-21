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
import org.opendaylight.persistence.util.common.type.Sort;
import org.opendaylight.persistence.util.common.type.page.Page;
import org.opendaylight.persistence.util.common.type.page.PageRequest;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * Data Access Object that supports paging.
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
 * @param <R> type of the page request
 * @param <D> type of the page
 * @param <C> type of the query's execution context; the context managed by the
 *            {@link org.opendaylight.persistence.DataStore}
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public interface PagedDao<I extends Serializable, T extends Identifiable<I>, F, S, R extends PageRequest,
                          D extends Page<R, T>, C> extends Dao<I, T, F, S, C> {

    /**
     * Gets a page of objects from the data store that match the given filter.
     *
     * @param filter filter to apply, {@code null} to consider all objects
     * @param sort sort specification
     * @param pageRequest page request
     * @param context data store context
     * @return a page of objects that match {@code filter} sorted as stated by
     *         {@code sortSpecification}
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    D find(@Nonnull F filter, @Nullable List<Sort<S>> sort, @Nonnull R pageRequest, @Nonnull C context)
            throws PersistenceException;
}

