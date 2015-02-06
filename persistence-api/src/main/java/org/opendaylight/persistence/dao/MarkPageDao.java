/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.dao;

import org.opendaylight.persistence.util.common.Identifiable;
import org.opendaylight.persistence.util.common.type.page.MarkPage;
import org.opendaylight.persistence.util.common.type.page.MarkPageRequest;

import java.io.Serializable;

/**
 * Data Access Object that supports paging via {@link MarkPage}.
 * <p>
 * A DAO should be used by {@link Query queries}.
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
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public interface MarkPageDao<I extends Serializable, T extends Identifiable<? super T, I>, F, S, C> extends
        PagedDao<I, T, F, S, MarkPageRequest<T>, MarkPage<T>, C> {
}
