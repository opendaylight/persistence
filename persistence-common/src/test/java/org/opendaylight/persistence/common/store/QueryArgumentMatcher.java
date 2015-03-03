/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.common.store;

import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.util.test.easymock.ArgumentMatcher;

/**
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
@SuppressWarnings("rawtypes")
class QueryArgumentMatcher<T, C> extends ArgumentMatcher<Query<T, C>> {

    private Class<? extends Query> queryClass;

    private QueryArgumentMatcher(Class<? extends Query> queryClass) {
        super(queryClass.getName());
        this.queryClass = queryClass;
    }

    public static <T, C> QueryArgumentMatcher<T, C> valueOf(Class<? extends Query> queryClass) {
        return new QueryArgumentMatcher<T, C>(queryClass);
    }

    @Override
    public boolean verifyMatch(Query<T, C> argument) {
        return this.queryClass.isInstance(argument);
    }
}
