/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.common.query;

import java.util.ArrayList;
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
import org.opendaylight.persistence.dao.Dao;
import org.opendaylight.persistence.util.common.type.Sort;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class FindQueryTest {

    @Test
    public void testExecute() throws Exception {
        List<MyIdentifiable> expectedResult = new ArrayList<MyIdentifiable>();
        Filter filter = new Filter();
        List<Sort<SortKey>> sort = Collections.emptyList();
        Context context = new Context();

        @SuppressWarnings("unchecked")
        Dao<?, MyIdentifiable, Filter, SortKey, Context> daoMock = EasyMock.createMock(Dao.class);

        EasyMock.expect(daoMock.find(EasyMock.same(filter), EasyMock.same(sort), EasyMock.same(context)))
                .andReturn(expectedResult);

        EasyMock.replay(daoMock);

        Query<List<MyIdentifiable>, Context> query = FindQuery.createQuery(filter, sort, daoMock);

        List<MyIdentifiable> result = query.execute(context);
        Assert.assertSame(expectedResult, result);

        EasyMock.verify(daoMock);
    }
}
