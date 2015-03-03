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
import org.opendaylight.persistence.dao.Dao;

import com.google.common.base.Preconditions;

/**
 * Query to delete all objects from the data store that match the given filter.
 * 
 * @param <F> type of the associated filter
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class DeleteQuery<F, C> implements Query<Void, C> {

    private F filter;
    private Dao<?, ?, F, ?, C> dao;

    private DeleteQuery(@Nonnull F filter, @Nonnull Dao<?, ?, F, ?, C> dao) {
        Preconditions.checkNotNull(dao, "dao");
        this.filter = filter;
        this.dao = dao;
    }

    /**
     * Creates a query.
     * <p>
     * This method is a convenience to infer the generic types.
     * 
     * @param filter filter
     * @param dao DAO to assist the query
     * @return the query
     */
    public static <I extends Serializable, F, C> Query<Void, C> createQuery(@Nonnull F filter,
            @Nonnull Dao<I, ?, F, ?, C> dao) {
        return new DeleteQuery<F, C>(filter, dao);
    }

    @Override
    public Void execute(C context) throws PersistenceException {
        this.dao.delete(this.filter, context);
        return null;
    }
}
