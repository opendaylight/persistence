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
import org.opendaylight.persistence.dao.KeyValueDao;
import org.opendaylight.persistence.query.SizeQuery;
import org.opendaylight.persistence.query.TestCase.Context;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class SizeQueryTest {

    @Test
    @SuppressWarnings("boxing")
    public void testExecute() throws Exception {
        Context context = new Context();

        @SuppressWarnings("unchecked")
        KeyValueDao<?, ?, Context> daoMock = EasyMock.createMock(Dao.class);

        Long result = Long.valueOf(10);
        EasyMock.expect(daoMock.size(EasyMock.same(context))).andReturn(result);

        EasyMock.replay(daoMock);

        Query<Long, Context> query = SizeQuery.createQuery(daoMock);
        Assert.assertEquals(result, query.execute(context));

        EasyMock.verify(daoMock);
    }
}
