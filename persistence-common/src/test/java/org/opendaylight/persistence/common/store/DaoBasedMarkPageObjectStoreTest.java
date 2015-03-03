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
import org.opendaylight.persistence.common.query.PagedFindQuery;
import org.opendaylight.persistence.dao.MarkPageDao;
import org.opendaylight.persistence.store.MarkPageObjectStore;
import org.opendaylight.persistence.util.common.type.Sort;
import org.opendaylight.persistence.util.common.type.page.MarkPage;
import org.opendaylight.persistence.util.common.type.page.MarkPageRequest;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings("javadoc")
public class DaoBasedMarkPageObjectStoreTest {

    private DataStore<Context> dataStoreMock;
    private MarkPageObjectStore<Long, IdentifiableObj, Filter, SortKey> objectStore;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        this.dataStoreMock = EasyMock.createMock(DataStore.class);
        MarkPageDao<Long, IdentifiableObj, Filter, SortKey, Context> daoMock = EasyMock.createMock(MarkPageDao.class);
        this.objectStore = new DaoBasedMarkPageObjectStore<Long, IdentifiableObj, Filter, SortKey, Context>(
                this.dataStoreMock, daoMock);
    }

    @Test
    public void testFind() throws PersistenceException {
        QueryArgumentMatcher<MarkPage<IdentifiableObj>, Context> queryArgumentMatcher = QueryArgumentMatcher
                .valueOf(PagedFindQuery.class);
        Filter filter = EasyMock.createMock(Filter.class);
        List<Sort<SortKey>> sort = Collections.emptyList();
        @SuppressWarnings("unchecked")
        MarkPageRequest<IdentifiableObj> pageRequest = EasyMock.createMock(MarkPageRequest.class);
        @SuppressWarnings("unchecked")
        MarkPage<IdentifiableObj> expected = EasyMock.createMock(MarkPage.class);
        EasyMock.expect(this.dataStoreMock.execute(queryArgumentMatcher.match())).andReturn(expected);
        EasyMock.replay(this.dataStoreMock);
        Assert.assertSame(expected, this.objectStore.find(filter, sort, pageRequest));
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
