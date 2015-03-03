/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.common.query;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.common.query.TestCase.Context;
import org.opendaylight.persistence.common.query.TestCase.Key;
import org.opendaylight.persistence.common.query.TestCase.MyIdentifiable;
import org.opendaylight.persistence.dao.BaseDao;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class ExistQueryTest {

    @SuppressWarnings("boxing")
    @Test
    public void testExecute() throws Exception {
        Key id = new Key();
        Context context = new Context();

        @SuppressWarnings("unchecked")
        BaseDao<Key, MyIdentifiable, Context> daoMock = EasyMock.createMock(BaseDao.class);

        EasyMock.expect(daoMock.exist(EasyMock.same(id), EasyMock.same(context))).andReturn(Boolean.TRUE);

        EasyMock.replay(daoMock);

        Query<Boolean, Context> query = ExistQuery.createQuery(id, daoMock);
        Assert.assertTrue(query.execute(context).booleanValue());

        EasyMock.verify(daoMock);
    }
}
