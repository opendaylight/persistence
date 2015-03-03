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
import org.opendaylight.persistence.dao.BaseDao;
import org.opendaylight.persistence.query.UpdateQuery;
import org.opendaylight.persistence.query.TestCase.Context;
import org.opendaylight.persistence.query.TestCase.Key;
import org.opendaylight.persistence.query.TestCase.MyIdentifiable;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class UpdateQueryTest {

    @Test
    public void testExecute() throws Exception {
        MyIdentifiable toUpdate = new MyIdentifiable();
        MyIdentifiable updated = new MyIdentifiable();
        Context context = new Context();

        @SuppressWarnings("unchecked")
        BaseDao<Key, MyIdentifiable, Context> daoMock = EasyMock.createMock(BaseDao.class);

        EasyMock.expect(daoMock.update(EasyMock.same(toUpdate), EasyMock.same(context))).andReturn(updated);

        EasyMock.replay(daoMock);

        Query<MyIdentifiable, Context> query = UpdateQuery.createQuery(toUpdate, daoMock);
        Assert.assertSame(updated, query.execute(context));

        EasyMock.verify(daoMock);
    }
}
