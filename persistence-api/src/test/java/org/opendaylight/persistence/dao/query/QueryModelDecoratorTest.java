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
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.query.TestCase.Context;
import org.opendaylight.persistence.util.common.Instruction;
import org.opendaylight.persistence.util.common.Subroutine;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class QueryModelDecoratorTest {

    @Test
    public void testExecute() throws PersistenceException {
        QueryResult queryResult = new QueryResult();
        Context context = new Context();

        @SuppressWarnings("unchecked")
        Query<QueryResult, Context> delegateMock = EasyMock.createMock(Query.class);
        Instruction preProcessingMock = EasyMock.createMock(Instruction.class);
        @SuppressWarnings("unchecked")
        Subroutine<QueryResult> postProcessingMock = EasyMock.createMock(Subroutine.class);

        preProcessingMock.execute();
        EasyMock.expect(delegateMock.execute(EasyMock.same(context))).andReturn(queryResult);
        postProcessingMock.execute(EasyMock.same(queryResult));

        EasyMock.replay(delegateMock, preProcessingMock, postProcessingMock);

        Query<QueryResult, Context> queryModelDecorator = new QueryModelDecorator<QueryResult, Context>(delegateMock,
                preProcessingMock, postProcessingMock);
        queryModelDecorator.execute(context);

        EasyMock.verify(delegateMock, preProcessingMock, postProcessingMock);
    }

    private static class QueryResult {

    }
}
