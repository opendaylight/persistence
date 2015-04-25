/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.jpa.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.dao.OffsetPageDao;
import org.opendaylight.persistence.dao.UpdateStrategy;
import org.opendaylight.persistence.jpa.JpaContext;
import org.opendaylight.persistence.jpa.dao.JpaUtil.PredicateProvider;
import org.opendaylight.persistence.util.common.type.Sort;
import org.opendaylight.persistence.util.common.type.page.OffsetPage;
import org.opendaylight.persistence.util.common.type.page.OffsetPageRequest;
import org.opendaylight.yangtools.concepts.Identifiable;

/**
 * JPA {@link OffsetPageDao}.
 * <p>
 * This class must remain state-less so it is thread safe.
 * <p>
 * A DAO should be used by {@link org.opendaylight.persistence.Query} queries.
 * 
 * @param <I>
 *            type of the identifiable object's id. This type should be immutable and it is critical it implements
 *            {@link Object#equals(Object)} and {@link Object#hashCode()} correctly.
 * @param <T>
 *            type of the identifiable object (object to store in the data store)
 * @param <P>
 *            type of the entity (an object annotated with {@link javax.persistence.Entity})
 * @param <F>
 *            type of the associated filter. A DAO is responsible for translating this filter to any mechanism
 *            understood by the underlying data store or database technology. For example, predicates in JPA-based
 *            implementations, or WHERE clauses in SQL-base implementations.
 * @param <S>
 *            type of the associated sort attribute or sort key used to construct sort specifications. A DAO is
 *            responsible for translating this specification to any mechanism understood by the underlying data store or
 *            database technology. For example, ORDER BY clauses in SQL-based implementations.
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public abstract class JpaOffsetPageDao<I extends Serializable, T extends Identifiable<I>, P, F, S>
        extends JpaDao<I, T, P, F, S> implements
        OffsetPageDao<I, T, F, S, JpaContext> {

    /**
     * Construct a DAO.
     * 
     * @param entityClass
     *            class of the object annotated with {@link javax.persistence.Entity}
     */
    protected JpaOffsetPageDao(Class<P> entityClass) {
        super(entityClass);
    }

    /**
     * Creates a DAO.
     * 
     * @param entityClass
     *            class of the object annotated with {@link javax.persistence.Entity}
     * @param updateStrategy
     *            update strategy
     */
    protected JpaOffsetPageDao(Class<P> entityClass,
            UpdateStrategy<P, T> updateStrategy) {
        super(entityClass, updateStrategy);
    }

    /**
     * Get a page of entities from the data store that match the given filter.
     * 
     * @param filter
     *            filter to apply, {@code null} to consider all entities
     * @param sortSpecification
     *            sort specification
     * @param pageRequest
     *            page request
     * @param context
     *            data store context
     * @return a page of objects that match {@code filter} sorted as stated by {@code sortSpecification}
     * @throws IndexOutOfBoundsException
     *             if the {@code pageRequest} is invalid
     * @throws PersistenceException
     *             if persistence errors occur while executing the operation
     */
    protected OffsetPage<P> findEntities(final F filter,
            List<Sort<S>> sortSpecification, OffsetPageRequest pageRequest,
            JpaContext context) throws IndexOutOfBoundsException,
            PersistenceException {
        PredicateProvider<P> predicateProvider = new PredicateProvider<P>() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder,
                    Root<P> root) {
                return getQueryPredicate(filter, criteriaBuilder, root);
            }
        };
        return JpaUtil.find(getEntityClass(), predicateProvider,
                convertSort(sortSpecification), pageRequest, context);
    }

    @Override
    public OffsetPage<T> find(F filter, List<Sort<S>> sortSpecification,
            OffsetPageRequest pageRequest, JpaContext context)
            throws IndexOutOfBoundsException, PersistenceException {
        OffsetPage<P> entitiesPage = findEntities(filter, sortSpecification,
                pageRequest, context);
        return entitiesPage.convert(this);
    }
}
