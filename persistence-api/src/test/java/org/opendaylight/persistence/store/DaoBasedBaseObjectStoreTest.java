/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.store;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.dao.BaseDao;
import org.opendaylight.persistence.dao.query.AddQuery;
import org.opendaylight.persistence.dao.query.DeleteByIdQuery;
import org.opendaylight.persistence.dao.query.ExistQuery;
import org.opendaylight.persistence.dao.query.GetQuery;
import org.opendaylight.persistence.dao.query.UpdateQuery;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class DaoBasedBaseObjectStoreTest {

    private DataStore<Context> dataStoreMock;
    private BaseObjectStore<Long, IdentifiableObj> objectStore;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        this.dataStoreMock = EasyMock.createMock(DataStore.class);
        BaseDao<Long, IdentifiableObj, Context> daoMock = EasyMock.createMock(BaseDao.class);
        this.objectStore = new DaoBasedBaseObjectStore<Long, IdentifiableObj, Context>(this.dataStoreMock, daoMock);
    }

    @SuppressWarnings("unused")
    @Test(expected = NullPointerException.class)
    public void testInvalidCreationCase1() {
        final DataStore<Context> invalidDataStoreMock = null;
        @SuppressWarnings("unchecked")
        final BaseDao<Long, IdentifiableObj, Context> validDao = EasyMock.createMock(BaseDao.class);
        new DaoBasedBaseObjectStore<Long, IdentifiableObj, Context>(invalidDataStoreMock, validDao);
    }

    @SuppressWarnings("unused")
    @Test(expected = NullPointerException.class)
    public void testInvalidCreationCase2() {
        @SuppressWarnings("unchecked")
        final DataStore<Context> validDataStoreMock = EasyMock.createMock(DataStore.class);
        final BaseDao<Long, IdentifiableObj, Context> invalidDao = null;
        new DaoBasedBaseObjectStore<Long, IdentifiableObj, Context>(validDataStoreMock, invalidDao);
    }

    @Test
    public void testAdd() throws PersistenceException {
        QueryArgumentMatcher<IdentifiableObj, Context> queryArgumentMatcher = QueryArgumentMatcher
                .valueOf(AddQuery.class);
        IdentifiableObj toAdd = EasyMock.createMock(IdentifiableObj.class);
        IdentifiableObj expected = EasyMock.createMock(IdentifiableObj.class);
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(expected);
        EasyMock.replay(this.dataStoreMock);
        Assert.assertSame(expected, this.objectStore.add(toAdd));
        EasyMock.verify(this.dataStoreMock);
    }

    @Test
    public void testUpdate() throws PersistenceException {
        QueryArgumentMatcher<IdentifiableObj, Context> queryArgumentMatcher = QueryArgumentMatcher
                .valueOf(UpdateQuery.class);
        IdentifiableObj toUpdate = EasyMock.createMock(IdentifiableObj.class);
        IdentifiableObj expected = EasyMock.createMock(IdentifiableObj.class);
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(expected);
        EasyMock.replay(this.dataStoreMock);
        Assert.assertSame(expected, this.objectStore.update(toUpdate));
        EasyMock.verify(this.dataStoreMock);
    }

    @Test
    public void testDelete() throws PersistenceException {
        QueryArgumentMatcher<Void, Context> queryArgumentMatcher = QueryArgumentMatcher.valueOf(DeleteByIdQuery.class);
        Long id = Long.valueOf(1);
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(null);
        EasyMock.replay(this.dataStoreMock);
        this.objectStore.delete(id);
        EasyMock.verify(this.dataStoreMock);
    }

    @Test
    public void testGet() throws PersistenceException {
        QueryArgumentMatcher<IdentifiableObj, Context> queryArgumentMatcher = QueryArgumentMatcher
                .valueOf(GetQuery.class);
        Long id = Long.valueOf(1);
        IdentifiableObj expected = EasyMock.createMock(IdentifiableObj.class);
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(expected);
        EasyMock.replay(this.dataStoreMock);
        Assert.assertSame(expected, this.objectStore.get(id));
        EasyMock.verify(this.dataStoreMock);
    }

    @Test
    public void testExist() throws PersistenceException {
        QueryArgumentMatcher<Boolean, Context> queryArgumentMatcher = QueryArgumentMatcher.valueOf(ExistQuery.class);
        Long id = Long.valueOf(1);
        boolean expected = true;
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(Boolean.valueOf(expected));
        EasyMock.replay(this.dataStoreMock);
        Assert.assertEquals(Boolean.valueOf(expected), Boolean.valueOf(this.objectStore.exist(id)));
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
