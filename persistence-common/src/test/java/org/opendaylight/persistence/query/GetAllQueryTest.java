/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.KeyValueDao;
import org.opendaylight.persistence.query.GetAllQuery;
import org.opendaylight.persistence.query.TestCase.Context;
import org.opendaylight.persistence.query.TestCase.MyIdentifiable;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class GetAllQueryTest {

    @Test
    public void testExecute() throws Exception {
        List<MyIdentifiable> expectedResult = new ArrayList<MyIdentifiable>();
        Context context = new Context();

        @SuppressWarnings("unchecked")
        KeyValueDao<?, MyIdentifiable, Context> daoMock = EasyMock.createMock(KeyValueDao.class);

        EasyMock.expect(daoMock.getAll(EasyMock.same(context))).andReturn(expectedResult);

        EasyMock.replay(daoMock);

        Query<Collection<MyIdentifiable>, Context> query = GetAllQuery.createQuery(daoMock);

        Collection<MyIdentifiable> result = query.execute(context);
        Assert.assertSame(expectedResult, result);

        EasyMock.verify(daoMock);
    }
}
