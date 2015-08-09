/*
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.persistence.common.query;

import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;

public class VoidQuery<C> implements Query<Void, C> {

    /*
     * NOTE: There are several ways of implementing the singleton pattern, some of them more secure
     * than others guaranteeing that one and only one instance will exists in the system (taking
     * care of deserialization). However, the singleton pattern is used here to minimize the number
     * of instances of this class since all of them will behave the same. It is irrelevant if the
     * system ended up with more than one instance of this class.
     */
    @SuppressWarnings("rawtypes")
    private static final VoidQuery INSTANCE = new VoidQuery();

    private VoidQuery() {

    }

    /**
     * Gets the only instance of this class.
     *
     * @return the only instance of this class
     */
    @SuppressWarnings("unchecked")
    public static <C> VoidQuery<C> getInstance() {
        return INSTANCE;
    }

    @Override
    public Void execute(C context) throws PersistenceException {
        return null;
    }
}

