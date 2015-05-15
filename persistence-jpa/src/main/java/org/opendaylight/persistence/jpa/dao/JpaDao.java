/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.jpa.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.dao.Dao;
import org.opendaylight.persistence.dao.UpdateStrategy;
import org.opendaylight.persistence.jpa.JpaContext;
import org.opendaylight.persistence.jpa.dao.JpaUtil.PredicateProvider;
import org.opendaylight.persistence.util.common.Converter;
import org.opendaylight.persistence.util.common.type.Sort;
import org.opendaylight.yangtools.concepts.Identifiable;

import com.google.common.base.Preconditions;

/**
 * JPA {@link Dao}.
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
public abstract class JpaDao<I extends Serializable, T extends Identifiable<I>, P, F, S>
        extends JpaKeyValueDao<I, T, P> implements Dao<I, T, F, S, JpaContext> {

    // QueryPredicateGenerator is state-less, so this class remains thread safe.
    private final JpaQueryPredicateGenerator<P> queryPredicateGenerator = JpaQueryPredicateGenerator
            .getInstance();

    // sortKeyConverter is state-less, so this class remains thread safe.
    private Converter<S, SingularAttribute<? super P, ?>> sortKeyConverter = new SortKeyConverter();

    /**
     * Construct a DAO.
     * 
     * @param entityClass
     *            class of the object annotated with {@link javax.persistence.Entity}
     */
    protected JpaDao(Class<P> entityClass) {
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
    protected JpaDao(Class<P> entityClass, UpdateStrategy<P, T> updateStrategy) {
        super(entityClass, updateStrategy);
    }

    /**
     * Get the entities from the data store that match the given filter.
     * 
     * @param filter
     *            filter to apply, {@code null} to retrieve all entities
     * @param sortSpecification
     *            sort specification
     * @param context
     *            data store context
     * @return the entities that match {@code filter} sorted as stated by {@code sortSpecification}
     * @throws PersistenceException
     *             if persistence errors occur while executing the operation
     */
    protected List<P> findEntities(final F filter,
            List<Sort<S>> sortSpecification, JpaContext context)
            throws PersistenceException {
        PredicateProvider<P> predicateProvider = new PredicateProvider<P>() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder,
                    Root<P> root) {
                return getQueryPredicate(filter, criteriaBuilder, root);
            }
        };

        List<Sort<SingularAttribute<? super P, ?>>> convertedSortList = convertSort(sortSpecification);
        return JpaUtil.find(getEntityClass(), predicateProvider,
                convertedSortList, context);
    }

    @Override
    public List<T> find(F filter, List<Sort<S>> sortSpecification,
            JpaContext context) throws PersistenceException {
        return convert(findEntities(filter, sortSpecification, context));
    }

    @Override
    public long count(final F filter, JpaContext context)
            throws PersistenceException {
        PredicateProvider<P> predicateProvider = new PredicateProvider<P>() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder,
                    Root<P> root) {
                return getQueryPredicate(filter, criteriaBuilder, root);
            }
        };
        return JpaUtil.count(getEntityClass(), predicateProvider, context);
    }

    @Override
    public void delete(final F filter, JpaContext context)
            throws PersistenceException {
        PredicateProvider<P> predicateProvider = new PredicateProvider<P>() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder,
                    Root<P> root) {
                return getQueryPredicate(filter, criteriaBuilder, root);
            }
        };

        JpaUtil.delete(getEntityClass(), predicateProvider, context);
    }

    /**
     * Gets a helper class to create query predicates based on filters.
     * 
     * @return A query pedicateGenerator
     */
    protected JpaQueryPredicateGenerator<P> getQueryPredicateGenerator() {
        return this.queryPredicateGenerator;
    }

    protected List<Sort<SingularAttribute<? super P, ?>>> convertSort(
            @Nullable List<Sort<S>> source) {
        //Preconditions.checkNotNull(source, "source");

		if (source != null) {
			List<Sort<SingularAttribute<? super P, ?>>> converted = new ArrayList<Sort<SingularAttribute<? super P, ?>>>(
					source.size());
			for (Sort<S> sortElement : source) {
				converted.add(sortElement.convert(this.sortKeyConverter));
			}

			return converted;
		}
        return null;
    }

    /**
     * Augment the underlying database query with specifics to be included as part of the where clause.
     * 
     * @param filter
     *            The input filter from which the criteria is set
     * @param builder
     *            the criteria builder object for augmenting the query object
     * @param root
     *            the root object type associated with the query
     * @return predicate object to be utilized in the query
     */
    protected abstract Predicate getQueryPredicate(F filter,
            CriteriaBuilder builder, Root<P> root);

    /**
     * Gets the singular attribute (JPA entity attribute -Column- definition)
     * 
     * @param sortKey
     *            sort key to map a column to
     * @return the singular attribute associated to the sorting key
     */
    protected abstract SingularAttribute<? super P, ?> getSingularAttribute(
            S sortKey);

    private class SortKeyConverter implements
            Converter<S, SingularAttribute<? super P, ?>> {

        @Override
        public SingularAttribute<? super P, ?> convert(S source) {
            return getSingularAttribute(source);
        }
    }
}
