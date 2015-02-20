/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.dao.query;

import javax.annotation.Nonnull;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Decorator that logs information related to the execution of the query (Like the query's name and
 * the time it took to execute).
 * 
 * @param <T> type of the identifiable object (object to store in the data store)
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public final class QueryLoggerDecorator<T, C> implements Query<T, C> {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryLoggerDecorator.class);

    private Query<T, C> delegate;

    /**
     * Creates a read query decorator.
     * 
     * @param delegate query delegate
     */
    public QueryLoggerDecorator(@Nonnull Query<T, C> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T execute(C context) throws PersistenceException {
        Class<?> queryClass = this.delegate.getClass();
        String queryName = queryClass.getSimpleName();

        LOGGER.debug("Executing query {}", queryName);

        T result = null;

        long startTime = System.currentTimeMillis();
        try {
            result = this.delegate.execute(context);
        }
        catch (Exception e) {
            LOGGER.error("Failure executing query {}", queryName, e);
            throw e;
        }
        long endTime = System.currentTimeMillis();

        LOGGER.debug("Query {} executed in {} ms generated result {}", queryName, Long.valueOf(endTime - startTime),
                result);

        return result;
    }
}
