/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.dao.query;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.Dao;
import org.opendaylight.persistence.util.common.type.SortSpecification;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * Query to get the objects from the data store that match the given filter.
 * 
 * @param <T> type of the identifiable object (object to store in the data store)
 * @param <F> type of the associated filter
 * @param <S> type of the associated sort attribute or sort key used to construct sort
 *            specifications
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class FindQuery<T extends Identifiable<?>, F, S, C> implements Query<List<T>, C> {

    private F filter;
    private SortSpecification<S> sortSpecification;
    private Dao<?, T, F, S, C> dao;

    private FindQuery(@Nonnull F filter, @Nullable SortSpecification<S> sortSpecification,
            @Nonnull Dao<?, T, F, S, C> dao) {
        this.filter = filter;
        this.sortSpecification = sortSpecification;
        this.dao = dao;
    }

    /**
     * Creates a query.
     * <p>
     * This method is a convenience to infer the generic types.
     * 
     * @param filter filter
     * @param sortSpecification sort specification
     * @param dao DAO to assist the query
     * @return the query
     */
    public static <T extends Identifiable<?>, F, S, C> Query<List<T>, C> createQuery(@Nonnull F filter,
            @Nullable SortSpecification<S> sortSpecification, @Nonnull Dao<?, T, F, S, C> dao) {
        return new FindQuery<T, F, S, C>(filter, sortSpecification, dao);
    }

    @Override
    public List<T> execute(C context) throws PersistenceException {
        return this.dao.find(this.filter, this.sortSpecification, context);
    }
}
