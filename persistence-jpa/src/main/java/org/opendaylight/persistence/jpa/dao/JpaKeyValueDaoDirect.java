/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.jpa.dao;

import java.io.Serializable;
import java.util.Collection;

import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.dao.KeyValueDao;
import org.opendaylight.persistence.jpa.JpaContext;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * {@link KeyValueDao} where the data transfer object pattern is not used and thus the {@link Identifiable} and the
 * entity (the object annotated with {@link javax.persistence.Entity}) are the same object.
 * <p>
 * This class must remain state-less so it is thread safe.
 * <p>
 * A DAO should be used by {@link org.opendaylight.persistence.Query} queries.
 * 
 * @param <I>
 *            type of the identifiable object's id. This type should be immutable and it is critical it implements
 *            {@link Object#equals(Object)} and {@link Object#hashCode()} correctly.
 * @param <T>
 *            type of the identifiable and entity object (object to store in the data store annotated with
 *            {@link javax.persistence.Entity})
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public class JpaKeyValueDaoDirect<I extends Serializable, T extends Identifiable<I>>
        implements KeyValueDao<I, T, JpaContext> {

    private final Class<T> entityClass;

    /**
     * Creates a DAO.
     * 
     * @param entityClass
     *            class of the object annotated with {@link javax.persistence.Entity}
     */
    public JpaKeyValueDaoDirect(Class<T> entityClass) {
        if (entityClass == null) {
            throw new NullPointerException("entityClass cannot be null");
        }

        this.entityClass = entityClass;
    }

    @Override
    public T add(T identifiable, JpaContext context)
            throws PersistenceException {
        if (identifiable == null) {
            throw new NullPointerException("identifiable cannot be null");
        }

        JpaUtil.persist(identifiable, context);
        return identifiable;
    }

    @Override
    public T update(T identifiable, JpaContext context)
            throws PersistenceException {
        if (identifiable == null) {
            throw new NullPointerException("identifiable cannot be null");
        }

        if (identifiable.getIdentifier() == null) {
            throw new NullPointerException("identifiable has no id");
        }

        JpaUtil.updateDetached(identifiable, context);
        return identifiable;
    }

    @Override
    public void delete(I id, JpaContext context) throws PersistenceException {
        T entity = get(id, context);
        deleteEntity(entity, context);
    }

    @Override
    public T get(I id, JpaContext context) throws PersistenceException {
        return JpaUtil.get(this.entityClass, id, context);
    }

    @Override
    public boolean exist(I id, JpaContext context) throws PersistenceException {
        return JpaUtil.exist(this.entityClass, id, context);
    }

    @Override
    public Collection<T> getAll(JpaContext context) throws PersistenceException {
        return JpaUtil.loadAll(getEntityClass(), context);
    }

    @Override
    public long size(JpaContext context) throws PersistenceException {
        return JpaUtil.size(getEntityClass(), context);
    }

    @Override
    public void clear(JpaContext context) throws PersistenceException {
        JpaUtil.delete(getEntityClass(), null, context);
    }

    /**
     * Gets the entity class.
     * 
     * @return the entity class
     */
    protected Class<T> getEntityClass() {
        return this.entityClass;
    }

    /**
     * Deletes the entity from the data store. Subclasses could override this method to do any pre-processing /
     * post-processing work.
     * 
     * @param entity
     *            entity to delete
     * @param context
     *            context
     * @throws PersistenceException
     *             if persistence errors occur while executing the operation
     */
    protected void deleteEntity(T entity, JpaContext context)
            throws PersistenceException {
        JpaUtil.delete(entity, context);
    }
}
