/*
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.persistence.util.common.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opendaylight.persistence.util.common.Converter;

public class SortSpecification<T> {


    private List<Sort<T>> components;

    /**
     * Creates a sort specification.
     */
    public SortSpecification() {
        this.components = new ArrayList<Sort<T>>();
    }

    /**
     * Appends a sort by component.
     *
     * @param attribute attribute to sort by
     * @param sortOrder sort order
     */
    public void addSortComponent(T attribute, SortOrder sortOrder) {
        this.components.add(new Sort<T>(attribute, sortOrder));
    }

    /**
     * Gets the specification's sort components.
     *
     * @return the specification's sort components
     */
    public List<Sort<T>> getSortComponents() {
        return Collections.unmodifiableList(this.components);
    }

    /**
     * Converts this sort specification to a different 'sort by' data type.
     *
     * @param converter converter
     * @return a sort specification with the new data type
     */
    public <D> SortSpecification<D> convert(Converter<T, D> converter) {
        SortSpecification<D> converted = new SortSpecification<D>();
        for (Sort<T> component : this.components) {
            converted.addSortComponent(converter.convert(component.by()), component.order());
        }
        return converted;
    }

   /* @Override
    public String toString() {
        return ObjectToStringConverter.toString(
                this,
                Property.valueOf("components", this.components)
        );
    }*/


   /* public static final class Sort<T> {
        private T sortBy;
        private SortOrder sortOrder;

        private Sort(@Nonnull T sortBy, @Nonnull SortOrder sortOrder) {
            this.sortBy = Preconditions.checkNotNull(sortBy, "sortBy");
            this.sortOrder = Preconditions.checkNotNull(sortOrder, "sortOrder");
        }

        *//**
         * Creates an ascending sort component.
         *
         * @param sortBy
         *            attribute to sort by
         * @return a sort component
         *//*
        public static <T> Sort<T> ascending(@Nonnull T sortBy) {
            return new Sort<T>(sortBy, SortOrder.ASCENDING);
        }

        *//**
         * Creates a descending sort component.
         *
         * @param sortBy
         *            attribute to sort by
         * @return a sort component
         *//*
        public static <T> Sort<T> descending(@Nonnull T sortBy) {
            return new Sort<T>(sortBy, SortOrder.DESCENDING);
        }

        *//**
         * Gets the attribute to sort by.
         *
         * @return the attribute to sort by
         *//*
        public T by() {
            return this.sortBy;
        }

        *//**
         * Gets the sort order.
         *
         * @return the sort order
         *//*
        public SortOrder order() {
            return this.sortOrder;
        }

        *//**
         * Converts Sort to a different sort by type.
         *
         * @param converter
         * @return Sort type
         *//*
        public <E> Sort<E> convert(Converter<T, E> converter) {
            return new Sort<E>(converter.convert(this.sortBy), this.sortOrder);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.sortBy, this.sortOrder);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            Sort<?> other = (Sort<?>) obj;

            if (!Objects.equal(this.sortBy, other.sortBy)) {
                return false;
            }

            if (this.sortOrder != other.sortOrder) {
                return false;
            }

            return true;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("by", this.sortBy)
                    .add("order", this.sortOrder).toString();
        }
    }
*/


}
