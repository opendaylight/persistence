/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.jpa.jpql;

import javax.annotation.Nonnull;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.opendaylight.persistence.util.common.filter.ComparabilityCondition;
import org.opendaylight.persistence.util.common.filter.IntervalCondition;
import org.opendaylight.persistence.util.common.type.Interval;

import com.google.common.base.Preconditions;

/**
 * Interval condition predicate.
 * 
 * @param <P>
 *            type of the entity type of the entity (an object annotated with {@link javax.persistence.Entity})
 * @param <D>
 *            type of the attribute
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
class JpqlIntervalCondition<P, D extends Comparable<D>> implements
        JpqlPredicate {

    private JpqlPredicate delegate;

    /**
     * Creates an equality predicate.
     * 
     * @param condition
     *            interval condition
     * @param attribute
     *            attribute to apply the condition to
     * @param entityClass
     *            persistent object class
     */
    public JpqlIntervalCondition(@Nonnull IntervalCondition<D> condition,
            @Nonnull SingularAttribute<? super P, D> attribute,
            @Nonnull Class<P> entityClass) {
        Preconditions.checkNotNull(condition, "condition");
        Preconditions.checkNotNull(attribute, "attribute");
        Preconditions.checkNotNull(entityClass, "entityClass");

        this.delegate = new IntervalPredicate<P, D>(condition.getValue(),
                attribute, entityClass);
        if (condition.getMode().equals(IntervalCondition.Mode.NOT_IN)) {
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

    private static class IntervalPredicate<P, D extends Comparable<D>>
            implements JpqlPredicate {

        private JpqlPredicate delegate;

        protected IntervalPredicate(Interval<D> interval,
                SingularAttribute<? super P, D> attribute, Class<P> entityClass) {
            String leftValueParameter = attribute.getName() + "LeftValue";
            String rightValueParameter = attribute.getName() + "RightValue";

            switch (interval.getType()) {
                case OPEN: {
                    ComparabilityCondition<D> leftCondition = ComparabilityCondition
                            .greaterThan(interval.getLeftEndpoint());
                    ComparabilityCondition<D> rightCondition = ComparabilityCondition
                            .lessThan(interval.getRightEndpoint());
                    this.delegate = new JpqlAnd(new JpqlComparability<P, D>(
                            leftCondition, attribute, entityClass,
                            leftValueParameter), new JpqlComparability<P, D>(
                            rightCondition, attribute, entityClass,
                            rightValueParameter));
                }
                    break;
                case CLOSED: {
                    ComparabilityCondition<D> leftCondition = ComparabilityCondition
                            .greaterThanOrEqualTo(interval.getLeftEndpoint());
                    ComparabilityCondition<D> rightCondition = ComparabilityCondition
                            .lessThanOrEqualTo(interval.getRightEndpoint());
                    this.delegate = new JpqlAnd(new JpqlComparability<P, D>(
                            leftCondition, attribute, entityClass,
                            leftValueParameter), new JpqlComparability<P, D>(
                            rightCondition, attribute, entityClass,
                            rightValueParameter));
                }
                    break;
                case LEFT_CLOSED_RIGHT_OPEN: {
                    ComparabilityCondition<D> leftCondition = ComparabilityCondition
                            .greaterThanOrEqualTo(interval.getLeftEndpoint());
                    ComparabilityCondition<D> rightCondition = ComparabilityCondition
                            .lessThan(interval.getRightEndpoint());
                    this.delegate = new JpqlAnd(new JpqlComparability<P, D>(
                            leftCondition, attribute, entityClass,
                            leftValueParameter), new JpqlComparability<P, D>(
                            rightCondition, attribute, entityClass,
                            rightValueParameter));
                }
                    break;
                case LEFT_OPEN_RIGHT_CLOSED: {
                    ComparabilityCondition<D> leftCondition = ComparabilityCondition
                            .greaterThan(interval.getLeftEndpoint());
                    ComparabilityCondition<D> rightCondition = ComparabilityCondition
                            .lessThanOrEqualTo(interval.getRightEndpoint());
                    this.delegate = new JpqlAnd(new JpqlComparability<P, D>(
                            leftCondition, attribute, entityClass,
                            leftValueParameter), new JpqlComparability<P, D>(
                            rightCondition, attribute, entityClass,
                            rightValueParameter));
                }
                    break;
                case LEFT_OPEN_RIGHT_UNBOUNDED: {
                    ComparabilityCondition<D> leftCondition = ComparabilityCondition
                            .greaterThan(interval.getLeftEndpoint());
                    this.delegate = new JpqlComparability<P, D>(leftCondition,
                            attribute, entityClass, leftValueParameter);
                }
                    break;
                case LEFT_CLOSED_RIGHT_UNBOUNDED: {
                    ComparabilityCondition<D> leftCondition = ComparabilityCondition
                            .greaterThanOrEqualTo(interval.getLeftEndpoint());
                    this.delegate = new JpqlComparability<P, D>(leftCondition,
                            attribute, entityClass, leftValueParameter);
                }
                    break;
                case LEFT_UNBOUNDED_RIGHT_OPEN: {
                    ComparabilityCondition<D> rightCondition = ComparabilityCondition
                            .lessThan(interval.getRightEndpoint());
                    this.delegate = new JpqlComparability<P, D>(rightCondition,
                            attribute, entityClass, rightValueParameter);
                }
                    break;
                case LEFT_UNBOUNDED_RIGHT_CLOSED: {
                    ComparabilityCondition<D> rightCondition = ComparabilityCondition
                            .lessThanOrEqualTo(interval.getRightEndpoint());
                    this.delegate = new JpqlComparability<P, D>(rightCondition,
                            attribute, entityClass, rightValueParameter);
                }
                    break;
                case UNBOUNDED: {
                    this.delegate = new JpqlTautology();
                }
                    break;
            }

            assert (this.delegate != null);
        }

        @Override
        public String getPredicate() {
            return this.delegate.getPredicate();
        }

        @Override
        public void addParameters(Query query) {
            this.delegate.addParameters(query);
        }
    }
}
