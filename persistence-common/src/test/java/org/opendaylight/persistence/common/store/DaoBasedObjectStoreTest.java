/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.common.store;

import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.common.query.CountQuery;
import org.opendaylight.persistence.common.query.DeleteQuery;
import org.opendaylight.persistence.common.query.FindQuery;
import org.opendaylight.persistence.dao.Dao;
import org.opendaylight.persistence.store.ObjectStore;
import org.opendaylight.persistence.util.common.type.Sort;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings("javadoc")
public class DaoBasedObjectStoreTest {

    private DataStore<Context> dataStoreMock;
    private ObjectStore<Long, IdentifiableObj, Filter, SortKey> objectStore;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        this.dataStoreMock = EasyMock.createMock(DataStore.class);
        Dao<Long, IdentifiableObj, Filter, SortKey, Context> daoMock = EasyMock.createMock(Dao.class);
        this.objectStore = new DaoBasedObjectStore<Long, IdentifiableObj, Filter, SortKey, Context>(this.dataStoreMock,
                daoMock);
    }

    @Test
    public void testFind() throws PersistenceException {
        QueryArgumentMatcher<List<IdentifiableObj>, Context> queryArgumentMatcher = QueryArgumentMatcher
                .valueOf(FindQuery.class);
        Filter filter = EasyMock.createMock(Filter.class);
        List<Sort<SortKey>> sort = Collections.emptyList();
        @SuppressWarnings("unchecked")
        List<IdentifiableObj> expected = EasyMock.createMock(List.class);
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(expected);
        EasyMock.replay(this.dataStoreMock);
        Assert.assertSame(expected, this.objectStore.find(filter, sort));
        EasyMock.verify(this.dataStoreMock);
    }

    @Test
    public void testCount() throws PersistenceException {
        QueryArgumentMatcher<Long, Context> queryArgumentMatcher = QueryArgumentMatcher.valueOf(CountQuery.class);
        Filter filter = EasyMock.createMock(Filter.class);
        long expected = 1;
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(Long.valueOf(expected));
        EasyMock.replay(this.dataStoreMock);
        Assert.assertEquals(expected, this.objectStore.count(filter));
        EasyMock.verify(this.dataStoreMock);
    }

    @Test
    public void testDelete() throws PersistenceException {
        QueryArgumentMatcher<Void, Context> queryArgumentMatcher = QueryArgumentMatcher.valueOf(DeleteQuery.class);
        Filter filter = EasyMock.createMock(Filter.class);
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(null);
        EasyMock.replay(this.dataStoreMock);
        this.objectStore.delete(filter);
        EasyMock.verify(this.dataStoreMock);
    }

    private static class IdentifiableObj implements Identifiable<Long> {

        @Override
        public Long getIdentifier() {
            return null;
        }
    }

    private static class Filter {

    }

    private static class SortKey {

    }

    private static class Context {

    }
}
