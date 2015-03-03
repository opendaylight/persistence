/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.common.query;

import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.common.query.TestCase.Context;
import org.opendaylight.persistence.common.query.TestCase.Filter;
import org.opendaylight.persistence.common.query.TestCase.MyIdentifiable;
import org.opendaylight.persistence.common.query.TestCase.SortKey;
import org.opendaylight.persistence.dao.PagedDao;
import org.opendaylight.persistence.util.common.type.Sort;
import org.opendaylight.persistence.util.common.type.page.Page;
import org.opendaylight.persistence.util.common.type.page.PageRequest;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class PagedFindQueryTest {

    @Test
    public void testExecute() throws Exception {
        PageRequest pageRequest = new PageRequest(1);
        Page<PageRequest, MyIdentifiable> expectedResult = new Page<PageRequest, MyIdentifiable>(pageRequest,
                Collections.<MyIdentifiable> emptyList());
        Filter filter = new Filter();
        List<Sort<SortKey>> sort = Collections.emptyList();
        Context context = new Context();

        @SuppressWarnings("unchecked")
        PagedDao<?, MyIdentifiable, Filter, SortKey, PageRequest, Page<PageRequest, MyIdentifiable>, Context> daoMock = EasyMock
                .createMock(PagedDao.class);

        EasyMock.expect(
                daoMock.find(EasyMock.same(filter), EasyMock.same(sort), EasyMock.same(pageRequest),
                        EasyMock.same(context))).andReturn(expectedResult);

        EasyMock.replay(daoMock);

        Query<Page<PageRequest, MyIdentifiable>, Context> query = PagedFindQuery.createQuery(filter, sort,
                pageRequest, daoMock);
        Assert.assertSame(expectedResult, query.execute(context));

        EasyMock.verify(daoMock);
    }
}
