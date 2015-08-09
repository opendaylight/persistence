/*
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.persistence.common.query;

import java.io.Serializable;

import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.dao.BaseDao;
//import org.opendaylight.persistence.util.common.Identifiable;
import org.opendaylight.yangtools.concepts.Identifiable;

public class StoreQuery<I extends Serializable, T extends Identifiable<I>, C> implements Query<Void, C> {
    private T identifiable;
    private I id;
    private BaseDao<I, T, C> dao;

    private StoreQuery(I id, T identifiable, BaseDao<I, T, C> dao) {
    	this.identifiable = identifiable;
        this.id = id;
        this.dao = dao;
    }

    /**
     * Creates a query.
     * <p>
     * This method is a convenience to infer the generic types.
     *
     * @param identifiable object to store
     * @param dao DAO to assist the query
     * @return the query
     */
    public static <I extends Serializable, T extends Identifiable<I>, C> Query<Void, C> createQuery(
            I id, T identifiable, BaseDao<I, T, C> dao) {
        return new StoreQuery<I, T, C>(id, identifiable, dao);
    }

    @Override
    public Void execute(C context) throws PersistenceException {
        if (this.dao.exist(this.id, context)) {
            this.dao.update(this.identifiable, context);
        }
        else {
            this.dao.add(this.identifiable, context);
        }
        return null;
    }
}
