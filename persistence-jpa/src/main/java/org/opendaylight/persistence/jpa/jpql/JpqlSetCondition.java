/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.jpa.jpql;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.opendaylight.persistence.util.common.filter.SetCondition;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

/**
 * Set condition predicate.
 * 
 * @param <P>
 *            type of the entity type of the entity (an object annotated with {@link javax.persistence.Entity})
 * @param <D>
 *            type of the attribute
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
class JpqlSetCondition<P, D> implements JpqlPredicate {

    private JpqlPredicate delegate;

    /**
     * Creates an equality predicate.
     * 
     * @param condition
     *            set condition
     * @param attribute
     *            attribute to apply the condition to
     * @param entityClass
     *            persistent object class
     */
    public JpqlSetCondition(@Nonnull SetCondition<D> condition,
            @Nonnull SingularAttribute<? super P, D> attribute,
            @Nonnull Class<P> entityClass) {
        Preconditions.checkNotNull(condition, "condition");
        Preconditions.checkNotNull(attribute, "atttibute");
        Preconditions.checkNotNull(entityClass, "entityClass");

        this.delegate = new SetPredicate<P, D>(condition.getValues(),
                attribute, entityClass);
        if (condition.getMode().equals(SetCondition.Mode.NOT_IN)) {
            this.delegate = new JpqlNot(this.delegate);
        }
    }

    @Override
    public String getPredicate() {
        return this.delegate.getPredicate();
    }

    @Override
    public void addParameters(Query query) {
        this.delegate.addParameters(query);
    }

    private static class SetPredicate<P, D> implements JpqlPredicate {

        private Set<D> set;
        private SingularAttribute<? super P, D> attribute;
        private Class<P> entityClass;
        private String valueParameter;

        protected SetPredicate(Set<D> set,
                SingularAttribute<? super P, D> attribute, Class<P> entityClass) {
            this.set = set;
            this.attribute = attribute;
            this.entityClass = entityClass;
            this.valueParameter = attribute.getName() + "Values";
        }

        @Override
        public String getPredicate() {
            String attributeNameInQuery = JpqlUtil.getNameInQuery(
                    this.attribute, this.entityClass);
            return Joiner.on("")
                    .join(attributeNameInQuery, " In ", this.valueParameter)
                    .toString();
        }

        @Override
        public void addParameters(Query query) {
            query.setParameter(this.valueParameter, this.set);
        }
    }
}
