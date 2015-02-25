/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.jpa.jpql;

import javax.persistence.Query;

/**
 * Tautology predicate.
 * 
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
class JpqlTautology implements JpqlPredicate {

    @Override
    public String getPredicate() {
        return "1=1";
    }

    @Override
    public void addParameters(Query query) {

    }
}
