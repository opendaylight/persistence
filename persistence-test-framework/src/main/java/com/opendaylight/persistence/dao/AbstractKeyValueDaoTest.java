/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.opendaylight.persistence.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.dao.KeyValueDao;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * Integration test for {@link KeyValueDao} implementations.
 * 
 * @param <I> type of the identifiable object's id
 * @param <T> type of the identifiable object (object to store in the data store)
 * @param <C> type of the query's execution context; the context managed by the {@link DataStore}
 * @param <D> type of the DAO to test
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public abstract class AbstractKeyValueDaoTest<I extends Serializable, T extends Identifiable<I>, C, D extends KeyValueDao<I, T, C>>
        extends AbstractBaseDaoTest<I, T, C, D> {

    /**
     * Creates a new DAO integration test.
     * 
     * @param dataStore data store
     */
    public AbstractKeyValueDaoTest(@Nonnull DataStore<C> dataStore) {
        super(dataStore);
    }

    /**
     * @throws PersistenceException if any errors occur during execution
     */
    @Test
    public void testGetAll() throws PersistenceException {
        List<T> expected = createIdentifiables(5);
        final Map<I, StoredObject<T>> searchSpace = store(expected);
        Assert.assertEquals(expected.size(), size());

        Collection<T> actual = execute(new DaoQuery<Collection<T>>() {
            @Override
            protected Collection<T> execute(D dao, C context) throws PersistenceException {
                return dao.getAll(context);
            }
        });

        assertSearch(expected, new ArrayList<T>(actual), searchSpace, false, null);
    }

    /**
     * @throws PersistenceException if any errors occur during execution
     */
    @Test
    public void testSize() throws PersistenceException {
        List<T> expected = createIdentifiables(5);
        store(expected);
        Assert.assertEquals(expected.size(), size());
    }

    /**
     * @throws PersistenceException if any errors occur during execution
     */
    @Test
    public void testClear() throws PersistenceException {
        List<T> expected = createIdentifiables(5);
        store(expected);
        Assert.assertEquals(expected.size(), size());
        clear();
        Assert.assertEquals(0, size());
    }

    @Override
    protected void clear() throws PersistenceException {
        execute(new DaoQuery<Void>() {
            @Override
            protected Void execute(D dao, C context) throws PersistenceException {
                dao.clear(context);
                return null;
            }
        });

        Assert.assertEquals(0, size());
    }

    @Override
    protected long size() throws PersistenceException {
        return execute(new DaoQuery<Long>() {
            @Override
            protected Long execute(D dao, C context) throws PersistenceException {
                return Long.valueOf(dao.size(context));
            }
        }).longValue();
    }
}
