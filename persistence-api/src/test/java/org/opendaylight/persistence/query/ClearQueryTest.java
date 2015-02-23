/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.query;

import org.easymock.EasyMock;
import org.junit.Test;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.KeyValueDao;
import org.opendaylight.persistence.query.TestCase.Context;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class ClearQueryTest {

    @Test
    public void testExecute() throws Exception {
        Context context = new Context();

        @SuppressWarnings("unchecked")
        KeyValueDao<?, ?, Object> daoMock = EasyMock.createMock(KeyValueDao.class);

        daoMock.clear(EasyMock.same(context));

        EasyMock.replay(daoMock);

        Query<Void, Object> query = ClearQuery.createQuery(daoMock);
        query.execute(context);

        EasyMock.verify(daoMock);
    }
}
