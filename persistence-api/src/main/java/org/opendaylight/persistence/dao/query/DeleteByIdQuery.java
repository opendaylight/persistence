/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.dao.query;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.BaseDao;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * Query to delete an object from the data store.
 * 
 * @param <I> type of the identifiable object's id
 * @param <T> type of the identifiable object (object to store in the data store)
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class DeleteByIdQuery<I extends Serializable, T extends Identifiable<I>, C> implements Query<Void, C> {

    private I id;
    private BaseDao<I, T, C> dao;

    private DeleteByIdQuery(@Nonnull I id, @Nonnull BaseDao<I, T, C> dao) {
        this.id = id;
        this.dao = dao;
    }

    /**
     * Creates a query.
     * <p>
     * This method is a convenience to infer the generic types.
     * 
     * @param id id of the object to delete
     * @param dao DAO to assist the query
     * @return the query
     */
    public static <I extends Serializable, T extends Identifiable<I>, C> Query<Void, C> createQuery(@Nonnull I id,
            @Nonnull BaseDao<I, T, C> dao) {
        return new DeleteByIdQuery<I, T, C>(id, dao);
    }

    @Override
    public Void execute(C context) throws PersistenceException {
        this.dao.delete(this.id, context);
        return null;
    }
}
