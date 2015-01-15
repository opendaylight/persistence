/*
 * Copyright (c) 2012 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence;

/**
 * Query to perform persistence operations.
 * 
 * @param <T> type of the query's result
 * @param <C> type of the query's execution context. This context should provide everything the
 *            query needs to perform persistence operations (A Database connection for example).
 * @see DataStore
 * @author Robert Gagnon
 * @author Fabiel Zuniga
 */
public interface Query<T, C> {

    /**
     * Executes the query.
     * 
     * @param context query execution context. This context should be considered valid just for the
     *            execution of this query. After the query finish execution the context may be
     *            destroyed.
     * @return the result of the query
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    T execute(C context) throws PersistenceException;
}
