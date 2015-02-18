/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.dao.query;

import java.util.Collections;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.PagedDao;
import org.opendaylight.persistence.dao.query.TestCase.Context;
import org.opendaylight.persistence.dao.query.TestCase.Filter;
import org.opendaylight.persistence.dao.query.TestCase.MyIdentifiable;
import org.opendaylight.persistence.dao.query.TestCase.SortKey;
import org.opendaylight.persistence.util.common.type.SortSpecification;
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
        SortSpecification<SortKey> sortSpec = new SortSpecification<SortKey>();
        Context context = new Context();

        @SuppressWarnings("unchecked")
        PagedDao<?, MyIdentifiable, Filter, SortKey, PageRequest, Page<PageRequest, MyIdentifiable>, Context> daoMock = EasyMock
                .createMock(PagedDao.class);

        EasyMock.expect(
                daoMock.find(EasyMock.same(filter), EasyMock.same(sortSpec), EasyMock.same(pageRequest),
                        EasyMock.same(context))).andReturn(expectedResult);

        EasyMock.replay(daoMock);

        Query<Page<PageRequest, MyIdentifiable>, Context> query = PagedFindQuery.createQuery(filter, sortSpec,
                pageRequest, daoMock);
        Assert.assertSame(expectedResult, query.execute(context));

        EasyMock.verify(daoMock);
    }
}
