/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.store;

import java.util.Collection;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.dao.KeyValueDao;
import org.opendaylight.persistence.query.ClearQuery;
import org.opendaylight.persistence.query.GetAllQuery;
import org.opendaylight.persistence.query.SizeQuery;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings("javadoc")
public class DaoBasedKeyValueObjectStoreTest {

    private DataStore<Context> dataStoreMock;
    private KeyValueObjectStore<Long, IdentifiableObj> objectStore;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        this.dataStoreMock = EasyMock.createMock(DataStore.class);
        KeyValueDao<Long, IdentifiableObj, Context> daoMock = EasyMock.createMock(KeyValueDao.class);
        this.objectStore = new DaoBasedKeyValueObjectStore<Long, IdentifiableObj, Context>(this.dataStoreMock, daoMock);
    }

    @Test
    public void testGetAll() throws PersistenceException {
        QueryArgumentMatcher<Collection<IdentifiableObj>, Context> queryArgumentMatcher = QueryArgumentMatcher
                .valueOf(GetAllQuery.class);
        @SuppressWarnings("unchecked")
        Collection<IdentifiableObj> expected = EasyMock.createMock(Collection.class);
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(expected);
        EasyMock.replay(this.dataStoreMock);
        Assert.assertEquals(expected, this.objectStore.getAll());
        EasyMock.verify(this.dataStoreMock);
    }

    @Test
    public void testSize() throws PersistenceException {
        QueryArgumentMatcher<Long, Context> queryArgumentMatcher = QueryArgumentMatcher.valueOf(SizeQuery.class);
        long expected = 1;
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(Long.valueOf(expected));
        EasyMock.replay(this.dataStoreMock);
        Assert.assertEquals(expected, this.objectStore.size());
        EasyMock.verify(this.dataStoreMock);
    }

    @Test
    public void testClear() throws PersistenceException {
        QueryArgumentMatcher<Void, Context> queryArgumentMatcher = QueryArgumentMatcher.valueOf(ClearQuery.class);
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(null);
        EasyMock.replay(this.dataStoreMock);
        this.objectStore.clear();
        EasyMock.verify(this.dataStoreMock);
    }

    private static class IdentifiableObj implements Identifiable<Long> {

        @Override
        public Long getIdentifier() {
            return null;
        }
    }

    private static class Context {

    }
}
