/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.query;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.Dao;
import org.opendaylight.persistence.query.CountQuery;
import org.opendaylight.persistence.query.TestCase.Context;
import org.opendaylight.persistence.query.TestCase.Filter;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class CountQueryTest {

    @Test
    @SuppressWarnings("boxing")
    public void testExecute() throws Exception {
        Filter filter = new Filter();
        Context context = new Context();

        @SuppressWarnings("unchecked")
        Dao<?, ?, Filter, ?, Context> daoMock = EasyMock.createMock(Dao.class);

        Long result = Long.valueOf(10);
        EasyMock.expect(daoMock.count(EasyMock.same(filter), EasyMock.same(context))).andReturn(result);

        EasyMock.replay(daoMock);

        Query<Long, Context> query = CountQuery.createQuery(filter, daoMock);
        Assert.assertEquals(result, query.execute(context));

        EasyMock.verify(daoMock);
    }
}
