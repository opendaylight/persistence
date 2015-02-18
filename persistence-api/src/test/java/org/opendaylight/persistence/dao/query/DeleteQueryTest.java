/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.dao.query;

import org.easymock.EasyMock;
import org.junit.Test;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.Dao;
import org.opendaylight.persistence.dao.query.TestCase.Context;
import org.opendaylight.persistence.dao.query.TestCase.Filter;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class DeleteQueryTest {

    @Test
    public void testExecute() throws Exception {
        Filter filter = new Filter();
        Context context = new Context();

        @SuppressWarnings("unchecked")
        Dao<?, ?, Filter, ?, Context> daoMock = EasyMock.createMock(Dao.class);

        daoMock.delete(EasyMock.same(filter), EasyMock.same(context));

        EasyMock.replay(daoMock);

        Query<Void, Context> query = DeleteQuery.createQuery(filter, daoMock);
        query.execute(context);

        EasyMock.verify(daoMock);
    }
}
